package nicolas.johan.iem.pokecard.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.GameCategory;

/**
 * Created by iem on 19/01/2018.
 */

public class ListCategoryGameAdapter extends ArrayAdapter<GameCategory> {
    ListCategoryGameAdapter.GameCategoryHandler viewHolder;
    Bitmap bitmapimg;
    Context context;

    public ListCategoryGameAdapter(Context context, List<GameCategory> exchanges) {
        super(context, 0, exchanges);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_category_item, parent, false);
        }

        viewHolder = (ListCategoryGameAdapter.GameCategoryHandler) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ListCategoryGameAdapter.GameCategoryHandler();
            viewHolder.category = (TextView) convertView.findViewById(R.id.categoryName);
            convertView.setTag(viewHolder);
        }

        GameCategory item = getItem(position);

        viewHolder.category.setText(item.getName());

        return convertView;
    }

    private class GameCategoryHandler {
        public TextView category;
    }
}
