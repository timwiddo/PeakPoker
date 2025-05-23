package hwr.oop.projects.peakpoker.core.deck

import hwr.oop.projects.peakpoker.core.card.Card

class Deck {

    private val suits = listOf("Hearts", "Diamonds", "Clubs", "Spades")
    private val ranks = listOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")

    private val originalOrder: List<Card>

    // TODO: Evaluate if this needs to be mutable
    private var cards: MutableList<Card>


    init {
        val tempList = suits.flatMap { suit ->
            ranks.map { rank ->
                Card(suit, rank)
            }
        }

        originalOrder = tempList
        cards = tempList.toMutableList()
    }

    fun peak(): List<Card> {
        return cards.toList()
    }

    fun shuffle() {
        val beforeShuffle = cards.toList()
        cards.shuffle()

        // Check if shuffle was successful
        val differentPositions = beforeShuffle.zip(cards).count { (before, after) -> before != after }
        if (differentPositions == 0) {
            shuffle()
        }
    }
}
