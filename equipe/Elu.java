package equipe;

public class Elu extends Personne {
    public Elu(String nom, String prenom, int age) {
        super(nom, prenom, age);
    }
    public void estimerBenefice(projet.Projet p) {
        java.util.Random rand = new java.util.Random();
        p.setUtilite(rand.nextInt(100) + 1);
    }
}