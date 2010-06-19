<html>
<head>
	<style type="text/css">
		body,table{
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
	<#setting number_format="0.#">
	${MANAGER.empName}，您好！<br><br>
	${SENDER.empName}向您提交了新的请假申请：<br>
	<table>
		<tr>
			<td>员工编号：</td>
			<td>${LEAVE.lrEmpNo.empDistinctNo}</td>
			<td> 员工姓名：</td>
			<td>${LEAVE.lrEmpNo.empName}</td>
			<td> 所在部门：</td>
			<td>${LEAVE.lrEmpDept.departmentName}</td>
		</tr>
		<tr>
			<td>请假单编号：</td>
			<td>${LEAVE.lrNo?string.number}</td>
			<td>请假种类： </td>
			<td>${LEAVE.lrLtNo.ltName}</td>
			<td>当前状态：</td>
			<td>
				<#if LEAVE.lrStatus=2>已提交
				<#elseif LEAVE.lrStatus=15>HR备案
				<#elseif LEAVE.lrStatus=16>HR销假
				<#elseif LEAVE.lrStatus=21>已拒绝
				<#elseif LEAVE.lrStatus=22>已作废
				<#else>正在审批
				</#if>
			</td>
		</tr>
		<tr>
			<td>开始时间：</td>
			<td>
				<#if LEAVE.lrStartApm?exists>
				${LEAVE.lrStartDate?string("yyyy-MM-dd")}
				<#if LEAVE.lrStartApm=0>上午
				<#else>下午
				</#if>
				<#else>
				${LEAVE.lrStartDate?string("yyyy-MM-dd HH:mm")}
				</#if>
			</td>
			<td>结束时间：</td>
			<td>
				<#if LEAVE.lrEndApm?exists>
				${LEAVE.lrEndDate?string("yyyy-MM-dd")}
				<#if LEAVE.lrEndApm=0>上午
				<#else>下午
				</#if>
				<#else>
				${LEAVE.lrEndDate?string("yyyy-MM-dd HH:mm")}
				</#if>
			</td>
			<td>请假总时间：</td>
			<td>
				<#if LEAVE.lrStartApm?exists>
				${(LEAVE.lrTotalHours/8)?string.number}天
				<#else>
				${LEAVE.lrTotalHours}小时
				</#if>
			</td>
		</tr>
		<#if LEAVE.lrLtNo.ltSpecialCat=1 || LEAVE.lrLtNo.ltSpecialCat=5 || LEAVE.lrLtNo.ltSpecialCat=10>
		<tr>
			<td>假期可享用时间：</td>
			<td>
				<#if LEAVE.lrStartApm?exists>
					<#if LEAVE.useableDays?exists> ${LEAVE.useableDays}</#if>
				<#else>
					<#if LEAVE.useableHours?exists> ${LEAVE.useableHours}</#if>
				</#if>
			</td>
			<td>假期已使用时间：</td>
			<td>
				<#if LEAVE.lrStartApm?exists>
					<#if LEAVE.usedDays?exists> ${LEAVE.usedDays}</#if>
				<#else>
					<#if LEAVE.usedHours?exists> ${LEAVE.usedHours}</#if>
				</#if>
			</td>
			<td>截止年底可用：</td>
			<td>
				<#if LEAVE.lrStartApm?exists>
					<#if LEAVE.remainDays?exists> ${LEAVE.remainDays}</#if>
				<#else>
					<#if LEAVE.remainHours?exists> ${LEAVE.remainHours}</#if>
				</#if>
			</td>
		</tr>
		</#if>
		<tr>
			<td>请假理由：</td>
			<td colspan="5">${LEAVE.lrReason}</td>
		</tr>
	</table>
	<br/>
	请点击以下链接审批请假申请：<br>
	<a href="${APPROVEURL}">直接审批</a><br/>
	<a href="${URL}">登录系统</a><br/>
	${SYSNAME}<br/>
	${PHONE}
</body>
</html>