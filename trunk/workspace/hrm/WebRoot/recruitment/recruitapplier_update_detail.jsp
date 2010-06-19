<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>

<head>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/search.js"></script>
<script language="javascript" src="../resource/js/validator.js"></script>
<script type="text/javascript" src="../resource/js/recruitment/applier.js"></script>
<script type="text/javascript" src="../resource/js/recruitment/applierCreate.js"></script>
<!-- dwr ѡ����Ƹ�ƻ� -->
	<script type="text/javascript" src="../dwr/interface/RecruitapplierCreateInit.js"></script>
<title>����ӦƸ����Ϣ</title>
</head>
<body onload="checkCreatStatus(document.getElementById('applierStatus').value)">
	<div id="update_applier" align="left">	
		<ww:component template="bodyhead">
			<ww:param name="pagetitle" value="'����ӦƸ��'" />
			<ww:param name="helpUrl" value="'45'" />
		</ww:component>	
	<ww:form id="updateApplier" name="updateApplier" action="recruitapplierUpdateDetail" namespace="/recruitment"  method="POST" enctype="multipart/form-data">
		 <ww:token />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">			
			<ww:hidden name="applier.recaCreateTime"></ww:hidden>
			<ww:hidden name="applier.id"></ww:hidden>
			<ww:hidden id="flagFrom" name="flagFrom"/>			
			<tr>						
			<td width="10%" align="right">��Ƹ�ƻ�<font color="red">*</font>��</td><td width="20%" align="left" >	<ww:textfield  id="plan_job_title"  name="applier.recaPlan.recpNo" size="20" readonly="true" maxlength="32" required="true" /> </td>
			<ww:hidden id="plan_id" name="applier.recaPlan.id" />	
			<ww:hidden id="id" name="id" value="%{applier.recaPlan.id}"/>	<!-- ��Ƹ�ƻ���id -->	
			<ww:hidden name="planID"/>	
			<ww:file label="��������"  id="attachment"  name="file" accept="text/doc,text/xls,text/pdf,text/htm,text/html,text/zip,text/rar" onchange="file_check();"/>
			<td><ww:if test="applier.recaAttachmentName!=null">
					<a title="���ظ���:<ww:property value='applier.recaAttachmentName'/>" href="recruitapplierDownFile.action?fileName=<ww:property value='applier.recaAttachmentName'/>">
						<img src="../resource/images/attachment.gif"/>
						ԭ������<ww:property value="applier.recaAttachmentName" />
					</a>
				</ww:if>
			</td>
			</tr>
			
			<tr>
				<ww:select label="��Ƹ����" name="applier.recaChannel.id" list="RecruitchannelList" listKey="id" required="true" listValue="recchName" multiple="false" emptyOption="true" value="applier.recaChannel.id" size="1" />
				<ww:select label="ӦƸ��״̬" id="applierStatus" name="applier.recaStatus" list="applierStatus" listKey="id.statusconfNo" listValue="statusconfDesc" multiple="false" emptyOption="true"  onchange="checkCreatStatus(this.value);"/>						
			</tr>
				
			<tr>
						 <ww:textfield label="����" id="recaName" name="applier.recaName" size="20" required="true" maxlength="32"/>
						<td align="right">����/����ʱ��:</td>
					    <td><ww:textfield id="interviewDate" name="applier.recaInterviewTime" required="true" size="21"  />
					    <img onclick="WdatePicker({el:'interviewDate',dateFmt:'yyyy-MM-dd HH:mm'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">								
					    </td> 
			</tr>
			
			<tr>
						<ww:textfield label="�绰" id="recaPhone" name="applier.recaPhone" size="20" required="true" maxlength="16" onkeypress="checkphone();"/>
						<ww:textfield id="interviewer" name="applier.recaInterviewer" label="����/������" size="20"  maxlength="32" />
			</tr>

			<tr>		
			<ww:hidden name="applier.recaCreateBy.id"/>
			<ww:hidden name="applier.recaAttachmentName"/>
			<ww:hidden name="applier.recaLastChangeBy.id" />		
			<tr >
						<ww:textfield label="�����ַ" name="applier.recaEmail" size="20" required="true" maxlength="64"  onkeypress="checkemail();"/>
						<td align="right" rowspan="2">��ע��</td>	 <td rowspan="2"><ww:textarea name="applier.recaComment" cols="30" rows="3" onkeypress="MKeyTextLength(this,128);" /></td>
			</tr>
			
			<tr>
						<ww:select label="ѧ��" name="applier.recaDiploma"   multiple="false"  list="@com.hr.base.ComonBeans@getEmpDiploma()" emptyOption="true" size="1" />					  
			</tr>
			
			<tr>
				 <td colspan="2" align="right"><input type="submit" value="����"></td>
				 <td colspan="2" align="left">&nbsp;<input type="reset" value="ȡ��"></td>     
					
			</tr>
		</table>
</ww:form>
</div> 
</body>	