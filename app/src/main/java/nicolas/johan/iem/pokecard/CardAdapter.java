package nicolas.johan.iem.pokecard;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by iem on 14/11/2017.
 */

public class CardAdapter extends ArrayAdapter<String>{
    PokemonViewHolder viewHolder;
    Bitmap bitmapimg;
    Context context;

    public CardAdapter(Context context, ArrayList<String> cartes) {
        super(context, 0, cartes);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.carditem, parent, false);
        }

        viewHolder = (PokemonViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new PokemonViewHolder();
            viewHolder.imgCarte = (ImageView) convertView.findViewById(R.id.imgCarte);
            convertView.setTag(viewHolder);
        }

        String pokemonItem = getItem(position);

        Picasso.with(context).load(pokemonItem).into(viewHolder.imgCarte);
        return convertView;
    }

    private class PokemonViewHolder {
        public ImageView imgCarte;
    }
}
