<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../resource/js/util.js"></script>
<script type="text/javascript">
	function getDays(type){
		alert(type);
		//MyAttendance.getDays(tyepe,function(){document.write(msg);});
	}
</script>
<title>系统提示</title>
</head>
<body>
<center>
	<TABLE id=table borderColor=#b8c0c4 cellSpacing=0 cellPadding=3 width="100%" class="detailtable">
		<tr>
			<td nowrap class="tablefield" width="15%">员工编号：</td>
			<td width="35%"><ww:property value="emp.empDistinctNo"/></td>
			<td nowrap class="tablefield" width="15%">员工姓名：</td>
			<td width="35%"><ww:property value="emp.empName"/></td>
		</tr>
		<tr>
			<td nowrap="nowrap" class="tablefield">所属部门：</td>
			<td><ww:property value="emp.empDeptNo.departmentName"/></td>
			<td nowrap class="tablefield">用工形式：</td>
			<td><ww:property value="emp.empType.emptypeName"/></td>
		</tr>
		<tr>
			<td nowrap class="tablefield">去年年假余额：</td>
			<td><ww:property value="lbLastYear"/></td>
			<td nowrap class="tablefield">剩余天数：</td>
			<td><ww:property value="lbrLastYear"/></td>
		</tr>
		<tr>
			<td nowrap class="tablefield">今年年假额度：</td>
			<td><ww:property value="lbCurrYear"/></td>
			<td nowrap class="tablefield">剩余天数：</td>
			<td><ww:property value="lbrCurrYear"/></td>
		</tr>
	 	<tr align='left'>
	 	  <td nowrap colspan=4 class="tablefield"><h4>今年剩余可用年假为  <ww:property value="%{lbrCurrYear+lbrLastYear}"/> 天：</h4></td>
		</tr>
		<!-- 
		<tr>
			<td nowrap class="tablefield">今年年假额度：</td>
			<td><ww:if test="leaveBalance.lbDaysOfYear!=null"><ww:property value="leaveBalance.lbDaysOfYear"/></ww:if><ww:else>0</ww:else>天</td>
			<ww:if test="leaveBalance.lbBalEndDate!=null">
				<td nowrap class="tablefield">去年年假余额：</td>
			    <td><ww:property value="leaveBalance.lbBalForward"/></td>
				<td nowrap class="tablefield">去年余额最晚使用日期：</td>
				<td><ww:property value="leaveBalance.lbBalEndDate"/></td>
			</ww:if>
			<ww:else>
				<td colspan="6">&nbsp;</td>
			</ww:else>
		</tr> -->
	</TABLE>
	<ww:if test="days!=null">
	<br>
		<table borderColor=#b8c0c4 cellSpacing=0 align="left" width="100%">
			<tr>
				<td align="left">
			<h4>今年已请假期</h4></td>
			</tr>
			<ww:iterator value="days" status="index">
				<tr>
					<td align="left"><ww:property value="key"/>:
					<ww:property value="value"/>天</td>
				</tr>
			</ww:iterator>
		</table>
	</ww:if>
</center>
<span align="left">
			<br>
			
	
</span>

</body>
</html>