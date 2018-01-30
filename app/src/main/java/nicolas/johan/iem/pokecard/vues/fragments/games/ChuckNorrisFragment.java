package nicolas.johan.iem.pokecard.vues.fragments.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.ChuckNorrisFactsModel;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuckNorrisFragment extends BaseFragment {
    View parent;
    TextView pokecoinsWins;
    TextView norrisFact;
    ImageView imgNorris;
    Button next;

    public ChuckNorrisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent= inflater.inflate(R.layout.fragment_norris_game, container, false);
        init();

        loadFact();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFact();
            }
        });

        return parent;
    }

    public void init(){
        pokecoinsWins=parent.findViewById(R.id.pokecoinswinnorris);
        norrisFact=parent.findViewById(R.id.norris_fact);
        next=parent.findViewById(R.id.btn_norris_next);
        imgNorris=parent.findViewById(R.id.imgNorris);
    }

    public void loadFact(){
        Call<ChuckNorrisFactsModel> request = PokemonApp.getPokemonService().getChuckNorrisFact(AccountSingleton.getInstance().getIdUser());
        request.enqueue(new Callback<ChuckNorrisFactsModel>() {
            @Override
            public void onResponse(Call<ChuckNorrisFactsModel> call, Response<ChuckNorrisFactsModel> response) {
                if(response.isSuccessful()){
                    try{
                        ChuckNorrisFactsModel tmp=response.body();
                        norrisFact.setText(tmp.getFact());
                        pokecoinsWins.setText(""+tmp.pokeCoinsWin);
                        Picasso.with(activity).load(tmp.getPicture()).into(imgNorris);
                        activity.update();
                    }catch (Exception e){
                        norrisFact.setText("Erreur de chargement...");
                        pokecoinsWins.setText("0");
                    }
                }
            }

            @Override
            public void onFailure(Call<ChuckNorrisFactsModel> call, Throwable t) {
                Toast.makeText(activity, "Erreur de chargement des Chuck Norris Facts...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static ChuckNorrisFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChuckNorrisFragment fragment = new ChuckNorrisFragment();
        fragment.setArguments(args);
        return fragment;
    }

}