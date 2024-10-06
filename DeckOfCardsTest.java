// DeckOfCardsTest.java
// Card shuffling and dealing

public class DeckOfCardsTest {
    // execute application
    public static void main(String[] args) {
        DeckOfCards myDeckOfCards = new DeckOfCards();
        myDeckOfCards.shuffle(); // place Cards to random order

        myDeckOfCards.game(); // plays through a single game

    } // end of main method
} // end of DeckOfCardsTest class