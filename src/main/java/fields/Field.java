package fields;

public abstract class Field
{
    private final String name;
    public String getName() { return this.name; }
    private final int position;
    public int getPosition() { return this.position; }
    private final FieldType type;
    public FieldType getType() { return this.type; }
    private final Integer price;
    public Integer getPrice() { return this.price; }

    public Field(String name, int position, FieldType type, Integer price)
    {
        this.name = name;
        this.position = position;
        this.type = type;
        this.price = price;
    }
}
