<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configProfile.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. ȱʡ����£����������Ա���Ĳ�ѯ��ʽ����������֯�ṹͼ������</p>
		<p>2. ϵͳȱʡ��ͬ���ڡ������ڵ��ڡ����յ��ھ���45���ڸ�������</p>
		<p>3. ֻ������д1-31��ȱʡΪ15�գ���ʾԱ��15�ջ�15��֮ǰ��ְ��������һ���¹���</p>
	</div>
	<div class="contentdiv">
	<form id="otherProfileForm" action="configuration/profileUpdateConfig.action" name="otherProfileForm" method="post">
		<s:token />
		<p>��ͬ����<s:textfield name="sys_contract_expire_remind" size="4" 
				maxlength="4" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>��֮�ڸ�������
		</p>
		<p>
			���õ���<s:textfield name="sys_trial_expire_remind" size="4" maxlength="4"
				onkeyup="value=value.replace(/\D/g,'')"></s:textfield>��֮�ڸ�������
		</p>
		<p>
			���յ���<s:textfield name="sys_birthday_remind" size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>��֮�ڸ�������
		</p>
		<p>
			����������ò���(���¼���)��
			<s:textfield name="sys_salary_joinyear_calc" id="sys_salary_joinyear_calc"
				size="4" maxlength="2" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>
		</p>
		<p>
		            ��֯�ṹ�䶯�Ƿ񱣴���ʷ��
		    <s:select id="sys_profile_save_history" name="sys_profile_save_history" list="#{'0':'������','1':'����'}" emptyOption="false"></s:select>
		</p>
		<p>
		            ��֯�ṹ�������ã�
		    <s:select id="sys_position_max_exceed" name="sys_position_max_exceed" list="#{'0':'û�б���','1':'�б����Ҳ��ܳ���','2':'�б��ƿ��Գ���'}" emptyOption="false"></s:select>
		</p>
		<br />
		<p>
			<input type="button" id="updateProfileBt" name="submitProfile" value=" �� �� " onClick="doProfileUpdate()" />
		</p>
		<s:hidden id="sys_profile_sub_type" name="sys_profile_sub_type"></s:hidden>
	</form>
	</div>
</div>
</body>
</html>