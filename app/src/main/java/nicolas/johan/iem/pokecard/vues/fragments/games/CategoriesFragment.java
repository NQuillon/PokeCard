package nicolas.johan.iem.pokecard.vues.fragments.games;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.ListCategoryGameAdapter;
import nicolas.johan.iem.pokecard.pojo.GameCategory;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends BaseFragment {
    View parent;
    List<GameCategory> liste;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent= inflater.inflate(R.layout.categories_game, container, false);


        Call<List<GameCategory>> cat = PokemonApp.getPokemonService().getAllCategories();

        cat.enqueue(new Callback<List<GameCategory>>() {
            @Override
            public void onResponse(Call<List<GameCategory>> call, Response<List<GameCategory>> response) {
                if(response.isSuccessful()){
                    try {
                        liste = response.body();
                        refresh();
                    }catch(Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GameCategory>> call, Throwable t) {

            }
        });



        return parent;
    }

    public void refresh(){
        ListView lv=parent.findViewById(R.id.listCategoryGame);
        ListCategoryGameAdapter adapter=new ListCategoryGameAdapter(parent.getContext(),liste);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle data=new Bundle();
                data.putString("category", liste.get(position).getId());
                Fragment f=(Fragment) QuestionGame.newInstance(data);
                showFragment(f);
            }
        });
    }


    public static CategoriesFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

}