package chancecards;

import game.Player;

public class MoveToCard extends ChanceCard{
    int value;
    public MoveToCard(String text, int value){
        super(text);
        this.value = value;
    }

    public void execute(Player p){
        int spot;
        spot = p.getPosition();
        p.setPosition(value);
        if(spot > p.getPosition()){
            p.changeBalance(4000);
        }
    }
}
