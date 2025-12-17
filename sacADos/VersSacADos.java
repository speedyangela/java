

//Vous fournirez une classe VersSacADos avec des méthodes permettant de générer un objet de la
//classe SacADos à partir d’une liste de projets (c.f. section sur notre équipe municipale) et des budgets
//alloués par Dauphine City. Nous proposons deux options différentes :
//CAS1— Les budgets concernent les 3 différents types de coûts (économique, social, et environnemental).
//CAS2— Les budgets concernent les 5 différents secteurs et seul le coût économique est considéré. Dit
//autrement, il y a un budget de B1 euros pour le sport, de B2 euros pour la santé,. . .



package sacADos;
import projet.Projet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VersSacADos {

    //CAS 1 --> budget sur les couts 
    public static SacADos convertir(List<Projet> listeProjets, int[] budgets) {
        List<Objet> listeObjets = new ArrayList<>();
        for (Projet p : listeProjets) {
            // 1 par défaut
            int utilite = (p.getUtilite() == 0) ? 1 : p.getUtilite();
            
            int[] couts = new int[] {
                p.getCoutEconomique(),
                p.getCoutSocial(),
                p.getCoutEnvironnemental()
            };
            Objet obj = new Objet(utilite, couts);
            listeObjets.add(obj);
        }
        return new SacADos(3, budgets, listeObjets);
    }

    //CAS2 -> budget par secteur  

    public static SacADos convertirSecteurs(List<Projet> listeProjets, int[] budgets) {
        
        // 0: Sport, 1: Santé, 2: Education, 3: Culture, 4: Attractivité (indice)
        List<String> ordreSecteurs = Arrays.asList(
            "sport", "santé", "éducation", "culture", "attractivité économique"
        );

        List<Objet> listeObjets = new ArrayList<>();

        for (Projet p : listeProjets) {
            int utilite = (p.getUtilite() == 0) ? 1 : p.getUtilite();
            
            
            int[] couts = new int[5];
            
            String sec = p.getSecteur().toLowerCase();
            int index = ordreSecteurs.indexOf(sec);

            if (index != -1) {
    
                couts[index] = p.getCoutEconomique();
            } else {
                //blc
            }

            listeObjets.add(new Objet(utilite, couts));
        }

        return new SacADos(5, budgets, listeObjets);
    }
    //charger depyis fichier 
    public static SacADos lireFichier(String chemin) {
        try {
            java.util.Scanner sc = new java.util.Scanner(new java.io.File(chemin));

    
            int n = sc.nextInt();
            int k = sc.nextInt();
            sc.nextLine();

            
            int[] utilites = new int[n];
            for (int i = 0; i < n; i++) {
                utilites[i] = sc.nextInt();
            }

            
            int[][] coutsMatrice = new int[k][n];
            for (int l = 0; l < k; l++) {
                for (int i = 0; i < n; i++) {
                    coutsMatrice[l][i] = sc.nextInt();
                }
            }

            
            int[] budgets = new int[k];
            for (int l = 0; l < k; l++) {
                budgets[l] = sc.nextInt();
            }
            
            sc.close();

            
            List<Objet> objets = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                
                int[] sesCouts = new int[k];
                for (int l = 0; l < k; l++) {
                    sesCouts[l] = coutsMatrice[l][i];
                }
                objets.add(new Objet(utilites[i], sesCouts));
            }

            System.out.println(">> Fichier chargé ! (" + n + " objets, " + k + " dimensions)");
            return new SacADos(k, budgets, objets);

        } catch (java.io.FileNotFoundException e) {
            System.out.println("ERREUR : Fichier introuvable (" + chemin + ")");
            return null;
        } catch (Exception e) {
            System.out.println("ERREUR lors de la lecture : " + e.getMessage());
            return null;
        }
    }
}