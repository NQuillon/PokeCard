package nicolas.johan.iem.pokecard.vues.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.FriendsAdapter;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment {
    List<FriendAccount> friendsList;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate=inflater.inflate(R.layout.fragment_friends, container, false);



        Call<List<FriendAccount>> friends = PokemonApp.getPokemonService().getFriends(AccountSingleton.getInstance().getIdUser());

        friends.enqueue(new Callback<List<FriendAccount>>() {
            @Override
            public void onResponse(Call<List<FriendAccount>> call, Response<List<FriendAccount>> response) {
                if (response.isSuccessful()) {
                     friendsList = response.body();

                    ListView listeFriends=(ListView) inflate.findViewById(R.id.listFriends);
                    FriendsAdapter adapter=new FriendsAdapter(inflate.getContext(),friendsList);
                    listeFriends.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<FriendAccount>> call, Throwable t) {
                Toast.makeText(inflate.getContext(), "Echec du chargement des amis...", Toast.LENGTH_SHORT).show();
            }
        });





        return inflate;

    }

    public static FriendsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}