<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>

<html>
	<head>
		<title>邮件设置</title>
		<style type="text/css">@import url("../resource/css/tab.css");</style>
		<script type="text/javascript" src="../resource/js/dwr/tabpane.js"></script>
		<script type="text/javascript" src="../dwr/interface/emailtemplate.js"></script>
		<script type="text/javascript" src="../dwr/interface/emailsend.js"></script>			
		<style type="text/css">
.mySpan{
	font-size: 12px;
	color: #002780;
	text-decoration: underline;
	cursor: pointer
}</style>

</head>
 <body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'邮件模版'" />
	<s:param name="helpUrl" value="'11'" />
</s:component>
<div class="btnlink">
<input type ="button" class ="button2" onclick="redirectMailManager();" value="返回"></input>
</div>
<table cellpadding="0" cellspacing="0" width="100%" class="gridtableList">
	<tr>
	  <th>模板编号</th>
	  <th>备注</th>
	  <th>发送模式</th>
	  <th>状态</th>
	  <th>操作</th>
	</tr>
<tbody id="tabPage1items">
<s:if test="!templateList.isEmpty()">        
	<s:iterator value="templateList" status="index">
	    <input type="hidden" id="et_status_<s:property value='id'/>" value="<s:property value='etStatus'/>" />
	    <div id="etContent<s:property value="id"/>" style="display:none"><s:property value="etContent"/></div>
		<tr align ="left" id="tabPage1<s:property value="id"/>">
		<td align ="left" NOWRAP id="etTitleNo<s:property value="id"/>" class="orangeFont"><s:property value="etTitleNo" /></td>
		<td align ="left" ><s:property value="etNotes"/></td>
		<td>
			<s:if test="etSendMode==null || etSendMode == 0">立即发送</s:if>
			<s:elseif test="etSendMode == 1">立即发送并保存</s:elseif>
			<s:elseif test="etSendMode == 2">保存后定时发送</s:elseif>
		</td>
		<td align ="center"><s:property value="etStatus ==0?'停用':'启用'"/></td>
		<td align ="right">
		<a href="modifyTemplateInit.action?templateId=<s:property value='id'/>" ><img alt= '修改' class = "mySpan" src = "../resource/images/edit.gif" /></a>
		<img href="#" alt= '预览' class = "mySpan" src = "../resource/images/Preview.gif" onclick="showDesc('<s:property value="id"/>','<s:property value="etTitleNo" />');"/>
		</td></tr>
	</s:iterator>
</s:if>
<s:else>
	<tr><td colspan="10"><script>errMsg("","不存在记录");</script></td></tr>
</s:else>
</tbody>
</table>	

<!--邮件模板--预览模板-->
<div id="dlgViewtemplate" class="prompt_div_inline">
	<TABLE cellpadding="0" cellspacing="1" border="0" width="100%" class="formtable">
		<tr><td>
		<div id="viewTemplate"></div>
		</td></tr>
		<tr><td align="center">			
		<input id="viewclose" type="button" onclick="hrm.common.closeDialog('dlgViewtemplate');" name="Submit3" value="关闭">
		</td></tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:498px;height:120px;top:0px;left:0px;" frameborder="0" ></iframe>
</div>

<script type="text/javascript" language="javascript">
// 初始化dialog；
hrm.common.initDialog('dlgViewtemplate', 520);

var editObj = null;				//操作行 tr
var cp = 'tabPage1';			//操作页面 integer  currentPage

// 返回邮件列表；
function redirectMailManager(){
    window.location="emailSearch.action";
}

// 显示模板内容；
function showDesc(templateNo,mailName){
	clearErr();
	//dwr读取模板标题、内容
	emailtemplate.getTemplateContent(mailName,callback);
	function callback(data){
		if(data == null){
			alert("无权操作，请重新登录后重试.");
			return;
		}
		$("#dlgViewtemplate").dialog('option', 'title', "预览邮件模版(" + mailName+ ")" .trim());
		hrm.common.openDialog('dlgViewtemplate');
		var html = "标　题：";
		html += data.title+"<br><br>";
		html += "内　容：";
		html += data.body+"<br><br>";
		$('#viewTemplate').html(html);
	}
}


//清除出错信息；
function clearErr(){
	if($('#errMsg').get(0)!=null){
		$('#errMsg').get(0).className="";
		$('#errMsg').get(0).innerHTML="";
	}
}
</script>
</body>
</html>
