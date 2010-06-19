<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>

<head>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/search.js"></script>
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
	<ww:form id="updateApplier" name="updateApplier" action="recruitapplierUpdate" namespace="/recruitment"  method="POST" enctype="multipart/form-data">
		 <ww:token />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">				
			<ww:hidden name="applier.recaCreateTime"></ww:hidden>
			<ww:hidden name="applier.id"></ww:hidden>	
			<tr>						
			<td width="10%" align="right">招聘计划<font color="red">*</font>：</td><td width="20%" align="left" >
			<ww:select list="jobTitles" listKey="jobtitleNo" listValue="jobtitleName" emptyOption="true"  id="plan_job_title"  name="applier.recaPlan.recpJobTitle.jobtitleNo"></ww:select>
			</td>
			<ww:hidden id="plan_id" name="applier.recaPlan.id" />
			<ww:hidden name="planID"/>	
			<ww:file label="简历附件"  id="attachment"  name="file" accept="text/doc,text/xls,text/pdf,text/htm,text/html,text/zip,text/rar" onchange="file_check();"/><td>
			<ww:if test="applier.recaAttachmentName!=null">
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
			
<div id="choosePlan_div" style="position:absolute;z-index:2;top:100px;left:100;solid; width:460px;display:none;" >	
	  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	  		<tr>
	  			<td>
	  	 				<div id="remark_hand" style="CURSOR: move" NOWRAP class="prompt_div_head">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  	 						 <TR>
							        <td colspan="5" align="center" >
							       	  计划名称查询：<input type="text" id="searchCondition" name="searchCondition" onkeypress="searchConditionKeypress();"/>
							          <input type="button" id="btn_searchCondition" onclick="RecruitapplierCreateInit.chooseRecruitplanList(document.getElementById('searchCondition').value,chooseRecruitplanBack)" value=" 查询 "/>
							        </td>
							        <td align="right" valign="middle"><img src="../resource/images/close_div.gif" onclick="Element.hide('choosePlan_div');" style="CURSOR: pointer"/></td>
						        </TR>	
						      </table>
						   </div>	
	  			</td>
	  		</tr>
			 <tr>
				 <td>	
						 <table width="100%"  border="0" cellspacing="0" cellpadding="0" class="prompt_div_body" >							      						
								<TR>
										<TD id="planID_div" class="tablefield" nowrap="nowrap" width="50"  >
											编号
										</TD>
										<TD id="jobTitle_div" class="tablefield" nowrap="nowrap" width="100" >
											计划名称
										</TD>
										<TD  id="department_div" class="tablefield" nowrap="nowrap" width="100" >
										    部门
										</TD>
										<TD  id="num_div" class="tablefield" nowrap="nowrap" width="60" >
											招聘人数
										</TD>
										<TD id="date_div" class="tablefield" nowrap="nowrap" width="100" >
											职位发布日期
										</TD>
										<TD class="tablefield" nowrap="nowrap" width="10" >
											&nbsp;&nbsp;
										</TD>																										
								</TR>
						</table>
				</td>
			</tr>	
			<tr>
				<td>	
						<div id="content"  align="left" style="overflow:auto; border:1px #dddddd solid;height: 80px "  >
							<table width="100%"  border="0" cellspacing="0" cellpadding="0"  class="prompt_div_body" >
							 <tbody id="items">
								<ww:if test="!RecruitplanList.isEmpty()">
									<ww:iterator value="RecruitplanList" status="index" >		
											<TR id='<ww:property value="%{'tr'+(#index.index)}"/>' onclick="choosePlan('<ww:property value="id"/>','<ww:property value="recpJobTitle"/>');">															
											<td class="tablefield" nowrap="nowrap" width="50">	<ww:property value="id"/></td>
											<td class="tablefield" nowrap="nowrap" width="100">	<ww:property value="recpJobTitle.jobtitleName"/></td>
											<td class="tablefield" nowrap="nowrap" width="100">	<ww:property value="recpDepartmentNo.departmentName"/></td>
											<td class="tablefield" nowrap="nowrap" width="60">	<ww:property value="recpNumberExpect"/></td>
											<td class="tablefield" nowrap="nowrap" width="100">	<ww:date name="recpStartDate" format="yyyy-MM-dd"/></td>										
											</TR>
									</ww:iterator>
								</ww:if>
								</tbody>			
							</table>
						</div> 
				</td>
			</tr>
		</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight);top:0px;left:0px;" frameborder="0" ></iframe>
	</div>
</ww:form>
</div> 
</body>	