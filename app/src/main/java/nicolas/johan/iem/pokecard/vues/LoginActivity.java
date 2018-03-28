package nicolas.johan.iem.pokecard.vues;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.Model.LoginModel;
import nicolas.johan.iem.pokecard.pojo.Model.LoginSpecialModel;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;


public class LoginActivity extends AppCompatActivity {

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
    TextView logo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVariables();
        initInterface();
        initFacebook();
        initGoogle();
    }

    private void initVariables() {
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
        logo=(TextView) findViewById(R.id.logo);

    }
    private void initGoogle() {
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
    private void initFacebook() {
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
                                                LoginSpecialModel request = new LoginSpecialModel(object.getString("first_name")+" "+object.getString("last_name"), "facebook", object.getString("id"), profile.getProfilePictureUri(150,150).toString());
                                                ManagerPokemonService.getInstance().loginSpecial(request, (LoginActivity) context);
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
    }
    private void initInterface() {

        loginButtonFacebook.setReadPermissions("email");
        loginButtonFacebook.setReadPermissions("public_profile");

        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("Continuer avec Google");

        //Mise en place de la police d'écriture du logo
        Typeface tf = Typeface.createFromAsset(getAssets(),"Pokemon Solid.ttf");
        logo.setTypeface(tf);

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
        try {
            LoginModel request=new LoginModel(pseudo, password);
            ManagerPokemonService.getInstance().login(request, (LoginActivity) context);
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
        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        loginButton.setEnabled(true);
    }
    public void onBadLogin(){
        Toast.makeText(context, "Login ou mot de passe incorrect", Toast.LENGTH_LONG).show();
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
            String photoUrl;
            try {
                photoUrl = acct.getPhotoUrl().toString();
            }catch(Exception e) {
                photoUrl = "https://slack-imgs.com/?c=1&url=https%3A%2F%2Feternia.fr%2Fpublic%2Fmedia%2Fsl%2Fsprites%2Fformes%2F025_kanto.png";
            }
            LoginSpecialModel request=new LoginSpecialModel(acct.getDisplayName(), "google", acct.getId(), photoUrl);
            ManagerPokemonService.getInstance().loginSpecial(request, (LoginActivity) context);
        } else {
            Toast.makeText(this, "Echec, vous n'êtes pas connecté", Toast.LENGTH_SHORT).show();
        }
    }

}