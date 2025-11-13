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
}
