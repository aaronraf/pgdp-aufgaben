package pgdp.game;

import pgdp.InputReader;
import pgdp.RandomNumberGenerator;

public class GuessTheNumber {

    private int lives;
    private int points;

    public GuessTheNumber() {
        lives = 3;
        points = 0;
    }

    public void guessTheNumber() {
        System.out.println("Hello, Number Detective!");

        while (true) {
            showStats();
            printMenu();
            int userInput = InputReader.readInt();

            while (userInput > 4 || userInput < 1) {
                System.out.println("This was not a valid choice, please try again.");
                userInput = InputReader.readInt();
            }

            if (userInput == 4) {
                System.out.println("Goodbye!");
                endGame();
                return;
            }

            int upperBound = 0;
            int maxAttempts = 0;
            int pointsReward = 0;
            int livesReward = 0;

            switch (userInput) {
                case 1 -> {
                    upperBound = 100;
                    maxAttempts = 8;
                    pointsReward = 200;
                }

                case 2 -> {
                    upperBound = 500;
                    maxAttempts = 10;
                    pointsReward = 200;
                    livesReward = 1;
                }

                case 3 -> {
                    upperBound = 1000;
                    maxAttempts = 10;
                    pointsReward = 500;
                    livesReward = 3;
                }
            }

            int number = RandomNumberGenerator.getGenerator().generate(upperBound);
            boolean numberGuessed = false;
            for (int i = 1; i <= maxAttempts; i++) {
                if (i == maxAttempts && points >= 600) {
                    System.out.println("LAST ATTEMPT! Do you want to buy a hint for 600 points? (1) yes (2) no");
                    userInput = InputReader.readInt();
                    while (userInput < 1 || userInput > 2) {
                        System.out.println("This was not a valid choice, please try again.");
                        userInput = InputReader.readInt();
                    }

                    if (userInput == 1) {
                        points -= 600;
                        System.out.println("The number is " + (number % 2 == 0 ? "even" : "odd") + "!");
                    }
                }

                System.out.println("(" + i + "/" + maxAttempts + ") Enter your guess:");
                userInput = InputReader.readInt();

                if (i == maxAttempts && userInput != number) {
                    break;
                } else if (number < userInput) {
                    System.out.println("The number is lower.");
                } else if (number > userInput) {
                    System.out.println("The number is higher.");
                } else {
                    System.out.println("Congrats! You guessed the correct number.");
                    this.points += pointsReward;
                    this.lives += livesReward;
                    numberGuessed = true;
                    break;
                }
            }

            if (!numberGuessed) {
                System.out.println("Sorry, you've used all attempts. The correct number was " + number + ".");
                lives--;
                if (this.lives == 0) {
                    System.out.println("Game over! You are out of lives.");
                    endGame();
                    break;
                }
            }
        }
    }

    private void endGame() {
        System.out.println("You are leaving with " + points + " points!");
    }

    private void showStats() {
        System.out.println("You have " + lives + " lives and " + points + " points.");
    }

    // <==================================== HELPER METHODS ====================================>

    private void printMenu() {
        System.out.println("Choose difficulty level to start a new game:\n" +
                "(1) Easy   [0;100)   8 Attempts, Reward: +200 Points\n" +
                "(2) Medium [0;500)  10 Attempts, Reward: +200 Points +1 Life\n" +
                "(3) Hard   [0;1000) 10 Attempts, Reward: +500 Points +3 Lives\n" +
                "(4) Exit");
    }

    public static void main(String[] args) {
        new GuessTheNumber().guessTheNumber();
    }

}
