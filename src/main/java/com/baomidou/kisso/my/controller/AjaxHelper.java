package com.baomidou.kisso.my.controller;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Ajax响应帮助类
 * <p>
 * @author oa
 * @date	2017/7/11
 * @version  1.0.0
 */
public class AjaxHelper {

    /**
     * @Description 响应JSON内容字符串
     * @param response
     * @param object
     * @throws IOException
     */
	public static void jsonPrint( HttpServletResponse response, Object object, String charset ) throws IOException {
		outPrint(response, JSON.toJSONString(object), charset);
	}


    /**
     * @Description 响应Ajax请求
     * @param response
     * @param content
     *            响应内容
     * @param charset
     *            字符编码
     * @throws IOException
     */
	public static void outPrint( HttpServletResponse response, String content, String charset ) throws IOException {
		response.setContentType("text/html;charset=" + charset);
		PrintWriter out = response.getWriter();
		out.print(content);
		out.flush();
	}

}
