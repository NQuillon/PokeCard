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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexFragment extends BaseFragment {
    View parent;
    LinearLayout loadingScreen;
    LinearLayout noPokemon;
    List<Pokemon> pokedexRetrofit;

    public PokedexFragment() {
        // Required empty public constructor
    }

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
        /*final ArrayList<Pokemon> monPokedex;
        String result="";
        try{
            result=new GETrequest().execute("user/"+AccountSingleton.getInstance().getIdUser()+"/pokedex").get();
        }catch (Exception e){

        }
        monPokedex=new ArrayList<Pokemon>();
        try{
            JSONObject resp = new JSONObject(result);
            JSONArray jArray = resp.getJSONArray("pokedex");

            for (int i=0; i < jArray.length(); i++) {
                Pokemon tmp=new Pokemon();
                JSONObject oneObject = jArray.getJSONObject(i);
                tmp.setId(oneObject.getInt("id"));
                tmp.setName(oneObject.getString("name"));
                tmp.setUrlPicture(oneObject.getString("urlPicture"));
                monPokedex.add(tmp);
            }
        }catch (Exception e){

        }*/

        loadingScreen=(LinearLayout) parent.findViewById(R.id.loadingPokedex);
        noPokemon=(LinearLayout) parent.findViewById(R.id.noPokemon);


            Call<List<Pokemon>> pokemons = PokemonApp.getPokemonService().getFromId(AccountSingleton.getInstance().getIdUser());

            pokemons.enqueue(new Callback<List<Pokemon>>() {
                @Override
                public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                    if (response.isSuccessful()) {
                        try {
                            loadingScreen.setVisibility(View.GONE);
                            pokedexRetrofit = response.body();
                            if (pokedexRetrofit.size() == 0) {
                                noPokemon.setVisibility(View.VISIBLE);
                            }
                            refresh(pokedexRetrofit);
                        }catch(Exception e){}
                    }
                }

                @Override
                public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                    try{
                        TextView loadingText = (TextView) parent.findViewById(R.id.loadingTextPokedex);
                        loadingText.setText("Une erreur est survenue, veuillez réessayer dans un moment.");
                    }catch(Exception e){

                    }
                }
            });




        return parent;
    }

    private void refresh(final List<Pokemon> monPokedex) {
        PokemonAdapter myPokemonAdapter=new PokemonAdapter(getActivity(), monPokedex);
        GridView gridview = (GridView) parent.findViewById(R.id.myPokedex);
        gridview.setAdapter(myPokemonAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {

                Bundle data=new Bundle();
                data.putInt("id",monPokedex.get(position).getId());

                Fragment f = DetailsPokemon.newInstance(data);
                showFragment(f);

                /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                f.setArguments(data);
                fragmentTransaction.replace(R.id.content_main, f);
                fragmentTransaction.addToBackStack("Nav");
                fragmentTransaction.commit();*/
            }
        });
    }

}