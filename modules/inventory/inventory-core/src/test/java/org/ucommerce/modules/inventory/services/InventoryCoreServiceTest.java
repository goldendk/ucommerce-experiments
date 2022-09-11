package org.ucommerce.modules.inventory.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ucommerce.modules.inventory.InventoryTestData;
import org.ucommerce.modules.inventory.InventoryTestFactory;
import org.ucommerce.modules.inventory.model.*;
import org.ucommerce.shared.kernel.factory.ObjectFactory;
import org.ucommerce.shared.kernel.ids.ProductId;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryCoreServiceTest {

    private InventoryCoreService core;
    private MockInventoryRepository repository;

    @BeforeAll
    public static void beforeAll() {
        InventoryTestFactory.initializeObjectFactory(null);
    }

    @BeforeEach
    public void beforeEach() {
        core = new InventoryCoreService(repository);

        repository = (MockInventoryRepository) ObjectFactory.createFactory().createObject(InventoryRepository.class);
    }

    @Test
    public void shouldLoadAtpForMultipleLocationsForOneProduct() {

        //given
        repository.addStock(InventoryRepository.STOCK_CACHE_ID, InventoryTestData.site1, InventoryTestData.product1, 2);
        repository.addStock(InventoryRepository.STOCK_CACHE_ID, InventoryTestData.site2, InventoryTestData.product1, 3);

        //when
        AtpResult atp = core.getAtp(new AtpRequestData(new ProductId[]{InventoryTestData.product1}, new LocationId[]{InventoryTestData.site1, InventoryTestData.site2}));

        //then
        assertEquals(2, atp.atps().length);
        assertEquals(Set.of(new Atp(new Amount(2, "pcs"), InventoryTestData.site1, InventoryTestData.product1), new Atp(new Amount(3, "pcs"), InventoryTestData.site2, InventoryTestData.product1)), Set.of(atp.atps()), "Must be equal!");

    }

    @Test
    public void shouldLoadAtpForMultipleProductsForOneLocation() {
        //given
        repository.addStock(InventoryRepository.STOCK_CACHE_ID, InventoryTestData.site1, InventoryTestData.product1, 2);
        repository.addStock(InventoryRepository.STOCK_CACHE_ID, InventoryTestData.site1, InventoryTestData.product2, 3);

        //when
        AtpResult atp = core.getAtp(new AtpRequestData(new ProductId[]{InventoryTestData.product1, InventoryTestData.product2}, new LocationId[]{InventoryTestData.site1}));

        //then
        assertEquals(2, atp.atps().length);
        assertEquals(Set.of(new Atp(new Amount(2, "pcs"), InventoryTestData.site1, InventoryTestData.product1), new Atp(new Amount(3, "pcs"), InventoryTestData.site1, InventoryTestData.product2)), Set.of(atp.atps()), "Must be equal!");

    }

}