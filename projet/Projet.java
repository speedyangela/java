package projet;
import java.util.Random;

public class Projet {
    String titre;
    String description;
    String secteur;
    int utilite;
    int coutEconomique;
    int coutSocial;
    int coutEnvironnemental;

    public Projet(String titre, String desc, String sec, int utilite, int coutEco, int coutS, int coutEnv) {
        this.titre = titre;
        this.description = desc;
        this.secteur = sec;
        this.utilite = utilite;
        this.coutEconomique = coutEco;
        this.coutSocial = coutS;
        this.coutEnvironnemental = coutEnv;
    }
    
    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getSecteur() {
        return secteur;
    }

    public int getUtilite() {
        return utilite;
    }

    public int getCoutEconomique() {
        return coutEconomique;
    }

    public int getCoutSocial() {
        return coutSocial;
    }

    public int getCoutEnvironnemental() {
        return coutEnvironnemental;
    }

    // --- ICI C'EST LE CODE QU'IL TE MANQUAIT ---
    // Ces méthodes permettent à l'équipe de modifier les valeurs (Simulation)
    
    public void setUtilite(int utilite) {
        this.utilite = utilite;
    }

    public void setCoutEconomique(int coutEconomique) {
        this.coutEconomique = coutEconomique;
    }

    public void setCoutSocial(int coutSocial) {
        this.coutSocial = coutSocial;
    }

    public void setCoutEnvironnemental(int coutEnvironnemental) {
        this.coutEnvironnemental = coutEnvironnemental;
    }

    // On garde la méthode statique au cas où, même si on utilise celle de l'Expert maintenant
    public static Projet genererAleatoirement(int numero) {
        Random r = new Random();
        String[] secteurs = {"sport", "santé", "éducation", "culture", "attractivité économique"};
        String secteur = secteurs[r.nextInt(secteurs.length)];
        int utilite = r.nextInt(101); 
        int coutEco = r.nextInt(101); 
        int coutSocial = r.nextInt(101); 
        int coutEnv = r.nextInt(101); 
        return new Projet("Projet " + secteur + " " + numero, "Description générée.", secteur, utilite, coutEco, coutSocial, coutEnv);
    }
}