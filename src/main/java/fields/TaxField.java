package fields;

import fields.effects.TaxEffect;

public class TaxField extends Field
{
    private TaxEffect effect;
    public TaxField(String name, int position, FieldType type, TaxEffect effect)
    {
        super(name, position, type);
        this.effect = effect;
    }
}
