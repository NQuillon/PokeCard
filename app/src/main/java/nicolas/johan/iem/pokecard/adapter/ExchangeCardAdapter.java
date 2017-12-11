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

import java.util.ArrayList;
import java.util.List;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.Account;
import nicolas.johan.iem.pokecard.pojo.Card;

/**
 * Created by iem on 14/11/2017.
 */

public class ExchangeCardAdapter extends ArrayAdapter<Card>{
    PokemonViewHolder viewHolder;
    Context context;

    public ExchangeCardAdapter(Context context, List<Card> cartes) {
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
            viewHolder.nbCards=(TextView) convertView.findViewById(R.id.cardsNumber);
            convertView.setTag(viewHolder);
        }

        Card pokemonItem = getItem(position);

            int nb=0;
            for(int i=0; i<Account.getInstance().getListeCards().size();i++){
                if(Account.getInstance().getListeCards().get(i).equals(pokemonItem.getId())){
                    nb++;
                }
            }
            if(nb>=2){
                viewHolder.nbCards.setText("x"+nb);
            }else{
                viewHolder.nbCards.setText("");
            }


        Picasso.with(context).load(pokemonItem.getUrlPicture()).into(viewHolder.imgCarte);
        return convertView;
    }

    private class PokemonViewHolder {
        public ImageView imgCarte;
        public TextView nbCards;
    }
}
