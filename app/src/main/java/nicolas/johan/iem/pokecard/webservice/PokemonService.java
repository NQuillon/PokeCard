package nicolas.johan.iem.pokecard.webservice;

import java.util.List;

import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.pojo.ExchangePOST;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.LoginClass;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.pojo.PokemonDetails;
import nicolas.johan.iem.pokecard.pojo.AccountModel;
import nicolas.johan.iem.pokecard.pojo.VerifyClass;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("user/{idUser}/{idPokemon}/cards")
    Call<List<Card>> getCardsFromId(@Path("idUser") int idUser, @Path("idPokemon") int idPokemon);

    @POST("exchange/send")
    Call<AccountModel> sendExchangeRequest(@Body ExchangePOST request);

    @GET("user/{idUser}/getFriends") //a verifier
    Call<List<FriendAccount>> getFriends(@Path("idUser") String idUser);

    @POST("verify")
    Call<AccountModel> verifyAccount(@Body VerifyClass request);

    @POST("login")
    Call<AccountModel> login(@Body LoginClass request);

    @POST("signup")
    Call<AccountModel> signup(@Body LoginClass request);

}
