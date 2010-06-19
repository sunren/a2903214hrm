<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />	
<style type="text/css">
	body{margin:0;padding:0;text-align:center;}
	td{white-space:nowrap;}
	th{white-space:nowrap}
</style>
<SCRIPT language="javascript"> 
　　function printit() 
　　{ 
	　　if (confirm('确定打印吗？')) { 
		document.getElementById('button_print').style.display ="none";
		document.getElementById('button_close').style.display="none";
		window.print();
	　　} 
		
　　} 
　　</SCRIPT>
<style type="text/css" media='print'>
.noprint{display : none }
</style>
<title>职位说明书</title> 
</head>
<body><!-- 
<object ID="WebBrowser1" WIDTH="0" HEIGHT="0" 
CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"> 
</object> -->
<!-- 
<input onclick="WebBrowser1.ExecWB(7,1)" type="button" value="打印预览"/> -->
<center>
<table class="gridtableList" width="680">
  <tr>
    <td colspan="8" nowrap="nowrap">
      <div align="center"><h3>
		 职位说明书
		 &nbsp;&nbsp;&nbsp;
	      <INPUT onclick="javascript:printit()" type="button" value="打 印" name="button_print" id="button_print"/>
		  <input onclick="javascript:window.close();" type="button" value="关 闭" id="button_close"/>
	  </h3></div>
    </td>
  </tr>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">基本信息</div></th>
  </tr>
  <tr>
    <td width="10%">职位名称：</td>
    <td width="15%"><s:property value="pb.pbName"/></td>
    <td width="10%">所属部门：</td>
    <td width="15%"><s:property value="pb.pbDeptId.departmentName"/></td>
    <td width="10%">职务类别：</td>
    <td width="15%"><s:property value=""/></td>
  </tr>
  <tr>
    <td width="10%">上级职位名称：</td>
    <td width="15%"><s:property value="pb.pbSupId.pbName"/></td>
    <td width="10%">是否负责职位：</td>
    <td width="15%">
				    <s:if test="pb.pbInCharge==1">
				     是
				    </s:if>
				    <s:else>
				     否
				    </s:else>
    </td>
    <s:if test="exceedConfig.equals(\"0\")">
    </s:if>
    <s:else>
    <td width="10%">编制人数：</td>
    <td width="15%"><s:property value="pb.pbMaxEmployee"/></td>
    </s:else>
  </tr>
  <tr>
    <td width="10%">编制日期：</td>
    <td width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日</td>
    <td width="10%">编制人：</td>
    <td width="15%"></td>
    <td width="10%">批准人：</td>
    <td width="15%"></td>
  </tr>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">职位描述</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		        <tr><td>内部工作关系：<br/><br/></td></tr>
		        <tr><td>外部工作关系：<br/><br/></td></tr>
		        <tr><td>职位目的：<br/><br/></td></tr>
		</table>
	</td>
  </tr>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">职位职责</div></th>
  </tr>
  <s:if test="pbRespList!=null&&pbRespList.size>0">
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		       <!--  
  		        <tr align="center" >
			    <td width="20%" nowrap="nowrap">职责名称</td>
			    <td width="65%" nowrap="nowrap">职责描述</td>
			    <td width="10%" nowrap="nowrap">职责权重</td>
		        </tr>
		        -->
  			<s:iterator value="pbRespList">
				<tr>
					<td width="80%"><s:property value="ourName" /></td>
				    <td width="20%"><s:property value="ourRate" /></td>
				</tr>
			</s:iterator>
  		</table>
  	</td>
  </tr>
  </s:if>
  <s:else>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		        <tr align="center">
			    <td nowrap="nowrap">无记录</td>
		        </tr>
  		</table>
  	</td>
  </tr>
  </s:else>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">任职资格</div></th>
  </tr>
  <s:if test="pbQualifyList!=null && pbQualifyList.size>0">
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		       <!-- 
  		        <tr align="center" >
			    <td width="20%" nowrap="nowrap">资格名称</td>
			    <td width="65%" nowrap="nowrap">资格描述</td>
		        </tr>
		         -->
  			<s:iterator value="pbQualifyList">
				<tr>
					<td width="20%"><s:property value="ouqName" /></td>
				    <td width="65%"><s:property value="ouqDesc" /></td>
				</tr>
			</s:iterator>
  		</table>
  	</td>
  </tr>
  </s:if>
  <s:else>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		        <tr align="center">
			    <td nowrap="nowrap">无记录</td>
		        </tr>
  		</table>
  	</td>
  </tr>
  </s:else>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">考核标准</div></th>
  </tr>
  <s:if test="pbPerfcrList!=null && pbPerfcrList.size>0">
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  			<s:iterator value="pbPosPerfcrList">
				<tr>
					<td width="80%"><s:property value="oupName" /></td>
				    <td width="20%"><s:property value="oupRate" /></td>
				</tr>
			</s:iterator>
  		</table>
  	</td>
  </tr>
  </s:if>
  <s:else>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		        <tr align="center">
			    <td nowrap="nowrap">无记录</td>
		        </tr>
  		</table>
  	</td>
  </tr>
  </s:else>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">工作环境</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		        <tr><td>工作地点：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="pb.pbWorkAddress"/></td></tr>
		        <tr><td>工作时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="pb.pbWorkTime"/></td></tr>
		        <tr><td>使用工具、设备：&nbsp;<s:property value="pb.pbWorkTool"/></td></tr>
		</table>
	</td>
  </tr>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">职位关系</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		        <tr><td>可直接升迁的职位：</td></tr>
		        <tr><td>可相互转换的职位：</td></tr>
		        <tr><td>可升迁至此的职位：</td></tr>
		</table>
	</td>
  </tr>
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">现职人员</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		        <tr><td>&nbsp;&nbsp;&nbsp;<s:property value="empNameListString"/></td></tr>
		</table>
	</td>
  </tr>
</table>
</center>
</body>
</html>