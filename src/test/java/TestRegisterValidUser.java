import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRegisterValidUser {
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
    @DisplayName("Create new valid user")
    public void userCanBeCreated() {
        ValidatableResponse responseRegister = userClient.register(user);
        int actualStatusCodeCreate = responseRegister.extract().statusCode();
        boolean isSuccessInMessageTrueCreate = responseRegister.extract().path("success");
        accessToken = responseRegister.extract().path("accessToken");
        assertEquals(200, actualStatusCodeCreate);
        assertTrue(isSuccessInMessageTrueCreate);
    }

    @After
    @Step("Delete user")
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }


}
