package com.pf.im.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * IM 聊天历史消息实体，对应表 im_chat_message。
 */
public class ImChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String roomId;

    private Long fromUserId;

    private Long toUserId;

    private String msgType;

    /**
     * 消息内容密文：与 ChatMessage.content 对应
     */
    private String contentCipher;

    /**
     * 扩展信息（JSON），与 ChatMessage.extra 对应
     */
    private String extra;

    /**
     * 消息时间戳，毫秒
     */
    private Long msgTime;

    private Integer readFlag;

    private Integer deletedFlag;

    private Date createTime;

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

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContentCipher() {
        return contentCipher;
    }

    public void setContentCipher(String contentCipher) {
        this.contentCipher = contentCipher;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Long msgTime) {
        this.msgTime = msgTime;
    }

    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }

    public Integer getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
