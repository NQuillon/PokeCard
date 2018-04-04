package nicolas.johan.iem.pokecard.pojo.Model;

import java.util.ArrayList;

/**
 * Created by iem on 13/12/2017.
 */

public class AccountModel {
    private String idUser; //id bd
    private String pseudo;
    private String picture;
    private ArrayList<String> listePokemon = new ArrayList<>();
    private ArrayList<String> listeCards = new ArrayList<>();
    private int pokeCoin;
    private String zipCode;
    private String idAccount;//id facebook/google

    /**
     * Constructeur privé
     */
    private AccountModel() {
    }

    public AccountModel(String idUser, String pseudo, String picture, ArrayList<String> listePokemon, ArrayList<String> listeCards, int pokeCoin, String idAccount, String zipCode) {
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.picture = picture;
        this.listePokemon = listePokemon;
        this.listeCards = listeCards;
        this.pokeCoin = pokeCoin;
        this.idAccount = idAccount;
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
