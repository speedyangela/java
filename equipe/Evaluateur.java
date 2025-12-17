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
    public void evaluer(projet.Projet p) {
        java.util.Random rand = new java.util.Random();
        int estimation = rand.nextInt(100) + 1; 

        if (this.specialite.equalsIgnoreCase("economique")) {
            p.setCoutEconomique(estimation);
        } else if (this.specialite.equalsIgnoreCase("social")) {
            p.setCoutSocial(estimation);
        } else if (this.specialite.equalsIgnoreCase("environnemental")) {
            p.setCoutEnvironnemental(estimation);
        }
    }
}
