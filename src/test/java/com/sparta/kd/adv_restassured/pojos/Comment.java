package com.sparta.kd.adv_restassured.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Comment{

	@JsonProperty("line")
	private Object line;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("body")
	private String body;

	@JsonProperty("url")
	private String url;

	@JsonProperty("author_association")
	private String authorAssociation;

	@JsonProperty("path")
	private Object path;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("reactions")
	private Reactions reactions;

	@JsonProperty("id")
	private int id;

	@JsonProperty("position")
	private Object position;

	@JsonProperty("user")
	private User user;

	@JsonProperty("commit_id")
	private String commitId;

	@JsonProperty("node_id")
	private String nodeId;

	public Object getLine(){
		return line;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getBody(){
		return body;
	}

	public String getUrl(){
		return url;
	}

	public String getAuthorAssociation(){
		return authorAssociation;
	}

	public Object getPath(){
		return path;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getHtmlUrl(){
		return htmlUrl;
	}

	public Reactions getReactions(){
		return reactions;
	}

	public int getId(){
		return id;
	}

	public Object getPosition(){
		return position;
	}

	public User getUser(){
		return user;
	}

	public String getCommitId(){
		return commitId;
	}

	public String getNodeId(){
		return nodeId;
	}

	public boolean createdDateInThePast(){
		LocalDateTime createdDate = LocalDateTime.parse(createdAt.substring(0,16));
		return createdDate.isBefore(LocalDateTime.now());
	}
}