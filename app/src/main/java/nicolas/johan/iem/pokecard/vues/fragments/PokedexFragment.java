package nicolas.johan.iem.pokecard.vues.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.adapter.PokemonAdapter;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.getUserPokemonInterface;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;

public class PokedexFragment extends BaseFragment implements webServiceInterface, getUserPokemonInterface {
    View parent;
    LinearLayout loadingScreen;
    LinearLayout noPokemon;
    List<Pokemon> listPokemons;

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
        listPokemons =new ArrayList<>();
        getActivity().setTitle("Mon Pokédex");

        loadingScreen=(LinearLayout) parent.findViewById(R.id.loadingPokedex);
        noPokemon=(LinearLayout) parent.findViewById(R.id.noPokemon);

        ManagerPokemonService.getInstance().getUserPokemon(this, this);

        EditText searchBar=(EditText)parent.findViewById(R.id.searchBar_Pokedex);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Pokemon> tmp=new ArrayList<>();
                for(int i=0; i< listPokemons.size(); i++){
                    if(listPokemons.get(i).getName().contains(s.toString().toLowerCase())){
                        tmp.add(listPokemons.get(i));
                    }
                }
                refresh(tmp);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return parent;
    }

    public void refresh(final List<Pokemon> monPokedex) {
        if(listPokemons.size()==0){
            listPokemons=monPokedex;
        }

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