package appli;

import equipe.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import projet.Projet;
import sacADos.*;
import solveur.glouton.*;

public class Main {

    // là y a tt ce qu'il nous faut pour gérer la ville
    private static EquipeMunicipale equipe;
    private static List<Projet> projets = new ArrayList<>();
    private static SacADos sacADosActuel = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SIMULATION MUNICIPALE ===");
        
        // On prépare le truc de base
        initialiserEquipe(); 

        boolean continuer = true;
        while (continuer) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Générer de nouveaux projets");
            System.out.println("2. Créer l'instance Sac à Dos (Conversion)");
            System.out.println("3. Résoudre (Lancer les algos)");
            System.out.println("0. Quitter");
            System.out.print("Ton choix : ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    genererProjetsInteractif();
                    break;
                case 2:
                    creerInstanceSacADos();
                    break;
                case 3:
                    resoudreInstance();
                    break;
                case 0:
                    System.out.println("Allez salut !");
                    continuer = false;
                    break;
                default:
                    System.out.println("Mauvais bouton chef.");
            }
        }
        scanner.close();
    }

    private static void initialiserEquipe() {
        // On recrute les gars sûrs
        Elu elu = new Elu("Dupont", "Marie", 45);
        equipe = new EquipeMunicipale(elu);
        
        equipe.getEvaluateurs().add(new Evaluateur("Martin", "Paul", 40, "economique"));
        equipe.getEvaluateurs().add(new Evaluateur("Durand", "Claire", 38, "social"));
        equipe.getEvaluateurs().add(new Evaluateur("Bernard", "Luc", 50, "environnemental"));
        
        equipe.getExperts().add(new Expert("Lemoine", "Alice", 35, "sport"));
        equipe.getExperts().add(new Expert("Royer", "Thomas", 32, "santé"));
        
        System.out.println("Equipe prête.");
    }

    private static void genererProjetsInteractif() {
        System.out.print("Combien de projets on lance ? : ");
        int nb = lireEntier();
        
        // On vide la liste d'avant pour repartir propre
        projets.clear(); 
        for (int i = 1; i <= nb; i++) {
            Projet p = Projet.genererAleatoirement(i);
            projets.add(p);
            System.out.println(" - " + p.getTitre() + " (Util: " + p.getUtilite() + ")");
        }
    }

    private static void creerInstanceSacADos() {
        if (projets.isEmpty()) {
            System.out.println("ERREUR : Y'a pas de projets, fais l'option 1 d'abord.");
            return;
        }

        // tout ce qui est argent : 
        System.out.println("Budget ECONOMIQUE ? :");
        int b1 = lireEntier();
        System.out.println("Budget SOCIAL ? :");
        int b2 = lireEntier();
        System.out.println("Budget ENVIRONNEMENTAL ? :");
        int b3 = lireEntier();
        
        int[] budgets = {b1, b2, b3};
        
        // là on résout avec le fichier VersSacADos.java
        sacADosActuel = VersSacADos.convertir(projets, budgets);
        System.out.println(">> C'est bon, le sac est prêt !");
    }

    private static void resoudreInstance() {
        if (sacADosActuel == null) {
            System.out.println("ERREUR : Fais l'option 2 avant, sinon on résout rien du tout.");
            return;
        }

        System.out.println("Quel algo on utilise ?");
        System.out.println("1. Glouton (Ajout - Sigma)");
        System.out.println("2. Glouton (Ajout - Max)");
        
        int choix = lireEntier();
        List<Objet> solution = null;

        if (choix == 1) {
            solution = GloutonAjoutSolver.solve(sacADosActuel, Comparateurs.fSigma());
        } else if (choix == 2) {
            solution = GloutonAjoutSolver.solve(sacADosActuel, Comparateurs.fMax());
        } else {
            System.out.println("Connais pas cet algo.");
            return;
        }

        System.out.println(">> On garde " + solution.size() + " projets.");
        int utiliteTotale = 0;
        for(Objet o : solution) utiliteTotale += o.getUtilite();
        System.out.println(">> Utilité totale : " + utiliteTotale);
    }

    // Méthode pour pas que ça crash si on tape n'importe quoi
    private static int lireEntier() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("C'est pas un nombre ça, réessaie : ");
            }
        }
    }
}