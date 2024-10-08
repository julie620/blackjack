
// DeckOfCards.java
//DeckOfCards class represents a deck of playing cards.
import java.security.SecureRandom;
import java.util.Scanner;

public class DeckOfCards {
    // random number generator
    private static final SecureRandom randomNumbers = new SecureRandom();
    private static final Scanner input = new Scanner(System.in);
    private static final int NUMBER_OF_CARDS = 52;

    private Card[] deck = new Card[NUMBER_OF_CARDS]; // Card references
    private int currentCard = 0; // index of next Card to be dealt (0 - 51)
    private int dealerValue = 0; // dealer's total hand value
    private int playerValue = 0; // player's total hand value
    private int playerCredits = 10; // start the player off with 10 credits
    private String dealerHand = "Dealer's Hand: ";
    private String playerHand = "Player's Hand: ";

    public int printPlayerCredits() {
        System.out.println("Credits: " + playerCredits);
        return playerCredits;
    }

    // introduction method
    public void intro() {
        System.out.println("Blackjack Game");
        System.out.println("");
        System.out.println("Welcome to Whatcom Community College Casino: WCCC");
        System.out.println("Rules: Players are each dealt 2 cards and given the option to Hit, or Stand (h/s).");
        System.out.println("(h) Hit (another card is dealt)");
        System.out.println("(s) Stand (stay with current hand)");
        System.out.println("The player closest to 21 without going over 21 wins.");
        System.out.println("10, Jack, Queen, and King's all have a value of 10. Ace has a value of 1.");
        System.out.println("You will start with 10 credits.");
        System.out.println("You can win 1 credit by beating the dealer");
        System.out.println("You can win 2 credits by getting a total of 21.");
        System.out.println("If you go over 21 or the dealer wins, you lose 1 credit");
        System.out.println("Good Luck!");
        System.out.println("");
    } // end introduction

    // constructor fills deck of Cards
    public DeckOfCards() {
        String[] faces = { "Ace", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "Jack", "Queen", "King" };
        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };

        // populate deck with Card objects
        for (int count = 0; count < deck.length; count++) {
            deck[count] = new Card(faces[count % 13], suits[count / 13]);
        } // end of for loop
    } // end of DeckofCards Constructor

    // shuffle deck of Cards with one-pass algorithm
    public void shuffle() {
        // next call to method dealCard should start at deck[0] again
        currentCard = 0;

        // for each Card, pick another random Card (0-51) and swap them
        for (int first = 0; first < deck.length; first++) {
            // selct a random number between 0 and 51
            int second = randomNumbers.nextInt(NUMBER_OF_CARDS);

            // swap current Card with randomly selected Card
            Card temp = deck[first];
            deck[first] = deck[second];
            deck[second] = temp;
        } // end for loop
    } // end of shuffle method

    // deal one card
    public Card dealCard() {
        // determine whether Cards remain to be dealt
        if (currentCard < deck.length) {
            return deck[currentCard++]; // return current Card in array
        } else {
            return null; // return null to indicate that all cards were dealt
        } // end if/else
    } // end of deal Card method

    // deals a card to the player
    public void dealToPlayer() {
        dealCard();
        playerValue += deck[currentCard].getValue(); // adds up player's total hand
        if (currentCard == 1) { // prints first card in player's hand
            playerHand = playerHand.concat(deck[currentCard].toString());
        } else { // adds spaces between previous card and new card
            playerHand = playerHand.concat("    " + deck[currentCard].toString());
        } // end if/else
    } // end of dealToPlayer method

    // deals card to the dealer
    public void dealToDealer() {
        dealCard();
        dealerValue += deck[currentCard].getValue(); // add up dealer's total hand
        if (currentCard == 4) { // prints the dealer's unknown card
            dealerHand = dealerHand.concat("     Unknown_Card");
        } else { // prints known cards
            dealerHand = dealerHand.concat(currentCard == 2 ? deck[currentCard].toString()
                    : "    " + deck[currentCard].toString());
        } // end if/else
    } // end of dealToDealer

    // deals out the first two cards for the player and dealer
    public void initialDeal() {
        dealToPlayer();
        dealToDealer();
        dealToPlayer();
        dealToDealer();
        System.out.println(playerHand);
        System.out.println(dealerHand);
    } // end of intialDeal

    // checks player's hand while they hit/stand
    public Boolean cardCheck() {
        if (playerValue > 21) {
            System.out.println("\nBust!");
            printPlayerCredits(); // print the players credits
            resetGame(); // reset the values for new game
            playAgain(); // ask if player would like too play again
            return false;
        } else if (playerValue == 21) {
            System.out.println("\nYou have 21! Press [Enter] to see the dealer's play.");
            String enter = input.nextLine();
            if (enter.equals("")) {
                playerCredits += 2; // Add 2 credits for a 21
                printPlayerCredits(); // print the players credits
                resetGame(); // reset the values for new game
                playAgain(); // ask if player would like too play again
                return false;
            }
        } // end of if/else

        return true;
    }

    // goes throungh the dealer's play
    public void dealerPlay() {
        // reveals the dealer's unknown card
        String regex = "Unknown_Card";
        dealerHand = dealerHand.replaceAll(regex, deck[4].toString());
        System.out.println(dealerHand);
        int drawCount = 0;// tracks how many cards the dealer drew

        // dealer will take cards as long as value is less than 17
        while (dealerValue < 17) {
            dealToDealer();
            drawCount++;
        } // end while loop

        System.out.print("\nThe Dealer took " + drawCount);
        System.out.println(drawCount == 1 ? " card.\n" : " cards.\n");
    }

    // compares the player's and dealer's total after each have finished their play
    public void finalCheck() {
        System.out.println("\nPlayer's Total: " + playerValue);
        System.out.println("Dealer's Total: " + dealerValue);
        if (dealerValue == playerValue) {
            System.out.println("\nIt's a draw!");
        } else if (playerValue > dealerValue || dealerValue > 21) {
            System.out.println("\nYou win!");
            playerCredits += 1; // Add 1 credit for a win
        } else {
            System.out.println("\nYou Lose!");
            playerCredits -= 1; // Lose 1 credit for a loss
        } // end of if/else
    } // end of finalCheck method

    // plays through a single game
    public void game() {

        initialDeal();

        while (cardCheck()) {
            System.out.print("\nHit or Stand (h/s) ");
            String response = input.nextLine();
            System.out.println();

            if (response.equalsIgnoreCase("h")) {
                dealToPlayer();
                System.out.println(playerHand);
            } else if (response.equalsIgnoreCase("s")) {
                System.out.println("Your total is: " + playerValue);
                System.out.println("\nPress [Enter] to see the dealer's play.");
                String enter = input.nextLine();
                if (enter.equals("")) {
                    break;
                }
            } else {
                System.out.println("Your response is invalid. Please try again.");
            } // end of if/else
        } // end while loop

        // executes if player doesn't go bust
        if (playerValue <= 21) {
            dealerPlay();
            System.out.println(playerHand);
            System.out.println(dealerHand);
            finalCheck();
            printPlayerCredits();
            System.out.println("");
            resetGame();
            playAgain();
        } // end of if
    }

    // prompt the user to play again if remaining credits
    public void playAgain() {
        if (playerCredits > 0) {
            System.out.println("Would you like to play again? (y/n): ");
            String again = input.nextLine();

            if (again.equalsIgnoreCase("y")) {
                System.out.println("");
                shuffle(); // re-shuffle
                game(); // play another hand
            } else {
                System.out.println("Thank you for playing.");
            }
        } else {
            System.out.println("Game Over");
        }
    }

    // reset values for new game
    public void resetGame() {
        playerValue = 0;
        dealerValue = 0;
        playerHand = "Player's Hand: ";
        dealerHand = "Dealer's Hand: ";
    }
}
