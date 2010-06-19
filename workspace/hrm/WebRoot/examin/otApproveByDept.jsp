<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>		
		<!-- css������Ϣ -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>			 	   
		<title>�ҵļӰ൥���</title>
	</head>
	<body>
	   	<s:component template="bodyhead">
		</s:component>
		<br/>
		<form id="addovertimerequest" name="addovertimerequest" method="POST" action="deptOtModifyAndApprove.action">

        	<!-- �����ֶ� -->
			<s:hidden id="approveOper" name="approveOper"></s:hidden>
			<s:hidden id="infoMeg" name="infoMeg" />
			<s:hidden id="srcAction" name="srcAction"/>
			<s:hidden id="gmanager" name="gmanager"/>
			<s:hidden id="orIdUp" name="orIdUp"/>
			<s:hidden name="otr.orTotalHours" id="otr.orTotalHours"></s:hidden>
			<s:hidden id="of_Bean.orEmpNo.id" name="of_Bean.orEmpNo.id"/>
			<s:hidden name="of_Bean.isTiaoxiu" id="of_Bean.isTiaoxiu"></s:hidden>

			<table width="100%">
			<tr>
			<td width="3%">
				<img src="../resource/images/h3Arrow.gif">
			</td>
			<td>
				<h3>�޸Ĳ������Ӱ�����</h3>
			</td>
			</tr>
			</table>
			<table width="100%" class="formtable">
			<!--  showOtInfo -->
				<tr>							
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						Ա����ţ�
					</td>
					<td align="left" >
						<s:property value="otr.orEmpNo.empDistinctNo" />
					</td>
			       <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						Ա��������
					</td>
					<td align="left" nowrap>
						<s:property value="otr.orEmpNo.empName"/>
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						���ڲ��ţ�
					</td>
					<td align="left">
						<s:property value="otr.orEmpDept.departmentName"/>
					</td>
				</tr>
				<tr>							
					
			       <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						�Ӱ൥��ţ�
					</td>
					<td align="left" >
						<s:property value="otr.orNo"/>
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						�Ӱ����ࣺ
					</td>
					<td align="left" nowrap>
						<s:property value="otr.orOtNo.otName"/>
					</td>
					 <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						��ǰ״̬��
					</td>
					<td align="left">
						<s:property value="of_Bean.otStatus" />
					</td>
				</tr>
				
				<tr>							
					<td align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						�Ӱ࿪ʼʱ�䣺
					</td>
					<td align="left" >
						<s:date name="otr.orStartDate"  format="yyyy-MM-dd HH:mm" />
					</td>
			       <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						�Ӱ����ʱ�䣺
					</td>
					<td align="left"  nowrap>
						<s:date name="otr.orEndDate"  format="yyyy-MM-dd HH:mm" />
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						�Ӱ���ʱ�䣺
					</td>
					<td align="left" >
						<s:property value="otr.orTotalHours" />&nbsp;Сʱ
					</td>
				</tr>
				
				<tr>							
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						��������׼�Ӱࣺ
					</td>
					<td align="left" nowrap>
						<s:property value="of_Bean.usedTime" />Сʱ
					</td>
					<td  align="left"  nowrap width="10%" style="padding:6px 6px 6px 6px">
						�Ӱ����ɣ�
					</td>
					<td align="left"   colspan="3">
						<s:property value="otr.orReason" />
					</td>
				</tr>

			</table>
			<TABLE width="100%" class="formtable">
				<tr>
				    <td>
				        ���ڣ�
				    </td>
				    <td>
				      <s:textfield  id="date" name="of_Bean.startDate" required="true" size="12"  />				       
				      <img onclick="WdatePicker({el:'date'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">	
       			    </td>
       			<td rowspan="3">
       				    ��ע:
       			</td>
       			<td rowspan="3" colspan="3">
       				    <s:textarea name="of_Bean.orAppComment" cols="50" rows="4"  required="true" id="of_Bean.orAppComment"/>
       			</td>
				</tr>
				<tr>
				      <td>
				        <label >��ʼʱ��<span class="required">*</span>:</label>
					  </td>
					  <td>
					      <s:select id="startTime" name="of_Bean.startTime" list="#{'0':'0��','1':'1��','2':'2��','3':'3��','4':'4��','5':'5��','6':'6��','7':'7��','8':'8��','9':'9��','10':'10��','11':'11��','12':'12��','13':'13��','14':'14��','15':'15��','16':'16��','17':'17��','18':'18��','19':'19��','20':'20��','21':'21��','22':'22��','23':'23��'}" emptyOption="true" required="true"  />
					      <s:select name="of_Bean.startTimeMinute" list="#{'0':'��','30':'��'}"	/>
					  </td>
				</tr>
				<tr>
					  <td>
					    <label >����ʱ��<span class="required">*</span>:</label>
					  </td>
					  <td>
					     <s:select id="endTime" name="of_Bean.endTime" list="#{'0':'0��','1':'1��','2':'2��','3':'3��','4':'4��','5':'5��','6':'6��','7':'7��','8':'8��','9':'9��','10':'10��','11':'11��','12':'12��','13':'13��','14':'14��','15':'15��','16':'16��','17':'17��','18':'18��','19':'19��','20':'20��','21':'21��','22':'22��','23':'23��','24':'24��'}" emptyOption="true" required="true"  />
					     <s:select name="of_Bean.endTimeMinute" list="#{'0':'��','30':'��'}"	/>
					  </td>
				</tr>
				
				<tr>
				    <td></td>
				</tr>
				<tr>
					<td height="1" colspan="4" align="center">						
							<input id="approveBtn" name="sub"   type="button" value="��׼" onclick="submitOtRequest('approve');">	
							<input id="rejectBtn" name="sub"   type="button" value="�ܾ�" onclick="submitOtRequest('reject');">						
							<input  type="reset" name="reset" value="����">
					</td>
				</tr>
			</TABLE>
	<!--  showOtLogInfo!!! -->		
			<br>
			<s:if test="of_Bean.logList!=null && of_Bean.logList.size()>0">
			    <s:iterator value="of_Bean.logList">
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
		</form>
<script type="text/javascript">
//��׼����׼(���÷���)
function submitOtRequest(operate){ 
    var comm=document.getElementById("of_Bean.orAppComment").value;
    if(comm==null || comm==""){
        alert("��ע��Ϣ����Ϊ�գ�");
        return false;
    }
    if(comm.length>255){
        alert("��ע��Ϣ̫�����Ϊ255���ַ���");
        return false;
    }
    var approveMsg="��ȷ��Ҫ�ύ�����Ӱ൥��";
    var rejectMsg="��ȷ��Ҫ�ܾ������Ӱ൥��";
    if(!hrm.common.isNull(operate) && operate == 'approve' && !confirm(approveMsg)){
  	    return false;
    }
    if(!hrm.common.isNull(operate) && operate == 'reject' && !confirm(rejectMsg)){
	    return false;
    }
       
    document.getElementById('approveBtn').disabled=true;
    document.getElementById('rejectBtn').disabled=true;
    document.getElementById('approveOper').value = operate;
    document.getElementById("addovertimerequest").submit();
}
</script>  
</body>
</html>
