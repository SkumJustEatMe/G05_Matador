package fields;

import fields.effects.ChanceEffect;

public class ChanceField extends Field
{
    private ChanceEffect effect;

    public ChanceField(int position, FieldType type, ChanceEffect effect)
    {
        super("Prøv lykken!", position, type);
        this.effect = effect;
    }
}
