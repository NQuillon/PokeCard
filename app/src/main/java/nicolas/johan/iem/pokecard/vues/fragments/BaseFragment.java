package nicolas.johan.iem.pokecard.vues.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import nicolas.johan.iem.pokecard.vues.Accueil;

/**
 * Created by iem on 13/12/2017.
 */

public class BaseFragment extends Fragment {
    public Accueil activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Accueil) context;
    }

    public void showFragment(Fragment f) {
        this.activity.showFragment(f);
    }
}
