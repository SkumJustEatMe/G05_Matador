package chancecards;

import fields.BuyableField;
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

    public void execute(Player player, boolean isReverseMode) {
        System.out.println("Du har trukket et kort som siger " + getText());
        int oldPosition = player.getPosition();
        for (int i = player.getPosition(); i <= 39 ; i++) {
            if (i == 39) {
                i = 0;
            }
            if (GameBoard.getSingleton().getFields()[i].getType() == FieldType.FERRY) {
                player.setPosition(i);
                if (GameBoard.getSingleton().getFields()[i].getState().hasOwner()) {
                    Player opponent = GameBoard.getSingleton().getFields()[i].getState().getOwner();
                    int nrOfFerries = GameBoard.getSingleton().getNrOfFerriesOwnedByPlayer(opponent);
                    int rent = ((BuyableField) GameBoard.getSingleton().getFields()[i]).getRent()[nrOfFerries];
                    player.changeBalance(-(rent * timesRent));
                    opponent.changeBalance(rent * timesRent);
                }
                break;
            }
        }

        if (isReverseMode){
            if (player.getPosition() > oldPosition) player.changeBalance(4000);
        }
        else {
            if (player.getPosition() < oldPosition) player.changeBalance(4000);
        }
    }
}
