package com.sparta.kd.adv_restassured.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reactions{

	@JsonProperty("confused")
	private int confused;

	@JsonProperty("-1")
	private int minus1;

	@JsonProperty("total_count")
	private int totalCount;

	@JsonProperty("+1")
	private int plus1;

	@JsonProperty("rocket")
	private int rocket;

	@JsonProperty("hooray")
	private int hooray;

	@JsonProperty("eyes")
	private int eyes;

	@JsonProperty("url")
	private String url;

	@JsonProperty("laugh")
	private int laugh;

	@JsonProperty("heart")
	private int heart;

	public int getConfused(){
		return confused;
	}

	public int getMinus1(){
		return minus1;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public int getPlus1(){
		return plus1;
	}

	public int getRocket(){
		return rocket;
	}

	public int getHooray(){
		return hooray;
	}

	public int getEyes(){
		return eyes;
	}

	public String getUrl(){
		return url;
	}

	public int getLaugh(){
		return laugh;
	}

	public int getHeart(){
		return heart;
	}
}