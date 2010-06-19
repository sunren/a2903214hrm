<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>		
	<!-- css������Ϣ -->
	<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
    <jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
	<title>�޸��ҵļӰ�����</title>
</head>
<body>
       <s:if test="of_Bean!=null">
       	<s:component template="bodyhead">
		</s:component>
		<br/>
		<form id="updateovertimerequest" name="updateovertimerequest" method="POST" action="myOTUpdateDo.action">
			
			<!-- �����ֶ� --> 
			<s:hidden id="orIdUp" name="orIdUp" />
			<s:hidden name="of_Bean.orEmpNo.id"/>
			
			<!-- �����ֶ� 
			<input type="hidden" name="of_Bean.or.orId" value="<s:property value="orIdUp"/>"/>
			<s:hidden name="status"/>
			<s:hidden name="id"/>
			<s:hidden name="overLimit"/>
			<s:hidden id="infoMeg" name="infoMeg" value=""  />
			<s:hidden name="of_Bean.orEmpNo.empLocationNo.id" id="empLocationNo.id" />
			�����ֶ� --> 

			<table width="100%">
			<tr>
			<td width="3%" align="right">
				<img src="../resource/images/h3Arrow.gif">
			</td>
			<td>
				<h3>�޸ļӰ�</h3>
			</td>
			</tr>
			</table>
				<TABLE width="100%" >
				<tr>
				    <td width="13%" align="right">
				        �Ӱ൥���:
				    </td>
				    <td>
						<s:property value="otr.orNo" />
				    </td>
				</tr>
				<tr>
				    <td align="right">
				        �Ӱ���ʱ��:
				    </td>
				    <td>
						<s:property value="otr.orTotalHours" />Сʱ
				    </td>
				</tr>
				<tr>
					<td align="right">
						<label >Ա������<span class="required">*</span>:</label>
					</td>
					<td>
						<s:property value="of_Bean.orEmpNo.empName" />
				    </td>
			    </tr>
				<tr>
					<td align="right">
						�Ӱ�����<span class="required">*</span>:
					</td>	
					<td>
						<s:select id="of_Bean.orOtNo.otNo" name="of_Bean.orOtNo.otNo" list="of_Bean.allOtType" listKey="otNo" listValue="otName" value="of_Bean.orOtNo.otNo" emptyOption="false" required="true" />
					</td>			
				</tr>
				<tr>
					<td align="right">
						����<span class="required">*</span>:
					</td>
					<td>
						 <s:textfield id="es_Bean_startDate" name="of_Bean.startDate" required="true" size="12" /> 
						 <img onclick="WdatePicker({el:'es_Bean_startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">				
					</td>
				</tr>
				<tr>
					<td align="right">
						<label >��ʼʱ��<span class="required">*</span>:</label></td>
					<td>
					<s:select id="startTime" name="of_Bean.startTime" list="#{'0':'0��','1':'1��','2':'2��','3':'3��','4':'4��','5':'5��','6':'6��','7':'7��','8':'8��','9':'9��','10':'10��','11':'11��','12':'12��','13':'13��','14':'14��','15':'15��','16':'16��','17':'17��','18':'18��','19':'19��','20':'20��','21':'21��','22':'22��','23':'23��'}" emptyOption="true" required="true"  />
					<s:select name="of_Bean.startTimeMinute" list="#{'0':'��','30':'��'}"	/></td>
				</tr>
				<tr>
					<td align="right">
						<label >����ʱ��<span class="required">*</span>:</label></td>
					<td>
					<s:select id="endTime" name="of_Bean.endTime" list="#{'0':'0��','1':'1��','2':'2��','3':'3��','4':'4��','5':'5��','6':'6��','7':'7��','8':'8��','9':'9��','10':'10��','11':'11��','12':'12��','13':'13��','14':'14��','15':'15��','16':'16��','17':'17��','18':'18��','19':'19��','20':'20��','21':'21��','22':'22��','23':'23��','24':'24��'}" emptyOption="true" required="true"  />
					<s:select name="of_Bean.endTimeMinute" list="#{'0':'��','30':'��'}"	/>
				</tr>
				<tr>					
					<s:textarea name="of_Bean.orReason" label="�Ӱ�����" cols="40" rows="6" required="true" />
				</tr>
				<tr >
					<td></td>
					<td height="1" colspan="9">								
					<input title="[Alt+S]" accesskey="S" id="submited" name="submited" class="button" type="submit" onclick="otClickDisable('submited','updateovertimerequest','false')" value="�޸�">	
					<input title="[Alt+A]" accesskey="A" id="reset" name="reset" class="button" type="reset" value="����" >
					</td>
				</tr>
			</TABLE>
		</form>
	</s:if>
	<s:else>		
			Ҫ���µļӰ����벻���ڣ�	
	</s:else>
</body>
<script>
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ� 
</script>
</html>
