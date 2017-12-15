package nicolas.johan.iem.pokecard.vues;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.POSTrequest;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountModel;
import nicolas.johan.iem.pokecard.pojo.LoginClass;
import nicolas.johan.iem.pokecard.pojo.VerifyClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;

    EditText pseudoText;
    EditText passwordText;
    Button loginButton;
    TextView signupLink;
    LoginButton loginButtonFacebook;
    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    GoogleSignInAccount acct;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=this;

        //Init Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        //Récupération des éléments graphiques
        pseudoText=(EditText) findViewById(R.id.input_pseudo_login);
        passwordText=(EditText) findViewById(R.id.input_password) ;
        loginButton=(Button)findViewById(R.id.btn_login);
        signupLink=(TextView) findViewById(R.id.link_signup);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        loginButtonFacebook = (LoginButton) findViewById(R.id.login_button_facebook);
        loginButtonFacebook.setReadPermissions("email");
        loginButtonFacebook.setReadPermissions("public_profile");

        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("Continuer avec Google");

        //Mise en place de la police d'écriture du logo
        Typeface tf = Typeface.createFromAsset(getAssets(),"Pokemon Solid.ttf");
        TextView logo=(TextView) findViewById(R.id.logo);
        logo.setTypeface(tf);

        //CallBack Login Facebook
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest grequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                            try {
                                                Profile profile=Profile.getCurrentProfile();
                                                /*JSONObject jsonParam = new JSONObject();
                                                jsonParam.put("pseudo", object.getString("first_name")+" "+object.getString("last_name"));
                                                jsonParam.put("password", "facebook");
                                                jsonParam.put("idUser", object.getString("id"));
                                                jsonParam.put("profilePicture", profile.getProfilePictureUri(150,150).toString());

                                                String resp=new POSTrequest().execute("verify", jsonParam).get();

                                                JSONObject objResult=new JSONObject(resp);

                                                AccountSingleton.getInstance().setPseudo(objResult.getString("pseudo"));
                                                AccountSingleton.getInstance().setIdAccount(object.getString("id"));
                                                AccountSingleton.getInstance().setPicture(objResult.getString("profilePicture"));
                                                AccountSingleton.getInstance().setPokeCoin(objResult.getInt("pokecoin"));
                                                AccountSingleton.getInstance().setIdUser(objResult.getString("idUser"));

                                                AccountSingleton.getInstance().setListeCards(new ArrayList<String>(Arrays.asList(objResult.getString("cards").replace("[","").replace("]","").replace("\"","").split(","))));
                                                AccountSingleton.getInstance().setListePokemon(new ArrayList<String>(Arrays.asList(objResult.getString("pokemon").split(","))));
                                                */

                                                VerifyClass request=new VerifyClass(object.getString("first_name")+" "+object.getString("last_name"), "facebook", object.getString("id"), profile.getProfilePictureUri(150,150).toString());
                                                Call<AccountModel> call = PokemonApp.getPokemonService().verifyAccount(request);
                                                call.enqueue(new Callback<AccountModel>() {
                                                    @Override
                                                    public void onResponse(retrofit2.Call<AccountModel> call, Response<AccountModel> response) {
                                                        AccountModel tmpAccount=response.body();
                                                        AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());
                                                        AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                                                        AccountSingleton.getInstance().setIdAccount(tmpAccount.getIdAccount());
                                                        AccountSingleton.getInstance().setIdUser(tmpAccount.getIdUser());
                                                        AccountSingleton.getInstance().setPicture(tmpAccount.getPicture());
                                                        AccountSingleton.getInstance().setPokeCoin(tmpAccount.getPokeCoin());
                                                        AccountSingleton.getInstance().setPseudo(tmpAccount.getPseudo());

                                                        onLoginSuccess();
                                                    }

                                                    @Override
                                                    public void onFailure(retrofit2.Call<AccountModel> call, Throwable t) {
                                                        Log.e("ERREUR",t.getMessage());
                                                        Toast.makeText(LoginActivity.this, "Impossible de communiquer avec le serveur", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }catch(Exception e){
                                                Toast.makeText(LoginActivity.this, "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
                                            }
                                        LoginManager.getInstance().logOut(); //déconnexion facebook
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "email,first_name,last_name,id"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
                        grequest.setParameters(parameters);
                        grequest.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Annulé", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, "Erreur"+exception, Toast.LENGTH_LONG).show();
                    }
                });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        //Login Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Echec", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, 1);
            }
        });
    }

    public void login() {

        //Vérification des champs
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        String pseudo = pseudoText.getText().toString();
        String password = passwordText.getText().toString();
        String result="";
        try {
            /*JSONObject jsonParam = new JSONObject();
            jsonParam.put("pseudo", pseudo);
            jsonParam.put("password", password);
            result=new POSTrequest().execute("login", jsonParam).get();

            JSONObject objResult=new JSONObject(result);*/

            LoginClass request=new LoginClass(pseudo, password);
            Call<AccountModel> call = PokemonApp.getPokemonService().login(request);
            call.enqueue(new Callback<AccountModel>() {
                @Override
                public void onResponse(retrofit2.Call<AccountModel> call, Response<AccountModel> response) {
                    AccountModel tmpAccount=response.body();

                    if(response.code()==400){
                        Toast.makeText(context, "Login ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                        loginButton.setEnabled(true);
                    }
                    else {
                        AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());
                        AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                        AccountSingleton.getInstance().setIdAccount(tmpAccount.getIdAccount());
                        AccountSingleton.getInstance().setIdUser(tmpAccount.getIdUser());
                        AccountSingleton.getInstance().setPicture(tmpAccount.getPicture());
                        AccountSingleton.getInstance().setPokeCoin(tmpAccount.getPokeCoin());
                        AccountSingleton.getInstance().setPseudo(tmpAccount.getPseudo());

                        onLoginSuccess();
                    }

                }

                @Override
                public void onFailure(retrofit2.Call<AccountModel> call, Throwable t) {
                    Log.e("ERREUR",t.getMessage());
                    loginButton.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Impossible de communiquer avec le serveur", Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            Toast.makeText(this, "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            loginButton.setEnabled(true);
        }

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Intent i=new Intent(LoginActivity.this, Accueil.class);
        startActivity(i);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Echec", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String pseudo = pseudoText.getText().toString();
        String password = passwordText.getText().toString();

        if (pseudo.isEmpty() || pseudo.length() < 3) {
            pseudoText.setError("Au moins 3 caractères");
            valid = false;
        } else {
            pseudoText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Le mot de passe doit contenir entre 4 et 10 caractères");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            //Toast.makeText(this, "Connecté en tant que "+acct.getDisplayName()+" ("+acct.getEmail()+")", Toast.LENGTH_LONG).show();
            signInButton.setVisibility(View.INVISIBLE);

            String photoUrl=acct.getPhotoUrl().toString();
            if(acct.getPhotoUrl().toString()=="" || acct.getPhotoUrl()==null){
                photoUrl="https://slack-imgs.com/?c=1&url=https%3A%2F%2Feternia.fr%2Fpublic%2Fmedia%2Fsl%2Fsprites%2Fformes%2F025_kanto.png";
            }else{
                photoUrl=acct.getPhotoUrl().toString();
            }
            VerifyClass request=new VerifyClass(acct.getDisplayName(), "google", acct.getId(), photoUrl);
            Call<AccountModel> call = PokemonApp.getPokemonService().verifyAccount(request);
            call.enqueue(new Callback<AccountModel>() {
                @Override
                public void onResponse(retrofit2.Call<AccountModel> call, Response<AccountModel> response) {
                    AccountModel tmpAccount=response.body();
                    AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());
                    AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                    AccountSingleton.getInstance().setIdAccount(tmpAccount.getIdAccount());
                    AccountSingleton.getInstance().setIdUser(tmpAccount.getIdUser());
                    AccountSingleton.getInstance().setPicture(tmpAccount.getPicture());
                    AccountSingleton.getInstance().setPokeCoin(tmpAccount.getPokeCoin());
                    AccountSingleton.getInstance().setPseudo(tmpAccount.getPseudo());

                    onLoginSuccess();
                }

                @Override
                public void onFailure(retrofit2.Call<AccountModel> call, Throwable t) {
                    Log.e("ERREUR",t.getMessage());
                    mGoogleApiClient.disconnect();
                    loginButton.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Impossible de communiquer avec le serveur", Toast.LENGTH_SHORT).show();
                }
            });





/*
            String response="";
            try {
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("pseudo", acct.getDisplayName());
                jsonParam.put("password", "google");
                jsonParam.put("idUser", acct.getId());
                String url="";
                try{
                    jsonParam.put("profilePicture", acct.getPhotoUrl().toString());
                }catch(Exception e){
                    jsonParam.put("profilePicture", "https://slack-imgs.com/?c=1&url=https%3A%2F%2Feternia.fr%2Fpublic%2Fmedia%2Fsl%2Fsprites%2Fformes%2F025_kanto.png");
                }
                response=new POSTrequest().execute("verify", jsonParam).get();
                //Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            }catch(Exception e){}
            AccountSingleton.getInstance().setIdAccount(acct.getId());
            String url="";
            try{
                url=acct.getPhotoUrl().toString();
            }catch(Exception e){
                url="https://slack-imgs.com/?c=1&url=https%3A%2F%2Feternia.fr%2Fpublic%2Fmedia%2Fsl%2Fsprites%2Fformes%2F025_kanto.png";
            }

            AccountSingleton.getInstance().setPicture(url);
            try {
                JSONObject objResult = new JSONObject(response);
                AccountSingleton.getInstance().setPseudo(objResult.getString("pseudo"));
                AccountSingleton.getInstance().setPokeCoin(objResult.getInt("pokecoin"));
                AccountSingleton.getInstance().setIdUser(objResult.getString("idUser"));

                AccountSingleton.getInstance().setListeCards(new ArrayList<String>(Arrays.asList(objResult.getString("cards").replace("[","").replace("]","").replace("\"","").split(","))));
                AccountSingleton.getInstance().setListePokemon(new ArrayList<String>(Arrays.asList(objResult.getString("pokemon").replace("[","").replace("]","").replace("\"","").split(","))));

                Intent i=new Intent(LoginActivity.this, Accueil.class);
                startActivity(i);
                finish();
            }catch(Exception e){
                Toast.makeText(this, "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
                mGoogleApiClient.disconnect();
            }
*/


        } else {
            Toast.makeText(this, "Echec, vous n'êtes pas connecté", Toast.LENGTH_SHORT).show();
        }
    }



}