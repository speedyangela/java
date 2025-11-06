package sacADos;

public class Objet {
    private int utilite;
    private int[] couts;
    public Objet(int util, int[] couts) {
        this.utilite = util;
        this.couts = couts;
    }
    public int getUtilite() {
        return utilite;
    }

    public int[] getCouts() {
        return couts;
    }

}


