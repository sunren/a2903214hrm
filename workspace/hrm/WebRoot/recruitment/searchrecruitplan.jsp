<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax"%>
<%@ include file="/configuration/log.jsp" %>
<%@ include file="planDetail_div.jsp" %>
<head>
	<title>我的招聘计划</title>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/search.js"></script>
	<script type="text/javascript" src="../resource/js/validor.js"></script>
		<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/applier.js"></script>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/recruitment.js"></script>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/recruitment/searchrecruitment.js"></script>
	<script type="text/javascript" src="../resource/js/validator.js"></script>
	<!-- dwr提交 -->
	<script type="text/javascript" src="../dwr/interface/SearchRecruitplan.js"></script>
</head>
<body>

	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'我的招聘计划'" />
		<ww:param name="helpUrl" value="'40'" />
	</ww:component>

	<ww:form id="searchrecruitplan" name="searchrecruitplan" action="SearchRecruitplan" namespace="/recruitment" method="POST">
		<% %>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
			
					<td>
						<ww:hidden id="order" name="page.order" />
						<ww:hidden id="operate" name="page.operate" />
	                    <ww:hidden  id="idfordetail" name="id" />
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<ww:select label="岗位" list="jobTitles" listKey="jobtitleNo" listValue="jobtitleName" emptyOption="true" name="plan.recpJobTitle.jobtitleNo"></ww:select>
								<ww:select label="工作地区" id="recpWorkLocation" name="plan.recpWorkLocation.id" list="allLocation" listKey="id" listValue="locationName" multiple="false" headerKey="" emptyOption="true" />
								<ww:select id="recpStatus" label="计划状态" name="plan.recpStatus" list="allStatus" listKey="id.statusconfNo" listValue="statusconfDesc" emptyOption="true" multiple="false" />
								<td> 显示历史纪录：<input type="checkbox" id="viewAll" <ww:if test='viewAll!=null'>checked</ww:if> name="viewAll"  value="allList" class="checkbox"/></td>	 			
							
							</tr>
						</table>
					</td>
					<td>
						<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"   value="查询">
						<input title="[Alt+C]" accesskey="C" class="button" type="button" value="重置" onClick="window.location='SearchRecruitplan.action';">
						<br> 
					</td>
				</tr>
			</table>
			<p>
				&nbsp;
			</p>
		<input class="button" type="button" onClick="window.location='AddRecruitplanInit.action';" name="createPlan" value="新增招聘计划">
 
		<table id="recruitplantable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">

			<ww:if test="page.isSplit()">
				<tr>
					<td colspan="10" align="right" class="listViewPaginationTdS1">
						<ww:hidden name="page.currentPage" />
						<a href="#" onclick="splits_recruitment('first','searchrecruitplan');"><img
								src='../resource/images/start.gif' width='11' height='9'
								alt='开始'>开始</a>
						<a href="#" onclick="splits_recruitment('previous','searchrecruitplan');"><img
								src='../resource/images/previous.gif' width='6' height='9'
								alt='上页'>上页</a> （
						<ww:property value="page.currentPage+'/'+page.totalPages" />
						页｜共
						<ww:property value="page.totalRows" />
						条）
						<a href="#" onclick="splits_recruitment('next','searchrecruitplan');">下页<img
								src='../resource/images/next.gif' width='6' height='9'></a>
						<a href="#" onclick="splits_recruitment('last','searchrecruitplan');">最后<img
								src='../resource/images/end.gif' width='11' height='9' alt='最后'>
						</a>
					</td>
				</tr>
			</ww:if>
			<tr >
				<th><input id='changIds_choose' name='changIds_choose' class="checkbox" type="checkbox" onclick="checkAll('changIds');" value="0"></th>
				<th><a href="#" onclick="order_submit_recruitment('recpNo','searchrecruitplan');">编号</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpNo_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recpDepartmentNo.id','searchrecruitplan');">所属部门</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpDepartmentNo.id_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recpWorkLocation.id','searchrecruitplan');">工作地区</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpWorkLocation.id_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recpJobTitle','searchrecruitplan');">职位名称</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpJobTitle_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recpNumberExpect','searchrecruitplan');">人数</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpNumberExpect_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recpStartDate','searchrecruitplan');">职位发布日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpStartDate_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recpEndDate','searchrecruitplan');">职位关闭日期</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpEndDate_img'></th>
				<th><a href="#" onclick="order_submit_recruitment('recpStatus','searchrecruitplan');">状态</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='recpStatus_img'></th>
				<th>操作	</th>	
			</tr>
			<ww:if test="!recruitplanList.isEmpty()">
						<ww:iterator value="recruitplanList" status="index">
								<tr id="submitTr<ww:property value='id'/>"> 
								<ww:if test="(recpStatus ==@com.hr.base.Status@RP_STATUS_DRAFT)||recpStatus ==(@com.hr.base.Status@RP_STATUS_REJECT)">
									<td align="center">
										<input type="checkbox" name='changIds' class="checkbox" value="<ww:property value='id'/>" />
									</td>
								</ww:if>
								<ww:else>
								<td>&nbsp;</td>
								</ww:else>
								<td>
														
									<span class="view"  onclick="showplanDetailInSP(
													'<ww:property value="recpJobTitle.jobtitleName"/>',
													'<ww:property value="recpNumberExpect" />',
													'<ww:property value="recpDepartmentNo.departmentName" />',
													'<ww:property value="recpNumberActual" />',
													'<ww:property value="recpWorkLocation.locationName" />',
													'<ww:property value="recpType.emptypeName" />',
													'<ww:property value="recpDegree" />',
													'<ww:date name="recpStartDate" format="yyyy-MM-dd" />',
													'<ww:property value="recpLanguageSkill" />',
													'<ww:date name="recpEndDate" format="yyyy-MM-dd" />',
													'<ww:property value="recpJobSkill" />',
													'<ww:property value="getRecpStatus(recpStatus)"/>',
													'<ww:date name="recpCreateTime" format="yyyy-MM-dd"/>',
													'<ww:date name="recpLastChangeTime" format="yyyy-MM-dd"/>',
													'<ww:property value="recpJobDesc" escape="false"/>',
													'<ww:property value="recpComments" />',
														'planDetail');">																
																 <ww:property value="recpNo" />
									</span>
									
								</td>
								<td>
									<ww:property value="recpDepartmentNo.departmentName" />
								</td>
								<td>
									<ww:property value="recpWorkLocation.locationName" />
								</td>
								<td>
									<ww:property value="recpJobTitle.jobtitleName" />
								</td>
								<td>
									<ww:property value="recpNumberExpect" />
								</td>
								<td>
									<ww:date name="recpStartDate" format="yyyy-MM-dd" />
								</td>
								<td>
									<ww:date name="recpEndDate" format="yyyy-MM-dd" />									
								</td>								
											
								<td  onMouseOut="hideLog();"><span class="view" onclick="LogShowDiv('<ww:property value="id" />','recruitplan',this);"><ww:property value="getRecpStatus(recpStatus)"/></span></td>
								
								<td align="center">
									<ww:if test="recpStatus ==(@com.hr.base.Status@RP_STATUS_DRAFT)||recpStatus ==(@com.hr.base.Status@RP_STATUS_REJECT)">
											<a href="UpdateRecruitplanInit.action?id=<ww:property value='id'/>"/>
											<img src="../resource/images/edit.gif" alt='修改' border='0' /></a>
									</ww:if>
									<ww:else>
											<img src="../resource/images/blankSpace.gif"  border='0' />
									</ww:else>
									
									<ww:if test="recpStatus ==(@com.hr.base.Status@RP_STATUS_DRAFT)||recpStatus ==(@com.hr.base.Status@RP_STATUS_REJECT)">
									<a href="#" onclick="deleteRecruitplan('<ww:property value='id'/>')" >
									<img src="../resource/images/delete.gif"   alt='删除' border='0' /></a>
									</ww:if>
									<ww:else>
											<img src="../resource/images/blankSpace.gif"  border='0' />
									</ww:else>
									
									<ww:if test="recpStatus ==(@com.hr.base.Status@RP_STATUS_DRAFT)||recpStatus ==(@com.hr.base.Status@RP_STATUS_REJECT)">
									<a href="#"><img src="../resource/images/Submit.gif" alt='提交计划' onclick="submitPlan('<ww:property value="id"/>');" border='0'/></a>
									</ww:if>
									<ww:else>
											<img src="../resource/images/blankSpace.gif"  border='0' />
									</ww:else>
				</td>
			</tr>
			</ww:iterator>
		</ww:if>
		<ww:else>
			<tr>
				<!-- 不存在符合条件的招聘计划！ -->
				<td align="center" colspan="11">
					不存在符合条件的招聘计划!
				</td>
			</tr>
		</ww:else>
		</table>
		<ww:if test="!recruitplanList.isEmpty()">
			<p>
				&nbsp;
			</p>
			<table>
				<tr>
					<td>
					
						<input class="button" type="button" onclick="submitPlanS();" value="提交招聘计划">
						
					</td>
				</tr>
			</table>
		</ww:if>
		
		<div id="tmpletId" style="DISPLAY: none"><img src="../resource/images/basic_search.gif" onload="check_order();"/></div>
		
		<div id="comment" title="提交招聘计划"> 
			<table border="0" cellspacing="2" cellpadding="0"  class="prompt_div_body" width="100%">
				<tr>	
						<ww:hidden  id="commentId" name="plan.id" />								
						<ww:textarea id="comments" name="comments" label="备注"   cols="45" rows="5" onkeypress="MKeyTextLength(this,255);"/>
				</tr>
				<tr>
				<td align="center" colspan="2">
				<input type="button" onclick="addcomments();" value="提交招聘计划">
				<input type="button" onclick="hrm.common.closeDialog('comment');cleanPlan();" value="取消">
				</td>
				</tr>
			</table>				
		<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;" frameborder="0" ></iframe>				
	</div>	
		<script type="text/javascript" language="javascript">
		  		hrm.common.initDialog('comment',450);
		</script>		
	</ww:form>
			
	<script type="text/javascript">
		function submitPlan(id) {
			$('#commentId').val(id);
			if($('#comments_label') != null){
				$('#comments_label').html('');
			}
			hrm.common.openDialog('comment');
		}
	</script>								
</body>
