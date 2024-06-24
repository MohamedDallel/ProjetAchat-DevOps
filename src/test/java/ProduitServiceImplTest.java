import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.ProduitServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProduitServiceImplTest {
    @InjectMocks
    private ProduitServiceImpl produitService;

    @Mock
    private ProduitRepository produitRepository;
    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRetrieveAllProduits() {
        Produit produit1 = new Produit();
        Produit produit2 = new Produit();
        Mockito.when(produitRepository.findAll()).thenReturn(Arrays.asList(produit1, produit2));

        List<Produit> produits = produitService.retrieveAllProduits();

        Assertions.assertEquals(2, produits.size());
    }

    @Test
    void testAddProduit() {
        Produit p = new Produit();
        Mockito.when(produitRepository.save(p)).thenReturn(p);

        Produit result = produitService.addProduit(p);

        Mockito.verify(produitRepository, Mockito.times(1)).save(p);
        Assertions.assertEquals(p, result);
    }

    @Test
    void testDeleteProduit() {
        Long produitId = 1L;

        produitService.deleteProduit(produitId);

        Mockito.verify(produitRepository, Mockito.times(1)).deleteById(produitId);
    }

    @Test
    void testUpdateProduit() {
        Produit p = new Produit();
        Mockito.when(produitRepository.save(p)).thenReturn(p);

        Produit result = produitService.updateProduit(p);

        // Then
        Mockito.verify(produitRepository, Mockito.times(1)).save(p);
        Assertions.assertEquals(p, result);
    }

    @Test
    void testRetrieveProduit() {
        Long produitId = 1L;
        Produit expectedProduit = new Produit();
        Mockito.when(produitRepository.findById(produitId)).thenReturn(Optional.of(expectedProduit));

        Produit result = produitService.retrieveProduit(produitId);

        Assertions.assertEquals(expectedProduit, result);
    }

    @Test
    void testAssignProduitToStock() {

        Long idProduit = 1L;
        Long idStock = 1L;
        Produit produit = new Produit();
        Stock stock = new Stock();
        Mockito.when(produitRepository.findById(idProduit)).thenReturn(Optional.of(produit));
        Mockito.when(stockRepository.findById(idStock)).thenReturn(Optional.of(stock));

        produitService.assignProduitToStock(idProduit, idStock);

        Assertions.assertEquals(stock, produit.getStock());
        Mockito.verify(produitRepository, Mockito.times(1)).save(produit);
    }


}