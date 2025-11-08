package solveur.glouton;

import sacADos.Objet;
import sacADos.SacADos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class GloutonAjoutSolver {

    
    public static List<Objet> solve(SacADos sac, Comparator<Objet> comp) {
        List<Objet> tries = new ArrayList<>(sac.getObjets());
        Collections.sort(tries, comp); 

        List<Objet> solution = new ArrayList<>();
        int[] budgets = sac.getBudgets();

        for (Objet o : tries) {
            if (peutAjouter(solution, o, budgets)) {
                solution.add(o);
            }
        }
        return solution;
    }

    
    private static boolean peutAjouter(List<Objet> solution, Objet o, int[] budgets) {
        int dim = budgets.length;
        int[] total = new int[dim];

        
        for (Objet x : solution) {
            int[] c = x.getCouts();
            for (int i = 0; i < dim; i++) {
                total[i] += c[i];
            }
        }

        
        int[] coutsO = o.getCouts();
        for (int i = 0; i < dim; i++) {
            if (total[i] + coutsO[i] > budgets[i]) {
                return false;
            }
        }
        return true;
    }
}

