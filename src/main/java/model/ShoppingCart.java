package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<CartRow> row;

    public ShoppingCart() {
        this.row = new ArrayList<CartRow>();
    }

    public List<CartRow> getRow(){
        return this.row;
    }

    public void addCard(Card card) {
        CartRow cartRow = new CartRow(card, card.getPrezzoAttuale());
        this.row.add(cartRow);
    }

    public void removeCard(int id) {
        for(CartRow cartRow : this.row) {
            Card c = cartRow.getCard();
            if(id == c.getId()){
                row.remove(cartRow);
            }
        }
    }

    public Float getTotal() {
        Float total = 0.0f;
        for(CartRow cartRow : this.row) {
            total += cartRow.getPrice();
        }
        return total;
    }
}
