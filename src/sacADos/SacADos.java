package sacADos;

import java.util.List;

public class SacADos {
    private int dimension;
    private int[] budgets;
    private List<Objet> objets;
    public SacADos(int dim, int[] budgets, List<Objet> objets) {
        this.dimension = dim;
        this.budgets = budgets;
        this.objets = objets;
    }
    public int getDimension() {
        return dimension;
    }

    public int[] getBudgets() {
        return budgets;
    }

    public List<Objet> getObjets() {
        return objets;
    }

}