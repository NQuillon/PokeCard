package nicolas.johan.iem.pokecard.vues.fragments.exchange;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;

public class ExchangeFragment extends BaseFragment {
    View parent;

    public ExchangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_exchange, container, false);

        FloatingActionButton fb_exchange=(FloatingActionButton) parent.findViewById(R.id.fb_exchange);
        fb_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = (Fragment) new NewExchangeFragment();
                showFragment(f);
                /*FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, f);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });


        return parent;
    }

    public static ExchangeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ExchangeFragment fragment = new ExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

}