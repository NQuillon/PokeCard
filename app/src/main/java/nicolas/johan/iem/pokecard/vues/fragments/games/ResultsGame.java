package nicolas.johan.iem.pokecard.vues.fragments.games;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.vues.fragments.BaseFragment;

public class ResultsGame extends BaseFragment {
    View parent;
    boolean card1open = false;
    boolean card2open = false;
    boolean card3open = false;
    boolean card4open = false;
    boolean card5open = false;
    ImageView imgCard1;
    ImageView imgCard2;
    ImageView imgCard3;
    ImageView imgCard4;
    ImageView imgCard5;
    TextView allOpenBtn;
    ArrayList<String> oldCards;

    public ResultsGame() {
        // Required empty public constructor
    }

    public static ResultsGame newInstance(Bundle data) {
        ResultsGame fragment = new ResultsGame();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent = inflater.inflate(R.layout.fragment_results_game, container, false);

        oldCards = AccountSingleton.getInstance().getListeCards();

        activity.update();

        allOpenBtn = parent.findViewById(R.id.allOpenBtn);
        final Bundle data = getArguments();

        final TextView messageResult = (TextView) parent.findViewById(R.id.message_result);
        messageResult.setText(data.getString("message"));

        TextView correctAnswers = (TextView) parent.findViewById(R.id.correctAnswers);
        correctAnswers.setText("" + data.getInt("correctAnswers"));

        final TextView totalQuestions = parent.findViewById(R.id.totalQuestions);

        switch (data.getInt("correctAnswers")) {
            case 1:
            case 2:
            case 3:
            case 4:
                correctAnswers.setTextColor(Color.parseColor("#e81b1b"));
                totalQuestions.setTextColor(Color.parseColor("#e81b1b"));
                messageResult.setTextColor(Color.parseColor("#e81b1b"));
                break;
            case 5:
            case 6:
            case 7:
                correctAnswers.setTextColor(Color.parseColor("#394eef"));
                totalQuestions.setTextColor(Color.parseColor("#394eef"));
                messageResult.setTextColor(Color.parseColor("#394eef"));
                break;
            case 8:
            case 9:
            case 10:
                correctAnswers.setTextColor(Color.parseColor("#2cf755"));
                totalQuestions.setTextColor(Color.parseColor("#2cf755"));
                messageResult.setTextColor(Color.parseColor("#2cf755"));
                break;
            default:
                //
        }

        TextView pokeCoinsWin = (TextView) parent.findViewById(R.id.pokecoinswin);
        pokeCoinsWin.setText("" + data.getInt("pokeCoinsWin"));

        ImageView imageView = parent.findViewById(R.id.imgResult);
        final GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(data.getString("img")).into(imageViewTarget);


        imgCard1 = parent.findViewById(R.id.cardwin1);
        if (data.get("cardsWin1") != null) {

            imgCard1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!card1open) {
                        Picasso.with(getContext()).load(data.get("cardsWin1").toString()).into(imgCard1);

                        if (!oldCards.contains(data.getString("idCardsWin1"))) {
                            TextView tmpNew = parent.findViewById(R.id.newCard1);
                            tmpNew.setVisibility(View.VISIBLE);
                        }
                        card1open = true;
                        verifOpen();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp = (ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url = data.get("cardsWin1").toString();
                        url = url.replace(".png", "_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        } else {
            imgCard1.setImageResource(R.mipmap.no_card_win);
            card1open = true;
            verifOpen();
        }

        imgCard2 = parent.findViewById(R.id.cardwin2);
        if (data.get("cardsWin2") != null) {

            imgCard2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!card2open) {
                        Picasso.with(getContext()).load(data.get("cardsWin2").toString()).into(imgCard2);

                        if (!oldCards.contains(data.getString("idCardsWin2"))) {
                            TextView tmpNew = parent.findViewById(R.id.newCard2);
                            tmpNew.setVisibility(View.VISIBLE);
                        }
                        card2open = true;
                        verifOpen();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp = (ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url = data.get("cardsWin2").toString();
                        url = url.replace(".png", "_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        } else {
            imgCard2.setImageResource(R.mipmap.no_card_win);
            card2open = true;
            verifOpen();
        }

        imgCard3 = parent.findViewById(R.id.cardwin3);
        if (data.get("cardsWin3") != null) {

            imgCard3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!card3open) {
                        Picasso.with(getContext()).load(data.get("cardsWin3").toString()).into(imgCard3);

                        if (!oldCards.contains(data.getString("idCardsWin3"))) {
                            TextView tmpNew = parent.findViewById(R.id.newCard3);
                            tmpNew.setVisibility(View.VISIBLE);
                        }
                        card3open = true;
                        verifOpen();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp = (ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url = data.get("cardsWin3").toString();
                        url = url.replace(".png", "_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        } else {
            imgCard3.setImageResource(R.mipmap.no_card_win);
            card3open = true;
            verifOpen();
        }

        imgCard4 = parent.findViewById(R.id.cardwin4);
        if (data.get("cardsWin4") != null) {

            imgCard4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!card4open) {
                        Picasso.with(getContext()).load(data.get("cardsWin4").toString()).into(imgCard4);
                        if (!oldCards.contains(data.getString("idCardsWin4"))) {
                            TextView tmpNew = parent.findViewById(R.id.newCard4);
                            tmpNew.setVisibility(View.VISIBLE);
                        }
                        card4open = true;
                        verifOpen();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp = (ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url = data.get("cardsWin4").toString();
                        url = url.replace(".png", "_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        } else {
            imgCard4.setImageResource(R.mipmap.no_card_win);
            card4open = true;
            verifOpen();
        }

        imgCard5 = parent.findViewById(R.id.cardwin5);
        if (data.get("cardsWin5") != null) {

            imgCard5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!card5open) {
                        Picasso.with(getContext()).load(data.get("cardsWin5").toString()).into(imgCard5);
                        if (!oldCards.contains(data.getString("idCardsWin5"))) {
                            TextView tmpNew = parent.findViewById(R.id.newCard5);
                            tmpNew.setVisibility(View.VISIBLE);
                        }
                        card5open = true;
                        verifOpen();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = activity.getLayoutInflater().inflate(R.layout.detailzoom, null);
                        ImageView tmp = (ImageView) customLayout.findViewById(R.id.zoomimg);
                        String url = data.get("cardsWin5").toString();
                        url = url.replace(".png", "_hires.png");
                        Picasso.with(getContext()).load(url).into(tmp);
                        builder.setView(customLayout);
                        builder.show();
                    }
                }
            });
        } else {
            imgCard5.setImageResource(R.mipmap.no_card_win);
            card5open = true;
            verifOpen();
        }

        allOpenBtn = parent.findViewById(R.id.allOpenBtn);
        allOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allOpen();
                allOpenBtn.setEnabled(false);
                allOpenBtn.setVisibility(View.GONE);
            }
        });

        return parent;

    }

    public void allOpen() {
        if (!card1open) {
            imgCard1.performClick();
        }
        if (!card2open) {
            imgCard2.performClick();
        }
        if (!card3open) {
            imgCard3.performClick();
        }
        if (!card4open) {
            imgCard4.performClick();
        }
        if (!card5open) {
            imgCard5.performClick();
        }
    }

    public void verifOpen() {
        if (card1open && card2open && card3open && card4open && card5open) {
            allOpenBtn.setEnabled(false);
            allOpenBtn.setVisibility(View.GONE);
        }
    }


}