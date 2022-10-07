package tests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.junit.JUnit4CitrusTestRunner;
import com.consol.citrus.message.MessageType;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import pojo.http.Booking;
import pojo.http.BookingFactory;

import java.util.Random;

public class TestHttp extends JUnit4CitrusTestRunner {

    @Test
    @CitrusTest
    public void testUpdateBooking(@CitrusResource TestContext context) {
        // Хелпер для авторизации
        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .send()
                .post("/auth")
                .payload("{\"username\" : \"admin\", \"password\" : \"password123\"}"));

        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .receive()
                .response()
                .extractFromPayload("$.token", "token"));

        Booking booking = new BookingFactory().create();

        // Хелпер для подготовки тестовых данных
        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .send()
                .post("/booking")
                .accept("application/json")
                .payload(booking, "objectMapper"));

        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .receive()
                .response()
                .extractFromPayload("$.bookingid", "bookingid"));

        booking.setTotalprice(new Random().nextInt(100000));
        booking.setAdditionalneeds("Lunch");

        // Сам тест
        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .send()
                .put("/booking/${bookingid}")
                .accept("application/json")
                .header("Cookie", "token=${token}")
                .payload(booking, "objectMapper"));

        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .receive()
                .response(HttpStatus.OK)
                .messageType(MessageType.JSON)
                .payload(booking, "objectMapper"));

        // Хелпер для удаления тестовых данных
        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .send()
                .delete("/booking/${bookingid}")
                .header("Cookie", "token=${token}"));
    }
}
