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
    private int dealerValue = 0;
    private int playerValue = 0;
    private String dealerHand = "Dealer's Hand: ";
    private String playerHand = "Player's Hand: ";

    // constructor fills deck of Cards
    public DeckOfCards () {
        String[] faces = {"Ace", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

        //populate deck with Card objects
        for (int count = 0; count < deck.length; count++) {
            deck[count] =
                new Card(faces[count % 13], suits[count / 13]);
        } // end of for loop
    } // end of DeckofCards Constructor

    // shuffle deck of Cards with one-pass algorithm
    public void shuffle() {
        // next call to method dealCard should start at deck[0] again
        currentCard = 0;

        //for each Card, pick another random Card (0-51) and swap them 
        for (int first = 0; first < deck.length; first++) {
            //selct a random number between 0 and 51
            int second = randomNumbers.nextInt(NUMBER_OF_CARDS);

            // swap current Card with randomly selected Card
            Card temp = deck[first];
            deck[first] = deck [second];
            deck[second] = temp;
        } // end for loop
    } // end of shuffle method

    //deal one card
    public Card dealCard() {
        //determine whether Cards remain to be dealt
        if (currentCard < deck.length) {
            return deck[currentCard++]; // return current Card in array
        }
        else {
            return null; // return null to indicate that all cards were dealt
        } //end if/else
    } // end of deal Card method

    public void dealToPlayer() {
        dealCard();
        playerValue += deck[currentCard].getValue();
        if (currentCard == 1) {
            playerHand = playerHand.concat(deck[currentCard].toString());
        }
        else {
            playerHand = playerHand.concat("    " + deck[currentCard].toString());
        }
    }

    public void dealToDealer() {
        dealCard();
        dealerValue += deck[currentCard].getValue();
        if (currentCard == 4) {
            dealerHand = dealerHand.concat("     Unknown_Card");
        }
        else {
            dealerHand = dealerHand.concat(currentCard == 2 ? deck[currentCard].toString()
                : "    " + deck[currentCard].toString());
        }
    }

    public void initialDeal() {
        dealToPlayer();
        dealToDealer();
        dealToPlayer();
        dealToDealer();
        System.out.println(playerHand);
        System.out.println(dealerHand);
    }

    public Boolean cardCheck() {
        if (playerValue > 21) {
            System.out.println("\nBust!");
            return false;
        }
        else if (playerValue == 21) {
            System.out.println("\nYou have 21! Press [Enter] to see the dealer's play.");
            String enter = input.nextLine();
            if (enter.equals("")) {
                return false;
            }
        }
        return true;
    }

    public void dealerPlay() {
        String regex = "Unknown_Card";
        dealerHand = dealerHand.replaceAll(regex, deck[4].toString());
        System.out.println(dealerHand);
        int drawCount = 0;
        //dealer will take cards as long as value is less than 17
        while (dealerValue < 17) { 
            dealToDealer();
            drawCount++;
        }
        System.out.print("\nThe Dealer took " + drawCount);
        System.out.println(drawCount == 1 ? " card.\n" : " cards.\n");
    }

    public void finalCheck() {
        System.out.println("\nPlayer's Total: " + playerValue);
        System.out.println("Dealer's Total: " + dealerValue);
        if (dealerValue == playerValue) {
            System.out.println("\nIt's a draw!");
        }
        else if (playerValue > dealerValue || dealerValue > 21){
            System.out.println("\nYou win!");
        }
        else {
            System.out.println("\nYou Lose!");
        }
    }

    public void game() {
        //intro method
        initialDeal();
        while (cardCheck()) {
            System.out.print("\nHit or Stand (h/s) ");
            String response = input.nextLine();
            System.out.println();
            if (response.equalsIgnoreCase("h")) {
                dealToPlayer();
                System.out.println(playerHand);
            }
            else if (response.equalsIgnoreCase("s")) {
                System.out.println("Your total is: " + playerValue);
                System.out.println("\nPress [Enter] to see the dealer's play.");
                String enter = input.nextLine();
                if (enter.equals("")) {
                    break;
                }
            }
            else {
                System.out.println("Your response is invalid. Please try again.");
            }
        }
        if (playerValue <= 21) {
            dealerPlay();
            System.out.println(playerHand);
            System.out.println(dealerHand);
            finalCheck();
        }
    }
}
