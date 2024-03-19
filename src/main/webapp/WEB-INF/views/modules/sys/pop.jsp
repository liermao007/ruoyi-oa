<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<script src='${pageContext.request.contextPath}/static/jquery/jquery.min.js'></script>
    <script src='${pageContext.request.contextPath}/static/jquery/yanue.pop.js'></script>
</head>
<body>
<script type="text/javascript" >
	  //记得加载jquery
	  //作者：yanue
	  //使用参数：1.标题，2.链接地址，3.内容简介
	  window.onload=function(){
			var pop=new Pop("这里是标题，哈哈",
			"http://www.yanue.info/js/pop/pop.html",
			"请输入你的内容简介，这里是内容简介.请输入你的内容简介，这里是内容简介.请输入你的内容简介，这里是内容简介");
		}
</script>
<script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript" src="yanue.pop.js"></script>
<div id="pop" style="display:none;">
	<style type="text/css">
	*{margin:0;padding:0;}
	#pop{background:#fff;width:260px;border:1px solid #e0e0e0;font-size:12px;position: fixed;right:10px;bottom:10px;}
	#popHead{line-height:32px;background:#f6f0f3;border-bottom:1px solid #e0e0e0;position:relative;font-size:12px;padding:0 0 0 10px;}
	#popHead h2{font-size:14px;color:#666;line-height:32px;height:32px;}
	#popHead #popClose{position:absolute;right:10px;top:1px;}
	#popHead a#popClose:hover{color:#f00;cursor:pointer;}
	#popContent{padding:5px 10px;}
	#popTitle a{line-height:24px;font-size:14px;font-family:'微软雅黑';color:#333;font-weight:bold;text-decoration:none;}
	#popTitle a:hover{color:#f60;}
	#popIntro{text-indent:24px;line-height:160%;margin:5px 0;color:#666;}
	#popMore{text-align:right;border-top:1px dotted #ccc;line-height:24px;margin:8px 0 0 0;}
	#popMore a{color:#f60;}
	#popMore a:hover{color:#f00;}
	</style>
	<div id="popHead">
	<a id="popClose" title="关闭">关闭</a>
	<h2>温馨提示</h2>
	</div>
	<div id="popContent">
	<dl>
		<dt id="popTitle"><a href="http://yanue.info/" target="_blank">这里是参数</a></dt>
		<dd id="popIntro">这里是内容简介</dd>
	</dl>
	<p id="popMore"><a href="http://yanue.info/" target="_blank">查看 »</a></p>
	</div>
</div>
<!--右下角pop弹窗 end-->
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>zhel 
</body>
</html>