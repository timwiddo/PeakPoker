package hwr.oop.projects.peakpoker.core.cards

import hwr.oop.projects.peakpoker.core.card.Card
import hwr.oop.projects.peakpoker.core.card.CommunityCards
import hwr.oop.projects.peakpoker.core.card.Rank
import hwr.oop.projects.peakpoker.core.card.Suit
import hwr.oop.projects.peakpoker.core.exceptions.DuplicateCardException
import hwr.oop.projects.peakpoker.core.exceptions.InvalidCardConfigurationException
import hwr.oop.projects.peakpoker.core.game.GameId
import hwr.oop.projects.peakpoker.core.game.GameInterface
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class CommunityCardsTest : AnnotationSpec() {

  private val mockGame = object : GameInterface {
    override val id: GameId = GameId("mockGame")
  }

  @Test
  fun `CommunityCards should contain exactly five cards`() {
    val cards = listOf(
      Card(Suit.DIAMONDS, Rank.FIVE),
      Card(Suit.DIAMONDS, Rank.TEN),
      Card(Suit.SPADES, Rank.ACE),
      Card(Suit.CLUBS, Rank.KING),
      Card(Suit.HEARTS, Rank.QUEEN)
    )

    val communityCards = CommunityCards(cards, mockGame)

    assertThat(communityCards.cards).hasSize(5)
  }

  @Test
  fun `CommunityCards should throw exception when less than 5 cards are provided`() {
    val cards = listOf(
      Card(Suit.DIAMONDS, Rank.FIVE),
      Card(Suit.DIAMONDS, Rank.TEN),
      Card(Suit.SPADES, Rank.ACE),
      Card(Suit.CLUBS, Rank.KING)
    )

    assertThatThrownBy { CommunityCards(cards, mockGame) }
      .isExactlyInstanceOf(InvalidCardConfigurationException::class.java)
      .hasMessageContaining("exactly five cards")
  }

  @Test
  fun `CommunityCards should throw exception when more than 5 cards are provided`() {
    val cards = listOf(
      Card(Suit.DIAMONDS, Rank.FIVE),
      Card(Suit.DIAMONDS, Rank.TEN),
      Card(Suit.SPADES, Rank.ACE),
      Card(Suit.CLUBS, Rank.KING),
      Card(Suit.HEARTS, Rank.QUEEN),
      Card(Suit.HEARTS, Rank.JACK)
    )

    assertThatThrownBy { CommunityCards(cards, mockGame) }
      .isExactlyInstanceOf(InvalidCardConfigurationException::class.java)
      .hasMessageContaining("exactly five cards")
  }

  @Test
  fun `CommunityCards should not allow duplicate cards`() {
    val duplicateCard = Card(Suit.DIAMONDS, Rank.FIVE)
    val cards = listOf(
      duplicateCard,
      Card(Suit.DIAMONDS, Rank.TEN),
      Card(Suit.SPADES, Rank.ACE),
      Card(Suit.CLUBS, Rank.KING),
      duplicateCard
    )

    assertThatThrownBy { CommunityCards(cards, mockGame) }
      .isExactlyInstanceOf(DuplicateCardException::class.java)
      .hasMessageContaining("duplicates")
  }

  @Test
  fun `CommunityCards should work with empty list initialization`() {
    val communityCards = CommunityCards(emptyList(), mockGame)

    assertThat(communityCards.cards).isEmpty()
  }

  @Test
  fun `CommunityCards should implement Iterable interface correctly`() {
    val cards = listOf(
      Card(Suit.DIAMONDS, Rank.FIVE),
      Card(Suit.DIAMONDS, Rank.TEN),
      Card(Suit.SPADES, Rank.ACE),
      Card(Suit.CLUBS, Rank.KING),
      Card(Suit.HEARTS, Rank.QUEEN)
    )

    val communityCards = CommunityCards(cards, mockGame)
    val iteratedCards = communityCards.toList()

    assertThat(iteratedCards).containsExactlyElementsOf(cards)
  }
}
