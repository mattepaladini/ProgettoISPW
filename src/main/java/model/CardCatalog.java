package model;

import com.example.progettoispw.bean.CollectableCardBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardCatalog implements Serializable {

    private List<Card> cards;
    private Seller seller;

    public CardCatalog( Seller seller) {
        this.cards = new ArrayList<>();
        this.seller = seller;
    }

    public void addCollectableCard(Card card) {
        this.cards.add(card);
    }

    public void removeCollectableCard(Card card) {
        for(Card c : this.cards) {
            if(card.getId() == c.getId()) {
                this.cards.remove(c);
            }
        }
    }

    public Seller getSeller(){
        return seller;
    }

    public List<Card> getCards(){
        return cards;
    }
}
