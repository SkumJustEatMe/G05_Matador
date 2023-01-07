package fields;

import fields.effects.JailEffect;

public class JailField extends Field
{
    private final JailEffect effect;
    public JailEffect getEvent() { return this.effect; }

    public JailField(String name, int position, FieldType type, JailEffect effect){
        super(name, position, type);
        this.effect = effect;
    }
}



