package chancecards;

import fields.GameBoard;
import game.Player;

public class MoveCard extends ChanceCard{
    int amount;
    MoveCard(String text, int amount){
        super(text);
        this.amount = amount;
    }

    public void execute(Player p, boolean isReverseGameMode){
        System.out.println("Du har trukket et kort som siger " + getText());

        if (isReverseGameMode) {
            moveInReverseMode(p);
        }
        else {
            moveInNormalMode(p);
        }

    }

    private void moveInReverseMode(Player p) {
        int totalFields = GameBoard.getSingleton().getFields().length;
        int currentPosition = p.getPosition();

        if (currentPosition - amount > totalFields)
        {
            p.setPosition(currentPosition - amount - totalFields);
        }
        else if (currentPosition - amount < 0)
        {
            p.setPosition(currentPosition - amount + totalFields);
            p.changeBalance(4000);
        }
        else
        {
            p.setPosition(currentPosition - amount);
        }
    }

    private void moveInNormalMode(Player p)
    {
        int totalFields = GameBoard.getSingleton().getFields().length;
        int currentPosition = p.getPosition();

        if (currentPosition + amount > totalFields)
        {
            p.setPosition(currentPosition + amount - totalFields);
            p.changeBalance(4000);
        }
        else if (currentPosition + amount < 0)
        {
            p.setPosition(currentPosition + totalFields + amount);
        }
        else
        {
            p.setPosition(currentPosition + amount);
        }
    }
}