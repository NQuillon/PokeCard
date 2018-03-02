package nicolas.johan.iem.pokecard.vues.fragments.exchange;

import android.os.Bundle;
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
import nicolas.johan.iem.pokecard.adapter.FriendsExchangeAdapter;
import nicolas.johan.iem.pokecard.pojo.Model.AccountModel;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.ExchangePOST;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.getFriendsInterface;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiverExchange extends BaseFragment implements webServiceInterface, getFriendsInterface {
    List<FriendAccount> friendsList;
    View parent;
    String idPokemon;
    String nomJoueur="";
    LinearLayout loadingScreen;
    ReceiverExchange that;

    public ReceiverExchange() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_receiverexchange, container, false);
        Bundle data=getArguments();
        idPokemon=data.getString("id");

        that = this;

        loadingScreen=(LinearLayout) parent.findViewById(R.id.loadingFriendExchange);

        ManagerPokemonService.getInstance().getFriends(this, this);
        return parent;

    }

    public static ReceiverExchange newInstance(Bundle data) {
        
        ReceiverExchange fragment = new ReceiverExchange();
        fragment.setArguments(data);
        return fragment;
    }


    @Override
    public void onSuccess() {
        loadingScreen.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        Toast.makeText(parent.getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refresh(List<FriendAccount> list) {
        friendsList = list;

        ListView listeFriends=(ListView) parent.findViewById(R.id.listFriends_exchange);
        FriendsExchangeAdapter adapter=new FriendsExchangeAdapter(parent.getContext(),friendsList);
        listeFriends.setAdapter(adapter);

        listeFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                ExchangePOST request=new ExchangePOST(Integer.parseInt(AccountSingleton.getInstance().getIdUser()),Integer.parseInt(friendsList.get(position).getIdUser()),idPokemon, "send"); //a modifier si remerciement !
                nomJoueur=friendsList.get(position).getPseudo();
                ManagerPokemonService.getInstance().sendExchange(request, that);
            }
        });
    }

    public void exchangeSuccessful(AccountModel accountModel){
        AccountSingleton.getInstance().setListePokemon(accountModel.getListePokemon());
        AccountSingleton.getInstance().setListeCards(accountModel.getListeCards());

        activity.update();

        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ExchangeFragment f = ExchangeFragment.newInstance();
        showFragment(f);
    }
}