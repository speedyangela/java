package appli;

import equipe.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import projet.Projet;
import sacADos.*;
import solveur.glouton.*;
import solveur.hillclimbing.*; 

public class Main {

    // là y a tt ce qu'il nous faut pour gérer la ville
    private static EquipeMunicipale equipe;
    private static List<Projet> projets = new ArrayList<>();
    private static SacADos sacADosActuel = null;
    
    // on stocke la solution ici pour pouvoir la réutiliser genre hill climbing 
    private static List<Objet> solutionActuelle = new ArrayList<>();
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SIMULATION MUNICIPALE ===");
        
        // On prépare le truc de base (recrutement de l'équipe)
        initialiserEquipe(); 

        boolean continuer = true;
        while (continuer) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Générer de nouveaux projets (Simulation)");
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
        
        //pr faire bien avec les listes de secteurs 
        List<String> secteursAlice = new ArrayList<>();
        secteursAlice.add("sport");
        secteursAlice.add("santé");
        equipe.getExperts().add(new Expert("Lemoine", "Alice", 35, secteursAlice));
        
        //il a qu'un secteur
        equipe.getExperts().add(new Expert("Royer", "Thomas", 32, "éducation"));
        
        System.out.println("Equipe prête.");
    }

    private static void genererProjetsInteractif() {
        System.out.print("Combien de projets on lance ? : ");
        int nb = lireEntier();
        
        // On vide la liste d'avant pour repartir propre
        projets.clear(); 
        java.util.Random rand = new java.util.Random();

        // simu commence !!!!
        for (int i = 1; i <= nb; i++) {
            
            // expert propose projet 
            Expert expertChoisi = equipe.getExperts().get(rand.nextInt(equipe.getExperts().size()));
            Projet p = expertChoisi.proposerProjet(i);
            
            // les evaluateurs donnent leur avis 
            for (Evaluateur eval : equipe.getEvaluateurs()) {
                eval.evaluer(p);
            }
            
            // élu choisi 
            equipe.getElu().estimerBenefice(p);
            
            // ajout le projet a la liste 
            projets.add(p);
            System.out.println(" - " + p.getTitre() + " (Util: " + p.getUtilite() + ") [Créé par " + expertChoisi.getNom() + "]");
        }
    }

    private static void creerInstanceSacADos() {
        System.out.println("Comment créer le Sac à Dos ?");
        System.out.println("1. Depuis les Projets (Simulation - Impacts)");
        System.out.println("2. Depuis les Projets (Simulation - Secteurs)");
        System.out.println("3. Depuis un FICHIER DE TEST (Benchmark)"); // NOUVEAU
        
        int choix = lireEntier();

        if (choix == 3) {
            System.out.print("Nom du fichier (ex: test.txt) : ");
            String chemin = scanner.nextLine();
            //On essaie de charger
            SacADos s = VersSacADos.lireFichier(chemin);
            if (s != null) {
                sacADosActuel = s;
                // n vide la liste de projets car on est passé en mode "Fichier pur"
                projets.clear(); 
            }
            return;
        }

        // si on n'est pas en mode fichier il faut des projets 
        if (projets.isEmpty()) {
            System.out.println("ERREUR : Y'a pas de projets générés ! (Fais l'option 1 du menu principal d'abord)");
            return;
        }

        if (choix == 1) {
            System.out.println("Budget ECONOMIQUE ? :");
            int b1 = lireEntier();
            System.out.println("Budget SOCIAL ? :");
            int b2 = lireEntier();
            System.out.println("Budget ENVIRONNEMENTAL ? :");
            int b3 = lireEntier();
            sacADosActuel = VersSacADos.convertir(projets, new int[]{b1, b2, b3});
            
        } else if (choix == 2) {
            System.out.println("Il faut définir 5 budgets.");
            int[] budgets = new int[5];
            String[] noms = {"Sport", "Santé", "Education", "Culture", "Economie"};
            for(int i=0; i<5; i++) {
                System.out.println("Budget pour " + noms[i] + " ? :");
                budgets[i] = lireEntier();
            }
            sacADosActuel = VersSacADos.convertirSecteurs(projets, budgets);
            
        } else {
            System.out.println("Choix invalide.");
        }

        if (sacADosActuel != null) {
            System.out.println(">> Sac prêt ! (Dim: " + sacADosActuel.getDimension() + ")");
        }
    }

    private static void resoudreInstance() {
        if (sacADosActuel == null) {
            System.out.println("ERREUR : Fais l'option 2 avant, sinon on résout rien du tout.");
            return;
        }

        System.out.println("\n--- MENU RESOLUTION ---");
        System.out.println("1. Glouton (AJOUT - Sigma) -> Remplir le sac");
        System.out.println("2. Glouton (AJOUT - Max)   -> Remplir le sac");
        System.out.println("3. Glouton (RETRAIT - Sigma) -> Vider le surplus puis remplir");
        System.out.println("4. Glouton (RETRAIT - Max)   -> Vider le surplus puis remplir");
        System.out.println("5. Hill Climbing -> Optimiser la solution actuelle");
        System.out.print("Ton choix : ");
        
        int choix = lireEntier();

        // hillcling a beosin solution depart
        if (choix == 5) {
            if (solutionActuelle.isEmpty()) {
                System.out.println("ERREUR lance d'abord un glouton");
                return;
            }
            System.out.println(">> lancement Hill Climbing .");
            solutionActuelle = HillClimbing.resoudre(sacADosActuel, solutionActuelle);
            
        } else {
            // pr les gloutons on écrase l'ancienne solution
            if (choix == 1) {
                solutionActuelle = GloutonAjoutSolver.solve(sacADosActuel, Comparateurs.fSigma());
            } else if (choix == 2) {
                solutionActuelle = GloutonAjoutSolver.solve(sacADosActuel, Comparateurs.fMax());
            } else if (choix == 3) {
                solutionActuelle = GloutonRetraitSolver.solve(sacADosActuel, Comparateurs.fSigma());
            } else if (choix == 4) {
                solutionActuelle = GloutonRetraitSolver.solve(sacADosActuel, Comparateurs.fMax());
            } else {
                System.out.println("Connais pas cet algo.");
                return;
            }
        }

        System.out.println("\n>> Résultat final : On garde " + solutionActuelle.size() + " projets.");
        int utiliteTotale = 0;
        for(Objet o : solutionActuelle) utiliteTotale += o.getUtilite();
        System.out.println(">> Utilité totale : " + utiliteTotale);
    }

    // Méthode pour pas que ça crash si on tape nptkoi
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