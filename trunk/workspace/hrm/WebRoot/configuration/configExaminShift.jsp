<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configExaminShift.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. 迟到次数、早退次数、缺勤小时数会在月底自动进行汇总</p>
		<p>2. 必须在班次提前刷卡和延后刷卡之间刷卡，否则刷卡记录无效</p>
		<p>3. 每日迟到、早退使用分钟数；其他均换算成小时；缺勤与请假也可以换算成天数</p>
	</div>
	<div class="contentdiv">
		<form id="examinShiftConfigForm" action="examinShiftUpdateConfig.action" name="examinShiftConfigForm" method="post">
			<s:token />
			<p>
				迟到次数：迟到在<s:textfield id="firstLateBegin"
						name="firstLateBegin" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>到 <s:textfield
						id="firstLateEnd" name="firstLateEnd" size="3" onkeyup="value=value.replace(/\D/g,''); changeSencondLateBegin();" maxlength="3"/>分钟内算一次；
				迟到在<s:textfield id="secondLateBegin"
						name="secondLateBegin" size="3" readonly="true" />到 <s:textfield
						id="secondLateEnd" name="secondLateEnd" size="3" onkeyup="value=value.replace(/\D/g,''); changeThirdLateBegin();" maxlength="3"/>分钟内算二次；
				迟到在<s:textfield id="thirdLateBegin"
						name="thirdLateBegin" size="3" readonly="true" />到 <s:textfield
						id="thirdLateEnd" name="thirdLateEnd" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>分钟内算三次。
			</p>
			<p>
				早退次数：早退在<s:textfield id="firstEarlyLeaveBegin"
						name="firstEarlyLeaveBegin" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>到 <s:textfield
						id="firstEarlyLeaveEnd" name="firstEarlyLeaveEnd" size="3" onkeyup="value=value.replace(/\D/g,''); changeSecondEarlyLeaveBegin();" maxlength="3"/>分钟内算一次；
				早退在<s:textfield id="secondEarlyLeaveBegin"
						name="secondEarlyLeaveBegin" size="3" readonly="true"/>到 <s:textfield
						id="secondEarlyLeaveEnd" name="secondEarlyLeaveEnd" size="3" onkeyup="value=value.replace(/\D/g,''); changeThirdEarlyLeaveBegin();" maxlength="3"/>分钟内算二次；
				早退在<s:textfield id="thirdEarlyLeaveBegin"
						name="thirdEarlyLeaveBegin" size="3" readonly="true"/>到 <s:textfield
						id="thirdEarlyLeaveEnd" name="thirdEarlyLeaveEnd" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>分钟内算三次。
			</p>
			<p>
				缺勤时间：迟到早退超过
					<s:textfield id="sys_examin_absent_minute"
						name="sys_examin_absent_minute" size="4" onkeyup="value=value.replace(/\D/g,'');" maxlength="4"/>
					分钟算缺勤。
			</p>
			<p>
				提前刷卡：上班最多提前
					<s:textfield id="sys_shift_start_minute"
						name="sys_shift_start_minute" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>
					分钟刷卡。
			</p>
			<p>
				延后刷卡：下班最晚延后
					<s:textfield id="sys_shift_end_minute" name="sys_shift_end_minute"
						size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>
					分钟刷卡。
			</p>
			<p>
				重复刷卡：在<s:textfield id="sys_shift_cardrepeat_minute"
						name="sys_shift_cardrepeat_minute" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>
					分钟内重复刷卡只保留第一笔刷卡记录。
			</p>
			<p>
				分钟折算小时：超过<s:textfield id="sys_shift_asHour"
						name="sys_shift_asHour" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3"/>分钟算1小时；
				超过<s:textfield id="sys_shift_asHalfHour"
						name="sys_shift_asHalfHour" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3" />分钟算半小时。</span>
			</p>
			<p>
				小时折算天数：超过一天应工作小时数的<s:textfield id="sys_absent_asDay"
						name="sys_absent_asDay" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3" />% 算一天；
				超过一天应工作小时数的<s:textfield id="sys_absent_asHalfDay"
						name="sys_absent_asHalfDay" size="3" onkeyup="value=value.replace(/\D/g,'');" maxlength="3" />% 算半天。 </span>
			</p>
			<p>
				<br>
				<input type="button" id="updateExaminShiftBt"
					name="updateExaminShiftBt" value=" 更 新 " onClick="return doExaminShiftCheck();" />
			</p>
	    </form>
	</div>
</div>
</body>
</html>