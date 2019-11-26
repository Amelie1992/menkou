package com.wzlue.wechat.utils;

/**
 * 通过code换取access_token
 * @author Administrator
 *
 */
public class AccessToken {

	private String accessToken;
	private int expiresIn;
	private String openId;
	private String refreshToken;
	private String scope;
	private String unionid;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	@Override
	public String toString() {
		return "AccessToken [accessToken=" + accessToken + ", expiresIn="
				+ expiresIn + ", openId=" + openId + ", refreshToken="
				+ refreshToken + ", scope=" + scope + ", unionid=" + unionid
				+ "]";
	}

	
	
}
