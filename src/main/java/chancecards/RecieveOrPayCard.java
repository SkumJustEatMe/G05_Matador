package chancecards;

import game.Player;

public class RecieveOrPayCard extends ChanceCard{
    int amount;
    RecieveOrPayCard(String text, int amount){
        super(text);
        this.amount = amount;
    }


    public void execute(Player p){
       p.changeBalance(amount);
    }
}
