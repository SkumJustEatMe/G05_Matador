package chancecards;

import fields.GameBoard;
import game.Player;

public class MoveCard extends ChanceCard{
    int amount;
    MoveCard(String text, int amount){
        super(text);
        this.amount = amount;
    }

    public void execute(Player p){
        if(p.getPosition() + amount > GameBoard.getSingleton().getFields().length) {
            p.setPosition(p.getPosition() + amount - GameBoard.getSingleton().getFields().length);
            p.changeBalance(4000);
        }else if(p.getPosition() + amount < 0){
                p.setPosition(p.getPosition()+40+amount);
        } else {
            p.setPosition(p.getPosition()+amount);
        }
    }
}