package com.thinkgem.jeesite.modules.oa.servlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Key Util: 1> according file name|size ..., generate a key;
 * 			 2> the key should be unique.
 */
public class TokenUtil {

	/**
	 * 生成Token， A(hashcode>0)|B + |name的Hash值| +_+size的值
	 * @param name
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(String name, String size,HttpServletRequest request)
			throws IOException {
        if (name == null || size == null)
			return "";
		int code = name.hashCode();
		try {
			String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_" + size.trim();
			/** TODO: store your token, here just create a file */
			IoUtil.storeToken(token,request);
			
			return token;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
}
