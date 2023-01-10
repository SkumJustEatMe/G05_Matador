package chancecards;

import game.Player;

public class GetOutOfJailCard extends ChanceCard{
    public GetOutOfJailCard(String text){
        super(text);
    }
    public void execute(Player p){
        p.setGetOutOfJailFreeCard(+1);
    }
}
