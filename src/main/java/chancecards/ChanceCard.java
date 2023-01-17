package chancecards;

import game.Player;

public abstract class ChanceCard{
    private String text;

    public String getText(){
        return this.text;
    }

    public ChanceCard(String text){
        this.text = text;
    }

    public void execute(Player p){}

}

