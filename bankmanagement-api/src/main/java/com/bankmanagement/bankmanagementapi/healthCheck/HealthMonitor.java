package com.bankmanagement.bankmanagementapi.healthCheck;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class  HealthMonitor implements  HealthIndicator {

    @Override
    public Health health(){
        if(isReportServiceAvailable()){
            return Health.up().withDetail("Service","It is Up").build();
        }
        else {
           return Health.down().withDetail("Service","It is Down").build();
        }
    }

    private boolean isReportServiceAvailable(){
        return false;
    }
}
