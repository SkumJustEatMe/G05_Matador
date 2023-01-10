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
        for (int i = 0; i < players.size(); i++) {
            if (i == playerIndex) {
                continue;
            }
            players.get(i).changeBalance(-value);
            players.get(playerIndex).changeBalance(i * value);
        }
    }
}