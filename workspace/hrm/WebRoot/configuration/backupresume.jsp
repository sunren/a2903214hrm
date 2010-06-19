<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<title>���ݱ����뻹ԭ</title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'���ݱ����뻹ԭ'" />
	<s:param name="helpUrl" value="'59'" />
</s:component>

<form id="backupForm" name="backupForm" action="backup.action" namespace="/configuration" method="post">
<s:token/>
<s:hidden id="testbin" name="testbin"/>
   <br>
   <table name="resumeTable" cellpadding="0" cellspacing="0" class="gridtableList" width="100%">
   <tr>
   <td>
   1. ������ֻ��������MySQL�����£��������װ��Oracle�����£���������DBA��ϵ���ݱ������ˡ�
   </td></tr>
   <tr><td>
   2. ���ݱ��ݺͻָ�ֻ�Ƕ����ݿ����ݽ��в��������ᱸ�ݻ�ָ��û��Ѿ��ϴ����ļ���
   </td></tr>  
   <tr><td>
   3. ���ݱ��ݺͻָ�ʱ�뱣֤û�������û��ڲ������ݣ�����ᵼ��ϵͳ�쳣��
   </td></tr>
   <tr><td>
   4. �������ݱ����Ժ��ϴ����ļ������ݻָ����ļ����ӿ��ܶ�ʧ�����²����ļ����ɶ�д��   
   </td></tr>
   <tr><td>&nbsp;</td></tr>
   </table>
   <br>
   <span>
   <input type ="button" class ="button" value ="�������ݿ�" onclick ="showTip_div(0,null);">
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
	                <a href="#" onclick="splits('first');"><img src='../resource/images/start.gif' width='11' height='9' alt='��ʼ'>��ʼ</a>
	                <a href="#" onclick="splits('previous');"><img src='../resource/images/previous.gif' width='6' height='9' alt='��ҳ'>��ҳ</a>
	                  ��<s:property value="pager.currentPage+'/'+pager.totalPages"/>ҳ����<s:property value="pager.totalRows"/>����
	                <a href="#" onclick="splits('next');">��ҳ<img src='../resource/images/next.gif' width='6' height='9'></a>
	                <a href="#" onclick="splits('last');">���<img src='../resource/images/end.gif' width='11' height='9' alt='���'></a>
				</td>
		     </tr>
		</s:if> 
		<tr>
			<th>�ļ�����</th>
			<th>����ʱ��</th>
			<th>�ļ���С</th>
			<th>ִ�в���</th>
		</tr>
		<s:if test="!resumeList.isEmpty()">
			<s:iterator value="resumeList">
				<tr>
					<td align ="center"><s:property value="fileName"/></td>
					<td align ="center"><s:property value="fileCreatTime"/></td>
					<td align ="center"><s:property value="fileSize"/> k</td>
					<td align ="center">
					<span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer" href="#" onclick="deleteResume('<s:property value='fileName'/>');">ɾ��</span>&nbsp;|
					<span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer" href="#" onclick= "showTip_div(1,'<s:property value='fileName'/>');">��ԭ</span>&nbsp;			
					</td>
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<tr><td colspan="6" align="center">û�б����ļ�</td></tr>
		</s:else>
	</table>
</form>

<div id="dlgSqlbin" class="prompt_div_inline">
    <table width="100%" cellspacing="0" cellpadding="0" class="formtable">
        <tr><td >���ķ�������MySQL��binĿ¼·�����������������Ը��������磺
             <br>&nbsp;&nbsp;Windows: C:\Program Files\MySQL\MySQL Server 5.0\bin
             <br>&nbsp;&nbsp;Linux�£�   /usr/local/mysql5/bin</td></tr>
        <tr><td>
        <table><tr><td nowrap>
	    <s:textfield id = "binInput" name = "binInput" size = "36" label = "MySQL��binĿ¼·��"></s:textfield>
	    </td></tr></table>
	    </td></tr>	
	    <tr></tr>
		<tr>
		<td align ="center">
		<input type="button" onclick="agreeSqlBin();" value="ȷ��">
		<input type="button" onclick="closeSqlBin();" value="ȡ��"></td>
		</tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:398px;height:114px;top:0px;left:0px;" frameborder="0" ></iframe>
</div>

<script type="text/javascript" language="javascript">
// ��ʼ��dialog
hrm.common.initDialog('dlgSqlbin', 500);

var tip = -1;
var operateId = null;
function showTip_div(flag,id){
	var file = $('#testbin').val() ;
	if(file.length < 10 || file.indexOf("bin") == -1){
		$('#dlgSqlbin').dialog('option', 'title', 'mysql�������趨');
		hrm.common.openDialog('dlgSqlbin');
		return;
	}
	tip = flag;
	operateId = id;
	if(flag == 0){
	    if (confirm("��ȷ��Ҫ�������ݿ���?")){ 
		    agreeOperate(); 
		    tip = -1;
		    operateId = null;
	    }
	}
	else if (confirm("��ȷ��Ҫ��ԭ���ݿ���?")){ 
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
	if(tip == -1 ){alert("δִ�в���!");return;}	
	if(tip == 0){document.getElementById('backupForm').submit();}
	else{
		$("#resumeName").val(operateId);
		document.getElementById('resumeForm').submit();
	}
}
	
function deleteResume(id){
	if(id.length == 0){alert("δִ�в���!");return;}
	if(confirm("��ȷ��Ҫɾ����������ļ���?")){
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