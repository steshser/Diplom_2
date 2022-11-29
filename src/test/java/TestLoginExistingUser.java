import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
    @Step("Login valid user")
    public void userCanLogin(){
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
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
