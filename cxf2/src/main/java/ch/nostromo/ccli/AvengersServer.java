package ch.nostromo.ccli;


import ch.nostromo.ccli.service.Avengers;
import ch.nostromo.ccli.service.AvengersImpl;

import javax.xml.ws.Endpoint;

public class AvengersServer {

    public static void main(String[] args)  {
        // Start web service
        Avengers avengersImpl = new AvengersImpl();
        Endpoint.publish("http://localhost:6666/AvengersPort", avengersImpl);
    }


}
