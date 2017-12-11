package nicolas.johan.iem.pokecard.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nicolas.johan.iem.pokecard.pojo.Account;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.R;

/**
 * Created by iem on 14/11/2017.
 */

public class CardAdapter extends ArrayAdapter<Card>{
    PokemonViewHolder viewHolder;
    Bitmap bitmapimg;
    Context context;

    public CardAdapter(Context context, ArrayList<Card> cartes) {
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

        Card pokemonItem = getItem(position);

        if(Account.getInstance().getListeCards().contains("base5-32") && pokemonItem.getId().equals("base5-32")){
            viewHolder.imgCarte.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        }

        Picasso.with(context).load(pokemonItem.getUrlPicture()).into(viewHolder.imgCarte);
        return convertView;
    }

    private class PokemonViewHolder {
        public ImageView imgCarte;
    }
}
