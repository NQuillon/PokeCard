package nicolas.johan.iem.pokecard.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class ExchangePOST {

    private int idSender;
    private int idReceiver;
    private String idCard;
    private String status;

    public ExchangePOST() {
    }

    public ExchangePOST(int idSender, int idReceiver, String idCard, String status) {
        super();
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.idCard = idCard;
        this.status=status;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}