package nicolas.johan.iem.pokecard.vues.fragments.exchange;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.ListExchangeAdapter;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.ExchangeModel;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeFragment extends BaseFragment {
    View parent;
    List<ExchangeModel> listeEchanges;

    public ExchangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_exchange, container, false);


        refreshData();

        final SwipeRefreshLayout swipeRefresh=(SwipeRefreshLayout) parent.findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefresh.setRefreshing(false);
            }
        });





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

    public void refreshData(){
        Call<List<ExchangeModel>> exchanges =  PokemonApp.getPokemonService().getAllExchanges(AccountSingleton.getInstance().getIdUser());

        exchanges.enqueue(new Callback<List<ExchangeModel>>() {
            @Override
            public void onResponse(Call<List<ExchangeModel>> call, Response<List<ExchangeModel>> response) {
                if(response.isSuccessful()) {
                    listeEchanges=response.body();
                    refreshView();
                }
            }

            @Override
            public void onFailure(Call<List<ExchangeModel>> call, Throwable t) {
                TextView loadingText=(TextView) parent.findViewById(R.id.loadingTextNewExchange);
                loadingText.setText("Une erreur est survenue, veuillez réessayer dans un moment.");
            }
        });
    }

    public static ExchangeFragment newInstance() {

        Bundle args = new Bundle();
        
        ExchangeFragment fragment = new ExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void refreshView() {
        ListExchangeAdapter adapter=new ListExchangeAdapter(getActivity(), listeEchanges);
        ListView listview = (ListView) parent.findViewById(R.id.list_exchange);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {

            }
        });
    }




}