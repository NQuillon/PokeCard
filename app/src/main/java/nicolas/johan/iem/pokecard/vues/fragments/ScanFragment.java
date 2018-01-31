package nicolas.johan.iem.pokecard.vues.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.tech.Ndef;
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

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.GridViewAlertCardAdapter;
import nicolas.johan.iem.pokecard.adapter.StoreAdapter;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.BuyModel;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.pojo.StoreItem;
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

        NfcAdapter nfc=NfcAdapter.getDefaultAdapter(activity);

        if (nfc == null) {
            Toast.makeText(activity, "Cet appareil ne supporte pas le NFC", Toast.LENGTH_LONG).show();
        }

        if (!nfc.isEnabled()) {
            Toast.makeText(activity, "NFC désactivé", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "NFC activé", Toast.LENGTH_SHORT).show();
        }

        setupForegroundDispatch(activity,nfc);

        return parent;
    }

    public static ScanFragment newInstance() {

        ScanFragment fragment = new ScanFragment();
        return fragment;
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity, activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);

        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);
    }

}