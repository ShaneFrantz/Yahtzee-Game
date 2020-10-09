import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    //for testing without having to wait for things
    boolean skip = false;
    boolean goToScoreScreen = false;

    //declaring all ScoreType variables

    ScoreType ones = new ScoreType("Ones", true, false, 1, true);
    ScoreType twos = new ScoreType("Twos", true, false, 2, true);
    ScoreType threes = new ScoreType("Threes", true, false, 3, true);
    ScoreType fours = new ScoreType("Fours", true, false,4, true);
    ScoreType fives = new ScoreType("Fives", true, false, 5, true);
    ScoreType sixes = new ScoreType("Sixes", true, false,6, true);

    ScoreType threeOfAKind = new ScoreType("Three of a Kind", false, true, 0, false);
    ScoreType fourOfAKind = new ScoreType("Four of a Kind", false, true, 0, false);
    ScoreType fullHouse = new ScoreType("Full House", false, false, 0, false);
    ScoreType smallStraight = new ScoreType("Small Straight", false, false, 0, false);
    ScoreType largeStraight = new ScoreType("Large Straight", false, false, 0, false);
    ScoreType yahtzee = new ScoreType("Yahtzee", false, false, 0, false);
    ScoreType chance = new ScoreType("Chance", false, true, 0, false);
    ScoreType yahtzeeBonus = new ScoreType("Yahtzee Bonus", false, false, 0, false);


    ArrayList<ScoreType> scoreTypes = populateScoreTypes();

    public ArrayList<ScoreType> populateScoreTypes() {

        //populating scoreTypes

        ArrayList<ScoreType> scores = new ArrayList<ScoreType>();

        scores.add(ones);
        scores.add(twos);
        scores.add(threes);
        scores.add(fours);
        scores.add(fives);
        scores.add(sixes);

        scores.add(threeOfAKind);
        scores.add(fourOfAKind);
        scores.add(fullHouse);
        scores.add(smallStraight);
        scores.add(largeStraight);
        scores.add(yahtzee);
        scores.add(chance);
        scores.add(yahtzeeBonus);

        return scores;

    }


    Scanner scan = new Scanner(System.in);
    int savedNumber = -1;
    int rollNumber = 1;
    //initializing diceToRoll to 5 (changes with each roll to match array size)
    int diceToRoll = 5;

    ArrayList<Integer> dice = new ArrayList<Integer>();
    ArrayList<Integer> savedDice = new ArrayList<Integer>();

    int upperSectionScore = 0;
    int lowerSectionScore = 0;

    int yahtzeesScored = 0;

    String name;

    //METHOD TO START GAME
    public void start() {

        int score = 0;

        System.out.println("What is your name?");
        name = scan.nextLine();

        System.out.print("Hi " + name + "! Welcome to Yahtzee!");

        wait(4);
        clearScreen();

        //rolls all 5 dice for first roll, then the number of dice not saved for the remaining rolls

        //stops rolling when the numbers of scores to get is empty EXCLUDING yahtzee bonus

        while (scoreTypes.size() != 1) {

            goToScoreScreen = false;
            savedDice.clear();
            while (rollNumber <= 3) {

                if (diceToRoll == 0) {
                    goToScoreScreen = true;
                    break;
                }


                dice = rollDice(diceToRoll, rollNumber);
                if (rollNumber != 1)
                    System.out.println("Saved Numbers: " + savedDice + "\n");
                System.out.println("Rolled Numbers: " + dice + "\n");
                if (rollNumber != 3)
                    System.out.print("Which numbers would you like to save? (Type \"-\" then the number to \"un-save\" the number. Type \"0\" to re-roll your unsaved dice.)\n\nNumber: ");

                if (rollNumber == 3) {
                    wait(3);
                    goToScoreScreen = true;
                    break;
                }

                while (savedNumber != 0) {

                    if (rollNumber != 3)
                        saveNumbers();
                }

                //flag to check if going to score screen (skips the code following this)
                if (goToScoreScreen) {
                    goToScoreScreen = false;
                    break;
                }

                clearScreen();
                rollNumber++;
                //resets savedNumber
                savedNumber = -1;
                diceToRoll = dice.size();

            }
            //putting all of the dice into one array to make it easier to handle when coding for the score screen
            dice.addAll(savedDice);
            scoreScreen();

            clearScreen();

            //initializing dice variables
            savedNumber = -1;
            rollNumber = 1;
            //initializing diceToRoll to 5 (changes with each roll to match array size)
            diceToRoll = 5;
        }

        endScreen();

    }


    //method to roll dice
    public ArrayList<Integer> rollDice(int numberOfDice, int rollNumber) {
        ArrayList<Integer> rolledDice = new ArrayList<Integer>();

        System.out.println("ROLL #" + rollNumber + ": \n");

        //populating rolledDice with random numbers 1-6
        for (int i = 0; i < numberOfDice; i++) {
            rolledDice.add((int)(Math.random() * 6) + 1);
        }

        return rolledDice;
    }

    public void wait(int seconds) {
        if ((!skip)) {
            try {
                Thread.sleep(seconds * 1000);
            }
            catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getInt() {
        try {
            int number = scan.nextInt();
            return number;
        }
        catch (Exception e) {
            System.out.print("\nThat is not a valid number. Please pick another number.\nNumber: ");
            scan.next();
            return getInt();
        }
    }

    public void clearScreen() {
        for (int i = 0; i < 1000; i++)
            System.out.println("\n");
    }

    public void scoreScreen() {
        clearScreen();

        System.out.print("Current Dice: " + dice + "\n\n");

        System.out.print("Please pick the corresponding number of the category that you want to add to your score.\n\n\n");

        //printing out numbers, categories, and the scores for each category

        for (int i = 0; i < scoreTypes.size(); i++) {

            System.out.print((i + 1) + ". " + scoreTypes.get(i).name + " (" + ScoreType.getScore(scoreTypes.get(i), dice) + ")\n\n");
        }
        System.out.print("Number: ");

        //checking for valid score number
        while (true) {
            int scoreNumber = getInt();
            if (scoreNumber < 1 || scoreNumber > scoreTypes.size()) {
                System.out.print("\nThat is not a valid number. Please pick another number.\nNumber: ");
            }
            else {

                //checking if the user inputs a NON VALID yahtzee bonus
                if (scoreTypes.get(scoreNumber - 1).name.equals("Yahtzee Bonus") && !(ScoreType.firstYahtzeeScored)) {
                    System.out.print("\nYou have selected Yahtzee Bonus without having first scored a Yahtzee. Please pick another number.\nNumber: ");
                    continue;
                }

                clearScreen();
                //displaying score message and adding score to upper or lower section
                System.out.println("You scored " + ScoreType.getScore(scoreTypes.get(scoreNumber - 1), dice) + " points with \"" + scoreTypes.get(scoreNumber - 1).name + "\"!");
                if (scoreTypes.get(scoreNumber - 1).isUpperSection)
                    upperSectionScore += ScoreType.getScore(scoreTypes.get(scoreNumber - 1), dice);
                else
                    lowerSectionScore += ScoreType. getScore(scoreTypes.get(scoreNumber - 1), dice);
                wait(4);
                //deletes score from the list if it's not Yahtzee Bonus (Yahtzee Bonus never deletes as you can get an infinite number)
                if (!(scoreTypes.get(scoreNumber - 1).name.equals("Yahtzee Bonus")))
                    scoreTypes.remove(scoreNumber - 1);
                //adds 1 to the yahtzee total if one is rolled

                //checking if yahtzee/yahtzee bonus score is not equal to 0, meaning that a yahtzee was actually scored rather than an empty store
                if ((scoreTypes.get(scoreNumber - 1).name.equals("Yahtzee") || scoreTypes.get(scoreNumber - 1).name.equals("Yahtzee Bonus")) && (!(ScoreType.getScore(scoreTypes.get(scoreNumber - 1), dice) == 0))) {
                    yahtzeesScored++;
                }

                return;
            }
        }
    }

    //method to save numbers to a separate dice array to allow users to not have to re-roll every single dice on each roll
    public void saveNumbers() {

        savedNumber = getInt();
        clearScreen();
        //FOR SAVING
        if (savedNumber > 0) {

            if (!(dice.contains(savedNumber))) {
                clearScreen();
                System.out.println("That number is not available to save. Please pick a valid number. (0 to stop saving/un-saving numbers)");
                System.out.println("\nSaved Numbers: " + savedDice);
                System.out.print("\nUnsaved Numbers: " + dice + "\n\nNumber: ");
            }

            else {
                savedDice.add(savedNumber);
                dice.remove((Integer) savedNumber);

                System.out.println("Saved Numbers: " + savedDice);
                System.out.println("\nUnsaved Numbers: " + dice);
                System.out.print("\n" + savedNumber + " saved. Type another number to save/un-save (0 to stop saving/un-saving numbers.)\n\nNumber: ");
            }
        }
        //FOR UN-SAVING
        else if (savedNumber < 0) {

            if (!(savedDice.contains(Math.abs(savedNumber)))) {
                clearScreen();
                System.out.println("That number was not saved. Please pick a valid number (0 to stop saving/un-saving numbers)");
                System.out.println("\nSaved Numbers: " + savedDice);
                System.out.print("\nUnsaved Numbers: " + dice + "\n\nNumber: ");
            }

            else {
                savedDice.remove((Integer) Math.abs(savedNumber));
                dice.add(Math.abs(savedNumber));

                System.out.println("Saved Numbers: " + savedDice);
                System.out.println("\nUnsaved Numbers: " + dice);
                System.out.print("\n" + Math.abs(savedNumber) + " un-saved. Type another number to save/un-save (0 to stop saving/un-saving numbers.)\n\nNumber: ");
            }
        }
    }

    //adding up all instances of a certain dice number in the array
    public static int countDiceNumber(ArrayList<Integer> dice, int numberToCount) {
        int totalOfDiceNumber = 0;
        for (int i = 0; i < dice.size(); i++) {
            if (dice.get(i) == numberToCount)
                totalOfDiceNumber++;
        }
        return totalOfDiceNumber;
    }

    public static int addUpDice(ArrayList<Integer> dice) {
        int total = 0;
        for (int i = 0; i < dice.size(); i++)
            total += dice.get(i);
        return total;
    }

    //displays the player's total score at the end of the game (upper section, lower section, and total score)
    public void endScreen() {
        clearScreen();
        //will check if section scores are equal to 1 for grammatical reasons
        if (!(upperSectionScore == 1))
            System.out.print("You scored a total of " + upperSectionScore + " points in the upper section.\n");
        else
            System.out.print("You scored a total of 1 point in the upper section.\n");

        //checking for bonus in upper score section
        if (upperSectionScore >= 63) {
            upperSectionScore += 35;
            System.out.print("\nYour upper section score was 63 or higher, earning you a bonus of 35 points.\n\nThis makes your new upper section total " + upperSectionScore + " points.\n");
        }

        //printing lower section score
        //mathematically impossible to score 1 point in lower section, no need to fix grammar here
        System.out.print("\nYou scored a total of " + lowerSectionScore + " points in the lower section.\n");

        //fixing grammar if user only rolled 1 yahtzee
        if (yahtzeesScored == 1)
            System.out.print("\nYou also rolled 1 Yahtzee this game!\n");
        else
            System.out.print("\nYou also rolled " + yahtzeesScored + " Yahtzees this game!\n");

        System.out.print("\nThat brings you to a total of...\n\n\n" + (upperSectionScore + lowerSectionScore) + " POINTS!\n\n" + name + ", Thank you for playing!");


    }
}
