package com.pf.im.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pf.common.core.utils.StringUtils;
import com.pf.common.core.web.controller.BaseController;
import com.pf.common.core.web.domain.AjaxResult;
import com.pf.im.service.ConversationKeyService;

/**
 * 会话密钥控制器：
 * 为前端提供房间级会话密钥获取接口，
 * 前端在进入某个房间时调用此接口获取 conversationKey，
 * 用于对消息内容进行端到端加解密。
 */
@RestController
@RequestMapping("/conversationKey")
public class ConversationKeyController extends BaseController {

    private final ConversationKeyService conversationKeyService;

    public ConversationKeyController(ConversationKeyService conversationKeyService) {
        this.conversationKeyService = conversationKeyService;
    }

    /**
     * 获取或创建指定房间的会话密钥。
     *
     * @param roomId 房间/会话 ID
     * @return 明文会话密钥字符串
     */
    @GetMapping("/room/{roomId}")
    public AjaxResult getOrCreateConversationKey(@PathVariable("roomId") String roomId) {
        if (StringUtils.isEmpty(roomId)) {
            return error("roomId 不能为空");
        }
        String conversationKey = conversationKeyService.getOrCreateConversationKey(roomId);
        if (conversationKey == null) {
            return error("获取会话密钥失败");
        }
        return success(conversationKey);
    }
}
