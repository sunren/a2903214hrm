<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
	<!-- css������Ϣ -->
	<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
	<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>		 	 
    <script type="text/javascript" src="../dwr/interface/DWRSystemConfigReader.js"></script>
    <script type="text/javascript" src="../dwr/interface/EmpExaminAction.js"></script>
	<title>�����ҵ��������</title>
	</head>
	<body onload="cacheLeaveCat();">
		<s:component template="bodyhead">
		</s:component>
		<br/>

		<form id="empLRAdd"  name="empLRAdd" action="empLRAddDo.action" method="post" >
			<!--������-->
			<s:token/>
			<s:hidden id="infoMeg" name="infoMeg"  />
			<s:hidden name="lf_Bean.lrEmpNo.id" id="empId" ></s:hidden>
			<s:hidden name="lf_Bean.lrEmpNo.empShiftType" id="empShiftType"></s:hidden>
			<s:hidden name="lf_Bean.lrEmpNo.empLocationNo.id" id="empLocationNo.id"></s:hidden>
			<s:hidden name="lf_Bean.applyLRByDay" id="lf_Bean.applyLRByDay"></s:hidden>
			<table width="100%">
			    <tr>
			        <td width="3%" align="right">
				        <img src="../resource/images/h3Arrow.gif">
			        </td>
			        <td>
				        <h3>�������</h3>
			        </td>
			    </tr>
			 </table>
			<table width="100%" cellpadding="0" cellspacing="0" align="left">
			    <tr>
					<td width="13%" align="right">
						Ա������<span class="required">*</span>:
					</td>
					<td>
				        <s:textfield id="empName" name="lf_Bean.lrEmpNo.empName" required="true" readonly="true"/>
				        <s:set name="auth" value="@com.hr.util.DatabaseSysConfigManager@getInstance()"/>
				        <s:if test="#request.auth.getProperty('sys.examin.create.level')==1">
				        	<ty:auth auths="401 or 411,2 or 411,3">
				        		<img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick="showChooseEmpDiv(4,1);" alt='���ͼ��ѡ��Ա��'/>
				        	</ty:auth>
				        </s:if>
				    </td>        
			    </tr>			
				<tr>
				    <td align="right">
						�������<span class="required">*</span>:
					</td>
					<td>
					<s:select id="lf_Bean.lrLtNo.ltNo"  name="lf_Bean.lrLtNo.ltNo"  list="lf_Bean.allLtType" listKey="ltNo" listValue="ltName" emptyOption="false" required="true" onchange="showOrDisplayFlag(this.value);"/>
				
					</td>
				</tr>
				<tr>
					<td align="right">
						��ʼ����<span class="required">*</span>:
					</td>
					<td>
						<s:textfield  id="lf_Bean.beginDate" name="lf_Bean.beginDate" required="true" size="12" />
						<img onclick="WdatePicker({el:'lf_Bean.beginDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						<s:if test="lf_Bean.applyLRByDay">
							<s:select id="lf_Bean.beginApm" name="lf_Bean.beginApm" list="#{0:'����',1:'����'}" emptyOption="false" required="true"/>
						</s:if>
						<s:else>
							<s:select id="lf_Bean.beginHour" name="lf_Bean.beginHour" list="@com.hr.examin.action.beans.Hours@getHourList()" listKey="hour" listValue="description" required="true" emptyOption="false" required="true" />
							<s:select id="lf_Bean.beginMinute" name="lf_Bean.beginMinute" list="#{0:'��',30:'��'}" required="true" emptyOption="false"></s:select>
						</s:else>
					</td>
				</tr>
				<tr>
					<td align="right">
						��������<span class="required">*</span>:
					</td>
					<td>
						<s:textfield  id="lf_Bean.endDate" name="lf_Bean.endDate" required="true" size="12"  />
						<img onclick="WdatePicker({el:'lf_Bean.endDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						<s:if test="lf_Bean.applyLRByDay">
							<s:select id="lf_Bean.endApm" name="lf_Bean.endApm" list="#{0:'����',1:'����'}" required="true" emptyOption="false" />
						</s:if>
						<s:else>
							<s:select id="lf_Bean.endHour" name="lf_Bean.endHour" list="@com.hr.examin.action.beans.Hours@getHourList_24()" listKey="hour" listValue="description" required="true" emptyOption="false" required="true" />
							<s:select id="lf_Bean.endMinute" name="lf_Bean.endMinute" list="#{0:'��',30:'��'}" required="true" emptyOption="false"></s:select>
						</s:else>
					</td>
				</tr>
				<tr>
				   <td align="right">
						�������<span id="reasonRedFlag" class="required">*</span>:
					</td>
					<td align="left">
					<s:textarea id="lf_Bean.lrReason" name="lf_Bean.lrReason" cols="40" rows="6"/>
				    </td>
				</tr>
				<!--�ύ��Ϣ-->
				<tr>
					<td></td>
					<td height="1" colspan="9">						
						<input title="[Alt+S]" accesskey="S" id="submited" name="submited" class="button" type="button" onclick="submitLrRequest();" value="�ύ">	
						<input title="[Alt+A]" accesskey="A" id="reset" name="reset" class="button" type="button" value="����" >
					</td>
				</tr>
			</table>
		</form>
<script language="javascript">
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ�
  
function submitLrRequest(){
    if(document.getElementById("lf_Bean.lrReason").value.length>255){
        alert("��������Ϊ255���ַ���");
        return false;
    }
    return readRemindContent();
}

//�����������������������map,key:����uuid��value:����
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


//��ȡ��ʾ���
function readRemindContent(){

	var leaverequest = createLeaveRequest();
    DWRSystemConfigReader.readLeaveRequestRemindContent(leaverequest,callback);
	function callback(data){
	if(data == null || data.length == 0){
		return;
	}	
     if(!confirm(data)){
        return false;
     }
	document.getElementById('submited').disabled=true;
   	document.getElementById('empLRAdd').submit();
   	return true;
    }
    return false;
}
 
//��װleaverequest����
function createLeaveRequest(){
	//����������
	var selectedLeaveType = document.getElementById("lf_Bean.lrLtNo.ltNo").value;
	var lt = CAT_CACH_MAP[selectedLeaveType];
	var lr = new Object();
	lr.lrLtNo = lt;//�������
	lr.lrEmpNo=new Object();//Ա����Ϣ
	lr.lrEmpNo.id = document.getElementById("empId").value;
	lr.lrEmpNo.empLocationNo = new Object();
	lr.lrEmpNo.empLocationNo.id = document.getElementById("empLocationNo.id").value;
	var startDate = document.getElementById('lf_Bean.beginDate').value;
	var endDate = document.getElementById('lf_Bean.endDate').value;
	if(document.getElementById('lf_Bean.applyLRByDay').value=='true'){//����
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

// ������ٵ���������Ƿ���ʾ��ɫ�����ǣ�
function showOrDisplayFlag(ltId){
    var lt = CAT_CACH_MAP[ltId];
    var ltcur = new Object();
    ltcur = lt;
	var needComments = ltcur.ltNeedComments;
    if(needComments == 1){
	     document.getElementById("reasonRedFlag").innerHTML = "*";
	}else{
	     document.getElementById("reasonRedFlag").innerHTML = "";
	}
  }
</script>  
<%@ include file="../profile/search_emp_div.jsp"%>
</body>
</html>
