package nicolas.johan.iem.pokecard.vues.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import nicolas.johan.iem.pokecard.R;
import nicolas.johan.iem.pokecard.adapter.GridViewProfilePicture;
import nicolas.johan.iem.pokecard.pojo.AccountSingleton;
import nicolas.johan.iem.pokecard.pojo.Model.EditPseudoModel;
import nicolas.johan.iem.pokecard.pojo.Model.ModifyZIPModel;
import nicolas.johan.iem.pokecard.pojo.Model.NewPictureModel;
import nicolas.johan.iem.pokecard.pojo.ProfilPicture;
import nicolas.johan.iem.pokecard.vues.Accueil;
import nicolas.johan.iem.pokecard.webservice.ManagerPokemonService;
import nicolas.johan.iem.pokecard.webservice.webServiceInterface;

public class SettingsFragment extends PreferenceFragment implements webServiceInterface {
    Accueil activity;
    SettingsFragment that;
    View customLayout;
    AlertDialog dialog;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        that = this;

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
                        EditPseudoModel tmp = new EditPseudoModel(AccountSingleton.getInstance().getIdUser(), input.getText().toString());
                        ManagerPokemonService.getInstance().editPseudo(tmp, that);
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
                builder.setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                customLayout = getActivity().getLayoutInflater().inflate(R.layout.dialog_exchange, null);

                builder.setView(customLayout);

                dialog = builder.create();
                dialog.setCancelable(true);
                dialog.show();

                ManagerPokemonService.getInstance().getListPictures(that);
                return false;
            }

        });


        final EditTextPreference zipCode = (EditTextPreference) findPreference("zipCode");
        zipCode.setText(AccountSingleton.getInstance().getZipCode());
        zipCode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                ModifyZIPModel tmp = new ModifyZIPModel(AccountSingleton.getInstance().getIdUser(), newValue.toString());
                ManagerPokemonService.getInstance().editZipCode(tmp, that);
                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Accueil) context;
    }

    @Override
    public void onSuccess() {
        activity.update();
        Toast.makeText(activity, "Modification effectuée", Toast.LENGTH_SHORT).show();
        try {
            dialog.dismiss();
        } catch (Exception e) {
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(activity, "Impossible d'effectuer les modifications", Toast.LENGTH_SHORT).show();
    }

    public void initListPictures(final List<ProfilPicture> listPictures) {
        GridView gv_pictures = (GridView) customLayout.findViewById(R.id.gridview_cards);
        GridViewProfilePicture cardAdapter = new GridViewProfilePicture(getActivity(), listPictures);
        gv_pictures.setAdapter(cardAdapter);

        gv_pictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewPictureModel tmp = new NewPictureModel(AccountSingleton.getInstance().getIdUser(), listPictures.get(position).getBase64());
                ManagerPokemonService.getInstance().editProfilPicture(tmp, that);
            }
        });
    }
}