package com.impala.webservices;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import static org.jdom2.filter.Filters.attribute;
import static org.jdom2.filter.Filters.attribute;

public class testSAAJ {

    /**
     * Starting point for the SAAJ - SOAP Client Testing
     */
    public static void main(String args[]) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            String url = "https://www.neosurf.info:443/soap/index.php";
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "https://neosurf.info/soap/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("type", serverURI);

        /*
        Constructed SOAP Request Message:
        <?xml version="1.0" encoding="UTF-8"?>
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:type="https://neosurf.info/soap/">
               <SOAP-ENV:Header />
               <SOAP-ENV:Body>
                  <get_dtickets>
                     <currency>EUR</currency>
                     <hash>819ea5abae576f5a62f1407468d5f162869b848a</hash>
                     <type:IDProduct xsi:type="xsd:int">14</type:IDProduct>
                     <IDReseller>impalapay.reseller.test@neosurf.com+soked3dpme</IDReseller>
                     <IDTransaction>TRX002</IDTransaction>
                     <type:IDUser xsi:type="xsd:int">225</type:IDUser>
                     <type:quantity xsi:type="xsd:int">1</type:quantity>
                  </get_dtickets>
               </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
         */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("get_dtickets");
        
        //start adding param
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("currency");
        soapBodyElem1.addTextNode("EUR");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("hash");
        soapBodyElem2.addTextNode("819ea5abae576f5a62f1407468d5f162869b848a");
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("IDProduct", "type", "xsd:int");
        soapBodyElem3.addTextNode("14");
        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("IDReseller");
        soapBodyElem4.addTextNode("impalapay.reseller.test@neosurf.com+soked3dpme");
        SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("IDTransaction");
        soapBodyElem5.addTextNode("TRX002");
        SOAPElement soapBodyElem6 = soapBodyElem.addChildElement("IDUser", "type", "xsd:int");
        soapBodyElem6.addTextNode("225");
        SOAPElement soapBodyElem7 = soapBodyElem.addChildElement("quantity", "type", "xsd:int");
        soapBodyElem7.addTextNode("1");        

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "get_dtickets");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    /**
     * Method used to print the SOAP Response
     */
    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
    }

}
