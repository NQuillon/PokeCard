package nicolas.johan.iem.pokecard.vues.fragments.exchange;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.FriendsAdapter;
import nicolas.johan.iem.pokecard.adapter.FriendsExchangeAdapter;
import nicolas.johan.iem.pokecard.pojo.AccountModel;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.ExchangePOST;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.vues.Accueil;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiverExchange extends BaseFragment {
    List<FriendAccount> friendsList;
    View parent;
    String idPokemon;
    String nomJoueur="";
    LinearLayout loadingScreen;

    public ReceiverExchange() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_receiverexchange, container, false);
        Bundle data=getArguments();
        idPokemon=data.getString("id");

        loadingScreen=(LinearLayout) parent.findViewById(R.id.loadingFriendExchange);


        Call<List<FriendAccount>> friends = PokemonApp.getPokemonService().getFriends(AccountSingleton.getInstance().getIdUser());

        friends.enqueue(new Callback<List<FriendAccount>>() {
            @Override
            public void onResponse(Call<List<FriendAccount>> call, Response<List<FriendAccount>> response) {
                if (response.isSuccessful()) {
                     friendsList = response.body();
                    loadingScreen.setVisibility(View.GONE);

                    ListView listeFriends=(ListView) parent.findViewById(R.id.listFriends_exchange);
                    FriendsExchangeAdapter adapter=new FriendsExchangeAdapter(parent.getContext(),friendsList);
                    listeFriends.setAdapter(adapter);

                    listeFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                            ExchangePOST request=new ExchangePOST(Integer.parseInt(AccountSingleton.getInstance().getIdUser()),Integer.parseInt(friendsList.get(position).getIdUser()),idPokemon, "send"); //a modifier si remerciement !
                            nomJoueur=friendsList.get(position).getPseudo();
                            Call<AccountModel> call = PokemonApp.getPokemonService().sendExchangeRequest(request);
                            call.enqueue(new Callback<AccountModel>() {
                                @Override
                                public void onResponse(retrofit2.Call<AccountModel> call, Response<AccountModel> response) {
                                    AccountModel tmpAccount=response.body();
                                    AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                                    AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());

                                    activity.update();



                                    activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    ExchangeFragment f = ExchangeFragment.newInstance();
                                    showFragment(f);
                                }

                                @Override
                                public void onFailure(retrofit2.Call<AccountModel> call, Throwable t) {
                                    Toast.makeText(parent.getContext(), "Impossible d'effectuer l'échange", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<FriendAccount>> call, Throwable t) {
                Toast.makeText(parent.getContext(), "Echec du chargement des amis...", Toast.LENGTH_SHORT).show();
            }
        });





        return parent;

    }

    public static ReceiverExchange newInstance(Bundle data) {
        
        ReceiverExchange fragment = new ReceiverExchange();
        fragment.setArguments(data);
        return fragment;
    }


}