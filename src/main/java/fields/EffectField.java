package fields;

public class EffectField extends Field {
    private final Effect effect;
    public Effect getEffect() { return this.effect; }
    public EffectField(String name, int position, FieldType type, Effect effect, Integer price)
    {
        super(name, position, type, price);
        this.effect = effect;
    }
}
