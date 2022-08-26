package org.ucommerce.apps.inventory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ucommerce.apps.inventory.substitution.ReplacementChain;
import org.ucommerce.apps.inventory.substitution.ReplacementProductRule;
import org.ucommerce.apps.inventory.substitution.ReplacementRepository;
import org.ucommerce.modules.inventory.InventoryTestData;
import org.ucommerce.modules.inventory.InventoryTestFactory;
import org.ucommerce.modules.inventory.MockInventoryRepository;
import org.ucommerce.modules.inventory.model.*;
import org.ucommerce.modules.inventory.services.AtpService;
import org.ucommerce.modules.inventory.services.InventoryCore;
import org.ucommerce.modules.inventory.services.InventoryCoreService;
import org.ucommerce.modules.inventory.services.InventoryRepository;
import org.ucommerce.shared.kernel.factory.ObjectFactory;
import org.ucommerce.shared.kernel.ids.ProductId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReplacementProductRuleComponentTest {
    private ReplacementProductRule rule;
    ReplacementRepository replacementRepository;
    AtpService atpService;
    MockInventoryRepository inventoryRepository;
    @AfterEach
    public void afterEach(){
        InventoryCore.clear();
    }

    @BeforeEach
    public void beforeEach() {
        InventoryTestFactory.initializeObjectFactory(null);
        replacementRepository = new ReplacementRepository();
        ObjectFactory.createFactory().registerInstance(ReplacementRepository.class, replacementRepository);
        inventoryRepository = (MockInventoryRepository) ObjectFactory.createFactory().createObject(InventoryRepository.class);
        rule = new ReplacementProductRule();
        rule.registerRule();

        atpService = new InventoryCoreService(inventoryRepository);
    }


    @Test
    public void shouldAddSubstitutionWhenProductInChainChainIsAvailable() {

        //given
        replacementRepository.addChain(new ReplacementChain(InventoryTestData.product1, new ProductId[]{InventoryTestData.product1, InventoryTestData.product2}));
        inventoryRepository.addStock(InventoryRepository.STOCK_CACHE_ID, InventoryTestData.site1, InventoryTestData.product1, 2);
        inventoryRepository.addStock(InventoryRepository.STOCK_CACHE_ID, InventoryTestData.site2, InventoryTestData.product1, 20);
        inventoryRepository.addStock(InventoryRepository.STOCK_CACHE_ID, InventoryTestData.site1, InventoryTestData.product2, 3);
        inventoryRepository.addStock(InventoryRepository.STOCK_CACHE_ID, InventoryTestData.site2, InventoryTestData.product2, 10);

        //when
        AtpResult atpResult = atpService.getAtp(new AtpRequestData(new ProductId[]{InventoryTestData.product1}, new LocationId[]{InventoryTestData.site1},
                new AtpRequestFlag[]{new AtpRequestFlag(ReplacementProductRule.REPLACEMENT_FLAG_ID, true)}));

        //then
        assertEquals(1, atpResult.atps().length);
        Atp atp = atpResult.atps()[0];
        assertEquals(5, atp.amount().quantity());

    }

}