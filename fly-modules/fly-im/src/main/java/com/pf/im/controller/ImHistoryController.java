package com.pf.im.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pf.common.core.utils.StringUtils;
import com.pf.common.core.web.controller.BaseController;
import com.pf.common.core.web.domain.AjaxResult;
import com.pf.common.security.utils.SecurityUtils;
import com.pf.im.domain.ImChatMessage;
import com.pf.im.model.ChatMessage;
import com.pf.im.service.ImChatMessageService;

/**
 * 聊天历史接口：
 * 提供当前用户与目标用户之间的详细聊天记录查询（按时间排序），
 * 前端可根据 roomId 过滤到某个会话。
 */
@RestController
@RequestMapping("/history")
public class ImHistoryController extends BaseController {

    private final ImChatMessageService chatMessageService;

    public ImHistoryController(ImChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    /**
     * 获取当前用户与目标用户之间的聊天记录（密文），可按 roomId 过滤。
     *
     * @param targetUserId 目标用户ID
     * @param roomId       房间ID（可选）
     * @param limit        返回条数上限，默认 100
     * @return ChatMessage 列表，content 为密文，由前端使用 conversationKey 解密
     */
    @GetMapping("/private")
    public AjaxResult listPrivateHistory(@RequestParam("targetUserId") Long targetUserId,
                                         @RequestParam(value = "roomId", required = false) String roomId,
                                         @RequestParam(value = "limit", required = false, defaultValue = "100") int limit) {
        if (targetUserId == null) {
            return error("targetUserId 不能为空");
        }
        if (limit <= 0) {
            limit = 100;
        }
        Long currentUserId = SecurityUtils.getUserId();
        List<ImChatMessage> historyList = chatMessageService.listPrivateHistory(currentUserId, targetUserId, roomId, limit);
        List<ChatMessage> result = new ArrayList<>();
        for (ImChatMessage item : historyList) {
            ChatMessage msg = new ChatMessage();
            msg.setRoomId(item.getRoomId());
            msg.setFromUserId(item.getFromUserId());
            msg.setToUserId(item.getToUserId());
            msg.setType(item.getMsgType());
            msg.setTimestamp(item.getMsgTime());
            msg.setExtra(item.getExtra());
            // 此处直接返回密文内容，前端根据 roomId 对应的 conversationKey 解密
            msg.setContent(item.getContentCipher());
            result.add(msg);
        }
        return success(result);
    }
}
