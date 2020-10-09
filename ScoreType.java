import java.util.ArrayList;

public class ScoreType {
    String name;
    boolean isNumber;
    boolean isAddAll;
    int diceNumber;
    boolean isUpperSection;

    static boolean firstYahtzeeScored = false;

    //parameters for name, if its number based (top half of score sheet), and if you have to add up all of the dice to get the score
    //topSectionNumber is used so that getScore knows what number to look for in the array (will be the same code for all numbers)

    ScoreType(String n, boolean number, boolean addAll, int dNumber, boolean section) {
        name = n;
        isNumber = number;
        isAddAll = addAll;
        diceNumber = dNumber;
        isUpperSection = section;
    }

    public static int getScore(ScoreType scoretype, ArrayList<Integer> dice) {

        if (scoretype.isNumber)
            //returns the total of all of the 1's, 2's, 3's, added up (for the top section)
            return Game.countDiceNumber(dice, scoretype.diceNumber) * scoretype.diceNumber;

        else if (scoretype.isAddAll) {
            //checking for a valid 3 of a kind
            if (scoretype.name.equals("Three of a Kind")) {
                if (Game.countDiceNumber(dice, 1) >= 3 || Game.countDiceNumber(dice, 2) >= 3 || Game.countDiceNumber(dice, 3) >= 3 || Game.countDiceNumber(dice, 4) >= 3 || Game.countDiceNumber(dice, 5) >= 3 || Game.countDiceNumber(dice, 6) >= 3)
                    return Game.addUpDice(dice);
                else
                    return 0;
            }

            else if (scoretype.name.equals("Four of a Kind")) {
                if (Game.countDiceNumber(dice, 1) >= 4 || Game.countDiceNumber(dice, 2) >= 4 || Game.countDiceNumber(dice, 3) >= 4 || Game.countDiceNumber(dice, 4) >= 4 || Game.countDiceNumber(dice, 5) >= 4 || Game.countDiceNumber(dice, 6) >= 4)
                    return Game.addUpDice(dice);
                else
                    return 0;
                }

            else if (scoretype.name.equals("Chance"))
                return Game.addUpDice(dice);
        }

        else if (scoretype.name.equals("Full House")) {
            //checking for valid full house
            if ((Game.countDiceNumber(dice, 1) == 3 && Game.countDiceNumber(dice, 2) == 2) || (Game.countDiceNumber(dice, 1) == 3 && Game.countDiceNumber(dice, 3) == 2) || (Game.countDiceNumber(dice, 1) == 3 && Game.countDiceNumber(dice, 4) == 2) || (Game.countDiceNumber(dice, 1) == 3 && Game.countDiceNumber(dice, 5) == 2) || (Game.countDiceNumber(dice, 1) == 3 && Game.countDiceNumber(dice, 6) == 2) || (Game.countDiceNumber(dice, 2) == 3 && Game.countDiceNumber(dice, 1) == 2) || (Game.countDiceNumber(dice, 2) == 3 && Game.countDiceNumber(dice, 3) == 2) || (Game.countDiceNumber(dice, 2) == 3 && Game.countDiceNumber(dice, 4) == 2) || (Game.countDiceNumber(dice, 2) == 3 && Game.countDiceNumber(dice, 5) == 2) || (Game.countDiceNumber(dice, 2) == 3 && Game.countDiceNumber(dice, 6) == 2) || (Game.countDiceNumber(dice, 3) == 3 && Game.countDiceNumber(dice, 1) == 2) || (Game.countDiceNumber(dice, 3) == 3 && Game.countDiceNumber(dice, 2) == 2) || (Game.countDiceNumber(dice, 3) == 3 && Game.countDiceNumber(dice, 4) == 2) || (Game.countDiceNumber(dice, 3) == 3 && Game.countDiceNumber(dice, 5) == 2) || (Game.countDiceNumber(dice, 3) == 3 && Game.countDiceNumber(dice, 6) == 2) || (Game.countDiceNumber(dice, 4) == 3 && Game.countDiceNumber(dice, 1) == 2) || (Game.countDiceNumber(dice, 4) == 3 && Game.countDiceNumber(dice, 2) == 2) || (Game.countDiceNumber(dice, 4) == 3 && Game.countDiceNumber(dice, 3) == 2) || (Game.countDiceNumber(dice, 4) == 3 && Game.countDiceNumber(dice, 5) == 2) || (Game.countDiceNumber(dice, 4) == 3 && Game.countDiceNumber(dice, 6) == 2) || (Game.countDiceNumber(dice, 5) == 3 && Game.countDiceNumber(dice, 1) == 2) || (Game.countDiceNumber(dice, 5) == 3 && Game.countDiceNumber(dice, 2) == 3) || (Game.countDiceNumber(dice, 5) == 3 && Game.countDiceNumber(dice, 3) == 2) || (Game.countDiceNumber(dice, 5) == 3 && Game.countDiceNumber(dice, 4) == 2) || (Game.countDiceNumber(dice, 5) == 3 && Game.countDiceNumber(dice, 6) == 2) ||(Game.countDiceNumber(dice, 6) == 3 && Game.countDiceNumber(dice, 1) == 2) || (Game.countDiceNumber(dice, 6) == 3 && Game.countDiceNumber(dice, 2) == 2) || (Game.countDiceNumber(dice, 6) == 3 && Game.countDiceNumber(dice, 3) == 2) || (Game.countDiceNumber(dice, 6) == 3 && Game.countDiceNumber(dice, 4) == 2) || (Game.countDiceNumber(dice, 6) == 3 && Game.countDiceNumber(dice, 5) == 2))
                return 25;
        }

        else if (scoretype.name.equals("Small Straight")) {
            //checking for valid small straight
            if ((dice.contains(1) && dice.contains(2) && dice.contains (3) && dice.contains(4)) || ((dice.contains(2) && dice.contains(3) && dice.contains(4) && dice.contains(5)) || (dice.contains(3) && dice.contains(4) && dice.contains(5) && dice.contains(6))))
                return 30;
            else
                return 0;
        }

        else if (scoretype.name.equals("Large Straight")) {
            //checking for valid large straight
            if ((dice.contains(1) && dice.contains(2) && dice.contains(3) && dice.contains(4) && dice.contains(5)) || (dice.contains(2) && dice.contains(3) && dice.contains(4) && dice.contains(5) && dice.contains(6)))
                return 40;
            else
                return 0;
        }

        else if (scoretype.name.equals("Yahtzee")) {
            //check for valid yahtzee
            if (Game.countDiceNumber(dice, 1) == 5 || Game.countDiceNumber(dice, 2) == 5 || Game.countDiceNumber(dice, 3) == 5 || Game.countDiceNumber(dice, 4) == 5 || Game.countDiceNumber(dice, 5) == 5 || Game.countDiceNumber(dice, 6) == 5)
                return 50;
            else
                return 0;
        }

        else if (scoretype.name.equals("Yahtzee Bonus")) {
            //check for valid yahtzee and that a yahtzee was already scored
            if ((Game.countDiceNumber(dice, 1) == 5 || Game.countDiceNumber(dice, 2) == 5 || Game.countDiceNumber(dice, 3) == 5 || Game.countDiceNumber(dice, 4) == 5 || Game.countDiceNumber(dice, 5) == 5 || Game.countDiceNumber(dice, 6) == 5) && firstYahtzeeScored)
                return 100;
            else
                return 0;
        }
        return 0;
    }
}
