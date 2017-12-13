package nicolas.johan.iem.pokecard.pojo;

import java.util.ArrayList;

/**
 * Created by iem on 13/12/2017.
 */

public class FriendAccount {
    String idUser; //id bd
    String pseudo;
    String picture;
    ArrayList<String> listePokemon=new ArrayList<>();
    ArrayList<String> listeCards=new ArrayList<>();

        public FriendAccount(String idUser, String pseudo, String picture, ArrayList<String> listePokemon, ArrayList<String> listeCards, int pokeCoin, String idAccount) {
            this.idUser = idUser;
            this.pseudo = pseudo;
            this.picture = picture;
            this.listePokemon = listePokemon;
            this.listeCards = listeCards;
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
    }
