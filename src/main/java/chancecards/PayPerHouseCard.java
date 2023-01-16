package chancecards;

import fields.GameBoard;
import game.Player;

public class PayPerHouseCard extends ChanceCard {
    int house;
    int hotel;
    public PayPerHouseCard(String text, int house, int hotel) {
        super(text);
        this.house = house;
        this.hotel = hotel;
    }

    public void execute(Player player) {
        System.out.println("Du har trukket et kort som siger " + getText());
        int pay = GameBoard.getSingleton().getTotalHousesOwnedByPlayer(player) * house + GameBoard.getSingleton().getTotalHotelsOwnedByPlayer(player) * hotel;
        player.changeBalance(-pay);
    }
}
