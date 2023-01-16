package chancecards;

import game.Player;

import java.util.ArrayList;

public class ReceivePerPlayerCard extends ChanceCard{

    int value;
    public ReceivePerPlayerCard(String text, int value){
        super(text);
        this.value = value;
    }

    public void execute(ArrayList<Player> players, int playerIndex) {
        System.out.println("Du har trukket et kort som siger " + getText());
        for (int i = 1; i < players.size(); i++) {
            if (i == playerIndex) {
                continue;
            }
            players.get(i).changeBalance(-value);
        }
        players.get(playerIndex).changeBalance((players.size() - 1) * value);
    }
}
