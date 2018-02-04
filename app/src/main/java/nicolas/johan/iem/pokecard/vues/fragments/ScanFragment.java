package nicolas.johan.iem.pokecard.vues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.pojo.CardNFC;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanFragment extends BaseFragment {
    View parent;


    public ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_scan, container, false);
        Bundle data=getArguments();
        String msg=data.getString("message");
        final ImageView cardTv=parent.findViewById(R.id.cardNFC);

        Call<Card> request= PokemonApp.getPokemonService().addCardFromNFC(new CardNFC(AccountSingleton.getInstance().getIdUser(),msg));
        request.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if(response.isSuccessful()){
                    try{
                        Picasso.with(activity).load(response.body().getUrlPicture()).into(cardTv);
                        activity.update();
                        Toast.makeText(activity, "Carte ajouté à votre pokedex !", Toast.LENGTH_SHORT).show();
                    }catch(Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {

            }
        });

        return parent;
    }

    public static ScanFragment newInstance(Bundle data) {
        ScanFragment fragment = new ScanFragment();
        fragment.setArguments(data);
        return fragment;
    }

}