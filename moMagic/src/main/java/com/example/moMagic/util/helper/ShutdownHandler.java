package com.example.moMagic.util.helper;

import com.example.moMagic.model.AppConf;
import com.example.moMagic.repository.AppConfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
public class ShutdownHandler {

    @Autowired
    private AppConfRepository appConfRepository;

    @PreDestroy
    public void onShutdown() {
        AppConf appConf = appConfRepository.findFirstByOrderById();
        if(appConf.getStatus() == 1){
            appConf.setStatus(0);
            appConfRepository.save(appConf);
        }
    }
}
