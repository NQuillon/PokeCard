package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by iem on 13/12/2017.
 */

public class VerifyClass {
    private String pseudo;
    private String password;
    private String idUser;
    private String picture;

    public VerifyClass() {
    }

    public VerifyClass(String pseudo, String password, String idUser, String picture) {
        this.pseudo = pseudo;
        this.password = password;
        this.idUser = idUser;
        this.picture = picture;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
