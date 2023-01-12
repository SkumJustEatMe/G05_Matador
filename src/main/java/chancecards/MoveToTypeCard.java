package chancecards;

import fields.BuyableField;
import fields.Field;
import fields.FieldType;
import fields.GameBoard;
import game.Player;

import java.util.ArrayList;

public class MoveToTypeCard extends ChanceCard {
    FieldType type;
    int timesRent;

    public MoveToTypeCard(String text, FieldType type, int timesRent) {
        super(text);
        this.type = type;
        this.timesRent = timesRent;
    }

    public void execute(ArrayList<Player> players, int currentPlayerIndex) {
        int spot = players.get(currentPlayerIndex).getPosition();
        for (int i = players.get(currentPlayerIndex).getPosition(); i <= 39 ; i++) {
            if (i == 39) {
                i = 0;
            }
            if (GameBoard.getSingleton().getFields()[i].getType() == FieldType.FERRY) {
                players.get(currentPlayerIndex).setPosition(i);
                if (GameBoard.getSingleton().getFields()[i].getState().hasOwner()) {
                    Player opponent = GameBoard.getSingleton().getFields()[i].getState().getOwner();
                    int nrOfFerries = GameBoard.getSingleton().getNrOfFerriesOwnedByPlayer(opponent);
                    int rent = ((BuyableField) GameBoard.getSingleton().getFields()[i]).getRent()[nrOfFerries];
                    players.get(currentPlayerIndex).changeBalance(-(rent * timesRent));
                    GameBoard.getSingleton().getFields()[i].getState().getOwner().changeBalance(rent * timesRent);
                }
            }
        }
        if (players.get(currentPlayerIndex).getPosition()<spot){
            players.get(currentPlayerIndex).changeBalance(4000);
        }
    }
}
