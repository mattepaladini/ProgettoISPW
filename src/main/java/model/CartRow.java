package model;

public class CartRow {

    private Card card;
    private Float price;

    public CartRow(Card card, Float price) {
        this.card = card;
        this.price = price;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
