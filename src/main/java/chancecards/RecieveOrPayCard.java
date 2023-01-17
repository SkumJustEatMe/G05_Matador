package chancecards;

import game.Player;

public class RecieveOrPayCard extends ChanceCard{
    int amount;
    RecieveOrPayCard(String text, int amount){
        super(text);
        this.amount = amount;
    }


    public void execute(Player p){
        System.out.println("Du har trukket et kort som siger " + getText());
       p.changeBalance(amount);
    }
}
