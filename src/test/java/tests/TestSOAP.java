package tests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.junit.JUnit4CitrusTestRunner;
import features.PojoXML;
import helpers.soap.GetAllCountries;
import org.junit.Test;
import pojo.xml.org.oorsprong.websamples.*;

import java.util.List;
import java.util.Random;

public class TestSOAP extends JUnit4CitrusTestRunner {

    public static Random rand = new Random();

    @Test
    @CitrusTest
    public void testGetCountryNameByCode(@CitrusResource TestContext context) {
        applyBehavior(new GetAllCountries(context));
        List<TCountryCodeAndName> allCountriesList = context
                .getVariable("allCountries", ArrayOftCountryCodeAndName.class)
                .getTCountryCodeAndName();

        TCountryCodeAndName testCountry = allCountriesList.get(rand.nextInt(allCountriesList.size()));

        PojoXML<CountryName> ptxRq = new PojoXML<>();

        CountryName countryName = new CountryName();
        countryName.setSCountryISOCode(testCountry.getSISOCode());

        soap(soapActionBuilder -> soapActionBuilder
                .client("soapClient")
                .send()
                .payload(ptxRq.pojoToXml(
                        CountryName.class,
                        countryName,
                        "http://www.oorsprong.org/websamples.countryinfo",
                        "CountryName")));

        PojoXML<CountryNameResponse> ptxRs = new PojoXML<>();

        CountryNameResponse countryNameResponse = new CountryNameResponse();
        countryNameResponse.setCountryNameResult(testCountry.getSName());

        soap(soapActionBuilder -> soapActionBuilder
                .client("soapClient")
                .receive()
                .payload(ptxRs.pojoToXml(
                        CountryNameResponse.class,
                        countryNameResponse,
                        "http://www.oorsprong.org/websamples.countryinfo",
                        "CountryNameResponse")));
    }

    @Test
    @CitrusTest
    public void testGetCountryCodeByName(@CitrusResource TestContext context) {
        applyBehavior(new GetAllCountries(context));
        List<TCountryCodeAndName> allCountriesList = context
                .getVariable("allCountries", ArrayOftCountryCodeAndName.class)
                .getTCountryCodeAndName();

        TCountryCodeAndName testCountry = allCountriesList.get(rand.nextInt(allCountriesList.size()));

        PojoXML<CountryISOCode> ptxRq = new PojoXML<>();

        CountryISOCode countryISOCode = new CountryISOCode();
        countryISOCode.setSCountryName(testCountry.getSName());

        soap(soapActionBuilder -> soapActionBuilder
                .client("soapClient")
                .send()
                .payload(ptxRq.pojoToXml(
                        CountryISOCode.class,
                        countryISOCode,
                        "http://www.oorsprong.org/websamples.countryinfo",
                        "CountryISOCode")));

        PojoXML<CountryISOCodeResponse> ptxRs = new PojoXML<>();

        CountryISOCodeResponse countryISOCodeResponse = new CountryISOCodeResponse();
        countryISOCodeResponse.setCountryISOCodeResult(testCountry.getSISOCode());

        soap(soapActionBuilder -> soapActionBuilder
                .client("soapClient")
                .receive()
                .payload(ptxRs.pojoToXml(
                        CountryISOCodeResponse.class,
                        countryISOCodeResponse,
                        "http://www.oorsprong.org/websamples.countryinfo",
                        "CountryISOCodeResponse")));
    }
}
