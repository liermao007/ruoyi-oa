package com.thinkgem.jeesite.common.utils;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author oa
 */
public class RedirectMessageUtils {
    public static void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        redirectAttributes.addFlashAttribute("message", sb.toString());
    }
}
