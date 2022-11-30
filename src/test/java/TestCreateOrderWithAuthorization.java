import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestCreateOrderWithAuthorization {
    private UserClient userClient;
    private User user;
    private String accessToken;
    private OrdersClient ordersClient;
    private IngredientsClient ingredientsClient;

    @Before
    @Step("Prepare to create order by authorized user")
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getNewValidUser();
        ingredientsClient = new IngredientsClient();
        ordersClient = new OrdersClient();
    }

    @Test
    @Step("Create order by authorized user")
    public void authorizedUserCanCreateOrder() {
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        accessToken = responseRegister.extract().path("accessToken");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
        // create order with all ingredients
        ValidatableResponse responseIngredients = ingredientsClient.getIngredients();
        ArrayList<HashMap<String, String>> responseData = responseIngredients.extract().path("data");
        Ingredients ingredients = IngredientsGenerator.getValidIngredientsHash(responseData);
        // create order
        ValidatableResponse responseCreateOrder = ordersClient.createOrderAuthorizedUserWithIngredients(accessToken, ingredients);
        int actualStatusCodeCreateOrder = responseCreateOrder.extract().statusCode();
        boolean isSuccessInMessageTrueCreateOrder = responseCreateOrder.extract().path("success");
        assertEquals(200, actualStatusCodeCreateOrder);
        assertTrue(isSuccessInMessageTrueCreateOrder);
    }

    @Test
    @Step("Create order by authorized user with invalid ingredients hash")
    public void authorizedUserCanNotCreateOrderWithInvalidIngredients() {
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        accessToken = responseRegister.extract().path("accessToken");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
        Ingredients ingredients = IngredientsGenerator.getInvalidIngredientsHash();
        // create order
        ValidatableResponse responseCreateOrder = ordersClient.createOrderAuthorizedUserWithIngredients(accessToken, ingredients);
        int actualStatusCodeCreateOrder = responseCreateOrder.extract().statusCode();
        assertEquals(500, actualStatusCodeCreateOrder);
    }

    @Test
    @Step("Create order by authorized user without ingredients")
    public void authorizedUserCanNotCreateOrderWithoutIngredients() {
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        accessToken = responseRegister.extract().path("accessToken");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
        ValidatableResponse responseCreateOrder = ordersClient.createOrderAuthorizedUserWithoutIngredients(accessToken);
        int actualStatusCodeCreateOrder = responseCreateOrder.extract().statusCode();
        boolean isSuccessInMessageFalseCreateOrder = responseCreateOrder.extract().path("success");
        String responseMessage = responseCreateOrder.extract().path("message");
        assertEquals(400, actualStatusCodeCreateOrder);
        assertFalse(isSuccessInMessageFalseCreateOrder);
        assertEquals("Ingredient ids must be provided", responseMessage);
    }

    @After
    @Step("Delete user")
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

}
