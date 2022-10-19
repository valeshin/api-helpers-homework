package features;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

public class PojoXML<T> {
    public Class<T> generic;

    public String pojoToXml(Class<T> requestClass, Object requestData, String namespaseURI, String localPart) { //namespaseURI=http://oms.rt.ru/ , localPart=submitOrderRequest
        generic = requestClass;
        String s = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(requestClass);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // To format XML
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            JAXBElement<T> jaxbElement =
                    new JAXBElement<T>(new QName(namespaseURI, localPart),
                            requestClass,
                            (T) requestData);

            OutputStream output = new OutputStream() {
                private StringBuilder string = new StringBuilder();
                @Override
                public void write(int b) throws IOException {
                    this.string.append((char) b);
                }

                public String toString(){
                    return this.string.toString();
                }
            };
            jaxbMarshaller.marshal(jaxbElement, output);
            s = String.valueOf(output);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return s;
    }

    public T xmlToPojo(Class<T> requestClass, String xml) {
        generic = requestClass;
        T pojo = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((Class) requestClass);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            pojo = (T) jaxbUnmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return pojo;
    }
}