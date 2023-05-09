package com.stellarburger.user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import  com.stellarburger.Client;

import static io.restassured.RestAssured.given;

public class UserClient  extends Client {

    private final Filter requestFilter = new RequestLoggingFilter();
    private final Filter responseFiler = new ResponseLoggingFilter();

    private static final String PATH_REGISTER="api/auth/register";
    private static final String PATH_LOGIN = "/api/auth/login";
    private static final String PATH = "/api/auth/user";
    @Step("Create")
    public ValidatableResponse create(User user){
        return
                RestAssured
                        .with()
                        .filters(requestFilter, responseFiler)
                        .given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .body(user)
                .when()
                .post(PATH_REGISTER)
                .then();
    }
    @Step("login")
    public ValidatableResponse login(Credentials credentials){
        return given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then();
    }
    @Step("delete")
    public ValidatableResponse delete(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(PATH)
                .then();
    }
    @Step("update authorized user data")
    public ValidatableResponse updateAuthorizedUserData(String accessToken,Credentials credentials){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(credentials)
                .when()
                .patch(PATH)
                .then();
    }
    @Step("update unauthorized user data")
    public ValidatableResponse updateUnauthorizedUserData(){
        return given()
                .spec(getSpec())
                .when()
                .patch(PATH)
                .then();
    }
}
