package nicolas.johan.iem.pokecard.vues.fragments.exchange;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.ListExchangeAdapter;
import nicolas.johan.iem.pokecard.pojo.Model.ExchangeModel;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;

public class ExchangeFragment extends BaseFragment implements webServiceInterface {
    View parent;
    List<ExchangeModel> listeEchanges;
    ExchangeFragment that;

    public ExchangeFragment() {
    }

    public static ExchangeFragment newInstance() {

        Bundle args = new Bundle();

        ExchangeFragment fragment = new ExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent = inflater.inflate(R.layout.fragment_exchange, container, false);
        that = this;

        ManagerPokemonService.getInstance().getAllExchange(this);

        final SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) parent.findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ManagerPokemonService.getInstance().getAllExchange(that);
                swipeRefresh.setRefreshing(false);
            }
        });

        FloatingActionButton fb_exchange = (FloatingActionButton) parent.findViewById(R.id.fb_exchange);
        fb_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = (Fragment) new NewExchangeFragment();
                showFragment(f);
            }
        });

        return parent;
    }

    public void refreshView(List<ExchangeModel> listEchanges) {
        listeEchanges = listEchanges;
        ListExchangeAdapter adapter = new ListExchangeAdapter(getActivity(), listeEchanges);
        ListView listview = (ListView) parent.findViewById(R.id.list_exchange);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //todo
            }
        });
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {
        TextView loadingText = (TextView) parent.findViewById(R.id.loadingTextNewExchange);
        loadingText.setText("Une erreur est survenue, veuillez réessayer dans un moment.");
    }
}