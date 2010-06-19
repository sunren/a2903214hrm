<html>
<head>
	<style type="text/css">
	body,table {
		color: #333;
		BORDER: 0px;
		LINE-HEIGHT: 150%;
		LINE-HEIGHT: 150%;
		TEXT-ALIGN: left;
		background-color: #F9F9F5;
		font-family: Courier New, Arial, Verdana, Helvetica, sans-serif;
		font-size: 12px;
		empty-cells: show;
		/*FONT-FAMILY: 'Lucida Grande','Lucida Sans Unicode',
		'宋体','新宋体',arial,verdana,sans-serif;*/
	}
	</style>
</head>
<body>
	${MANAGER.empName}，您好！<br><br>${SENDER.empName}向您提交了新的加班申请：<br>
	<table>
		<tr>
		<td>员工编号：</td>
		<td>${OR.orEmpNo.empDistinctNo}</td>
		<td>员工姓名：</td>
		<td>${OR.orEmpNo.empName}</td>
		<td> 所在部门：</td>
		<td>${OR.orEmpDept.departmentName}</td>
		</tr>
		<tr>
			<td>加班单编号：</td>
			<td>${OR.orNo?string.number} </td>
			<td>加班种类：</td>
			<td>${OR.orOtNo.otName}</td>
			<td> 当前状态：</td>
			<td>
				<#if OR.orStatus=2>已提交
				<#elseif OR.orStatus=15>HR备案
				<#elseif OR.orStatus=16>HR销假
				<#elseif OR.orStatus=21>已拒绝
				<#elseif OR.orStatus=22>已作废
				<#else>正在审批
				</#if>
			</td>
		</tr>
		<tr>
			<td>开始时间：</td>
			<td>${OR.orStartDate?string("yyyy-MM-dd HH:mm")}</td>
			<td>结束时间：</td>
			<td>${OR.orEndDate?string("yyyy-MM-dd HH:mm")}</td>
			<td>加班总时间：</td>
			<td>${OR.orTotalHours?string.number} 小时</td>
		</tr>
		<tr>
			<td>加班理由：</td>
			<td colspan=5>${OR.orReason} </td>
		</tr>
	</table>
	<br>
	请点击以下链接审批加班申请：<br><a href="${APPROVEURL}">直接审批</a><br/>
	<a href="${URL}">登录系统</a><br/>
	${SYSNAME}<br/>
	${PHONE}
</body>
</html>