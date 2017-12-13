package nicolas.johan.iem.pokecard.vues.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.CardAdapter;
import nicolas.johan.iem.pokecard.adapter.ExchangeCardAdapter;
import nicolas.johan.iem.pokecard.adapter.PokemonAdapter;
import nicolas.johan.iem.pokecard.pojo.Account;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.pojo.ExchangePOST;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.vues.Accueil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewExchangeFragment extends Fragment {
    View parent;
    Accueil attachContext;
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

        Call<List<Pokemon>> pokemons =  PokemonApp.getPokemonService().getFromId(Account.getInstance().getIdUser());

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
        GridView gridview = (GridView) parent.findViewById(R.id.newExchange);
        gridview.setAdapter(myPokemonAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View v, int position, long id) {
                Bundle data=new Bundle();
                data.putInt("id",monPokedex.get(position).getId());

                //AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Sélectionnez la carte");
                final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.dialog_exchange, null);

                Call<List<Card>> list =  PokemonApp.getPokemonService().getCardsFromId(Integer.parseInt(Account.getInstance().getIdUser()),monPokedex.get(position).getId());

                list.enqueue(new Callback<List<Card>>() {
                                 @Override
                                 public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                                     if(response.isSuccessful()) {
                                         cardsList = response.body();
                                         GridView gv_exchange = (GridView) customLayout.findViewById(R.id.exchange_cards);
                                         ExchangeCardAdapter exchange_cardAdapter=new ExchangeCardAdapter(getActivity(),cardsList);
                                         gv_exchange.setAdapter(exchange_cardAdapter);
                                         gv_exchange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                                                 //------------------TEST POST RETROFIT--------------

                                                 ExchangePOST request=new ExchangePOST(Integer.parseInt(Account.getInstance().getIdUser()),12,cardsList.get(position).getId());
                                                 Call<Account> call = PokemonApp.getPokemonService().sendExchangeRequest(request);
                                                 call.enqueue(new Callback<Account>() {
                                                     @Override
                                                     public void onResponse(retrofit2.Call<Account> call, Response<Account> response) {
                                                         Account tmpAccount=response.body();
                                                         Account.getInstance().setListePokemon(tmpAccount.getListePokemon());
                                                         Account.getInstance().setListeCards(tmpAccount.getListeCards());

                                                         attachContext.update();

                                                         Toast.makeText(parent.getContext(), ""+response.code(), Toast.LENGTH_SHORT).show();
                                                     }

                                                     @Override
                                                     public void onFailure(retrofit2.Call<Account> call, Throwable t) {
                                                         Toast.makeText(parent.getContext(), "ECHEC", Toast.LENGTH_SHORT).show();
                                                     }
                                                 });

                                                 //--------------------------------------------------
                                             }
                                         });
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<List<Card>> call, Throwable t) {
                                     System.out.println("CA MARCHE PAS");
                                 }
                             });

                builder.setView(customLayout);
                builder.show();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.attachContext=(Accueil) context;
    }

    public static NewExchangeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NewExchangeFragment fragment = new NewExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}