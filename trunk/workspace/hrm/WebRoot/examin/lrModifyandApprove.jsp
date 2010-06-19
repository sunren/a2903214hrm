<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<!-- css������Ϣ -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>		 
		<title>�ҵ���ٵ��޸�</title>
	</head>
	<body>
		<s:component template="bodyhead">			
		</s:component>

		<s:form id="LRUpdateDo" name="LRUpdateDo" action="hrConfirmWithModify" method="post">
			<!--������-->
			<s:hidden id="approveOper" name="approveOper"></s:hidden>
			<s:hidden id="lrUpdateId" name="lrUpdateId" />
			<s:hidden id="srcAction" name="srcAction"></s:hidden>
			<s:hidden name="gmanager" id="gmanager"></s:hidden>
			<s:hidden name="lf_Bean.lrEmpNo.id" />
			<br>
			<table width="100%">
			<tr>
			<td width="2%">
				<img src="../resource/images/h3Arrow.gif">
			</td>
			<td>
				<h3>�޸Ĳ������������</h3>
			</td>
			</tr>
			</table>
			<table  width="100%" class="formtable">
				<tr>							
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						Ա����ţ�
					</td>
					<td align="left" >
						<s:property value="lr.lrEmpNo.empDistinctNo" />
					</td>
			       <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						Ա��������
					</td>
					<td align="left" nowrap>
						<s:property value="lr.lrEmpNo.empName" />
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						���ڲ��ţ�
					</td>
					<td align="left">
						<s:property value="lr.lrEmpDept.departmentName"/>
					</td>
				</tr>
				
				<tr>							
				   <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						��ٵ���ţ�
					</td>
					<td align="left" >
						<s:property value="lr.lrNo"/>
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						������ࣺ
					</td>
					<td align="left" nowrap>
						<s:property value="lr.lrLtNo.ltName"/>
					</td>
					 <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						��ǰ״̬��
					</td>
					<td align="left">
						<s:property value="lf_Bean.lrStatus" />
					</td>
				</tr>
				
				<tr>							
					<td align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						��ٿ�ʼʱ�䣺
					</td>
					<td align="left" >
						<s:property value="lf_Bean.startTime" />
					</td>
			       <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						��ٽ���ʱ�䣺
					</td>
					<td align="left" >
						<s:property value="lf_Bean.endTime" />
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						�����ʱ�䣺
					</td>
					<td align="left" >
						<s:property value="lf_Bean.totalTime" />
					</td>
				</tr>
				
				<tr>						
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						���ڿ�����ʱ�䣺
					</td>
					<td align="left" >
						<s:property value="lf_Bean.useableTime" />
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						������ʹ��ʱ�䣺
					</td>
					<td align="left" nowrap>
						<s:property value="lf_Bean.usedTime" />
					</td>
				    <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						��ֹ��׿��ã�
					</td>
					<td align="left">
						<s:property value="lf_Bean.remainTime" />
					</td>
				</tr>
				
				<tr>							
					<td  align="left"  nowrap width="10%" style="padding:6px 6px 6px 6px">
						������ɣ�
					</td>
					<td align="left"   colspan="5">
						<s:property value="lr.lrReason" />
					</td>
				</tr>
			</table>
			<table  width="100%" class="formtable">
			<tr>
			    <td colspan="6">
			    
			    </td>
			</tr>
				<tr>
					<td align="left" nowrap>
						<label>��ʼ����<span class="required">*</span>��</label>
					</td>
					<td nowrap>					
						<s:textfield  id="lf_Bean.beginDate" name="lf_Bean.beginDate" required="true" size="12"  />		
						<img onclick="WdatePicker({el:'lf_Bean.beginDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">							
						<s:if test="lf_Bean.applyLRByDay">
							<s:select id="lf_Bean.beginAPM" name="lf_Bean.beginApm" list="#{0:'����',1:'����'}" emptyOption="false" required="true"/>
						</s:if>
						<s:else>
							<s:select id="lf_Bean.beginHour" name="lf_Bean.beginHour" list="@com.hr.examin.action.beans.Hours@getHourList()" listKey="hour" listValue="description" required="true" emptyOption="false" required="true" />
							<s:select id="lf_Bean.beginMinute" name="lf_Bean.beginMinute" list="#{0:'��',30:'��'}" required="true" emptyOption="false"></s:select>
						</s:else>
					</td>
					<td align="right" rowspan="2" width="10%">
						��ע:
					</td>
					<td rowspan="2" colspan="3" align="left">
						<s:textarea id="lf_Bean.lrAppComment" name="lf_Bean.lrAppComment" cols="40" rows="4" required="true" cssStyle="font-family:Courier New;font-size:12px" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap width="10%">��������<span class="required">*</span>��</td>
					<td nowrap>					 
						<s:textfield  id="lf_Bean.endDate" name="lf_Bean.endDate" required="true" size="12" />	
						<img onclick="WdatePicker({el:'lf_Bean.endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">								    													
						<s:if test="lf_Bean.applyLRByDay">
							<s:select id="lf_Bean.endAPM" name="lf_Bean.endApm" list="#{0:'����',1:'����'}" required="true" emptyOption="false" />
						</s:if>
						<s:else>
							<s:select id="lf_Bean.endHour" name="lf_Bean.endHour" list="@com.hr.examin.action.beans.Hours@getHourList_24()" listKey="hour" listValue="description" required="true" emptyOption="false" required="true" />
							<s:select id="lf_Bean.endMinute" name="lf_Bean.endMinute" list="#{0:'��',30:'��'}" required="true" emptyOption="false"></s:select>
						</s:else>
					</td>
				</tr>
				<tr>
                    <td colspan="6">
					</td>
				</tr>
				<tr>
					<td height="1" colspan="5" align="center">
					    <s:if test="srcAction == 'allLeaveSearch'">
					        <input id="approveBtn" id="submited" name="submited" type="button"
							onclick="return submitLrUpdateRequest('approve');" value="����">
						    <input id="reset" name="reset" type="reset" value="����">
					    </s:if>
					    <s:else>
							<input id="approveBtn" name="submited" type="button"
								onclick="return submitLrUpdateRequest('approve');" value="ͬ��">
							<input id="rejectBtn" name="submited" type="button"
								onclick="return submitLrUpdateRequest('reject');" value="�ܾ�">
							<input id="reset" name="reset" type="reset" value="����">
						</s:else>
					</td>
				</tr>
			</table>
			<br>
			<s:if test="lf_Bean.logList!=null && lf_Bean.logList.size()>0">
			    <s:iterator value="lf_Bean.logList">
				  <p align="left">
				   &nbsp;
				   <s:property value="%{slChangeEmpno.empName}" />��
						    <s:date name="slChangeTime" format="yyyy-MM-dd HH:mm:ss" />
						    <s:property value="%{slAction}" />
					        <s:if test="slComments!=null && slComments!='' && slComments.length>0">
					            &nbsp;��ע��Ϣ��<s:property value="%{slComments}" />
					        </s:if>
					</p>       
		
			    </s:iterator>
			</s:if>
		</s:form>
<script type="text/javascript">
function submitLrUpdateRequest(operate){
   var comment=document.getElementById("lf_Bean.lrAppComment").value;
     if(comment.length>255){
       alert("��ע��Ϣ̫�����Ϊ255���ַ���");
       return false;
     }
     if(comment==null || comment=="" || comment.length<1){
       alert("��ע��Ϣ����Ϊ�գ�");
       return false;
     }
     var approveMsg="��ȷ��Ҫ�ύ������ٵ���";
     var rejectMsg="��ȷ��Ҫ�ܾ�������ٵ���";
     if(!hrm.common.isNull(operate) && operate == 'approve' && !confirm(approveMsg)){
   	    return false;
     }
     if(!hrm.common.isNull(operate) && operate == 'reject' && !confirm(rejectMsg)){
 	    return false;
     }

	if(document.getElementById('approveBtn')!=null) document.getElementById('approveBtn').disabled=true;
	if(document.getElementById('rejectBtn')!=null) document.getElementById('rejectBtn').disabled=true;
	document.getElementById('approveOper').value = operate;
	if(document.getElementById('srcAction').value == 'deptLeaveSearch'){
	    document.getElementById('LRUpdateDo').action = "deptLrModifyAndApprove.action";
	}
	if(document.getElementById('srcAction').value == 'hrLeaveSearch'){
	    document.getElementById('LRUpdateDo').action = "hrLrModifyAndApprove.action";
	}
	if(document.getElementById('srcAction').value == 'allLeaveSearch'){
	    document.getElementById('LRUpdateDo').action = "allLrModifyAndConfirm.action";
	}
	document.getElementById('LRUpdateDo').submit();
	return true;
  }
</script>
</body>
</html>
