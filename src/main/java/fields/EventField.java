package fields;

public class EventField extends Field
{
    private Event event;
    public Event getEvent() { return this.event; }

    public EventField(String name, Event event){
        super(name);
        this.event = event;
    }
}



