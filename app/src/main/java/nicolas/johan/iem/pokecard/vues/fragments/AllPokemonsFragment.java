package nicolas.johan.iem.pokecard.vues.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.PokemonAdapter;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPokemonsFragment extends BaseFragment implements webServiceInterface {
    View parent;
    LinearLayout loadingScreen;

    public AllPokemonsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_allpokemons, container, false);

        loadingScreen=(LinearLayout) parent.findViewById(R.id.loadingAllPokemons);
        ManagerPokemonService.getInstance().getAllPokemon(this);
        return parent;
    }

    public static AllPokemonsFragment newInstance() {
        
        Bundle args = new Bundle();
        AllPokemonsFragment fragment = new AllPokemonsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void refresh(final List<Pokemon> pokedex) {
        PokemonAdapter myPokemonAdapter=new PokemonAdapter(getActivity(), pokedex);
        GridView gridview = (GridView) parent.findViewById(R.id.allPokemons);
        gridview.setAdapter(myPokemonAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                Bundle data=new Bundle();
                data.putInt("id",pokedex.get(position).getId());
                Fragment f = DetailsPokemon.newInstance(data);
                showFragment(f);
            }
        });
    }

    @Override
    public void onSuccess() {
        loadingScreen.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        TextView loadingText = (TextView) parent.findViewById(R.id.loadingTextAllPokemons);
        loadingText.setText("Une erreur est survenue, veuillez réessayer dans un moment.");
    }
}