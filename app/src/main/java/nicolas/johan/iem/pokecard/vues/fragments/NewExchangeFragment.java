package nicolas.johan.iem.pokecard.vues.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewExchangeFragment extends Fragment {
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
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                Bundle data=new Bundle();
                data.putInt("id",monPokedex.get(position).getId());

                //AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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


}