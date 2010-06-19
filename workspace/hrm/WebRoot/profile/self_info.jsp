<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<!--
 =========================================================
' File: employee_update.jsp
' Version:1.1.0 standard version
' Date: 2007-2-2
' Script Written by hr.com
'=========================================================
' Copyright   2007 hr.com. All rights reserved.
' Web: http://www.hr.com
' Email: admin@hr.com
'=======================================================
 -->
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<!-- DWR文件引用 -->
<script type="text/javascript" src="../dwr/interface/my.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpAddUpdate.js"></script>
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<title>修改员工信息</title>
</head>
<body onload="display_practice_yearOrMonth();">
<div id="profile_update">
<s:component template="bodyhead"/>
<s:form name="updateEmployee" action="ajaxSelf" namespace="/profile" method="POST" enctype="multipart/form-data">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		   	<s:textfield id="empNo" label="员工编号" name="emp.empDistinctNo" size="20" required="true" readonly="true" maxlength="64"/>
			<td align="right">邮箱地址:</td>
			<td>
				<s:textfield name="emp.empEmail" size="20"  maxlength="64" readonly="true" onkeyup="notchinese(this);"/>
				<s:if test="emp.empEmail!=null&&emp.empEmail!=''">
               		<s:if test="#session.userinfo.employee.id!=emp.id">
               		<a class="listViewTdLinkS1" href="mailto:<s:property value='emp.empEmail'/>"><img alt="发送邮件" src="../resource/images/Emails.gif"/></a>
               		</s:if>
               	</s:if>
               </td>
			<td rowspan="5">
			     <img border="1" id="showPicture" style="border: 1px darkslategray outset;" name="showPicture" src="../servlet/showImage?style=head&fileName=<s:property value="emp.empImage"/>" align="left" width="80" height="85"/>
			</td>
		</tr>
		<tr>
			<s:textfield label="员工姓名" id="empName" name="emp.empName" size="20" readonly="true" required="true" maxlength="64"/>
			<td align="right">即时通讯:</td>
            <td>  
				<s:textfield name="connectionNo" id="idConnectionNo" required="true" size="20" maxlength="64" tabindex="25" onkeyup="hrm.common.notChinese(event,this);" readonly="true" onblur="hrm.profile.checkConnection('idconnectionType');"/>
				<s:select name="connectionType" id="idconnectionType" onblur="hrm.profile.checkConnection('idconnectionType');" tabindex="26" value="connectionType" disabled="true" required="true" list="#{0:'MSN', 1:'QQ', 2:'旺旺'}"/>
			</td>
            <td colspan="2"></td>
            <td colspan="2"></td>
		</tr>
		<tr>
			<td align="right">出生日期<span class="required">*</span>:</td>
			<td>
				<s:textfield name="emp.empBirthDate" size="10" readonly="true" maxlength="10"/>
				&nbsp;&nbsp;&nbsp;婚否:
				<s:select name="emp.empMarital"  disabled="true" value="emp.empMarital" list="#{'':'请选择',0:'未婚', 1:'已婚'}"/>
			</td>
			<s:textfield label="工作电话" name="emp.empWorkPhone" size="20"  maxlength="32" readonly="true"/>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td align="right">性别<span class="required">*</span>:</td>
			<td>	
				<s:select name="emp.empGender" required="true"  disabled="true" value="emp.empGender" list="#{1:'男', 0:'女'}"/>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;血型:</span>
				<s:select name="emp.empBlood"  disabled="true" value="emp.empBlood" list="#{'':'请选择', 'O ':'O ', 'A ':'A ','B ':'B ', 'AB':'AB'}"/>
			</td>
			<td align="right">部门<span class="required">*</span>:</td>
			<td><s:textfield name="emp.empDeptNo.departmentName" size="20" readonly="true" maxlength="64"/></td>
			
			
			<td colspan="2"></td>
		</tr>
		<tr>
			<td align="right">政治面貌:</td>
			<td nowrap="nowrap">
				<s:textfield name="emp.empPolitics" size="13" readonly="true" maxlength="64"/>
				<span>&nbsp;学历:</span>
				<s:textfield name="emp.empDiploma" size="8" readonly="true" maxlength="64"/>
			</td>
			<td align="right">职位<span class="required">*</span>:</td>
			<td nowrap="nowrap">
				<s:textfield name="emp.position.positionPbId.pbName" size="20" readonly="true" maxlength="64"/>
			</td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<s:textfield label="毕业院校" name="emp.empSchool" readonly="true" size="30" maxlength="64"/>
			<td align="right">用工形式<span class="required">*</span>:</td>
			<td><s:textfield name="emp.empType.emptypeName" size="20" readonly="true" maxlength="64"/></td>
		</tr>
		<tr>
			<td align="right">专业:</td>
			<td><s:textfield name="emp.empSpeciality" size="14" readonly="true" maxlength="64"/></td>
            <td align="right">工作地区<span class="required">*</span>:</td>
            <td><s:textfield name="emp.empLocationNo.locationName" size="20" readonly="true" maxlength="64"/></td>
        </tr>
		<tr>
			<td align="right">籍贯:</td>
			<td>
				<s:textfield name="emp.empCityNo" size="14" readonly="true" maxlength="64"/>
			</td>
			<td align="right">上传照片:</td>
			<td align="left" colspan="3">
				<s:file id="picture" disabled="true" size="30" name="file" accept="text/GIF,text/jpg,text/jpeg"/>
			</td>
		</tr>
		<tr>
			<td align="right">民族:</td>
			<td><s:textfield name="emp.empNation" size="14" readonly="true" maxlength="64"/></td>	
			<td align="right">参加工作日期:</td>
			<td>
				<s:textfield id="with_job_date" name="emp.empWorkDate" size="10" readonly="true" maxlength="10" required="true"/>
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工龄:&nbsp;<span id="work_year"></span>
			</td>
		</tr>
		<tr>
			<td align="right">户口所在地:</td>
			<td><s:textfield name="emp.empResidenceLoc" readonly="true" maxlength="128" size="30"></s:textfield></td>
			<td align="right">入职日期<span class="required">*</span>:</td>
			<td>
				<s:textfield id="join_date" name="emp.empJoinDate" size="10" readonly="true" maxlength="10" required="true"/>
				&nbsp;&nbsp;&nbsp;&nbsp;司龄:&nbsp;<span id="join_date_year"></span>
			</td>
        </tr>
        <tr>
			<td align="right">档案所在地:</td>
			<td><s:textfield name="emp.empProfileLoc" readonly="true" maxlength="128" size="30"></s:textfield></td>
			<td align="right">转正日期:</td>
			<td>
				<s:textfield id="confirm_date" name="emp.empConfirmDate" size="10" readonly="true" maxlength="10"/>
				&nbsp;&nbsp;&nbsp;试用期：&nbsp;<span id="practice_month"></span>
			</td>
        </tr>
		<tr>
			<td align="right">证件种类<span class="required">*</span>:</td>
			<td>
				<s:select name="empIdentificationType" value="emp.empIdentificationType"
					list="#{0:'身份证', 1:'护照', 2:'驾驶证', 3:'毕业证', 9:'其他'}" disabled="true"/>
				<s:textfield name="emp.empIdentificationNo" readonly="true" size="20" maxlength="64" onkeyup="notchinese(this);"/>
			</td>
			<s:textfield label="社保种类" name="emp.empBenefitType.benefitTypeName" readonly="true" size="20" maxlength="32" onkeyup="notchinese(this);"/>		
		</tr>
		
       	<s:if test="exShiftEnable==1">
			<tr>
            	<td align="right">考勤方式:</td>
				<td>
				    <s:if test="emp.empShiftType==0">免刷卡</s:if>
				    <s:elseif test="emp.empShiftType==2">默认班次刷卡</s:elseif>
				    <s:elseif test="emp.empShiftType==3">按班次刷卡</s:elseif>
				</td>
       			<td align="right">考勤卡号:</td>
       			<td><s:property value="emp.empShiftNo"/></td>
			</tr>
       	</s:if>
       	
		<tr>
			<s:textfield label="家庭电话" name="emp.empHomePhone" size="20" maxlength="32" readonly="true"/>
			<s:textfield label="紧急联系人" name="emp.empUrgentContact" size="20" maxlength="64" readonly="true"/>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<s:textfield label="手机" name="emp.empMobile" size="20" maxlength="32" readonly="true"/>
			<s:textfield label="紧急联系方式" name="emp.empUrgentConMethod" size="20" maxlength="128" readonly="true"/>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<s:textfield label="当前住址" name="emp.empCurrAddr" size="30" maxlength="64" readonly="true"/>
			<td align="right" rowspan="3">备注:</td>
			<td colspan="3" rowspan="3"><s:textarea name="emp.empComments" cols="35" rows="2" readonly="true"/></td>		
		</tr>
		<tr>
			<s:textfield label="邮编" name="emp.empCurrZip" size="8" maxlength="6" readonly="true"/>
		</tr>
		<tr>
			<s:textfield label="家庭地址" name="emp.empHomeAddr" size="30" maxlength="64" readonly="true"/>
		</tr>
		<tr>
			<s:textfield label="邮编" name="emp.empHomeZip" size="8" maxlength="6" readonly="true"/>
			<td align="right">状态<span class="required">*</span>:</td>
			<td>
				<s:hidden name="emp.empStatus"/>
				<s:hidden name="emp.empStatus"/>
				<s:if test="emp.empStatus==0">
					<input value="离职" class="textDisabled" disabled="disabled"/>
				</s:if>
				<s:elseif test="emp.empStatus==1">
					<input value="在职" class="textDisabled" disabled="disabled"/>
				</s:elseif>
			</td>
			
			<!-- <s:textfield label="最后修改时间" name="emp.empLastChangeTime" size="10" readonly="true" maxlength="10"/>-->
		</tr>
		<tr>
			<td colspan="6" align="center">&nbsp;</td> 
        </tr>
		<tr>
			<td colspan="6" align="center"><input type="button" value="员工资料卡" onclick="hrm.profile.showInfoCardInfo('<s:property value="emp.id"/>');"></td> 
        </tr>
	</table>
</s:form>
</div>             
<script type="text/javascript">
//页面加载时初始化工龄，试用期等
display_practice_yearOrMonth=function(){
    var start_work_date=document.getElementById('with_job_date').value;
    var join_date=document.getElementById('join_date').value;
    var due_date=document.getElementById('confirm_date').value;
    if(hrm.common.isDate(start_work_date)){
    	hrm.profile.setIdValue('work_year',hrm.profile.getTimeBetween(0,start_work_date));
    }
    if(hrm.common.isDate(join_date)){
    	hrm.profile.setIdValue('join_date_year',hrm.profile.getTimeBetween(1,join_date));
    }
    if(hrm.common.isDate(due_date)&&hrm.common.isDate(join_date)){
    	hrm.profile.setIdValue('practice_month',hrm.profile.getTimeBetween(1,join_date,due_date));
	}
}
</script>                        
</body>	