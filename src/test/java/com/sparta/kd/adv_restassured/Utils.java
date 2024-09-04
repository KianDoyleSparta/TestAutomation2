package com.sparta.kd.adv_restassured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Utils {
    public static RequestSpecification getGitHubCommentsRequestSpec(String baseURI, String path, String token, String owner, String repo) {
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(path)
                .addHeaders(Map.of(
                        "Accept", "application/vnd.github+json",
                        "Authorization", "Bearer " + token,
                        "X-GitHub-Api-Version", "2022-11-28"
                ))
                .addPathParams(Map.of(
                        "owner", owner,
                        "repo", repo
                ))
                .build();
    }

    public static RequestSpecification getSpecificGitHubCommentsRequestSpec(String baseURI, String path, Integer commentId, String token, String owner, String repo) {
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(path)
                .addHeaders(Map.of(
                        "Accept", "application/vnd.github+json",
                        "Authorization", "Bearer " + token,
                        "X-GitHub-Api-Version", "2022-11-28"
                ))
                .addPathParams(Map.of(
                        "owner", owner,
                        "repo", repo,
                        "commentId", commentId

                ))
                .build();
    }

    public static RequestSpecification postRequestSpecForComment(String baseUri, String path, String token, String owner, String repo, String commitSha, String commentBody) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeaders(Map.of(
                        "Accept", "application/vnd.github+json",
                        "Authorization", "Bearer " + token,
                        "X-GitHub-Api-Version", "2022-11-28"
                ))
                .addPathParams(Map.of(
                        "owner", owner,
                        "repo", repo,
                        "commit_sha", commitSha
                ))
                .setContentType(ContentType.JSON)
                .setBody(Map.of("body", commentBody))
                .build();
    }

    public static RequestSpecification deleteCommentRequestSpec(String baseUri, String path, String token, String owner, String repo, Integer commentId) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeaders(Map.of(
                        "Accept", "application/vnd.github+json",
                        "Authorization", "Bearer " + token,
                        "X-GitHub-Api-Version", "2022-11-28"
                ))
                .addPathParams(Map.of(
                        "owner", owner,
                        "repo", repo,
                        "commentId", commentId
                ))
                .build();
    }

    public static int getNumberOfComments(String baseUri, String getAllPath, String bearerToken, String userName, String repoName){
        return RestAssured
                .given(Utils.getGitHubCommentsRequestSpec(
                        baseUri,
                        getAllPath,
                        bearerToken,
                        userName,
                        repoName
                ))
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("$")
                .size();
    }
}
