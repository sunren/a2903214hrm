<%@ page language="java" contentType="text/html; charset=GBK"	pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configExamin.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. 缺省情况下，请假加班审批，考勤员排班，经理查看下属每日考勤，均按“组织结构图”进行</p>
		<p>2. 缺省经理和HR可以代人提交请假加班申请，并在审批时修改申请，HR允许执行销假和加班确认操作</p>
		<p>3. 每月考勤数据汇总开始日期限制：如果选择上月，只允许16日-28日；如果是本月，只允许1日-15日；缺省为本月1日</p>
		<p>4. 不开放刷卡功能，考勤只能按天计算；开放刷卡功能，则请假可以按小时计算，每月考勤汇总可以按小时（或按天）统计；缺省一天等于8小时</p>
	</div>
	<div class="contentdiv">
		<form id="examinForm" action="examinUpdateConfig.action" method="post">
			<s:token />
			<p>
				创建非本人请假加班记录：
				<s:hidden id="sys_examin_create_level" name="sys_examin_create_level" ></s:hidden>
                <s:if test="sys_examin_create_level==0">
					<input type="radio" class="radio" id="submitSub" name="submitSub" onclick="changeSelectSub()" value="1"></input>允许
					<input type="radio" class="radio" id="submitSub" name="submitSub" onclick="changeSelectSub()" value="0" checked></input>禁止
				</s:if>
				<s:else>
					<input type="radio" class="radio" id="submitSub" name="submitSub" onclick="changeSelectSub()" value="1" checked></input>允许
					<input type="radio" class="radio" id="submitSub" name="submitSub" onclick="changeSelectSub()" value="0"></input>禁止
				</s:else>
			</p>
			<p>
				审批时修改请假加班记录：
				<s:hidden id="sys_examin_update_level" name="sys_examin_update_level"></s:hidden>
                <s:if test="sys_examin_update_level==0">
				    <input type="radio" class="radio" id="sys_examin_update_levelRadio" name="sys_examin_update_levelRadio" onClick="changeUpdateLevelRadio()" value="1"></input>允许
				    <input type="radio" class="radio" id="sys_examin_update_levelRadio" name="sys_examin_update_levelRadio" onClick="changeUpdateLevelRadio()" value="0" checked></input>禁止
				</s:if>
				<s:else>
				    <input type="radio" class="radio" id="sys_examin_update_levelRadio" name="sys_examin_update_levelRadio" onClick="changeUpdateLevelRadio()" value="1" checked></input>允许
				    <input type="radio" class="radio" id="sys_examin_update_levelRadio" name="sys_examin_update_levelRadio" onClick="changeUpdateLevelRadio()" value="0"></input>禁止
				</s:else>
			</p>
			<p>
				请假是否允许HR销假:
				<s:hidden id="sys_examin_lr_confirm" name="sys_examin_lr_confirm"></s:hidden>
		        <s:if test="sys_examin_lr_confirm==0">
		            <input type="radio" class="radio" id="sys_examin_lr_confirmRadio" name="sys_examin_lr_confirmRadio" onClick="changeLrConfirmRadio()" value="0" checked/>不允许
		            <input type="radio" class="radio" id="sys_examin_lr_confirmRadio" name="sys_examin_lr_confirmRadio" onClick="changeLrConfirmRadio()" value="1" />允许
		        </s:if>
		        <s:else>
		            <input type="radio" class="radio" id="sys_examin_lr_confirmRadio" name="sys_examin_lr_confirmRadio" onClick="changeLrConfirmRadio()" value="0" />不允许
		            <input type="radio" class="radio" id="sys_examin_lr_confirmRadio" name="sys_examin_lr_confirmRadio" onClick="changeLrConfirmRadio()" value="1" checked/>允许
		        </s:else>
			</p>
			<p>
				加班是否允许HR确认:
				<s:hidden id="sys_examin_ot_confirm" name="sys_examin_ot_confirm"></s:hidden>
		        <s:if test="sys_examin_ot_confirm==0">
		            <input type="radio" class="radio" id="sys_examin_ot_confirmRadio" name="sys_examin_ot_confirmRadio" onClick="changeOtConfirmRadio()" value="0" checked/>不允许
		            <input type="radio" class="radio" id="sys_examin_ot_confirmRadio" name="sys_examin_ot_confirmRadio" onClick="changeOtConfirmRadio()" value="1" />允许
		        </s:if>
		        <s:else>
		            <input type="radio" class="radio" id="sys_examin_ot_confirmRadio" name="sys_examin_ot_confirmRadio" onClick="changeOtConfirmRadio()" value="0" />不允许
		            <input type="radio" class="radio" id="sys_examin_ot_confirmRadio" name="sys_examin_ot_confirmRadio" onClick="changeOtConfirmRadio()" value="1" checked/>允许
		        </s:else>
			</p>
			<p>
				每月考勤汇总起始日期:
			    <s:select name="beginMonth" list="#{1:'本月',0:'上月'}" id="beginMonth"></s:select>
			    <s:textfield id="beginDay" maxlength="2" size="3" name="beginDay" onkeyup="value=value.replace(/\D/g,'')" ></s:textfield>日
			</p>
			<p>
				是否开放刷卡功能:
		        <s:hidden id="sys_examin_shift_enable" name="sys_examin_shift_enable"></s:hidden>
		        <s:if test="sys_examin_shift_enable==0">
		            <input type="radio" class="radio"  name="shiftOpen" onClick="changeShiftRadio()" value="0" checked/>不开放
		            <input type="radio" class="radio"  name="shiftOpen" onClick="changeShiftRadio()" value="1" />开放
		        </s:if>
		        <s:else>
		            <input type="radio" class="radio"  name="shiftOpen" onClick="changeShiftRadio()" value="0" />不开放
		            <input type="radio" class="radio"  name="shiftOpen" onClick="changeShiftRadio()" value="1" checked/>开放
		        </s:else>
			</p>
			<p>
				排班信息导入是否覆盖:
		        <s:hidden id="sys_examin_shiftimport_override" name="sys_examin_shiftimport_override"></s:hidden>
		        <s:if test="sys_examin_shiftimport_override==0">
		            <input type="radio" class="radio"  name="shiftImportOpen" onClick="changeShiftImportRadio()" value="0" checked/>否
		            <input type="radio" class="radio"  name="shiftImportOpen" onClick="changeShiftImportRadio()" value="1" />是
		        </s:if>
		        <s:else>
		            <input type="radio" class="radio"  name="shiftImportOpen" onClick="changeShiftImportRadio()" value="0" />否
		            <input type="radio" class="radio"  name="shiftImportOpen" onClick="changeShiftImportRadio()" value="1" checked/>是
		        </s:else>
			</p>
			<p>
				<s:hidden id="leaveMode" name="leaveMode"></s:hidden>
			           考勤每月汇总计算方式：按
			    <s:select list="#{'day':'天','hour':'小时'}"  id="templeaveMode" name="templeaveMode" value="%{leaveMode}" onchange="changeLeaveMode();" ></s:select>
			             计算，一天等于<s:textfield id="hoursPerDay" name="hoursPerDay" size="3" maxlength="2" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>小时
			</p>
			<p>
				考勤请假模式：<s:select id="sys_examin_leave_type" name="sys_examin_leave_type" list="#{'0':'混合模式','1':'按小时','2':'按天'}" emptyOption="false"></s:select>
			</p>
			<p>
				<br>
			    <input id="updateExaminBt" type="button" onclick="updateExaminConfirm();" value=" 更 新 " />
			</p>
		</form>
	</div>
</div>
<script type="text/javascript">
	changeShiftRadio();
</script>
</body>
</html>
				
			

