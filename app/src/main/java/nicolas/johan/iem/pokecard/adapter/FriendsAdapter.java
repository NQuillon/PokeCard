package nicolas.johan.iem.pokecard.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
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

public class FriendsAdapter extends ArrayAdapter<FriendAccount> {
    FriendsAdapter.FriendViewHolder viewHolder;
    Bitmap bitmapimg;
    Context context;

    public FriendsAdapter(Context context, List<FriendAccount> friends) {
        super(context, 0, friends);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_item, parent, false);
        }

        viewHolder = (FriendsAdapter.FriendViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new FriendsAdapter.FriendViewHolder();
            viewHolder.profilePicture_friend = (ImageView) convertView.findViewById(R.id.profilePicture_friend);
            viewHolder.pseudo_friend = (TextView) convertView.findViewById(R.id.pseudo_friend);
            viewHolder.nbCartes = (TextView) convertView.findViewById(R.id.nbCartes_friends);
            convertView.setTag(viewHolder);
        }

        FriendAccount friendItem = getItem(position);

        viewHolder.pseudo_friend.setText(friendItem.getPseudo());
        viewHolder.nbCartes.setText("" + friendItem.getNbCartes());

        if (URLUtil.isValidUrl(friendItem.getPicture())) {
            Picasso.with(context).load(friendItem.getPicture()).into(viewHolder.profilePicture_friend);
        } else {
            byte[] imageBytes = Base64.decode(friendItem.getPicture(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            viewHolder.profilePicture_friend.setImageBitmap(decodedImage);
        }

        return convertView;
    }

    private class FriendViewHolder {
        public ImageView profilePicture_friend;
        public TextView pseudo_friend;
        public TextView nbCartes;
    }
}
