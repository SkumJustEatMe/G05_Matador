package fields;

public class EffectField extends Field {
    private Effect effect;
    public Effect getEffect() { return this.effect; }
    public EffectField(String name, int position, FieldType type, Effect effect)
    {
        super(name, position, type);
        this.effect = effect;
    }
}
