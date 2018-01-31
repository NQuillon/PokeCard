package nicolas.johan.iem.pokecard.vues;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.pojo.AccountModel;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.MeteoModel;
import nicolas.johan.iem.pokecard.vues.fragments.AllPokemonsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.exchange.ExchangeFragment;
import nicolas.johan.iem.pokecard.vues.fragments.FriendsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.games.GameFragment;
import nicolas.johan.iem.pokecard.vues.fragments.PokedexFragment;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.vues.fragments.SettingsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.StoreFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Accueil extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView pseudo_header;
    TextView pokecoin;
    TextView nbCards;
    ImageView profileImage;
    ImageView modify_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEdit=prefs.edit();
        prefsEdit.putString("idUser", AccountSingleton.getInstance().getIdUser());
        prefsEdit.commit();

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

        nbCards = (TextView) header.findViewById(R.id.nbCards_header);

        update();

        profileImage = (ImageView) header.findViewById(R.id.profileImage);

        /*profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });*/

        Picasso.with(this).load(AccountSingleton.getInstance().getPicture()).into(profileImage);

        navigationView.setNavigationItemSelectedListener(this);
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
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new SettingsFragment())
                    .commit();
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


        Call<AccountModel> request = PokemonApp.getPokemonService().majAccount(AccountSingleton.getInstance().getIdUser());
        request.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                if (response.isSuccessful()) {
                    try {
                        AccountModel tmpAccount = response.body();
                        AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());
                        AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                        AccountSingleton.getInstance().setIdAccount(tmpAccount.getIdAccount());
                        AccountSingleton.getInstance().setIdUser(tmpAccount.getIdUser());
                        AccountSingleton.getInstance().setPicture(tmpAccount.getPicture());
                        AccountSingleton.getInstance().setPokeCoin(tmpAccount.getPokeCoin());
                        AccountSingleton.getInstance().setPseudo(tmpAccount.getPseudo());

                        Picasso.with(getBaseContext()).load(AccountSingleton.getInstance().getPicture()).into(profileImage);
                        pseudo_header.setText(AccountSingleton.getInstance().getPseudo());
                        if(AccountSingleton.getInstance().getListeCards().get(0).equals("")){
                            nbCards.setText("0");
                        }else{
                            nbCards.setText(""+ AccountSingleton.getInstance().getListeCards().size());
                        }
                        pokecoin.setText(""+AccountSingleton.getInstance().getPokeCoin());
                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Impossible de mettre à jour le compte", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Impossible de mettre à jour le compte", Toast.LENGTH_SHORT).show();
            }
        });

        Call<MeteoModel> getMeteo = PokemonApp.getPokemonService().getMeteoFromId(AccountSingleton.getInstance().getIdUser());
        getMeteo.enqueue(new Callback<MeteoModel>() {
            @Override
            public void onResponse(Call<MeteoModel> call, Response<MeteoModel> response) {
                if(response.isSuccessful()){
                    try{
                        ImageView imgMeteo=(ImageView) findViewById(R.id.imgMeteo);
                        TextView tempMeteo=(TextView) findViewById(R.id.tempMeteo);

                        MeteoModel tmp=response.body();

                        tempMeteo.setText(tmp.getTemp());
                        Picasso.with(getBaseContext()).load(tmp.getImg()).into(imgMeteo);
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<MeteoModel> call, Throwable t) {

            }
        });

    }

}
