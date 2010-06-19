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
<table id="table_depthist" cellpadding="0" cellspacing="1" width="100%" class="gridtableList">
   <s:hidden id="currDeptId" name="dept.id"></s:hidden>
   <tr>
      <th>操作</th>
      <th>操作时间</th>
      <th>操作者</th>
      <th>备注</th>
   </tr>
   <s:if test="!deptLogList.isEmpty()">
    	<s:iterator value="deptLogList" status="index">
    		<tr>
				<td align="center" width="20%"><s:property value="slAction" /></td>  
				<td align="center" width="20%"><s:date  name="slChangeTime" format="yyyy-MM-dd hh:mm:ss"/></td>
			    <td align="center" width="20%"><s:property value="slChangeEmpno.empName" /></td>
			    <td align="center" width="40%"><s:property value="slComments" /></td>
    		</tr>
    	</s:iterator>
    </s:if>
	<s:else>
		<tr>
			<td align="center" colspan="8">不存在历史动作!</td>
		</tr>
	</s:else>
</table>
</body>
</html>