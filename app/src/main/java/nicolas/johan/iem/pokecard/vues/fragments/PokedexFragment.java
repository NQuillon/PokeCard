package nicolas.johan.iem.pokecard.vues.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nicolas.johan.iem.pokecard.pojo.Account;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.adapter.PokemonAdapter;
import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexFragment extends Fragment {
    View parent;
    LinearLayout loadingScreen;
    List<Pokemon> pokedexRetrofit;

    public PokedexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_pokedex, container, false);
        getActivity().setTitle("Mon Pokédex");
        /*final ArrayList<Pokemon> monPokedex;
        String result="";
        try{
            result=new GETrequest().execute("user/"+Account.getInstance().getIdUser()+"/pokedex").get();
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

        Call<List<Pokemon>> pokemons =  PokemonApp.getPokemonService().getFromId(Account.getInstance().getIdUser());

        pokemons.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if(response.isSuccessful()) {
                    loadingScreen.setVisibility(View.GONE);
                    pokedexRetrofit = response.body();
                    refresh(pokedexRetrofit);
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                TextView loadingText=(TextView) parent.findViewById(R.id.loadingTextPokedex);
                loadingText.setText("Une erreur est survenue, veuillez réessayer dans un moment.");
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

                Fragment f = (Fragment) new DetailsPokemon();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                f.setArguments(data);
                fragmentTransaction.replace(R.id.content_main, f);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


}