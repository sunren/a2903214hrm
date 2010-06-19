<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />	
<style type="text/css">
	body{margin:0;padding:0;text-align:center;}
	td{white-space:nowrap;}
	th{white-space:nowrap}
</style>
<SCRIPT language="javascript"> 
　　function printit() 
　　{ 
	　　if (confirm('确定打印吗？')) { 
		document.getElementById('button_print').style.display ="none";
		document.getElementById('button_close').style.display="none";
		window.print();
	　　} 
		
　　} 
　　</SCRIPT>
<style type="text/css" media="print">
.noprint{display : none }
</style>
<title>员工个人信息资料卡</title> 
</head>
<body><!-- 
<object ID="WebBrowser1" WIDTH="0" HEIGHT="0" 
CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"> 
</object> -->
<!-- 
<input onclick="WebBrowser1.ExecWB(7,1)" type="button" value="打印预览"/> -->
<center>
<table class="gridtableList" width="680">
  <tr>
    <td colspan="8" nowrap="nowrap"><div align="center">
      <h3>
		 <s:if test="employee.empStatus==1">员工资料信息卡</s:if>
		 <s:else>离职员工资料信息卡</s:else>
		 &nbsp;&nbsp;&nbsp;
	      <INPUT onclick="javascript:printit()" type="button" value="打 印" name="button_print" id="button_print"/>
		  <input onclick="javascript:window.close();" type="button" value="关 闭" id="button_close"/>
	  </h3>
    </div></td>
  </tr>
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">基本资料</div></th>
  </tr>
  <tr>
    <td width="10%">员工姓名：</td>
    <td width="15%"><s:property value="employee.empName"/></td>
    <td width="10%">员工编号：</td>
    <td width="15%"><s:property value="employee.empDistinctNo"/></td>
     <td>用工形式：</td>
    <td><s:property value="employee.empType.emptypeName"/></td>
    <s:if test="employee.branch!=null &&employee.branch.id!=''">
  	<td>分公司：</td>
  	<td><s:property value="%{employee.branch.id==''?'':employee.branch.branchName}"/></td>
  	</s:if>
  </tr>
  <tr>
   <td width="10%">出生年月：</td>
    <td width="15%"><s:property value="employee.empBirthDate"/></td>
     <td width="10%">性别：</td>
    <td width="15%">
    	<s:if test="employee.empGender==1">
    		男
    	</s:if>
    	<s:else>
    		女
    	</s:else>
    </td>
     <td width="10%">婚否:</td>
    <td width="15%">
    	<s:if test="employee.empMarital==0">未婚</s:if>
    	<s:elseif test="employee.empMarital==1">已婚</s:elseif>
    </td>
    <td colspan="2" rowspan="5" width="110">
    	<img border="1" id="showPicture" style="border: 1px darkslategray outset;" name="showPicture" src="../servlet/showImage?style=head&fileName=<s:property value="employee.empImage"/>" align="left" width="110" height="140"/>
    </td>
  </tr>
  <tr>
    <td>政治面貌：</td>
    <td><s:property value="employee.empPolitics"/></td>
    <td>民族：</td>
    <td><s:property value="employee.empNation"/></td>
    <td>血型：</td>
    <td><s:property value="employee.empBlood"/></td>
    
    
  </tr>
  <tr>
  	<td>毕业学校：</td>
    <td><s:property value="employee.empSchool"/></td>
    <td>专业：</td>
    <td><s:property value="employee.empSpeciality"/></td>
    <td>学历：</td>
    <td><s:property value="employee.empDiploma"/></td>
  </tr>
  <tr>
  	<td>
    	<s:if test="employee.empIdentificationType==0">身份证：</s:if>
    	<s:elseif test="employee.empIdentificationType==1">护照：</s:elseif>
    	<s:elseif test="employee.empIdentificationType==2">驾驶证：</s:elseif>
    	<s:elseif test="employee.empIdentificationType==3">毕业证：</s:elseif>
    	<s:elseif test="employee.empIdentificationType==9">其他：</s:elseif>
    </td>
    <td><s:property value="employee.empIdentificationNo"/></td>
    <td>籍贯:</td>
  	<td><s:property value="employee.empCityNo"/></td>
    <td>社保种类：</td>
    <td><s:property value="%{employee.empBenefitType.id==''?'':employee.empBenefitType.benefitTypeName}"/></td>
  </tr>
  <tr>
    <td>家庭电话：</td>
    <td><s:property value="employee.empHomePhone"/></td>
    <td>移动电话：</td>
    <td><s:property value="employee.empMobile"/></td>
     <td>工作电话</td>
    <td><s:property value="employee.empWorkPhone"/></td>
  </tr>
   <tr>
    <td>参加工作日期：</td>
    <td><s:property value="employee.empWorkDate"/></td>
    <td>入职日期：</td>
    <td><s:property value="employee.empJoinDate"/></td>
    <td>转正日期：</td>
    <td><s:property value="employee.empConfirmDate"/></td>
  </tr>
  <tr>
  	<td>组织单元：</td>
    <td  colspan="3" width="75%"><s:property value="employee.empDeptNo.departmentName"/></td>
     <td>职位：</td>
    <td><s:property value="employee.empPbNo.pbName"/></td>
  </tr>
  <tr>
    <td>工作地区：</td>
    <td><s:property value="employee.empLocationNo.locationName"/></td>
  	<s:set name="auth" value="@com.hr.util.DatabaseSysConfigManager@getInstance()"/>
  	<s:if test="#request.auth.getProperty('sys.examin.shift.enable')==1">
 	    <td>考勤方式:</td>
 	    <s:if test="employee.empShiftType==0">
 	        <td colspan="3">免刷卡</td>
 	    </s:if><s:else>
 	        <td>
 	    	   <s:if test="employee.empShiftType==2">默认班次刷卡</s:if>
	  		   <s:elseif test="employee.empShiftType==3">按班次刷卡</s:elseif>
 	  	    </td>
	 	  	<td>考勤卡号:</td>
	 	  	<td><s:property value="employee.empShiftNo"/></td>
 	  	</s:else>
  	</s:if>
  </tr>
  <tr>
  	 <td>邮箱地址：</td>
    <td><s:property value="employee.empEmail"/></td>
     <td>户口所在地：</td>
    <td><s:property value="employee.empResidenceLoc"/></td>
      <td>紧急联系人：</td>
    <td><s:property value="employee.empUrgentContact"/></td>
  </tr>
  <tr>
    <td><s:property value="connectionType"/>：</td>
    <td><s:property value="connectionNo"/></td>
    <td>档案所在地：</td>
    <td><s:property value="employee.empProfileLoc"/></td>
     <td>紧急联系方式：</td>
    <td><s:property value="employee.empUrgentConMethod"/></td>
  </tr>
  <tr>
    <td>家庭住址（邮编）：</td>
    <td><s:property value="employee.empHomeAddr"/></td>
  	<td><s:property value="employee.empHomeZip"/></td>
  	<td>当前住址（邮编）：</td>
    <td><s:property value="employee.empCurrAddr"/></td>
    <td><s:property value="employee.empCurrZip"/></td>
  </tr>
  <s:if test="employee.empComments != null">
  <tr>
    <td>备注：</td>
    <td colspan="7"><s:property value="employee.empComments"/></td>
  </tr>
  </s:if>
  <s:if test="empaddconfList!=null&&empaddconfList.size>0">
  <tr>
  	<th colspan="8"><div align="left">自定义信息</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		  	<s:iterator value="empaddconfList" status="rowstatus">
				<td width="10%"><s:property value="eadcFieldName+'：'"/></td>
				<td width="40%">
					<s:if test="eadcFieldType=='input'||eadcFieldType=='date'||eadcFieldType=='textarea'">
						<s:property value="value"/>
					</s:if>		
					<s:if test="eadcFieldType=='select'" >
						<s:iterator value="fieldValueList">
								<s:if test="#request['value']==top"><s:property/></s:if>
						</s:iterator>
					</s:if>
				</td>
				<s:if test="#rowstatus.odd != true">
				</tr><tr>
				</s:if>
			</s:iterator>
		</table>
	</td>
</tr>
</s:if>
<s:if test="ehjList!=null && ehjList.size>0">
	<tr>
		<th colspan="8"><div align="left">工作经历</div></th>
	</tr>
	<tr>
		<td colspan="8">
			 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
				<s:iterator value="ehjList">
					<tr id="<s:property value="ehjId"/>">
						<td><s:date name="ehjDateStart" format="yyyy/MM" /></td>
						<td><s:date name="ehjDateEnd" format="yyyy/MM" /></td>
						<td><s:property value="ehjCompany" /></td>
						<td><s:property value="ehjPosition" /></td>
						<td><s:property value="ehjDescription" /></td>	
					</tr>
				</s:iterator>
			</table>
		</td>
	</tr>
</s:if>
<s:if test="eheList!=null&&eheList.size>0">
  <tr>
    <th colspan="8"><div align="left">教育背景</div></th>
  </tr>
  <tr>
    <td colspan="8">
    	<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
			<s:iterator value="eheList">
				<tr id="<s:property value="eheId" />">
					<td><s:property value="eheDateStart" /></td>
					<td><s:property value="eheDateEnd" /></td>
					<td><s:property value="eheSchool" /></td>
					<td><s:property value="eheMajor" /></td>
					<td><s:property value="eheDegree" /></td>
				</tr>
			</s:iterator>
		</table>
    </td>
  </tr>
  </s:if>
<s:if test="ehtList!=null&&ehtList.size>0">
  <tr>
    <th colspan="8"><div align="left">培训经历</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		  <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
			<s:iterator value="ehtList">
				<tr id="<s:property value="ehtId"/>">
					<td><s:property value="ehtStartDate" /></td>
					<td><s:property value="ehtEndDate" /></td>
					<td><s:property value="ehtDepartment" /></td>
					<td><s:property value="ehtLocation" /></td>
					<td><s:property value="ehtCourse" /></td>
					<td><s:property value="ehtCertificate" /></td>
				</tr>
			</s:iterator>
		</table>
	</td>
</tr>
</s:if>
<s:if test="erelList!=null&&erelList.size>0">
  <tr>
    <th colspan="8"><div align="left">社会关系</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		  <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		  <s:iterator value="erelList">
			<tr id="<s:property value="erelId"/>">
			    <td><s:property value="erelRelationship"/></td>
			    <td><s:property value="erelName"/></td>
			    <td><s:date name="erelBirthday" format="yyyy-MM-dd"/></td>
			    <td><s:property value="erelCompany"/></td>
			    <td><s:property value="erelPosition"/></td>
				<td><s:property value="erelContactInfo"/></td>
			</tr>
		</s:iterator>
		</table>
	</td>
</tr>
</s:if>
 <s:if test="contractList!=null&&contractList.size>0">
 	<tr>
 		<th colspan="8"><div align="left">人事合同</div></th>
 	</tr>
 	<tr>
 		<td colspan="8">
 			<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
 				<s:iterator value="contractList">
					<tr>
						<td><s:property value="ectStartDate"/></td>
						<td><s:property value="ectEndDate"/></td>
						<td><s:property value="ectNo"/></td>
						<td><s:property value="emptype.ectName"/></td>
						<td><s:if test="ectStatus==1">有效</s:if><s:if test="ectStatus==2">终止</s:if><s:if test="ectStatus==3">解除</s:if></td>
						<td><s:if test="etcExpire==0">有限期</s:if><s:if test="etcExpire==1">无限期</s:if></td>
						<td><s:property value="ectComments"/></td>
					</tr>
				 </s:iterator>
 			</table>
 		</td>
 	</tr>
 </s:if>
  <s:if test="transferList!=null&&transferList.size>0">
  <tr>
    <th colspan="8"><div align="left">变动记录</div></th>
  </tr>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  			<s:iterator value="transferList">
				<tr>
					<td><s:property value="eftTransferDate"/></td>
					<td><s:if test="eftTransferType==0">平调</s:if><s:if test="eftTransferType==1">晋级</s:if>
						<s:if test="eftTransfedType==2">降级</s:if><s:if test="eftTransferType==3">转岗</s:if><s:if test="eftTransferType==4">转正</s:if></td>
					<td><s:property value="eftOldDeptNo.departmentName"/></td>
					<td><s:property value="eftOldJobTitle.jobtitleName"/></td>
					<td><s:property value="eftNewDeptNo.departmentName"/></td>
					<td><s:property value="eftNewJobTitle.jobtitleName"/></td>
					<td><s:property value="eftReason"/></td>
					<td><s:property value="eftComments"/></td>
				</tr>
			</s:iterator>
  		</table>
  	</td>
  </tr>
  </s:if>
  <s:if test="evalList!=null&&evalList.size>0">
  <tr>
  	<th colspan="8"><div align="left">考评记录</div></th>
  </tr>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
	  		<s:iterator value="evalList">
				<tr>
					<td><s:property value="eeStartDate"/></td>
					<td><s:property value="eeEndDate"/></td>
					<td><s:property value="department.departmentName"/></td>
					<td><s:property value="eePbNo.pbName"/></td>
					<td><s:property value="employeeByEeManager.empName"/></td>
					<td><s:property value="eeType"/></td>
					<td><s:property value="eeRate"/></td>
					<td><s:property value="eeComments"/></td> 
				</tr>
			</s:iterator>
		</table>
  	</td>
  </tr>
  </s:if>
<s:if test="rewardList!=null&&rewardList.size>0">
  <tr>
    <th colspan="8"><div align="left">奖惩记录</div></th>
  </tr>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
				<s:iterator value="rewardList">
				<tr>
			 		<td><s:property value="erExeDate"/></td>
			 		<td><s:property value="department.departmentName"/></td>
			 		<td><s:property value="erPbNo.pbName"/></td>
			 		<td><s:property value="erTypeMean"/></td>
			 		<td><s:property value="erContent"/></td>
				</tr>
			  </s:iterator>
		</table>
  	</td>
  </tr>
  </s:if>
  <s:if test="quitList!=null&&quitList.size>0">
  	<tr>
  		<th colspan="8"><div align="left">离职记录</div></th>
  	</tr>
  	<tr>
  		<td colspan="8">
  			<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
	  			<s:iterator value="quitList" status="quitStatus">
	   			<tr id="<s:property value="eqId"/>">
	   				<td><s:property value="eqDate"/></td>
	   				<td><s:if test="eqType==0">辞职</s:if><s:if test="eqType==1">复职</s:if><s:if test="eqType==2">停薪留职</s:if>
	   					<s:if test="eqType==3">退休</s:if><s:if test="eqType==4">辞退</s:if><s:if test="eqType==5">合同到期</s:if><s:if test="eqType==6">其他</s:if></td>
	   				<td><s:property value="eqPermission.empName"/></td>
	   				<td><s:property value="eqReason"/></td>
	   				<td><s:property value="erComments"/></td>
	   			</tr>
	   			</s:iterator>
	   		</table>
  		</td>
  	</tr>
  </s:if>
</table>
</center>
</body>
</html>