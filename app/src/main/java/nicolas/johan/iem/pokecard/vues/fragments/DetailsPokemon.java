package nicolas.johan.iem.pokecard.vues.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.CardAdapter;
import nicolas.johan.iem.pokecard.pojo.PokemonDetails;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPokemon extends Fragment implements webServiceInterface {
    View parent;
    ImageView imgPokemon;
    TextView id;
    TextView nom;
    TextView type;
    TextView poids;
    TextView taille;
    PokemonDetails details;
    LinearLayout loadingScreen;

    public DetailsPokemon() {}

    public static DetailsPokemon newInstance(Bundle data) {
        DetailsPokemon fragment = new DetailsPokemon();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_details, container, false);

        initVariables();

        Bundle data=getArguments();
        ManagerPokemonService.getInstance().getPokemonDetails(data.getInt("id"), this);

        return parent;
    }

    private void initVariables() {
        imgPokemon=(ImageView)parent.findViewById(R.id.details_imgPokemon);
        id=(TextView)parent.findViewById(R.id.details_id);
        nom=(TextView)parent.findViewById(R.id.details_nom);
        type=(TextView)parent.findViewById(R.id.details_type);
        poids=(TextView)parent.findViewById(R.id.details_poids);
        taille=(TextView)parent.findViewById(R.id.details_taille);
        loadingScreen=(LinearLayout) parent.findViewById(R.id.loadingDetails);
    }

    public void refresh(final PokemonDetails pok) {
        Picasso.with(getContext()).load(pok.getUrlPicture()).into(imgPokemon);
        id.setText("(n°"+pok.getId()+")");
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
                url=url.replace(".png","_hires.png");
                Picasso.with(getContext()).load(url).into(tmp);
                builder.setView(customLayout);
                builder.show();
            }
        });
    }

    @Override
    public void onSuccess() {
        loadingScreen.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        TextView loadingText = (TextView) parent.findViewById(R.id.loadingTextDetails);
        loadingText.setText("Une erreur est survenue, veuillez réessayer dans un moment.");
    }
}