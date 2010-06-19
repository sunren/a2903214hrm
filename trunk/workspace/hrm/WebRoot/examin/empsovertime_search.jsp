<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>		
		<!-- css������Ϣ -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />	
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>
	 	<!-- �Լ���� -->
		<script type="text/javascript" src="../resource/js/hrm/examin.js"></script>
		<script type="text/javascript" src="../dwr/interface/MYOT.js"></script>		
		<script type="text/javascript" src="../dwr/interface/HROT.js"></script>	
		<title>Ա���Ӱ��б�</title> 		
</head>
	<body onload="hrm.common.check_order();">
	    <jsp:include flush="true" page="dlg_leaveorovertime_examin.jsp"></jsp:include>		    
		<jsp:include flush="true" page="/configuration/log.jsp"></jsp:include>
		<jsp:include flush="true" page="div_overtimeshow.jsp"></jsp:include>
		<jsp:include flush="true" page="div_examincoment.jsp"></jsp:include>
		<br/>
		<s:component template="bodyhead">
		</s:component>
		
		<form id="actionSrc" name="actionSrc" action="<s:property value='actionSrc'/>"  method="POST">
		
			<!--������Ϣ-->
			<s:hidden id="srcAction" name="srcAction"/>
		 	<s:hidden id="operation" name="page.operate" value=""/>
		 	<s:hidden id="order" name="page.order" />
		 	<s:hidden id="infoMeg" name="infoMeg" value="" />
		 	<s:hidden id="orIdUp" name="orIdUp"/>
		 	<s:hidden id="approveOper" name="approveOper" value=""/>
		 	<s:hidden id="of_Bean_orAppComment" name="of_Bean.orAppComment" value=""/>
		 	<s:hidden id="formIstiaoxiu" name="of_Bean.isTiaoxiu"/>
		 	<s:hidden id="formOrTiaoxiuHours" name="of_Bean.orTiaoxiuHours"/>
		 	<s:hidden id="formOrTiaoxiuExpire" name="of_Bean.orTiaoxiuExpire"/>			
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tblSearchCondition">
							<tr>
								<s:textfield label="Ա��" id="es_Bean.emp" name="es_Bean.emp" size="16" maxlength="64"></s:textfield>
								<s:textfield label="���" id="es_Bean.no" name="es_Bean.no" size="14" maxlength="14"></s:textfield>
								<td align="right">��ʼ����:</td>
								<td><s:textfield  id="es_Bean_startDate" name="es_Bean.startDate" required="true" size="12" />
								<img onclick="WdatePicker({el:'es_Bean_startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td> 				
								<s:select label="�Ӱ�����" id="es_Bean.typeNo" name="es_Bean.typeNo" list="otTypeList"  listKey="otNo" listValue="otName" emptyOption="true" />
							</tr>
							<tr>
								<td align="right">��֯��Ԫ:</td>
								<td>
								    <s:orgselector id="orgselector1" name="es_Bean.depNos" hiddenFieldName="es_Bean.depNo"/>
								</td>
								<s:textfield label="����" id="es_Bean.reason" name="es_Bean.reason" size="14" maxlength="128"></s:textfield>
								<td align="right">��������:</td>
 								<td><s:textfield  id="es_Bean_endDate" name="es_Bean.endDate" required="true" size="12"   /> 
								<img onclick="WdatePicker({el:'es_Bean_startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>												
							</tr>
						</table>
						</td>
						<td rowspan="2">				
							<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="��ѯ">
							<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="button"  onclick="window.location='hrOvertimeSearch.action'" value="����">
						<br> 
					</td>
				</tr>
			</table>
			<p align="left">&nbsp;</p>
			<s:if test="es_Bean!=null&&es_Bean.result!=null&&!es_Bean.result.isEmpty()">
				<table cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td rowspan="2">				
						    <input id="batchButton_a" name="batchButton_a" class="button" type="button" onclick="hrm.examin.showRejectDiv('approve','logComents_r');" value="��׼">
						    <input id="batchButton_r" name="batchButton_r" class="button" type="button" onclick="hrm.examin.showRejectDiv('reject','logComents_r');" value="�ܾ�">
						</td>
						<td align="right">
						    ���β�ѯ���õ�<s:property value="page.totalRows" />����ټ�¼&nbsp;
						</td>
					</tr>
				</table>
			</s:if> 
			<s:else>
			    <table cellpadding="0" cellspacing="0" width="100%" border="0">
			        <tr><td align="right">
						    ���β�ѯ���õ�<s:property value="page.totalRows" />���Ӱ��¼&nbsp;
						</td>
				    </tr>
				</table>
			</s:else>
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
				<tr>
					<th>
						<input id="changIds" name="changIds" class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('changIds','changIds');;" value="ѡ��">
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('id','actionSrc')">���<img src='../resource/images/arrow_.gif' width='8' height='10' id='id_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('emp','actionSrc')">Ա������<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp_img'></a>
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('dep','actionSrc')">����<img src='../resource/images/arrow_.gif' width='8' height='10' id='dep_img'></a>
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
								<s:if test="orStatus<@com.hr.base.Status@OTR_STATUS_HR_APPROVE">
									<input id="changIds" type="checkbox" name="changIds" class="checkbox" value="<s:property value='orId'/>" />
								</s:if>
								<s:else>
									&nbsp;
								</s:else>
							</td>
							<td align="center">
								<strong>
								<span onclick="ShowOT('<s:property value='orId'/>')" class="view">
									<s:property value="orNo" />
								</span>
								</strong>
							</td>
							<td>
								<s:property value="orEmpNo.empName" />
							</td>
							<td>
								<s:property value="orEmpDept.departmentName" />
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
								<s:property value="@com.hr.util.MyTools@decimalFormat(orTotalHours, 4, 1)"/>
							</td>
							<td onclick="LogShowDiv('<s:property value="orId" />','overtimerequest',this);" onMouseOut="hideLog('logTable');">
								<span class="view">
									<s:property value="orStatusMean"/>
								</span>	
							</td>
							<td align="left" nowrap="nowrap">
								<a href="#" onclick="showHrApprove('<s:property value='orId'/>','<s:property value='orNo'/>',<s:property value='%{orTotalHours}'/>);" >
									<img src="../resource/images/MgrApprove.gif" alt='����ͨ��' border='0' />
								</a>
								<a href="#" onclick="hrm.examin.setId('<s:property value='orId'/>');hrm.examin.showRejectDiv('reject','logComents_r');" >
									<img src="../resource/images/MgrReject.gif" alt='�ܾ�' border='0' />
								</a>
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
			<s:if test="es_Bean!=null&&es_Bean.result!=null&&!es_Bean.result.isEmpty()">
				<table cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td>
						    <input id="batchButton_a"  name="batchButton_a" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('approve','logComents_r');" value="��׼">
						    <input id="batchButton_r"  name="batchButton_r" class="button"   type="button"   onclick="hrm.examin.showRejectDiv('reject','logComents_r');" value="�ܾ�">
						</td>
					</tr>
				</table>
			</s:if>
		</form>
	</body>
	
<div id="dlgHrApproveOT" style="width:330; display:none;">  
	<table cellSpacing="0" cellPadding="0" width="100%" class="listView" width="100%" >
		<s:hidden name="otTime" id="otTime"></s:hidden>
		<s:hidden name="orId" id="orId"></s:hidden>
		<s:hidden name="otNo" id="otNo"></s:hidden>
		<tr>
		<td>
		�Ƿ���ݣ�
		    <input type="radio" name="isTiaoxiu" id="tiaoxiu1" value="0" checked="checked" onclick="hideTiaoxiu();"/>�� 
		    <input type="radio" name="isTiaoxiu" id="tiaoxiu2" value="1" onclick="showTiaoxiu();"/>�� 
		</td>
		</tr>
		<tr id="tiaoXiuTR" style="display:none;">
			<td nowrap="nowrap" style="padding:4px;">����ʱ�䣺</td>
		    <td><s:textfield id="tiaoXiuTime" name="tiaoXiuTime" maxLength="4" size="4" onkeyup="value=value.replace(/[^\d \.]/g,'')"></s:textfield></td>
		    <td>Сʱ&nbsp;&nbsp;</td>
		    <span id="expireDiv" style="display:block;">	
		        <td>��Ч����</td>	     
		    	<td><s:textfield id="otTiaoxiuExpire" name="otTiaoxiuExpire" required="true" size="10"  />
		    	<img onclick="WdatePicker({el:'otTiaoxiuExpire'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle"></td>								
		    </span>
		</tr>
		<tr>
			<td>
			    ��ע:
			<s:textarea id="otApproveComents" name="otApproveComents" cols="25" rows="5" />	
			</td>
		</tr>
		<tr>
			<td align="center">
				<input id="subBution" class="button"  type="button" onclick="return subHrApprove();" value="�ύ">
				<input  class="button"  type="button" onclick="hrm.common.closeDialog('dlgHrApproveOT');resetTiaoXiuDiv();" value="ȡ��">
			</td>
		</tr>
	</table> 	
</div>
<script type="text/javascript">
/**��ʾhr������***/
function showHrApprove(orId,otNo,otHours){
    document.getElementById("orId").value=orId;
    document.getElementById("otNo").value=otNo;	               	        
    document.getElementById("otTime").value=otHours;
    document.getElementById("tiaoXiuTime").value=otHours;	        	      
   
    $("#dlgHrApproveOT").dialog("option","title",'HR�������Ϊ'+otNo+'�ļӰ൥');
    if(hrm.common.navigatorIsIE()){
    $("#dlgHrApproveOT").dialog("option","width","500");
    }	        	        
    hrm.common.openDialog('dlgHrApproveOT');
}

/***��ֵΪ v ��name��ǩ����isTiaoxiu��radio��ѡ��**/
function resetRadio(v){
    var r=document.getElementsByName("isTiaoxiu"); 
    for(i=0;i<r.length;i++){
        if(r[i].value==v){
            r[i].checked=true;
            return;
        }
    }
}
	    
//�ж��Ƿ����	   
function getValue(name){
    var r=document.getElementsByName(name);
    for(i=0;i<r.length;i++){
        if(r[i].checked==true){
            return r[i].value;
        }
    }
}

/***����ر�hr�����㰴ť��ʱ��������Ҫ�� �Ƿ���ݴ���"��"��״̬���������ص���ʱ��**/
function resetTiaoXiuDiv(){
    hideTiaoxiu();
    resetRadio("0");
}

/**���� ����ʱ�� ������***/
function hideTiaoxiu(){  
    document.getElementById("tiaoXiuTR").style.display="none";
}
	    
/**��ʾ ����ʱ�� ������***/
function showTiaoxiu(){
    var orId = document.getElementById("orId").value;
    document.getElementById("tiaoXiuTR").style.display="block";
    HROT.getTiaoxiuExpire(orId, getExpireCallBack);
    function getExpireCallBack(expire){
        if(expire == null){       
            document.getElementById("expireDiv").style.display = "none";
        }else{
            document.getElementById("expireDiv").style.display = "block";
            document.getElementById("otTiaoxiuExpire").value = expire;
        }
    }
}
	    
/***���hr������׼�İ�ť�������¼�***/
function subHrApprove(){
    /**��ñ�ע��Ϣ������ʱ�䣬�Լ��Ƿ�Ҫ���е����������ֶε�ֵ**/
    var orId=document.getElementById("orId").value;
    var comment=document.getElementById("otApproveComents").value;
    var tiaoxiuTime=document.getElementById("tiaoXiuTime").value;
    var otTiaoxiuExpire = document.getElementById("otTiaoxiuExpire").value;
    var isTiaoXiu=getValue("isTiaoxiu");
    /**��עΪ�����ֶ�***/
    if(comment==null || comment==""){
        alert("��ע�ֶ�Ϊ�����ֶΡ�");
        return false;
    }
    /**����ʱ��Ϊ�յ�ʱ���Ĭ��Ϊ0***/
    if(tiaoxiuTime==null || tiaoxiuTime==''){
        tiaoxiuTime=0;
    }
    tiaoxiuTime=parseFloat(tiaoxiuTime);
    //���ñ��ύ��ֵ
    var formElement=$('#actionSrc').get(0);
    $('#orIdUp').val(orId);
    $('#of_Bean_orAppComment').val(comment);
    $('#approveOper').val('tiaoxiu');
    if(isTiaoXiu=="0"){
        /**�����ǲ����е���**/
         $('#formIstiaoxiu').val('false'); 
    }else{
        /***˵���ǽ��е���**/
         $('#formIstiaoxiu').val('true');
         $('#formOrTiaoxiuHours').val(tiaoxiuTime);
         $('#formOrTiaoxiuExpire').val(otTiaoxiuExpire);
    }
    formElement.action ='hrOtApproveOrReject.action'; //js��ʽ�ύ��           
	formElement.submit(); 
	hrm.common.closeDialog('dlgHrApproveOT');
}
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ�
 
//�����������÷���(�����ֱ�Ϊͬ���ܽӣ���ע��ϢId�����ܱ�ע��ϢId����Id��Action��)
function batchReject(){
   commonbatch('approveOper','logComents_r','of_Bean_orAppComment','actionSrc','hrOtApproveOrReject.action')
}
  
hrm.common.initDialog('dlgOtShow');
hrm.common.initDialog('dlgHrApproveOT');
</script>
</html>