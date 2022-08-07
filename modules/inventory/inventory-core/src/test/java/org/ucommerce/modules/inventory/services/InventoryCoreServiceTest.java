package org.ucommerce.modules.inventory.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ucommerce.modules.inventory.TestData;
import org.ucommerce.modules.inventory.model.*;
import org.ucommerce.shared.kernel.factory.ObjectFactory;
import org.ucommerce.shared.kernel.ids.ProductId;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryCoreServiceTest {

    private InventoryCoreService core;
    private MockInventoryRepository repository;

    @BeforeEach
    public void beforeEach() {
        core = new InventoryCoreService(repository);
        ObjectFactory.createFactory().register(AtpModifierListProvider.class, AtpModifierListProvider.class);
        ObjectFactory.createFactory().register(InventoryRepository.class, MockInventoryRepository.class);
        repository = (MockInventoryRepository) ObjectFactory.createFactory().createObject(InventoryRepository.class);
    }

    @Test
    public void shouldLoadAtpForMultipleLocationsForOneProduct() {

        //given
        repository.addStock(InventoryRepository.STOCK_CACHE_ID, TestData.site1, TestData.product1, 2);
        repository.addStock(InventoryRepository.STOCK_CACHE_ID, TestData.site2, TestData.product1, 3);

        //when
        AtpResult atp = core.getAtp(new AtpRequestData(new ProductId[]{TestData.product1}, new LocationId[]{TestData.site1, TestData.site2}));

        //then
        assertEquals(2, atp.atps().length);
        assertEquals(Set.of(new Atp(new Amount(2, "pcs"), TestData.site1, TestData.product1), new Atp(new Amount(3, "pcs"), TestData.site2, TestData.product1)), Set.of(atp.atps()), "Must be equal!");

    }

    @Test
    public void shouldLoadAtpForMultipleProductsForOneLocation() {
        //given
        repository.addStock(InventoryRepository.STOCK_CACHE_ID, TestData.site1, TestData.product1, 2);
        repository.addStock(InventoryRepository.STOCK_CACHE_ID, TestData.site1, TestData.product2, 3);

        //when
        AtpResult atp = core.getAtp(new AtpRequestData(new ProductId[]{TestData.product1, TestData.product2}, new LocationId[]{TestData.site1}));

        //then
        assertEquals(2, atp.atps().length);
        assertEquals(Set.of(new Atp(new Amount(2, "pcs"), TestData.site1, TestData.product1), new Atp(new Amount(3, "pcs"), TestData.site1, TestData.product2)), Set.of(atp.atps()), "Must be equal!");

    }

}