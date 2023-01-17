package fields;

import java.awt.*;

public class BuyableField extends Field {

    private final Integer housePrice;
    public Integer getHousePrice() { return this.housePrice; }
    private final int[] rent;
    public int[] getRent() { return this.rent; }
    public BuyableField(String name, int position, FieldType type, Color color, int price, Integer housePrice, int[] rent)
    {
        super(name, position, type, color, price);
        this.housePrice = housePrice;
        this.rent = rent;
    }
}
