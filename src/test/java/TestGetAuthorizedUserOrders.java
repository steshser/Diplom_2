import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
    @Step("Get orders list of authorized user")
    public void canGetOrdersListOfAuthorizedUser() {
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        accessToken = responseRegister.extract().path("accessToken");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
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
