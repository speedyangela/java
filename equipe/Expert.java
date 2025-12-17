package equipe;

import java.util.ArrayList;
import java.util.List;


public class Expert extends Personne {

   
    // j'ai rajouté liste parce qu'un expert peut avoir plusieurs secteurs (c dit dans le sujet)
    private List<String> secteurs;

    
   
    //si on a deja une liste préparé pour un expert 
    public Expert(String nom, String prenom, int age, List<String> secteurs) {
        super(nom, prenom, age); // Envoie l'identité à la classe mère Personne pour qu'elle gère le stockage.
        this.secteurs = secteurs; // On stocke la liste qu'on nous donne.
    }
    
   
    //a utiliser si on veut creer un expert avec 1 seul secteur vite fait
    // comme ça on créer pas une liste dans le main 
    public Expert(String nom, String prenom, int age, String secteurUnique) {
        super(nom, prenom, age); 
        
        
        this.secteurs = new ArrayList<>(); 
        this.secteurs.add(secteurUnique);  
    }

    public List<String> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<String> secteurs) {
        this.secteurs = secteurs;
    }
    public projet.Projet proposerProjet(int numero) {
        java.util.Random rand = new java.util.Random();
        
        String secteurChoisi = "";
        if (!getSecteurs().isEmpty()) {
            secteurChoisi = getSecteurs().get(rand.nextInt(getSecteurs().size()));
        } else {
            secteurChoisi = "Général"; 
        }

        return new projet.Projet(
            "Projet " + secteurChoisi + " " + numero, 
            "Description proposée par l'expert " + getNom(), 
            secteurChoisi, 
            0, 0, 0, 0 
        );
    }
}