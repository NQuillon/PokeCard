package nicolas.johan.iem.pokecard.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;

/**
 * Created by iem on 19/01/2018.
 */

public class FriendsExchangeAdapter extends ArrayAdapter<FriendAccount>{
    FriendsExchangeAdapter.FriendViewHolder viewHolder;
    Bitmap bitmapimg;
    Context context;

    public FriendsExchangeAdapter(Context context, List<FriendAccount> friends) {
        super(context, 0, friends);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.receiverexchange_item, parent, false);
        }

        viewHolder = (FriendsExchangeAdapter.FriendViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new FriendsExchangeAdapter.FriendViewHolder();
            viewHolder.profilePicture_friend = (ImageView) convertView.findViewById(R.id.profilePicture_friendexchange);
            viewHolder.pseudo_friend = (TextView) convertView.findViewById(R.id.pseudo_friendexchange);
            viewHolder.nbCartes=(TextView) convertView.findViewById(R.id.nbCartes_friendsexchange);
            convertView.setTag(viewHolder);
        }

        FriendAccount friendItem = getItem(position);

        viewHolder.pseudo_friend.setText(friendItem.getPseudo());
        viewHolder.nbCartes.setText(""+friendItem.getNbCartes());
        //new chargeProfilePicture().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        Picasso.with(context).load(friendItem.getPicture()).into(viewHolder.profilePicture_friend);
        return convertView;
    }

    private class FriendViewHolder {
        public ImageView profilePicture_friend;
        public TextView pseudo_friend;
        public TextView nbCartes;
    }
}
