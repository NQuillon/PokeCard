package nicolas.johan.iem.pokecard;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    EditText nameText;
    EditText pseudoText;
    EditText passwordText;
    Button signupButton;
    TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pseudoText=(EditText) findViewById(R.id.input_pseudo);
        passwordText=(EditText) findViewById(R.id.input_password);
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
        String result="";
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
        }
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        Intent i=new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Echec", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
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
}