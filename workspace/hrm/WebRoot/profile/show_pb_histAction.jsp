<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
</head>
<body>
<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
  <tr>
  <th>����</th>
  <th>����ʱ��</th>
  <th>������</th>
  <th>��ע</th>
  </tr>
   <s:if test="!pbHistActionList.isEmpty()">
    	<s:iterator value="pbHistActionList" status="index">
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
			<td align="center" colspan="8">������ʷ����!</td>
		</tr>
	</s:else>
</table>
<script type="text/javascript">

</script>
</body>
</html>