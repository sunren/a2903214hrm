<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<title>Ԥ���屨��</title>
<script type="text/javascript">
	function confirmDelete(reportId){
		if(window.confirm("ȷ��Ҫɾ����")){
			document.getElementById('form').action="deleteReport.action?reportId="+reportId;
			document.getElementById('form').submit();
		}
		return ;
	}
</script>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'Ԥ���屨��'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<div id="basic" style="DISPLAY: block;clear : both">
	<form action="preList.action" method="post" id="form">
		<s:token/>
		<table width="100%" class="formtable">
			<tr>
				<td>�������ƣ�</td>
				<td><s:textfield name="preDef.reportPreName"/></td>
				<td>��������ģ�飺</td>
				<td>
					<s:select list="#{1:'Ա��ģ��',2:'н��ģ��',3:'��ѵģ��',4:'����ģ��',6:'��Ƹģ��',9:'ϵͳģ��'}" name="report.reportModule" emptyOption="true"></s:select>
				</td>
				<td rowspan="4">
					<input type="submit" value="�顡ѯ">&nbsp;&nbsp;
				    <input type="reset" value="�ء���" onclick="window.location='listAll.action'">
				</td>
			</tr>
		</table>
	</form>
	<p>&nbsp;</p>
	<input type="button" value="�½�Ԥ���屨��" onclick="window.location='uploadInit.action'"/>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<tr>
			<th NOWRAP>��������</th>
			<th NOWRAP>����ģ��</th>
			<th NOWRAP>��������</th>
			<th NOWRAP>��ʾģʽ</th>
			<th nowrap>ִ��URL</th>
			<th NOWRAP>��������</th>
			<th colspan="3">����</th>
		</tr>
		<s:if test="reports.isEmpty()">
		<tr>
			<td colspan="8" align="center">������ƥ��ı����¼��</td>
		</tr>
		</s:if>
		<s:else>
		<s:iterator value="reports">
		<tr>
			<td><s:property value="reportPreName"/></td>
			<td>
				<s:if test="reportPreModule==1">Ա��ģ��</s:if>
				<s:elseif test="reportPreModule==2">н��ģ��</s:elseif>
				<s:elseif test="reportPreModule==3">��ѵģ��</s:elseif>
				<s:elseif test="reportPreModule==4">����ģ��</s:elseif>
				<s:elseif test="reportPreModule==6">��Ƹģ��</s:elseif>
				<s:elseif test="reportPreModule==9">ϵͳģ��</s:elseif>
				<s:else>����ģ��</s:else>
			</td>
			<td><s:if test="reportPreType==0">365HRMԤ����</s:if>
				<s:elseif test="reportPreType==1">�ͻ�Ԥ����</s:elseif></td>
			<td>
				<s:if test="reportPreDisplayMode==0">ֻ��ʾ���</s:if>
				<s:elseif test="reportPreDisplayMode==1">ֻ��ʾͼ��</s:elseif>
				<s:elseif test="reportPreDisplayMode==2">���+ͼ��</s:elseif>
			</td>
			<td><s:if test="reportPreUrl==0">��׼URLִ��</s:if><s:else>����URLִ��</s:else></td>
			<td><s:property value="reportPreDescription"/></td>
			<td></td>
			<td><a href="#" onclick="confirmDelete('<s:property value='reportPreId'/>')"><img src="../resource/images/delete.gif" alt='ɾ��'  border='0'/></a></td>
			<td>
				<a href="paramsInit.action?reportId=<s:property value='reportId'/>"><img src="../resource/images/TaskReports.gif" alt='���б���'  border='0'/></a>
			</td>
		</tr>
		</s:iterator>
		</s:else>
		
	</table>
</div>
</body>
</html>