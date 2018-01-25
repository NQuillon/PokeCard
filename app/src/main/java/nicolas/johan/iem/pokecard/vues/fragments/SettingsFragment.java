package nicolas.johan.iem.pokecard.vues.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.ModifyZIPModel;
import nicolas.johan.iem.pokecard.vues.Accueil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends PreferenceFragment {
    Accueil activity;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        final EditTextPreference zipCode=(EditTextPreference) findPreference("zipCode");
        zipCode.setText(AccountSingleton.getInstance().getZipCode());
        zipCode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                ModifyZIPModel tmp = new ModifyZIPModel(AccountSingleton.getInstance().getIdUser(), newValue.toString());
                Call<ResponseBody> editZIP = PokemonApp.getPokemonService().setZipCode(tmp);
                editZIP.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try{
                                Toast.makeText(getActivity(), "Mise à jour de la météo...", Toast.LENGTH_SHORT).show();
                                activity.update();
                            }catch (Exception e){

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Echec de changement de code postal", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity=(Accueil)context;
    }
}