<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="jscalendar" uri="/jscalendar" %>
<!--
 =========================================================
' File: employee_update.jsp
' Version:1.1.0 standard version
' Date: 2007-2-2
' Script Written by tengsource.com
'=========================================================
' Copyright   2007 tengsource.com. All rights reserved.
' Web: http://www.tengsource.com
' Email: admin@tengsource.com
'=======================================================
 -->
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<!-- DWR文件引用 -->
<script type="text/javascript" src="../dwr/interface/my.js"></script>
<script type="text/javascript" src="../dwr/interface/EditEmpInit.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpAddUpdate.js"></script>

<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<title>修改员工信息</title>
</head>
<body onload="display_practice_yearOrMonth();">
<div id="selectcontent" class="selectdiv" style="visibility:hidden;pixelHeight:20px;z-index:9;">
	<iframe id="selframe" frameborder="0" height="100%"></iframe>
	<div id="selecthtml" class="selectcontent">selectdate</div>
</div>
<!-- 可输入的select -->
<script type="text/javascript" src="../resource/js/edit_select.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />

<div id="profile_update">
	<s:component template="bodyhead"/>
	<s:form name="updateEmployee" action="updateEmp" namespace="/profile" method="POST" enctype="multipart/form-data">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	        	<td align="right">员工编号<span class="required">*</span>:</td>
	        	<td>
					<s:textfield id="empNo" name="emp.empDistinctNo" size="20" required="true" maxlength="64" tabindex="1"
						cssClass="textDisabled" readonly="true" onkeyup="hrm.profile.createAutoNoOnKeyUp(event,this,'autoCreateEmpNo');"/>
					<input type="hidden" name="oldEmpDistinctNo" value="<s:property value='emp.empDistinctNo'/>"/>
					<ty:auth auths="101">
						<input onclick='doChange();' type='button' value='修改编号'></input>
					</ty:auth>
				</td>
				<td align="right">邮箱地址:</td>
                <td colspan="3">
                    <s:textfield name="emp.empEmail" size="23"  maxlength="64" tabindex="24"
						onkeyup="hrm.common.notChinese(event,this);" onblur="hrm.common.checkEmail(this);"/>
                	<s:if test="emp.empEmail!=null && emp.empEmail!='' && #session.userinfo.employee.id!=emp.id">
                		<a class="listViewTdLinkS1" href="mailto:<s:property value='emp.empEmail'/>">
                			<img alt="发送邮件" src="../resource/images/Emails.gif"/>
                		</a>
                	</s:if>
                </td>
                <s:hidden name="emp.empImage"/>
                <s:hidden name="emp.empCreateBy.id"/>
                <s:hidden name="emp.id"/>
                <s:hidden name="emp.empCreateTime"/>
                <s:hidden name="emp.empImportByInterface"/>
                <s:hidden name="emp.quit.eqId"/>
                <s:hidden name="emp.benefit.ebfId"/>
                <s:hidden name="emp.config.id"/>
                <s:hidden name="emp.contract.ectId"/><s:token/>
                <td></td>
                <td></td>
               
                <td rowspan="6">
                     <img border="1" id="showPicture" style="border:1px darkslategray outset;" name="showPicture" align="left" width="88"
                     	src="../servlet/showImage?style=head&fileName=<s:property value='emp.empImage'/>" height="112"/>
                </td>
	        </tr>
		    <%@ include file="employee_form.jsp"%>
		    <tr>
				<s:textfield label="邮编" name="emp.empHomeZip" size="8" maxlength="6" tabindex="23"
					onkeyup="hrm.common.notChinese(event,this);" onblur="hrm.common.checkZip(this)"/>
				<td align="right">状态<span class="required">*</span>:</td>
				<td>
	                <s:hidden name="emp.empStatus"/>
	                <s:hidden name="emp.empTerminateDate"/>
	                <s:hidden name="emp.empLastChangeTime"/>
	                <s:if test="emp.empStatus==1">
	                    <input value="在职" class="textDisabled" size="10" tabindex="42" disabled="disabled"/>
	                   	&nbsp;&nbsp;最后修改: <s:date name="emp.empLastChangeTime" format="yyyy-MM-dd hh:mm"/>
	                </s:if>
	                <s:else>
	                    <input value="离职" class="textDisabled" size="10" tabindex="42" disabled="disabled"/>
	                   	&nbsp;&nbsp;&nbsp;&nbsp;离职日期: <s:date name="emp.empTerminateDate" format="yyyy-MM-dd"/>
	                </s:else>
				</td>
	        </tr>
		    <tr>
	            <td colspan="6" align="center" height="40">
		        	<br />
	                <input type="submit" value="修改" tabindex="43" refreshId="tabContent">
	                &nbsp;<input type="reset" value="返回" tabindex="44" onclick="history.back(-1);">
	                &nbsp;<input type="button" value="员工资料卡" tabindex="45" onclick="hrm.profile.showInfoCardInfo('<s:property value="emp.id"/>');"> 
	            </td>
	        </tr>
		</table>
	</s:form>
</div>
<%@ include file="search_emp_div.jsp"%>
<!-- 自动计算编号显示的div -->
<div id="autoCreateEmpNo" style="position:absolute;z-index:5;width:146px;display:none;solid;text-align:left;" class="prompt_div_inline"></div>
<!-- 自动计算编号 -->

<script type="text/javascript">
function doChange(){
	if(confirm("如果修改了员工编号，可能会影响该员工的其他资料信息，是否确定修改？")){
		if(!hrm.common.navigatorIsIE()){
			$('#empNo')[0].removeAttribute('readonly');
			$('#empNo')[0].removeAttribute('class');
		}else{
			$('#empNo')[0].readOnly=false;
			$('#empNo')[0].className="";
		}
	}
}
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）
parent.document.getElementById('parentEmpName').innerHTML ='<s:property value="empName"/>';
</script> 
<jsp:include flush="true" page="position_choose_div.jsp"></jsp:include>                        
</body>	