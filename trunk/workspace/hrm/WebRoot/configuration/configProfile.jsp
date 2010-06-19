<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configProfile.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. 缺省情况下，经理对下属员工的查询方式，均按“组织结构图”进行</p>
		<p>2. 系统缺省合同到期、试用期到期、生日到期均在45天内给予提醒</p>
		<p>3. 只允许填写1-31，缺省为15日，表示员工15日或15日之前入职，当月算一个月工龄</p>
	</div>
	<div class="contentdiv">
	<form id="otherProfileForm" action="configuration/profileUpdateConfig.action" name="otherProfileForm" method="post">
		<s:token />
		<p>合同到期<s:textfield name="sys_contract_expire_remind" size="4" 
				maxlength="4" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>天之内给予提醒
		</p>
		<p>
			试用到期<s:textfield name="sys_trial_expire_remind" size="4" maxlength="4"
				onkeyup="value=value.replace(/\D/g,'')"></s:textfield>天之内给予提醒
		</p>
		<p>
			生日到期<s:textfield name="sys_birthday_remind" size="4" maxlength="4" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>天之内给予提醒
		</p>
		<p>
			工龄计算配置参数(按月计算)：
			<s:textfield name="sys_salary_joinyear_calc" id="sys_salary_joinyear_calc"
				size="4" maxlength="2" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>
		</p>
		<p>
		            组织结构变动是否保存历史：
		    <s:select id="sys_profile_save_history" name="sys_profile_save_history" list="#{'0':'不保存','1':'保存'}" emptyOption="false"></s:select>
		</p>
		<p>
		            组织结构编制设置：
		    <s:select id="sys_position_max_exceed" name="sys_position_max_exceed" list="#{'0':'没有编制','1':'有编制且不能超编','2':'有编制可以超编'}" emptyOption="false"></s:select>
		</p>
		<br />
		<p>
			<input type="button" id="updateProfileBt" name="submitProfile" value=" 更 新 " onClick="doProfileUpdate()" />
		</p>
		<s:hidden id="sys_profile_sub_type" name="sys_profile_sub_type"></s:hidden>
	</form>
	</div>
</div>
</body>
</html>