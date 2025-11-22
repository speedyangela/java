package appli;

import equipe.*;
import java.util.ArrayList;
import java.util.List;
import sacADos.*;
import solveur.glouton.*;

public class Main {

    public static void main(String[] args) {

        Elu elu = new Elu("Dupont", "Marie", 45);
        Evaluateur evalEco = new Evaluateur("Martin", "Paul", 40, "economique");
        Evaluateur evalSoc = new Evaluateur("Durand", "Claire", 38, "social");
        Evaluateur evalEnv = new Evaluateur("Bernard", "Luc", 50, "environnemental");
        Expert expSport = new Expert("Lemoine", "Alice", 35, "sport");
        Expert expSante = new Expert("Royer", "Thomas", 32, "santé");
        EquipeMunicipale equipe = new EquipeMunicipale(elu);
        equipe.getEvaluateurs().add(evalEco);
        equipe.getEvaluateurs().add(evalSoc);
        equipe.getEvaluateurs().add(evalEnv);
        equipe.getExperts().add(expSport);
        equipe.getExperts().add(expSante);
        /* Là j'ai mis des print mais c pas obligé jpense, c comme vous voulez */
        System.out.println("Élu : " + elu.getNom() + " " + elu.getPrenom());
        System.out.println("Nombre d'évaluateurs : " + equipe.getEvaluateurs().size());
        System.out.println("Nombre d'experts : " + equipe.getExperts().size());
        System.out.println();

        int[] budgets = {100, 80, 60};
        List<Objet> objets = new ArrayList<Objet>();
        objets.add(new Objet(60, new int[]{30, 20, 10}));
        objets.add(new Objet(40, new int[]{10, 30, 20}));
        objets.add(new Objet(30, new int[]{20, 10, 30}));
        objets.add(new Objet(50, new int[]{40, 20, 10}));
        objets.add(new Objet(35, new int[]{10, 15, 25}));

        SacADos sac = new SacADos(3, budgets, objets);
        System.out.println("Instance de sac à dos créée.");
        System.out.println("Budgets : [" + budgets[0] + ", " + budgets[1] + ", " + budgets[2] + "]");
        System.out.println("Nombre d'objets disponibles : " + sac.getObjets().size());
        System.out.println();

        List<Objet> solution = GloutonAjoutSolver.solve(sac, Comparateurs.fSigma());
        int utiliteTotale = 0;
        int[] coutsTotaux = new int[budgets.length];

        System.out.println("Solution gloutonne (objets sélectionnés) :");

        for (Objet o : solution) {
            utiliteTotale += o.getUtilite();
            int[] c = o.getCouts();
            for (int i = 0; i < budgets.length; i++) {
                coutsTotaux[i] += c[i];
            }
            System.out.println(" - Objet utilité = " + o.getUtilite() + " | coûts = [" + c[0] + ", " + c[1] + ", " + c[2] + "]");
        }

        System.out.println();
        System.out.println("Utilité totale de la solution : " + utiliteTotale);
        System.out.println("Couts totaux : [" + coutsTotaux[0] + ", " + coutsTotaux[1] + ", " + coutsTotaux[2] + "]");
        System.out.println("Budgets    : [" + budgets[0] + ", " + budgets[1] + ", " + budgets[2] + "]");

        boolean respecte = true;
        for (int i = 0; i < budgets.length; i++) {
            if (coutsTotaux[i] > budgets[i]) {
                respecte = false;
            }
        }
        System.out.println();
        if (respecte) {
            System.out.println("Les contraintes de budget sont respectées.");
        } else {
            System.out.println("Attention : les contraintes de budget ne sont pas respectées.");
        }
    }
}
