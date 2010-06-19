<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<head>
	<!-- 验证js -->
	<script type="text/javascript" src="../resource/js/validor.js"></script>
	<script type="text/javascript" src="../resource/js/validator.js"></script>
	<!-- 日期时间js -->
	<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
	<!-- 自己编写的js -->
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/add_update_Recruitplan.js"></script>
</head>
<html>	
	<body valign="top">
		<ww:component template="bodyhead">
			<ww:param name="pagetitle" value="'创建招聘计划'" />
			<ww:param name="helpUrl" value="'41'" />
		</ww:component>
	
	<form id="addrecruitplan" name="addrecruitplan" method="POST" action="AddRecruitplan.action">        
	        <ww:token />	       
			<!-- <ww:hidden id="recpStatus" name="recruitplan.recpStatus" /> -->
			<ww:hidden id="DraftOrSubmit" name="DraftOrSubmit"/>			
			<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr>
				<td width="10%" align="right">职位名称<font color="red">*</font>:</td><td width="25%" align="left">
				<ww:select list="jobTitles" listKey="jobtitleNo" listValue="jobtitleName" emptyOption="true" name="recruitplan.recpJobTitle.jobtitleNo"></ww:select>
				</td>
				<td width="10%" align="right">计划招聘人数<font color="red">*</font>:</td><td width="20%" align="left"><ww:textfield id="recpNumberExpect" name="recruitplan.recpNumberExpect" required="true" size="8"	maxlength="8" onkeypress="return onlyNumber();" /></td>
				</tr>				
				<tr>
				<ww:if test="hasAuthModuleCondition(611,3)">
					<ww:select id="recruitplan.recpDepartmentNo.id" label="职位所属部门" name="recruitplan.recpDepartmentNo.id" list="allDept" listKey="id" listValue="departmentName " emptyOption="true" required="true"  />
				</ww:if>
				<ww:else>
					<ww:hidden id="recruitplan.recpDepartmentNo.id" name="recruitplan.recpDepartmentNo.id" value="%{departmentId}"/>
					<ww:textfield id="departmentName" label="职位所属部门" name="departmentName" size="12" readonly="true" required="true"/>
				</ww:else>				
				<ww:select id="recpWorkLocation" label="工作地区" name="recruitplan.recpWorkLocation.id" list="allLocation" listKey="id" listValue="locationName" emptyOption="true" required="true" />
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
					<ww:textarea name="recruitplan.recpComments" label="备注" cols="50" rows="4" onkeypress="MKeyTextLength(this,255);" />
				</tr>
				<tr>
				<td colspan="4"></td>
				</tr>
				<tr align="center">
					<td height="1" colspan="9">						
							<input id="btnSaveDraft" type="button" value="存为草稿" onclick="createPlan();" />
							<input id="btnSubmitRP" type="button" value="提交招聘计划" onclick="submitPlan()" />	
							<input  type="reset"  value="重置" />
					</td>
				</tr>
			</TABLE>
		</form>
	</body>
</html>
