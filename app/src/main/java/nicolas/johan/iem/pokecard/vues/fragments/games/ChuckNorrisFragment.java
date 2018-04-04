package nicolas.johan.iem.pokecard.vues.fragments.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.Model.ChuckNorrisFactsModel;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;

public class ChuckNorrisFragment extends BaseFragment implements webServiceInterface {
    View parent;
    TextView pokecoinsWins;
    TextView norrisFact;
    ImageView imgNorris;
    Button next;
    ChuckNorrisFragment that;

    public ChuckNorrisFragment() {
    }

    public static ChuckNorrisFragment newInstance() {

        Bundle args = new Bundle();

        ChuckNorrisFragment fragment = new ChuckNorrisFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent = inflater.inflate(R.layout.fragment_norris_game, container, false);
        init();

        ManagerPokemonService.getInstance().getChuckNorrisFact(that);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagerPokemonService.getInstance().getChuckNorrisFact(that);
            }
        });

        return parent;
    }

    public void init() {
        pokecoinsWins = parent.findViewById(R.id.pokecoinswinnorris);
        norrisFact = parent.findViewById(R.id.norris_fact);
        next = parent.findViewById(R.id.btn_norris_next);
        imgNorris = parent.findViewById(R.id.imgNorris);
        that = this;
    }

    public void loadFact(ChuckNorrisFactsModel chuckNorrisFactsModel) {
        norrisFact.setText(chuckNorrisFactsModel.getFact());
        pokecoinsWins.setText("" + chuckNorrisFactsModel.pokeCoinsWin);
        Picasso.with(activity).load(chuckNorrisFactsModel.getPicture()).into(imgNorris);
    }

    @Override
    public void onSuccess() {
        activity.update();
    }

    @Override
    public void onFailure() {
        norrisFact.setText("Erreur de chargement...");
        pokecoinsWins.setText("0");
    }
}