package nicolas.johan.iem.pokecard;

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


public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;

    EditText emailText;
    EditText passwordText;
    Button loginButton;
    TextView signupLink;
    LoginButton loginButtonFacebook;
    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    GoogleSignInAccount acct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        //Récupération des éléments graphiques
        emailText=(EditText) findViewById(R.id.input_email);
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
                        Toast.makeText(LoginActivity.this, "Connecté", Toast.LENGTH_LONG).show();
                        GraphRequest grequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                            try {
                                                System.out.println(object);
                                                Profile profile=Profile.getCurrentProfile();
                                                JSONObject jsonParam = new JSONObject();
                                                jsonParam.put("mail", object.getString("email"));
                                                jsonParam.put("password", "facebook");

                                                request.sendPost("verify", jsonParam);

                                                //GERER SI MAIL BLOQUE
                                                Account.getInstance().setMail(object.getString("email"));
                                                Account.getInstance().setPrenom(object.getString("first_name"));
                                                Account.getInstance().setNom(object.getString("last_name"));
                                                Account.getInstance().setId(object.getString("id"));
                                                Account.getInstance().setPicture(profile.getProfilePictureUri(150,150));

                                            }catch(Exception e){}
                                        Intent i=new Intent(LoginActivity.this, Accueil.class);
                                        startActivity(i);
                                        finish();
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
                startActivityForResult(intent, REQUEST_SIGNUP);
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

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        try {
            Profile profile=Profile.getCurrentProfile();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("mail", email);
            jsonParam.put("password", password);

            request.sendPost("login", jsonParam);
            //Implémenter le cas d'erreur
        }catch(Exception e){}

        onLoginSuccess();

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

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Adresse email invalide");
            valid = false;
        } else {
            emailText.setError(null);
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
            Toast.makeText(this, "Connecté en tant que "+acct.getDisplayName()+" ("+acct.getEmail()+")", Toast.LENGTH_LONG).show();
            signInButton.setVisibility(View.INVISIBLE);

            try {
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("mail", acct.getEmail());
                jsonParam.put("password", "google");

                request.sendPost("verify", jsonParam);
            }catch(Exception e){}

        } else {
            Toast.makeText(this, "Echec, vous n'êtes pas connecté", Toast.LENGTH_SHORT).show();
        }
    }



}