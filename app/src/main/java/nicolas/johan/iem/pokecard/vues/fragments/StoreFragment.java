package nicolas.johan.iem.pokecard.vues.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.GridViewAlertCardAdapter;
import nicolas.johan.iem.pokecard.adapter.StoreAdapter;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.pojo.Model.BuyModel;
import nicolas.johan.iem.pokecard.pojo.StoreItem;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;

public class StoreFragment extends BaseFragment implements webServiceInterface {
    View parent;
    View customLayout;
    List<StoreItem> items;
    FrameLayout pokecoins;
    List<Card> cardsWin;
    TextView mypokecoinValue;
    GridView gridView;
    StoreFragment that;

    public StoreFragment() {
        // Required empty public constructor
    }

    public static StoreFragment newInstance() {

        Bundle args = new Bundle();

        StoreFragment fragment = new StoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent = inflater.inflate(R.layout.fragment_store, container, false);
        mypokecoinValue = parent.findViewById(R.id.nbMyPokecoin);
        mypokecoinValue.setText("" + AccountSingleton.getInstance().getPokeCoin());
        pokecoins = parent.findViewById(R.id.myPokecoinFrame);
        that = this;

        ManagerPokemonService.getInstance().getListBoosters(this);

        return parent;
    }

    public void refresh(List<StoreItem> storeItems) {
        items = storeItems;
        StoreAdapter adapter = new StoreAdapter(parent.getContext(), storeItems);
        gridView = (GridView) parent.findViewById(R.id.gridStore);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final FrameLayout btn = (FrameLayout) view.findViewById(R.id.btnStore);
                if (items.get(position).getPrice() <= AccountSingleton.getInstance().getPokeCoin()) {
                    gridView.setEnabled(false);
                    btn.setBackgroundResource(R.drawable.round_button_true);

                    //AlertDialog
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Cartes acquises");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btn.setBackgroundResource(R.drawable.round_button_null);
                            dialog.dismiss();
                        }
                    });

                    customLayout = getActivity().getLayoutInflater().inflate(R.layout.dialog_exchange, null);

                    builder.setView(customLayout);

                    final AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();

                    Toast.makeText(activity, "Chargement en cours", Toast.LENGTH_SHORT).show();

                    int tmpPokecoin = AccountSingleton.getInstance().getPokeCoin() - items.get(position).getPrice();
                    mypokecoinValue.setText("" + tmpPokecoin);
                    BuyModel tmp = new BuyModel(AccountSingleton.getInstance().getIdUser(), items.get(position).getNbCartes());
                    ManagerPokemonService.getInstance().buyBooster(tmp, that);
                } else {
                    btn.setBackgroundResource(R.drawable.round_button_false);
                    pokecoins.setBackgroundResource(R.drawable.round_button_false);
                    Animation anim = AnimationUtils.loadAnimation(activity, R.anim.zoom_in);
                    Animation anim2 = AnimationUtils.loadAnimation(activity, R.anim.shake);
                    btn.startAnimation(anim2);
                    pokecoins.startAnimation(anim);
                    Thread th = new Thread() {
                        @Override
                        public void run() {
                            try {
                                synchronized (this) {
                                    wait(800);
                                }
                            } catch (InterruptedException e) {
                            } finally {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Animation anim = AnimationUtils.loadAnimation(activity, R.anim.zoom_out);
                                        pokecoins.startAnimation(anim);
                                        btn.setBackgroundResource(R.drawable.round_button_null);
                                        pokecoins.setBackgroundResource(R.drawable.round_button_null);
                                    }
                                });
                            }
                        }
                    };
                    th.start();
                }
            }
        });
    }

    public void onBuy(List<Card> newCards) {
        cardsWin = newCards;
        GridView gv_cardsWin = (GridView) customLayout.findViewById(R.id.gridview_cards);
        GridViewAlertCardAdapter cardAdapter = new GridViewAlertCardAdapter(getActivity(), cardsWin);
        gv_cardsWin.setAdapter(cardAdapter);
        gridView.setEnabled(true);
    }

    @Override
    public void onSuccess() {
        activity.update();
    }

    @Override
    public void onFailure() {

    }
}