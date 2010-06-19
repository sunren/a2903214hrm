<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>组织结构维护</title>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
</head>
<body>

<form action="viewEdit.action" id="viewEditDept" method="post">
<s:hidden id="dept.id" name="dept.id"/>
<s:hidden id="operate" name="operate"/>
<s:hidden id="operateOver" name="operateOver"/>
<br>
<table width="100%">
<tr><td align="center"><s:property value="titleStr"/></td></tr>
<tr><td align="center">
<table width="80%" border="0" cellspacing="0" cellpadding="0" class="gridtableList" align="center">
	<tr>
		<td align="right" width="20%">部门名称：</td>
		<td align="left" width="20%">
			<s:property value="dept.departmentName"/>
			&nbsp;
		</td>
		<td align="right" width="20%">部门描述：</td>
		<td align="left" width="20%">
			<s:property value="dept.departmentDesc"/>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td align="right">负责人：</td>
		<td align="left">
			<s:property value="departmentDesc"/>
			&nbsp;
		</td>
		<td align="right">负责人职位：</td>
		<td align="left">

			&nbsp;
		</td>
	</tr>
	<tr>
		<td align="right">部门职责：</td>
		<td align="left" colspan="3">
			<s:property value="departmentBusinessDesc"/>
			&nbsp;
			<a>附件</a>
			<s:elseif test="operate=='new' || operate=='edit'">
			          上传附件：<s:file id="dept.departmentBusinessAttach" name="dept.departmentBusinessAttach" tabindex="31" onchange="hrm.profile.checkAttachFile();" size="30" accept="text/GIF,text/jpg,text/jpeg"/>
			</s:elseif>
        </td>
	</tr>
	<tr>
	    <td align="right">状态：</td>
		<td align="left" colspan="3">
			<s:if test="dept.departmentStatus == 1">启用</s:if><s:else>停用</s:else>
		</td>
	</tr>
	<tr>
	    <td align="right">备注：</td>
	    <td align="left" colspan="3">
			<s:property value="dept.departmentMemo"/>
			&nbsp;
	    </td>
	</tr>
	
	<!-- 以下为不可修改内容，在后台汇总出来 -->
	<tr>
		<td align="right">创建时间：</td>
		<td align="left"><s:property value="dept.departmentCreateTime"/>&nbsp;</td>
		<td align="right">结束时间：</td>
		<td align="left"><s:property value="dept.departmentEndTime"/>&nbsp;</td>
	</tr>
	<tr>
	    <td align="right">直属上级组织：</td>
	    <td align="left" colspan="3"><s:property value="dept.departmentMemo"/>&nbsp;</td>
	</tr>
	<tr>
	    <td align="right">直属下级组织：</td>
	    <td align="left" colspan="3"><s:property value="dept.departmentMemo"/>&nbsp;</td>
	</tr>
	<tr>
	    <td align="right">人员编制（定编/在编）：</td>
	    <td align="left" colspan="3"><s:property value="orderNum"/>/<s:property value="actualNum"/></td>
	</tr>
</table>
</td></tr></table>
</form>
</body>
</html>
