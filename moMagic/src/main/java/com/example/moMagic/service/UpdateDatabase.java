package com.example.moMagic.service;

import com.example.moMagic.model.ChargeConf;
import com.example.moMagic.model.Inbox;
import com.example.moMagic.model.KeywordDetails;

public interface UpdateDatabase {

    int updateInboxStatus(Inbox inbox, String s);

    int updateSuccessLog(Inbox inbox, ChargeConf chargeConf, KeywordDetails keywordDetails, String response);
    int updateFailLog(Inbox inbox, ChargeConf chargeConf, KeywordDetails keywordDetails, String response);
}
