package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by iem on 23/01/2018.
 */

public class GameCategory {
    public String id;
    public String name;

    public GameCategory(String id, String name) {
        this.id = id;
        this.name = name;
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
}
