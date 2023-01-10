package game;

public class Die
{
    private final int MAX = 6;  // højeste antal øjne

    private GameController gameController;

    private int faceValue;  // antal øjne der vises pt

    public Die() // vores constructor
    {
        faceValue = 1;
    }

    public  int roll() // ruller terningen og returnerer antal øjne
    {
        faceValue = (int)(Math.random() * MAX) + 1;

        return faceValue;
    }

    public int getFaceValue() // Bruges til at finde ud af hvad den seneste faceValue er(blevet rullet til)
    {
        return faceValue;
    }

    public String toString() // Omformaterer faceValue til en String der bliver til et tal mellem 1-6
    {
        String result = Integer.toString(faceValue);

        return result;
    }

    public boolean EqualRolls(){

    String roll1 = gameController.getCurrentDieRoll1();
    String roll2 = gameController.getCurrentDieRoll2();
        return equals(roll2 roll1);
    


    }


}