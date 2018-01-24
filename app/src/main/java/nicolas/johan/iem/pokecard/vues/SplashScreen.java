package nicolas.johan.iem.pokecard.vues;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import nicolas.johan.iem.pokecard.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#ec1e24"));

        Typeface tf = Typeface.createFromAsset(getAssets(),"Pokemon Solid.ttf");
        TextView logo=(TextView) findViewById(R.id.logo);
        logo.setTypeface(tf);

        TextView tb=(TextView) findViewById(R.id.tb);

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

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(6600);
                    }
                } catch (InterruptedException e) {
                } finally {
                    finish();
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.in, R.anim.out);
            }
            }
        };

        splashTread.start();
    }

}
