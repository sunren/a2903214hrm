<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<title>数据备份与还原</title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'数据备份与还原'" />
	<s:param name="helpUrl" value="'59'" />
</s:component>

<form id="backupForm" name="backupForm" action="backup.action" namespace="/configuration" method="post">
<s:token/>
<s:hidden id="testbin" name="testbin"/>
   <br>
   <table name="resumeTable" cellpadding="0" cellspacing="0" class="gridtableList" width="100%">
   <tr>
   <td>
   1. 本功能只能运行在MySQL环境下，如果您安装在Oracle环境下，请与您的DBA联系数据备份事宜。
   </td></tr>
   <tr><td>
   2. 数据备份和恢复只是对数据库内容进行操作，不会备份或恢复用户已经上传的文件。
   </td></tr>  
   <tr><td>
   3. 数据备份和恢复时请保证没有其他用户在操作数据，否则会导致系统异常。
   </td></tr>
   <tr><td>
   4. 对于数据备份以后上传的文件，数据恢复后，文件链接可能丢失，导致部分文件不可读写。   
   </td></tr>
   <tr><td>&nbsp;</td></tr>
   </table>
   <br>
   <span>
   <input type ="button" class ="button" value ="备份数据库" onclick ="showTip_div(0,null);">
   </span>
</form>

<form id="deleteForm" name="deleteForm" action="delete.action" namespace="/configuration" method="post">
<s:token/>
    <s:hidden id="deleteName" name="deleteName"/>
</form>

<form id="sqlbinForm" name="sqlbinForm" action="sqlbin.action" namespace="/configuration" method="post">
<s:token/>
    <s:hidden id="sqlbin" name="sqlbin"/>
</form>

<form id="resumeForm" name="resumeForm" action="resume.action" namespace="/configuration" method="post">
<s:token/>
    <s:hidden id="resumeName" name="resumeName"/>
	<s:hidden id="order" name="pager.order"/>
	<s:hidden id="operate" name="pager.operate"/>
  	<s:hidden name="pager.currentPage"/>
	<br>
	<table name="resumeTable" cellpadding="0" cellspacing="0" class="gridtableList" width="100%">
		<s:if test="pager.isSplit()">
		     <tr>
		     	<td colspan="11" align="right"  class="listViewPaginationTdS1">
	                <a href="#" onclick="splits('first');"><img src='../resource/images/start.gif' width='11' height='9' alt='开始'>开始</a>
	                <a href="#" onclick="splits('previous');"><img src='../resource/images/previous.gif' width='6' height='9' alt='上页'>上页</a>
	                  （<s:property value="pager.currentPage+'/'+pager.totalPages"/>页｜共<s:property value="pager.totalRows"/>条）
	                <a href="#" onclick="splits('next');">下页<img src='../resource/images/next.gif' width='6' height='9'></a>
	                <a href="#" onclick="splits('last');">最后<img src='../resource/images/end.gif' width='11' height='9' alt='最后'></a>
				</td>
		     </tr>
		</s:if> 
		<tr>
			<th>文件名称</th>
			<th>备份时间</th>
			<th>文件大小</th>
			<th>执行操作</th>
		</tr>
		<s:if test="!resumeList.isEmpty()">
			<s:iterator value="resumeList">
				<tr>
					<td align ="center"><s:property value="fileName"/></td>
					<td align ="center"><s:property value="fileCreatTime"/></td>
					<td align ="center"><s:property value="fileSize"/> k</td>
					<td align ="center">
					<span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer" href="#" onclick="deleteResume('<s:property value='fileName'/>');">删除</span>&nbsp;|
					<span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer" href="#" onclick= "showTip_div(1,'<s:property value='fileName'/>');">还原</span>&nbsp;			
					</td>
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<tr><td colspan="6" align="center">没有备份文件</td></tr>
		</s:else>
	</table>
</form>

<div id="dlgSqlbin" class="prompt_div_inline">
    <table width="100%" cellspacing="0" cellpadding="0" class="formtable">
        <tr><td >您的服务器端MySQL的bin目录路径设置有误，请先予以更正，例如：
             <br>&nbsp;&nbsp;Windows: C:\Program Files\MySQL\MySQL Server 5.0\bin
             <br>&nbsp;&nbsp;Linux下：   /usr/local/mysql5/bin</td></tr>
        <tr><td>
        <table><tr><td nowrap>
	    <s:textfield id = "binInput" name = "binInput" size = "36" label = "MySQL的bin目录路径"></s:textfield>
	    </td></tr></table>
	    </td></tr>	
	    <tr></tr>
		<tr>
		<td align ="center">
		<input type="button" onclick="agreeSqlBin();" value="确定">
		<input type="button" onclick="closeSqlBin();" value="取消"></td>
		</tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:398px;height:114px;top:0px;left:0px;" frameborder="0" ></iframe>
</div>

<script type="text/javascript" language="javascript">
// 初始化dialog
hrm.common.initDialog('dlgSqlbin', 500);

var tip = -1;
var operateId = null;
function showTip_div(flag,id){
	var file = $('#testbin').val() ;
	if(file.length < 10 || file.indexOf("bin") == -1){
		$('#dlgSqlbin').dialog('option', 'title', 'mysql服务器设定');
		hrm.common.openDialog('dlgSqlbin');
		return;
	}
	tip = flag;
	operateId = id;
	if(flag == 0){
	    if (confirm("您确定要备份数据库吗?")){ 
		    agreeOperate(); 
		    tip = -1;
		    operateId = null;
	    }
	}
	else if (confirm("您确定要还原数据库吗?")){ 
	    agreeOperate(); 
	    tip = -1;
	    operateId = null;
	} 
}

function closeSqlBin(){
	hrm.common.closeDialog('dlgSqlbin');
}
	
function agreeSqlBin(){
	$("#sqlbin").val($("#binInput").val());
    document.getElementById("sqlbinForm").submit();
} 
	
function agreeOperate(){
	if(tip == -1 ){alert("未执行操作!");return;}	
	if(tip == 0){document.getElementById('backupForm').submit();}
	else{
		$("#resumeName").val(operateId);
		document.getElementById('resumeForm').submit();
	}
}
	
function deleteResume(id){
	if(id.length == 0){alert("未执行操作!");return;}
	if(confirm("您确定要删除这个备份文件吗?")){
		$('#deleteName').val(id);
		document.getElementById('deleteForm').submit();	
	}
}
	
function resetBackup(){
	$("#fileName").val("");
	$("#fileCreatTime").val("");
	$("#fileSize").val("");
	document.getElementById('searchForm').submit();		
}
</script>
</body>
</html>