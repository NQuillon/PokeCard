package nicolas.johan.iem.pokecard.pojo;

/**
 * Created by iem on 23/01/2018.
 */

public class ExchangeModel {
    public String pseudoSender;
    public String status;
    public String cardName;
    public String cardPicture;
    public String idSender;
    public String idEchange;
    public String idReceiver;
    public String pseudoReceiver;

    public ExchangeModel(String pseudoSender, String status, String cardName, String cardPicture, String idSender, String idEchange, String idReceiver, String pseudoReceiver) {
        this.pseudoSender = pseudoSender;
        this.status = status;
        this.cardName = cardName;
        this.cardPicture = cardPicture;
        this.idSender = idSender;
        this.idEchange = idEchange;
        this.idReceiver = idReceiver;
        this.pseudoReceiver = pseudoReceiver;
    }

    public String getPseudoSender() {
        return pseudoSender;
    }

    public void setPseudoSender(String pseudoSender) {
        this.pseudoSender = pseudoSender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardPicture() {
        return cardPicture;
    }

    public void setCardPicture(String cardPicture) {
        this.cardPicture = cardPicture;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdEchange() {
        return idEchange;
    }

    public void setIdEchange(String idEchange) {
        this.idEchange = idEchange;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getPseudoReceiver() {
        return pseudoReceiver;
    }

    public void setPseudoReceiver(String pseudoReceiver) {
        this.pseudoReceiver = pseudoReceiver;
    }
}
