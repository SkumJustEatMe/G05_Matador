package fields;

import java.awt.*;

public class EffectField extends Field {
    private final Effect effect;
    public Effect getEffect() { return this.effect; }
    public EffectField(String name, int position, FieldType type, Effect effect, Color color, Integer price)
    {
        super(name, position, type, color, price);
        this.effect = effect;
    }
}
