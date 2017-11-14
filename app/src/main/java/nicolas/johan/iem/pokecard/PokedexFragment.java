package nicolas.johan.iem.pokecard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PokedexFragment extends Fragment {
    View parent;

    public PokedexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_pokedex, container, false);

        List<String> maListe=new ArrayList<String>();
        maListe.add("test1");
        maListe.add("test2");
        maListe.add("test3");
        maListe.add("test4");
        maListe.add("test5");
        maListe.add("test6");
        maListe.add("test7");
        maListe.add("test8");
        maListe.add("test9");
        maListe.add("test10");
        maListe.add("test11");
        maListe.add("test12");
        maListe.add("test13");
        maListe.add("test14");
        maListe.add("test15");


        GridView gridview = (GridView) parent.findViewById(R.id.myPokedex);
        gridview.setAdapter(new PokemonAdapter(getActivity(), maListe));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                Toast.makeText(getContext(), "" + position,Toast.LENGTH_SHORT).show();
            }
        });

        return parent;
    }

}