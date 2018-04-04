package nicolas.johan.iem.pokecard.vues;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import nicolas.johan.iem.pokecard.R;

/**
 * Created by iem on 13/12/2017.
 */

public class BaseActivity extends AppCompatActivity {

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack("Nav")
                .replace(R.id.content_main, fragment) //replace: recharge la page     add: garde en mémoire
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void clearBackstack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
