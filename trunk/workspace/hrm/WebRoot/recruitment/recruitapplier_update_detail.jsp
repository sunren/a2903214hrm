<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>

<head>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/search.js"></script>
<script language="javascript" src="../resource/js/validator.js"></script>
<script type="text/javascript" src="../resource/js/recruitment/applier.js"></script>
<script type="text/javascript" src="../resource/js/recruitment/applierCreate.js"></script>
<!-- dwr 选择招聘计划 -->
	<script type="text/javascript" src="../dwr/interface/RecruitapplierCreateInit.js"></script>
<title>更新应聘者信息</title>
</head>
<body onload="checkCreatStatus(document.getElementById('applierStatus').value)">
	<div id="update_applier" align="left">	
		<ww:component template="bodyhead">
			<ww:param name="pagetitle" value="'更新应聘者'" />
			<ww:param name="helpUrl" value="'45'" />
		</ww:component>	
	<ww:form id="updateApplier" name="updateApplier" action="recruitapplierUpdateDetail" namespace="/recruitment"  method="POST" enctype="multipart/form-data">
		 <ww:token />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">			
			<ww:hidden name="applier.recaCreateTime"></ww:hidden>
			<ww:hidden name="applier.id"></ww:hidden>
			<ww:hidden id="flagFrom" name="flagFrom"/>			
			<tr>						
			<td width="10%" align="right">招聘计划<font color="red">*</font>：</td><td width="20%" align="left" >	<ww:textfield  id="plan_job_title"  name="applier.recaPlan.recpNo" size="20" readonly="true" maxlength="32" required="true" /> </td>
			<ww:hidden id="plan_id" name="applier.recaPlan.id" />	
			<ww:hidden id="id" name="id" value="%{applier.recaPlan.id}"/>	<!-- 招聘计划的id -->	
			<ww:hidden name="planID"/>	
			<ww:file label="简历附件"  id="attachment"  name="file" accept="text/doc,text/xls,text/pdf,text/htm,text/html,text/zip,text/rar" onchange="file_check();"/>
			<td><ww:if test="applier.recaAttachmentName!=null">
					<a title="下载附件:<ww:property value='applier.recaAttachmentName'/>" href="recruitapplierDownFile.action?fileName=<ww:property value='applier.recaAttachmentName'/>">
						<img src="../resource/images/attachment.gif"/>
						原附件：<ww:property value="applier.recaAttachmentName" />
					</a>
				</ww:if>
			</td>
			</tr>
			
			<tr>
				<ww:select label="招聘渠道" name="applier.recaChannel.id" list="RecruitchannelList" listKey="id" required="true" listValue="recchName" multiple="false" emptyOption="true" value="applier.recaChannel.id" size="1" />
				<ww:select label="应聘者状态" id="applierStatus" name="applier.recaStatus" list="applierStatus" listKey="id.statusconfNo" listValue="statusconfDesc" multiple="false" emptyOption="true"  onchange="checkCreatStatus(this.value);"/>						
			</tr>
				
			<tr>
						 <ww:textfield label="姓名" id="recaName" name="applier.recaName" size="20" required="true" maxlength="32"/>
						<td align="right">笔试/面试时间:</td>
					    <td><ww:textfield id="interviewDate" name="applier.recaInterviewTime" required="true" size="21"  />
					    <img onclick="WdatePicker({el:'interviewDate',dateFmt:'yyyy-MM-dd HH:mm'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">								
					    </td> 
			</tr>
			
			<tr>
						<ww:textfield label="电话" id="recaPhone" name="applier.recaPhone" size="20" required="true" maxlength="16" onkeypress="checkphone();"/>
						<ww:textfield id="interviewer" name="applier.recaInterviewer" label="笔试/面试人" size="20"  maxlength="32" />
			</tr>

			<tr>		
			<ww:hidden name="applier.recaCreateBy.id"/>
			<ww:hidden name="applier.recaAttachmentName"/>
			<ww:hidden name="applier.recaLastChangeBy.id" />		
			<tr >
						<ww:textfield label="邮箱地址" name="applier.recaEmail" size="20" required="true" maxlength="64"  onkeypress="checkemail();"/>
						<td align="right" rowspan="2">备注：</td>	 <td rowspan="2"><ww:textarea name="applier.recaComment" cols="30" rows="3" onkeypress="MKeyTextLength(this,128);" /></td>
			</tr>
			
			<tr>
						<ww:select label="学历" name="applier.recaDiploma"   multiple="false"  list="@com.hr.base.ComonBeans@getEmpDiploma()" emptyOption="true" size="1" />					  
			</tr>
			
			<tr>
				 <td colspan="2" align="right"><input type="submit" value="更新"></td>
				 <td colspan="2" align="left">&nbsp;<input type="reset" value="取消"></td>     
					
			</tr>
		</table>
</ww:form>
</div> 
</body>	