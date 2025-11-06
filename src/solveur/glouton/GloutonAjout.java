package solveur.glouton;

import sacADos.Objet;
import sacADos.SacADos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class GloutonAjout {

    // Glouton "à ajout" :
    // 1) trier les objets du plus intéressant au moins intéressant (via comp)
    // 2) parcourir et ajouter si on reste sous les budgets
    public static List<Objet> solve(SacADos sac, Comparator<Objet> comp) {
        List<Objet> tries = new ArrayList<>(sac.getObjets());
        Collections.sort(tries, comp); // tri décroissant selon le comparateur

        List<Objet> solution = new ArrayList<>();
        int[] budgets = sac.getBudgets();

        for (Objet o : tries) {
            if (peutAjouter(solution, o, budgets)) {
                solution.add(o);
            }
        }
        return solution;
    }

    // true si ajouter o à solution ne dépasse aucun budget
    private static boolean peutAjouter(List<Objet> solution, Objet o, int[] budgets) {
        int dim = budgets.length;
        int[] total = new int[dim];

        // somme des coûts actuels
        for (Objet x : solution) {
            int[] c = x.getCouts();
            for (int i = 0; i < dim; i++) {
                total[i] += c[i];
            }
        }

        // ajout virtuel de o et vérification
        int[] coutsO = o.getCouts();
        for (int i = 0; i < dim; i++) {
            if (total[i] + coutsO[i] > budgets[i]) {
                return false;
            }
        }
        return true;
    }
}

