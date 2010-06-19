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
<!-- DWR�ļ����� -->
<script type="text/javascript" src="../dwr/interface/my.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpAddUpdate.js"></script>
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<title>�޸�Ա����Ϣ</title>
</head>
<body onload="display_practice_yearOrMonth();">
<div id="profile_update">
<s:component template="bodyhead"/>
<s:form name="updateEmployee" action="ajaxSelf" namespace="/profile" method="POST" enctype="multipart/form-data">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		   	<s:textfield id="empNo" label="Ա�����" name="emp.empDistinctNo" size="20" required="true" readonly="true" maxlength="64"/>
			<td align="right">�����ַ:</td>
			<td>
				<s:textfield name="emp.empEmail" size="20"  maxlength="64" readonly="true" onkeyup="notchinese(this);"/>
				<s:if test="emp.empEmail!=null&&emp.empEmail!=''">
               		<s:if test="#session.userinfo.employee.id!=emp.id">
               		<a class="listViewTdLinkS1" href="mailto:<s:property value='emp.empEmail'/>"><img alt="�����ʼ�" src="../resource/images/Emails.gif"/></a>
               		</s:if>
               	</s:if>
               </td>
			<td rowspan="5">
			     <img border="1" id="showPicture" style="border: 1px darkslategray outset;" name="showPicture" src="../servlet/showImage?style=head&fileName=<s:property value="emp.empImage"/>" align="left" width="80" height="85"/>
			</td>
		</tr>
		<tr>
			<s:textfield label="Ա������" id="empName" name="emp.empName" size="20" readonly="true" required="true" maxlength="64"/>
			<td align="right">��ʱͨѶ:</td>
            <td>  
				<s:textfield name="connectionNo" id="idConnectionNo" required="true" size="20" maxlength="64" tabindex="25" onkeyup="hrm.common.notChinese(event,this);" readonly="true" onblur="hrm.profile.checkConnection('idconnectionType');"/>
				<s:select name="connectionType" id="idconnectionType" onblur="hrm.profile.checkConnection('idconnectionType');" tabindex="26" value="connectionType" disabled="true" required="true" list="#{0:'MSN', 1:'QQ', 2:'����'}"/>
			</td>
            <td colspan="2"></td>
            <td colspan="2"></td>
		</tr>
		<tr>
			<td align="right">��������<span class="required">*</span>:</td>
			<td>
				<s:textfield name="emp.empBirthDate" size="10" readonly="true" maxlength="10"/>
				&nbsp;&nbsp;&nbsp;���:
				<s:select name="emp.empMarital"  disabled="true" value="emp.empMarital" list="#{'':'��ѡ��',0:'δ��', 1:'�ѻ�'}"/>
			</td>
			<s:textfield label="�����绰" name="emp.empWorkPhone" size="20"  maxlength="32" readonly="true"/>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td align="right">�Ա�<span class="required">*</span>:</td>
			<td>	
				<s:select name="emp.empGender" required="true"  disabled="true" value="emp.empGender" list="#{1:'��', 0:'Ů'}"/>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ѫ��:</span>
				<s:select name="emp.empBlood"  disabled="true" value="emp.empBlood" list="#{'':'��ѡ��', 'O ':'O ', 'A ':'A ','B ':'B ', 'AB':'AB'}"/>
			</td>
			<td align="right">����<span class="required">*</span>:</td>
			<td><s:textfield name="emp.empDeptNo.departmentName" size="20" readonly="true" maxlength="64"/></td>
			
			
			<td colspan="2"></td>
		</tr>
		<tr>
			<td align="right">������ò:</td>
			<td nowrap="nowrap">
				<s:textfield name="emp.empPolitics" size="13" readonly="true" maxlength="64"/>
				<span>&nbsp;ѧ��:</span>
				<s:textfield name="emp.empDiploma" size="8" readonly="true" maxlength="64"/>
			</td>
			<td align="right">ְλ<span class="required">*</span>:</td>
			<td nowrap="nowrap">
				<s:textfield name="emp.position.positionPbId.pbName" size="20" readonly="true" maxlength="64"/>
			</td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<s:textfield label="��ҵԺУ" name="emp.empSchool" readonly="true" size="30" maxlength="64"/>
			<td align="right">�ù���ʽ<span class="required">*</span>:</td>
			<td><s:textfield name="emp.empType.emptypeName" size="20" readonly="true" maxlength="64"/></td>
		</tr>
		<tr>
			<td align="right">רҵ:</td>
			<td><s:textfield name="emp.empSpeciality" size="14" readonly="true" maxlength="64"/></td>
            <td align="right">��������<span class="required">*</span>:</td>
            <td><s:textfield name="emp.empLocationNo.locationName" size="20" readonly="true" maxlength="64"/></td>
        </tr>
		<tr>
			<td align="right">����:</td>
			<td>
				<s:textfield name="emp.empCityNo" size="14" readonly="true" maxlength="64"/>
			</td>
			<td align="right">�ϴ���Ƭ:</td>
			<td align="left" colspan="3">
				<s:file id="picture" disabled="true" size="30" name="file" accept="text/GIF,text/jpg,text/jpeg"/>
			</td>
		</tr>
		<tr>
			<td align="right">����:</td>
			<td><s:textfield name="emp.empNation" size="14" readonly="true" maxlength="64"/></td>	
			<td align="right">�μӹ�������:</td>
			<td>
				<s:textfield id="with_job_date" name="emp.empWorkDate" size="10" readonly="true" maxlength="10" required="true"/>
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����:&nbsp;<span id="work_year"></span>
			</td>
		</tr>
		<tr>
			<td align="right">�������ڵ�:</td>
			<td><s:textfield name="emp.empResidenceLoc" readonly="true" maxlength="128" size="30"></s:textfield></td>
			<td align="right">��ְ����<span class="required">*</span>:</td>
			<td>
				<s:textfield id="join_date" name="emp.empJoinDate" size="10" readonly="true" maxlength="10" required="true"/>
				&nbsp;&nbsp;&nbsp;&nbsp;˾��:&nbsp;<span id="join_date_year"></span>
			</td>
        </tr>
        <tr>
			<td align="right">�������ڵ�:</td>
			<td><s:textfield name="emp.empProfileLoc" readonly="true" maxlength="128" size="30"></s:textfield></td>
			<td align="right">ת������:</td>
			<td>
				<s:textfield id="confirm_date" name="emp.empConfirmDate" size="10" readonly="true" maxlength="10"/>
				&nbsp;&nbsp;&nbsp;�����ڣ�&nbsp;<span id="practice_month"></span>
			</td>
        </tr>
		<tr>
			<td align="right">֤������<span class="required">*</span>:</td>
			<td>
				<s:select name="empIdentificationType" value="emp.empIdentificationType"
					list="#{0:'���֤', 1:'����', 2:'��ʻ֤', 3:'��ҵ֤', 9:'����'}" disabled="true"/>
				<s:textfield name="emp.empIdentificationNo" readonly="true" size="20" maxlength="64" onkeyup="notchinese(this);"/>
			</td>
			<s:textfield label="�籣����" name="emp.empBenefitType.benefitTypeName" readonly="true" size="20" maxlength="32" onkeyup="notchinese(this);"/>		
		</tr>
		
       	<s:if test="exShiftEnable==1">
			<tr>
            	<td align="right">���ڷ�ʽ:</td>
				<td>
				    <s:if test="emp.empShiftType==0">��ˢ��</s:if>
				    <s:elseif test="emp.empShiftType==2">Ĭ�ϰ��ˢ��</s:elseif>
				    <s:elseif test="emp.empShiftType==3">�����ˢ��</s:elseif>
				</td>
       			<td align="right">���ڿ���:</td>
       			<td><s:property value="emp.empShiftNo"/></td>
			</tr>
       	</s:if>
       	
		<tr>
			<s:textfield label="��ͥ�绰" name="emp.empHomePhone" size="20" maxlength="32" readonly="true"/>
			<s:textfield label="������ϵ��" name="emp.empUrgentContact" size="20" maxlength="64" readonly="true"/>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<s:textfield label="�ֻ�" name="emp.empMobile" size="20" maxlength="32" readonly="true"/>
			<s:textfield label="������ϵ��ʽ" name="emp.empUrgentConMethod" size="20" maxlength="128" readonly="true"/>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<s:textfield label="��ǰסַ" name="emp.empCurrAddr" size="30" maxlength="64" readonly="true"/>
			<td align="right" rowspan="3">��ע:</td>
			<td colspan="3" rowspan="3"><s:textarea name="emp.empComments" cols="35" rows="2" readonly="true"/></td>		
		</tr>
		<tr>
			<s:textfield label="�ʱ�" name="emp.empCurrZip" size="8" maxlength="6" readonly="true"/>
		</tr>
		<tr>
			<s:textfield label="��ͥ��ַ" name="emp.empHomeAddr" size="30" maxlength="64" readonly="true"/>
		</tr>
		<tr>
			<s:textfield label="�ʱ�" name="emp.empHomeZip" size="8" maxlength="6" readonly="true"/>
			<td align="right">״̬<span class="required">*</span>:</td>
			<td>
				<s:hidden name="emp.empStatus"/>
				<s:hidden name="emp.empStatus"/>
				<s:if test="emp.empStatus==0">
					<input value="��ְ" class="textDisabled" disabled="disabled"/>
				</s:if>
				<s:elseif test="emp.empStatus==1">
					<input value="��ְ" class="textDisabled" disabled="disabled"/>
				</s:elseif>
			</td>
			
			<!-- <s:textfield label="����޸�ʱ��" name="emp.empLastChangeTime" size="10" readonly="true" maxlength="10"/>-->
		</tr>
		<tr>
			<td colspan="6" align="center">&nbsp;</td> 
        </tr>
		<tr>
			<td colspan="6" align="center"><input type="button" value="Ա�����Ͽ�" onclick="hrm.profile.showInfoCardInfo('<s:property value="emp.id"/>');"></td> 
        </tr>
	</table>
</s:form>
</div>             
<script type="text/javascript">
//ҳ�����ʱ��ʼ�����䣬�����ڵ�
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