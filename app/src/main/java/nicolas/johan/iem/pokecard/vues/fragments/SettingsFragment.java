package nicolas.johan.iem.pokecard.vues.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import nicolas.johan.iem.pokecard.PokemonApp;
import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.GridViewProfilePicture;
import nicolas.johan.iem.pokecard.pojo.AccountModel;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.EditPseudoModel;
import nicolas.johan.iem.pokecard.pojo.ModifyZIPModel;
import nicolas.johan.iem.pokecard.pojo.NewPictureModel;
import nicolas.johan.iem.pokecard.pojo.ProfilPicture;
import nicolas.johan.iem.pokecard.vues.Accueil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends PreferenceFragment {
    Accueil activity;
    List<ProfilPicture> listPictures;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        Preference pseudo = (Preference) findPreference("profil_pseudo");
        pseudo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
                builder.setTitle("Entrez votre nouveau pseudo");

                final EditText input = new EditText(activity);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                input.setText(AccountSingleton.getInstance().getPseudo());
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditPseudoModel tmp=new EditPseudoModel(AccountSingleton.getInstance().getIdUser(),input.getText().toString());
                        Call<AccountModel> editPseudo = PokemonApp.getPokemonService().editPseudo(tmp);
                        editPseudo.enqueue(new Callback<AccountModel>() {
                            @Override
                            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                                if(response.isSuccessful()){
                                    activity.update();
                                    Toast.makeText(activity, "Pseudo modifié", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(activity, "Impossible de modifier le pseudo", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AccountModel> call, Throwable t) {
                                Toast.makeText(activity, "Impossible de modifier le pseudo", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return false;
            }
        });

        Preference picture = (Preference) findPreference("profil_picture");
        picture.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                //AlertDialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Nouvelle photo de profil");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.dialog_exchange, null);

                builder.setView(customLayout);
                //builder.show();
                final AlertDialog dialog=builder.create();
                dialog.setCancelable(true);
                dialog.show();


                //appel route
                Call<List<ProfilPicture>> getList = PokemonApp.getPokemonService().getListPicturess();
                getList.enqueue(new Callback<List<ProfilPicture>>() {
                    @Override
                    public void onResponse(Call<List<ProfilPicture>> call, Response<List<ProfilPicture>> response) {
                        if(response.isSuccessful()){
                            try{
                                listPictures=response.body();
                                GridView gv_pictures = (GridView) customLayout.findViewById(R.id.gridview_cards);
                                GridViewProfilePicture cardAdapter=new GridViewProfilePicture(getActivity(),listPictures);
                                gv_pictures.setAdapter(cardAdapter);

                                gv_pictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        NewPictureModel tmp=new NewPictureModel(AccountSingleton.getInstance().getIdUser(),listPictures.get(position).getUrl());
                                        Call<ResponseBody> setNewPicture=PokemonApp.getPokemonService().setNewProfilPicture(tmp);
                                        setNewPicture.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if(response.isSuccessful()){
                                                    try{
                                                        activity.update();
                                                        dialog.dismiss();
                                                        Toast.makeText(getActivity(), "La photo de profil a bien été changée", Toast.LENGTH_SHORT).show();
                                                    }catch (Exception e){

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                            }
                                        });
                                    }
                                });

                            }catch (Exception e){

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProfilPicture>> call, Throwable t) {

                    }
                });
                return false;
            }

        });


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
                        if(response.isSuccessful() && response.code()!=400){
                            try{
                                Toast.makeText(getActivity(), "Mise à jour de la météo...", Toast.LENGTH_SHORT).show();
                                zipCode.setText(AccountSingleton.getInstance().getZipCode());
                                activity.update();
                            }catch (Exception e){

                            }
                        }else{
                            Toast.makeText(activity, "Ce code postal n'existe pas", Toast.LENGTH_SHORT).show();
                            zipCode.setText(AccountSingleton.getInstance().getZipCode());
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