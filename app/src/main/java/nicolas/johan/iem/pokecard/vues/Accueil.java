package nicolas.johan.iem.pokecard.vues;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Model.MeteoModel;
import nicolas.johan.iem.pokecard.vues.fragments.AllPokemonsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.FriendsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.PokedexFragment;
import nicolas.johan.iem.pokecard.vues.fragments.ScanFragment;
import nicolas.johan.iem.pokecard.vues.fragments.SettingsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.StoreFragment;
import nicolas.johan.iem.pokecard.vues.fragments.exchange.ExchangeFragment;
import nicolas.johan.iem.pokecard.vues.fragments.games.GameFragment;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;

public class Accueil extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, webServiceInterface {

    TextView pseudo_header;
    TextView pokecoin;
    TextView nbCards;
    ImageView profileImage;
    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;
    NavigationView navigationView;
    Toolbar toolbar;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        init();

        //NFC
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            //nfc not support your device.
            return;
        }
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString("idUser", AccountSingleton.getInstance().getIdUser());
        prefsEdit.apply();

        showFragment(PokedexFragment.newInstance());

        update();

        profileImage = (ImageView) header.findViewById(R.id.profileImage);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        header.setBackgroundColor(Color.rgb(246, 186, 44));
        pseudo_header = (TextView) header.findViewById(R.id.pseudo_header);
        pseudo_header.setText(AccountSingleton.getInstance().getPseudo());
        pokecoin = (TextView) header.findViewById(R.id.pokecoin_header);
        nbCards = (TextView) header.findViewById(R.id.nbCards_header);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accueil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pokedex) {
            clearBackstack();
            showFragment(PokedexFragment.newInstance());
            getSupportActionBar().setTitle("Mon Pokédex");
        } else if (id == R.id.nav_allpokemons) {
            clearBackstack();
            showFragment(AllPokemonsFragment.newInstance());
            getSupportActionBar().setTitle("Tous les pokémons");
        } else if (id == R.id.nav_echanger) {
            clearBackstack();
            showFragment(ExchangeFragment.newInstance());
            getSupportActionBar().setTitle("Échanger");
        } else if (id == R.id.nav_jeux) {
            clearBackstack();
            showFragment(GameFragment.newInstance());
            getSupportActionBar().setTitle("Jeux");
        } else if (id == R.id.nav_boutique) {
            clearBackstack();
            showFragment(StoreFragment.newInstance());
            getSupportActionBar().setTitle("Boutique");
        } else if (id == R.id.nav_amis) {
            clearBackstack();
            showFragment(FriendsFragment.newInstance());
            getSupportActionBar().setTitle("Mes amis");
        } else if (id == R.id.nav_params) {
            clearBackstack();
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new SettingsFragment())
                    .commit();
            getSupportActionBar().setTitle("Paramètres");
        } else if (id == R.id.nav_deco) {
            clearBackstack();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefsEdit = prefs.edit();
            prefsEdit.putBoolean("keepConnected", false);
            prefsEdit.apply();
            finish();
            Intent i = new Intent(Accueil.this, LoginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.in, R.anim.out);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void update() {
        ManagerPokemonService.getInstance().getUserAccount(AccountSingleton.getInstance().getIdUser(), this);
        ManagerPokemonService.getInstance().getMeteo(AccountSingleton.getInstance().getIdUser(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        getTagInfo(intent);
    }

    private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        //NdefMessage ndefMessage = ndef.getCachedNdefMessage();
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            System.out.println(message);
            ndef.close();
            clearBackstack();
            Bundle data = new Bundle();
            data.putString("message", message);
            showFragment(ScanFragment.newInstance(data));
            getSupportActionBar().setTitle("Scan NFC");
        } catch (Exception e) {

        }
    }

    @Override
    public void onSuccess() {

        profileImage = (ImageView) header.findViewById(R.id.profileImage);
        if (URLUtil.isValidUrl(AccountSingleton.getInstance().getPicture())) {
            Picasso.with(getBaseContext()).load(AccountSingleton.getInstance().getPicture()).into(profileImage);
        } else {
            byte[] imageBytes = Base64.decode(AccountSingleton.getInstance().getPicture(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            profileImage.setImageBitmap(decodedImage);
        }

        pseudo_header.setText(AccountSingleton.getInstance().getPseudo());
        if (AccountSingleton.getInstance().getListeCards().get(0).equals("")) {
            nbCards.setText("0");
        } else {
            nbCards.setText("" + AccountSingleton.getInstance().getListeCards().size());
        }
        pokecoin.setText("" + AccountSingleton.getInstance().getPokeCoin());
    }

    @Override
    public void onFailure() {
        Toast.makeText(getBaseContext(), "Impossible de mettre à jour le compte", Toast.LENGTH_SHORT).show();
    }

    public void onMeteo(MeteoModel meteoModel) {
        ImageView imgMeteo = (ImageView) findViewById(R.id.imgMeteo);
        TextView tempMeteo = (TextView) findViewById(R.id.tempMeteo);
        tempMeteo.setText(meteoModel.getTemp());
        Picasso.with(getBaseContext()).load(meteoModel.getImg()).into(imgMeteo);
    }
}
