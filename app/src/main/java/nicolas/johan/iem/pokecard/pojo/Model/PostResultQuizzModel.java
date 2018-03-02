package nicolas.johan.iem.pokecard.pojo.Model;

/**
 * Created by iem on 24/01/2018.
 */

public class PostResultQuizzModel {
    String idUser;
    int score;

    public PostResultQuizzModel() {
    }

    public PostResultQuizzModel(String idUser, int score) {
        this.idUser = idUser;
        this.score = score;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
