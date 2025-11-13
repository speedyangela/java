package equipe;

import java.util.List;
import java.util.ArrayList;

public class EquipeMunicipale {
    List<Expert> experts;
    Elu elu;
    List<Evaluateur> evaluateurs;

    public EquipeMunicipale(Elu elu) {
        this.elu = elu;
        this.experts = new ArrayList<>();
        this.evaluateurs = new ArrayList<>();
    }

    public List<Expert> getExperts() {
        return experts;
    }

    public void setExperts(List<Expert> experts) {
        this.experts = experts;
    }

    public Elu getElu() {
        return elu;
    }

    public void setElu(Elu elu) {
        this.elu = elu;
    }

    public List<Evaluateur> getEvaluateurs() {
        return evaluateurs;
    }

    public void setEvaluateurs(List<Evaluateur> evaluateurs) {
        this.evaluateurs = evaluateurs;
    }
}
