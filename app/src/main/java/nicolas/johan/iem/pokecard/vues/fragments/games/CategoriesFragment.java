package nicolas.johan.iem.pokecard.vues.fragments.games;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.ListCategoryGameAdapter;
import nicolas.johan.iem.pokecard.pojo.GameCategory;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;

public class CategoriesFragment extends BaseFragment {
    View parent;

    public CategoriesFragment() {
    }

    public static CategoriesFragment newInstance() {

        Bundle args = new Bundle();

        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent = inflater.inflate(R.layout.categories_game, container, false);

        ManagerPokemonService.getInstance().getListCategories(this);

        return parent;
    }

    public void refresh(final List<GameCategory> listCategory) {
        ListView lv = parent.findViewById(R.id.listCategoryGame);
        ListCategoryGameAdapter adapter = new ListCategoryGameAdapter(parent.getContext(), listCategory);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle data = new Bundle();
                data.putString("category", listCategory.get(position).getId());
                Fragment f = (Fragment) QuestionGame.newInstance(data);
                showFragment(f);
            }
        });
    }

}