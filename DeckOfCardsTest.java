// DeckOfCardsTest.java
// Card shuffling and dealing

public class DeckOfCardsTest {
    // execute application
    public static void main(String[] args) {
        DeckOfCards myDeckOfCards = new DeckOfCards();
        myDeckOfCards.intro(); // print instructions
        int playCount = 0;
        while (myDeckOfCards.playAgain(playCount)) {
            myDeckOfCards.shuffle();
            myDeckOfCards.game(); // plays through a single game
            playCount++;
        }

    } // end of main method
} // end of DeckOfCardsTest class
