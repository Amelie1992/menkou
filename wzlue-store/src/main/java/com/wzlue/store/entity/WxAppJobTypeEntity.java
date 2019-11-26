package com.wzlue.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店（自定义）岗位种类
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-04 15:38:28
 */
@Data
public class WxAppJobTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//appid
	private String appId;
	//按钮个数
	private Integer number;
	//按钮1
	private Long button1;
	//按钮2
	private Long button2;
	//按钮3
	private Long button3;
	//按钮4
	private Long button4;
	//按钮5
	private Long button5;
	//按钮6
	private Long button6;
	//按钮7
	private Long button7;
	//按钮8
	private Long button8;
	//
	private Date createTime;
	//
	private Date updateTime;
	//公众号名称
	private String appname;
    //按钮1
    private String button11;
    //按钮2
    private String button22;
    //按钮3
    private String button33;
    //按钮4
    private String button44;
    //按钮5
    private String button55;
    //按钮6
    private String button66;
    //按钮7
    private String button77;
    //按钮8
    private String button88;

	/**
	 * 设置：appid
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：appid
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：按钮个数
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}
	/**
	 * 获取：按钮个数
	 */
	public Integer getNumber() {
		return number;
	}
	/**
	 * 设置：按钮1
	 */
	public void setButton1(Long button1) {
		this.button1 = button1;
	}
	/**
	 * 获取：按钮1
	 */
	public Long getButton1() {
		return button1;
	}
	/**
	 * 设置：按钮2
	 */
	public void setButton2(Long button2) {
		this.button2 = button2;
	}
	/**
	 * 获取：按钮2
	 */
	public Long getButton2() {
		return button2;
	}
	/**
	 * 设置：按钮3
	 */
	public void setButton3(Long button3) {
		this.button3 = button3;
	}
	/**
	 * 获取：按钮3
	 */
	public Long getButton3() {
		return button3;
	}
	/**
	 * 设置：按钮4
	 */
	public void setButton4(Long button4) {
		this.button4 = button4;
	}
	/**
	 * 获取：按钮4
	 */
	public Long getButton4() {
		return button4;
	}
	/**
	 * 设置：按钮5
	 */
	public void setButton5(Long button5) {
		this.button5 = button5;
	}
	/**
	 * 获取：按钮5
	 */
	public Long getButton5() {
		return button5;
	}
	/**
	 * 设置：按钮6
	 */
	public void setButton6(Long button6) {
		this.button6 = button6;
	}
	/**
	 * 获取：按钮6
	 */
	public Long getButton6() {
		return button6;
	}
	/**
	 * 设置：按钮7
	 */
	public void setButton7(Long button7) {
		this.button7 = button7;
	}
	/**
	 * 获取：按钮7
	 */
	public Long getButton7() {
		return button7;
	}
	/**
	 * 设置：按钮8
	 */
	public void setButton8(Long button8) {
		this.button8 = button8;
	}
	/**
	 * 获取：按钮8
	 */
	public Long getButton8() {
		return button8;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
