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
		'����','������',arial,verdana,sans-serif;*/
	}
	</style>
</head>
<body>
	${MANAGER.empName}�����ã�<br><br>${SENDER.empName}�����ύ���µļӰ����룺<br>
	<table>
		<tr>
		<td>Ա����ţ�</td>
		<td>${OR.orEmpNo.empDistinctNo}</td>
		<td>Ա��������</td>
		<td>${OR.orEmpNo.empName}</td>
		<td> ���ڲ��ţ�</td>
		<td>${OR.orEmpDept.departmentName}</td>
		</tr>
		<tr>
			<td>�Ӱ൥��ţ�</td>
			<td>${OR.orNo?string.number} </td>
			<td>�Ӱ����ࣺ</td>
			<td>${OR.orOtNo.otName}</td>
			<td> ��ǰ״̬��</td>
			<td>
				<#if OR.orStatus=2>���ύ
				<#elseif OR.orStatus=15>HR����
				<#elseif OR.orStatus=16>HR����
				<#elseif OR.orStatus=21>�Ѿܾ�
				<#elseif OR.orStatus=22>������
				<#else>��������
				</#if>
			</td>
		</tr>
		<tr>
			<td>��ʼʱ�䣺</td>
			<td>${OR.orStartDate?string("yyyy-MM-dd HH:mm")}</td>
			<td>����ʱ�䣺</td>
			<td>${OR.orEndDate?string("yyyy-MM-dd HH:mm")}</td>
			<td>�Ӱ���ʱ�䣺</td>
			<td>${OR.orTotalHours?string.number} Сʱ</td>
		</tr>
		<tr>
			<td>�Ӱ����ɣ�</td>
			<td colspan=5>${OR.orReason} </td>
		</tr>
	</table>
	<br>
	�����������������Ӱ����룺<br><a href="${APPROVEURL}">ֱ������</a><br/>
	<a href="${URL}">��¼ϵͳ</a><br/>
	${SYSNAME}<br/>
	${PHONE}
</body>
</html>