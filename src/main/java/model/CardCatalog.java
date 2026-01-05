package model;

import com.example.progettoispw.bean.CollectableCardBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardCatalog implements Serializable {


    private List<CollectableCardBean> cards;
    private Seller seller;

    public CardCatalog( Seller seller) {
        this.cards = new ArrayList<>();
        this.seller = seller;
    }


    public void addCollectableCard(CollectableCardBean card) {
        //.......
    }

    public void removeCollectableCard(CollectableCardBean card) {
        //.......
    }

    public Seller getSeller(){
        return seller;
    }

    public List<CollectableCardBean> getCards(){
        return cards;
    }
}
