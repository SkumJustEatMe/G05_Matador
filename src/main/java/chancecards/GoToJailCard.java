package chancecards;

import game.Player;

public class GoToJailCard extends ChanceCard {
    public GoToJailCard(String text){
        super(text);
    }

    public void execute(Player p) {
        System.out.println("Du har trukket et kort som siger " + getText());
        p.setPosition(10);
        p.setJailed(true);
    }

}
