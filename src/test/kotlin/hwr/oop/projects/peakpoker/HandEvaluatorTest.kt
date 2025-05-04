package hwr.oop.projects.peakpoker

import hwr.oop.projects.peakpoker.core.card.Card
import hwr.oop.projects.peakpoker.core.card.Rank
import hwr.oop.projects.peakpoker.core.card.Suit
import hwr.oop.projects.peakpoker.core.deck.Deck
import hwr.oop.projects.peakpoker.core.hand.HandEvaluator
import hwr.oop.projects.peakpoker.core.hand.HandRanking
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat



class HandEvaluatorTest : AnnotationSpec() {

    @Test
    fun `flush wird erkannt`() {
        // fünf Karten gleicher Farbe, aber nicht in Folge
        val cards = listOf(
            Card(Suit.HEARTS.name, Rank.TWO.name),
            Card(Suit.HEARTS.name, Rank.FIVE.name),
            Card(Suit.HEARTS.name, Rank.NINE.name),
            Card(Suit.HEARTS.name, Rank.JACK.name),
            Card(Suit.HEARTS.name, Rank.KING.name)
        )

        val ranking = HandEvaluator.evaluate(cards)
        assertThat(ranking).isEqualTo(HandRanking.FLUSH)
    }

    @Test
    fun `straight wird erkannt`() {
        // fünf aufeinanderfolgende Werte, unterschiedliche Farben
        val cards = listOf(
            Card(Suit.CLUBS.name, Rank.FOUR.name),
            Card(Suit.HEARTS.name, Rank.FIVE.name),
            Card(Suit.DIAMONDS.name, Rank.SIX.name),
            Card(Suit.SPADES.name, Rank.SEVEN.name),
            Card(Suit.CLUBS.name, Rank.EIGHT.name)
        )

        val ranking = HandEvaluator.evaluate(cards)
        assertThat(ranking).isEqualTo(HandRanking.STRAIGHT)
    }

    @Test
    fun `vierling wird erkannt`() {
        // vier Karten gleichen Rangs + eine beliebige Fünfte
        val cards = listOf(
            Card(Suit.HEARTS.name, Rank.ACE.name),
            Card(Suit.DIAMONDS.name, Rank.ACE.name),
            Card(Suit.CLUBS.name, Rank.ACE.name),
            Card(Suit.SPADES.name, Rank.ACE.name),
            Card(Suit.HEARTS.name, Rank.THREE.name)
        )

        val ranking = HandEvaluator.evaluate(cards)
        assertThat(ranking).isEqualTo(HandRanking.FOUR_OF_A_KIND)
    }

    @Test
    fun `full house wird erkannt`() {
        // drei gleiche + zwei gleiche
        val cards = listOf(
            Card(Suit.HEARTS.name, Rank.KING.name),
            Card(Suit.DIAMONDS.name, Rank.KING.name),
            Card(Suit.CLUBS.name, Rank.KING.name),
            Card(Suit.HEARTS.name, Rank.QUEEN.name),
            Card(Suit.SPADES.name, Rank.QUEEN.name)
        )

        val ranking = HandEvaluator.evaluate(cards)
        assertThat(ranking).isEqualTo(HandRanking.FULL_HOUSE)
    }

    @Test
    fun `high card wird erkannt, wenn nichts anderes passt`() {
        val cards = listOf(
            Card(Suit.HEARTS.name, Rank.TWO.name),
            Card(Suit.DIAMONDS.name, Rank.FIVE.name),
            Card(Suit.CLUBS.name, Rank.NINE.name),
            Card(Suit.SPADES.name, Rank.JACK.name),
            Card(Suit.HEARTS.name, Rank.KING.name)
        )

        val ranking = HandEvaluator.evaluate(cards)
        assertThat(ranking).isEqualTo(HandRanking.HIGH_CARD)
    }

    @Test
    fun `evaluate wirft IllegalArgumentException bei Duplikaten`() {
        // given: fünf Karten, wobei zwei identisch sind
        val duplicateCard = Card(Suit.HEARTS.name, Rank.ACE.name)
        val hand = listOf(
            duplicateCard,
            duplicateCard,
            Card(Suit.DIAMONDS.name, Rank.KING.name),
            Card(Suit.CLUBS.name, Rank.QUEEN.name),
            Card(Suit.SPADES.name, Rank.JACK.name)
        )

        // expect
        shouldThrow<IllegalArgumentException> {
            HandEvaluator.evaluate(hand)
        }
    }
}