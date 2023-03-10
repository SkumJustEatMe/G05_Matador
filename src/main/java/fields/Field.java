package fields;

import java.awt.*;

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
    private final Color color;
    public Color getColor() { return this.color; }

    private FieldState state;
    public FieldState getState() {return this.state;}

    public Field(String name, int position, FieldType type, Color color, Integer price)
    {
        this.name = name;
        this.position = position;
        this.type = type;
        this.color = color;
        this.price = price;
        this.state = new FieldState();
    }
}
