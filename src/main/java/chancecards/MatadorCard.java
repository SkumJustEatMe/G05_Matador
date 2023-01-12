package chancecards;

import fields.GameBoard;
import game.Player;

import java.awt.*;

public class MatadorCard extends ChanceCard {
    int matador;

    public MatadorCard(String text, int matador) {
        super(text);
        this.matador = matador;
    }
    public void execute(Player player) {
        if (GameBoard.getSingleton().totalWealth(player) <= 15000) {
            player.changeBalance(matador);
        }
    }
}
