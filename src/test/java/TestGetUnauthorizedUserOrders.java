import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestGetUnauthorizedUserOrders {
    private OrdersClient ordersClient;

    @Before
    @Step("Prepare to get orders list of unauthorized user")
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Get orders list of unauthorized user")
    public void canNotGetOrdersListOfUnauthorizedUser() {
        ValidatableResponse responseUserOrders = ordersClient.getUnauthorizedUserOrders();
        int actualStatusCodeOrders = responseUserOrders.extract().statusCode();
        boolean isSuccessInMessageFalseOrders = responseUserOrders.extract().path("success");
        String responseMessage = responseUserOrders.extract().path("message");
        assertEquals(401, actualStatusCodeOrders);
        assertFalse(isSuccessInMessageFalseOrders);
        assertEquals("You should be authorised", responseMessage);
    }
}
