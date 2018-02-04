package nicolas.johan.iem.pokecard.webservice;

import java.util.List;

import nicolas.johan.iem.pokecard.pojo.AccountModel;
import nicolas.johan.iem.pokecard.pojo.BuyModel;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.pojo.CardNFC;
import nicolas.johan.iem.pokecard.pojo.ChuckNorrisFactsModel;
import nicolas.johan.iem.pokecard.pojo.EditPseudoModel;
import nicolas.johan.iem.pokecard.pojo.ExchangeModel;
import nicolas.johan.iem.pokecard.pojo.ExchangePOST;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.GameCategory;
import nicolas.johan.iem.pokecard.pojo.GetResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.LoginClass;
import nicolas.johan.iem.pokecard.pojo.ManageFriendsModel;
import nicolas.johan.iem.pokecard.pojo.MeteoModel;
import nicolas.johan.iem.pokecard.pojo.ModifyZIPModel;
import nicolas.johan.iem.pokecard.pojo.NewPictureModel;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.pojo.PokemonDetails;
import nicolas.johan.iem.pokecard.pojo.PostResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.ProfilPicture;
import nicolas.johan.iem.pokecard.pojo.QuestionGameModel;
import nicolas.johan.iem.pokecard.pojo.StoreItem;
import nicolas.johan.iem.pokecard.pojo.VerifyClass;
import okhttp3.ResponseBody;
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

    @GET("exchange/{idUser}")
    Call<List<ExchangeModel>> getAllExchanges(@Path("idUser") String idUser);

    @GET("quizz/category")
    Call<List<GameCategory>> getAllCategories();

    @GET("quizz/{quizzId}")
    Call<List<QuestionGameModel>> getQuestionsFromCategory(@Path("quizzId") String quizzId);

    @POST("user/{idUser}/addFriend")
    Call<List<FriendAccount>> addFriendByPseudo(@Path("idUser") String id, @Body ManageFriendsModel pseudoFriend);

    @POST("user/{idUser}/delFriend")
    Call<List<FriendAccount>> delFriendByPseudo(@Path("idUser") String id, @Body ManageFriendsModel pseudoFriend);

    @POST("option/editPseudo")
    Call<AccountModel> editPseudo(@Body EditPseudoModel newPseudo);

    @POST("quizz/results")
    Call<GetResultQuizzModel> getResultsFromQuizz(@Body PostResultQuizzModel data);

    @GET("user/{idUser}")
    Call<AccountModel> majAccount(@Path("idUser") String idUser);

    @GET("/cards/list/boosters")
    Call<List<StoreItem>> getItemsStore();

    @POST("cards/buy")
    Call<List<Card>> buyBooster(@Body BuyModel buyModel);

    @GET("weather/{idUser}")
    Call<MeteoModel> getMeteoFromId(@Path("idUser") String idUser);

    @POST("/option/editZipCode")
    Call<ResponseBody> setZipCode(@Body ModifyZIPModel newZip);

    @GET("user/picture/list")
    Call<List<ProfilPicture>> getListPicturess();

    @POST("/option/editProfilPicture")
    Call<ResponseBody> setNewProfilPicture(@Body NewPictureModel newPicture);

    @GET("/chuckNorris/{idUser}/random")
    Call<ChuckNorrisFactsModel> getChuckNorrisFact(@Path("idUser") String idUser);

    @POST("/user/addCardNFC")
    Call<Card> addCardFromNFC(@Body CardNFC card);
}
