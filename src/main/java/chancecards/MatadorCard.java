package chancecards;

import game.Player;

import java.awt.*;

public class MatadorCard extends ChanceCard {
    int matador;

    public MatadorCard(String text, int matador) {
        super(text);
        this.matador = matador;
    }
    public void execute(Player p) {
        if (p.getValues <= 15000) {
            p.changeBalance(matador);
        }
    }
}
