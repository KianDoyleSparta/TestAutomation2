package com.sparta.kd.adv_restassured;

import com.sparta.kd.adv_restassured.pojos.Comment;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class GetCommitCommentsTests {
    private static Response response;
    private static final String BASE_URI = AppConfig.getBaseUri();
    private static final String PATH = AppConfig.getRepoPath() + "/comments";
    private static final String OWNER = AppConfig.getOwner();
    private static final String REPO_NAME = AppConfig.getRepoName();
    private static final String BEARER_TOKEN = AppConfig.getToken();

    private static Comment[] comments;

    @BeforeAll
    public static void beforeAll(){
        response =
                RestAssured
                        .given(Utils.getGitHubCommentsRequestSpec(
                                BASE_URI,
                                PATH,
                                BEARER_TOKEN,
                                OWNER,
                                REPO_NAME
                        ))
                        .when()
                        .get()
                        .thenReturn();
        comments = response.as(Comment[].class);
    }

    @Test
    @DisplayName("Get all repos comments returns 1 comment")
    void getAllRepositoryComments_Returns1Comment_withPojos(){
        MatcherAssert.assertThat(comments.length, Matchers.is(1));
    }
    @Test
    @DisplayName("Get all comments and check the status code")
    void getAllComments_ChecksStatusCode(){
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Get all repository comments returns 1 comment")
    void getAllRepositoryComments_Returns1Comment(){
        MatcherAssert.assertThat(response.jsonPath().getList("").size(), Matchers.is(1));
    }

    @Test
    @DisplayName("First comment has correct user name associated")
    void firstComment_HasCorrectUserNameAssociated() {
        // Verify that the login of the user of the first comment matches the expected OWNER value
        MatcherAssert.assertThat(response.jsonPath().getString("[0].user.login"), Matchers.is(OWNER));
    }
}