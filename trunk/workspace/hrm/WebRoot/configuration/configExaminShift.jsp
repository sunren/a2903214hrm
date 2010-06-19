<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configExaminShift.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. �ٵ����������˴�����ȱ��Сʱ�������µ��Զ����л���</p>
		<p>2. �����ڰ����ǰˢ�����Ӻ�ˢ��֮��ˢ��������ˢ����¼��Ч</p>
		<p>3. ÿ�ճٵ�������ʹ�÷������������������Сʱ��ȱ�������Ҳ���Ի��������</p>
	</div>
	<div class="contentdiv">
		<form id="examinShiftConfigForm" action="examinShiftUpdateConfig.action" name="examinShiftConfigForm" method="post">
			<s:token />
			<p>
				�ٵ��������ٵ���<s:textfield id="firstLateBegin"
						name="firstLateBegin" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>�� <s:textfield
						id="firstLateEnd" name="firstLateEnd" size="3" onkeyup="value=value.replace(/\D/g,''); changeSencondLateBegin();" maxlength="3"/>��������һ�Σ�
				�ٵ���<s:textfield id="secondLateBegin"
						name="secondLateBegin" size="3" readonly="true" />�� <s:textfield
						id="secondLateEnd" name="secondLateEnd" size="3" onkeyup="value=value.replace(/\D/g,''); changeThirdLateBegin();" maxlength="3"/>����������Σ�
				�ٵ���<s:textfield id="thirdLateBegin"
						name="thirdLateBegin" size="3" readonly="true" />�� <s:textfield
						id="thirdLateEnd" name="thirdLateEnd" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>�����������Ρ�
			</p>
			<p>
				���˴�����������<s:textfield id="firstEarlyLeaveBegin"
						name="firstEarlyLeaveBegin" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>�� <s:textfield
						id="firstEarlyLeaveEnd" name="firstEarlyLeaveEnd" size="3" onkeyup="value=value.replace(/\D/g,''); changeSecondEarlyLeaveBegin();" maxlength="3"/>��������һ�Σ�
				������<s:textfield id="secondEarlyLeaveBegin"
						name="secondEarlyLeaveBegin" size="3" readonly="true"/>�� <s:textfield
						id="secondEarlyLeaveEnd" name="secondEarlyLeaveEnd" size="3" onkeyup="value=value.replace(/\D/g,''); changeThirdEarlyLeaveBegin();" maxlength="3"/>����������Σ�
				������<s:textfield id="thirdEarlyLeaveBegin"
						name="thirdEarlyLeaveBegin" size="3" readonly="true"/>�� <s:textfield
						id="thirdEarlyLeaveEnd" name="thirdEarlyLeaveEnd" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>�����������Ρ�
			</p>
			<p>
				ȱ��ʱ�䣺�ٵ����˳���
					<s:textfield id="sys_examin_absent_minute"
						name="sys_examin_absent_minute" size="4" onkeyup="value=value.replace(/\D/g,'');" maxlength="4"/>
					������ȱ�ڡ�
			</p>
			<p>
				��ǰˢ�����ϰ������ǰ
					<s:textfield id="sys_shift_start_minute"
						name="sys_shift_start_minute" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>
					����ˢ����
			</p>
			<p>
				�Ӻ�ˢ�����°������Ӻ�
					<s:textfield id="sys_shift_end_minute" name="sys_shift_end_minute"
						size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>
					����ˢ����
			</p>
			<p>
				�ظ�ˢ������<s:textfield id="sys_shift_cardrepeat_minute"
						name="sys_shift_cardrepeat_minute" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>
					�������ظ�ˢ��ֻ������һ��ˢ����¼��
			</p>
			<p>
				��������Сʱ������<s:textfield id="sys_shift_asHour"
						name="sys_shift_asHour" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>������1Сʱ��
				����<s:textfield id="sys_shift_asHalfHour"
						name="sys_shift_asHalfHour" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3" />�������Сʱ��</span>
			</p>
			<p>
				Сʱ��������������һ��Ӧ����Сʱ����<s:textfield id="sys_absent_asDay"
						name="sys_absent_asDay" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3" />% ��һ�죻
				����һ��Ӧ����Сʱ����<s:textfield id="sys_absent_asHalfDay"
						name="sys_absent_asHalfDay" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3" />% ����졣 </span>
			</p>
			<p>
				<br>
				<input type="button" id="updateExaminShiftBt"
					name="updateExaminShiftBt" value=" �� �� " onClick="return doExaminShiftCheck();" />
			</p>
	    </form>
	</div>
</div>
</body>
</html>