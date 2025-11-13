package equipe;

public class Evaluateur extends Personne {
    String specialite;

    public Evaluateur(String nom, String prenom, int age, String cout) {
        super(nom, prenom, age);
        this.specialite = cout;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}

