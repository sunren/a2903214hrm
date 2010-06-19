<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<head>	
	<script type="text/javascript" src="../resource/js/validator.js"></script>
	<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	

</head>
<body>
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'新增课程计划'" />
		<ww:param name="helpUrl" value="'53'" />
	</ww:component>
<div id="selectcontent" class="selectdiv" style="visibility:hidden;pixelHeight:20px;z-index:9;"><iframe id=selframe frameborder=0 height=100%></iframe><div id="selecthtml" class="selectcontent">selectdate</div></div>
<!-- 可输入的select -->
<script type="text/javascript" src="../resource/js/edit_select.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<div id="create_trcp" align="left">
<ww:form name="trcp" id="trcpForm" action="trcpAdd" namespace="/training" method="POST" enctype="multipart/form-data">
	<ww:token></ww:token>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">		
		<ww:hidden name="trcp.trcpCourseNo.trcNo"></ww:hidden>
		<ww:hidden name="trcp.trcpCourseNo.trcName"></ww:hidden>
		<tr>		
		<td width="10%" align="right"> 课程名称：</td><td width="25%"><ww:property value="trcp.trcpCourseNo.trcName" /></td>
		<td></td><td></td>
		</tr>
		<tr>
		<td rowspan="3" align="right">限制报名部门：</td><td rowspan="3"><ww:select name="trcp.trcpDeptLimit" required="false" list="deptList" listKey="departmentName" listValue="departmentName" emptyOption="true" multiple="true"/>									
		<ww:select name="trcp.trcpStatus" label="状态" required="true" list="#{1:'允许申请', 0:'关闭'}" emptyOption="false" multiple="false"/>
		</tr>
		<tr>
		<ww:textfield id="trcpBudgetYear" label="预算年度" name="trcp.trcpBudgetYear" size="16" required="false" maxlength="4" onkeypress="MKeyIsNumber();"/>		
		</tr>
		<tr>	
		<ww:textfield id="trcpBudgetFee" label="预计培训成本（元）" name="trcp.trcpBudgetFee" size="16" required="false" maxlength="16" onkeypress="MKeyIsNumberOrDot();"/>
		</tr>
		<tr>
			    <ww:textfield id="trcpAttendeeNo" label="限制报名人数" name="trcp.trcpAttendeeNo" size="16" required="false" maxlength="16"  onkeypress="MKeyIsNumber();"/>
				<td align="right">培训时间(精确到小时)<font color="red">*</font>:</td>
				<td>
				<ww:textfield  id="trcpBudgetHour" name="trcp.trcpBudgetHour" required="true" size="12"  />
				</td>
		</tr>		
		<tr>				
				<ww:select id="trcpCoordinator.id" label="负责员工" name="trcp.trcpCoordinator.id" list="empList" listKey="id" listValue="empName" size="16" required="false" multiple="false" emptyOption="true" size="1"/>
				<td align="right">培训开始日期<font color="red">*</font>:</td>
				<td>
				<ww:textfield  id="trcpStartDate" name="trcp.trcpStartDate" required="true" size="12"  />
				<img onclick="WdatePicker({el:'trcpStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
				</td>
		</tr>
		<tr>	
				<ww:textfield id="trcpTeacher" label="培训老师" name="trcp.trcpTeacher" size="16" required="false" maxlength="64"/>
				<td align="right">培训结束日期<font color="red">*</font>:</td>
				<td>
				<ww:textfield  id="trcpEndDate" name="trcp.trcpEndDate" required="true" size="12"  onchange="if (!isComdate(document.getElementById('trcpStartDate').value, document.getElementById('trcpEndDate').value)){alert('培训开始时间必须小于培训结束时间!');document.getElementById('trcpEndDate').value=''}" />
				<img onclick="WdatePicker({el:'trcpEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
				</td>
		</tr>
		<tr>
				<ww:textfield id="trcpInstitution" label="培训机构" name="trcp.trcpInstitution" size="32" required="false" maxlength="128"/>
				<td align="right">报名开始日期:</td>
				<td>
				<ww:textfield  id="trcpEnrollStartDate" name="trcp.trcpEnrollStartDate" required="true" size="12"  />
				<img onclick="WdatePicker({el:'trcpEnrollStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
				</td>	
		</tr>
		<tr>				
				<ww:textfield id="trcpLocation" label="培训地点" name="trcp.trcpLocation" size="32" required="false" maxlength="128"/>				
				<td align="right">报名结束日期:</td>
				<td>
				<ww:textfield  id="trcpEnrollEndDate" name="trcp.trcpEnrollEndDate" required="true" size="12"  />
				<img onclick="WdatePicker({el:'trcpEnrollEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
				</td>
		</tr>
				
		<tr>				
				<ww:textarea id="trcpComments" label="备注" name="trcp.trcpComments" cols="32" rows="4" required="false"/>
				
		</tr>
		<tr>
				 <td colspan="2" align="right">
				 	<input type="submit" value="确定" refreshId="tabContent">
				 	&nbsp;<input type="reset" value="取消">
				 	<input type="hidden" name="trcNo" value="<ww:property value='trcNo'/>"/>
				 	<a class="tabDetailViewDFLink" href="viewTrcTrcpInit.action?trcNo=<ww:property value='trcNo'/>">返回课程详细信息页面&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;
				 </td>
				 <td colspan="4" align="left">                                                      
				 </td>     
		</tr>
	</table>
</ww:form>
</div>      
                              
</body>	