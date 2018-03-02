package nicolas.johan.iem.pokecard.pojo.Model;

/**
 * Created by iem on 25/01/2018.
 */

public class ModifyZIPModel {
    String idUser;
    String zipCode;

    public ModifyZIPModel() {
    }

    public ModifyZIPModel(String idUser, String zipCode) {
        this.idUser = idUser;
        this.zipCode = zipCode;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
