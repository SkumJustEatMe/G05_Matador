package chancecards;

import game.Player;

import java.awt.*;

public class GoToJail extends ChanceCard {
    int value;
    public GoToJail(String text, int value){
        super(text);
        this.value = value;
    }

    public void execute(Player p) {
        int spot;
        spot = p.getPosition();
        p.setPosition(value);
    }

}
