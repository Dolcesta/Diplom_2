package usertest.change;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import com.stellarburger.user.User;
import com.stellarburger.user.UserClient;
import com.stellarburger.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ChangeUnauthorizedUserDataTest {
    private UserClient userClient;
    private User user;
    private String accessToken;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.createDefaultUser();
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
    }

    @After
    public void cleanUp() {
            userClient.delete(accessToken);
    }

    @Test
    @DisplayName("change email for not authorized user")
    public void unauthorizedUserCantChangeEmail() {
        user.setEmail("joey@gmail.com");
        ValidatableResponse responseUpdateData = userClient.updateUnauthorizedUserData();
        int actualStatusCodeChange = responseUpdateData.extract().statusCode();
        boolean isUserChangeData = responseUpdateData.extract().path("success");
        String changeDataMessage = responseUpdateData.extract().path("message");
        assertEquals("Expected 401", SC_UNAUTHORIZED, actualStatusCodeChange);
        assertFalse("Expected false", isUserChangeData);
        assertEquals("You should be authorised", changeDataMessage);
    }

    @Test
    @DisplayName("change password for not authorized user")
    public void unauthorizedUserCantChangePassword() {
        user.setPassword("c,jbkjhhgxfdt");
        ValidatableResponse responseUpdateData = userClient.updateUnauthorizedUserData();
        int actualStatusCodeChange = responseUpdateData.extract().statusCode();
        boolean isUserChangeData = responseUpdateData.extract().path("success");
        String changeDataMessage = responseUpdateData.extract().path("message");
        assertEquals("Expected 401", SC_UNAUTHORIZED, actualStatusCodeChange);
        assertFalse("Expected false", isUserChangeData);
        assertEquals("You should be authorised", changeDataMessage);
    }

    @Test
    @DisplayName("change name for not authorized user")
    public void unauthorizedUserCantChangeName() {
        user.setName("qEGHFJGH");
        ValidatableResponse responseUpdateData = userClient.updateUnauthorizedUserData();
        int actualStatusCodeChange = responseUpdateData.extract().statusCode();
        boolean isUserChangeData = responseUpdateData.extract().path("success");
        String changeDataMessage = responseUpdateData.extract().path("message");
        assertEquals("Expected 401", SC_UNAUTHORIZED, actualStatusCodeChange);
        assertFalse("Expected false", isUserChangeData);
        assertEquals("You should be authorised", changeDataMessage);
    }
}