<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
<title>OA办公系统</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctxStatic}/oaApp/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctxStatic}/oaApp/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctxStatic}/oaApp/css/fullcalendar.css" />
<link rel="stylesheet" href="${ctxStatic}/oaApp/css/matrix-style.css" />
<link rel="stylesheet" href="${ctxStatic}/oaApp/css/matrix-media.css" />
<link href="${ctxStatic}/oaApp/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${ctxStatic}/oaApp/css/oaApp2017101901.css" />
</head>
<body>

<!--Header-part-->
<div id="header">
  <h1><a href="dashboard.html">智慧岗位工作平台</a></h1>
</div>


<!--sidebar-menu-->


<div id="content">
  <div id="content-header">
  </div>

  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box widget-calendar">
          <div class="widget-title"> <span class="icon"><i class="icon-calendar"></i></span>
            <h5>日程安排</h5>
            <div class="buttons float_R"> <a id="add-event" data-toggle="modal" href="#modal-add-event" class="btn btn-inverse btn-mini bgC_Blue"><i class="icon-plus icon-white"></i></a>
              <div class="modal hide" id="modal-add-event">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">×</button>
                  <h3>添加一个新事件</h3>
                </div>
                <div class="modal-body">
                  <p>事件名称:</p>
                  <p>
                    <input id="event-name" type="text" />
                  </p>
                </div>
                <div class="modal-footer"> <a href="#" class="btn" data-dismiss="modal">取消</a> <a href="#" id="add-event-submit" class="btn btn-primary">添加事件</a> </div>
              </div>
            </div>
          </div>
          <div class="widget-content pad_No bor_N">
            <div class="panel-left marT_36">
              <div id="fullcalendar"></div>
            </div>
            <div id="external-events" class="panel-right">
              <div class="panel-title">
                <h5>将事件拖到刻度盘</h5>
              </div>
              <div class="panel-content">
                <div class="external-event ui-draggable label label-inverse">我的活动 1</div>
                <div class="external-event ui-draggable label label-inverse">我的活动 2</div>
                <div class="external-event ui-draggable label label-inverse">我的活动 3</div>
                <div class="external-event ui-draggable label label-inverse">我的活动 4</div>
                <div class="external-event ui-draggable label label-inverse">我的活动 5</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!--尾部-part-->

<div id="footer">

</div>
<!--end-Footer-part-->



<!--end-尾部-part-->

<script src="${ctxStatic}/oaApp/js/excanvas.min.js"></script>
<script src="${ctxStatic}/oaApp/js/jquery.min.js"></script>
<script>
    $(document).ready(function(){
        $('#footer').load('footer.html');

    })
</script>
<script src="${ctxStatic}/oaApp/js/jquery.ui.custom.js"></script>
<script src="${ctxStatic}/oaApp/js/bootstrap.min.js"></script>
<!--<script src="js/jquery.flot.min.js"></script> -->
<!--<script src="js/jquery.flot.resize.min.js"></script> -->
<script src="${ctxStatic}/oaApp/js/jquery.peity.min.js"></script>
<script src="${ctxStatic}/oaApp/js/matrix.js"></script>
<script src="${ctxStatic}/oaApp/js/fullcalendar.min.js"></script>
<script src="${ctxStatic}/oaApp/js/matrix.calendar.js"></script>
<script src="${ctxStatic}/oaApp/js/matrix.chat.js"></script>
<script src="${ctxStatic}/oaApp/js/jquery.validate.js"></script>
<script src="${ctxStatic}/oaApp/js/matrix.form_validation.js"></script>
<script src="${ctxStatic}/oaApp/js/jquery.wizard.js"></script>
<script src="${ctxStatic}/oaApp/js/jquery.uniform.js"></script>
<script src="${ctxStatic}/oaApp/js/select2.min.js"></script>
<script src="${ctxStatic}/oaApp/js/matrix.popover.js"></script>
<script src="${ctxStatic}/oaApp/js/jquery.dataTables.min.js"></script>
<script src="${ctxStatic}/oaApp/js/matrix.tables.js"></script>
<script src="${ctxStatic}/oaApp/js/footer.js"></script>
<!--<script src="js/matrix.interface.js"></script> -->
<script type="text/javascript">
  // This function is called from the pop-up menus to transfer to
  // a different page. Ignore if the value returned is a null string:
  function goPage (newURL) {

      // if url is empty, skip the menu dividers and reset the menu selection to default
      if (newURL != "") {
      
          // if url is "-", it is this page -- reset the menu:
          if (newURL == "-" ) {
              resetMenu();            
          } 
          // else, send page to designated URL            
          else {  
            document.location.href = newURL;
          }
      }
  }

// resets the menu selection upon entry to this page:
function resetMenu() {
   document.gomenu.selector.selectedIndex = 2;
}
</script>
</body>
</html>
