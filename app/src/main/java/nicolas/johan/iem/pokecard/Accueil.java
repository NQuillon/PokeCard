package nicolas.johan.iem.pokecard;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import org.json.JSONObject;

import java.net.URL;

public class Accueil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView pseudo_header;
    TextView pokecoin;
    ImageView profileImage;
    Bitmap bitprofile;
    ImageView modify_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showFragment(new PokedexFragment());
        ///////////////////////////////////////////////////


        ///////////////////////////////////////////////////

        ///////////////////////////////////////////////////


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        header.setBackgroundColor(Color.rgb(246, 186, 44));
        pseudo_header = (TextView) header.findViewById(R.id.pseudo_header);
        pseudo_header.setText(Account.getInstance().getPseudo());
        pokecoin = (TextView) header.findViewById(R.id.pokecoin_header);
        pokecoin.setText(""+Account.getInstance().getPokeCoin());
        modify_header=(ImageView) header.findViewById(R.id.modify_header);

        profileImage = (ImageView) header.findViewById(R.id.profileImage);


        View.OnClickListener modify_listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);
                builder.setTitle("Entrez votre nouveau pseudo");

                final EditText input = new EditText(Accueil.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                input.setText(Account.getInstance().getPseudo());
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Account.getInstance().setPseudo(input.getText().toString());
                        pseudo_header.setText(input.getText().toString());
                        try{
                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("idUser", Account.getInstance().getIdUser());
                            jsonParam.put("pseudo", input.getText().toString());
                            new request().execute("option/editPseudo",jsonParam);
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
                URL imageURL = new URL(Account.getInstance().getPicture().toString());
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pokedex) {
            showFragment(new PokedexFragment());
        } else if (id == R.id.nav_echanger) {
            showFragment(new ExchangeFragment());
        } else if (id == R.id.nav_jeux) {
            showFragment(new GameFragment());
        } else if (id == R.id.nav_boutique) {
            showFragment(new StoreFragment());
        } else if (id == R.id.nav_amis) {
            showFragment(new FriendsFragment());
        } else if (id == R.id.nav_params) {
            showFragment(new SettingsFragment());
        } else if (id == R.id.nav_deco) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
