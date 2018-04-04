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
import nicolas.johan.iem.pokecard.pojo.StoreItem;

/**
 * Created by iem on 14/11/2017.
 */

public class StoreAdapter extends ArrayAdapter<StoreItem> {
    PokemonViewHolder viewHolder;
    Bitmap bitmapimg;
    Context context;

    public StoreAdapter(Context context, List<StoreItem> item) {
        super(context, 0, item);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.storeitem, parent, false);
        }

        viewHolder = (PokemonViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new PokemonViewHolder();
            viewHolder.imgStore = (ImageView) convertView.findViewById(R.id.imgstore);
            viewHolder.txtStore = (TextView) convertView.findViewById(R.id.txtStore);
            viewHolder.priceStore = (TextView) convertView.findViewById(R.id.priceStore);
            convertView.setTag(viewHolder);
        }

        StoreItem storeItem = getItem(position);

        viewHolder.txtStore.setText(storeItem.getDescription());
        viewHolder.priceStore.setText("" + storeItem.getPrice());

        Picasso.with(context).load(storeItem.getImg()).into(viewHolder.imgStore);
        return convertView;
    }

    private class PokemonViewHolder {
        public ImageView imgStore;
        public TextView txtStore;
        public TextView priceStore;
    }
}
