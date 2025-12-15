package sacADos;
import projet.Projet;
import java.util.ArrayList;
import java.util.List;

//Vous fournirez une classe VersSacADos avec des méthodes permettant de générer un objet de la
//classe SacADos à partir d’une liste de projets (c.f. section sur notre équipe municipale) et des budgets
//alloués par Dauphine City. Nous proposons deux options différentes :
//— Les budgets concernent les 3 différents types de coûts (économique, social, et environnemental).
//— Les budgets concernent les 5 différents secteurs et seul le coût économique est considéré. Dit
//autrement, il y a un budget de B1 euros pour le sport, de B2 euros pour la santé,. . .


public class VersSacADos {

    public static SacADos convertir(List<Projet> listeProjets, int[] budgets) {
        List<Objet> listeObjets = new ArrayList<>();
        for (Projet p : listeProjets) {
            int utilite = p.getUtilite();
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
}
