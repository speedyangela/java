package solveur.hillclimbing;
 
import java.util.ArrayList;
import java.util.List;
import sacADos.Objet;
import sacADos.SacADos;

public class HillClimbing {

    
    public static List<Objet> resoudre(SacADos sac, List<Objet> depart) {
        List<Objet> actuelle = new ArrayList<>(depart);
        int meilleureUtilite = calculUtilite(actuelle);
        boolean change = true;

        while (change) {
            change = false;
            List<Objet> meilleurVoisin = null;
            int utiliteVoisin = meilleureUtilite;

            // on teste tous les voisins en retirant un objet et en en ajoutant un autre
            for (int i = 0; i < actuelle.size(); i++) {
                Objet oRetire = actuelle.get(i);

                for (Objet oAjoute : sac.getObjets()) {
                    if (contient(actuelle, oAjoute)) continue;

                    List<Objet> test = new ArrayList<>(actuelle);
                    test.remove(oRetire);
                    test.add(oAjoute);

                    if (respecteBudgets(test, sac.getBudgets())) {
                        int utiliteTest = calculUtilite(test);
                        if (utiliteTest > utiliteVoisin) {
                            utiliteVoisin = utiliteTest;
                            meilleurVoisin = test;
                        }
                    }
                }
            }

            
            for (Objet o : sac.getObjets()) {
                if (contient(actuelle, o)) continue;
                List<Objet> test = new ArrayList<>(actuelle);
                test.add(o);

                if (respecteBudgets(test, sac.getBudgets())) {
                    int utiliteTest = calculUtilite(test);
                    if (utiliteTest > utiliteVoisin) {
                        utiliteVoisin = utiliteTest;
                        meilleurVoisin = test;
                    }
                }
            }

            
            if (meilleurVoisin != null) {
                actuelle = meilleurVoisin;
                meilleureUtilite = utiliteVoisin;
                change = true;
            }
        }

        return actuelle;
    }

    
    private static int calculUtilite(List<Objet> objets) {
        int total = 0;
        for (Objet o : objets) total += o.getUtilite();
        return total;
    }

    
    private static boolean respecteBudgets(List<Objet> objets, int[] budgets) {
        int[] total = new int[budgets.length];
        for (Objet o : objets) {
            int[] c = o.getCouts();
            for (int i = 0; i < budgets.length; i++) {
                total[i] += c[i];
                if (total[i] > budgets[i]) return false;
            }
        }
        return true;
    }


    private static boolean contient(List<Objet> liste, Objet o) {
        for (Objet x : liste) if (x == o) return true;
        return false;
    }
}

