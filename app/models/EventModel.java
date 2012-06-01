package models;

import java.util.*;

import play.libs.*;
import play.libs.F.*;

public class EventModel {
    
    final ArchivedEventStream<EventModel.Event> events = new ArchivedEventStream<EventModel.Event>(100);
    
    /**
     * New user joins channel
     */
    public EventStream<EventModel.Event> join() {
        return events.eventStream();
    }
    
    /**
     * An alert comes in
     */
    public void alert(String text) {
        if(text == null || text.trim().equals("")) {
            return;
        }
        events.publish(new Alert(text));
    }
    
    
    // ~~~~~~~~~ EventModel events

    // extend this class for objects which can be put into the event queue
    public static abstract class Event {
        
        final public String type;
        final public Long timestamp;
        
        public Event(String type) {
            this.type = type;
            this.timestamp = System.currentTimeMillis();
        }
        
    }
    
    // an extension of the Event class - contains a text string
    public static class Alert extends Event {
        
        final public String text;
        
        public Alert(String text) {
            super("alert");
            this.text = text;
        }
        
    }
    
    // ~~~~~~~~~ EventModel factory

    static EventModel instance = null;
    public static EventModel get() {
        if(instance == null) {
            instance = new EventModel();
        }
        return instance;
    }
    
}
