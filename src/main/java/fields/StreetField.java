package fields;

public class StreetField extends Field
{
    private final int housePrice;
    public int getHousePrice() {return this.housePrice;}
    private final int[] rent;
    public int[] getRent() {return this.rent;}

    public StreetField(String name, int position, FieldType type, int housePrice, int[] rent)
    {
        super(name, position, type, FieldEffect.NONE);
        this.housePrice = housePrice;
        this.rent = rent;
    }
}