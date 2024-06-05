package tn.esprit.rh.stock.services;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import tn.esprit.rh.stock.entities.Stock;

import tn.esprit.rh.stock.entities.SecteurActivite;
import tn.esprit.rh.stock.repositories.StockRepository;
import tn.esprit.rh.stock.repositories.ProduitRepository;
import tn.esprit.rh.stock.repositories.SecteurActiviteRepository;

import tn.esprit.rh.stock.services.StockServiceImpl;
@ExtendWith(MockitoExtension.class)
public class StockServuieImplMockTest {
    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    public StockServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveStock_ExistingStock() {
        Long stockId = 1L;
        Stock mockStock = mockStock.createMockStock();

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(mockStock));

        Stock result = stockService.retrieveStock(stockId);

        assertNotNull(result);
        assertEquals(stockId, result.getIdStock());
        assertEquals("Mock Stock", result.getLibelleStock());
        assertEquals(100, result.getQte());
        assertEquals(50, result.getQteMin());
    }

    @Test
    public void testRetrieveStock_NonExistingStock() {
        Long stockId = 999L;

        when(stockRepository.findById(stockId)).thenReturn(Optional.empty());

        Stock result = stockService.retrieveStock(stockId);

        assertNull(result);
    }

    @Test
    public void testRetrieveStatusStock_NoStockInRed() {
        when(stockRepository.retrieveStatusStock()).thenReturn(Collections.emptyList());

        String result = stockService.retrieveStatusStock();

        assertNotNull(result);
        assertEquals("", result);
    }

    @Test
    public void testRetrieveStatusStock_WithStockInRed() {
        Stock stockInRed = MockStock.createMockStock();
        stockInRed.setQte(30); // Below the minimum quantity

        when(stockRepository.retrieveStatusStock()).thenReturn(Collections.singletonList(stockInRed));

        String result = stockService.retrieveStatusStock();

        assertNotNull(result);
        assertTrue(result.contains("le stock Mock Stock a une quantité de 30"));
    }
}
