<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<meta http-equiv="Accept-Encoding" content="gzip" />
		<!-- css������Ϣ -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />	
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
		<!-- �Լ���� -->
		<script type="text/javascript" src="../resource/js/hrm/examin.js"></script>
		<script type="text/javascript" src="../dwr/interface/MYOT.js"></script>
		<title>�ҵļӰ��б�</title>
	</head>
	<body onload="hrm.common.check_order();">		  	     
		<jsp:include flush="true" page="/configuration/log.jsp"></jsp:include>
		<jsp:include flush="true" page="div_overtimeshow.jsp"></jsp:include>
		<br/>	
		<s:component template="bodyhead">
		</s:component>
		
		<form id="actionSrc" name="actionSrc" action="myOvertimeSearch.action"  method="POST">
			<!--������Ϣ-->
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
								<td><s:textfield label="���" id="es_Bean_no" name="es_Bean.no" size="14" maxlength="14"></s:textfield></td>								 
								<td align="right">��ʼ����:</td>
								<td><s:textfield  id="es_Bean_startDate" name="es_Bean.startDate" required="true" size="12" /> 
								<img onclick="WdatePicker({el:'es_Bean_startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>
								<td><s:select label="�Ӱ�����" id="es_Bean_typeNo" name="es_Bean.typeNo" list="otTypeList"  listKey="otNo" listValue="otName" emptyOption="true" /></td>
							</tr>
							<tr>
								<td><s:textfield label="����" id="es_Bean_reason" name="es_Bean.reason" size="14" maxlength="128"></s:textfield></td>
								<td align="right">��������:</td>
								<td><s:textfield   id="es_Bean_endDate" name="es_Bean.endDate" required="true" size="12"  /> 
								<img onclick="WdatePicker({el:'es_Bean_endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>									
								<td><s:select label="�Ӱ�״̬" id="es_Bean_statu" name="es_Bean.statu"  list="statusMap" listKey="key" listValue="value" emptyOption="true"/></td>
							</tr>
						</table>
					</td>
					<td rowspan="2">				
						<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="��ѯ">
						<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="button"   onclick="window.location='myOvertimeSearch.action'" value="����">
					</td>
				</tr>
			</table>
			<p align="left">&nbsp;</p> 
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
			    <tr>
			    <td align="left"><input class="button"  type="button" onClick="window.location='empOTAddInit.action';" name="createRequest" value="�����Ӱ�">
			        <input class="button" type="button" id="showTotal" onClick="hrm.common.openDialog('dlgOtTotal',null,'��ȼӰ����')" name="showTotal" value="��ȼӰ����"/></td>
			    <td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />���Ӱ��¼&nbsp;</td>
			    </tr>
			</table>
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
				<tr>
				   <!--
					<th>
						<input id="changIds" name='changIds' class="checkbox" type="checkbox" onclick="checkAll('changIds');" value="ѡ�вݸ�">
					</th>
					-->
					<th>
						<a href="#" onclick="hrm.common.order_submit('id','actionSrc')">���<img src='../resource/images/arrow_.gif' width='8' height='10' id='id_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('type','actionSrc')">����<img src='../resource/images/arrow_.gif' width='8' height='10' id='type_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('reason','actionSrc')">�Ӱ�����<img src='../resource/images/arrow_.gif' width='8' height='10' id='reason_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('startT','actionSrc')">�Ӱ�ʱ��<img src='../resource/images/arrow_.gif' width='8' height='10' id='startT_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('total','actionSrc')">Сʱ��<img src='../resource/images/arrow_.gif' width='8' height='10' id='total_img'></a>
					</th>
					<th> 
						<a href="#" onclick="hrm.common.order_submit('statu','actionSrc')">״̬<img src='../resource/images/arrow_.gif' width='8' height='10' id='statu_img'></a>
					</th>
					<th> 
						����
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
								<s:date name="orStartDate" format="yyyy-MM-dd HH:mm" />��<s:property value = "get24Point(orEndDate)"/>
								<!-- <s:date name="orEndDate" format="HH:mm" /> -->
							</td>
							<td>
								<s:property value="@com.hr.util.MyTools@decimalFormat(orTotalHours, 4, 1)"/><s:if test="orTiaoxiuHours > 0">(��)</s:if>
							</td>
							<td onclick="LogShowDiv('<s:property value="orId" />','overtimerequest',this);" onMouseOut="hideLog('logTable');">
								<span class="view">
									<s:property value="orStatusMean"/>
								</span>	
							</td>
							<td align="left">
								<s:if test="orStatus==@com.hr.base.Status@OTR_STATUS_REJECT">
									<a href="#" onclick="hrm.common.submitForm('actionSrc','myOTUpdateInit.action','orIdUp','<s:property value='orId'/>')" > 
										<img src="../resource/images/edit.gif" alt='�޸�' border='0' />
									</a>
								</s:if>
								<s:if test="orStatus == @com.hr.base.Status@OTR_STATUS_REJECT">
									<a href="#" onclick="hrm.examin.deletePlan('<s:property value='orId'/>','<s:property value='orNo'/>','overtime');" >
										<img src="../resource/images/delete.gif" alt='ɾ��' border='0' />
									</a>
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td align="center" colspan="11">�����ڷ��������ļӰ൥��</td>
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
					�·�
				<br></th>
		<s:iterator value="overtimeTotal[13]">
				<th align="center">
					
				<br></th>
		</s:iterator>
		</tr>
		<tr><td align="center" class="tablefield">һ��<br></td>
			<s:iterator value="overtimeTotal[0]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">����<br></td>
			<s:iterator value="overtimeTotal[1]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">����<br></td>
			<s:iterator value="overtimeTotal[2]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">����<br></td>
			<s:iterator value="overtimeTotal[3]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">����<br></td>
			<s:iterator value="overtimeTotal[4]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">����<br></td>
			<s:iterator value="overtimeTotal[5]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">����<br></td>
			<s:iterator value="overtimeTotal[6]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">����<br></td>
			<s:iterator value="overtimeTotal[7]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">����<br></td>
			<s:iterator value="overtimeTotal[8]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">ʮ��<br></td>
			<s:iterator value="overtimeTotal[9]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">ʮһ��<br></td>
			<s:iterator value="overtimeTotal[10]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">ʮ����<br></td>
			<s:iterator value="overtimeTotal[11]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		<tr><td align="center" class="tablefield">����<br></td>
			<s:iterator value="overtimeTotal[12]">
			<td align="center"><s:property /></td>
		</s:iterator></tr>
		</s:if>
		<s:else>
			<tr>
				<td align="center" colspan="5">����������ݲ����ڣ�<br></td>										
			</tr>
		</s:else>
			<tr>
				<td align="center" colspan="5"><input type="button" value="�ر�" class="button" onclick="hrm.common.closeDialog('dlgOtTotal');"/><br></td>										
			</tr>
	</table>
</div>
</body>
</html>
<script>
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ�      
hrm.common.initDialog('dlgOtTotal'); //��ٻ���
hrm.common.initDialog('dlgOtShow');  //�������
</script>