package nicolas.johan.iem.pokecard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPokemon extends Fragment {
    View parent;
    ImageView imgPokemon;
    TextView id;
    TextView nom;
    TextView type;
    TextView poids;
    TextView taille;
    PokemonDetails details;

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
        Bundle data=getArguments();

        /*String result="";

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

        }*/

        Call<PokemonDetails> pokdet =  PokemonApp.getPokemonService().getDetails(data.getInt("id"));

        pokdet.enqueue(new Callback<PokemonDetails>() {
            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                if(response.isSuccessful()) {
                    details = response.body();
                    refresh(details);
                }
            }
            @Override
            public void onFailure(Call<PokemonDetails> call, Throwable t) {

            }
        });


        return parent;
    }

    private void refresh(final PokemonDetails pok) {
        Picasso.with(getContext()).load(pok.getUrlPicture()).into(imgPokemon);
        id.setText(pok.getId());
        nom.setText(pok.getName());
        type.setText(pok.getType());
        poids.setText(pok.getWeight());
        taille.setText(pok.getHeight());
        CardAdapter myCardsAdapter=new CardAdapter(getContext(), pok.getCards());
        GridView gridview = (GridView) parent.findViewById(R.id.details_cartes);
        gridview.setAdapter(myCardsAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.detailzoom, null);
                ImageView tmp=(ImageView) customLayout.findViewById(R.id.zoomimg);
                String url=pok.getCards().get(position).getUrlPicture();
                url.replace(".png","_hires.png");
                Picasso.with(getContext()).load(url).into(tmp);
                builder.setView(customLayout);
                builder.show();
            }
        });
    }
}