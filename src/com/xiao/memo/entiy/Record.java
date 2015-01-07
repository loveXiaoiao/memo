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
	//是否设置闹钟
	private Integer isAlarm;
	
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
