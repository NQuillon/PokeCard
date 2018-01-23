package nicolas.johan.iem.pokecard.vues.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import nicolas.johan.iem.pokecard.POSTrequest;
import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.FriendsAdapter;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.vues.Accueil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment {
    List<FriendAccount> friendsList;
    View parent;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_friends, container, false);



        Call<List<FriendAccount>> friends = PokemonApp.getPokemonService().getFriends(AccountSingleton.getInstance().getIdUser());

        friends.enqueue(new Callback<List<FriendAccount>>() {
            @Override
            public void onResponse(Call<List<FriendAccount>> call, Response<List<FriendAccount>> response) {
                if (response.isSuccessful()) {
                     friendsList = response.body();
                    refresh();
                }
            }

            @Override
            public void onFailure(Call<List<FriendAccount>> call, Throwable t) {
                Toast.makeText(parent.getContext(), "Echec du chargement des amis...", Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fb_exchange=(FloatingActionButton) parent.findViewById(R.id.add_friend);
        fb_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Pseudo de l'ami à ajouter :");

                final EditText input = new EditText(parent.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(parent.getContext(), "Requete envoyee", Toast.LENGTH_SHORT).show();

                        Call<FriendAccount> addfriend = PokemonApp.getPokemonService().addFriendByPseudo(input.getText().toString());
                        addfriend.enqueue(new Callback<FriendAccount>() {
                            @Override
                            public void onResponse(Call<FriendAccount> call, Response<FriendAccount> response) {
                                if(response.isSuccessful()){
                                    refresh();
                                }
                            }

                            @Override
                            public void onFailure(Call<FriendAccount> call, Throwable t) {
                                Toast.makeText(parent.getContext(), "Impossible d'ajouter cet ami", Toast.LENGTH_LONG).show();
                            }
                        });




                        refresh();
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


        return parent;

    }

    public void refresh(){
        ListView listeFriends=(ListView) parent.findViewById(R.id.listFriends);
        FriendsAdapter adapter=new FriendsAdapter(parent.getContext(),friendsList);
        listeFriends.setAdapter(adapter);
    }

    public static FriendsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}