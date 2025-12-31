package model;

import java.util.List;

public class Ordine {
    private List<Carta> carteOrdinate;
    private String indirizzoSpedizione;
    private User compratore;

    public Ordine(List<Carta> carteOrdinate, String indirizzoSpedizione, User compratore) {
        this.carteOrdinate = carteOrdinate;
        this.indirizzoSpedizione = indirizzoSpedizione;
        this.compratore = compratore;
    }

    public List<Carta> getCarteOrdinate() {
        return carteOrdinate;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public User getCompratore() {
        return compratore;
    }
}
