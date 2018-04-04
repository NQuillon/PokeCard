package nicolas.johan.iem.pokecard.webservice;

import android.util.Log;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Card;
import nicolas.johan.iem.pokecard.pojo.FriendAccount;
import nicolas.johan.iem.pokecard.pojo.GameCategory;
import nicolas.johan.iem.pokecard.pojo.Model.AccountModel;
import nicolas.johan.iem.pokecard.pojo.Model.BuyModel;
import nicolas.johan.iem.pokecard.pojo.Model.CardNFCModel;
import nicolas.johan.iem.pokecard.pojo.Model.ChuckNorrisFactsModel;
import nicolas.johan.iem.pokecard.pojo.Model.EditPseudoModel;
import nicolas.johan.iem.pokecard.pojo.Model.ExchangeModel;
import nicolas.johan.iem.pokecard.pojo.Model.ExchangePOSTModel;
import nicolas.johan.iem.pokecard.pojo.Model.GetResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.Model.LoginModel;
import nicolas.johan.iem.pokecard.pojo.Model.LoginSpecialModel;
import nicolas.johan.iem.pokecard.pojo.Model.ManageFriendsModel;
import nicolas.johan.iem.pokecard.pojo.Model.MeteoModel;
import nicolas.johan.iem.pokecard.pojo.Model.ModifyZIPModel;
import nicolas.johan.iem.pokecard.pojo.Model.NewPictureModel;
import nicolas.johan.iem.pokecard.pojo.Model.PostResultQuizzModel;
import nicolas.johan.iem.pokecard.pojo.Model.QuestionGameModel;
import nicolas.johan.iem.pokecard.pojo.Pokemon;
import nicolas.johan.iem.pokecard.pojo.PokemonDetails;
import nicolas.johan.iem.pokecard.pojo.ProfilPicture;
import nicolas.johan.iem.pokecard.pojo.StoreItem;
import nicolas.johan.iem.pokecard.vues.Accueil;
import nicolas.johan.iem.pokecard.vues.LoginActivity;
import nicolas.johan.iem.pokecard.vues.SignUpActivity;
import nicolas.johan.iem.pokecard.vues.fragments.AllPokemonsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.DetailsPokemon;
import nicolas.johan.iem.pokecard.vues.fragments.FriendsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.ScanFragment;
import nicolas.johan.iem.pokecard.vues.fragments.SettingsFragment;
import nicolas.johan.iem.pokecard.vues.fragments.StoreFragment;
import nicolas.johan.iem.pokecard.vues.fragments.exchange.ExchangeFragment;
import nicolas.johan.iem.pokecard.vues.fragments.exchange.NewExchangeFragment;
import nicolas.johan.iem.pokecard.vues.fragments.exchange.ReceiverExchange;
import nicolas.johan.iem.pokecard.vues.fragments.games.CategoriesFragment;
import nicolas.johan.iem.pokecard.vues.fragments.games.ChuckNorrisFragment;
import nicolas.johan.iem.pokecard.vues.fragments.games.QuestionGame;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by iem on 02/03/2018.
 */

public class ManagerPokemonService {

    private static ManagerPokemonService INSTANCE = null;

    private ManagerPokemonService() {
    }

    public static ManagerPokemonService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManagerPokemonService();
        }
        return INSTANCE;
    }

    //region AUTHENTIFICATION

    public void login(LoginModel loginModel, final LoginActivity callback) {
        Call<AccountModel> call = PokemonApp.getPokemonService().login(loginModel);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(retrofit2.Call<AccountModel> call, Response<AccountModel> response) {
                AccountModel tmpAccount = response.body();
                if (response.code() == 400) {
                    callback.onBadLogin();
                } else {
                    AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());
                    AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                    AccountSingleton.getInstance().setIdAccount(tmpAccount.getIdAccount());
                    AccountSingleton.getInstance().setIdUser(tmpAccount.getIdUser());
                    AccountSingleton.getInstance().setPicture(tmpAccount.getPicture());
                    AccountSingleton.getInstance().setPokeCoin(tmpAccount.getPokeCoin());
                    AccountSingleton.getInstance().setPseudo(tmpAccount.getPseudo());
                    callback.onLoginSuccess();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AccountModel> call, Throwable t) {
                Log.e("ERREUR", t.getMessage());
                callback.onLoginFailed();
            }
        });
    }

    public void loginSpecial(LoginSpecialModel account, final LoginActivity callback) {
        Call<AccountModel> call = PokemonApp.getPokemonService().verifyAccount(account);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                AccountModel tmpAccount = response.body();
                AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());
                AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                AccountSingleton.getInstance().setIdAccount(tmpAccount.getIdAccount());
                AccountSingleton.getInstance().setIdUser(tmpAccount.getIdUser());
                AccountSingleton.getInstance().setPicture(tmpAccount.getPicture());
                AccountSingleton.getInstance().setPokeCoin(tmpAccount.getPokeCoin());
                AccountSingleton.getInstance().setPseudo(tmpAccount.getPseudo());

                callback.onLoginSuccess();
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Log.e("ERREUR", t.getMessage());
                callback.onLoginFailed();
            }
        });
    }

    public void signUp(LoginModel loginModel, final SignUpActivity callback) {
        Call<AccountModel> call = PokemonApp.getPokemonService().signup(loginModel);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(retrofit2.Call<AccountModel> call, Response<AccountModel> response) {
                AccountModel tmpAccount = response.body();

                if (response.code() == 400) {
                    callback.onBadSignup();
                } else {
                    AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());
                    AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                    AccountSingleton.getInstance().setIdAccount(tmpAccount.getIdAccount());
                    AccountSingleton.getInstance().setIdUser(tmpAccount.getIdUser());
                    AccountSingleton.getInstance().setPicture(tmpAccount.getPicture());
                    AccountSingleton.getInstance().setPokeCoin(tmpAccount.getPokeCoin());
                    AccountSingleton.getInstance().setPseudo(tmpAccount.getPseudo());

                    callback.onSignupSuccess();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<AccountModel> call, Throwable t) {
                Log.e("ERREUR", t.getMessage());
                callback.onSignupFailed();
            }
        });
    }

    //endregion

    //region USER

    public void getUserAccount(String idUser, final webServiceInterface callback) {
        Call<AccountModel> request = PokemonApp.getPokemonService().majAccount(idUser);
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

                        callback.onSuccess();
                    } catch (Exception e) {
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getFriends(final getFriendsInterface callback, final webServiceInterface callbackWeb) {
        Call<List<FriendAccount>> friends = PokemonApp.getPokemonService().getFriends(AccountSingleton.getInstance().getIdUser());
        friends.enqueue(new Callback<List<FriendAccount>>() {
            @Override
            public void onResponse(Call<List<FriendAccount>> call, Response<List<FriendAccount>> response) {
                if (response.isSuccessful()) {
                    try {
                        callbackWeb.onSuccess();
                        callback.refresh(response.body());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FriendAccount>> call, Throwable t) {
                callbackWeb.onFailure();
            }
        });
    }

    public void addFriend(final ManageFriendsModel friend, final FriendsFragment callback) {
        Call<List<FriendAccount>> addfriend = PokemonApp.getPokemonService().addFriendByPseudo(AccountSingleton.getInstance().getIdUser(), friend);
        addfriend.enqueue(new Callback<List<FriendAccount>>() {
            @Override
            public void onResponse(Call<List<FriendAccount>> call, Response<List<FriendAccount>> response) {
                if (response.isSuccessful()) {
                    try {
                        callback.onSuccess();
                        callback.onAddFriend(friend.getPseudoFriend());
                        callback.refresh(response.body());
                    } catch (Exception e) {
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<FriendAccount>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void delFriend(final ManageFriendsModel friend, final FriendsFragment callback) {
        Call<List<FriendAccount>> delfriend = PokemonApp.getPokemonService().delFriendByPseudo(AccountSingleton.getInstance().getIdUser(), friend);
        delfriend.enqueue(new Callback<List<FriendAccount>>() {
            @Override
            public void onResponse(Call<List<FriendAccount>> call, Response<List<FriendAccount>> response) {
                if (response.isSuccessful()) {
                    try {
                        callback.onDelFriend(friend.getPseudoFriend());
                        callback.refresh(response.body());
                    } catch (Exception e) {
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<FriendAccount>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    //endregion

    //region POKEMON

    public void getAllPokemon(final AllPokemonsFragment callback) {
        Call<List<Pokemon>> pokemons = PokemonApp.getPokemonService().getAll();
        pokemons.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (response.isSuccessful()) {
                    try {
                        callback.refresh(response.body());
                        callback.onSuccess();
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getUserPokemon(final getUserPokemonInterface callback, final webServiceInterface callbackWeb) {
        Call<List<Pokemon>> pokemons = PokemonApp.getPokemonService().getFromId(AccountSingleton.getInstance().getIdUser());
        pokemons.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (response.isSuccessful()) {
                    try {
                        callbackWeb.onSuccess();
                        callback.refresh(response.body());
                        if (response.body().size() == 0) {
                            callback.onNoPokemon();
                        }
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                try {
                    callbackWeb.onFailure();
                } catch (Exception e) {
                }
            }
        });
    }

    public void getPokemonDetails(int idPokemon, final DetailsPokemon callback) {
        Call<PokemonDetails> pokdet = PokemonApp.getPokemonService().getDetails(idPokemon);
        pokdet.enqueue(new Callback<PokemonDetails>() {
            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                if (response.isSuccessful()) {
                    try {
                        callback.onSuccess();
                        callback.refresh(response.body());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonDetails> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    //endregion

    //region CARDS

    public void getCardsById(int idUser, int Id, final NewExchangeFragment callback) {
        Call<List<Card>> list = PokemonApp.getPokemonService().getCardsFromId(idUser, Id);
        list.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                if (response.isSuccessful()) {
                    callback.showListCards(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void addCardNFC(String message, final ScanFragment callback) {
        Call<Card> request = PokemonApp.getPokemonService().addCardFromNFC(new CardNFCModel(AccountSingleton.getInstance().getIdUser(), message));
        request.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                    callback.afficheCard(response.body());
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {

            }
        });
    }

    public void getListBoosters(final StoreFragment callback) {
        Call<List<StoreItem>> listeItems = PokemonApp.getPokemonService().getItemsStore();
        listeItems.enqueue(new Callback<List<StoreItem>>() {
            @Override
            public void onResponse(Call<List<StoreItem>> call, Response<List<StoreItem>> response) {
                if (response.isSuccessful()) {
                    try {
                        callback.refresh(response.body());
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<StoreItem>> call, Throwable t) {

            }
        });
    }

    public void buyBooster(BuyModel buyModel, final StoreFragment callback) {
        Call<List<Card>> buyRequest = PokemonApp.getPokemonService().buyBooster(buyModel);
        buyRequest.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                if (response.isSuccessful()) {
                    callback.onBuy(response.body());
                    callback.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    //endregion

    //region EXCHANGE

    public void getAllExchange(final ExchangeFragment callback) {
        Call<List<ExchangeModel>> exchanges = PokemonApp.getPokemonService().getAllExchanges(AccountSingleton.getInstance().getIdUser());

        exchanges.enqueue(new Callback<List<ExchangeModel>>() {
            @Override
            public void onResponse(Call<List<ExchangeModel>> call, Response<List<ExchangeModel>> response) {
                if (response.isSuccessful()) {
                    callback.refreshView(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ExchangeModel>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void sendExchange(ExchangePOSTModel exchangePOSTModel, final ReceiverExchange callback) {
        Call<AccountModel> call = PokemonApp.getPokemonService().sendExchangeRequest(exchangePOSTModel);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(retrofit2.Call<AccountModel> call, Response<AccountModel> response) {
                callback.exchangeSuccessful(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<AccountModel> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    //endregion

    //region GAMES

    public void getListCategories(final CategoriesFragment callback) {
        Call<List<GameCategory>> cat = PokemonApp.getPokemonService().getAllCategories();
        cat.enqueue(new Callback<List<GameCategory>>() {
            @Override
            public void onResponse(Call<List<GameCategory>> call, Response<List<GameCategory>> response) {
                if (response.isSuccessful()) {
                    callback.refresh(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GameCategory>> call, Throwable t) {

            }
        });
    }

    public void getQuestions(String category, final QuestionGame callback) {
        Call<List<QuestionGameModel>> questions = PokemonApp.getPokemonService().getQuestionsFromCategory(category);
        questions.enqueue(new Callback<List<QuestionGameModel>>() {
            @Override
            public void onResponse(Call<List<QuestionGameModel>> call, Response<List<QuestionGameModel>> response) {
                if (response.isSuccessful()) {
                    callback.showQuestion(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<QuestionGameModel>> call, Throwable t) {

            }
        });
    }

    public void getResults(PostResultQuizzModel postResultQuizzModel, final QuestionGame callback) {
        Call<GetResultQuizzModel> request = PokemonApp.getPokemonService().getResultsFromQuizz(postResultQuizzModel);
        request.enqueue(new Callback<GetResultQuizzModel>() {
            @Override
            public void onResponse(Call<GetResultQuizzModel> call, Response<GetResultQuizzModel> response) {
                if (response.isSuccessful()) {
                    callback.showResults(response.body());
                } else {
                    callback.onFailure();
                }

            }

            @Override
            public void onFailure(Call<GetResultQuizzModel> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getChuckNorrisFact(final ChuckNorrisFragment callback) {
        Call<ChuckNorrisFactsModel> request = PokemonApp.getPokemonService().getChuckNorrisFact(AccountSingleton.getInstance().getIdUser());
        request.enqueue(new Callback<ChuckNorrisFactsModel>() {
            @Override
            public void onResponse(Call<ChuckNorrisFactsModel> call, Response<ChuckNorrisFactsModel> response) {
                if (response.isSuccessful()) {
                    try {
                        ChuckNorrisFactsModel tmp = response.body();
                        callback.loadFact(tmp);
                        callback.onSuccess();
                    } catch (Exception e) {
                        callback.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChuckNorrisFactsModel> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    //endregion

    //region OPTION

    public void editPseudo(EditPseudoModel pseudoModel, final SettingsFragment callback) {
        Call<AccountModel> editPseudo = PokemonApp.getPokemonService().editPseudo(pseudoModel);
        editPseudo.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void editZipCode(ModifyZIPModel modifyZIPModel, final SettingsFragment callback) {
        Call<ResponseBody> editZIP = PokemonApp.getPokemonService().setZipCode(modifyZIPModel);
        editZIP.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() != 400) {
                    callback.onSuccess();
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void editProfilPicture(NewPictureModel newPictureModel, final SettingsFragment callback) {
        Call<ResponseBody> setNewPicture = PokemonApp.getPokemonService().setNewProfilPicture(newPictureModel);
        setNewPicture.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getListPictures(final SettingsFragment callback) {
        Call<List<ProfilPicture>> getList = PokemonApp.getPokemonService().getListPictures();
        getList.enqueue(new Callback<List<ProfilPicture>>() {
            @Override
            public void onResponse(Call<List<ProfilPicture>> call, Response<List<ProfilPicture>> response) {
                if (response.isSuccessful()) {
                    callback.initListPictures(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProfilPicture>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }


    //endregion

    //region METEO

    public void getMeteo(String idUser, final Accueil accueil) {
        Call<MeteoModel> getMeteo = PokemonApp.getPokemonService().getMeteoFromId(idUser);
        getMeteo.enqueue(new Callback<MeteoModel>() {
            @Override
            public void onResponse(Call<MeteoModel> call, Response<MeteoModel> response) {
                if (response.isSuccessful()) {
                    try {
                        MeteoModel tmp = response.body();
                        accueil.onMeteo(tmp);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<MeteoModel> call, Throwable t) {
            }
        });
    }

    //endregion
}
