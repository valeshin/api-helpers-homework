package helpers.soap;

import com.consol.citrus.context.TestContext;
import features.PojoXML;
import helpers.Behavior;
import pojo.xml.org.oorsprong.websamples.ArrayOftCountryCodeAndName;
import pojo.xml.org.oorsprong.websamples.ListOfCountryNamesByName;
import pojo.xml.org.oorsprong.websamples.ListOfCountryNamesByNameResponse;

public class GetAllCountries extends Behavior {

    public GetAllCountries(TestContext context) {
        super(context);
    }

    @Override
    public void apply() {
        ListOfCountryNamesByName listOfCountryRequest = new ListOfCountryNamesByName();

        PojoXML<ListOfCountryNamesByName> ptxRq = new PojoXML<>();
        PojoXML<ListOfCountryNamesByNameResponse> ptxRs = new PojoXML<>();

        soap(soapActionBuilder -> soapActionBuilder
                .client("soapClient")
                .send()
                .payload(ptxRq.pojoToXml(
                        ListOfCountryNamesByName.class,
                        listOfCountryRequest,
                        "http://www.oorsprong.org/websamples.countryinfo",
                        "ListOfCountryNamesByName")));

        soap(soapActionBuilder -> soapActionBuilder
                .client("soapClient")
                .receive()
                .name("listOfCountriesMessage"));
        String response = context
                .getMessageStore()
                .getMessage("listOfCountriesMessage")
                .getPayload().toString();

        ArrayOftCountryCodeAndName arrayOfAllCountries = ptxRs
                .xmlToPojo(ListOfCountryNamesByNameResponse.class, response)
                .getListOfCountryNamesByNameResult();
        variable("allCountries", arrayOfAllCountries);
    }
}
