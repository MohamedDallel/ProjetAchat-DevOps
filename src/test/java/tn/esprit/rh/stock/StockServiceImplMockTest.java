package tn.esprit.rh.stock;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import tn.esprit.rh.stock.entities.Stock;
import tn.esprit.rh.stock.repositories.StockRepository;
import tn.esprit.rh.stock.services.StockServiceImpl;

public class StockServiceImplMockTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    public StockServiceImplMockTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllStocks() {
        Stock stock1 = new Stock(/* initialize with necessary fields */);
        Stock stock2 = new Stock(/* initialize with necessary fields */);
        List<Stock> expectedStocks = Arrays.asList(stock1, stock2);
        when(stockRepository.findAll()).thenReturn(expectedStocks);

        List<Stock> actualStocks = stockService.retrieveAllStocks();

        assertEquals(expectedStocks, actualStocks);
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    public void testAddStock() {
        Stock stock = new Stock(/* initialize with necessary fields */);
        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.addStock(stock);

        assertNotNull(result);
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void testDeleteStock() {
        Long stockId = 1L;
        doNothing().when(stockRepository).deleteById(stockId);

        stockService.deleteStock(stockId);

        verify(stockRepository, times(1)).deleteById(stockId);
    }

    @Test
    public void testUpdateStock() {
        Stock stock = new Stock(/* initialize with necessary fields */);
        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.updateStock(stock);

        assertNotNull(result);
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void testRetrieveStock() {
        Long stockId = 1L;
        Stock expectedStock = new Stock(/* initialize with necessary fields */);
        when(stockRepository.findById(stockId)).thenReturn(java.util.Optional.ofNullable(expectedStock));

        Stock result = stockService.retrieveStock(stockId);

        assertNotNull(result);
        assertEquals(expectedStock, result);
        verify(stockRepository, times(1)).findById(stockId);
    }

    @Test
    public void testRetrieveStatusStock() {
        Stock stockInRed = new Stock(/* initialize with necessary fields */);
        stockInRed.setQte(30); // Below the minimum quantity
        when(stockRepository.retrieveStatusStock()).thenReturn(Collections.singletonList(stockInRed));

        String result = stockService.retrieveStatusStock();

        assertNotNull(result);
        assertTrue(result.contains("le stock Mock Stock a une quantité de 30"));
        verify(stockRepository, times(1)).retrieveStatusStock();
    }
}
