package nicolas.johan.iem.pokecard.pojo.Model;

/**
 * Created by iem on 24/01/2018.
 */

public class EditPseudoModel {
    String idUser;
    String pseudo;

    public EditPseudoModel() {
    }

    public EditPseudoModel(String idUser, String pseudo) {
        this.idUser = idUser;
        this.pseudo = pseudo;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
