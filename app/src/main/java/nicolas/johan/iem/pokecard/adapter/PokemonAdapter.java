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

import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.R;

/**
 * Created by iem on 14/11/2017.
 */

public class PokemonAdapter extends ArrayAdapter<Pokemon>{
    PokemonViewHolder viewHolder;
    Bitmap bitmapimg;
    Context context;

        public PokemonAdapter(Context context, List<Pokemon> pokemons) {
            super(context, 0, pokemons);
            this.context=context;
        }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokemonitem, parent, false);
            }

            viewHolder = (PokemonViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new PokemonViewHolder();
                viewHolder.imgPokemon = (ImageView) convertView.findViewById(R.id.imgPokemon);
                viewHolder.nomPokemon = (TextView) convertView.findViewById(R.id.nomPokemon);
                convertView.setTag(viewHolder);
            }

            Pokemon pokemonItem = getItem(position);

            viewHolder.nomPokemon.setText(pokemonItem.getName());
        //new chargeProfilePicture().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        Picasso.with(context).load(pokemonItem.getUrlPicture()).into(viewHolder.imgPokemon);
            return convertView;
        }

        private class PokemonViewHolder {
            public ImageView imgPokemon;
            public TextView nomPokemon;
        }
    }
