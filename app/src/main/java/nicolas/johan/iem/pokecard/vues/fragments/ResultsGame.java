package nicolas.johan.iem.pokecard.vues.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountModel;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.GetResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.PostResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.QuestionGameModel;
import nicolas.johan.iem.pokecard.vues.LoginActivity;
import nicolas.johan.iem.pokecard.vues.SplashScreen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsGame extends BaseFragment {
    View parent;
    boolean card1open=false;
    boolean card2open=false;
    boolean card3open=false;
    boolean card4open=false;
    boolean card5open=false;

    public ResultsGame() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_results_game, container, false);
        final Bundle data=getArguments();

        TextView messageResult=(TextView) parent.findViewById(R.id.message_result);
        messageResult.setText(data.getString("message"));

        TextView correctAnswers=(TextView) parent.findViewById(R.id.correctAnswers);
        correctAnswers.setText("avec "+data.getInt("correctAnswers")+" réponses correctes !");

        TextView pokeCoinsWin=(TextView) parent.findViewById(R.id.pokecoinswin);
        pokeCoinsWin.setText(""+data.getInt("pokeCoinsWin"));

        ImageView imageView = parent.findViewById(R.id.imgResult);
        final GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(data.getString("img")).into(imageViewTarget);


        //Maj du Compte
        Call<AccountModel> request = PokemonApp.getPokemonService().majAccount(AccountSingleton.getInstance().getIdUser());
        request.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                if(response.isSuccessful()){
                    AccountModel tmpAccount=response.body();
                    AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());
                    AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                    AccountSingleton.getInstance().setIdAccount(tmpAccount.getIdAccount());
                    AccountSingleton.getInstance().setIdUser(tmpAccount.getIdUser());
                    AccountSingleton.getInstance().setPicture(tmpAccount.getPicture());
                    AccountSingleton.getInstance().setPokeCoin(tmpAccount.getPokeCoin());
                    AccountSingleton.getInstance().setPseudo(tmpAccount.getPseudo());
                    activity.update();
                }else{
                    Toast.makeText(activity, "Impossible de mettre à jour le compte", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Toast.makeText(activity, "Impossible de mettre à jour le compte", Toast.LENGTH_SHORT).show();
            }
        });



        final ImageView imgCard1=parent.findViewById(R.id.cardwin1);
        if(data.get("cardsWin1")!=null){

            imgCard1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!card1open){
                        Picasso.with(getContext()).load(data.get("cardsWin1").toString()).into(imgCard1);

                        if(!AccountSingleton.getInstance().getListeCards().contains(data.getString("idCardsWin1"))){
                            TextView tmpNew=parent.findViewById(R.id.newCard1);
                            tmpNew.setVisibility(View.VISIBLE);
                        }

                        card1open=true;
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp=(ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url=data.get("cardsWin1").toString();
                        url=url.replace(".png","_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        }else{
            imgCard1.setImageResource(R.mipmap.no_card_win);
        }

        final ImageView imgCard2=parent.findViewById(R.id.cardwin2);
        if(data.get("cardsWin2")!=null){

            imgCard2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!card2open){
                        Picasso.with(getContext()).load(data.get("cardsWin2").toString()).into(imgCard2);

                        if(!AccountSingleton.getInstance().getListeCards().contains(data.getString("idCardsWin2"))){
                            TextView tmpNew=parent.findViewById(R.id.newCard2);
                            tmpNew.setVisibility(View.VISIBLE);
                        }
                        card2open=true;
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp=(ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url=data.get("cardsWin2").toString();
                        url=url.replace(".png","_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        }else{
            imgCard2.setImageResource(R.mipmap.no_card_win);
        }

        final ImageView imgCard3=parent.findViewById(R.id.cardwin3);
        if(data.get("cardsWin3")!=null){

            imgCard3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!card3open){
                        Picasso.with(getContext()).load(data.get("cardsWin3").toString()).into(imgCard3);

                        if(!AccountSingleton.getInstance().getListeCards().contains(data.getString("idCardsWin3"))){
                            TextView tmpNew=parent.findViewById(R.id.newCard3);
                            tmpNew.setVisibility(View.VISIBLE);
                        }
                        card3open=true;
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp=(ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url=data.get("cardsWin3").toString();
                        url=url.replace(".png","_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        }else{
            imgCard3.setImageResource(R.mipmap.no_card_win);
        }

        final ImageView imgCard4=parent.findViewById(R.id.cardwin4);
        if(data.get("cardsWin4")!=null){

            imgCard4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!card4open){
                        Picasso.with(getContext()).load(data.get("cardsWin4").toString()).into(imgCard4);
                        if(!AccountSingleton.getInstance().getListeCards().contains(data.getString("idCardsWin4"))){
                            TextView tmpNew=parent.findViewById(R.id.newCard4);
                            tmpNew.setVisibility(View.VISIBLE);
                        }
                        card4open=true;
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp=(ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url=data.get("cardsWin4").toString();
                        url=url.replace(".png","_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        }else{
            imgCard4.setImageResource(R.mipmap.no_card_win);
        }

        final ImageView imgCard5=parent.findViewById(R.id.cardwin5);
        if(data.get("cardsWin5")!=null){

            imgCard5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!card5open){
                        Picasso.with(getContext()).load(data.get("cardsWin5").toString()).into(imgCard5);
                        if(!AccountSingleton.getInstance().getListeCards().contains(data.getString("idCardsWin5"))){
                            TextView tmpNew=parent.findViewById(R.id.newCard5);
                            tmpNew.setVisibility(View.VISIBLE);
                        }
                        card5open=true;
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp=(ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url=data.get("cardsWin5").toString();
                        url=url.replace(".png","_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        }else{
            imgCard5.setImageResource(R.mipmap.no_card_win);
        }




        return parent;

    }

    public static ResultsGame newInstance(Bundle data) {
        ResultsGame fragment = new ResultsGame();
        fragment.setArguments(data);
        return fragment;
    }


}