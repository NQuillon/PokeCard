package nicolas.johan.iem.pokecard;

/**
 * Created by Johan on 15/11/2017.
 */

public class Card {
    private String urlPicture;
    private String price;

    public Card(String urlPicture, String price) {
        this.urlPicture = urlPicture;
        this.price = price;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
