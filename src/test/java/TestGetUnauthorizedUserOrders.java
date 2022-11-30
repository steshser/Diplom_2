import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGetUnauthorizedUserOrders {
    private OrdersClient ordersClient;

    @Before
    @Step("Prepare to get orders list of unauthorized user")
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @Step("Get orders list of unauthorized user")
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
