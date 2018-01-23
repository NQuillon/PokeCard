package nicolas.johan.iem.pokecard.vues.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import javax.xml.datatype.Duration;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.FriendsExchangeAdapter;
import nicolas.johan.iem.pokecard.pojo.AccountModel;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.ExchangePOST;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.QuestionGameModel;
import nicolas.johan.iem.pokecard.vues.LoginActivity;
import nicolas.johan.iem.pokecard.vues.SplashScreen;
import nicolas.johan.iem.pokecard.vues.fragments.exchange.ExchangeFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionGame extends BaseFragment {
    List<FriendAccount> friendsList;
    View parent;
    List<QuestionGameModel> listeQuestions;
    int numQuestion=-1;
    TextView tvQuestion;
    TextView numQuestionTv;
    Button btnTrue;
    Button btnFalse;
    ProgressBar progress;
    int correctAnswers=0;

    public QuestionGame() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent=inflater.inflate(R.layout.fragment_question_game, container, false);
        Bundle data=getArguments();
        String idCategory=data.getString("category");

        tvQuestion=(TextView) parent.findViewById(R.id.question_game);
        btnTrue=(Button) parent.findViewById(R.id.trueBtn);
        btnFalse=(Button) parent.findViewById(R.id.falseBtn);
        progress=(ProgressBar) parent.findViewById(R.id.progress_game);
        numQuestionTv=(TextView) parent.findViewById(R.id.numQuestion);

        btnTrue.setEnabled(false);
        btnFalse.setEnabled(false);

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTrue.setEnabled(false);
                btnFalse.setEnabled(false);
                if(listeQuestions.get(numQuestion).getCorrect().equals("True")){
                    btnTrue.setBackgroundResource(R.drawable.round_button_true);
                    correctAnswers++;
                }
                else{
                    btnTrue.setBackgroundResource(R.drawable.round_button_false);
                    btnFalse.setBackgroundResource(R.drawable.round_button_true);
                }
                Thread splashTread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(1500);
                            }
                        } catch (InterruptedException e) {
                        } finally {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnTrue.setBackgroundResource(R.drawable.round_button_null);
                                    btnFalse.setBackgroundResource(R.drawable.round_button_null);
                                    showQuestion();
                                }
                            });


                        }
                    }
                };

                splashTread.start();

            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTrue.setEnabled(false);
                btnFalse.setEnabled(false);
                if(listeQuestions.get(numQuestion).getCorrect().equals("False")){
                    btnFalse.setBackgroundResource(R.drawable.round_button_true);
                    correctAnswers++;
                }
                else{
                    btnTrue.setBackgroundResource(R.drawable.round_button_true);
                    btnFalse.setBackgroundResource(R.drawable.round_button_false);
                }
                Thread splashTread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(1500);
                            }
                        } catch (InterruptedException e) {
                        } finally {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnTrue.setBackgroundResource(R.drawable.round_button_null);
                                    btnFalse.setBackgroundResource(R.drawable.round_button_null);
                                    showQuestion();
                                }
                            });


                        }
                    }
                };

                splashTread.start();
            }
        });


        Call<List<QuestionGameModel>> questions = PokemonApp.getPokemonService().getQuestionsFromCategory(data.getString("category"));

        questions.enqueue(new Callback<List<QuestionGameModel>>() {
            @Override
            public void onResponse(Call<List<QuestionGameModel>> call, Response<List<QuestionGameModel>> response) {
                if(response.isSuccessful()){
                    listeQuestions=response.body();
                    showQuestion();
                }
            }

            @Override
            public void onFailure(Call<List<QuestionGameModel>> call, Throwable t) {

            }
        });



        return parent;

    }

    public static QuestionGame newInstance(Bundle data) {
        
        QuestionGame fragment = new QuestionGame();
        fragment.setArguments(data);
        return fragment;
    }

    public void showQuestion(){
        btnTrue.setEnabled(true);
        btnFalse.setEnabled(true);
        if(numQuestion>=9){
            //afficher les resultats
            Toast.makeText(activity, "Réponses justes : "+correctAnswers, Toast.LENGTH_LONG).show();
            btnTrue.setEnabled(false);
            btnFalse.setEnabled(false);
        }else{
            numQuestion++;
            numQuestionTv.setText(numQuestion+1+"/10");
            progress.setProgress(numQuestion*10+10);
        }
        tvQuestion.setText(listeQuestions.get(numQuestion).getQuestion());
    }


}