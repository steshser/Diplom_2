import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrdersClient extends Client{
    private static final String ORDERS_PATH = "api/orders";
    private static final String ALL_ORDERS_PATH = "api/orders/all";

    public ValidatableResponse getUserOrders(String accessToken) {
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .when()
                .get(ORDERS_PATH)
                .then();
    }

    public ValidatableResponse getUnauthorizedUserOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDERS_PATH)
                .then();
    }

    public ValidatableResponse getAllOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(ALL_ORDERS_PATH)
                .then();
    }

    public ValidatableResponse createOrder(Ingredients ingredients) {
        return given()
                .spec(getSpec())
                .body(ingredients)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

}
