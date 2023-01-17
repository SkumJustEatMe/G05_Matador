package chancecards;

import game.Player;

public class MoveToCard extends ChanceCard{
    int value;
    public MoveToCard(String text, int value){
        super(text);
        this.value = value;
    }

    public void execute(Player p, boolean isReverseMode){
        System.out.println("Du har trukket et kort som siger " + getText());
        int oldPosition = p.getPosition();
        p.setPosition(value);
        int newPosition = p.getPosition();

        if(isReverseMode){
            if (oldPosition > newPosition) p.changeBalance(4000);
        }
        else {
            if (oldPosition < newPosition) p.changeBalance(4000);
        }
    }
}
