package nicolas.johan.iem.pokecard.vues;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URL;

import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.vues.fragments.AllPokemonsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.exchange.ExchangeFragment;
import nicolas.johan.iem.pokecard.vues.fragments.FriendsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.GameFragment;
import nicolas.johan.iem.pokecard.POSTrequest;
import nicolas.johan.iem.pokecard.vues.fragments.PokedexFragment;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.vues.fragments.SettingsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.StoreFragment;

public class Accueil extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView pseudo_header;
    TextView pokecoin;
    TextView nbCards;
    ImageView profileImage;
    Bitmap bitprofile;
    ImageView modify_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        showFragment(PokedexFragment.newInstance());

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        header.setBackgroundColor(Color.rgb(246, 186, 44));
        pseudo_header = (TextView) header.findViewById(R.id.pseudo_header);
        pseudo_header.setText(AccountSingleton.getInstance().getPseudo());
        pokecoin = (TextView) header.findViewById(R.id.pokecoin_header);
        pokecoin.setText(""+ AccountSingleton.getInstance().getPokeCoin());

        nbCards = (TextView) header.findViewById(R.id.nbCards_header);
        nbCards.setText(""+ AccountSingleton.getInstance().getListeCards().size());


        modify_header=(ImageView) header.findViewById(R.id.modify_header);

        profileImage = (ImageView) header.findViewById(R.id.profileImage);





        View.OnClickListener modify_listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);
                builder.setTitle("Entrez votre nouveau pseudo");

                final EditText input = new EditText(Accueil.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                input.setText(AccountSingleton.getInstance().getPseudo());
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccountSingleton.getInstance().setPseudo(input.getText().toString());
                        pseudo_header.setText(input.getText().toString());
                        try{
                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("idUser", AccountSingleton.getInstance().getIdUser());
                            jsonParam.put("pseudo", input.getText().toString());
                            new POSTrequest().execute("option/editPseudo",jsonParam);
                        }catch (Exception e){

                        }

                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        };

        pseudo_header.setOnClickListener(modify_listener);
        modify_header.setOnClickListener(modify_listener);

        new chargeProfilePicture().execute();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private class chargeProfilePicture extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL imageURL = new URL(AccountSingleton.getInstance().getPicture().toString());
                bitprofile = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            }catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            profileImage.setImageBitmap(bitprofile);
        }
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

        /*if (id == R.id.action_settings) {
            return true;
        }*/

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
            showFragment(SettingsFragment.newInstance());
            getSupportActionBar().setTitle("Paramètres");
        } else if (id == R.id.nav_deco) {
            clearBackstack();
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
        nbCards.setText(""+ AccountSingleton.getInstance().getListeCards().size());
    }

}
