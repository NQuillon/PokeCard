package nicolas.johan.iem.pokecard.vues;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Model.AccountModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#ec1e24"));

        Typeface tf = Typeface.createFromAsset(getAssets(), "Pokemon Solid.ttf");
        TextView logo = (TextView) findViewById(R.id.logo);
        logo.setTypeface(tf);

        TextView tb = (TextView) findViewById(R.id.tb);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.logo);
        a.reset();
        logo.clearAnimation();
        logo.startAnimation(a);

        Animation b = AnimationUtils.loadAnimation(this, R.anim.credits);
        b.reset();
        tb.clearAnimation();
        tb.startAnimation(b);


        ImageView imageView = (ImageView) findViewById(R.id.iv);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.loading).into(imageViewTarget);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean tmp = sharedPref.getBoolean("keepConnected", true);
        final String idUserShared = sharedPref.getString("idUser", "");

        if (!idUserShared.equals("") && tmp == true) {
            isConnected = true;
            Call<AccountModel> request = PokemonApp.getPokemonService().majAccount(idUserShared);
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
                        } catch (Exception e) {
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Impossible de se connecter (not sucessful)", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AccountModel> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "Impossible de se connecter (error)", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(6600);
                    }
                } catch (InterruptedException e) {
                } finally {

                    if (isConnected) {
                        finish();
                        Intent i = new Intent(SplashScreen.this, Accueil.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    } else {
                        finish();
                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }

                }
            }
        };

        splashTread.start();
    }

}

