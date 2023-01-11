/*package chancecards;

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

    public void execute(ArrayList<Player> players, int currentPlayerIndex, Field[] fields) {
        int spot = players.get(currentPlayerIndex).getPosition();
        for (int i = players.get(currentPlayerIndex).getPosition(); i <= 39 ; i++) {
            if(i == 39){
                i = 0;
            }
            if (fields[i].getType() == FieldType.FERRY) {
                players.get(currentPlayerIndex).setPosition(i);
            }
            if (fields[i].hasOwner()) {
                players.get(currentPlayerIndex).changeBalance(-((BuyableField)fields[i]).getRent()[nrOfFerryies-1] * timesRent);
                fields[i].owner.changeBalance(((BuyableField)fields[i]).getRent()[nrOfFerryies-1] * timesRent);
            }
        }
    }
}*/
