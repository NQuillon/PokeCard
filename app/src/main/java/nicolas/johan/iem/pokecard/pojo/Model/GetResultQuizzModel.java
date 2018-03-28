package nicolas.johan.iem.pokecard.pojo.Model;

import java.util.List;

import nicolas.johan.iem.pokecard.pojo.Card;

/**
 * Created by iem on 24/01/2018.
 */

public class GetResultQuizzModel {
    int pokeCoinsWin;
    List<Card> cardsWin;
    String message;
    String img;

    public GetResultQuizzModel() {
    }

    public GetResultQuizzModel(int pokeCoinsWin, List<Card> cardsWin, String message, String img) {
        this.pokeCoinsWin = pokeCoinsWin;
        this.cardsWin = cardsWin;
        this.message = message;
        this.img = img;
    }

    public int getPokeCoinsWin() {
        return pokeCoinsWin;
    }

    public void setPokeCoinsWin(int pokeCoinsWin) {
        this.pokeCoinsWin = pokeCoinsWin;
    }

    public List<Card> getCardsWin() {
        return cardsWin;
    }

    public void setCardsWin(List<Card> cardsWin) {
        try {
            this.cardsWin = cardsWin;
        }catch (Exception e){

        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
