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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewExchangeFragment extends BaseFragment {
    View parent;
    LinearLayout loadingScreen;
    List<Pokemon> pokedexRetrofit;
    List<Card> cardsList;

    public NewExchangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_new_exchange, container, false);
        getActivity().setTitle("Echange");

        loadingScreen=(LinearLayout) parent.findViewById(R.id.loadingNewExchange);

        Call<List<Pokemon>> pokemons =  PokemonApp.getPokemonService().getFromId(AccountSingleton.getInstance().getIdUser());

        pokemons.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if(response.isSuccessful()) {
                    loadingScreen.setVisibility(View.GONE);
                    pokedexRetrofit = response.body();
                    refresh(pokedexRetrofit);
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                TextView loadingText=(TextView) parent.findViewById(R.id.loadingTextNewExchange);
                loadingText.setText("Une erreur est survenue, veuillez réessayer dans un moment.");
            }
        });



        return parent;
    }

    private void refresh(final List<Pokemon> monPokedex) {
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

                final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.dialog_exchange, null);

                builder.setView(customLayout);
                //builder.show();
                final AlertDialog dialog=builder.create();
                dialog.show();


                Call<List<Card>> list =  PokemonApp.getPokemonService().getCardsFromId(Integer.parseInt(AccountSingleton.getInstance().getIdUser()),monPokedex.get(position).getId());

                list.enqueue(new Callback<List<Card>>() {
                                 @Override
                                 public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                                     if(response.isSuccessful()) {
                                         cardsList = response.body();
                                         GridView gv_exchange = (GridView) customLayout.findViewById(R.id.gridview_cards);
                                         GridViewAlertCardAdapter exchange_cardAdapter=new GridViewAlertCardAdapter(getActivity(),cardsList);
                                         gv_exchange.setAdapter(exchange_cardAdapter);
                                         gv_exchange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {


                                                 //final int JOUEUR=12;

                                                 Bundle data=new Bundle();
                                                 data.putString("id",cardsList.get(position).getId());
                                                 dialog.dismiss();
                                                 activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                                 Fragment f = (Fragment) ReceiverExchange.newInstance(data);
                                                 showFragment(f);

                                                 /*ExchangePOST request=new ExchangePOST(Integer.parseInt(AccountSingleton.getInstance().getIdUser()),JOUEUR,cardsList.get(position).getId());
                                                 Call<AccountModel> call = PokemonApp.getPokemonService().sendExchangeRequest(request);
                                                 call.enqueue(new Callback<AccountModel>() {
                                                     @Override
                                                     public void onResponse(retrofit2.Call<AccountModel> call, Response<AccountModel> response) {
                                                         AccountModel tmpAccount=response.body();
                                                         AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                                                         AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());

                                                         attachContext.update();

                                                         dialog.dismiss();

                                                         //showFragment(new PokedexFragment()); //TEST

                                                         final Snackbar mySnackbar = Snackbar
                                                                 .make(attachContext.findViewById(R.id.exchangeLayout),"La carte "+cardsList.get(position).getId()+" a été envoyée au joueur "+JOUEUR, Snackbar.LENGTH_LONG)
                                                                 .setActionTextColor(Color.GREEN)
                                                                 .setAction("Ok", new View.OnClickListener() {
                                                                     @Override
                                                                     public void onClick(View v) {
                                                                     }
                                                                 });
                                                         mySnackbar.show();
                                                     }

                                                     @Override
                                                     public void onFailure(retrofit2.Call<AccountModel> call, Throwable t) {
                                                         Toast.makeText(parent.getContext(), "ECHEC", Toast.LENGTH_SHORT).show();
                                                     }
                                                 });*/

                                             }
                                         });
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<List<Card>> call, Throwable t) {
                                     System.out.println("Echec");
                                 }
                             });

            }
        });
    }


    public static NewExchangeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NewExchangeFragment fragment = new NewExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}