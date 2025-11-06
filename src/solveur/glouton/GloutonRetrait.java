package solveur.glouton;

import sacADos.Objet;
import sacADos.SacADos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GloutonRetrait {

    // Variante "à retrait" : on part de tous les objets puis on retire les moins intéressants
    public static List<Objet> solve(SacADos sac, Comparator<Objet> comp) {
        // copie pour ne pas modifier la liste originale
        List<Objet> solution = new ArrayList<>(sac.getObjets());

        // tant qu'on dépasse un budget, on enlève l'objet le moins bon selon 'comp'
        while (!respecteBudgets(solution, sac.getBudgets())) {
            Objet moinsBon = trouverMoinsInteressant(solution, comp);
            solution.remove(moinsBon);
        }
        return solution;
    }

    // retourne true si tous les budgets sont respectés
    private static boolean respecteBudgets(List<Objet> objets, int[] budgets) {
        int dim = budgets.length;
        int[] total = new int[dim];

        for (Objet o : objets) {
            int[] c = o.getCouts();
            for (int i = 0; i < dim; i++) {
                total[i] += c[i];
                if (total[i] > budgets[i]) { // petit early-exit
                    return false;
                }
            }
        }
        return true;
    }

    // renvoie l'objet "le moins intéressant" selon le comparateur (donc à retirer en priorité)
    private static Objet trouverMoinsInteressant(List<Objet> objets, Comparator<Objet> comp) {
        Objet moinsBon = objets.get(0);
        for (Objet o : objets) {
            // si o est pire que moinsBon (comp trie du plus intéressant au moins intéressant)
            if (comp.compare(o, moinsBon) > 0) {
                moinsBon = o;
            }
        }
        return moinsBon;
    }
}

