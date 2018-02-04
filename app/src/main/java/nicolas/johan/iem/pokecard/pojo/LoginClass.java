package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by iem on 13/12/2017.
 */

public class LoginClass {
    private String pseudo;
    private String password;

    public LoginClass() {
    }

    public LoginClass(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
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
}
