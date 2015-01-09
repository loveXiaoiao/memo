package com.xiao.memo.entiy;

import java.util.Date;

public class Record {
	private Integer id;
	//内容
	private String content;
	//创建时间
	private Date createTime;
	//执行时间（过期时间）
	private Date expireTime;
	//是否设置闹钟 0为不设置，1为设置
	private Integer isAlarm;
	//是否过时，默认为0（未过时），1为已过时
	private Integer isOld;
	
	public Integer getIsOld() {
		return isOld;
	}
	public void setIsOld(Integer isOld) {
		this.isOld = isOld;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getIsAlarm() {
		return isAlarm;
	}
	public void setIsAlarm(Integer isAlarm) {
		this.isAlarm = isAlarm;
	}

}
