package org.ucommerce.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ucommerce.modules.inventory.commands.ReservationRequest;
import org.ucommerce.modules.inventory.model.AtpResult;
import org.ucommerce.modules.inventory.services.AtpService;

@Configuration
public class BeanConfig {

    @Bean
    public AtpService atpService() {
        return new AtpService() {
            @Override
            public AtpResult getAtp(ProductRequest productRequest) {
                return null;
            }

            @Override
            public AtpResult getAtp(LocationRequest productRequest) {
                return null;
            }

            @Override
            public void createReservation(ReservationRequest reservationRequest) {

            }

            @Override
            public void clearReservation(String externalIdentifier) {

            }
        };
    }
}
