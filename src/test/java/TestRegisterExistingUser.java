import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestRegisterExistingUser {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    @Step("Prepare data to creating new valid user")
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getNewValidUser();
    }

    @Test
    @Step("Create new valid user")
    public void userCanBeCreated(){
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        accessToken = responseRegister.extract().path("accessToken");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
        ValidatableResponse responseRegisterExistingUser = userClient.register(user);
        int actualStatusCodeExistingUser = responseRegisterExistingUser.extract().statusCode();
        boolean isSuccessInMessageFalseExistingUser = responseRegisterExistingUser.extract().path("success");
        String responseMessage = responseRegisterExistingUser.extract().path("message");
        assertEquals(403, actualStatusCodeExistingUser);
        assertFalse(isSuccessInMessageFalseExistingUser);
        assertEquals("User already exists", responseMessage);
    }

    @After
    @Step("Delete user")
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }
}
