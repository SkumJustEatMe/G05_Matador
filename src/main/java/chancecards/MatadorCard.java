package chancecards;

import fields.GameBoard;
import game.Player;

public class MatadorCard extends ChanceCard {
    int matador;

    public MatadorCard(String text, int matador) {
        super(text);
        this.matador = matador;
    }
    public void execute(Player player) {
        System.out.println("Du har trukket et kort som siger " + getText());
        if (GameBoard.getSingleton().getTotalWealth(player) <= 15000) {
            player.changeBalance(matador);
        }
    }
}
