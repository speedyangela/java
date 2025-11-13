package equipe;

public class Expert extends Personne {
    String secteur;

    public Expert(String nom, String prenom, int age, String secteur) {
        super(nom, prenom, age);
        this.secteur = secteur;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }
}
