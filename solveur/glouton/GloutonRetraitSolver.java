package solveur.glouton;

import sacADos.Objet;
import sacADos.SacADos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GloutonRetraitSolver {

    
    public static List<Objet> solve(SacADos sac, Comparator<Objet> comp) {
        
        List<Objet> solution = new ArrayList<>(sac.getObjets());

        
        while (!respecteBudgets(solution, sac.getBudgets())) {
            Objet moinsBon = trouverMoinsInteressant(solution, comp);
            solution.remove(moinsBon);
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
            
            if (comp.compare(o, moinsBon) > 0) {
                moinsBon = o;
            }
        }
        return moinsBon;
    }
}

