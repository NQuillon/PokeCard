package nicolas.johan.iem.pokecard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllPokemonsFragment extends Fragment {
    View parent;

    public AllPokemonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_allpokemons, container, false);

        final ArrayList<Pokemon> pokedex;
        String result="";
        try{
            result=new GETrequest().execute("pokedex").get();
        }catch (Exception e){

        }
        pokedex=new ArrayList<Pokemon>();
        JSONArray jsonArray;
        try{
            JSONObject resp = new JSONObject(result);
            JSONArray jArray = resp.getJSONArray("pokedex");

            for (int i=0; i < jArray.length(); i++) {
                Pokemon tmp=new Pokemon();
                JSONObject oneObject = jArray.getJSONObject(i);
                tmp.setId(oneObject.getInt("id"));
                tmp.setName(oneObject.getString("name"));
                tmp.setUrlPicture(oneObject.getString("urlPicture"));
                pokedex.add(tmp);
            }
        }catch (Exception e){

        }

        PokemonAdapter myPokemonAdapter=new PokemonAdapter(getActivity(), pokedex);
        GridView gridview = (GridView) parent.findViewById(R.id.allPokemons);
        gridview.setAdapter(myPokemonAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {

                Bundle data=new Bundle();
                data.putInt("id",pokedex.get(position).getId());

                Fragment f = (Fragment) new DetailsPokemon();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                f.setArguments(data);
                fragmentTransaction.replace(R.id.content_main, f);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return parent;
    }

}