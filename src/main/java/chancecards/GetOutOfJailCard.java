package chancecards;

import game.Player;

public class GetOutOfJailCard extends ChanceCard{
    public GetOutOfJailCard(String text){
        super(text);
    }
    public void execute(Player p){
        System.out.println("Du har trukket et kort som siger " + getText());
        p.setGetOutOfJailFreeCard(+1);
    }
}
