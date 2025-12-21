package appli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import sacADos.Objet;
import sacADos.SacADos;
import equipe.Expert;
import projet.Projet;
import solveur.glouton.GloutonAjoutSolver;
import solveur.glouton.Comparateurs;

public class TestsProjet {

    //TEST 1: vérifier qu'un Objet stocke bien ses infos
    @Test
    public void testObjet() {
        int[] couts = {10, 20, 30};
        Objet o = new Objet(100, couts);
        
        assertEquals(100, o.getUtilite());
        assertEquals(20, o.getCouts()[1]); // Vérifie le 2ème coût
    }

    //TEST 2: vérifier qu'un expert propose bien un projet
    @Test
    public void testExpertProposeProjet() {
        Expert expert = new Expert("Curie", "Marie", 50, "Santé");
        Projet p = expert.proposerProjet(1);
        
        assertNotNull(p); // Le projet ne doit pas être null
        assertTrue(p.getTitre().contains("Santé")); // Le titre doit contenir le secteur
        assertEquals("Santé", p.getSecteur());
    }

    //TEST 3: vérifier la conversion Projet -> Objet (VersSacADos)
    @Test
    public void testConversionSacADos() {
        //on crée un projet manuellement
        Projet p = new Projet("Test", "Desc", "Sport", 50, 10, 20, 30);
        List<Projet> liste = new ArrayList<>();
        liste.add(p);
        
        int[] budgets = {100, 100, 100};
        
        //on convertit
        SacADos sac = sacADos.VersSacADos.convertir(liste, budgets);
        
        assertEquals(3, sac.getDimension()); // 3 dimensions (Eco, Social, Env)
        assertEquals(1, sac.getObjets().size()); // 1 seul objet
        assertEquals(50, sac.getObjets().get(0).getUtilite()); // Utilité conservée
    }

    //TEST 4: vérifier que le Glouton ne dépasse pas le budget
    @Test
    public void testGloutonRespecteBudget() {
        //Objet 1: Coût 10, Utilité 100
        Objet o1 = new Objet(100, new int[]{10});
        //Objet 2: Coût 10, Utilité 50
        Objet o2 = new Objet(50, new int[]{10});
        
        List<Objet> objets = new ArrayList<>();
        objets.add(o1);
        objets.add(o2);
        
        //Budget de 15 (on ne peut prendre qu'un seul objet de coût 10)
        int[] budgets = {15};
        SacADos sac = new SacADos(1, budgets, objets);
        
        List<Objet> solution = GloutonAjoutSolver.solve(sac, Comparateurs.fSigma());
        
        //on doit avoir pris seulement le meilleur (o1)
        assertEquals(1, solution.size());
        assertEquals(100, solution.get(0).getUtilite());
    }

    //TEST 5: vérifier qu'un projet vide a bien une utilité par défaut
    @Test
    public void testProjetUtiliteParDefaut() {
        Projet p = new Projet("Vide", "...", "Sport", 0, 5, 5, 5);
        List<Projet> liste = new ArrayList<>();
        liste.add(p);
        
        SacADos sac = sacADos.VersSacADos.convertir(liste, new int[]{100, 100, 100});
        
        //si utilité = 0,le code met 1 par défaut
        assertEquals(1, sac.getObjets().get(0).getUtilite());
    }

    //TEST 6: vérifier que Hill Climbing renvoie une solution valide
    @Test
    public void testHillClimbing() {
        //création sac simple
        Objet o1 = new Objet(10, new int[]{5});
        Objet o2 = new Objet(20, new int[]{5});
        List<Objet> objets = new ArrayList<>();
        objets.add(o1);
        objets.add(o2);
        
        SacADos sac = new SacADos(1, new int[]{10}, objets);
        
        //solution de départ (vide)
        List<Objet> depart = new ArrayList<>();
        
        //lance algo
        List<Objet> solution = solveur.hillclimbing.HillClimbing.resoudre(sac, depart);
        
        assertNotNull(solution);
        // avec un budget de 10, il devrait pouvoir prendre les deux (5+5=10)
        // ou au moins trouver une solution non vide
        assertFalse(solution.isEmpty()); 
    }

    //TEST 7 : vérifier que Glouton RETRAIT retire le surplus
    @Test
    public void testGloutonRetrait() {
        //3 objets coûtant 10 chacun (Total 30)
        Objet o1 = new Objet(10, new int[]{10});
        Objet o2 = new Objet(10, new int[]{10});
        Objet o3 = new Objet(10, new int[]{10});
        
        List<Objet> tous = new ArrayList<>();
        tous.add(o1); tous.add(o2); tous.add(o3);
        
        //budget de 15 seulement (on ne peut en garder qu'un seul)
        SacADos sac = new SacADos(1, new int[]{15}, tous);
        
        List<Objet> solution = solveur.glouton.GloutonRetraitSolver.solve(sac, Comparateurs.fSigma());
        
        //il doit yen rester 1 seul (car 2 objets = coût 20 > 15)
        assertEquals(1, solution.size());
    
    }
    //TEST 8: vérifier qu'un evaluateur modifie le bon coût
    @Test
    public void testEvaluateurSpecialise() {
        Projet p = new Projet("Parc", "...", "Sport", 0, 0, 0, 0);
        
        // evaluateur spécialisé ds social
        equipe.Evaluateur evalSocial = new equipe.Evaluateur("Paul", "M", 30, "social");
        evalSocial.evaluer(p);
        //le coût social doit avoir changé (plus 0)
        assertTrue(p.getCoutSocial() > 0);
        //les autres coûts ne doivent PAS avoir bougé
        assertEquals(0, p.getCoutEconomique());
        assertEquals(0, p.getCoutEnvironnemental());
    }
    //TEST 9: vérifier l'ordre des objets (Comparateur)
    @Test
    public void testComparateurRatio() {
        //o1: Ratio 100/10 = 10
        Objet o1 = new Objet(100, new int[]{10});
        //o2: Ratio 50/2 = 25 (bcp plus intéressant)
        Objet o2 = new Objet(50, new int[]{2});
        
        java.util.Comparator<Objet> comp = Comparateurs.fSigma();
        
        //compare(o1, o2) doit renvoyer un nombre positif car o2 est "mieux" que o1
        //(rappel: tri est décroissant, le + grand ratio d'abord)
        int resultat = comp.compare(o1, o2);
        
        //si le résultat est > 0 ça veut dire que o2 passe avant o1 (ou inversement)
        //ds Comparateurs.java: return Double.compare(ratio2, ratio1);
        // Donc si ratio2 (25) > ratio1 (10), ça renvoie > 0.
        assertTrue(resultat > 0);
    }
}