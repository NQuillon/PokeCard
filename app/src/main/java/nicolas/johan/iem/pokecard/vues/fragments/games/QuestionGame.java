package nicolas.johan.iem.pokecard.vues.fragments.games;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.GetResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.PostResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.QuestionGameModel;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
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
                                wait(1000);
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
                    try {
                        listeQuestions = response.body();
                        showQuestion();
                    }catch(Exception e) {
                    }
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
            Toast.makeText(activity, "Chargement des résultats en cours...", Toast.LENGTH_LONG).show();
            btnTrue.setEnabled(false);
            btnFalse.setEnabled(false);


            PostResultQuizzModel tmp=new PostResultQuizzModel(AccountSingleton.getInstance().getIdUser(), correctAnswers);
            Call<GetResultQuizzModel> request = PokemonApp.getPokemonService().getResultsFromQuizz(tmp);
            request.enqueue(new Callback<GetResultQuizzModel>() {
                @Override
                public void onResponse(Call<GetResultQuizzModel> call, Response<GetResultQuizzModel> response) {
                    if(response.isSuccessful()) {
                        try {
                            GetResultQuizzModel tmp = response.body();

                            Bundle data = new Bundle();
                            data.putInt("correctAnswers", correctAnswers);
                            data.putString("message", tmp.getMessage());
                            data.putString("img", tmp.getImg());
                            data.putInt("pokeCoinsWin", tmp.getPokeCoinsWin());

                            try {
                                data.putString("cardsWin1", tmp.getCardsWin().get(0).getUrlPicture());
                                data.putString("cardsWin2", tmp.getCardsWin().get(1).getUrlPicture());
                                data.putString("cardsWin3", tmp.getCardsWin().get(2).getUrlPicture());
                                data.putString("cardsWin4", tmp.getCardsWin().get(3).getUrlPicture());
                                data.putString("cardsWin5", tmp.getCardsWin().get(4).getUrlPicture());
                            }catch (Exception e){

                            }
                            try {
                                data.putString("idCardsWin1", tmp.getCardsWin().get(0).getId());
                                data.putString("idCardsWin2", tmp.getCardsWin().get(1).getId());
                                data.putString("idCardsWin3", tmp.getCardsWin().get(2).getId());
                                data.putString("idCardsWin4", tmp.getCardsWin().get(3).getId());
                                data.putString("idCardsWin5", tmp.getCardsWin().get(4).getId());
                            }catch (Exception e){

                            }

                        Fragment f = (Fragment) ResultsGame.newInstance(data);
                        showFragment(f);
                    }catch(Exception e) {
                    }
                    }else{
                        Toast.makeText(activity, "Une erreur est survenue. Veuillez réessayer dans quelques instants", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<GetResultQuizzModel> call, Throwable t) {
                    Toast.makeText(activity, "Une erreur est survenue. Veuillez réessayer dans quelques instants", Toast.LENGTH_LONG).show();
                }
            });


        }else{
            numQuestion++;
            numQuestionTv.setText(numQuestion+1+"/10");
            progress.setProgress(numQuestion*10+10);
        }
        tvQuestion.setText(listeQuestions.get(numQuestion).getQuestion());
    }


}