<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
</head>
<body>
<table id="table_deptpbs" cellpadding="0" cellspacing="1" width="100%" class="gridtableList">
    <s:hidden id="currDeptId" name="dept.id"></s:hidden>
    <thead>
        <tr>
        	<th nowrap="nowrap" align="center">职位名称</th>
            <th nowrap="nowrap" align="center">职位描述</th>
            <th nowrap="nowrap" align="center">所在部门</th>
            <th nowrap="nowrap" align="center">上级职位</th>
            <th nowrap="nowrap" align="center">是否负责职位</th>
            <th nowrap="nowrap" align="center">编制设置</th>
            <th nowrap="nowrap" align="center">人员编制</th>
            <th nowrap="nowrap" align="center">职位状态</th>
        </tr>
    </thead>
    <tbody id="tbody_deptpbs">
        <s:iterator value="deptPBList">
            <tr>
                <td align="left"><s:property value="pbName" /></td>
                <td align="left"><s:property value="pbDesc" /></td>
                <td align="left"><s:property value="pbDeptId.departmentName" /></td>
                <td align="left"><s:property value="pbSupId.pbName" /></td>
                <td align="center"><s:if test="pbInCharge == 1">是</s:if><s:else>否</s:else></td>
                <td align="left"><s:if test="pbMaxExceed == 0">无编制</s:if><s:elseif test="pbMaxExceed == 1">有编制不允许超编</s:elseif><s:elseif test="pbMaxExceed == 2">有编制可以超编</s:elseif></td>
                <td align="center"><s:if test="pbMaxExceed==1 || pbMaxExceed==2"><s:property value="pbMaxEmployee" /></s:if><s:else>无编制</s:else></td>
                <td align="center"><s:if test="pbStatus == 1">启用</s:if><s:else>停用</s:else></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
</body>
</html>