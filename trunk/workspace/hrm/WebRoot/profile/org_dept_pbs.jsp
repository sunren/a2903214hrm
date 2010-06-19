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
        	<th nowrap="nowrap" align="center">ְλ����</th>
            <th nowrap="nowrap" align="center">ְλ����</th>
            <th nowrap="nowrap" align="center">���ڲ���</th>
            <th nowrap="nowrap" align="center">�ϼ�ְλ</th>
            <th nowrap="nowrap" align="center">�Ƿ���ְλ</th>
            <th nowrap="nowrap" align="center">��������</th>
            <th nowrap="nowrap" align="center">��Ա����</th>
            <th nowrap="nowrap" align="center">ְλ״̬</th>
        </tr>
    </thead>
    <tbody id="tbody_deptpbs">
        <s:iterator value="deptPBList">
            <tr>
                <td align="left"><s:property value="pbName" /></td>
                <td align="left"><s:property value="pbDesc" /></td>
                <td align="left"><s:property value="pbDeptId.departmentName" /></td>
                <td align="left"><s:property value="pbSupId.pbName" /></td>
                <td align="center"><s:if test="pbInCharge == 1">��</s:if><s:else>��</s:else></td>
                <td align="left"><s:if test="pbMaxExceed == 0">�ޱ���</s:if><s:elseif test="pbMaxExceed == 1">�б��Ʋ�������</s:elseif><s:elseif test="pbMaxExceed == 2">�б��ƿ��Գ���</s:elseif></td>
                <td align="center"><s:if test="pbMaxExceed==1 || pbMaxExceed==2"><s:property value="pbMaxEmployee" /></s:if><s:else>�ޱ���</s:else></td>
                <td align="center"><s:if test="pbStatus == 1">����</s:if><s:else>ͣ��</s:else></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
</body>
</html>