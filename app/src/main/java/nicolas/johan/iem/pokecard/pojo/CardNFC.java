package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by Johan on 31/01/2018.
 */

public class CardNFC {
    String idUser;
    String idCard;

    public CardNFC() {
    }

    public CardNFC(String idUser, String idCard) {
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
