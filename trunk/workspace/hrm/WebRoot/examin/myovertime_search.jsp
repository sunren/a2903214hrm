<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<meta http-equiv="Accept-Encoding" content="gzip" />
		<!-- css修饰信息 -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />	
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
		<!-- 自己添加 -->
		<script type="text/javascript" src="../resource/js/hrm/examin.js"></script>
		<script type="text/javascript" src="../dwr/interface/MYOT.js"></script>
		<title>我的加班列表</title>
	</head>
	<body onload="hrm.common.check_order();">		  	     
		<jsp:include flush="true" page="/configuration/log.jsp"></jsp:include>
		<jsp:include flush="true" page="div_overtimeshow.jsp"></jsp:include>
		<br/>	
		<s:component template="bodyhead">
		</s:component>
		
		<form id="actionSrc" name="actionSrc" action="myOvertimeSearch.action"  method="POST">
			<!--隐藏信息-->
		 	<s:hidden id="operation" name="page.operate" value=""/>
		 	<s:hidden id="order" name="page.order" />
		 	<s:hidden id="infoMeg" name="infoMeg" value=""/>
		 	<s:hidden id="errorMsg" name="errorMsg" value=""/>
		 	<s:hidden id="orIdUp" name="orIdUp"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<table id="tblSearchCondition" width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td><s:textfield label="编号" id="es_Bean_no" name="es_Bean.no" size="14" maxlength="14"></s:textfield></td>								 
								<td align="right">开始日期:</td>
								<td><s:textfield  id="es_Bean_startDate" name="es_Bean.startDate" required="true" size="12" /> 
								<img onclick="WdatePicker({el:'es_Bean_startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>
								<td><s:select label="加班种类" id="es_Bean_typeNo" name="es_Bean.typeNo" list="otTypeList"  listKey="otNo" listValue="otName" emptyOption="true" /></td>
							</tr>
							<tr>
								<td><s:textfield label="理由" id="es_Bean_reason" name="es_Bean.reason" size="14" maxlength="128"></s:textfield></td>
								<td align="right">结束日期:</td>
								<td><s:textfield   id="es_Bean_endDate" name="es_Bean.endDate" required="true" size="12"  /> 
								<img onclick="WdatePicker({el:'es_Bean_endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>									
								<td><s:select label="加班状态" id="es_Bean_statu" name="es_Bean.statu"  list="statusMap" listKey="key" listValue="value" emptyOption="true"/></td>
							</tr>
						</table>
					</td>
					<td rowspan="2">				
						<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="查询">
						<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="button"   onclick="window.location='myOvertimeSearch.action'" value="重置">
					</td>
				</tr>
			</table>
			<p align="left">&nbsp;</p> 
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
			    <tr>
			    <td align="left"><input class="button"  type="button" onClick="window.location='empOTAddInit.action';" name="createRequest" value="新增加班">
			        <input class="button" type="button" id="showTotal" onClick="hrm.common.openDialog('dlgOtTotal',null,'年度加班汇总')" name="showTotal" value="年度加班汇总"/></td>
			    <td align="right">本次查询共得到<s:property value="page.totalRows" />条加班记录&nbsp;</td>
			    </tr>
			</table>
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
				<tr>
				   <!--
					<th>
						<input id="changIds" name='changIds' class="checkbox" type="checkbox" onclick="checkAll('changIds');" value="选中草稿">
					</th>
					-->
					<th>
						<a href="#" onclick="hrm.common.order_submit('id','actionSrc')">编号<img src='../resource/images/arrow_.gif' width='8' height='10' id='id_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('type','actionSrc')">种类<img src='../resource/images/arrow_.gif' width='8' height='10' id='type_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('reason','actionSrc')">加班理由<img src='../resource/images/arrow_.gif' width='8' height='10' id='reason_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('startT','actionSrc')">加班时间<img src='../resource/images/arrow_.gif' width='8' height='10' id='startT_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('total','actionSrc')">小时数<img src='../resource/images/arrow_.gif' width='8' height='10' id='total_img'></a>
					</th>
					<th> 
						<a href="#" onclick="hrm.common.order_submit('statu','actionSrc')">状态<img src='../resource/images/arrow_.gif' width='8' height='10' id='statu_img'></a>
					</th>
					<th> 
						操作
					</th>
				</tr>
				<s:if test="es_Bean!=null&&es_Bean.result!=null&&!es_Bean.result.isEmpty()">
					<s:iterator value="es_Bean.result" status="index">
						<tr>
							<td align="center">
								<strong>
								<span onclick="ShowOT('<s:property value='orId'/>')" class="view">
									<s:property value="orNo" />
								</span>
								</strong>
							</td>
							<td>
								<s:property value="orOtNo.otName" />
							</td>
							<td>
								<s:property value="orReason" />
							</td>
							<td>
								<s:date name="orStartDate" format="yyyy-MM-dd HH:mm" />—<s:property value = "get24Point(orEndDate)"/>
								<!-- <s:date name="orEndDate" format="HH:mm" /> -->
							</td>
							<td>
								<s:property value="@com.hr.util.MyTools@decimalFormat(orTotalHours, 4, 1)"/><s:if test="orTiaoxiuHours > 0">(调)</s:if>
							</td>
							<td onclick="LogShowDiv('<s:property value="orId" />','overtimerequest',this);" onMouseOut="hideLog('logTable');">
								<span class="view">
									<s:property value="orStatusMean"/>
								</span>	
							</td>
							<td align="left">
								<s:if test="orStatus==@com.hr.base.Status@OTR_STATUS_REJECT">
									<a href="#" onclick="hrm.common.submitForm('actionSrc','myOTUpdateInit.action','orIdUp','<s:property value='orId'/>')" > 
										<img src="../resource/images/edit.gif" alt='修改' border='0' />
									</a>
								</s:if>
								<s:if test="orStatus == @com.hr.base.Status@OTR_STATUS_REJECT">
									<a href="#" onclick="hrm.examin.deletePlan('<s:property value='orId'/>','<s:property value='orNo'/>','overtime');" >
										<img src="../resource/images/delete.gif" alt='删除' border='0' />
									</a>
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td align="center" colspan="11">不存在符合条件的加班单！</td>
					</tr>
				</s:else>
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
	  			<tr class="listViewPaginationTdS1">
	 			  <td colspan="10" align="center">
	 			    <s:hidden id="page.currentPage" name="page.currentPage" />
	 			    <s:component template="pager">
	 			        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
	          	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'actionSrc'" />
	          	  	</s:component>
				  </td>
	  			</tr>
			</table>
		</form>
<div id="dlgOtTotal" style="display:none;width:600px">
	<table id="table3" cellSpacing="0" cellPadding="0" width="100%" class="gridview" id="logTable">
		<s:if test="overtimeTotal[12][overtimeTotal[0].length-1]!='0.0'">
		<tr>
				<th align="center">
					月份
				<br></th>
		<s:iterator value="overtimeTotal[13]">
				<th align="center">
					
				<br></th>
		</s:iterator>
		</tr>
		<tr><td align="center" class="tablefield">一月<br></td>
			<s:iterator value="overtimeTotal[0]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">二月<br></td>
			<s:iterator value="overtimeTotal[1]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">三月<br></td>
			<s:iterator value="overtimeTotal[2]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">四月<br></td>
			<s:iterator value="overtimeTotal[3]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">五月<br></td>
			<s:iterator value="overtimeTotal[4]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">六月<br></td>
			<s:iterator value="overtimeTotal[5]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">七月<br></td>
			<s:iterator value="overtimeTotal[6]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">八月<br></td>
			<s:iterator value="overtimeTotal[7]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">九月<br></td>
			<s:iterator value="overtimeTotal[8]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">十月<br></td>
			<s:iterator value="overtimeTotal[9]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">十一月<br></td>
			<s:iterator value="overtimeTotal[10]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">十二月<br></td>
			<s:iterator value="overtimeTotal[11]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">汇总<br></td>
			<s:iterator value="overtimeTotal[12]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		</s:if>
		<s:else>
			<tr>
				<td align="center" colspan="5">您请求的数据不存在！<br></td>										
			</tr>
		</s:else>
			<tr>
				<td align="center" colspan="5"><input type="button" value="关闭" class="button" onclick="hrm.common.closeDialog('dlgOtTotal');"/><br></td>										
			</tr>
	</table>
</div>
</body>
</html>
<script>
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）      
hrm.common.initDialog('dlgOtTotal'); //请假汇总
hrm.common.initDialog('dlgOtShow');  //单条请假
</script>