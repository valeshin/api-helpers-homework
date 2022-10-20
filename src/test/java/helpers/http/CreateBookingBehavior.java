package helpers.http;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import helpers.Behavior;
import pojo.http.Booking;

public class CreateBookingBehavior extends Behavior {

    public Booking booking;

    public CreateBookingBehavior(TestContext context, Booking booking) {
        super(context);
        this.booking = booking;
    }

    @Override
    public void apply() {
        // Хелпер для подготовки тестовых данных
        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .send()
                .post("/booking")
                .accept("application/json")
                .messageType(MessageType.JSON)
                .payload(booking, "objectMapper"));

        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .receive()
                .response()
                .extractFromPayload("$.bookingid", "bookingid"));
    }
}
