<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<title>��������</title>
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
	<s:param name="pagetitle" value="'��������'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<div id="basic" style="DISPLAY: block;clear : both">
	<form action="listAll.action" method="post" id="form">
		<s:token/>
		<table width="100%" class="formtable">
			<tr>
				<td>�������ƣ�</td>
				<td><s:textfield name="report.reportName"/></td>
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
	<ty:auth auths="701,2 or 701,3">
	<input type="button" value="�½����Ʊ���" onclick="window.location='createInit.action'"/>
	</ty:auth>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<tr>
			<th NOWRAP>��������</th>
			<th NOWRAP>����ģ��</th>
			<th NOWRAP>����ʱ��</th>
			<th NOWRAP>��ʾģʽ</th>
			<th NOWRAP>��������</th>
			<th colspan="3">����</th>
		</tr>
		<s:if test="reportList.isEmpty()">
		<tr>
			<td colspan="8" align="center">������ƥ��ı����¼��</td>
		</tr>
		</s:if>
		<s:else>
		<s:iterator value="reportList">
		<tr>
			<td><s:property value="reportName"/></td>
			<td>
				<s:if test="reportModule==1">Ա��ģ��</s:if>
				<s:elseif test="reportModule==2">н��ģ��</s:elseif>
				<s:elseif test="reportModule==3">��ѵģ��</s:elseif>
				<s:elseif test="reportModule==4">����ģ��</s:elseif>
				<s:elseif test="reportModule==6">��Ƹģ��</s:elseif>
				<s:elseif test="reportModule==9">ϵͳģ��</s:elseif>
				<s:else>����ģ��</s:else>
			</td>
			<td><s:date name="reportCreateTime"/></td>
			<td>
				<s:if test="reportDisplayMode==0">ֻ��ʾ���</s:if>
				<s:elseif test="reportDisplayMode==1">ֻ��ʾͼ��</s:elseif>
				<s:elseif test="2">���+ͼ��</s:elseif>
			</td>
			<td><s:property value="reportDescription"/></td>
			<td>
				<a href="updateReportInit.action?reportId=<s:property value='reportId'/>">
				<img src="../resource/images/edit.gif" alt='�޸�'  border='0'/></a> 
			</td>
			<td>
				<ty:auth auths="701,2 or 701,3">
					<a href="#" onclick="confirmDelete('<s:property value='reportId'/>')"><img src="../resource/images/delete.gif" alt='ɾ��'  border='0'/></a></td>
				</ty:auth>
			<td>
				<ty:auth auths="701,1 or 701,2 or 701,3">
					<a href="paramsInit.action?reportId=<s:property value='reportId'/>"><img src="../resource/images/ForecastWorksheet.gif" alt='���б���'  border='0'/></a>
				</ty:auth>
			</td>
		</tr>
		</s:iterator>
		</s:else>
		
	</table>
</div>
</body>
</html>