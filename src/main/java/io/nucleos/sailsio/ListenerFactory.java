package io.nucleos.sailsio;

/**
 * Created by cmarcano on 15/10/15.
 */
public class ListenerFactory {

    private String eventName;

    public ListenerFactory(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
