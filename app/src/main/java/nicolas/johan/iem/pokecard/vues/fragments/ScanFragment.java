package nicolas.johan.iem.pokecard.vues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;

public class ScanFragment extends BaseFragment implements webServiceInterface {
    View parent;
    ImageView cardTv;
    public ScanFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_scan, container, false);

        Bundle data=getArguments();
        String msg=data.getString("message");

        cardTv=parent.findViewById(R.id.cardNFC);

        ManagerPokemonService.getInstance().addCardNFC(msg, this);

        return parent;
    }

    public static ScanFragment newInstance(Bundle data) {
        ScanFragment fragment = new ScanFragment();
        fragment.setArguments(data);
        return fragment;
    }

    public void afficheCard(Card card){
        try{
            Picasso.with(activity).load(card.getUrlPicture()).into(cardTv);
        }catch(Exception e){}
    }

    @Override
    public void onSuccess() {
            activity.update();
            Toast.makeText(activity, "Carte ajouté à votre pokedex !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {

    }
}