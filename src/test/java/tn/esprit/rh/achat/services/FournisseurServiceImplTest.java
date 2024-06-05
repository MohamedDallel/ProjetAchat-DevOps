package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FournisseurServiceImplTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private DetailFournisseurRepository detailFournisseurRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private SecteurActiviteRepository secteurActiviteRepository;

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    @BeforeEach
    public void setUp() {
    	
    }

    @Test
    public void testRetrieveAllFournisseurs() {
        Fournisseur fournisseur1 = new Fournisseur();
        Fournisseur fournisseur2 = new Fournisseur();
        List<Fournisseur> expectedFournisseurs = Arrays.asList(fournisseur1, fournisseur2);
        when(fournisseurRepository.findAll()).thenReturn(expectedFournisseurs);

        List<Fournisseur> actualFournisseurs = fournisseurService.retrieveAllFournisseurs();

        assertEquals(expectedFournisseurs, actualFournisseurs);
        verify(fournisseurRepository, times(1)).findAll();
    }

    @Test
    public void testAddFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        detailFournisseur.setDateDebutCollaboration(new Date());
        fournisseur.setDetailFournisseur(detailFournisseur);
        when(fournisseurRepository.save(fournisseur)).thenReturn(fournisseur);

        Fournisseur addedFournisseur = fournisseurService.addFournisseur(fournisseur);

        assertEquals(fournisseur, addedFournisseur);
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }

    @Test
    public void testUpdateFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        fournisseur.setDetailFournisseur(detailFournisseur);
        when(detailFournisseurRepository.save(detailFournisseur)).thenReturn(detailFournisseur);
        when(fournisseurRepository.save(fournisseur)).thenReturn(fournisseur);

        Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(fournisseur);

        assertEquals(fournisseur, updatedFournisseur);
        verify(detailFournisseurRepository, times(1)).save(detailFournisseur);
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }

    @Test
    public void testDeleteFournisseur() {
        Long fournisseurId = 1L;

        fournisseurService.deleteFournisseur(fournisseurId);

        verify(fournisseurRepository, times(1)).deleteById(fournisseurId);
    }

    @Test
    public void testRetrieveFournisseur() {
        // Arrange
        Long fournisseurId = 1L;
        Fournisseur fournisseur = new Fournisseur();
        when(fournisseurRepository.findById(fournisseurId)).thenReturn(Optional.of(fournisseur));

        Fournisseur retrievedFournisseur = fournisseurService.retrieveFournisseur(fournisseurId);
        assertEquals(fournisseur, retrievedFournisseur);
        verify(fournisseurRepository, times(1)).findById(fournisseurId);
    }

    @Test
    public void testAssignSecteurActiviteToFournisseur() {
        // Arrange
        Long idSecteurActivite = 1L;
        Long idFournisseur = 1L;
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setSecteurActivites(new HashSet<>());
        SecteurActivite secteurActivite = new SecteurActivite();
        when(fournisseurRepository.findById(idFournisseur)).thenReturn(Optional.of(fournisseur));
        when(secteurActiviteRepository.findById(idSecteurActivite)).thenReturn(Optional.of(secteurActivite));

        // Act
        fournisseurService.assignSecteurActiviteToFournisseur(idSecteurActivite, idFournisseur);

        // Assert
        assertTrue(fournisseur.getSecteurActivites().contains(secteurActivite));
        verify(fournisseurRepository, times(1)).findById(idFournisseur);
        verify(secteurActiviteRepository, times(1)).findById(idSecteurActivite);
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }
}
