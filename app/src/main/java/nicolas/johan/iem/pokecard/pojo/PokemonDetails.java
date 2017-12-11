package nicolas.johan.iem.pokecard.pojo;

import java.util.ArrayList;

/**
 * Created by Johan on 15/11/2017.
 */

public class PokemonDetails {
    private String id;
    private String name;
    private String weight;
    private String height;
    private String type;
    private String urlPicture;
    private ArrayList<Card> cards;

    public PokemonDetails(String id, String name, String weight, String height, String type, String urlPicture, ArrayList<Card> cards) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.type = type;
        this.urlPicture = urlPicture;
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
