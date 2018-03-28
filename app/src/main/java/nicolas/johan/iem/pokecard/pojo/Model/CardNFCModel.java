package nicolas.johan.iem.pokecard.pojo.Model;

/**
 * Created by Johan on 31/01/2018.
 */

public class CardNFCModel {
    String idUser;
    String idCard;

    public CardNFCModel() {
    }

    public CardNFCModel(String idUser, String idCard) {
        this.idUser = idUser;
        this.idCard = idCard;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
