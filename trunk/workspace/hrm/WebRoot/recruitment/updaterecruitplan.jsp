<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax"%>
<head>
<script type="text/javascript" src="../resource/js/validor.js"></script>
<script type="text/javascript" src="../resource/js/validator.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/add_update_Recruitplan.js"></script>
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
</head>
<html>
	<body onload="deliver()">
		<ww:component template="bodyhead">
			<ww:param name="pagetitle" value="'更新招聘计划'" />
		</ww:component>
	
		<form id="UpdateRecruitplan" name="UpdateRecruitplan" method="POST" action="UpdateRecruitplan.action">
	
		 <ww:token />
			<ww:hidden name="recruitplan.id" />
			<ww:hidden name="recruitplan.recpNo" />
			<ww:hidden name="recruitplan.recpCreateBy.id" />
			<ww:hidden name="recruitplan.recpCreateTime" />
			<ww:hidden id="recruitplan.recpStatus" name="recruitplan.recpStatus" />
			<TABLE width="100%" >
				<tr>
				<td width="10%" align="right">职位名称<font color="red">*</font>：</td><td width="25%" align="left">
				<ww:select list="jobTitles" listKey="jobtitleNo" listValue="jobtitleName" emptyOption="true" name="recruitplan.recpJobTitle.jobtitleNo"></ww:select>
				</td>
				<td width="10%" align="right">计划招聘人数<font color="red">*</font>：</td><td width="25%" align="left"><ww:textfield id="recpNumberExpect" name="recruitplan.recpNumberExpect" required="true" size="8"	maxlength="8" onkeypress="return onlyNumber();" /></td>
				</tr>
			
				<tr>
				<ww:if test="hasAuthModuleCondition(611,3)">
					<ww:select id="recruitplan.recpDepartmentNo.id" label="职位所属部门" name="recruitplan.recpDepartmentNo.id" list="allDept" listKey="id" listValue="departmentName " emptyOption="true" required="true" />
				</ww:if>
				<ww:else>
					<ww:hidden id="recruitplan.recpDepartmentNo.id" name="recruitplan.recpDepartmentNo.id" value="%{departmentId}"/>
					<ww:textfield id="departmentName" label="职位所属部门" name="departmentName" size="12" readonly="true" required="true"/>
				</ww:else>		
					<ww:select id="recpWorkLocation" label="工作地区" name="recruitplan.recpWorkLocation.id" list="allLocation" listKey="id" listValue="locationName" emptyOption="true" required="true"/>
				
				</tr>
				<tr>
				<ww:select id="recpType" label="应聘用工形式" name="recruitplan.recpType.id" list="emptype" listKey="id" listValue="emptypeName" required="true" emptyOption="true" />
				<td align="right">职位发布日期:</td>
				<td>
				<ww:textfield  id="startDate" name="recruitplan.recpStartDate" required="true" size="12"/>
				<img onclick="WdatePicker({el:'startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
				</td>
				</tr>				
				<tr>
					
					<ww:textfield id="recpJobSkill" label="职位技能要求" name="recruitplan.recpJobSkill" size="40"  maxlength="255"/>
					<td align="right">截止日期:</td>
					<td>
					<ww:textfield  id="endDate" name="recruitplan.recpEndDate" required="true" size="12"/>
					<img onclick="WdatePicker({el:'endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
					</td>
				</tr>

				<tr>
					<ww:textfield id="recpLanguageSkill" label="语言技能要求" name="recruitplan.recpLanguageSkill" size="40"  maxlength="64"/>
					<ww:select id="recruitplan" label="学历要求" name="recruitplan.recpDegree" list="@com.hr.base.ComonBeans@getEmpDiploma()" emptyOption="true"  />
				</tr>
				<tr>
					<ww:textarea name="recruitplan.recpJobDesc" label="职位描述" cols="50" required="true" rows="6" onkeypress="MKeyTextLength(this,500);" />
				</tr>
				<tr>
					<ww:textarea id="comments" name="recruitplan.recpComments" label="备注" cols="50" rows="4" onkeypress="MKeyTextLength(this,255);" />
					<input type="hidden" id="oldComments" name="oldComments"/>								
				</tr>
				<tr>
				<td colspan="4"></td>
				</tr>
				<tr align="center">
					<td height="1" colspan="9">									
						<input type="button" id="btnUpdate" value="更新" onclick="updatePlanSubmit();">
						<input type="reset"  value="重置">
					</td>
				</tr>
			</TABLE>
		</form>
	</body>
</html>
