import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLoginExistingUser {

    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    @Step("Prepare data to login valid user")
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getNewValidUser();
    }

    @Test
    @DisplayName("Login valid user")
    public void userCanLogin() {
        ValidatableResponse responseRegister = userClient.register(user);
        ValidatableResponse responseLogin = userClient.login(UserCredentials.from(user));
        int actualStatusCodeLogin = responseLogin.extract().statusCode();
        boolean isSuccessInMessageTrueLogin = responseLogin.extract().path("success");
        String userData = responseLogin.extract().path("user").toString();
        accessToken = responseLogin.extract().path("accessToken");
        assertEquals(200, actualStatusCodeLogin);
        assertTrue(isSuccessInMessageTrueLogin);
        assertTrue(userData.contains("email"));
        assertTrue(userData.contains("name"));
        assertTrue(accessToken.contains("Bearer"));
    }

    @After
    @Step("Delete user")
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }
}
