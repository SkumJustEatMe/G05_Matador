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

    public void execute(Player p) {
        int spot = p.getPosition();
        for (int i = p.getPosition(); i != 40; i++) {
            if (GameBoard.getFieldList()[i] instanceof type) {
                p.setPosition(i);
            }
            if (field.hasOwner()) {
                p.changeBalance(-field.getvalue * timesRent);
            }
        }
    }
}
