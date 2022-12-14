import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends Client {
    private static final String INGREDIENTS_PATH = "api/ingredients";

    @Step("Get ingredients list")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then();
    }
}
