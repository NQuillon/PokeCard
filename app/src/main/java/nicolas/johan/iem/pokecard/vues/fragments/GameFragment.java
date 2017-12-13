package nicolas.johan.iem.pokecard.vues.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nicolas.johan.iem.pokecard.R;

public class GameFragment extends Fragment {

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    public static GameFragment newInstance() {
        
        Bundle args = new Bundle();
        
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

}