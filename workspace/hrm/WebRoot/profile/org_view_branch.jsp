<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>��֯�ṹά��</title>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
</head>
<body>

<form action="viewEdit.action" id="viewEditBranch" method="post">

<table width="100%">
<tr><td align="center"><s:property value="titleStr"/></td></tr>
<tr><td align="center">
<table width="80%" border="0" cellspacing="0" cellpadding="0" class="gridtableList" align="center">
	<tr>
		<td width="20%" align="right">��˾���ƣ�</td>
		<td width="20%" align="left">
			<s:property value="dept.departmentName"/>
		</td>
		<td width="20%" align="right">��˾������</td>
		<td width="20%" align="left">
			<s:property value="dept.departmentDesc"/>
		</td>
	</tr>
	<tr>
		<td align="right">���˴���</td>
		<td align="left">
			<s:property value="dept.departmentPersonInlaw"/>
        </td>
		<td align="right">����ְλ**��</td>
		<td align="left">
			<s:property value="dept.departmentDesc"/>
		</td>
	</tr>
	<tr>
		<td align="right">ֱӪ��Χ��</td>
		<td align="left" colspan="3">
			<s:property value="dept.departmentBusinessDesc"/>
			&nbsp;
            <a>����</a>
			<s:elseif test="operate=='new' || operate=='edit'">
			          �ϴ�������<s:file id="dept.departmentBusinessAttach" name="dept.departmentBusinessAttach" tabindex="31" onchange="hrm.profile.checkAttachFile();" size="30" accept="text/GIF,text/jpg,text/jpeg"/>
			</s:elseif>
        </td>
	</tr>
	<tr>
	    <td align="right">������ҵ��</td>
	    <td align="left">
			<s:property value="dept.departmentCate"/>
	    </td>
	    <td align="right">״̬��</td>
		<td align="left">
			<s:if test="dept.departmentStatus == 1">����</s:if><s:else>ͣ��</s:else>
		</td>
	</tr>
	<tr>
	    <td align="right">��ע��</td>
	    <td align="left" colspan="3">
			<s:property value="dept.departmentMemo"/>
	    </td>
	</tr>
	
	<!-- ����Ϊ�����޸����ݣ��ں�̨���ܳ��� -->
	<tr>
		<td align="right">����ʱ�䣺</td>
		<td align="left"><s:property value="dept.departmentCreateTime"/></td>
		<td align="right">����ʱ�䣺</td>
		<td align="left"><s:property value="dept.departmentEndTime"/></td>
	</tr>
	<tr>
	    <td align="right">ֱ���¼���֯��</td>
	    <td align="left" colspan="3"><s:property value="dept.departmentMemo"/></td>
	</tr>
	<tr>
	    <td align="right">��Ա���ƣ�����/�ڱࣩ��</td>
	    <td align="left" colspan="3"><s:property value="orderNum"/>/<s:property value="actualNum"/></td>
	</tr>
</table>
</td></tr></table>
</form>
</body>
</html>
