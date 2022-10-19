package helpers.http;

import com.consol.citrus.context.TestContext;
import helpers.Behavior;

public class AuthBehavior extends Behavior {

    public AuthBehavior(TestContext context) {
        super(context);
    }

    @Override
    public void apply() {
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
    }
}
