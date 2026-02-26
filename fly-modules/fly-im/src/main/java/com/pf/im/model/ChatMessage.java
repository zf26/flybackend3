package com.pf.im.model;

import java.io.Serializable;

/**
 * IM 聊天消息模型：
 * 用于前后端通过 STOMP 传输的统一消息结构，
 * 支持私聊（toUserId）和群聊/房间（roomId）两种场景。
 */
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    // 消息类型，例如：CHAT、NOTICE 等
    private String type;

    // 发送方用户 ID，由服务端根据当前会话自动填充
    private Long fromUserId;

    // 接收方用户 ID（私聊时必填）
    private Long toUserId;

    // 房间/会话 ID（群聊时使用）
    private String roomId;

    // 文本内容或序列化后的 JSON 内容
    private String content;

    // 消息时间戳，毫秒
    private Long timestamp;

    // 预留扩展字段，按需自定义
    private String extra;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
