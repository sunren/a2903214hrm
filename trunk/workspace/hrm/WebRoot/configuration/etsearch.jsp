<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>

<html>
	<head>
		<title>�ʼ�����</title>
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
	<s:param name="pagetitle" value="'�ʼ�ģ��'" />
	<s:param name="helpUrl" value="'11'" />
</s:component>
<div class="btnlink">
<input type ="button" class ="button2" onclick="redirectMailManager();" value="����"></input>
</div>
<table cellpadding="0" cellspacing="0" width="100%" class="gridtableList">
	<tr>
	  <th>ģ����</th>
	  <th>��ע</th>
	  <th>����ģʽ</th>
	  <th>״̬</th>
	  <th>����</th>
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
			<s:if test="etSendMode==null || etSendMode == 0">��������</s:if>
			<s:elseif test="etSendMode == 1">�������Ͳ�����</s:elseif>
			<s:elseif test="etSendMode == 2">�����ʱ����</s:elseif>
		</td>
		<td align ="center"><s:property value="etStatus ==0?'ͣ��':'����'"/></td>
		<td align ="right">
		<a href="modifyTemplateInit.action?templateId=<s:property value='id'/>" ><img alt= '�޸�' class = "mySpan" src = "../resource/images/edit.gif" /></a>
		<img href="#" alt= 'Ԥ��' class = "mySpan" src = "../resource/images/Preview.gif" onclick="showDesc('<s:property value="id"/>','<s:property value="etTitleNo" />');"/>
		</td></tr>
	</s:iterator>
</s:if>
<s:else>
	<tr><td colspan="10"><script>errMsg("","�����ڼ�¼");</script></td></tr>
</s:else>
</tbody>
</table>	

<!--�ʼ�ģ��--Ԥ��ģ��-->
<div id="dlgViewtemplate" class="prompt_div_inline">
	<TABLE cellpadding="0" cellspacing="1" border="0" width="100%" class="formtable">
		<tr><td>
		<div id="viewTemplate"></div>
		</td></tr>
		<tr><td align="center">			
		<input id="viewclose" type="button" onclick="hrm.common.closeDialog('dlgViewtemplate');" name="Submit3" value="�ر�">
		</td></tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:498px;height:120px;top:0px;left:0px;" frameborder="0" ></iframe>
</div>

<script type="text/javascript" language="javascript">
// ��ʼ��dialog��
hrm.common.initDialog('dlgViewtemplate', 520);

var editObj = null;				//������ tr
var cp = 'tabPage1';			//����ҳ�� integer  currentPage

// �����ʼ��б�
function redirectMailManager(){
    window.location="emailSearch.action";
}

// ��ʾģ�����ݣ�
function showDesc(templateNo,mailName){
	clearErr();
	//dwr��ȡģ����⡢����
	emailtemplate.getTemplateContent(mailName,callback);
	function callback(data){
		if(data == null){
			alert("��Ȩ�����������µ�¼������.");
			return;
		}
		$("#dlgViewtemplate").dialog('option', 'title', "Ԥ���ʼ�ģ��(" + mailName+ ")" .trim());
		hrm.common.openDialog('dlgViewtemplate');
		var html = "�ꡡ�⣺";
		html += data.title+"<br><br>";
		html += "�ڡ��ݣ�";
		html += data.body+"<br><br>";
		$('#viewTemplate').html(html);
	}
}


//���������Ϣ��
function clearErr(){
	if($('#errMsg').get(0)!=null){
		$('#errMsg').get(0).className="";
		$('#errMsg').get(0).innerHTML="";
	}
}
</script>
</body>
</html>
