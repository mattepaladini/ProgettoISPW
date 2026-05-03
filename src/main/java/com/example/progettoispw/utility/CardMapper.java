package com.example.progettoispw.utility;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Card;

public final class CardMapper {

    private CardMapper() {
        throw new IllegalStateException("Utility class");
    }


    public static CollectableCardBean toBean(Card card) {
        CollectableCardBean bean = new CollectableCardBean();
        bean.setName(card.getName());
        bean.setPrice(card.getPrice());
        bean.setLevel(card.getLevel());
        bean.setGradation(card.getGradation());
        bean.setType(card.getType());
        bean.setAttribute(card.getAttribute());
        bean.setSeller(card.getSeller());
        return bean;
    }

    public static Card toEntity(CollectableCardBean bean) {
        return new Card(
                bean.getName(),
                bean.getPrice(),
                bean.getGradation(),
                bean.getSeller(),
                bean.getLevel(),
                bean.getAttribute(),
                bean.getType()
        );
    }
}
