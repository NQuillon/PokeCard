package nicolas.johan.iem.pokecard.vues.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.FriendsAdapter;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.Model.ManageFriendsModel;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.getFriendsInterface;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment implements webServiceInterface, getFriendsInterface {
    List<FriendAccount> friendsList;
    ProgressBar loading;
    View parent;
    ListView listeFriends;
    FriendsAdapter adapter;
    FriendsFragment that;

    public FriendsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_friends, container, false);
        loading=parent.findViewById(R.id.loading_Friends);
        that = this;

        listeFriends=(ListView) parent.findViewById(R.id.listFriends);

        listeFriends.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> par, View view, final int position, long id) {
                final String tmpPseudo=friendsList.get(position).getPseudo();

                AlertDialog.Builder builder= new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Suppression d'un ami")
                        .setMessage(tmpPseudo+" ne sera plus dans votre liste d'amis.\nVous ne pourrez plus lui envoyer de cartes mais il pourra toujours vous en envoyer.")
                        .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ManageFriendsModel tmp=new ManageFriendsModel(friendsList.get(position).getPseudo());
                                ManagerPokemonService.getInstance().delFriend(tmp, that);
                            }
                        })
                        .setNegativeButton("Garder dans mes amis", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return true;
            }
        });

        ManagerPokemonService.getInstance().getFriends(this, this);

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
                        loading.setVisibility(View.VISIBLE);
                        ManageFriendsModel tmp = new ManageFriendsModel(input.getText().toString());
                        ManagerPokemonService.getInstance().addFriend(tmp, that);
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

    public void refresh(List<FriendAccount> list){
        friendsList = list;
        listeFriends=(ListView) parent.findViewById(R.id.listFriends);
        adapter=new FriendsAdapter(parent.getContext(), friendsList);
        listeFriends.setAdapter(adapter);
    }

    public static FriendsFragment newInstance() {
        Bundle args = new Bundle();
        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onAddFriend(String pseudo){
        Toast.makeText(parent.getContext(), pseudo + " a été ajouté à vos amis", Toast.LENGTH_LONG).show();
    }

    public void onDelFriend(String pseudo){
        Toast.makeText(parent.getContext(), pseudo + " n'est plus votre ami", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        Toast.makeText(parent.getContext(), "Une erreur est survenue", Toast.LENGTH_LONG).show();
    }
}