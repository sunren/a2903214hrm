<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>     
	<tr>
		<s:textfield label="Ա������" id="empName" name="emp.empName" size="20" required="true" maxlength="64" tabindex="2"/>
		<td align="right">��ʱͨѶ:</td>
		<td>  
			<s:textfield name="connectionNo" id="idConnectionNo" required="true" size="20" maxlength="64" tabindex="25" onkeyup="hrm.common.notChinese(event,this);" onblur="hrm.profile.checkConnection('idconnectionType');"/>
            <s:select name="connectionType" id="idconnectionType" value="connectionType" required="true" tabindex="26" list="#{0:'MSN', 1:'QQ', 2:'����'}" onblur="hrm.profile.checkConnection('idconnectionType');"/>
		</td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td align="right">��������<span class="required">*</span>:</td>
		<td>
			<s:textfield id="birthDate" name="emp.empBirthDate" required="true" tabindex="3" size="10"/>
			<img onclick="WdatePicker({el:'birthDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="14" height="22" style="cursor:pointer" align="absmiddle">
			&nbsp;���:<s:select name="emp.empMarital" value="emp.empMarital" tabindex="4" list="#{'':'��ѡ��',0:'δ��', 1:'�ѻ�'}"/>
		</td>
		<s:textfield label="�����绰" name="emp.empWorkPhone" size="23"  maxlength="32" tabindex="27" onkeyup="hrm.common.notChinese(event,this);" onkeypress="hrm.common.checkPhone(this);" onblur="hrm.common.checkPhone(this);"/>
		<td colspan="2"></td>
     </tr>
     <tr>
		<td align="right">�Ա�<span class="required">*</span>:</td>
		<td nowrap="nowrap">
			<s:select name="emp.empGender" required="true" tabindex="5" value="emp.empGender" list="#{1:'��', 0:'Ů'}"/>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ѫ��:</span>
			<s:select name="emp.empBlood" tabindex="6" value="emp.empBlood" list="#{'':'��ѡ��', 'O ':'O ', 'A ':'A ','B ':'B ', 'AB':'AB'}"/>
		</td>
		<td align="right">����<span class="required">*</span>:</td>
		<td>					
	    <s:hidden id="chang_dept_id" name="emp.empDeptNo.id"/>
		 <s:textfield id="chang_dept_name" name="emp.empDeptNo.departmentName" size="16" readonly="true"/>
		</td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td align="right">������ò:</td>
		<td nowrap="nowrap">
			<s:select name="emp.empPolitics" tabindex="7" value="emp.empPolitics" emptyOption="true" 
				list="@com.hr.base.ComonBeans@getEmpPolitics()"/>
			<span>ѧ��:</span>
			<s:select name="emp.empDiploma" tabindex="8" value="emp.empDiploma" list="@com.hr.base.ComonBeans@getEmpDiploma()" emptyOption="true" />
		</td>
		<td align="right">ְλ<span class="required">*</span>:</td>
		<td>	
			<div>
	           	<table cellpadding="0" cellspacing="0" class="select">
					<tr><td bgcolor="#FFFFFF">
						<s:hidden id="positionId" name="emp.position.id"/>
						<input id="empPBName" name="emp.position.positionPbId.pbName" value='<s:property value="emp.position.positionPbId.pbName"/>'  class='selecttext' readonly="true" size="12" />
						<button type="button" class="selectbutton" style="CURSOR: pointer" id="showdiv" onclick="showPostionTree('empPBName', 'positionId', 'chang_dept_name', 'chang_dept_id');"/>&nbsp;
						</button>
					</td></tr>
				</table>
			</div>
		</td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<s:textfield label="��ҵԺУ" tabindex="9" name="emp.empSchool" size="30" maxlength="64"/>
		<s:select label="�ù���ʽ" name="emp.empType.id" tabindex="28" list="empTypes" listKey="id" required="true" 
			listValue="emptypeName" multiple="false" emptyOption="true" value="emp.empType.id" size="1" />
    </tr>
    <tr>
        <td align="right">רҵ:</td>
		<td>
			<s:component template="editselect" name="emp.empSpeciality">
				<s:param name="list" value="@com.hr.base.ComonBeans@getEmpSpeciality()"/>
                <s:param name="size" value="8"/>
                <s:param name="height" value="220"/>
                <s:param name="tabindex" value="10"/>
			</s:component>
		</td>
		<s:select label="��������" name="emp.empLocationNo.id" tabindex="35" list="locations" listKey="id" required="true"
			listValue="locationName" multiple="false" emptyOption="true" value="emp.empLocationNo.id" size="1" />
	</tr>
	<tr>
		<td align="right">����:</td>
		<td>
			<s:component template="editselect"  name="emp.empCityNo">
				<s:param name="list" value="@com.hr.base.ComonBeans@getEmpCityNo()"/>
                <s:param name="size" value="8"/>
                <s:param name="height" value="220"/>
                <s:param name="tabindex" value="11"/>
			</s:component>
		</td>
        <td align="right">�ϴ���Ƭ:</td>
        <td align="left" colspan="5">
			<s:file id="picture" tabindex="31" onchange="hrm.profile.showPic();" size="30" name="file" accept="text/GIF,text/jpg,text/jpeg"/>
		</td>
	</tr>
	<tr>
		<td align="right">����:</td>
		<td>
			<s:component template="editselect"  name="emp.empNation">
				<s:param name="list" value="@com.hr.base.ComonBeans@getEmpNation()"/>
                <s:param name="size" value="8"/>
                <s:param name="height" value="200"/>
                <s:param name="tabindex" value="12"/>
            </s:component>
		</td>   
		<td align="right">�μӹ�������:</td>
		<td>
			<s:textfield id="with_job_date" name="emp.empWorkDate" required="true" tabindex="32" size="10" onchange="hrm.profile.setIdValue('work_year',hrm.profile.getTimeBetween(0,this.value));"/>
			<img onclick="WdatePicker({el:'with_job_date'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����:&nbsp;<span id="work_year"><s:property value="getYearOrMonth(emp.workYear)"/></span>
		</td>
	</tr>
	<tr>
		<td align="right">�������ڵ�:</td>
		<td>
			<s:textfield name="emp.empResidenceLoc" maxlength="128" size="30" tabindex="13"></s:textfield>
		</td>
		<td align="right">��ְ����<span class="required">*</span>:</td>
		<td>
			<s:textfield id="join_date" name="emp.empJoinDate" required="true" tabindex="33" size="10"
				onchange="hrm.profile.setIdValue('join_date_year',hrm.profile.getTimeBetween(1,this.value));"/>
			<img onclick="WdatePicker({el:'join_date'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;˾��:&nbsp;<span id="join_date_year"><s:property value="getYearOrMonth(emp.joinYear)"/></span>
		</td>
	</tr>
	<tr>
		<s:textfield label="�������ڵ�" name="emp.empProfileLoc" maxlength="128" size="30" tabindex="14"></s:textfield>
		<td align="right">ת������:</td>
		<td>
			<s:textfield id="confirm_date" name="emp.empConfirmDate" required="true" tabindex="34" size="10"
				onchange="hrm.profile.setIdValue('practice_month',hrm.profile.getTimeBetween(1,document.getElementById('join_date').value),this.value);"/>
			<img onclick="WdatePicker({el:'confirm_date'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
			&nbsp;&nbsp;&nbsp;������:&nbsp;<span id="practice_month"></span>
		</td>
	</tr>
	<tr>
		<td align="right">֤������<span class="required">*</span>:</td>
		<td>
			<s:select name="emp.empIdentificationType" id="identificationType" tabindex="15" onblur="hrm.common.checkIdentify('identificationType','idNo');" value="emp.empIdentificationType" required="true" list="#{0:'���֤', 1:'����', 2:'��ʻ֤', 3:'��ҵ֤', 9:'����'}"/>
			<s:textfield name="emp.empIdentificationNo" id="idNo" required="true" size="20" maxlength="64" tabindex="16" onkeyup="hrm.common.notChinese(event,this);" onblur="hrm.common.checkIdentify('identificationType','idNo');"/>
		</td>
		<td align="right">&nbsp;�籣����:</td>
		<td>
			<s:select id="empBenefitType" name="emp.empBenefitType.id" tabindex="20" list="benefitTypes" listKey="id"
					listValue="benefitTypeName" multiple="false" emptyOption="true" value="emp.empBenefitType.id" size="1" />
       		<s:if test="exShiftEnable==1">
				<span>
					&nbsp;���ڷ�ʽ:<s:select id="shiftType" onchange="setShiftNo();" name="emp.empShiftType" list="#{'0':'��ˢ��','2':'Ĭ�ϰ��ˢ��','3':'�����ˢ��'}"/>
				</span>
       		</s:if>
		</td>
	</tr>
    <s:if test="exShiftEnable==1">
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="right">
				<span id="shiftNo">&nbsp;&nbsp;���ڿ���:</span>
			</td>
			<td>
				<span id="shiftField"><s:textfield name="emp.empShiftNo" size="18" maxlength="32" tabindex="18" /></span>
			</td>
		</tr>
    </s:if>
	<tr>
		<s:textfield label="��ͥ�绰" name="emp.empHomePhone" size="20" maxlength="32" tabindex="18" onkeyup="hrm.common.notChinese(event,this);" onkeypress="hrm.common.checkPhone(this);" onblur="hrm.common.checkPhone(this);"/>
        <s:textfield label="������ϵ��" name="emp.empUrgentContact" size="23" maxlength="64" tabindex="39"/>
	</tr>
	<tr>
		<s:textfield label="�ֻ�" name="emp.empMobile" size="20" maxlength="32" tabindex="19" onkeyup="hrm.common.notChinese(event,this);" onkeypress="hrm.common.checkPhone(this);" onblur="hrm.common.checkPhone(this);"/>
        <s:textfield label="������ϵ��ʽ" name="emp.empUrgentConMethod" size="23" maxlength="128" tabindex="40"/>
    </tr>
    <tr>
        <s:textfield label="��ǰסַ" name="emp.empCurrAddr" size="30" maxlength="64" tabindex="20"/>
         <td align="right" rowspan="3">��ע:</td>
        <td colspan="4" rowspan="3"><s:textarea name="emp.empComments" cols="38" rows="3" tabindex="41" onkeypress="hrm.common.MKeyTextLength(this,255);"/></td> 
    </tr>
    <tr>
        <s:textfield label="�ʱ�" name="emp.empCurrZip" size="8" maxlength="6" tabindex="21" onkeyup="hrm.common.notChinese(event,this);" onblur="hrm.common.checkZip(this);"/>
    </tr>
    <tr>
        <s:textfield label="��ͥ��ַ" name="emp.empHomeAddr" size="30" maxlength="64" tabindex="22"/>
    </tr>
        
<script type="text/javascript">
//����ѡ��Ŀ��ڷ�ʽ�����Ƿ���ʾ���ڿ��������
function setShiftNo(){
	var shiftType = document.getElementById('shiftType');
	if(!shiftType || shiftType.value == '' || shiftType.value == '0'){
		if(document.getElementById('shiftNo') != null) {
			document.getElementById('shiftNo').style.display = 'none';
		}
		if(document.getElementById('shiftField')){
			document.getElementById('shiftField').style.display = 'none';
		}
		return;
	}
	if(document.getElementById('shiftNo') != null) {
		document.getElementById('shiftNo').style.display = 'inline';
	}
	if(document.getElementById('shiftField')){
		document.getElementById('shiftField').style.display = 'inline';
	}
}
//����ĳ��select��option��value��ֵ
function addOption(selectId,value,text,selected){
    var option = new Option(text, value);
    option.selected = selected;
    var select = document.getElementById(selectId);
    select.options[select.options.length] = option;
}

//var tempDept=document.getElementById("emp_empDeptNo_id").value;
//var tempBu=document.getElementById("emp_empBuNo_id").value;
setShiftNo();
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
