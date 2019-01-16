package com.niy.tds;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

public class StreamProcessor implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    public StreamProcessor(InputStream is) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(is);
    }

    public XMLStreamReader getReader() {
        return reader;
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
            }
        }
    }


    public boolean getInnerElementAttributes(String outerElement, String innerElement) throws XMLStreamException {
        boolean inside = false;
        while (reader.hasNext()) {
            int event = reader.next();

            if (event == XMLEvent.START_ELEMENT && reader.getLocalName().equals(outerElement)){
                inside = true;
            }
            if (event == XMLEvent.END_ELEMENT && reader.getLocalName().equals(outerElement)){
                inside = false;
            }
            if (event == XMLEvent.START_ELEMENT && reader.getLocalName().equals(innerElement) && inside){
                return true;
            }
        }
        return false;
    }

    public String getAttribute(String name) {
        return reader.getAttributeValue(null, name);
    }

}
