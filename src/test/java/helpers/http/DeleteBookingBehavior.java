package helpers.http;

import com.consol.citrus.context.TestContext;
import helpers.Behavior;

public class DeleteBookingBehavior extends Behavior {

    public DeleteBookingBehavior(TestContext context) {
        super(context);
    }

    @Override
    public void apply() {
        // Хелпер для удаления тестовых данных
        http(httpActionBuilder -> httpActionBuilder
                .client("httpClient")
                .send()
                .delete("/booking/${bookingid}")
                .header("Cookie", "token=${token}"));
    }
}
