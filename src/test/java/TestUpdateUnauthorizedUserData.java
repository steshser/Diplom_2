import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUpdateUnauthorizedUserData {
    private UserClient userClient;

    @Before
    @Step("Prepare to change data of unauthorized user")
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @Step("Update email")
    public void unauthorizedUserCanNotUpdateEmail() {
        User changedUser = UserGenerator.getChangedEmailUser();
        ValidatableResponse responseUpdateName = userClient.updateUnauthorizedUserData(UserData.from(changedUser));
        int actualStatusCodeChange = responseUpdateName.extract().statusCode();
        boolean isSuccessInMessageFalseChange = responseUpdateName.extract().path("success");
        String responseMessage = responseUpdateName.extract().path("message");
        assertEquals(401, actualStatusCodeChange);
        assertFalse(isSuccessInMessageFalseChange);
        assertEquals("You should be authorised", responseMessage);
    }

    @Test
    @Step("Update name")
    public void unauthorizedUserCanNotUpdateName() {
        User changedUser = UserGenerator.getChangedNameUser();
        ValidatableResponse responseUpdateName = userClient.updateUnauthorizedUserData(UserData.from(changedUser));
        int actualStatusCodeChange = responseUpdateName.extract().statusCode();
        boolean isSuccessInMessageFalseChange = responseUpdateName.extract().path("success");
        String responseMessage = responseUpdateName.extract().path("message");
        assertEquals(401, actualStatusCodeChange);
        assertFalse(isSuccessInMessageFalseChange);
        assertEquals("You should be authorised", responseMessage);
    }
}
