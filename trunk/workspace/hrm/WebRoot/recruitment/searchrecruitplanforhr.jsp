<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax" %>
<%@ include file="/configuration/log.jsp" %>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<head>
<title>��Ƹ�ƻ��б�</title>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/search.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/recruitment.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/searchrecruitment.js"></script>
<script type="text/javascript" src="../resource/js/validor.js"></script>
<script type="text/javascript" src="../resource/js/tmp.js"></script>
</head>
<body>
<ww:component template="bodyhead">
	<ww:param name="pagetitle" value="'��Ƹ�ƻ��б�'" />
	<ww:param name="helpUrl" value="'44'" />
</ww:component>
<ww:form id="SearchRecruitplanForHR" name="SearchRecruitplanForHR" action="SearchRecruitplanForHR" namespace="/recruitment" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
<tr>
<td>
	<ww:hidden id="order" name="page.order"/>
	<ww:hidden id="operate" name="page.operate"/>
 <!--<ww:hidden  id="idfordetail" name="id" />  -->  
	<div id="basic" style="DISPLAY: block;clear:both">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<ww:select label="ְλ����" list="jobTitles" listKey="jobtitleNo" listValue="jobtitleName" name="plan.recpJobTitle.jobtitleNo" emptyOption="true"/>
				<ww:select label="ְλ��������" name="plan.recpDepartmentNo.id" list="allDept" listKey="id"
					 listValue="departmentName" multiple="false" headerKey="" headerValue="��ѡ��" />
				<ww:select label="��������" name="plan.recpWorkLocation.id" list="allLocation" listKey="id"
					 listValue="locationName" multiple="false" headerKey="" headerValue="��ѡ��" />	
				<ww:select id="recpStatus" label="�ƻ�״̬" name="plan.recpStatus" list="allStatus" listKey="id.statusconfNo" listValue="statusconfDesc" emptyOption="true" multiple="false" />
				
		</tr>	
		<tr>		
		 <td align="right">��ʾ��ʷ��¼��</td>
		 <td><input type="checkbox" id="viewAll" <ww:if test='viewAll!=null'>checked</ww:if> name="viewAll"  value="allList" class="checkbox"/></td>	 			
		 <td colspan="6"></td>
		</tr>	
		</table>
	</div>
	<div id="advanced" style="DISPLAY: none;clear:both">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<ww:select label="ְλ����" list="jobTitles" listKey="jobtitleNo" listValue="jobtitleName" name="plan.recpJobTitle.jobtitleNo" emptyOption="true"/>		
				<ww:select label="ְλ��������" name="plan.recpDepartmentNo.id" list="allDept" listKey="id"
					 listValue="departmentName" multiple="false" headerKey="" headerValue="��ѡ��" />
				<ww:select label="��������" name="plan.recpWorkLocation.id" list="allLocation" listKey="id"
					 listValue="locationName" multiple="false" headerKey="" headerValue="��ѡ��" />
			</tr>
			<tr>
					<td  align="right" ><ww:text name='ְλ�������ڷ�Χ'/>��</td>
	                	<td nowrap>
	                       <jscalendar:jscalendar id="startDate"  name="plan.recpStartDate" format="%Y-%m-%d"  />
	                        <span><!--��--><ww:text name='desc.base.to'/> </span>   
							<jscalendar:jscalendar id="endDate" name="plan.recpEndDate" format="%Y-%m-%d" />
	                	</td>
					<ww:select id="recruitplan" label="ѧ��Ҫ��"name="plan.recpDegree"  list="@com.hr.base.ComonBeans@getEmpDiploma()" emptyOption="true" />
			</tr>
			<tr>
				
					    <ww:textfield id="recpLanguageSkill" label="���Լ���Ҫ��"name="plan.recpLanguageSkill" size="9" maxlength="9" />
					    <ww:textfield id="recpJobSkill" label="ְλ����Ҫ��"name="plan.recpJobSkill" size="9" maxlength="9" />
					   <ww:select id="recpStatus" label="�ƻ�״̬"	name="plan.recpStatus"  list="#{'':'��ѡ��',1:'�ݸ�',2:'���ύ',11:'��������',12:'HR����',21:'�Ѿܾ�',22:'������',23:'����ֹ',31:'�ѹر�'}"  />
									
			</tr>					
		</table>
	</div>
<td>
	<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit" onclick="search_check('basic','advanced');" name="button2" value="��ѯ"> 
	<input title="[Alt+C]" accesskey="C" class="button" type="button" value="����" onClick="window.location='SearchRecruitplanForHR.action';">
	<div id="t1" align="center" style="DISPLAY: block;clear : both">
		<img src="../resource/images/basic_search.gif" width="8" height="8">
		<a href="#" class=utilsLink onClick="showHide('basic','advanced','t1','t2');">�߼���ѯ</a>
	</div>				
	<div id="t2" align="center" style="DISPLAY: none;clear : both">
		<img src="../resource/images/advanced_search.gif" width="8" height="8">
		<a href="#" class=utilsLink onClick="showHide('basic','advanced','t1','t2');">��ͨ��ѯ</a>
	</div>				
</td>
</tr>
</table>
<p>&nbsp;</p> 
<table id="recruitplantable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">	
	<ww:if test="page.isSplit()">
	     <tr>
	     	<td colspan="10" align="right"  class="listViewPaginationTdS1">
	     			<ww:hidden name="page.currentPage"/>
                    <a href="#" onclick="splits_searchPlanForhr('first');"><img src='../resource/images/start.gif' width='11' height='9' alt='��ʼ'>��ʼ</a>
                    <a href="#" onclick="splits_searchPlanForhr('previous');"><img src='../resource/images/previous.gif' width='6' height='9' alt='��ҳ'>��ҳ</a>
                       ��<ww:property value="page.currentPage+'/'+page.totalPages"/>ҳ����<ww:property value="page.totalRows"/>����
                    <a href="#" onclick="splits_searchPlanForhr('next');">��ҳ<img src='../resource/images/next.gif' width='6' height='9'></a>
                    <a href="#" onclick="splits_searchPlanForhr('last');">���<img src='../resource/images/end.gif' width='11' height='9' alt='���'></a>
			</td>
	     </tr>
	</ww:if>     
	      <tr>
	      		<th><a href="#" onclick="order_submitforHR('recpNo');">���</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpNo_img'></th>
				<th><a href="#" onclick="order_submitforHR('recpDepartmentNo.id');">��������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpDepartmentNo.id_img'></th>
				<th><a href="#" onclick="order_submitforHR('recpWorkLocation.id');">��������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpWorkLocation.id_img'></th>
				<th><a href="#" onclick="order_submitforHR('recpJobTitle.jobtitleName');">ְλ����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpJobTitle.jobtitleName_img'></th>
				<th><a href="#" onclick="order_submitforHR('recpNumberExpect');">����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpNumberExpect_img'></th>
				<th><a href="#" onclick="order_submitforHR('recpStartDate');">ְλ��������</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpStartDate_img'></th>
				<th><a href="#" onclick="order_submitforHR('recpEndDate');">ְλ�ر�����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpEndDate_img'></th>
				<th><a href="#" onclick="order_submitforHR('recpLastChangeTime');">����޸�ʱ��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpLastChangeTime_img'>	</th>
				<th><a href="#" onclick="order_submitforHR('recpStatus');">״̬</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpStatus_img'></th>
				<th>����</th>				
			</tr>
	     <ww:if test="!recruitplanList.isEmpty()">
	     	<ww:iterator value="recruitplanList" status="index">
	     		 <tr>
		     		<td>
			     		<span class="view" onclick="viewRecruitplanDetailforHR('<ww:property value="id"/>')">
			     		<ww:property value="recpNo" />
						</span>
					</td>
					<td><ww:property value="recpDepartmentNo.departmentName" /></td>
					<td><ww:property value="recpWorkLocation.locationName" /></td>
					<td><ww:property value="recpJobTitle.jobtitleName" /></td>
					<td><ww:property value="recpNumberExpect" /></td>  
					<td><ww:date name="recpStartDate" format="yyyy-MM-dd"/></td>  
					<td><ww:date name="recpEndDate" format="yyyy-MM-dd"/></td> 
					<td><ww:date name="recpLastChangeTime" format="yyyy-MM-dd"/>									
					<td onMouseOut="hideLog();"><span class="view" onclick="LogShowDiv('<ww:property value="id" />','recruitplan',this);"><ww:property value="getRecpStatus(recpStatus)"/></span></td>						
			<td align="center">
			<ty:auth auths="601">
				<ww:if test="recpStatus ==@com.hr.base.Status@RP_STATUS_DRAFT||recpStatus ==@com.hr.base.Status@RP_STATUS_SUBMIT">
					<span class="view" onclick=deleteRecruitplanADM("<ww:property value='id'/>")>
						<img src="../resource/images/delete.gif" alt='ɾ��' border='0' /></span>
				</ww:if>
				<ww:else>
					<img src="../resource/images/blank.gif"  border='0' />
				</ww:else>
				<ww:if test="recpStatus ==@com.hr.base.Status@RP_STATUS_HR_APPROVE">
					<span class="view" onclick=closeRecruitplan("<ww:property value='id'/>")>	<img src="../resource/images/close.gif" alt='�ر�'  border='0' /></span>
					<a href="UpdateRecruitplanInitADM.action?id=<ww:property value='id'/>"/><img src="../resource/images/edit.gif" alt='�޸�' border='0' /></a>
					
				</ww:if>
			</ty:auth>
			</td>		
			
	     	</tr>
	     	</ww:iterator>
	     </ww:if>
	     <ww:else>
				<tr>
					<!-- �����ڷ�����������Ƹ�ƻ��� -->
					<td align="center" colspan="11">
						�����ڷ�����������Ƹ�ƻ�!
					</td>
				</tr>
			</ww:else>
</table>
<div id="tmpletId" style="DISPLAY: none"><img src="../resource/images/basic_search.gif" onload="check_showHide();check_order();"/></div>
</ww:form>


</body>	