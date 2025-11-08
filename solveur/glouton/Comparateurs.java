package solveur.glouton;

import sacADos.Objet;
import java.util.Comparator;


public class Comparateurs {

    
    public static Comparator<Objet> fSigma() {
        return new Comparator<Objet>() {
            @Override
            public int compare(Objet o1, Objet o2) {
                double ratio1 = (double) o1.getUtilite() / somme(o1.getCouts());
                double ratio2 = (double) o2.getUtilite() / somme(o2.getCouts());
                // tri décroissant (le plus "intéressant" d'abord)
                return Double.compare(ratio2, ratio1);
            }
        };
    }

    
    public static Comparator<Objet> fMax() {
        return new Comparator<Objet>() {
            @Override
            public int compare(Objet o1, Objet o2) {
                double ratio1 = (double) o1.getUtilite() / max(o1.getCouts());
                double ratio2 = (double) o2.getUtilite() / max(o2.getCouts());
                return Double.compare(ratio2, ratio1);
            }
        };
    }

    
    private static int somme(int[] tab) {
        int s = 0;
        for (int v : tab) s += v;
        return s;
    }

    private static int max(int[] tab) {
        int m = tab[0];
        for (int v : tab)
            if (v > m) m = v;
        return m;
    }
}
