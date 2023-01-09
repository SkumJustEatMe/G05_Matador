package fields;

public abstract class Field
{
    private final String name;
    public String getName() { return this.name; }
    private final int position;
    public int getPosition() { return this.position; }
    private final FieldType type;
    public FieldType getType() { return this.type; }

    public Field(String name, int position, FieldType type)
    {
        this.name = name;
        this.position = position;
        this.type = type;
    }
}
