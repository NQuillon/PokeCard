package nicolas.johan.iem.pokecard.vues;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Model.LoginModel;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;

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
        this.context = this;

        pseudoText = (EditText) findViewById(R.id.input_pseudo);
        passwordText = (EditText) findViewById(R.id.input_password);
        confirmPasswordText = (EditText) findViewById(R.id.input_confirm_password);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);

        Typeface tf = Typeface.createFromAsset(getAssets(), "Pokemon Solid.ttf");
        TextView logo = (TextView) findViewById(R.id.logo);
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

        LoginModel data = new LoginModel(pseudo, password);
        ManagerPokemonService.getInstance().signUp(data, (SignUpActivity) context);
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        Toast.makeText(context, "Vous êtes maintenant connecté à votre nouveau compte avec le login " + AccountSingleton.getInstance().getPseudo(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(SignUpActivity.this, Accueil.class);
        startActivity(i);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public void onBadSignup() {
        Toast.makeText(context, "Login déjà pris", Toast.LENGTH_LONG).show();
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