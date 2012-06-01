package controllers;

import play.*;
import play.libs.F.*;
import static play.libs.F.Matcher.*;
import static play.mvc.Http.WebSocketEvent.*;
import play.mvc.*;
import play.mvc.Http.*;

import java.util.*;

import models.*;

public class MyWebSocket extends WebSocketController {
    
    public static void echo() {
        
        // listen while connection is open
        while (inbound.isOpen()) {
            
            // get the singleton object containing the event queue
            EventModel events = EventModel.get();
            
            // Socket connected, get the command stream
            EventStream<EventModel.Event> eventStream = events.join();
         
            // Loop while the socket is open
            while(inbound.isOpen()) {
                
                // Listen for an event - either something direct on on the inbound socket channel, or the central event queue
                Either<WebSocketEvent,EventModel.Event> e = await(Promise.waitEither(
                    inbound.nextEvent(), 
                    eventStream.nextEvent()
                ));
                
                // Case: TextEvent received directon the socket
                for(String message: TextFrame.match(e._1)) {
                    // pass it to the central queue object
                    events.alert(message);
                    // and echo it back to original sender
                    outbound.send(message);
                }
                
                // Case: broadcast event from the queue
                for(EventModel.Alert alert: ClassOf(EventModel.Alert.class).match(e._2)) {
                    // here outbound goes to all connected users except the original sender
                    outbound.send(alert.text);
                }
                
                // Case: The socket has been closed
                for(WebSocketClose closed: SocketClosed.match(e._1)) {
                    disconnect();
                } 
                
            }    
            
        }
    }
    
}
