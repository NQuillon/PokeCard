package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by iem on 25/01/2018.
 */

public class MeteoModel {
    String temp;
    String img;

    public MeteoModel() {
    }

    public MeteoModel(String temp, String img) {
        this.temp = temp;
        this.img = img;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
