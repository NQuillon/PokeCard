package nicolas.johan.iem.pokecard;

import android.net.Uri;

/**
 * Created by Johan on 07/11/2017.
 */

public class Account {
    String id;
    String nom;
    String mail;
    String type;
    String prenom;
    Uri picture;


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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Uri getPicture() {
        return picture;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
