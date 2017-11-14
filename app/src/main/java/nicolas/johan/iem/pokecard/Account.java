package nicolas.johan.iem.pokecard;

import android.net.Uri;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Johan on 07/11/2017.
 */

public class Account {
    String idUser; //id bd
    String pseudo;
    String typeConnexion;
    String picture;
    ArrayList<String> listePokemon;
    int pokeCoin;
    String idAccount; //id facebook/google

    /** Constructeur privé */
    private Account()
    {}

    /** Instance unique non préinitialisée */
    private static Account INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static Account getInstance()
    {
        if (INSTANCE == null)
        { 	INSTANCE = new Account();
        }
        return INSTANCE;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getTypeConnexion() {
        return typeConnexion;
    }

    public void setTypeConnexion(String typeConnexion) {
        this.typeConnexion = typeConnexion;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArrayList<String> getListePokemon() {
        return listePokemon;
    }

    public void setListePokemon(ArrayList<String> listePokemon) {
        this.listePokemon = listePokemon;
    }

    public int getPokeCoin() {
        return pokeCoin;
    }

    public void setPokeCoin(int pokeCoin) {
        this.pokeCoin = pokeCoin;
    }

    public boolean isConnectedByFacebook(){
        return this.typeConnexion.equals("facebook");
    }

    public boolean isConnectedByGoogle(){
        return this.typeConnexion.equals("google");
    }
}
