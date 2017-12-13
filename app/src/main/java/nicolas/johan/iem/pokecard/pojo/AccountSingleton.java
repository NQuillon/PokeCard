package nicolas.johan.iem.pokecard.pojo;

import java.util.ArrayList;

/**
 * Created by Johan on 07/11/2017.
 */

public class AccountSingleton {
    private String idUser; //id bd
    private String pseudo;
    private String picture;
    private ArrayList<String> listePokemon=new ArrayList<>();
    private ArrayList<String> listeCards=new ArrayList<>();
    private int pokeCoin;
    private String idAccount; //id facebook/google

    /** Constructeur privé */
    private AccountSingleton()
    {}

    public AccountSingleton(String idUser, String pseudo, String picture, ArrayList<String> listePokemon, ArrayList<String> listeCards, int pokeCoin, String idAccount) {
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.picture = picture;
        this.listePokemon = listePokemon;
        this.listeCards = listeCards;
        this.pokeCoin = pokeCoin;
        this.idAccount = idAccount;
    }

    /** Instance unique non préinitialisée */
    private static AccountSingleton INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static AccountSingleton getInstance()
    {
        if (INSTANCE == null)
        { 	INSTANCE = new AccountSingleton();
        }
        return INSTANCE;
    }

    public ArrayList<String> getListeCards() {
        return listeCards;
    }

    public void setListeCards(ArrayList<String> listeCards) {
        this.listeCards = listeCards;
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
}
