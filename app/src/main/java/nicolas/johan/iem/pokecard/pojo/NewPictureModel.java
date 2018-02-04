package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by iem on 26/01/2018.
 */

public class NewPictureModel {
    String idUser;
    String profilPicture;

    public NewPictureModel() {
    }

    public NewPictureModel(String idUser, String profilPicture) {
        this.idUser = idUser;
        this.profilPicture = profilPicture;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getProfilPicture() {
        return profilPicture;
    }

    public void setProfilPicture(String profilPicture) {
        this.profilPicture = profilPicture;
    }
}
