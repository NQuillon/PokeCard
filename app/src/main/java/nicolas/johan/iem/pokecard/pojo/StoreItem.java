package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by Johan on 14/11/2017.
 */

public class StoreItem {
    String description;
    String img;
    int price;
    int nbCartes;

    public StoreItem() {
    }

    public StoreItem(String description, String img, int price, int nbCartes) {
        this.description = description;
        this.img = img;
        this.price = price;
        this.nbCartes = nbCartes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNbCartes() {
        return nbCartes;
    }

    public void setNbCartes(int nbCartes) {
        this.nbCartes = nbCartes;
    }
}
