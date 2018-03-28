package nicolas.johan.iem.pokecard.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.ProfilPicture;

/**
 * Created by iem on 14/11/2017.
 */

public class GridViewProfilePicture extends ArrayAdapter<ProfilPicture>{
    PictureViewHolder viewHolder;
    Context context;

    public GridViewProfilePicture(Context context, List<ProfilPicture> pictures) {
        super(context, 0, pictures);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.profil_picture_item, parent, false);
        }

        viewHolder = (PictureViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new PictureViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.imgProfilPicture);
            convertView.setTag(viewHolder);
        }

        ProfilPicture tmpPicture = getItem(position);

        byte[] imageBytes = Base64.decode(tmpPicture.getBase64(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        viewHolder.img.setImageBitmap(decodedImage);

        return convertView;
    }

    private class PictureViewHolder {
        public ImageView img;
    }
}
