package com.example.moMagic.scheduler;

import com.example.moMagic.repository.AppConfRepository;
import com.example.moMagic.util.helper.Interface.SchedulerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ChargingScheduler {

    
    private final AppConfRepository appConfRepository;
    private final SchedulerHelper schedulerHelper;
    private boolean schedulerEnabled = true;


    public ChargingScheduler(AppConfRepository appConfRepository, SchedulerHelper schedulerHelper) {
        this.appConfRepository = appConfRepository;
        this.schedulerHelper = schedulerHelper;
    }

    @Scheduled(fixedDelay = 30000) // Adjust the initial delay as needed
    public void runScheduledTask() {
        long startTime = System.currentTimeMillis();
        if (schedulerEnabled) {
            // Execute the task when the scheduler is enabled
            if(appConfRepository.findFirstByOrderById().getStatus() == 0){
                schedulerHelper.updateDatabaseRow();
                System.out.println("Scheduled task executed.");
            }
        }
        // Disable the scheduler after the task
        schedulerEnabled = false;
        // Wait for 200 millis (optional)
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Enable the scheduler after
        schedulerEnabled = true;
        long endTime = System.currentTimeMillis();
        long durationMinutes = (endTime - startTime) / 1000;
        System.out.println("Time Duration: "+ durationMinutes+ " seconds");

    }

}
