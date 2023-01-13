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
        if (GameBoard.getSingleton().getTotalWealth(player) <= 15000) {
            player.changeBalance(matador);
        }
    }
}
