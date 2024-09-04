package com.sparta.kd.adv_restassured.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User{

	@JsonProperty("gists_url")
	private String gistsUrl;

	@JsonProperty("repos_url")
	private String reposUrl;

	@JsonProperty("following_url")
	private String followingUrl;

	@JsonProperty("starred_url")
	private String starredUrl;

	@JsonProperty("login")
	private String login;

	@JsonProperty("followers_url")
	private String followersUrl;

	@JsonProperty("type")
	private String type;

	@JsonProperty("url")
	private String url;

	@JsonProperty("subscriptions_url")
	private String subscriptionsUrl;

	@JsonProperty("received_events_url")
	private String receivedEventsUrl;

	@JsonProperty("avatar_url")
	private String avatarUrl;

	@JsonProperty("events_url")
	private String eventsUrl;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("site_admin")
	private boolean siteAdmin;

	@JsonProperty("id")
	private int id;

	@JsonProperty("gravatar_id")
	private String gravatarId;

	@JsonProperty("node_id")
	private String nodeId;

	@JsonProperty("organizations_url")
	private String organizationsUrl;

	public String getGistsUrl(){
		return gistsUrl;
	}

	public String getReposUrl(){
		return reposUrl;
	}

	public String getFollowingUrl(){
		return followingUrl;
	}

	public String getStarredUrl(){
		return starredUrl;
	}

	public String getLogin(){
		return login;
	}

	public String getFollowersUrl(){
		return followersUrl;
	}

	public String getType(){
		return type;
	}

	public String getUrl(){
		return url;
	}

	public String getSubscriptionsUrl(){
		return subscriptionsUrl;
	}

	public String getReceivedEventsUrl(){
		return receivedEventsUrl;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public String getEventsUrl(){
		return eventsUrl;
	}

	public String getHtmlUrl(){
		return htmlUrl;
	}

	public boolean isSiteAdmin(){
		return siteAdmin;
	}

	public int getId(){
		return id;
	}

	public String getGravatarId(){
		return gravatarId;
	}

	public String getNodeId(){
		return nodeId;
	}

	public String getOrganizationsUrl(){
		return organizationsUrl;
	}
}