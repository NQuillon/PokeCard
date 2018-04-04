package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by iem on 13/12/2017.
 */

public class FriendAccount {
    String idUser; //id bd
    String pseudo;
    String picture;
    int nbCartes;

    public FriendAccount(String idUser, String pseudo, String picture, int nbCartes) {
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.picture = picture;
        this.nbCartes = nbCartes;
    }

    public int getNbCartes() {
        return nbCartes;
    }

    public void setNbCartes(int nbCartes) {
        this.nbCartes = nbCartes;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
