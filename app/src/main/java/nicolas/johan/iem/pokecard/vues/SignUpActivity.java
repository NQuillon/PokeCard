package nicolas.johan.iem.pokecard.vues;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import nicolas.johan.iem.pokecard.POSTrequest;
import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountModel;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.LoginClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText nameText;
    EditText pseudoText;
    EditText passwordText;
    EditText confirmPasswordText;
    Button signupButton;
    TextView loginLink;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.context=this;

        pseudoText=(EditText) findViewById(R.id.input_pseudo);
        passwordText=(EditText) findViewById(R.id.input_password);
        confirmPasswordText=(EditText) findViewById(R.id.input_confirm_password);
        signupButton=(Button) findViewById(R.id.btn_signup);
        loginLink=(TextView)findViewById(R.id.link_login);

        Typeface tf = Typeface.createFromAsset(getAssets(),"Pokemon Solid.ttf");
        TextView logo=(TextView) findViewById(R.id.logo);
        logo.setTypeface(tf);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        String pseudo = pseudoText.getText().toString();
        String password = passwordText.getText().toString();
        /*String result="";
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("pseudo", pseudo);
            jsonParam.put("password", password);
            result=new POSTrequest().execute("signup",jsonParam).get();
            JSONObject objResult=new JSONObject(result);
            if(objResult.getString("pseudo").equals("false")){
                Toast.makeText(this, "Le compte existe déjà, veuillez changer de pseudo", Toast.LENGTH_LONG).show();
                signupButton.setEnabled(true);
            }else{
                Toast.makeText(this, "Votre compte a été créé, veuillez vous connecter", Toast.LENGTH_LONG).show();
                onSignupSuccess();
            }
        }catch(Exception e){
            Toast.makeText(this, "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_LONG).show();
            signupButton.setEnabled(true);
        }*/

        LoginClass request=new LoginClass(pseudo, password);
        Call<AccountModel> call = PokemonApp.getPokemonService().signup(request);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(retrofit2.Call<AccountModel> call, Response<AccountModel> response) {
                AccountModel tmpAccount=response.body();

                if(response.code()==400){
                    Toast.makeText(context, "Login ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                    signupButton.setEnabled(true);
                }
                else {
                    AccountSingleton.getInstance().setListeCards(tmpAccount.getListeCards());
                    AccountSingleton.getInstance().setListePokemon(tmpAccount.getListePokemon());
                    AccountSingleton.getInstance().setIdAccount(tmpAccount.getIdAccount());
                    AccountSingleton.getInstance().setIdUser(tmpAccount.getIdUser());
                    AccountSingleton.getInstance().setPicture(tmpAccount.getPicture());
                    AccountSingleton.getInstance().setPokeCoin(tmpAccount.getPokeCoin());
                    AccountSingleton.getInstance().setPseudo(tmpAccount.getPseudo());

                    onSignupSuccess();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<AccountModel> call, Throwable t) {
                Log.e("ERREUR",t.getMessage());
                Toast.makeText(SignUpActivity.this, "Impossible de communiquer avec le serveur", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        Toast.makeText(context, "Vous êtes maintenant connecté à votre nouveau compte avec le login "+ AccountSingleton.getInstance().getPseudo(), Toast.LENGTH_LONG).show();
        Intent i=new Intent(SignUpActivity.this, Accueil.class);
        startActivity(i);
        finish();
    }

    public void onSignupFailed() {
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String pseudo = pseudoText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();

        if (pseudo.isEmpty() || pseudo.length() < 3) {
            pseudoText.setError("Au moins 3 caractères");
            valid = false;
        } else {
            pseudoText.setError(null);
        }

        if (!confirmPassword.equals(password)) {
            confirmPasswordText.setError("Mots de passe différents");
            valid = false;
        } else {
            confirmPasswordText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Le mot de passe doit contenir entre 4 et 10 caractères");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        return valid;
    }
}