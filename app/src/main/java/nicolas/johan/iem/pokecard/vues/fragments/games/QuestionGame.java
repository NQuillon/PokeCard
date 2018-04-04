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

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.Model.GetResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.Model.PostResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.Model.QuestionGameModel;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;

public class QuestionGame extends BaseFragment implements webServiceInterface {
    List<FriendAccount> friendsList;
    View parent;
    List<QuestionGameModel> listeQuestions;
    int numQuestion = -1;
    TextView tvQuestion;
    TextView numQuestionTv;
    Button btnTrue;
    Button btnFalse;
    ProgressBar progress;
    int correctAnswers = 0;

    public QuestionGame() {
        // Required empty public constructor
    }

    public static QuestionGame newInstance(Bundle data) {

        QuestionGame fragment = new QuestionGame();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent = inflater.inflate(R.layout.fragment_question_game, container, false);
        Bundle data = getArguments();
        String idCategory = data.getString("category");

        tvQuestion = (TextView) parent.findViewById(R.id.question_game);
        btnTrue = (Button) parent.findViewById(R.id.trueBtn);
        btnFalse = (Button) parent.findViewById(R.id.falseBtn);
        progress = (ProgressBar) parent.findViewById(R.id.progress_game);
        numQuestionTv = (TextView) parent.findViewById(R.id.numQuestion);

        btnTrue.setEnabled(false);
        btnFalse.setEnabled(false);

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTrue.setEnabled(false);
                btnFalse.setEnabled(false);
                if (listeQuestions.get(numQuestion).getCorrect().equals("True")) {
                    btnTrue.setBackgroundResource(R.drawable.round_button_true);
                    correctAnswers++;
                } else {
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
                                    showQuestion(listeQuestions);
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
                if (listeQuestions.get(numQuestion).getCorrect().equals("False")) {
                    btnFalse.setBackgroundResource(R.drawable.round_button_true);
                    correctAnswers++;
                } else {
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
                                    showQuestion(listeQuestions);
                                }
                            });


                        }
                    }
                };

                splashTread.start();
            }
        });

        String category = data.getString("category");
        ManagerPokemonService.getInstance().getQuestions(category, this);

        return parent;

    }

    public void showQuestion(List<QuestionGameModel> listQuestions) {
        listeQuestions = listQuestions;
        btnTrue.setEnabled(true);
        btnFalse.setEnabled(true);
        if (numQuestion >= 9) {
            //afficher les resultats
            Toast.makeText(activity, "Chargement des résultats en cours...", Toast.LENGTH_LONG).show();
            btnTrue.setEnabled(false);
            btnFalse.setEnabled(false);

            activity.clearBackstack();
            PostResultQuizzModel tmp = new PostResultQuizzModel(AccountSingleton.getInstance().getIdUser(), correctAnswers);
            ManagerPokemonService.getInstance().getResults(tmp, this);

        } else {
            numQuestion++;
            numQuestionTv.setText(numQuestion + 1 + "/10");
            progress.setProgress(numQuestion * 10 + 10);
        }
        tvQuestion.setText(listeQuestions.get(numQuestion).getQuestion());
    }

    public void showResults(GetResultQuizzModel results) {
        Bundle data = new Bundle();
        data.putInt("correctAnswers", correctAnswers);
        data.putString("message", results.getMessage());
        data.putString("img", results.getImg());
        data.putInt("pokeCoinsWin", results.getPokeCoinsWin());

        try {
            data.putString("cardsWin1", results.getCardsWin().get(0).getUrlPicture());
            data.putString("cardsWin2", results.getCardsWin().get(1).getUrlPicture());
            data.putString("cardsWin3", results.getCardsWin().get(2).getUrlPicture());
            data.putString("cardsWin4", results.getCardsWin().get(3).getUrlPicture());
            data.putString("cardsWin5", results.getCardsWin().get(4).getUrlPicture());
        } catch (Exception e) {
        }
        try {
            data.putString("idCardsWin1", results.getCardsWin().get(0).getId());
            data.putString("idCardsWin2", results.getCardsWin().get(1).getId());
            data.putString("idCardsWin3", results.getCardsWin().get(2).getId());
            data.putString("idCardsWin4", results.getCardsWin().get(3).getId());
            data.putString("idCardsWin5", results.getCardsWin().get(4).getId());
        } catch (Exception e) {
        }

        Fragment f = (Fragment) ResultsGame.newInstance(data);
        showFragment(f);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {
        Toast.makeText(activity, "Une erreur est survenue. Veuillez réessayer dans quelques instants", Toast.LENGTH_LONG).show();
    }
}