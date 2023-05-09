package usertest.create;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import com.stellarburger.user.User;
import com.stellarburger.user.UserClient;
import com.stellarburger.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserCreateNegativeTest {
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
    public void cleanUp(){
            userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check response when user can't created twice" )
    public void userCantBeCreatedTwice(){
        ValidatableResponse responseCreate = userClient.create(user);
        int actualSC = responseCreate.extract().statusCode();
        boolean isUserCreated = responseCreate.extract().path("success");
        String actualMessage = responseCreate.extract().path("message" );
        assertEquals("Expected 403",SC_FORBIDDEN, actualSC);
        assertEquals("User already exists",actualMessage);
        assertFalse(isUserCreated);
    }
}
