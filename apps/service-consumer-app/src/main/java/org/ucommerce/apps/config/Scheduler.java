package org.ucommerce.apps.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.ucommerce.apps.inventory.stockadjustment.StockAdjuster;

@Configuration
@EnableScheduling
public class Scheduler {

    @Scheduled(fixedRate = 5_000L, initialDelay = 2_000L)
    public void scheduleStockUpdates() {
        new StockAdjuster().adjustStock();
    }

}
