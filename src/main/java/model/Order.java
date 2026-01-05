package model;

import java.util.List;

public class Order {
    private List<Card> carteOrdinate;
    private String indirizzoSpedizione;
    private User compratore;

    public Order(List<Card> carteOrdinate, String indirizzoSpedizione, User compratore) {
        this.carteOrdinate = carteOrdinate;
        this.indirizzoSpedizione = indirizzoSpedizione;
        this.compratore = compratore;
    }

    public List<Card> getCarteOrdinate() {
        return carteOrdinate;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public User getCompratore() {
        return compratore;
    }
}
