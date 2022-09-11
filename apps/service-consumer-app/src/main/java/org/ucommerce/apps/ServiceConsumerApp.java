package org.ucommerce.apps;


import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.ucommerce.apps.inventory.stockadjustment.StockAdjuster;
import org.ucommerce.modules.inventory.configuration.InventorySetup;

import java.util.Arrays;

@SpringBootApplication
public class ServiceConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerApp.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(ApplicationContext ctx) {
        return args -> {
            InventorySetup.initializeWithDefaults();
            new StockAdjuster().setInitialStock();
        };
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
               // System.out.println(beanName);
            }

        };
    }
}