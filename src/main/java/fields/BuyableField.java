package fields;

public class BuyableField extends Field {

    private final int price;
    public int getPrice() { return this.price; }
    private final Integer housePrice;
    public int getHousePrice() { return this.housePrice; }
    private final int[] rent;
    public int[] getRent() { return this.rent; }
    public BuyableField(String name, int position, FieldType type, int price, int housePrice, int[] rent)
    {
        super(name, position, type);
        this.price = price;
        this.housePrice = housePrice;
        this.rent = rent;
    }
}
