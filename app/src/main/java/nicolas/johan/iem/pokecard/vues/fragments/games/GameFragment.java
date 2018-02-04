package nicolas.johan.iem.pokecard.vues.fragments.games;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;

public class GameFragment extends BaseFragment {
    View parent;
    LinearLayout btn_questions;
    LinearLayout btn_chuckNorris;

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent= inflater.inflate(R.layout.fragment_game, container, false);
        init();

        btn_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = (Fragment) CategoriesFragment.newInstance();
                showFragment(f);
            }
        });

        btn_chuckNorris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = (Fragment) ChuckNorrisFragment.newInstance();
                showFragment(f);
            }
        });

        return parent;
    }

    public void init(){
        btn_questions=parent.findViewById(R.id.games_questions);
        btn_chuckNorris=parent.findViewById(R.id.games_chucknorris);
    }

    public static GameFragment newInstance() {
        
        Bundle args = new Bundle();
        
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

}