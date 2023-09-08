package com.example.moMagic.util.helper.Implementation;

import com.example.moMagic.model.AppConf;
import com.example.moMagic.model.ChargeConf;
import com.example.moMagic.model.Inbox;
import com.example.moMagic.model.KeywordDetails;
import com.example.moMagic.repository.AppConfRepository;
import com.example.moMagic.repository.ChargeConfRepository;
import com.example.moMagic.repository.KeywordDetailsRepository;
import com.example.moMagic.service.InboxService;
import com.example.moMagic.service.UpdateDatabase;
import com.example.moMagic.util.helper.DisableSSL;
import com.example.moMagic.util.helper.Interface.SchedulerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class SchedulerHelperImpl implements SchedulerHelper {

    private final String unlockCodeUrl = "https://gb.gp.momagic.com.bd/unloc-code/test";
    private final String chargeCodeUrl = "https://gb.gp.momagic.com.bd/charge/test";
    
    private final AppConfRepository appConfRepository;
    
    private final KeywordDetailsRepository keywordDetailsRepository;
    
    private final InboxService inboxService;
    private final ChargeConfRepository chargeConfRepository;

    private final RestTemplate restTemplate;
    
    private final UpdateDatabase updateDatabase;


    private int currentPage = 0;

    public SchedulerHelperImpl(AppConfRepository appConfRepository, KeywordDetailsRepository keywordDetailsRepository, InboxService inboxService, RestTemplate restTemplate, DisableSSL disableSSL, ChargeConfRepository chargeConfRepository, UpdateDatabase updateDatabase) {
        this.appConfRepository = appConfRepository;
        this.keywordDetailsRepository = keywordDetailsRepository;
        this.inboxService = inboxService;
        this.restTemplate = restTemplate;
        this.chargeConfRepository = chargeConfRepository;
        this.updateDatabase = updateDatabase;
    }

    /**
     * This method will get data from various table then call some APIs,
     * and based on the response update some tables
     */
    @Override
    public void updateDatabaseRow() {
        // As this is a database transaction, and run it privately, that is why SSL is disabled
        DisableSSL.disableSslVerification();

        AppConf appConf = appConfRepository.findFirstByOrderById();
        // first get the first row then make it 1
        if(appConf.getStatus() == 0){
            appConf.setStatus(1);
            appConfRepository.save(appConf);
        }

        int pageSize = appConf.getNumberOfRows().intValue();

        // Use Page, as we have to process fixed row value that we get from appConf
        Page<Inbox> inboxPage = inboxService.getChunkOfInbox(pageSize, currentPage);
        int successResponse = 0;
        int failResponse = 0;

        if (inboxPage.hasContent()) {
            List<Inbox> inboxList = inboxPage.getContent();
            // Process the inboxList here

            List<KeywordDetails> keywordDetailsList = keywordDetailsRepository.findAll();
            KeywordDetails result = new KeywordDetails();
            try{
                for(Inbox inbox: inboxList){
                    if(inbox.getStatus() == 'N') {

                        String sms = inbox.getSmsText();
                        String[] words = sms.split("\\s+");
                        String firstWord = words[0];
                        String code = null;
                        Optional<KeywordDetails> keywordDetailsResult = keywordDetailsList.stream()
                                .filter(keywordDetails -> keywordDetails.getKeyword().equals(firstWord))
                                .findFirst();
                        if (keywordDetailsResult.isPresent()) {
                            // Found a matching KeywordDetails object
                            result = keywordDetailsResult.get();

                        } else {
                            // this will continue, because not matched with keyword_details
                            continue;
                        }

                        // Build the URL with query parameters
                        UriComponentsBuilder unlocCode = UriComponentsBuilder.fromHttpUrl(unlockCodeUrl)
                                .queryParam("msg", firstWord);

                        // Make a GET request to the third-party API
                        ResponseEntity<String> responseEntityForUnlocCode = restTemplate.getForEntity(unlocCode.toUriString(), String.class);

                        if (responseEntityForUnlocCode.getStatusCodeValue() != 200) {
                            // this will continue because unloc code api get error
                            continue;
                        }
                        try{
                            if(!ObjectUtils.isEmpty(responseEntityForUnlocCode)){
                                String[] codes = responseEntityForUnlocCode.getBody().split("::");
                                code = codes[1];
                            }
                        } catch (Exception e){
                            System.out.println("Error details: "+ e);
                        }


                        // Make request params for 2nd API
                        ChargeConf chargeConf = chargeConfRepository.findByPrice(Integer.parseInt(code));
                        UriComponentsBuilder chargeCode = UriComponentsBuilder.fromHttpUrl(chargeCodeUrl)
                                .queryParam("charge_code", chargeConf.getChargeCode())
                                .queryParam("msisdn",inbox.getMsisdn());

                        //Call the 2nd API for charging details
                        ResponseEntity<String> responseEntityForCharging = restTemplate.getForEntity(chargeCode.toUriString(), String.class);

                        if(!ObjectUtils.isEmpty(responseEntityForCharging) || responseEntityForCharging.getStatusCodeValue() == 200){
                            String resultFromServer = responseEntityForCharging.getBody();

                            if(resultFromServer.contains("Successful")){
                                successResponse = updateDatabase.updateSuccessLog(inbox,chargeConf,result,responseEntityForCharging.getBody());
                                if(successResponse == 1){
                                    updateDatabase.updateInboxStatus(inbox, "S");
                                }
                            } else if(resultFromServer.contains("Request failed")){
                                failResponse = updateDatabase.updateFailLog(inbox,chargeConf,result,responseEntityForCharging.getBody());
                                if(failResponse == 1){
                                    updateDatabase.updateInboxStatus(inbox, "F");
                                }
                            }
                        }

                    }
                }
            } catch (Exception e){
                System.out.println("Error details: "+ e);
                if(appConf.getStatus() == 1){
                    appConf.setStatus(0);
                    appConfRepository.save(appConf);
                }
            }

            // Update the current page for the next batch
            currentPage++;
        } else {

            if(appConf.getStatus() == 1){
                appConf.setStatus(0);
                appConfRepository.save(appConf);
            }
        }

        if(appConf.getStatus() == 1){
            appConf.setStatus(0);
            appConfRepository.save(appConf);
        }

    }
}
