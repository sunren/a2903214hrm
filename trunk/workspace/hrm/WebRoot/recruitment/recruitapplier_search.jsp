<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@ taglib prefix="ra" uri="/status" %>
<%@ include file="/configuration/log.jsp" %>
<%@ include file="planDetail_div.jsp" %>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<head>
	<title>�����˹����б�</title>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/applier.js"></script>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/applierSearch.js"></script>
	<script type="text/javascript" src="../resource/js/validator.js"></script>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/recruitment.js"></script>
	<script type="text/javascript" src="../resource/js/tmp.js"></script>
	

		
		
</head>
<body>
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'ӦƸ�߹���'" />		
		<ww:param name="helpUrl" value="'45'" />
	</ww:component>
	<ww:form  id="recruitapplierSearch" name="recruitapplierSearch" action="recruitapplierSearch" namespace="/recruitment" method="POST">
	<ww:token />
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
						<tr>
							<td>
									<ww:hidden id="order" name="page.order" />
									<ww:hidden id="operate" name="page.operate" />						               
									<table width="100%" b="0" cellspacing="0" cellpadding="0">
										<tr>										
											<ww:select label="ӦƸ����" id="applierDepartment" name="applier.recaPlan.recpDepartmentNo.id" list="allDepartment"
												listKey="id" listValue="departmentName" multiple="false" headerKey="" emptyOption="true" />
											<ww:select label="ӦƸ������λ" list="jobTitles" listKey="jobtitleNo" listValue="jobtitleName" emptyOption="true" name="applier.recaPlan.recpJobTitle.jobtitleNo"></ww:select>
											<ww:textfield label="ӦƸ������" id="applierName" name="applier.recaName" size="10" maxlength="10" />
				
											<ww:select label="ӦƸ��״̬" id="applierStatus" name="applier.recaStatus" list="applierStatus"
												listKey="id.statusconfNo" listValue="statusconfDesc" value="applier.recaStatus"   multiple="false" emptyOption="true" />
										</tr>				
									</table>
							</td>
							<td>
									<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"   value="��ѯ">
									<input title="[Alt+C]" accesskey="C" class="button" type="button" onclick="window.location='recruitapplierSearch.action';"  value="����">
									<br>
							</td>
						</tr>
				</table>
	<br />
		<input id="addButton"class="button" type="button"	onClick="window.location='recruitapplierCreateInit.action';" name="createApplier" value="���������">


		<table id="recruitAppliertable" cellpadding="0" cellspacing="0"	width="100%" border="0" class="gridtableList" >

			<ww:if test="page.isSplit()">
				<tr>
					<td colspan="10" align="right" class="listViewPaginationTdS1">
						<ww:hidden name="page.currentPage" />
						<a href="#" onclick="splits_recruitment('first','recruitapplierSearch');"><img src='../resource/images/start.gif' width='11' height='9' alt='��ʼ'>��ʼ</a>
						<a href="#" onclick="splits_recruitment('previous','recruitapplierSearch');"><img src='../resource/images/previous.gif' width='6' height='9' alt='��ҳ'>��ҳ</a>
						��
						<ww:property value="page.currentPage+'/'+page.totalPages" />
						ҳ����
						<ww:property value="page.totalRows" />
						����
						<a href="#" onclick="splits_recruitment('next','recruitapplierSearch');">��ҳ<img src='../resource/images/next.gif' width='6' height='9'></a>
						<a href="#" onclick="splits_recruitment('last','recruitapplierSearch');">���<img src='../resource/images/end.gif' width='11' height='9' alt='���'>	</a>
					</td>
				</tr>
			</ww:if>
			
			<tr>
				<th><a href="#" onclick="order_submit_recruitment('recaName','recruitapplierSearch');">ӦƸ������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recaName_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recaPlan.recpJobTitle','recruitapplierSearch');">ӦƸ������λ</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recaPlan.recpJobTitle_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recaPlan.recpType.emptypeName','recruitapplierSearch');">�ù���ʽ</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recaPlan.recpType.emptypeName_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recaPhone','recruitapplierSearch');">��ϵ�绰</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recaPhone_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recaDiploma','recruitapplierSearch');">ѧ��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recaDiploma_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recaInterviewTime','recruitapplierSearch');">Լ��ʱ��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recaInterviewTime_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recaInterviewer','recruitapplierSearch');">������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recaInterviewer_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recaCreateTime','recruitapplierSearch');">����ʱ��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recaCreateTime_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recaStatus','recruitapplierSearch');">״̬</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recaStatus_img'></th>
				<th>����</th>
			</tr>
			<ww:if test="!applierList.isEmpty()">
				<ww:iterator value="applierList" status="index">
					  <tr >
						<ww:hidden id="id" name="id"/>
						<td >
						<a href="#" class="listViewTdLinkS1" onclick="show_detail_applier('<ww:property value="recaChannel.recchName" />','<ww:property value="recaEmail" />','<ww:property value="recaCreateBy.empName" />','<ww:property value="recaCreateTime" />','<ww:property value="recaLastChangeBy.empName" />','<ww:property value="recaLastChangeTime" />');" onmouseout="hide_detail_applier()"><ww:property value="recaName" /></a>
						</td>			
						<td >
							<span class="view" onclick="showplanDetailInSP(
													'<ww:property value="recaPlan.recpJobTitle.jobtitleName"/>',
													'<ww:property value="recaPlan.recpNumberExpect" />',
													'<ww:property value="recaPlan.recpDepartmentNo.departmentName" />',
													'<ww:property value="recaPlan.recpNumberActual" />',
													'<ww:property value="recaPlan.recpWorkLocation.locationName" />',
													'<ww:property value="recaPlan.recpType.emptypeName" />',
													'<ww:property value="recaPlan.recpDegree" />',
													'<ww:date name="recaPlan.recpStartDate" format="yyyy-MM-dd" />',
													'<ww:property value="recaPlan.recpLanguageSkill" />',
													'<ww:date name="recaPlan.recpEndDate" format="yyyy-MM-dd" />',
													'<ww:property value="recaPlan.recpJobSkill" />',
													'<ww:property value="recaPlan.recpStatus"/>',
													'<ww:date name="recaPlan.recpCreateTime" format="yyyy-MM-dd"/>',
													'<ww:date name="recaPlan.recpLastChangeTime" format="yyyy-MM-dd"/>',
													'<ww:property value="recaPlan.recpJobDesc" escape="false"/>',
													'<ww:property value="recaPlan.recpComments" />',
														'planDetail');">																
																 <ww:property value="recaPlan.recpJobTitle.jobtitleName" /></span>
						</td>
						<td><ww:property value="recaPlan.recpType.emptypeName" /></td>
						
						<td><ww:property value="recaPhone" /></td>
						<td><ww:if test="recaDiploma!=null">
							<ww:property value="recaDiploma"/>	
							</ww:if>
						    <ww:else>
						     ��Ҫ��
						    </ww:else>											
						</td>
						
						<td><ww:property value="recaInterviewTime"/></td>
						<td><ww:property value="recaInterviewer"/></td>	
						<td><ww:property value="recaCreateTime"/></td>					
						<td align="center" onmouseout="hideLog();">							
						<span class="view" onclick="LogShowDiv('<ww:property value="id"/>','recruitapplier',this);">					
								<ra:applier><ww:property value="recaStatus"/> </ra:applier>
						</span>
							<ty:auth auths="601 ">
						     <input type="hidden" id='recaComment<ww:property value="%{#index.index}"/>' name='recaComment<ww:property value="%{#index.index}"/>' value='<ww:property value="recaComment"/>'/>
						    <img id="img_status" src="../resource/images/unpublish_inline.gif" status="<ww:property value='recaStatus'/>" onclick="show_remark_bishi('<ww:property value="recaStatus"/>','recaComment<ww:property value="%{#index.index}"/>','<ww:property value="id"/>','<ww:property value="recaName" />');" alt="����״̬"/>
					   		
					   		</ty:auth>
						</td>
						
						<td align="center">
						<a href="#">	<img src="../resource/images/edit.gif" onclick="editApplier('<ww:property value="id"/>');"alt="����"/></a>
						<a href="#">	<img src="../resource/images/delete.gif" onclick="deleteApplier('<ww:property value="id"/>');" alt="ɾ��"/></a>
					
							<ww:if test="recaAttachmentName!=null && recaAttachmentName!=''">  
							<a title="���ؼ���:<ww:property value='recaAttachmentName'/>" href="recruitapplierDownFile.action?fileName=<ww:property value='recaAttachmentName'/>"><img src="../resource/images/attachment.gif"/></a>
							</ww:if>
							<ww:else>
							<img src="../resource/images/blankSpace.gif"/>
							</ww:else>
						

						</td>
					</tr>

				</ww:iterator>
			</ww:if>
			<ww:else>
				<tr>
					<td align="center" colspan="9">
						�����ڷ���������������!
					</td>
				</tr>
			</ww:else>
		</table>
	
	</ww:form>	
	<div id="tmpletId" style="DISPLAY: none"><img src="../resource/images/basic_search.gif" onload="check_order();"/></div>
			<div id="remark_bishi" title="����ӦƸ��״̬">
					<div id="change_stutus_error" class="prompt_div_err"></div>	
						<ww:form id="remark_form_bishi"  namespace="/recruitment" method="POST" >	
						<ww:hidden id="applierID_bishi" name="applierID_bishi" ></ww:hidden>
						<ww:hidden id="applierName_bishi" name="applierName_bishi" ></ww:hidden>
						<ww:hidden id="applierStatus_bishi"  name="applierStatus_bishi"></ww:hidden>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class ="prompt_div_body" >
							<tr >
								  <td align="right" width="20%">��״̬<font color="red">*</font>��</td>							  
								  <td width="70%" style="color:red;">	  
								  		<ww:select  id="StatusID" name="applier_recaStatus" list="applierStatus"
										listKey="id.statusconfNo" listValue="statusconfDesc"  value="recaStatus" multiple="false" emptyOption="false"  onchange="applierStatusChange(this.value);" />
							      </td>
							</tr>
						</table>
						<table  width="100%" border="0" cellspacing="0" cellpadding="0"   id="interviewDate_bishi_table" class ="prompt_div_body">
							<tr>
								<td id="interviewDate_bishi_title" width="20%" align="right"></td>
								<td width="70%" align="left" style="color: red"><jscalendar:jscalendar	id="interviewDate_bishi" name="interviewDate_bishi" format="%Y-%m-%d %H:%M" required="true" size="20" showstime="true" /></td>
								
							</tr>
							
							<tr>
								<td id="recaInterviewer_bishi_title" width="20%" align="right" NOWRAP></td>
								<td width="70%" align="left" > <ww:textfield  id="recaInterviewer_bishi"  name="recaInterviewer_bishi" size="20"  maxlength="32"/></td>
								
							</tr>
						</table>
						
						<table  width="100%" border="0" cellspacing="0" cellpadding="0"   class ="prompt_div_body">
							<tr>
							<td id="common_title" width="20%" align="right" NOWRAP>��ע��</td>
							<td width="70%" align="left" > 	<ww:textarea id="common_id_bishi"  name="common_id_bishi"  cols="30"  rows="5" onkeypress="MKeyTextLength(this,128);"/></td>
							</tr>						
							<tr align="center">
							<td colspan="2">
								<input type="button" onclick="statusCheck(applierStatus_bishi.value);" value=" ���� ">&nbsp;							
								<input type="button" onclick="hrm.common.closeDialog('remark_bishi');" value=" �ر� ">
							</td>
							</tr>
						</table>
								
							</ww:form>							
						
				<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;" frameborder="0" ></iframe>
			</div>  	  		  		
	     	<div id='detail_applier'  align="left" style="position:absolute;left:300px;top:240px;z-index:2;solid; width:650px;display:none;"  class="prompt_div_inline" >													  			
				<TABLE id="detail_table" width="100%" border="0" cellspacing="0" cellpadding="0"  class="gridview">			
							  <tr><td align="left"></td><td align="left"></td></tr> 				 
							 <tr><td align="left"></td><td align="left"></td></tr> 
				</table></div>
		<script type="text/javascript" language="javascript">
  		  	hrm.common.initDialog('remark_bishi',360);  		  	
  		  	hrm.common.initDialog('planDetail',650);
		</script>
	
			    
</body>	