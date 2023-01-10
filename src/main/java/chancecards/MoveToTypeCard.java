package chancecards;

import fields.FieldType;
import fields.GameBoard;
import game.Player;

public class MoveToTypeCard extends ChanceCard {
    FieldType type;
    int timesRent;

    public MoveToTypeCard(String text, FieldType type, int timesRent) {
        super(text);
        this.type = type;
        this.timesRent = timesRent;
    }

    public void execute(Player p, GameBoard gameBoard) {
        int spot = p.getPosition();
        for (int i = p.getPosition(); i != 40; i++) {
            if (gameBoard.getFieldList()[i].getType() == FieldType.FERRY) {
                p.setPosition(i);
            }
            if (gameBoard.getFieldList()[i].hasOwner()) {
                p.changeBalance(-gameBoard.getFieldList()[i].getPrice() * timesRent);
            }
        }
    }
}
