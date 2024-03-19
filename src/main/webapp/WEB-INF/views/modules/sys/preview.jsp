<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<style type="text/css">
		h2, h3 { margin-top:0; }
		#results { float:right; margin:20px; padding:20px; border:1px solid; background:#ccc; }
		.video-img-upload  {
			background: rgba(0,0,0, 0.8);
			height: 40px;
		}
		.icon-camera {
			color: #fff;
			font-size: 18px;
			text-align: center;
		}
	</style>
</head>
<body>
<script src='${ctxStatic}/jquery/jquery-1.9.1.min.js'></script>

	<div id="my_camera"></div>
	
	<!-- First, include the Webcam.js JavaScript Library -->
	<script type="text/javascript" src="${ctxStatic}/webcamjs/webcam.min.js"></script>
	
	<!-- Configure a few settings and attach camera -->
	<script language="JavaScript">
		Webcam.set({
			width: 280,
			height: 210,
			image_format: 'jpeg',
			jpeg_quality: 90
		});
		Webcam.attach( '#my_camera' );
	</script>

	<div class="video-img-upload js-get-avatar" style="text-align: center;width: 280px;" onclick="save_photo();">
		<i class="icon-camera" style="position: relative;top: 7px;"  >点击认证</i>
	</div>
	<!-- Code to handle taking the snapshot and displaying it locally -->
	<script language="JavaScript">
		function preview_snapshot() {
			// freeze camera so user can preview pic
			Webcam.freeze();
		}
		
		function cancel_preview() {
			// cancel preview freeze and return to live camera feed
			Webcam.unfreeze();
		}
		
		function save_photo() {
			Webcam.freeze();
			// actually snap photo (from preview freeze) and display it
			Webcam.snap( function(data_uri) {
				// display results in page
				var Pic = data_uri.replace("data:image/jpeg;base64,", "");
				parent.$("#username").removeClass("required");
				parent.$("#password").removeClass("required");
				parent.$("#userlogo").val(Pic);
				parent.$("#loginForm").submit();
			} );
		}
	</script>
	
</body>
</html>
