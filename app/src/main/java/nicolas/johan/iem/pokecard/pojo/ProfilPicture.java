package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by iem on 26/01/2018.
 */

public class ProfilPicture {
    String base64;

    public ProfilPicture() {
    }

    public ProfilPicture(String base64) {
        this.base64 = base64;
    }

    public String getBase64() {
        return base64;
    }

    public void setUrl(String base64) {
        this.base64 = base64;
    }
}
