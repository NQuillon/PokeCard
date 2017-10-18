package nicolas.johan.iem.pokecard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#ec1e24"));


        ImageView imageView = (ImageView) findViewById(R.id.iv);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.loading).into(imageViewTarget);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(5000);
                    }
                } catch (InterruptedException e) {
                } finally {
                    finish();
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };

        splashTread.start();
    }

}

