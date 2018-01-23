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
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.ExchangeModel;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;

/**
 * Created by iem on 19/01/2018.
 */

public class ListExchangeAdapter extends ArrayAdapter<ExchangeModel>{
    ListExchangeAdapter.ListeEchanges viewHolder;
    Bitmap bitmapimg;
    Context context;

    public ListExchangeAdapter(Context context, List<ExchangeModel> exchanges) {
        super(context, 0, exchanges);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exchange_item, parent, false);
        }

        viewHolder = (ListExchangeAdapter.ListeEchanges) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ListExchangeAdapter.ListeEchanges();
            viewHolder.pseudo_listeEchanges = (TextView) convertView.findViewById(R.id.pseudo_listeEchanges);
            viewHolder.typeEchange = (ImageView) convertView.findViewById(R.id.typeEchange);
            viewHolder.pokemon_picture_listeEchange = (ImageView) convertView.findViewById(R.id.pokemon_picture_listeEchange);
            viewHolder.pokemon_name_listeEchange=(TextView) convertView.findViewById(R.id.pokemon_name_listeEchange);
            convertView.setTag(viewHolder);
        }

        ExchangeModel exchangeItem = getItem(position);

        viewHolder.pseudo_listeEchanges.setText(exchangeItem.getPseudoSender()+" ➣ "+exchangeItem.getPseudoReceiver());
        viewHolder.pokemon_name_listeEchange.setText(exchangeItem.getCardName());

        Picasso.with(context).load(exchangeItem.getCardPicture()).into(viewHolder.pokemon_picture_listeEchange);
        if(exchangeItem.getStatus().equals("send")){
            Picasso.with(context).load(R.mipmap.send).into(viewHolder.typeEchange);
        }else{
            Picasso.with(context).load(R.mipmap.receive).into(viewHolder.typeEchange);
        }
        return convertView;
    }

    private class ListeEchanges {
        public TextView pseudo_listeEchanges;
        public ImageView typeEchange;
        public ImageView pokemon_picture_listeEchange;
        public TextView pokemon_name_listeEchange;
    }
}
