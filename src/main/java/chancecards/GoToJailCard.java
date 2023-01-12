package chancecards;

import game.Player;

public class GoToJailCard extends ChanceCard {
    public GoToJailCard(String text){
        super(text);
    }

    public void execute(Player p) {
        p.setPosition(10);
        p.setJailed(true);
    }

}
