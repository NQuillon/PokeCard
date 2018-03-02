package nicolas.johan.iem.pokecard.pojo.Model;

/**
 * Created by iem on 25/01/2018.
 */

public class BuyModel {
    String idUser;
    int nbCartes;

    public BuyModel() {
    }

    public BuyModel(String idUser, int nbCartes) {
        this.idUser = idUser;
        this.nbCartes = nbCartes;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getNbCartes() {
        return nbCartes;
    }

    public void setNbCartes(int nbCartes) {
        this.nbCartes = nbCartes;
    }
}
