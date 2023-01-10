package chancecards;

import game.Player;

public class GoToJailCard extends ChanceCard {
    int value;
    public GoToJailCard(String text, int value){
        super(text);
        this.value = value;
    }

    public void execute(Player p) {
        p.setPosition(value);
    }

}
