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
		'����','������',arial,verdana,sans-serif;*/
	}
	</style>
</head>
<body>
	<#setting number_format="0.#">
	${MANAGER.empName}�����ã�<br><br>
	${SENDER.empName}�����ύ���µ�������룺<br>
	<table>
		<tr>
			<td>Ա����ţ�</td>
			<td>${LEAVE.lrEmpNo.empDistinctNo}</td>
			<td> Ա��������</td>
			<td>${LEAVE.lrEmpNo.empName}</td>
			<td> ���ڲ��ţ�</td>
			<td>${LEAVE.lrEmpDept.departmentName}</td>
		</tr>
		<tr>
			<td>��ٵ���ţ�</td>
			<td>${LEAVE.lrNo?string.number}</td>
			<td>������ࣺ </td>
			<td>${LEAVE.lrLtNo.ltName}</td>
			<td>��ǰ״̬��</td>
			<td>
				<#if LEAVE.lrStatus=2>���ύ
				<#elseif LEAVE.lrStatus=15>HR����
				<#elseif LEAVE.lrStatus=16>HR����
				<#elseif LEAVE.lrStatus=21>�Ѿܾ�
				<#elseif LEAVE.lrStatus=22>������
				<#else>��������
				</#if>
			</td>
		</tr>
		<tr>
			<td>��ʼʱ�䣺</td>
			<td>
				<#if LEAVE.lrStartApm?exists>
				${LEAVE.lrStartDate?string("yyyy-MM-dd")}
				<#if LEAVE.lrStartApm=0>����
				<#else>����
				</#if>
				<#else>
				${LEAVE.lrStartDate?string("yyyy-MM-dd HH:mm")}
				</#if>
			</td>
			<td>����ʱ�䣺</td>
			<td>
				<#if LEAVE.lrEndApm?exists>
				${LEAVE.lrEndDate?string("yyyy-MM-dd")}
				<#if LEAVE.lrEndApm=0>����
				<#else>����
				</#if>
				<#else>
				${LEAVE.lrEndDate?string("yyyy-MM-dd HH:mm")}
				</#if>
			</td>
			<td>�����ʱ�䣺</td>
			<td>
				<#if LEAVE.lrStartApm?exists>
				${(LEAVE.lrTotalHours/8)?string.number}��
				<#else>
				${LEAVE.lrTotalHours}Сʱ
				</#if>
			</td>
		</tr>
		<#if LEAVE.lrLtNo.ltSpecialCat=1 || LEAVE.lrLtNo.ltSpecialCat=5 || LEAVE.lrLtNo.ltSpecialCat=10>
		<tr>
			<td>���ڿ�����ʱ�䣺</td>
			<td>
				<#if LEAVE.lrStartApm?exists>
					<#if LEAVE.useableDays?exists> ${LEAVE.useableDays}</#if>
				<#else>
					<#if LEAVE.useableHours?exists> ${LEAVE.useableHours}</#if>
				</#if>
			</td>
			<td>������ʹ��ʱ�䣺</td>
			<td>
				<#if LEAVE.lrStartApm?exists>
					<#if LEAVE.usedDays?exists> ${LEAVE.usedDays}</#if>
				<#else>
					<#if LEAVE.usedHours?exists> ${LEAVE.usedHours}</#if>
				</#if>
			</td>
			<td>��ֹ��׿��ã�</td>
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
			<td>������ɣ�</td>
			<td colspan="5">${LEAVE.lrReason}</td>
		</tr>
	</table>
	<br/>
	����������������������룺<br>
	<a href="${APPROVEURL}">ֱ������</a><br/>
	<a href="${URL}">��¼ϵͳ</a><br/>
	${SYSNAME}<br/>
	${PHONE}
</body>
</html>