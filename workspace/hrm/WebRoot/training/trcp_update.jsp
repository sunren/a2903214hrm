<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<head>
	<script type="text/javascript" src="../resource/js/validator.js"></script>
	<script type="text/javascript" src="../resource/js/training/training.js"></script>
</head>
<body onload="init();">
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'�޸Ŀγ̼ƻ�'" />
		<ww:param name="helpUrl" value="'53'" />
	</ww:component>

<div id="update_trcp" align="left">
<ww:form name="trcp" action="trcpUpdate" namespace="/training" method="POST" enctype="multipart/form-data">
	<ww:token></ww:token>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<ww:hidden name="trcp.trcpId"></ww:hidden>		
		<ww:hidden name="trcp.trcpCreateTime"></ww:hidden>
		<ww:hidden name="trcp.trcpCreateBy.id"></ww:hidden>	
		<ww:hidden name="trcp.trcpCourseNo.trcNo"></ww:hidden>
		<ww:hidden name="trcp.trcpCourseNo.trcName"></ww:hidden>		
				
		<td width="10%" align="right">�γ����ƣ�</td><td width="25%"><ww:property value="trcp.trcpCourseNo.trcName" /></td>
		</tr>
		<tr>
		<ww:hidden id="DeptLimit" value='%{trcp.trcpDeptLimit}'/>
		<td rowspan="3" align="right">���Ʊ������ţ�</td><td rowspan="3"><ww:select id="trcp.trcpDeptLimit" name="trcp.trcpDeptLimit" required="false" list="deptList" listKey="departmentName" listValue="departmentName" emptyOption="true" multiple="true" value="deptListValue" /></td>									
		<ww:select name="trcp.trcpStatus" label="״̬" required="true" list="#{1:'��������', 0:'�ر�'}" emptyOption="false" multiple="false"/>
		</tr>
		<tr>
		<ww:textfield id="trcpBudgetYear" label="Ԥ�����" name="trcp.trcpBudgetYear" size="16" required="false" maxlength="4" onkeypress="MKeyIsNumber();"/>		
		</tr>
		<tr>	
		<ww:textfield id="trcpBudgetFee" label="Ԥ����ѵ�ɱ���Ԫ��" name="trcp.trcpBudgetFee" size="16" required="false" maxlength="16" onkeypress="MKeyIsNumberOrDot();"/>
		</tr>
		<tr>
			    <ww:textfield id="trcpAttendeeNo" label="���Ʊ�������" name="trcp.trcpAttendeeNo" size="16" required="false" maxlength="16"  onkeypress="MKeyIsNumber();"/>
				<td align="right">��ѵʱ��(��ȷ��Сʱ)<font color="red">*</font>:</td>
				<td>
				<ww:textfield  id="trcpBudgetHour" name="trcp.trcpBudgetHour" required="true" size="12"  />
				</td>
		</tr>		
		<tr>				
				<ww:select id="trcpCoordinator.id" label="����Ա��" name="trcp.trcpCoordinator.id" list="empList" listKey="id" listValue="empName" size="16" required="false" multiple="false" emptyOption="true" size="1"/>
				<td align="right">��ѵ��ʼ����<font color="red">*</font>:</td>
				<td>
				<ww:textfield  id="trcpStartDate" name="trcp.trcpStartDate" required="true" size="12"  />
				<img onclick="WdatePicker({el:'trcpStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
				</td>
		</tr>
		<tr>	
				<ww:textfield id="trcpTeacher" label="��ѵ��ʦ" name="trcp.trcpTeacher" size="16" required="false" maxlength="64"/>
				<td align="right">��ѵ��������<font color="red">*</font>:</td>
				<td>
				<ww:textfield  id="trcpEndDate" name="trcp.trcpEndDate" required="true" size="12"  onchange="if (!isComdate(document.getElementById('trcpStartDate').value, document.getElementById('trcpEndDate').value)){alert('��ѵ��ʼʱ�����С����ѵ����ʱ��!');document.getElementById('trcpEndDate').value=''}" />
				<img onclick="WdatePicker({el:'trcpEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
				</td>
		</tr>
		<tr>
				<ww:textfield id="trcpInstitution" label="��ѵ����" name="trcp.trcpInstitution" size="32" required="false" maxlength="128"/>
				<td align="right">������ʼ����:</td>
				<td>
				<ww:textfield  id="trcpEnrollStartDate" name="trcp.trcpEnrollStartDate" required="true" size="12"  />
				<img onclick="WdatePicker({el:'trcpEnrollStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
				</td>
		</tr>
		<tr>				
				<ww:textfield id="trcpLocation" label="��ѵ�ص�" name="trcp.trcpLocation" size="32" required="false" maxlength="128"/>				
				<td align="right">������������:</td>
				<td>
				<ww:textfield  id="trcpEnrollEndDate" name="trcp.trcpEnrollEndDate" required="true" size="12"  />
				<img onclick="WdatePicker({el:'trcpEnrollEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22"  style="cursor:pointer" align="absmiddle">
				</td>
		</tr>				
		<tr>				
				<ww:textarea id="trcpComments" label="��ע" name="trcp.trcpComments" cols="32" rows="4" required="false"/>
				<ww:file label="��ѵ��������" id="trcpFileName" name="file"/>
		</tr>
		<tr>
				 <td colspan="2" align="right">
				 	<input type="submit" value="ȷ��" refreshId="tabContent">
				 	&nbsp;<input type="reset" value="ȡ��">
				 	<input type="hidden" name="trcNo" value="<ww:property value='trcNo'/>"/>
				 	<a class="tabDetailViewDFLink" href="viewTrcTrcpInit.action?trcNo=<ww:property value='trcNo'/>">���ؿγ���ϸ��Ϣҳ��&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;
				 </td>
				 <td colspan="4" align="left">                                                      
				 </td>     
		</tr>
	</table>
</ww:form>
</div>      
<div id="errorMsg" style="display:none;border:1px solid #e00;background-color:#fee;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:#900"></div>  
</body>	