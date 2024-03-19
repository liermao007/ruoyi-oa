<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <script type="text/javascript">
      var table1=  window.parent.document.getElementById("name");
      function preview() {
          alert(document.body.clientWidth+","+document.body.clientHeight)
          return;
          bdhtml=window.document.body.innerHTML;
          sprnstr="<!--startprint-->";
          eprnstr="<!--endprint-->";
//          window.document.body.innerHTML=$('table').eq(0).html();
          window.document.body.innerHTML=document.getElementById("print").innerHTML;
          setTimeout('yscz()',500);
      }
      function yscz(){
          window.innerWidth =
          window.print();
          window.document.body.innerHTML=bdhtml;
          location.reload();
      }
    </script>
</head>
<body>
<div style="background-color: #ffffff">
    <input type="button" value="打印" onclick="preview()" />
   <table style="width: 500px" id="print">
       <tbody >
       <tr>
           <td nowrap="nowrap" height="33" width="4%">
               &nbsp;</td>
           <td nowrap="nowrap" height="33" width="6%">
               &nbsp;</td>
           <td nowrap="nowrap" height="33" width="84%">
               <p align="center">
                   <b><font size="4">支出凭单</font></b></p>
           </td>
           <td nowrap="nowrap" height="33" width="3%">
               &nbsp;</td>
           <td nowrap="nowrap" height="33" width="3%">
               &nbsp;</td>
       </tr>
       <tr>
           <td nowrap="nowrap" height="37" width="4%">
               &nbsp;</td>
           <td nowrap="nowrap" height="37" width="6%">
               &nbsp;</td>
           <td nowrap="nowrap" height="37" width="84%">
               <table border="0" cellpadding="0" cellspacing="0" height="25" width="100%">
                   <tbody>
                   <tr>
                       <td nowrap="nowrap" align="center" height="25" width="12%">
                           &nbsp;</td>
                       <td nowrap="nowrap" align="center" height="25" width="20%">
                           &nbsp;</td>
                       <td nowrap="nowrap" align="center" height="25" width="17%">
                           <p align="right">
                               <font size="2"><b>申请日期：</b></font></p>
                       </td>
                       <td nowrap="nowrap" align="center" height="25" width="17%">
                           <p align="left">
                               <font size="2">2017-02-13</font></p>
                       </td>
                       <td nowrap="nowrap" align="center" height="25" width="17%">
                           <p align="right">
                               <font size="2"><b>编号：</b></font></p>
                       </td>
                       <td nowrap="nowrap" align="center" height="25" width="17%">
                           <p align="left">
                               <font size="2">[编号]</font></p>
                       </td>
                   </tr>
                   </tbody>
               </table>
           </td>
           <td nowrap="nowrap" height="37" width="3%">
               &nbsp;</td>
           <td nowrap="nowrap" height="37" width="3%">
               &nbsp;</td>
       </tr>
       <tr>
           <td nowrap="nowrap" height="110" width="4%">
               &nbsp;</td>
           <td nowrap="nowrap" height="110" width="6%">
               &nbsp;</td>
           <td nowrap="nowrap" height="110" width="84%">
               <table border="1" bordercolor="#000000" cellpadding="0" cellspacing="0" height="76" width="100%">
                   <tbody>
                   <tr>
                       <td nowrap="nowrap" align="center" height="159" width="100%">
                           <table border="0" cellpadding="0" cellspacing="0" height="119" width="100%">
                               <tbody>
                               <tr>
                                   <td nowrap="nowrap" align="left" height="55" width="15%">
                                       <p align="right">
                                           <font size="2"><b>即　　付：</b></font></p>
                                   </td>
                                   <td nowrap="nowrap" colspan="3" height="55" style="BORDER-BOTTOM: #000000 2px solid" width="78%">
                                       <font size="2">4545</font></td>
                                   <td nowrap="nowrap" height="55" width="9%">
                                       <font size="2"><b>款</b></font></td>
                               </tr>
                               <tr>
                                   <td nowrap="nowrap" align="left" height="55" width="15%">
                                       <p align="right">
                                           <font size="2"><b>计人民币：</b></font></p>
                                   </td>
                                   <td nowrap="nowrap" height="55" style="BORDER-BOTTOM: #000000 2px solid" width="52%">
                                       <font size="2">[大写金额]</font></td>
                                   <td nowrap="nowrap" height="55" width="9%">
                                       <p align="right">
                                           <span style="FONT-FAMILY: 宋体; mso-bidi-font-size: 11.0pt; mso-ascii-theme-font: minor-fareast; mso-fareast-theme-font: minor-fareast; mso-hansi-theme-font: minor-fareast; mso-bidi-font-family: Times New Roman; mso-bidi-theme-font: minor-bidi; mso-ansi-language: EN-US; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA"><font size="2"><b>￥</b></font></span></p>
                                   </td>
                                   <td nowrap="nowrap" height="55" style="BORDER-BOTTOM: #000000 2px solid" width="11%">
                                       <font size="2">555,455</font></td>
                                   <td nowrap="nowrap" height="55" width="9%">
                                       <font size="2"><b>元</b></font></td>
                               </tr>
                               <tr>
                                   <td nowrap="nowrap" colspan="5" height="56" width="96%">
                                       <table border="0" cellpadding="0" cellspacing="0" height="44" width="100%">
                                           <tbody>
                                           <tr>
                                               <td nowrap="nowrap" height="44" width="16%">
                                                   &nbsp;</td>
                                               <td nowrap="nowrap" height="44" width="16%">
                                                   &nbsp;</td>
                                               <td nowrap="nowrap" align="right" height="44" width="17%">
                                                   <font size="2"><b>申请人：</b></font></td>
                                               <td nowrap="nowrap" align="right" height="44" width="17%">
                                                   <p align="left">
                                                       <font size="2">系统管理员</font></p>
                                               </td>
                                               <td nowrap="nowrap" align="right" height="44" width="12%">
                                                   <font size="2"><b>部门：</b></font></td>
                                               <td nowrap="nowrap" align="right" height="44" width="22%">
                                                   <p align="left">
                                                       <font size="2">总经理办公室</font></p>
                                               </td>
                                           </tr>
                                           </tbody>
                                       </table>
                                   </td>
                               </tr>
                               </tbody>
                           </table>
                       </td>
                   </tr>
                   <tr>
                       <td nowrap="nowrap" align="center" height="98" width="100%">
                           <table border="0" cellpadding="0" cellspacing="0" height="118" width="100%">
                               <tbody>
                               <tr>
                                   <td nowrap="nowrap" align="center" height="21" width="17%">
                                       <font size="2"><b>总经理：</b></font></td>
                                   <td nowrap="nowrap" align="center" height="21" width="18%">
                                       <font size="2"><b>财务部门：</b></font></td>
                                   <td nowrap="nowrap" align="center" height="21" width="18%">
                                       <font size="2"><b>部门经理：</b></font></td>
                                   <td nowrap="nowrap" align="center" height="21" width="18%">
                                       <font size="2"><b>小组负责人：</b></font></td>
                                   <td nowrap="nowrap" align="center" height="21" width="18%">
                                       <font size="2"><b>复　核：</b></font></td>
                                   <td nowrap="nowrap" align="center" height="21" width="11%">
                                       <font size="2"><b>领款人：</b></font></td>
                               </tr>
                               <tr>
                                   <td nowrap="nowrap" align="center" height="97" width="17%">
                                       <font size="2"></font></td>
                                   <td nowrap="nowrap" align="center" height="97" width="18%">
                                       <font size="2"></font></td>
                                   <td nowrap="nowrap" align="center" height="97" width="18%">
                                       <font size="2"></font></td>
                                   <td nowrap="nowrap" align="center" height="97" width="18%">
                                       <font size="2"></font></td>
                                   <td nowrap="nowrap" align="center" height="97" width="18%">
                                       <font size="2"></font></td>
                                   <td nowrap="nowrap" align="center" height="97" width="11%">
                                       <font size="2"></font></td>
                               </tr>
                               </tbody>
                           </table>
                       </td>
                   </tr>
                   </tbody>
               </table>
           </td>
           <td nowrap="nowrap" height="110" width="3%">
               <table border="0" cellpadding="0" cellspacing="0" height="103" width="100%">
                   <tbody>
                   <tr>
                       <td nowrap="nowrap" align="center" height="25" width="100%">
                           <font size="2"><b>附</b></font></td>
                   </tr>
                   <tr>
                       <td nowrap="nowrap" align="center" height="26" width="100%">
                           <font size="2"><b>单</b></font></td>
                   </tr>
                   <tr>
                       <td nowrap="nowrap" align="center" height="26" width="100%">
                           <font size="2"><b>据</b></font></td>
                   </tr>
                   <tr>
                       <td nowrap="nowrap" align="center" height="26" width="100%">
                           <font size="2"></font></td>
                   </tr>
                   <tr>
                       <td nowrap="nowrap" align="center" height="26" width="100%">
                           <font size="2"><b>张</b></font></td>
                   </tr>
                   </tbody>
               </table>
           </td>
           <td nowrap="nowrap" height="110" width="3%">
               &nbsp;</td>
       </tr>
       <tr>
           <td nowrap="nowrap" height="1" width="4%">
               &nbsp;</td>
           <td nowrap="nowrap" height="1" width="6%">
               &nbsp;</td>
           <td nowrap="nowrap" height="1" width="84%">
               &nbsp;</td>
           <td nowrap="nowrap" height="1" width="3%">
               &nbsp;</td>
           <td nowrap="nowrap" height="1" width="3%">
               &nbsp;</td>
       </tr>
       </tbody>
   </table>
</div>
</body>
</html>