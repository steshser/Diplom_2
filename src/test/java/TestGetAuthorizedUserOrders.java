import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGetAuthorizedUserOrders {
    private UserClient userClient;
    private User user;
    private String accessToken;
    private OrdersClient ordersClient;

    @Before
    @Step("Prepare to get orders list of authorized user")
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getNewValidUser();
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Get orders list of authorized user")
    public void canGetOrdersListOfAuthorizedUser() {
        ValidatableResponse responseRegister = userClient.register(user);
        accessToken = responseRegister.extract().path("accessToken");
        ValidatableResponse responseUserOrders = ordersClient.getUserOrders(accessToken);
        int actualStatusCodeOrders = responseUserOrders.extract().statusCode();
        boolean isSuccessInMessageTrueOrders = responseUserOrders.extract().path("success");
        assertEquals(200, actualStatusCodeOrders);
        assertTrue(isSuccessInMessageTrueOrders);
    }

    @After
    @Step("Delete user")
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }


}
