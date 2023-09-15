package ch.nostromo.ccli;


import ch.nostromo.ccli.service.Avengers;
import ch.nostromo.ccli.service.AvengersImpl;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.ext.logging.LoggingFeature;

public class AvengersServer {

    public static void main(String[] args)  {
        // Start web service
        Avengers avengersImpl = new AvengersImpl();
        Endpoint.publish("http://localhost:6666/AvengersPort", avengersImpl, new LoggingFeature());
    }


}
