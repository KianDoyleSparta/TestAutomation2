package com.sparta.kd.adv_restassured;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sparta.kd.adv_restassured.pojos.Comment;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PostCommitCommentWithValidBodyTests {

    private static Response response;
    private static final String BASE_URI = AppConfig.getBaseUri();
    private static final String POST_PATH = AppConfig.getRepoPath() + "/commits/{commit_sha}/comments";
    private static final String GET_ALL_PATH = AppConfig.getRepoPath() + "/comments";
    private static final String DELETE_PATH = AppConfig.getRepoPath() + "/comments/{commentId}";
    private static final String BEARER_TOKEN = AppConfig.getToken();
    private static final String COMMIT_SHA = AppConfig.getCommitSha();
    private static final String OWNER = AppConfig.getOwner();
    private static final String REPO = AppConfig.getRepoName();

    private static final String POST_MESSAGE = "Hello, World";
    private static Integer commentId; //set in beforeAll
    private static Integer initialNumberOfComments; //set in beforeAll
    private static Integer finalNumberOfComments; //set in beforeAll

    private static Comment comment;

    @BeforeAll
    public static void beforeAll() {
        initialNumberOfComments =
                Utils.getNumberOfComments(
                        BASE_URI,
                        GET_ALL_PATH,
                        BEARER_TOKEN,
                        OWNER,
                        REPO);

        response =
                RestAssured
                        .given(Utils.postRequestSpecForComment(
                                BASE_URI,
                                POST_PATH,
                                BEARER_TOKEN,
                                OWNER,
                                REPO,
                                COMMIT_SHA,
                                POST_MESSAGE
                        ))
                        .when()
                        .post()
                        .thenReturn();

        comment = response.as(Comment.class);

        finalNumberOfComments = Utils.getNumberOfComments(
                BASE_URI,
                GET_ALL_PATH,
                BEARER_TOKEN,
                OWNER,
                REPO);

        commentId = comment.getId();

    }

    @Test
    @DisplayName("Validate the response body")
    void validateResponseBody() {
        MatcherAssert.assertThat(comment.getBody(), Matchers.is("Hello, World"));
    }

    @Test
    @DisplayName("Validate the response status code")
    void validateResponseStatusCode() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(201));
    }

    @Test
    @DisplayName("Validate the number of comments")
    void validateNumberOfComments() {
        MatcherAssert.assertThat(finalNumberOfComments, Matchers.is(initialNumberOfComments + 1));
    }


    @AfterAll
    public static void afterAll(){

        RestAssured
                .given(Utils.deleteCommentRequestSpec(
                        BASE_URI,
                        DELETE_PATH,
                        BEARER_TOKEN,
                        OWNER,
                        REPO,
                        commentId
                ))
                .when()
                .delete()
                .then()
                .assertThat()
                .statusCode(204);
    }

}
