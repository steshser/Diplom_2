import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestCreateOrderWithoutAuthorization {

    private OrdersClient ordersClient;
    private IngredientsClient ingredientsClient;

    @Before
    @Step("Prepare to create order by unauthorized user")
    public void setUp() {
        ingredientsClient = new IngredientsClient();
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Create order by unauthorized user")
    public void unauthorizedUserCanCreateOrder() {
        // create order with all ingredients
        ValidatableResponse responseIngredients = ingredientsClient.getIngredients();
        ArrayList<HashMap<String, String>> responseData = responseIngredients.extract().path("data");
        Ingredients ingredients = IngredientsGenerator.getValidIngredientsHash(responseData);
        // create order
        ValidatableResponse responseCreateOrder = ordersClient.createOrderUnauthorizedUserWithIngredients(ingredients);
        int actualStatusCodeCreateOrder = responseCreateOrder.extract().statusCode();
        boolean isSuccessInMessageTrueCreateOrder = responseCreateOrder.extract().path("success");
        assertEquals(200, actualStatusCodeCreateOrder);
        assertTrue(isSuccessInMessageTrueCreateOrder);
    }

    @Test
    @DisplayName("Create order by unauthorized user with invalid ingredients hash")
    public void unauthorizedUserCanNotCreateOrderWithInvalidIngredients() {
        Ingredients ingredients = IngredientsGenerator.getInvalidIngredientsHash();
        // create order
        ValidatableResponse responseCreateOrder = ordersClient.createOrderUnauthorizedUserWithIngredients(ingredients);
        int actualStatusCodeCreateOrder = responseCreateOrder.extract().statusCode();
        assertEquals(500, actualStatusCodeCreateOrder);
    }

    @Test
    @DisplayName("Create order by unauthorized user with without ingredients")
    public void unauthorizedUserCanNotCreateOrderWithoutIngredients() {
        ValidatableResponse responseCreateOrder = ordersClient.createOrderUnauthorizedUserWithoutIngredients();
        int actualStatusCodeCreateOrder = responseCreateOrder.extract().statusCode();
        boolean isSuccessInMessageFalseCreateOrder = responseCreateOrder.extract().path("success");
        String responseMessage = responseCreateOrder.extract().path("message");
        assertEquals(400, actualStatusCodeCreateOrder);
        assertFalse(isSuccessInMessageFalseCreateOrder);
        assertEquals("Ingredient ids must be provided", responseMessage);
    }

}
