package com.example.progettoispw.utility;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Card;

/**
 * Utility class per la conversione tra entità Card del Model e CollectableCardBean del layer View.
 * Centralizza il mapping ed elimina la duplicazione nei controller logici.
 */
public final class CardMapper {

    private CardMapper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converte un'entità Card in un CollectableCardBean da passare alla View.
     */
    public static CollectableCardBean toBean(Card card) {
        CollectableCardBean bean = new CollectableCardBean();
        bean.setNomeCarta(card.getNome());
        bean.setPrezzoCorrente(card.getPrezzoAttuale());
        bean.setLivello(card.getLivello());
        bean.setGradazione(card.getGradazione());
        bean.setTipo(card.getTipo());
        bean.setAttributo(card.getAttributo());
        bean.setVenditore(card.getVenditore());
        return bean;
    }

    /**
     * Converte un CollectableCardBean (proveniente dalla View) in un'entità Card del Model.
     */
    public static Card toEntity(CollectableCardBean bean) {
        return new Card(
                bean.getNomeCarta(),
                bean.getPrezzoCorrente(),
                bean.getGradazione(),
                bean.getVenditore(),
                bean.getLivello(),
                bean.getAttributo(),
                bean.getTipo()
        );
    }
}
