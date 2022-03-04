package br.com.monitoratec.farol.graphql.model.dtos.payment;

import java.util.List;

public class CardDTOWrapper {
    private List<CardDTO> cards;

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }
}
