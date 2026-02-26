package com.pf.im.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * IM 会话/房间实体，对应表 im_conversation。
 */
public class ImConversation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String roomId;

    private String conversationType;

    private Long userIdA;

    private Long userIdB;

    private Integer status;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getConversationType() {
        return conversationType;
    }

    public void setConversationType(String conversationType) {
        this.conversationType = conversationType;
    }

    public Long getUserIdA() {
        return userIdA;
    }

    public void setUserIdA(Long userIdA) {
        this.userIdA = userIdA;
    }

    public Long getUserIdB() {
        return userIdB;
    }

    public void setUserIdB(Long userIdB) {
        this.userIdB = userIdB;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
