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
����function printit() 
����{ 
	����if (confirm('ȷ����ӡ��')) { 
		document.getElementById('button_print').style.display ="none";
		document.getElementById('button_close').style.display="none";
		window.print();
	����} 
		
����} 
����</SCRIPT>
<style type="text/css" media="print">
.noprint{display : none }
</style>
<title>Ա��������Ϣ���Ͽ�</title> 
</head>
<body><!-- 
<object ID="WebBrowser1" WIDTH="0" HEIGHT="0" 
CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"> 
</object> -->
<!-- 
<input onclick="WebBrowser1.ExecWB(7,1)" type="button" value="��ӡԤ��"/> -->
<center>
<table class="gridtableList" width="680">
  <tr>
    <td colspan="8" nowrap="nowrap"><div align="center">
      <h3>
		 <s:if test="employee.empStatus==1">Ա��������Ϣ��</s:if>
		 <s:else>��ְԱ��������Ϣ��</s:else>
		 &nbsp;&nbsp;&nbsp;
	      <INPUT onclick="javascript:printit()" type="button" value="�� ӡ" name="button_print" id="button_print"/>
		  <input onclick="javascript:window.close();" type="button" value="�� ��" id="button_close"/>
	  </h3>
    </div></td>
  </tr>
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">��������</div></th>
  </tr>
  <tr>
    <td width="10%">Ա��������</td>
    <td width="15%"><s:property value="employee.empName"/></td>
    <td width="10%">Ա����ţ�</td>
    <td width="15%"><s:property value="employee.empDistinctNo"/></td>
     <td>�ù���ʽ��</td>
    <td><s:property value="employee.empType.emptypeName"/></td>
    <s:if test="employee.branch!=null &&employee.branch.id!=''">
  	<td>�ֹ�˾��</td>
  	<td><s:property value="%{employee.branch.id==''?'':employee.branch.branchName}"/></td>
  	</s:if>
  </tr>
  <tr>
   <td width="10%">�������£�</td>
    <td width="15%"><s:property value="employee.empBirthDate"/></td>
     <td width="10%">�Ա�</td>
    <td width="15%">
    	<s:if test="employee.empGender==1">
    		��
    	</s:if>
    	<s:else>
    		Ů
    	</s:else>
    </td>
     <td width="10%">���:</td>
    <td width="15%">
    	<s:if test="employee.empMarital==0">δ��</s:if>
    	<s:elseif test="employee.empMarital==1">�ѻ�</s:elseif>
    </td>
    <td colspan="2" rowspan="5" width="110">
    	<img border="1" id="showPicture" style="border: 1px darkslategray outset;" name="showPicture" src="../servlet/showImage?style=head&fileName=<s:property value="employee.empImage"/>" align="left" width="110" height="140"/>
    </td>
  </tr>
  <tr>
    <td>������ò��</td>
    <td><s:property value="employee.empPolitics"/></td>
    <td>���壺</td>
    <td><s:property value="employee.empNation"/></td>
    <td>Ѫ�ͣ�</td>
    <td><s:property value="employee.empBlood"/></td>
    
    
  </tr>
  <tr>
  	<td>��ҵѧУ��</td>
    <td><s:property value="employee.empSchool"/></td>
    <td>רҵ��</td>
    <td><s:property value="employee.empSpeciality"/></td>
    <td>ѧ����</td>
    <td><s:property value="employee.empDiploma"/></td>
  </tr>
  <tr>
  	<td>
    	<s:if test="employee.empIdentificationType==0">���֤��</s:if>
    	<s:elseif test="employee.empIdentificationType==1">���գ�</s:elseif>
    	<s:elseif test="employee.empIdentificationType==2">��ʻ֤��</s:elseif>
    	<s:elseif test="employee.empIdentificationType==3">��ҵ֤��</s:elseif>
    	<s:elseif test="employee.empIdentificationType==9">������</s:elseif>
    </td>
    <td><s:property value="employee.empIdentificationNo"/></td>
    <td>����:</td>
  	<td><s:property value="employee.empCityNo"/></td>
    <td>�籣���ࣺ</td>
    <td><s:property value="%{employee.empBenefitType.id==''?'':employee.empBenefitType.benefitTypeName}"/></td>
  </tr>
  <tr>
    <td>��ͥ�绰��</td>
    <td><s:property value="employee.empHomePhone"/></td>
    <td>�ƶ��绰��</td>
    <td><s:property value="employee.empMobile"/></td>
     <td>�����绰</td>
    <td><s:property value="employee.empWorkPhone"/></td>
  </tr>
   <tr>
    <td>�μӹ������ڣ�</td>
    <td><s:property value="employee.empWorkDate"/></td>
    <td>��ְ���ڣ�</td>
    <td><s:property value="employee.empJoinDate"/></td>
    <td>ת�����ڣ�</td>
    <td><s:property value="employee.empConfirmDate"/></td>
  </tr>
  <tr>
  	<td>��֯��Ԫ��</td>
    <td  colspan="3" width="75%"><s:property value="employee.empDeptNo.departmentName"/></td>
     <td>ְλ��</td>
    <td><s:property value="employee.empPbNo.pbName"/></td>
  </tr>
  <tr>
    <td>����������</td>
    <td><s:property value="employee.empLocationNo.locationName"/></td>
  	<s:set name="auth" value="@com.hr.util.DatabaseSysConfigManager@getInstance()"/>
  	<s:if test="#request.auth.getProperty('sys.examin.shift.enable')==1">
 	    <td>���ڷ�ʽ:</td>
 	    <s:if test="employee.empShiftType==0">
 	        <td colspan="3">��ˢ��</td>
 	    </s:if><s:else>
 	        <td>
 	    	   <s:if test="employee.empShiftType==2">Ĭ�ϰ��ˢ��</s:if>
	  		   <s:elseif test="employee.empShiftType==3">�����ˢ��</s:elseif>
 	  	    </td>
	 	  	<td>���ڿ���:</td>
	 	  	<td><s:property value="employee.empShiftNo"/></td>
 	  	</s:else>
  	</s:if>
  </tr>
  <tr>
  	 <td>�����ַ��</td>
    <td><s:property value="employee.empEmail"/></td>
     <td>�������ڵأ�</td>
    <td><s:property value="employee.empResidenceLoc"/></td>
      <td>������ϵ�ˣ�</td>
    <td><s:property value="employee.empUrgentContact"/></td>
  </tr>
  <tr>
    <td><s:property value="connectionType"/>��</td>
    <td><s:property value="connectionNo"/></td>
    <td>�������ڵأ�</td>
    <td><s:property value="employee.empProfileLoc"/></td>
     <td>������ϵ��ʽ��</td>
    <td><s:property value="employee.empUrgentConMethod"/></td>
  </tr>
  <tr>
    <td>��ͥסַ���ʱࣩ��</td>
    <td><s:property value="employee.empHomeAddr"/></td>
  	<td><s:property value="employee.empHomeZip"/></td>
  	<td>��ǰסַ���ʱࣩ��</td>
    <td><s:property value="employee.empCurrAddr"/></td>
    <td><s:property value="employee.empCurrZip"/></td>
  </tr>
  <s:if test="employee.empComments != null">
  <tr>
    <td>��ע��</td>
    <td colspan="7"><s:property value="employee.empComments"/></td>
  </tr>
  </s:if>
  <s:if test="empaddconfList!=null&&empaddconfList.size>0">
  <tr>
  	<th colspan="8"><div align="left">�Զ�����Ϣ</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		  	<s:iterator value="empaddconfList" status="rowstatus">
				<td width="10%"><s:property value="eadcFieldName+'��'"/></td>
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
		<th colspan="8"><div align="left">��������</div></th>
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
    <th colspan="8"><div align="left">��������</div></th>
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
    <th colspan="8"><div align="left">��ѵ����</div></th>
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
    <th colspan="8"><div align="left">����ϵ</div></th>
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
 		<th colspan="8"><div align="left">���º�ͬ</div></th>
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
						<td><s:if test="ectStatus==1">��Ч</s:if><s:if test="ectStatus==2">��ֹ</s:if><s:if test="ectStatus==3">���</s:if></td>
						<td><s:if test="etcExpire==0">������</s:if><s:if test="etcExpire==1">������</s:if></td>
						<td><s:property value="ectComments"/></td>
					</tr>
				 </s:iterator>
 			</table>
 		</td>
 	</tr>
 </s:if>
  <s:if test="transferList!=null&&transferList.size>0">
  <tr>
    <th colspan="8"><div align="left">�䶯��¼</div></th>
  </tr>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  			<s:iterator value="transferList">
				<tr>
					<td><s:property value="eftTransferDate"/></td>
					<td><s:if test="eftTransferType==0">ƽ��</s:if><s:if test="eftTransferType==1">����</s:if>
						<s:if test="eftTransfedType==2">����</s:if><s:if test="eftTransferType==3">ת��</s:if><s:if test="eftTransferType==4">ת��</s:if></td>
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
  	<th colspan="8"><div align="left">������¼</div></th>
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
    <th colspan="8"><div align="left">���ͼ�¼</div></th>
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
  		<th colspan="8"><div align="left">��ְ��¼</div></th>
  	</tr>
  	<tr>
  		<td colspan="8">
  			<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
	  			<s:iterator value="quitList" status="quitStatus">
	   			<tr id="<s:property value="eqId"/>">
	   				<td><s:property value="eqDate"/></td>
	   				<td><s:if test="eqType==0">��ְ</s:if><s:if test="eqType==1">��ְ</s:if><s:if test="eqType==2">ͣн��ְ</s:if>
	   					<s:if test="eqType==3">����</s:if><s:if test="eqType==4">����</s:if><s:if test="eqType==5">��ͬ����</s:if><s:if test="eqType==6">����</s:if></td>
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