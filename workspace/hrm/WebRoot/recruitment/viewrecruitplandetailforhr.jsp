<%@ page contentType="text/html; charset=GBK" language="java"
	pageEncoding="GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@ taglib prefix="ra" uri="/status" %>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<%@ include file="/configuration/log.jsp" %>
<HEAD>
	<!-- 招聘方案详细信息 -->
	<script type="text/javascript" src="../resource/js/jenkov/jenkov_ajaxScript.js"></script>
	<SCRIPT language="javascript" src="../resource/js/validator.js"></SCRIPT>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/recruitment.js"></script>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/applier.js"></script>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/applierSearch.js"></script>
	<script type="text/javascript" src="../dwr/interface/deleteapplier.js"></script>
</HEAD>
<BODY >
	<ww:component template="bodyhead">
			<ww:param name="pagetitle" value="'招聘计划详细信息'" />
	</ww:component>
	<ww:form id="changeDetail" name="changeDetail" action="ViewRecruiplandetailforHR" method="POST" namespace="/recruitment">
		
		<table id="table3" cellSpacing="0" cellPadding="0" width="100%"
			class="listView">
			<tbody>
				<tr>
					<td class="listViewPaginationTdS1">
						<table id="table" cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tbody>
								<tr>
									<td noWrap align="right">						
										<A class="tabDetailViewDFLink" href="SearchRecruitplanForHR.action">返回到招聘计划列表</A>&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td vAlign="top">
						<table id="table" borderColor="#b8c0c4" cellSpacing="0" cellPadding="3"	width="100%" class="detailtable">
							<tbody>
										<tr>
											<ww:hidden name="plan.id" id="planId"></ww:hidden>
											<td width="15%" class="tablefield" nowrap>职位名称：</td>
											<td width="35%"  style="word-break:break-all;"><ww:property value="plan.recpJobTitle.jobtitleName" id="recpJobTitle"/></td>
											<td class="tablefield" nowrap>计划招聘人数：</td>
											<td ><ww:property value="plan.recpNumberExpect" /></td>
										</tr>
										<tr>
											<td width="15%" class="tablefield">职位所属部门：</td>
											<td width="35%" ><ww:property value="plan.recpDepartmentNo.departmentName" /></td>
											<td nowrap class="tablefield" nowrap>实际招聘人数：</td>
											<td nowrap ><ww:property value="plan.factNumber" /></td>
										</tr>
										<tr>
										 	<td width="15%" class="tablefield">工作地区：</td>
											<td width="35%" ><ww:property value="plan.recpWorkLocation.locationName" /></td>
											 <td width="15%" class="tablefield">应聘用工形式：</td>
											<td width="35%" ><ww:property value="plan.recpType.emptypeName" /></td>
										</tr>
										<tr>
											<td class="tablefield" nowrap>学历要求：</td>
											<td ><ww:property value="plan.recpDegree" /></td>
											<td class="tablefield" nowrap>职位发布日期：</td>
											<td id="fuxerenTD" style="word-break:break-all;"><ww:date name="plan.recpStartDate" format="yyyy-MM-dd" /></td>
										</tr>
										<tr>
											<td nowrap class="tablefield" nowrap>语言技能要求：</td>
											<td nowrap ><ww:property value="plan.recpLanguageSkill" /></td>
											<td nowrap class="tablefield" nowrap>截止日期:</td>
											<td nowrap id="classTD"><ww:date name="plan.recpEndDate" format="yyyy-MM-dd" /></td>
										</tr>
										<tr>
											<td class="tablefield" nowrap>职位技能要求：</td>
											<td id="faxTD"><ww:property value="plan.recpJobSkill" /></td>
											<td nowrap class="tablefield" nowrap>计划状态：</td>											
											<td nowrap >
											<ww:property value="getRecpStatus(recpStatus)"/>											
											</td>
										</tr>
										<tr><td nowrap class="tablefield" nowrap>创建时间：</td>
											<td nowrap ><ww:property value="plan.recpCreateBy.empName" />创建于<ww:date name="plan.recpCreateTime" format="yyyy-MM-dd"/></td>
											<td class="tablefield" nowrap>最后修改时间：</td>
											<td ><ww:property value="plan.recpLastChangeBy.empName" />最后修改于<ww:date name="plan.recpLastChangeTime" format="yyyy-MM-dd"/></td>
											
										</tr>
										<tr>
											<td nowrap class="tablefield" nowrap>职位描述：</td>
											<td ><ww:property value="plan.recpJobDesc" escape="false"/></td>
											<td nowrap class="tablefield" nowrap>备注：</td>
											<td><ww:property value="plan.recpComments" /></td>											
										</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
				<td>
					<br>
					<h2 align="center">本方案所有应聘者信息列表</h2>
					<table id="applieresForPlanTable" cellSpacing="0" cellPadding="0" width="100%"  border="0"  class="listView" align="right">	
					<ty:auth auths="601">
					<ww:if test="plan.recpStatus==@com.hr.base.Status@RP_STATUS_HR_APPROVE">
					<input id="addButton" class="button" type="button" onClick="window.location='recruitapplierCreateInit.action?planID=<ww:property value="plan.id" />';" name="createApplier" value="添加应聘者"/>
					</ww:if>
					</ty:auth>
					<tbody>	
						<ww:if test="!applierList.isEmpty()">
							<tr>
								<th>应聘者名称</th><th>应聘工作岗位</th><th>用工形式</th><th>联系电话</th>	<th>学历</th><th>约定时间</th><th>面试人</th><th>状态</th><th>操作</th>
							</tr>		
							
								<ww:iterator value="applierList" status="index">
									 <tr onMouseOver="this.bgColor='#DDF0F8'" onMouseOut="this.bgColor='#ffffff'">
										<ww:hidden id="id" name="id"/>
										<td>										
										<span class="view" onmouseout="hide_detail_applier();" onclick="show_detail_applier('<ww:property value="recaChannel.recchName" />','<ww:property value="recaEmail" />','<ww:property value="recaCreateBy.empName" />','<ww:property value="recaCreateTime" />','<ww:property value="recaLastChangeBy.empName" />','<ww:property value="recaLastChangeTime" />')"><ww:property value="recaName" /></span>
										</td>															
										<td>											
											<ww:property value="recaPlan.recpJobTitle.jobtitleName" />
										</td>										
										<td>										
											<ww:property value="recaPlan.recpType.emptypeName" />
										</td>										
										<td>										
											<ww:property value="recaPhone" />
										</td>										
										<td>
											<ww:property value="recaDiploma" />					
										</td>										
										<td>																					
											<ww:property value="recaInterviewTime"/>											
										</td>
										<td>
										<ww:property value="recaInterviewer"/>
										</td>
											<td align="center" onmouseout="hideLog();">							
												<a href="#" class="listViewTdLinkS1" onclick="LogShowDiv('<ww:property value="id"/>','recruitapplier',this);">	
												<ra:applier><ww:property value="recaStatus"/> </ra:applier>				
													
												</a>
											  		 <ty:auth auths="601">
												     <input type="hidden" id='recaComment<ww:property value="%{#index.index}"/>' name='recaComment<ww:property value="%{#index.index}"/>' value='<ww:property value="recaComment"/>'/>
												    <img id="img_status" src="../resource/images/unpublish_inline.gif" status="<ww:property value='recaStatus'/>" onclick="show_remark_bishi('<ww:property value="recaStatus"/>','recaComment<ww:property value="%{#index.index}"/>','<ww:property value="id"/>','<ww:property value="recaName" />');" alt="更改状态"/>
											   		</ty:auth>
												</td>
										
										<td align="center">
											<span class="view"><img src="../resource/images/edit.gif" onclick="editApplier_detail_hr('<ww:property value="id"/>')" alt="更新"/></span>
											<span class="view"><img src="../resource/images/delete.gif" onclick="deleteApplier_detail_hr('<ww:property value="id"/>')" alt="删除"/></span>
										    <ww:if test="recaAttachmentName!=null && recaAttachmentName!=''">  
											<a title="下载简历:<ww:property value='recaAttachmentName'/>" href="recruitapplierDownFile.action?fileName=<ww:property value='recaAttachmentName'/>"><img src="../resource/images/attachment.gif"/></a>
											</ww:if>
											<ww:else>
											<img src="../resource/images/blankSpace.gif"/>
											</ww:else>
										</td>
									</tr>
				
								</ww:iterator>
							 </ww:if>
									<ww:else>
									<tr><td align="center"> 没有符合条件的应聘者!</td> </tr>
									</ww:else>
								  </tbody>
							   </table>		

				</td>
				</tr>


			</tbody>
		</table>		
		
	</ww:form>
			<div id="remark_bishi" title="调整应聘者状态">
				<div id="change_stutus_error" class="prompt_div_err"></div>	
					<ww:form id="remark_form_bishi" name="remark_form_bishi" namespace="/recruitment" method="POST" >	
					<ww:hidden id="applierID_bishi" name="applierID_bishi" ></ww:hidden>
					<ww:hidden id="applierName_bishi" name="applierName_bishi" ></ww:hidden>
					<ww:hidden id="applierStatus_bishi"  name="applierStatus_bishi"></ww:hidden>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class ="prompt_div_body" >
						<tr >
							  <td align="right" width="20%">新状态<font color="red">*</font>：</td>							  
							  <td width="70%" style="color:red;">	  
							  		<ww:select  id="StatusID" name="applier_recaStatus" list="applierStatus"
									listKey="id.statusconfNo" listValue="statusconfDesc"  value="recaStatus" multiple="false" emptyOption="false"  onchange="applierStatusChange(this.value);" />
						      </td>
						</tr>
					</table>
					<table  width="100%" border="0" cellspacing="0" cellpadding="0"   id="interviewDate_bishi_table" class ="prompt_div_body">
						<tr>
							<td id="interviewDate_bishi_title" width="20%" align="right"></td>
							<td width="70%" align="left" style="color: red">
							<ww:textfield id="interviewDate_bishi" name="interviewDate_bishi" required="true" size="21"  />
						    <img onclick="WdatePicker({el:'interviewDate_bishi',dateFmt:'yyyy-MM-dd HH:mm'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">								
						    </td>
							
						</tr>
						
						<tr>
							<td id="recaInterviewer_bishi_title" width="20%" align="right" NOWRAP></td>
							<td width="70%" align="left" > <ww:textfield  id="recaInterviewer_bishi"  name="recaInterviewer_bishi" size="20"  maxlength="32"/></td>
							
						</tr>
					</table>
					
					<table  width="100%" border="0" cellspacing="0" cellpadding="0"   class ="prompt_div_body">
						<tr>
						<td id="common_title" width="20%" align="right" NOWRAP>备注：</td>
						<td width="70%" align="left" > 	<ww:textarea id="common_id_bishi"  name="common_id_bishi"  cols="30"  rows="5" onkeypress="MKeyTextLength(this,128);"/></td>
						</tr>						
						<tr align="center">
						<td colspan="2">
							<input type="button" onclick="statusCheck(applierStatus_bishi.value);" value=" 更新 ">&nbsp;							
							<input type="button" onclick="hrm.common.closeDialog('remark_bishi');" value=" 关闭 ">
						</td>
						</tr>
					</table>
							
						</ww:form>							
					
			<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;" frameborder="0" ></iframe>
		</div>   
				
 
	  	<div id='detail_applier'  align="left" style="position:absolute;left:300px;top:240px;z-index:2;solid; width:650px;display:none;"  class="prompt_div_inline" >													  			
				<table id="detail_table" width="100%" border="0" cellspacing="0" cellpadding="0"  class="gridview">			
							  <tr >
								   <td align="left"> 
								   </td>					  
								   
								  <td  align="left">
								   </td>
							 </tr> 				 
							 <tr>
								<td align="left">
								 </td>		
								
								 <td align="left">
								 </td>
							 </tr> 
							</table>	
			</div>  
	
	

		
		<script type="text/javascript" language="javascript">
  		  		hrm.common.initDialog('remark_bishi',360);  		  		
		</script>

</BODY>			 