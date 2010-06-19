<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
	<!-- css修饰信息 -->
	<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
	<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	 
	<!--  基础js-->		 
  	<script type="text/javascript" src="../dwr/interface/DWRSystemConfigReader.js"></script>		 
	<title>修改我的请假申请</title>
</head>
<body onload="cacheLeaveCat();"> 
	<s:component template="bodyhead">
	</s:component>
	<br/>
	<s:form id="LRUpdateDo" name="LRUpdateDo" namespace="/examin"  action="LRUpdateDo.action"  method="post">
	
		<!--隐藏域-->
		<s:hidden id="lrUpdateId" name="lrUpdateId" />
		<s:hidden id="srcAction" name="srcAction"></s:hidden>
		<s:hidden name="gmanager" id="gmanager"></s:hidden>
		<s:hidden name="lf_Bean.lrEmpNo.id" id="empId" ></s:hidden>
		<s:hidden name="lf_Bean.lrEmpNo.empShiftType" id="empShiftType"></s:hidden>
		<s:hidden name="lf_Bean.lrEmpNo.empLocationNo.id" id="empLocationNo.id"></s:hidden>
		<s:hidden name="lf_Bean.lrLtNo.ltNeedComments" id="ltNeedComments" />
		<table width="100%">
		           <tr>
		               <td width="3%" align="right">
			               <img src="../resource/images/h3Arrow.gif">
		               </td>
		               <td>
			               <h3>修改请假</h3>
		               </td>
		          </tr>
		        </table>
		
		<table cellpadding="0" cellspacing="0" align="left" width="100%">
			<tr>
			    <td width="13%" align="right">
			    	请假单编号:
				</td>
			    <td>
					<s:property value="lr.lrNo" />
			    </td>
			</tr>
			<tr>
			    <td align="right">
			    	请假总时间:
			    </td>
			    <td>
					<s:property value="lf_Bean.totalTime" />
			    </td>
			</tr>
			<tr>
				<td align="right">
					员工姓名:
				</td>
			<td>
				<s:property value="lf_Bean.lrEmpNo.empName" />
		    </td>
		    </tr>
			<tr>
				<td align="right">
					请假种类<span class="required">*</span>:
				</td>
				<td>
					<s:select id="lf_Bean.lrLtNo.ltNo" name="lf_Bean.lrLtNo.ltNo"  list="lf_Bean.allLtType" listKey="ltNo" listValue="ltName"  emptyOption="false" onchange="showOrDisplayFlag(this.value);"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label>开始日期<span class="required">*</span>:</label>
				</td>
				<td>
				    <s:textfield id="lf_Bean.beginDate" name="lf_Bean.beginDate" required="true" size="12" />						 
				    <img onclick="WdatePicker({el:'lf_Bean.beginDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					<s:if test="lf_Bean.applyLRByDay">
						<s:select id="lf_Bean.beginAPM" name="lf_Bean.beginApm" list="#{0:'上午',1:'下午'}" emptyOption="false" required="true"/>
					</s:if>
					<s:else>
						<s:select id="lf_Bean.beginHour" name="lf_Bean.beginHour" list="@com.hr.examin.action.beans.Hours@getHourList()" listKey="hour" listValue="description" required="true" emptyOption="false" required="true" />
						<s:select id="lf_Bean.beginMinute" name="lf_Bean.beginMinute" list="#{0:'整',30:'半'}" required="true" emptyOption="false"></s:select>
					</s:else>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label>结束日期<span class="required">*</span>:</label>
				</td>
				<td>
					<s:textfield id="lf_Bean.endDate" name="lf_Bean.endDate" required="true" size="12" />
					<img onclick="WdatePicker({el:'lf_Bean.endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
					<s:if test="lf_Bean.applyLRByDay">
						<s:select id="lf_Bean.endAPM" name="lf_Bean.endApm" list="#{0:'上午',1:'下午'}" required="true" emptyOption="false" />
					</s:if>
					<s:else>
						<s:select id="lf_Bean.endHour" name="lf_Bean.endHour" list="@com.hr.examin.action.beans.Hours@getHourList_24()" listKey="hour" listValue="description" required="true" emptyOption="false" required="true" />
						<s:select id="lf_Bean.endMinute" name="lf_Bean.endMinute" list="#{0:'整',30:'半'}" required="true" emptyOption="false"></s:select>
					</s:else>
				</td>
			</tr>
			<tr>
			<td align="right">
				请假理由<span id="reasonRedFlag" class="required">*</span>:
			</td>
			<td>
				<s:textarea id="lf_Bean.lrReason" name="lf_Bean.lrReason" cols="40" rows="6"/>
			</td>
			</tr>
			<!--提交信息-->
			<tr align="left">
				<td></td>
				<td height="1" colspan="9">						
					<input title="[Alt+S]" accesskey="S" id="submited" name="submited" class="button" type="submit" onclick="return submitLrUpdateRequest();" value="修改">	
					<input title="[Alt+A]" accesskey="A" id="reset" name="reset" class="button" type="button" value="重置" >
				</td>
			</tr>
	  </table>
	</s:form>
<script type="text/javascript">
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）

function submitLrUpdateRequest(){
  if(document.getElementById("lf_Bean.lrReason").value.length>255){
      alert("请假理由太长，最长为255个字符！");
      return false;
  }
  return readRemindContent();
  }
//缓存请假种类和请假特殊编码的map,key:种类uuid，value:编码
var CAT_CACH_MAP = {};
function cacheLeaveCat(){
<s:iterator value="lf_Bean.allLtType">
	var lt = new Object();
	lt.ltNo='<s:property value="ltNo"/>';
	lt.ltSpecialCat = '<s:property value="ltSpecialCat"/>';
	lt.ltName = '<s:property value="ltName"/>';
	lt.ltNeedComments = '<s:property value="ltNeedComments"/>';
	CAT_CACH_MAP[lt.ltNo] = lt;
</s:iterator>
}

//读取提示语句
function readRemindContent(){
 var leaverequest = createLeaveRequest();
 DWRSystemConfigReader.readLeaveRequestRemindContent(leaverequest,callback);
 function callback(data){
if(data == null || data.length == 0){
	return;
}	
 var msg = "确定要提交本次请假吗？";
 msg += "\n"+data;
    if(!confirm(msg)){
       return false;
    }
document.getElementById('submited').disabled=true;
  	document.getElementById('LRUpdateDo').submit();
  	return true;
 }
 return false;
}

//组装leaverequest对象
function createLeaveRequest(){
//请假种类对象
	var selectedLeaveType = document.getElementById("lf_Bean.lrLtNo.ltNo").value;
	var lt = CAT_CACH_MAP[selectedLeaveType];
	var lr = new Object();
	lr.lrLtNo = lt;//请假种类
	lr.lrEmpNo=new Object();//员工信息
	lr.lrEmpNo.id = document.getElementById("empId").value;
	lr.lrEmpNo.empShiftType= document.getElementById("empShiftType").value;
	lr.lrEmpNo.empLocationNo = new Object();
	lr.lrEmpNo.empLocationNo.id = document.getElementById("empLocationNo.id").value;
	var startDate = document.getElementById('lf_Bean.beginDate').value;
	var endDate = document.getElementById('lf_Bean.endDate').value;
	if(document.getElementById('lf_Bean.applyLRByDay')){//按天
		lr.lrStartApm = document.getElementById('lf_Bean.beginApm').value;
		lr.lrEndApm = document.getElementById('lf_Bean.endApm').value;
		lr.lrStartDate = createDate(startDate);
		lr.lrEndDate = createDate(endDate);
	}else{
		lr.lrStartApm = null;
		lr.lrEndApm = null;
		startDate += " " + document.getElementById('lf_Bean.beginHour').value;
		startDate += "-" + document.getElementById('lf_Bean.beginMinute').value;
		endDate += " " + document.getElementById('lf_Bean.endHour').value;
		endDate += "-" + document.getElementById('lf_Bean.endMinute').value;
		lr.lrStartDate = createDatetime(startDate);
		lr.lrEndDate = createDatetime(endDate);
	}
	return lr;
}


function createDate(dateStr){
	var dateArr = dateStr.split("-");
	var date = new Date(dateArr[0],dateArr[1]-1,dateArr[2]);
	return date;
}

function createDatetime(datetimeStr){
	var datetimeArr = datetimeStr.split(" ");
	var datetime = createDate(datetimeArr[0]);
	var timeArr = datetimeArr[1].split('-');
	datetime.setHours(timeArr[0]);
	datetime.setMinutes(timeArr[1]);
	return datetime;
}


// 根据请假的种类决定是否显示红色必填标记；
function showOrDisplayFlag(ltId){
   var lt = CAT_CACH_MAP[ltId];
	var needComments = lt.ltNeedComments;
   if(needComments == 1){
     document.getElementById("reasonRedFlag").innerHTML = "*";
	}else{
     document.getElementById("reasonRedFlag").innerHTML = "";
	}
}
</script>
</body>
</html>
