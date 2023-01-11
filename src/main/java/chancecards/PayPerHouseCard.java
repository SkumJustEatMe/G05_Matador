/*package chancecards;

import game.Player;

public class PayPerHouseCard extends ChanceCard {
    int house;
    int hotel;
    public PayPerHouseCard(String text, int house, int hotel) {
        super(text);
        this.house = house;
        this.hotel = hotel;
    }

    public void execute(Player p) {
        int pay = p.getHouses * house + p.getHotels * hotel;
        p.changeBalance(-pay);
    }

    public String printText(){
        return text;
    }
}*/
