package solveur.glouton;

import sacADos.Objet;
import sacADos.SacADos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GloutonRetraitSolver {

    public static List<Objet> solve(SacADos sac, Comparator<Objet> comp) {
        
       
        // on part avce tt les objets 
        List<Objet> solution = new ArrayList<>(sac.getObjets());

        // on enlève les objets les - bons tant que ça déborde du sac
        while (!respecteBudgets(solution, sac.getBudgets())) {
            Objet moinsBon = trouverMoinsInteressant(solution, comp);
            solution.remove(moinsBon);
        }

        
        // la je rajoute une phase d'ajout, mtn que le sac ferme on regarde si on peut 
        //rajouter des objets qu'on a pas gardé 
        List<Objet> objetsDisponibles = new ArrayList<>(sac.getObjets());
        objetsDisponibles.removeAll(solution);

       
        objetsDisponibles.sort(comp);

        for (Objet o : objetsDisponibles) {
            if (peutAjouter(solution, o, sac.getBudgets())) {
                solution.add(o);
            }
        }

        return solution;
    }

   

    private static boolean respecteBudgets(List<Objet> objets, int[] budgets) {
        int dim = budgets.length;
        int[] total = new int[dim];

        for (Objet o : objets) {
            int[] c = o.getCouts();
            for (int i = 0; i < dim; i++) {
                total[i] += c[i];
                if (total[i] > budgets[i]) { 
                    return false;
                }
            }
        }
        return true;
    }

    private static Objet trouverMoinsInteressant(List<Objet> objets, Comparator<Objet> comp) {
        
        Objet moinsBon = objets.get(0);
        for (Objet o : objets) {
            
            if (comp.compare(o, moinsBon) < 0) { 
                moinsBon = o;
            }
        }
        return moinsBon;
    }

    //j'ai copié la méthode depuis gloutonajout parce que j'en ai besoin ici mais thomas verifie stp 
    
    private static boolean peutAjouter(List<Objet> solution, Objet o, int[] budgets) {
        int dim = budgets.length;
        int[] total = new int[dim];
        
        // poids actuel 
        for (Objet x : solution) {
            int[] c = x.getCouts();
            for (int i = 0; i < dim; i++) {
                total[i] += c[i];
            }
        }
        
        // vérifie si ça passe avec le nouvel objet
        int[] coutsO = o.getCouts();
        for (int i = 0; i < dim; i++) {
            if (total[i] + coutsO[i] > budgets[i]) {
                return false;
            }
        }
        return true;
    }
}