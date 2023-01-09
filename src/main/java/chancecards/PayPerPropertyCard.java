package chancecards;

import game.Player;

import java.awt.*;

public class PayPerPropertyCard extends ChanceCard {
    int house;
    int hotel;
    public PayPerPropertyCard(String text, int house, int hotel) {
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
}
