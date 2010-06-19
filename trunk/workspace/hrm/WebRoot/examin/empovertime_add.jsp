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
		
		<form id="addovertimerequest" name="addovertimerequest" method="POST" action="empOTAddDo.action">

        	<!-- 隐藏字段 -->
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
				<h3>新增加班</h3>
			</td>
			</tr>
			</table>
			
			<TABLE width="100%" align="left">
			    <tr>
			    	<td width="13%" align="right">
						<label >员工姓名<span class="required">*</span>:</label>
					</td>
					<td>
				        <s:hidden name="of_Bean.orEmpNo.id" id="empId"></s:hidden>
				        <s:hidden name="of_Bean.orEmpNo.empLocationNo.id" id="empLocationNo.id"></s:hidden>
				        <s:textfield id="empName"  name="of_Bean.orEmpNo.empName" required="true" readonly="true"/>	
				        <s:set name="author" value="@com.hr.util.DatabaseSysConfigManager@getInstance()"/>
				        <s:if test="#request.author.getProperty('sys.examin.create.level')==1">
				        <ty:auth auths="401 or 411,2 or 411,3"><img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick="showChooseEmpDiv(4,1);" alt='点击图标选择员工'/></ty:auth>	        
			        	</s:if>
			        </td>
			    </tr>
				<tr>		
				<td align="right">
					加班种类<span class="required">*</span>:
				</td>
				<td>			
					<s:select id="of_Bean.orOtNo.otNo" name="of_Bean.orOtNo.otNo" list="of_Bean.allOtType" listKey="otNo" listValue="otName" emptyOption="false" required="true" />					
				</td>
				</tr>
				<tr>
				<td align="right">
				    日期<span class="required">*</span>:
				</td><td>			 
					<s:textfield  id="of_Bean.startDate" name="of_Bean.startDate" required="true" size="12"/>
					<img onclick="WdatePicker({el:'of_Bean.startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				</td>
				</tr>
				<tr>
					<td align="right">
						<label >开始时间<span class="required">*</span>:</label>
					</td>
					<td>
						<s:select id="startTime" name="of_Bean.startTime" list="#{'00':'0点','01':'1点','02':'2点','03':'3点','04':'4点','05':'5点','06':'6点','07':'7点','08':'8点','09':'9点','10':'10点','11':'11点','12':'12点','13':'13点','14':'14点','15':'15点','16':'16点','17':'17点','18':'18点','19':'19点','20':'20点','21':'21点','22':'22点','23':'23点'}" emptyOption="false" required="true"  />
						<s:select name="of_Bean.startTimeMinute" list="#{'0':'整','30':'半'}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<label >结束时间<span class="required">*</span>:</label>
					</td>
					<td>
						<s:select id="endTime" name="of_Bean.endTime" list="#{'00':'0点','01':'1点','02':'2点','03':'3点','04':'4点','05':'5点','06':'6点','07':'7点','08':'8点','09':'9点','10':'10点','11':'11点','12':'12点','13':'13点','14':'14点','15':'15点','16':'16点','17':'17点','18':'18点','19':'19点','20':'20点','21':'21点','22':'22点','23':'23点','24':'24点'}" emptyOption="false" required="true"  />
						<s:select name="of_Bean.endTimeMinute" list="#{'0':'整','30':'半'}"	/>
					</td>
				</tr>
				<tr>					
					<td align="right">
						加班理由<span class="required">*</span>:
					</td>
				<td>
					<s:textarea name="of_Bean.orReason"  cols="40" rows="6" required="true"/>
				</td>
				</tr>
				<tr>
					<td></td>
					<td height="1" colspan="9">						
						<input title="[Alt+S]" accesskey="S" id="submited" name="submited" class="button" type="submit" onclick="hrm.common.submitForm('addovertimerequest','empOTAddDo.action','status','SUBMIT')" value="提交">	
						<input title="[Alt+A]" accesskey="A" id="reset" name="reset" class="button" type="reset" value="重置" >
					</td>
				</tr>
			</TABLE>
		</form>
<script language="javascript">
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）
 
</script>
<%@ include file="../profile/search_emp_div.jsp"%>
</body>
</html>
