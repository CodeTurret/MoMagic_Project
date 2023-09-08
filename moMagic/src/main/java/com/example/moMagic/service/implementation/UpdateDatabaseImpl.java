package com.example.moMagic.service.implementation;

import com.example.moMagic.model.*;
import com.example.moMagic.repository.ChargeFailLogRepository;
import com.example.moMagic.repository.ChargeSuccessLogRepository;
import com.example.moMagic.repository.InboxRepository;
import com.example.moMagic.service.UpdateDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;

@Service
public class UpdateDatabaseImpl implements UpdateDatabase {

    private final ChargeSuccessLogRepository chargeSuccessLogRepository;
    private final ChargeFailLogRepository chargeFailLogRepository;
    private final InboxRepository inboxRepository;

    public UpdateDatabaseImpl(ChargeSuccessLogRepository chargeSuccessLogRepository, ChargeFailLogRepository chargeFailLogRepository,
                              InboxRepository inboxRepository) {
        this.chargeSuccessLogRepository = chargeSuccessLogRepository;
        this.chargeFailLogRepository = chargeFailLogRepository;
        this.inboxRepository = inboxRepository;
    }

    /**
     * This method will update the status field of Inbox
     * @param inbox
     * @param s
     * @return int
     */
    @Override
    public int updateInboxStatus(Inbox inbox, String s) {
        if(s.equalsIgnoreCase("S")){
            inbox.setStatus('S');
        }
        else if(s.equalsIgnoreCase("F")){
            inbox.setStatus('F');
        }
        Inbox inboxTemp = inboxRepository.save(inbox);
        if(!ObjectUtils.isEmpty(inboxTemp)){
            return 1;
        }
        return -1;
    }

    /**
     * This method will insert into ChangeSuccessLog table
     * @param inbox - Inbox
     * @param chargeConf - ChargeConf
     * @param keywordDetails - KeywordDetails
     * @param response - String
     * @return int
     */
    @Override
    public int updateSuccessLog(Inbox inbox, ChargeConf chargeConf, KeywordDetails keywordDetails, String response){
        String[] responseList = response.split("::");
        ChargeSuccessLog chargeSuccessLog = new ChargeSuccessLog();
        chargeSuccessLog.setSmsId(inbox.getId());
        chargeSuccessLog.setMsisdn(inbox.getMsisdn());
        chargeSuccessLog.setKeywordId(keywordDetails.getId());
        chargeSuccessLog.setAmount(chargeConf.getPrice());
        chargeSuccessLog.setAmountWithVat(chargeConf.getPriceWithVat());
        chargeSuccessLog.setValidity(chargeConf.getValidity());
        chargeSuccessLog.setChargeId(chargeConf.getId());
        chargeSuccessLog.setInsDate(new Timestamp(System.currentTimeMillis()));
        chargeSuccessLog.setTidRef(responseList[0]);
        chargeSuccessLog.setResponse(responseList[2]);
        chargeSuccessLog.setSms(inbox);
        chargeSuccessLog.setCharge(chargeConf);
        chargeSuccessLog.setKeyword(keywordDetails);

        ChargeSuccessLog resultChargeSuccessLog = chargeSuccessLogRepository.save(chargeSuccessLog);
        if(!ObjectUtils.isEmpty(resultChargeSuccessLog)){
            return 1;
        }
        return -1;
    }

    /**
     * This method will insert into ChangeFailLog table
     * @param inbox - Inbox
     * @param chargeConf - ChargeConf
     * @param keywordDetails - KeywordDetails
     * @param response - String
     * @return int
     */
    @Override
    public int updateFailLog(Inbox inbox, ChargeConf chargeConf, KeywordDetails keywordDetails, String response) {
        String[] responseList = response.split("::");
        ChargeFailLog chargeFailLog = new ChargeFailLog();
        chargeFailLog.setSmsId(inbox.getId());
        chargeFailLog.setMsisdn(inbox.getMsisdn());
        chargeFailLog.setKeywordId(keywordDetails.getId());
        chargeFailLog.setAmount(chargeConf.getPrice());
        chargeFailLog.setChargeId(chargeConf.getId());
        chargeFailLog.setInsDate(new Timestamp(System.currentTimeMillis()));
        chargeFailLog.setTidRef(responseList[0]);
        chargeFailLog.setFailCode(Integer.parseInt(responseList[1]));
        chargeFailLog.setResponse(responseList[2]);
        chargeFailLog.setSms(inbox);
        chargeFailLog.setCharge(chargeConf);
        chargeFailLog.setKeyword(keywordDetails);

        ChargeFailLog resultChargeFailLog = chargeFailLogRepository.save(chargeFailLog);
        if(!ObjectUtils.isEmpty(resultChargeFailLog)){
            return 1;
        }
        return -1;
    }


}
