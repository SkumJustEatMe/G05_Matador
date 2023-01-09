package fields;

public abstract class Field
{
    private final String name;
    public String getName() { return this.name; }
    private final int position;
    public int getPosition() { return this.position; }
    private final FieldType type;
    public FieldType getType() { return this.type; }
    private final FieldEffect effect;
    public FieldEffect getEffect() { return this.effect; }
    private final int price;
    public int getPrice() { return this.price; }

    public Field(String name, int position, FieldType type, FieldEffect effect)
    {
        this.name = name;
        this.position = position;
        this.type = type;
        this.effect = effect;
        this.price = price;
    }
}
