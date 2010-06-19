<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>		
		<!-- css修饰信息 -->
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>			
		<title>加班单修改并审批</title>
	</head>
	<body>
	   	<s:component template="bodyhead">
		</s:component>
		<br/>
		<form id="addovertimerequest" name="addovertimerequest" method="POST" action="allOtModifyAndConfirm.action">

        	<!-- 隐藏字段 -->
			<s:hidden id="approveOper" name="approveOper"></s:hidden>
			<s:hidden id="infoMeg" name="infoMeg" />
			<s:hidden id="srcAction" name="srcAction"/>
			<s:hidden id="gmanager" name="gmanager"/>
			<s:hidden id="orIdUp" name="orIdUp"/>
			<s:hidden id="otr.orTotalHours" name="otr.orTotalHours"></s:hidden>
			<s:hidden id="of_Bean.orEmpNo.id" name="of_Bean.orEmpNo.id"/>
			<s:hidden id="of_Bean.isTiaoxiu" name="of_Bean.isTiaoxiu"></s:hidden>
			
				<!-- 废弃字段 
			<s:hidden name="isTiaoxiu" id="isTiaoxiu"></s:hidden>
			<s:hidden name="of_Bean.or.orId" id="of_Bean.or.orId"></s:hidden>
			<s:hidden name="orTiaoxiuHours" id="orTiaoxiuHours" value="%{of_Bean.or.orTiaoxiuHours}"></s:hidden>
			<s:hidden name="orTotalHours" id="orTotalHours" value="%{of_Bean.or.orTotalHours}"></s:hidden>
				废弃字段 --> 

			<table width="100%">
			<tr>
			<td width="3%">
				<img src="../resource/images/h3Arrow.gif">
			</td>
			<td>
				<h3>加班申请确认</h3>
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
				        <s:textfield  id="date" name="of_Bean.startDate" required="true" size="12"/>
				         <img onclick="WdatePicker({el:'date'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">	
       			    </td>
       			<td rowspan="4">
       				    备注:
       			</td>
       			<td rowspan="4" colspan="3">
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
					  <td>
					    <label >是否调休<span class="required">*</span>:</label>
					  </td>
					  <td>
					     <s:if test="of_Bean.isTiaoxiu">
					          <input type="radio" class="radio" name="tiaoxiu" id="tiaoxiu1" value="0"  onclick="hideTiaoxiu();"/>否 
		                      <input type="radio" class="radio" name="tiaoxiu" id="tiaoxiu2" value="1" checked onclick="showTiaoxiu();"/>是
					     </s:if>
					     <s:else>
					          <input type="radio" class="radio" name="tiaoxiu" id="tiaoxiu1" value="0" checked onclick="hideTiaoxiu();"/>否 
		                      <input type="radio" class="radio" name="tiaoxiu" id="tiaoxiu2" value="1" onclick="showTiaoxiu();"/>是 
					     </s:else>
					  </td>
				</tr>
				<tr>
		            <td>
		            <div id="div1"><label >调休时间：</label></div>
		                    
		            </td>
		            <td id="tiaoXiuTR" align="left">
		                <s:textfield id="of_Bean.orTiaoxiuHours" name="of_Bean.orTiaoxiuHours" maxLength="6" size="6" onkeyup="value=value.replace(/[^\d \.]/g,'')"></s:textfield>小时
		                <s:if test="expireTiaoxiu == 1">
		                    <s:textfield  label="有效期至" id="otTiaoxiuExpire" name="otTiaoxiuExpire" required="true" size="12" />
		                    <img onclick="WdatePicker({el:'otTiaoxiuExpire'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">								    																			
		                </s:if>
		            </td>
		         
		        </tr>
		        <tr><td></td></tr>
				<tr>
					<td height="1" colspan="4" align="center">						
							<input id="sub" name="sub" type="button" value="批准" onclick=" return submitOtRequest('approve');">	
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
configTiaoXiu();
//提交或拒绝(公用方法)
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
    if(!confirm("您确定要提交该条加班单吗？")){
        return false;
    }
    
    document.getElementById('approveOper').value = operate;
    document.getElementById("addovertimerequest").submit();
    return true;
}
       
/***对是否显示调休的文本框进行设置***/
function configTiaoXiu(){
     var t=document.getElementById("of_Bean.isTiaoxiu").value;
    if(t=="true"){
        showTiaoxiu();
    }else{
        hideTiaoxiu();
   var t1=document.getElementById("of_Bean.orTiaoxiuHours").value;
  if(t1==null || t1=="") { // 没有值的话，预设totalHours作为调休Hours
 	document.getElementById("of_Bean.orTiaoxiuHours").value=document.getElementById("otr.orTotalHours").value;
 }
    }
}
     
     
/**隐藏 调休时间 所在行***/
function hideTiaoxiu(){
     document.getElementById("tiaoXiuTR").style.display="none";
     document.getElementById("div1").style.display="none";
     document.getElementById("of_Bean.isTiaoxiu").value="false";
}
	    
/**显示 调休时间 所在行***/
function showTiaoxiu(){
    document.getElementById("tiaoXiuTR").style.display="block";
    document.getElementById("div1").style.display="block";
    document.getElementById("of_Bean.isTiaoxiu").value="true";
}
</script>  
</body>
</html>
