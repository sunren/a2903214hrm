<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/javascript" src="../resource/js/hrm/common.js"></script>
</head>
<body>
<s:hidden value="positionId"/>
<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList" style="width: 530px;">
   <tr>
	   <td align="right">
	       所属部门:
	   </td>
	   <td>
	     <s:property  id="" value="pb.pbDeptId.departmentName" />
	   </td>
	   <td align="right">
	      负责职位:
	   </td>
	   <td >
	      <SPAN id="isChargePos" />
	   </td>
   </tr>
   
   <tr>
	   <td align="right">
	       职位名称:
	   </td>
	   <td>
	     <s:property  value="pb.pbName" />
	   </td>
       <td align="right">
	      员工姓名:
	   </td>
	   <td>
	      <s:if test="position.positionEmpNo==null">
	          空缺
	      </s:if>
	      <s:else>
	      <s:property  value="position.positionEmpNo.empName" />
	      </s:else>
	   </td>
	  
   </tr>
   
   <tr>
	    <td align="right">
   	上级职位：
   	</td>
   	<td>
   		<s:if test="parentPb != null">
	      <s:property  value="parentPb.pbName" />(
			<s:if test="parentPosition.positionEmpNo==null">
	          空缺
	      </s:if>
	      <s:else>
	      <s:property  value="parentPosition.positionEmpNo.empName" />
	      </s:else>
			)
	    </s:if>
	    <s:else>
	    	空缺(空缺)
	    </s:else>
   	</td>
	  
	   <td align="right">
	     员工编号:
	   </td>
	   <td>
	      <s:if test="position.positionEmpNo==null">
	          空缺
	      </s:if>
	      <s:else>
	      <s:property  value="position.positionEmpNo.empDistinctNo" />
	      </s:else>
	   </td>
	   
   </tr>
</table>

</body>
<script type="text/javascript">
//设置是否负责职位的显示
var chargeVal='<s:property value='pb.pbInCharge'/>';
if(chargeVal==0){
   $('#isChargePos').html('不是');
}else if(chargeVal==1){
     $('#isChargePos').html('是');
}

</script>
</html>