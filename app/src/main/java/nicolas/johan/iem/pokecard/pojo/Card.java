package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by Johan on 15/11/2017.
 */

public class Card {
    private String id;
    private String urlPicture;
    private String price;

    public Card(String id, String urlPicture, String price) {
        this.id=id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
