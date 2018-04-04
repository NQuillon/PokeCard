package nicolas.johan.iem.pokecard.webservice;

import java.util.List;

import nicolas.johan.iem.pokecard.pojo.FriendAccount;

/**
 * Created by iem on 02/03/2018.
 */

public interface getFriendsInterface {
    void refresh(List<FriendAccount> list);
}
