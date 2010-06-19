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
		
		<form id="addovertimerequest" name="addovertimerequest" method="POST" action="empOTAddDo.action">

        	<!-- �����ֶ� -->
			<s:token />
			<s:hidden name="overLimit"/>
			<s:hidden name="status"/>
			<s:hidden id="infoMeg" name="infoMeg" value=""  />
			<table width="100%">
			<tr>
			<td width="3%" align="right">
				<img src="../resource/images/h3Arrow.gif">
			</td>
			<td>
				<h3>�����Ӱ�</h3>
			</td>
			</tr>
			</table>
			
			<TABLE width="100%" align="left">
			    <tr>
			    	<td width="13%" align="right">
						<label >Ա������<span class="required">*</span>:</label>
					</td>
					<td>
				        <s:hidden name="of_Bean.orEmpNo.id" id="empId"></s:hidden>
				        <s:hidden name="of_Bean.orEmpNo.empLocationNo.id" id="empLocationNo.id"></s:hidden>
				        <s:textfield id="empName"  name="of_Bean.orEmpNo.empName" required="true" readonly="true"/>	
				        <s:set name="author" value="@com.hr.util.DatabaseSysConfigManager@getInstance()"/>
				        <s:if test="#request.author.getProperty('sys.examin.create.level')==1">
				        <ty:auth auths="401 or 411,2 or 411,3"><img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick="showChooseEmpDiv(4,1);" alt='���ͼ��ѡ��Ա��'/></ty:auth>	        
			        	</s:if>
			        </td>
			    </tr>
				<tr>		
				<td align="right">
					�Ӱ�����<span class="required">*</span>:
				</td>
				<td>			
					<s:select id="of_Bean.orOtNo.otNo" name="of_Bean.orOtNo.otNo" list="of_Bean.allOtType" listKey="otNo" listValue="otName" emptyOption="false" required="true" />					
				</td>
				</tr>
				<tr>
				<td align="right">
				    ����<span class="required">*</span>:
				</td><td>			 
					<s:textfield  id="of_Bean.startDate" name="of_Bean.startDate" required="true" size="12"/>
					<img onclick="WdatePicker({el:'of_Bean.startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				</td>
				</tr>
				<tr>
					<td align="right">
						<label >��ʼʱ��<span class="required">*</span>:</label>
					</td>
					<td>
						<s:select id="startTime" name="of_Bean.startTime" list="#{'00':'0��','01':'1��','02':'2��','03':'3��','04':'4��','05':'5��','06':'6��','07':'7��','08':'8��','09':'9��','10':'10��','11':'11��','12':'12��','13':'13��','14':'14��','15':'15��','16':'16��','17':'17��','18':'18��','19':'19��','20':'20��','21':'21��','22':'22��','23':'23��'}" emptyOption="false" required="true"  />
						<s:select name="of_Bean.startTimeMinute" list="#{'0':'��','30':'��'}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<label >����ʱ��<span class="required">*</span>:</label>
					</td>
					<td>
						<s:select id="endTime" name="of_Bean.endTime" list="#{'00':'0��','01':'1��','02':'2��','03':'3��','04':'4��','05':'5��','06':'6��','07':'7��','08':'8��','09':'9��','10':'10��','11':'11��','12':'12��','13':'13��','14':'14��','15':'15��','16':'16��','17':'17��','18':'18��','19':'19��','20':'20��','21':'21��','22':'22��','23':'23��','24':'24��'}" emptyOption="false" required="true"  />
						<s:select name="of_Bean.endTimeMinute" list="#{'0':'��','30':'��'}"	/>
					</td>
				</tr>
				<tr>					
					<td align="right">
						�Ӱ�����<span class="required">*</span>:
					</td>
				<td>
					<s:textarea name="of_Bean.orReason"  cols="40" rows="6" required="true"/>
				</td>
				</tr>
				<tr>
					<td></td>
					<td height="1" colspan="9">						
						<input title="[Alt+S]" accesskey="S" id="submited" name="submited" class="button" type="submit" onclick="hrm.common.submitForm('addovertimerequest','empOTAddDo.action','status','SUBMIT')" value="�ύ">	
						<input title="[Alt+A]" accesskey="A" id="reset" name="reset" class="button" type="reset" value="����" >
					</td>
				</tr>
			</TABLE>
		</form>
<script language="javascript">
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ�
 
</script>
<%@ include file="../profile/search_emp_div.jsp"%>
</body>
</html>
