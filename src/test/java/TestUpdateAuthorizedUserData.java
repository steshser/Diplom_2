import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

public class TestUpdateAuthorizedUserData {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    @Step("Prepare to change data of authorized user")
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getNewValidUser();
    }

    @Test
    @Step("Update email to different")
    public void userCanBeUpdateEmailToDifferent(){
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        accessToken = responseRegister.extract().path("accessToken");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
        User changedUser = UserGenerator.getChangedEmailUser();
        String changedEmail = changedUser.getEmail();
        ValidatableResponse responseUpdateName = userClient.updateAuthorizedUserData(accessToken, UserData.from(changedUser));
        int actualStatusCodeChange = responseUpdateName.extract().statusCode();
        boolean isSuccessInMessageTrueChange = responseUpdateName.extract().path("success");
        HashMap<String, String> changedUserData = responseUpdateName.extract().path("user");
        String responseEmail = changedUserData.get("email");
        assertEquals(200, actualStatusCodeChange);
        assertTrue(isSuccessInMessageTrueChange);
        assertEquals(changedEmail, responseEmail);
    }

    @Test
    @Step("Update email to same")
    public void userCanNotBeUpdateEmailToSame(){
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        accessToken = responseRegister.extract().path("accessToken");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
        ValidatableResponse responseUpdateName = userClient.updateAuthorizedUserData(accessToken, UserData.from(user));
        int actualStatusCodeChange = responseUpdateName.extract().statusCode();
        boolean isSuccessInMessageTrueChange = responseUpdateName.extract().path("success");
        String responseMessage = responseUpdateName.extract().path("message");
        assertEquals(403, actualStatusCodeChange);
        assertFalse(isSuccessInMessageTrueChange);
        assertEquals("User with such email already exists", responseMessage);
    }

    @Test
    @Step("Update name")
    public void userCanBeUpdateName(){
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        accessToken = responseRegister.extract().path("accessToken");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
        User changedUser = UserGenerator.getChangedNameUser();
        String changedName = changedUser.getName();
        ValidatableResponse responseUpdateName = userClient.updateAuthorizedUserData(accessToken, UserData.from(changedUser));
        int actualStatusCodeChange = responseUpdateName.extract().statusCode();
        boolean isSuccessInMessageTrueChange = responseUpdateName.extract().path("success");
        HashMap<String, String> changedUserData = responseUpdateName.extract().path("user");
        String responseName = changedUserData.get("name");
        assertEquals(200, actualStatusCodeChange);
        assertTrue(isSuccessInMessageTrueChange);
        assertEquals(changedName, responseName);
    }

    @After
    @Step("Delete user")
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }
}
