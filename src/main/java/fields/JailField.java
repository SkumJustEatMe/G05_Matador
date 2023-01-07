package fields;

import fields.events.JailEvent;

public class JailField extends Field
{
    private final JailEvent event;
    public JailEvent getEvent() { return this.event; }

    public JailField(String name, int position, FieldType type, JailEvent event){
        super(name, position, type);
        this.event = event;
    }
}



