package com.thinkgem.jeesite.modules.linkFace.linkface.exception;

import org.apache.shiro.authc.AccountException;

/**
 * 
 */
public class LinkFaceException  extends AccountException {
    public LinkFaceException() {
    }

    public LinkFaceException(String message) {
        super(message);
    }

    public LinkFaceException(Throwable cause) {
        super(cause);
    }

    public LinkFaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
