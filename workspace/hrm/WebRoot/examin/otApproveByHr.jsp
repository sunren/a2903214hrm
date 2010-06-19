<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>		
		<!-- css������Ϣ -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>			
		<title>�Ӱ൥�޸Ĳ�����</title>
	</head>
	<body>
	   	<s:component template="bodyhead">
		</s:component>
		<br/>
		<form id="addovertimerequest" name="addovertimerequest" method="POST" action="allOtModifyAndConfirm.action">

        	<!-- �����ֶ� -->
			<s:hidden id="approveOper" name="approveOper"></s:hidden>
			<s:hidden id="infoMeg" name="infoMeg" />
			<s:hidden id="srcAction" name="srcAction"/>
			<s:hidden id="gmanager" name="gmanager"/>
			<s:hidden id="orIdUp" name="orIdUp"/>
			<s:hidden id="otr.orTotalHours" name="otr.orTotalHours"></s:hidden>
			<s:hidden id="of_Bean.orEmpNo.id" name="of_Bean.orEmpNo.id"/>
			<s:hidden id="of_Bean.isTiaoxiu" name="of_Bean.isTiaoxiu"></s:hidden>
			
				<!-- �����ֶ� 
			<s:hidden name="isTiaoxiu" id="isTiaoxiu"></s:hidden>
			<s:hidden name="of_Bean.or.orId" id="of_Bean.or.orId"></s:hidden>
			<s:hidden name="orTiaoxiuHours" id="orTiaoxiuHours" value="%{of_Bean.or.orTiaoxiuHours}"></s:hidden>
			<s:hidden name="orTotalHours" id="orTotalHours" value="%{of_Bean.or.orTotalHours}"></s:hidden>
				�����ֶ� --> 

			<table width="100%">
			<tr>
			<td width="3%">
				<img src="../resource/images/h3Arrow.gif">
			</td>
			<td>
				<h3>�Ӱ�����ȷ��</h3>
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
				        <s:textfield  id="date" name="of_Bean.startDate" required="true" size="12"/>
				         <img onclick="WdatePicker({el:'date'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">	
       			    </td>
       			<td rowspan="4">
       				    ��ע:
       			</td>
       			<td rowspan="4" colspan="3">
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
					  <td>
					    <label >�Ƿ����<span class="required">*</span>:</label>
					  </td>
					  <td>
					     <s:if test="of_Bean.isTiaoxiu">
					          <input type="radio" class="radio" name="tiaoxiu" id="tiaoxiu1" value="0"  onclick="hideTiaoxiu();"/>�� 
		                      <input type="radio" class="radio" name="tiaoxiu" id="tiaoxiu2" value="1" checked onclick="showTiaoxiu();"/>��
					     </s:if>
					     <s:else>
					          <input type="radio" class="radio" name="tiaoxiu" id="tiaoxiu1" value="0" checked onclick="hideTiaoxiu();"/>�� 
		                      <input type="radio" class="radio" name="tiaoxiu" id="tiaoxiu2" value="1" onclick="showTiaoxiu();"/>�� 
					     </s:else>
					  </td>
				</tr>
				<tr>
		            <td>
		            <div id="div1"><label >����ʱ�䣺</label></div>
		                    
		            </td>
		            <td id="tiaoXiuTR" align="left">
		                <s:textfield id="of_Bean.orTiaoxiuHours" name="of_Bean.orTiaoxiuHours" maxLength="6" size="6" onkeyup="value=value.replace(/[^\d \.]/g,'')"></s:textfield>Сʱ
		                <s:if test="expireTiaoxiu == 1">
		                    <s:textfield  label="��Ч����" id="otTiaoxiuExpire" name="otTiaoxiuExpire" required="true" size="12" />
		                    <img onclick="WdatePicker({el:'otTiaoxiuExpire'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">								    																			
		                </s:if>
		            </td>
		         
		        </tr>
		        <tr><td></td></tr>
				<tr>
					<td height="1" colspan="4" align="center">						
							<input id="sub" name="sub" type="button" value="��׼" onclick=" return submitOtRequest('approve');">	
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
configTiaoXiu();
//�ύ��ܾ�(���÷���)
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
    if(!confirm("��ȷ��Ҫ�ύ�����Ӱ൥��")){
        return false;
    }
    
    document.getElementById('approveOper').value = operate;
    document.getElementById("addovertimerequest").submit();
    return true;
}
       
/***���Ƿ���ʾ���ݵ��ı����������***/
function configTiaoXiu(){
     var t=document.getElementById("of_Bean.isTiaoxiu").value;
    if(t=="true"){
        showTiaoxiu();
    }else{
        hideTiaoxiu();
   var t1=document.getElementById("of_Bean.orTiaoxiuHours").value;
  if(t1==null || t1=="") { // û��ֵ�Ļ���Ԥ��totalHours��Ϊ����Hours
 	document.getElementById("of_Bean.orTiaoxiuHours").value=document.getElementById("otr.orTotalHours").value;
 }
    }
}
     
     
/**���� ����ʱ�� ������***/
function hideTiaoxiu(){
     document.getElementById("tiaoXiuTR").style.display="none";
     document.getElementById("div1").style.display="none";
     document.getElementById("of_Bean.isTiaoxiu").value="false";
}
	    
/**��ʾ ����ʱ�� ������***/
function showTiaoxiu(){
    document.getElementById("tiaoXiuTR").style.display="block";
    document.getElementById("div1").style.display="block";
    document.getElementById("of_Bean.isTiaoxiu").value="true";
}
</script>  
</body>
</html>
