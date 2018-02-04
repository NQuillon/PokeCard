package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by Johan on 14/11/2017.
 */

public class Pokemon {
    private int id;
    private String name;
    private String urlPicture;

    public Pokemon(){

    }

    public Pokemon(int id, String name, String urlPicture) {
        this.id = id;
        this.name = name;
        this.urlPicture = urlPicture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }
}
