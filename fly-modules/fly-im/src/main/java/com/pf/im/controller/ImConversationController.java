package com.pf.im.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pf.common.core.web.controller.BaseController;
import com.pf.common.core.web.domain.AjaxResult;
import com.pf.common.security.utils.SecurityUtils;
import com.pf.im.domain.ImConversation;
import com.pf.im.service.ImConversationService;

/**
 * 会话列表接口：
 * 提供按当前登录用户或指定用户查询会话列表的能力。
 */
@RestController
@RequestMapping("/conversation")
public class ImConversationController extends BaseController {

    private final ImConversationService conversationService;

    public ImConversationController(ImConversationService conversationService) {
        this.conversationService = conversationService;
    }

    /**
     * 获取当前登录用户的会话列表（增强版，包含对方用户信息）。
     *
     * @return 会话列表（包含扩展字段）
     */
    @GetMapping("/my")
    public AjaxResult listMyConversations() {
        Long userId = SecurityUtils.getUserId();
        List<ImConversation> list = conversationService.listByUserId(userId);
        
        // 将会话转换为包含扩展信息的Map列表
        List<Map<String, Object>> result = new ArrayList<>();
        for (ImConversation conversation : list) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", conversation.getId());
            item.put("roomId", conversation.getRoomId());
            item.put("conversationType", conversation.getConversationType());
            item.put("userIdA", conversation.getUserIdA());
            item.put("userIdB", conversation.getUserIdB());
            item.put("status", conversation.getStatus());
            item.put("remark", conversation.getRemark());
            item.put("createTime", conversation.getCreateTime());
            item.put("updateTime", conversation.getUpdateTime());
            
            // 确定对方用户ID
            Long otherUserId = null;
            if (userId.equals(conversation.getUserIdA())) {
                otherUserId = conversation.getUserIdB();
            } else if (userId.equals(conversation.getUserIdB())) {
                otherUserId = conversation.getUserIdA();
            }
            
            // TODO: 这里应该调用用户服务获取对方用户信息
            // 暂时使用模拟数据
            if (otherUserId != null) {
                item.put("otherUserName", "用户" + otherUserId);
                item.put("otherUserRole", "医生");
                item.put("otherUserAvatar", null);
            }
            
            // TODO: 获取最后一条消息
            item.put("lastMessage", "暂无消息");
            item.put("lastMessageTime", "");
            item.put("unreadCount", 0);
            
            result.add(item);
        }
        
        return success(result);
    }

    /**
     * （可选）按指定用户ID查询其会话列表，供后台使用。
     *
     * @param userId 用户ID
     * @return 会话列表
     */
    @GetMapping("/byUser")
    public AjaxResult listByUser(@RequestParam("userId") Long userId) {
        List<ImConversation> list = conversationService.listByUserId(userId);
        return success(list);
    }
}
