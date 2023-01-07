package fields;

import fields.events.JailEvent;

public class JailField extends Field
{
    private JailEvent event;
    public JailEvent getEvent() { return this.event; }

    public JailField(String name, JailEvent event){
        super(name);
        this.event = event;
    }
}



