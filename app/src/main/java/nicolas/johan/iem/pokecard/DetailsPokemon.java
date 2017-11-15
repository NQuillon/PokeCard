package nicolas.johan.iem.pokecard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsPokemon extends Fragment {
    View parent;
    ImageView imgPokemon;
    TextView id;
    TextView nom;
    TextView type;
    TextView poids;
    TextView taille;

    public DetailsPokemon() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_details, container, false);

        imgPokemon=(ImageView)parent.findViewById(R.id.details_imgPokemon);
        id=(TextView)parent.findViewById(R.id.details_id);
        nom=(TextView)parent.findViewById(R.id.details_nom);
        type=(TextView)parent.findViewById(R.id.details_type);
        poids=(TextView)parent.findViewById(R.id.details_poids);
        taille=(TextView)parent.findViewById(R.id.details_taille);

        String result="";
        Bundle data=getArguments();
        try{
            result=new GETrequest().execute("pokemon/"+data.getInt("id")).get();
            JSONObject obj=new JSONObject(result);
            Picasso.with(getContext()).load(obj.getString("urlPicture")).into(imgPokemon);
            id.setText(obj.getString("id"));
            nom.setText(obj.getString("name"));
            type.setText(obj.getString("type"));
            poids.setText(obj.getString("weight"));
            taille.setText(obj.getString("height"));
        }catch (Exception e){
        }

        final ArrayList<String> cartes=new ArrayList<String>();

        try{
            JSONObject resp = new JSONObject(result);
            JSONArray jArray = resp.getJSONArray("cards");

            for (int i=0; i < jArray.length(); i++) {
                String tmp="";
                JSONObject oneObject = jArray.getJSONObject(i);
                tmp=oneObject.getString("urlPicture");
                cartes.add(tmp);
            }
        }catch (Exception e){

        }


        CardAdapter myCardsAdapter=new CardAdapter(getContext(), cartes);
        GridView gridview = (GridView) parent.findViewById(R.id.details_cartes);
        gridview.setAdapter(myCardsAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.detailzoom, null);
                ImageView tmp=(ImageView) customLayout.findViewById(R.id.zoomimg);
                String url=cartes.get(position);
                url.replace(".png","_hires.png");
                Picasso.with(getContext()).load(url).into(tmp);
                builder.setView(customLayout);
                builder.show();
            }
        });


        return parent;
    }
}