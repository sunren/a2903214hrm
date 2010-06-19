<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>		
		<!-- css修饰信息 -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>			 	   
		<title>我的加班单添加</title>
	</head>
	<body>
	   	<s:component template="bodyhead">
		</s:component>
		<br/>
		<form id="addovertimerequest" name="addovertimerequest" method="POST" action="deptOtModifyAndApprove.action">

        	<!-- 隐藏字段 -->
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
				<h3>修改并审批加班申请</h3>
			</td>
			</tr>
			</table>
			<table width="100%" class="formtable">
			<!--  showOtInfo -->
				<tr>							
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						员工编号：
					</td>
					<td align="left" >
						<s:property value="otr.orEmpNo.empDistinctNo" />
					</td>
			       <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						员工姓名：
					</td>
					<td align="left" nowrap>
						<s:property value="otr.orEmpNo.empName"/>
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						所在部门：
					</td>
					<td align="left">
						<s:property value="otr.orEmpDept.departmentName"/>
					</td>
				</tr>
				<tr>							
					
			       <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						加班单编号：
					</td>
					<td align="left" >
						<s:property value="otr.orNo"/>
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						加班种类：
					</td>
					<td align="left" nowrap>
						<s:property value="otr.orOtNo.otName"/>
					</td>
					 <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						当前状态：
					</td>
					<td align="left">
						<s:property value="of_Bean.otStatus" />
					</td>
				</tr>
				
				<tr>							
					<td align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						加班开始时间：
					</td>
					<td align="left" >
						<s:date name="otr.orStartDate"  format="yyyy-MM-dd HH:mm" />
					</td>
			       <td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						加班结束时间：
					</td>
					<td align="left"  nowrap>
						<s:date name="otr.orEndDate"  format="yyyy-MM-dd HH:mm" />
					</td>
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						加班总时间：
					</td>
					<td align="left" >
						<s:property value="otr.orTotalHours" />&nbsp;小时
					</td>
				</tr>
				
				<tr>							
					<td  align="left" nowrap width="10%" style="padding:6px 6px 6px 6px">
						本月已批准加班：
					</td>
					<td align="left" nowrap>
						<s:property value="of_Bean.usedTime" />小时
					</td>
					<td  align="left"  nowrap width="10%" style="padding:6px 6px 6px 6px">
						加班理由：
					</td>
					<td align="left"   colspan="3">
						<s:property value="otr.orReason" />
					</td>
				</tr>

			</table>
			<TABLE width="100%" class="formtable">
				<tr>
				    <td>
				        日期：
				    </td>
				    <td>
				      <s:textfield  id="date" name="of_Bean.startDate" required="true" size="12"  />				       
				      <img onclick="WdatePicker({el:'date'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">	
       			    </td>
       			<td rowspan="3">
       				    备注:
       			</td>
       			<td rowspan="3" colspan="3">
       				    <s:textarea name="of_Bean.orAppComment" cols="50" rows="4"  required="true" id="of_Bean.orAppComment"/>
       			</td>
				</tr>
				<tr>
				      <td>
				        <label >开始时间<span class="required">*</span>:</label>
					  </td>
					  <td>
					      <s:select id="startTime" name="of_Bean.startTime" list="#{'0':'0点','1':'1点','2':'2点','3':'3点','4':'4点','5':'5点','6':'6点','7':'7点','8':'8点','9':'9点','10':'10点','11':'11点','12':'12点','13':'13点','14':'14点','15':'15点','16':'16点','17':'17点','18':'18点','19':'19点','20':'20点','21':'21点','22':'22点','23':'23点'}" emptyOption="true" required="true"  />
					      <s:select name="of_Bean.startTimeMinute" list="#{'0':'整','30':'半'}"	/>
					  </td>
				</tr>
				<tr>
					  <td>
					    <label >结束时间<span class="required">*</span>:</label>
					  </td>
					  <td>
					     <s:select id="endTime" name="of_Bean.endTime" list="#{'0':'0点','1':'1点','2':'2点','3':'3点','4':'4点','5':'5点','6':'6点','7':'7点','8':'8点','9':'9点','10':'10点','11':'11点','12':'12点','13':'13点','14':'14点','15':'15点','16':'16点','17':'17点','18':'18点','19':'19点','20':'20点','21':'21点','22':'22点','23':'23点','24':'24点'}" emptyOption="true" required="true"  />
					     <s:select name="of_Bean.endTimeMinute" list="#{'0':'整','30':'半'}"	/>
					  </td>
				</tr>
				
				<tr>
				    <td></td>
				</tr>
				<tr>
					<td height="1" colspan="4" align="center">						
							<input id="approveBtn" name="sub"   type="button" value="批准" onclick="submitOtRequest('approve');">	
							<input id="rejectBtn" name="sub"   type="button" value="拒绝" onclick="submitOtRequest('reject');">						
							<input  type="reset" name="reset" value="重置">
					</td>
				</tr>
			</TABLE>
	<!--  showOtLogInfo!!! -->		
			<br>
			<s:if test="of_Bean.logList!=null && of_Bean.logList.size()>0">
			    <s:iterator value="of_Bean.logList">
				  <p align="left">
				   &nbsp;
				   <s:property value="%{slChangeEmpno.empName}" />于
						    <s:date name="slChangeTime" format="yyyy-MM-dd HH:mm:ss" />
						    <s:property value="%{slAction}" />
					        <s:if test="slComments!=null && slComments!='' && slComments.length>0">
					            &nbsp;备注信息：<s:property value="%{slComments}" />
					        </s:if>
					</p>       
		
			    </s:iterator>
			</s:if>
		</form>
<script type="text/javascript">
//批准或批准(公用方法)
function submitOtRequest(operate){ 
    var comm=document.getElementById("of_Bean.orAppComment").value;
    if(comm==null || comm==""){
        alert("备注信息不能为空！");
        return false;
    }
    if(comm.length>255){
        alert("备注信息太长，最长为255个字符！");
        return false;
    }
    var approveMsg="您确定要提交该条加班单吗";
    var rejectMsg="您确定要拒绝该条加班单吗？";
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
