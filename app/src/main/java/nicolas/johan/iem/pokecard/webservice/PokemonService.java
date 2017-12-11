package nicolas.johan.iem.pokecard.webservice;

import java.util.List;

import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.pojo.PokemonDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by iem on 15/11/2017.
 */

public interface PokemonService  {

    @GET("pokedex")
    Call<List<Pokemon>> getAll();

    @GET("user/{id}/pokedex")
    Call<List<Pokemon>> getFromId(@Path("id") String id);

    @GET("pokemon/{id}")
    Call<PokemonDetails> getDetails(@Path("id") int id);

}
