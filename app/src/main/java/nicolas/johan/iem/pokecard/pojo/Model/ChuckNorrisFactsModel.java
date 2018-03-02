package nicolas.johan.iem.pokecard.pojo.Model;

/**
 * Created by Johan on 30/01/2018.
 */

public class ChuckNorrisFactsModel {
    public String picture;
    public String fact;
    public int pokeCoinsWin;

    public ChuckNorrisFactsModel() {
    }

    public ChuckNorrisFactsModel(String picture, String fact, int pokeCoinsWin) {
        this.picture = picture;
        this.fact = fact;
        this.pokeCoinsWin = pokeCoinsWin;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public int getPokeCoinsWin() {
        return pokeCoinsWin;
    }

    public void setPokeCoinsWin(int pokeCoinsWin) {
        this.pokeCoinsWin = pokeCoinsWin;
    }
}
