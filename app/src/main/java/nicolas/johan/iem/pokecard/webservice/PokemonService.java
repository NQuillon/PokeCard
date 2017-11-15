package nicolas.johan.iem.pokecard.webservice;

import java.util.List;

import nicolas.johan.iem.pokecard.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by iem on 15/11/2017.
 */

public interface PokemonService  {

    @GET("pokedex")
    Call<List<Pokemon>> getAll();

    @GET("pokedex")
    Call<String> getAllString();

    @GET("pokemon/{id}")
    Call<Pokemon> getFromId(@Path("id") int id);

}
