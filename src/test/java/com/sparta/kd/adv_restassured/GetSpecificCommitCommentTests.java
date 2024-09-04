package com.sparta.kd.adv_restassured;

import com.sparta.kd.adv_restassured.pojos.Comment;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class GetSpecificCommitCommentTests {
    private static Response response;
    private static final String BASE_URI = AppConfig.getBaseUri();
    private static final String PATH = AppConfig.getRepoPath() + "/comments/{commentId}";
    private static final String OWNER = AppConfig.getOwner();
    private static final String REPO_NAME = AppConfig.getRepoName();
    private static final String BEARER_TOKEN = AppConfig.getToken();
    private static final Integer COMMENT_ID = 146224045;
    private static Comment comment;

    @BeforeAll
    public static void beforeAll() {
        response =
                RestAssured
                        .given(Utils.getSpecificGitHubCommentsRequestSpec(
                                BASE_URI,
                                PATH,
                                COMMENT_ID,
                                BEARER_TOKEN,
                                OWNER,
                                REPO_NAME
                        ))
                        .when()
                        .get()
                        .thenReturn();
        comment = response.as(Comment.class);
    }

    @Test
    @DisplayName("Comment createdAt date/time is in the past")
    void commentCreatedInPastTests() {
        MatcherAssert.assertThat(comment.createdDateInThePast(), Matchers.is(true));
    }
    @Test
    @DisplayName("Get comment with a specific id and check the reactions total count")
    void reactionsTests() {
        MatcherAssert.assertThat(comment.getReactions().getTotalCount(), Matchers.is(0));
    }
    @Test
    @DisplayName("Get comment with a specific Id returns a comment with that Id")
    void getCommentWithId_ReturnsThatComment() {
        MatcherAssert.assertThat(response.jsonPath().get("id"), Matchers.is(COMMENT_ID));
    }

    @Test
    @DisplayName("Get comment with a specific Id and check the Server header")
    void getCommentWithId_ChecksServerHeader() {
        MatcherAssert.assertThat(response.header("Server"), Matchers.is("github.com"));
    }

    @Test
    @DisplayName("Get comment with a specific Id and check the status code")
    void getCommentWithId_ChecksStatusCode() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Get comment with a specific Id and check the reactions total count")
    void getCommentWithId_ChecksReactionsTotalCount() {
        MatcherAssert.assertThat(response.jsonPath().get("reactions.total_count"), Matchers.is(0));
    }
}