package ch.nostromo.ccli.interceptor;


import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.message.Message;

import java.util.Set;

public class HideTheTruthInterceptor extends LoggingOutInterceptor {

   @Override
    public void handleMessage(Message message) {
       // Filter headers
       addSensitiveProtocolHeaderNames(Set.of("TrueHero"));

       // .. or filter elements
       // addSensitiveElementNames();

       super.handleMessage(message);
   }



}
