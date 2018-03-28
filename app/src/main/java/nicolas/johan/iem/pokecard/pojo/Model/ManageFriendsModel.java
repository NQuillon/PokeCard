package nicolas.johan.iem.pokecard.pojo.Model;

/**
 * Created by iem on 24/01/2018.
 */

public class ManageFriendsModel {
    String pseudoFriend;

    public ManageFriendsModel(String pseudoFriend) {
        this.pseudoFriend = pseudoFriend;
    }

    public ManageFriendsModel() {
    }

    public String getPseudoFriend() {
        return pseudoFriend;
    }

    public void setPseudoFriend(String pseudoFriend) {
        this.pseudoFriend = pseudoFriend;
    }
}
