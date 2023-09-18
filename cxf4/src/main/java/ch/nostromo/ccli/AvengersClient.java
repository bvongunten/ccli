package ch.nostromo.ccli;

import ch.nostromo.ccli.interceptor.HideTheTruthInterceptor;
import ch.nostromo.ccli.service.Avengers;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.SOAPBinding;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class AvengersClient {
    private static final QName SERVICE_NAME = new QName("http://service.ccli.nostromo.ch/", "Avengers");
    private static final QName PORT_NAME = new QName("http://service.ccli.nostromo.ch/", "AvengersPort");

    public static void main(String[] args) {

        // Create service
        Service service = Service.create(SERVICE_NAME);
        String endpointAddress = "http://localhost:6666/AvengersPort";
        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);

        Avengers avengers = service.getPort(Avengers.class);
        Client client = ClientProxy.getClient(avengers);

        // Add custom log interceptor
        client.getEndpoint().getOutInterceptors().add(new HideTheTruthInterceptor());

        // Add some controversy to the headers
        Map<String, List<String>> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        headers.put("TrueHero", Collections.singletonList("Loki"));
        client.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);

        // Call service
        avengers.getFirstAvenger();
   }

}
