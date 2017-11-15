package nicolas.johan.iem.pokecard;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import nicolas.johan.iem.pokecard.webservice.PokemonService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iem on 15/11/2017.
 */

public class PokemonApp extends Application {
    private static PokemonService pokemonService;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit.Builder mBuilder =
                new Retrofit.Builder()
                        .baseUrl("http://192.168.43.200:3000/")
                        .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

        // log

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okBuilder.addInterceptor(logging);

        okBuilder.readTimeout(1, TimeUnit.MINUTES);

        OkHttpClient httpClient = okBuilder.build();

        Retrofit retrofit = mBuilder.client(httpClient).build();
        pokemonService = retrofit.create(PokemonService.class);

    }


    public static PokemonService getPokemonService() {

        return pokemonService;
    }
}
