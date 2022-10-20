package tests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.junit.JUnit4CitrusTestRunner;
import com.consol.citrus.message.MessageType;
import helpers.http.AuthBehavior;
import helpers.http.CreateBookingBehavior;
import helpers.http.DeleteBookingBehavior;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import pojo.http.Booking;
import pojo.http.BookingFactory;
import java.util.Random;

public class TestHttp extends JUnit4CitrusTestRunner {

    @Test
    @CitrusTest
    public void testUpdateBooking(@CitrusResource TestContext context) {
        applyBehavior(new AuthBehavior(context));

        Booking booking = new BookingFactory().create();

        applyBehavior(new CreateBookingBehavior(context, booking));

        booking.setTotalprice(new Random().nextInt(100000));
        booking.setAdditionalneeds("Lunch");

        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .send()
                .put("/booking/${bookingid}")
                .accept("application/json")
                .messageType(MessageType.JSON)
                .header("Cookie", "token=${token}")
                .payload(booking, "objectMapper"));

        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .receive()
                .response(HttpStatus.OK)
                .messageType(MessageType.JSON)
                .payload(booking, "objectMapper"));

        applyBehavior(new DeleteBookingBehavior(context));
    }
}
