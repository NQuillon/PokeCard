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

import java.util.List;

import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.adapter.PokemonAdapter;
import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.getUserPokemonInterface;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexFragment extends BaseFragment implements webServiceInterface, getUserPokemonInterface {
    View parent;
    LinearLayout loadingScreen;
    LinearLayout noPokemon;

    public PokedexFragment() {}

    public static PokedexFragment newInstance() {
        Bundle args = new Bundle();
        PokedexFragment fragment = new PokedexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_pokedex, container, false);
        getActivity().setTitle("Mon Pokédex");

        loadingScreen=(LinearLayout) parent.findViewById(R.id.loadingPokedex);
        noPokemon=(LinearLayout) parent.findViewById(R.id.noPokemon);

        ManagerPokemonService.getInstance().getUserPokemon(this, this);

        return parent;
    }

    public void refresh(final List<Pokemon> monPokedex) {
        PokemonAdapter myPokemonAdapter=new PokemonAdapter(getActivity(), monPokedex);
        GridView gridview = (GridView) parent.findViewById(R.id.myPokedex);
        gridview.setAdapter(myPokemonAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {

                Bundle data=new Bundle();
                data.putInt("id",monPokedex.get(position).getId());

                Fragment f = DetailsPokemon.newInstance(data);
                showFragment(f);
            }
        });
    }

    public void onNoPokemon(){
        noPokemon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        loadingScreen.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        TextView loadingText = (TextView) parent.findViewById(R.id.loadingTextPokedex);
        loadingText.setText("Une erreur est survenue, veuillez réessayer dans un moment.");
    }
}