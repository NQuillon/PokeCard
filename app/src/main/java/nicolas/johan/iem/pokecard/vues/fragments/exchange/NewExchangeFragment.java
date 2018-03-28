package nicolas.johan.iem.pokecard.vues.fragments.exchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.GridViewAlertCardAdapter;
import nicolas.johan.iem.pokecard.adapter.PokemonAdapter;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.getUserPokemonInterface;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewExchangeFragment extends BaseFragment implements webServiceInterface, getUserPokemonInterface {
    View parent;
    LinearLayout loadingScreen;
    List<Pokemon> pokedexRetrofit;
    List<Card> cardsList;
    LinearLayout noPokemon;
    View customLayout;
    AlertDialog dialog;
    NewExchangeFragment that;

    public NewExchangeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_new_exchange, container, false);
        getActivity().setTitle("Echange");
        that = this;

        noPokemon=(LinearLayout) parent.findViewById(R.id.noPokemonExchange);

        loadingScreen=(LinearLayout) parent.findViewById(R.id.loadingNewExchange);

        ManagerPokemonService.getInstance().getUserPokemon(this, this);

        return parent;
    }

    public void refresh(final List<Pokemon> monPokedex) {
        PokemonAdapter myPokemonAdapter=new PokemonAdapter(getActivity(), monPokedex);
        final GridView gridview = (GridView) parent.findViewById(R.id.newExchange);
        gridview.setAdapter(myPokemonAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View v, int position, long id) {
                Bundle data=new Bundle();
                data.putInt("id",monPokedex.get(position).getId());

                //AlertDialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Sélectionnez la carte");
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                customLayout = getActivity().getLayoutInflater().inflate(R.layout.dialog_exchange, null);

                builder.setView(customLayout);
                //builder.show();
                dialog=builder.create();
                dialog.show();

                ManagerPokemonService.getInstance().getCardsById(Integer.parseInt(AccountSingleton.getInstance().getIdUser()), monPokedex.get(position).getId(), that);

            }
        });
    }

    public static NewExchangeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NewExchangeFragment fragment = new NewExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void showListCards(List<Card> listCards){
        cardsList = listCards;
        GridView gv_exchange = (GridView) customLayout.findViewById(R.id.gridview_cards);
        GridViewAlertCardAdapter exchange_cardAdapter=new GridViewAlertCardAdapter(getActivity(),cardsList);
        gv_exchange.setAdapter(exchange_cardAdapter);
        gv_exchange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                Bundle data=new Bundle();
                data.putString("id",cardsList.get(position).getId());
                dialog.dismiss();

                activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Fragment f = (Fragment) ReceiverExchange.newInstance(data);
                showFragment(f);
            }
        });
    }


    @Override
    public void onNoPokemon() {
        noPokemon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        loadingScreen.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        TextView loadingText=(TextView) parent.findViewById(R.id.loadingTextNewExchange);
        loadingText.setText("Une erreur est survenue, veuillez réessayer dans un moment.");
    }
}