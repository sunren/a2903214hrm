<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>		
	<!-- css修饰信息 -->
	<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
    <jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
	<title>修改我的加班申请</title>
</head>
<body>
       <s:if test="of_Bean!=null">
       	<s:component template="bodyhead">
		</s:component>
		<br/>
		<form id="updateovertimerequest" name="updateovertimerequest" method="POST" action="myOTUpdateDo.action">
			
			<!-- 隐藏字段 --> 
			<s:hidden id="orIdUp" name="orIdUp" />
			<s:hidden name="of_Bean.orEmpNo.id"/>
			
			<!-- 废弃字段 
			<input type="hidden" name="of_Bean.or.orId" value="<s:property value="orIdUp"/>"/>
			<s:hidden name="status"/>
			<s:hidden name="id"/>
			<s:hidden name="overLimit"/>
			<s:hidden id="infoMeg" name="infoMeg" value=""  />
			<s:hidden name="of_Bean.orEmpNo.empLocationNo.id" id="empLocationNo.id" />
			废弃字段 --> 

			<table width="100%">
			<tr>
			<td width="3%" align="right">
				<img src="../resource/images/h3Arrow.gif">
			</td>
			<td>
				<h3>修改加班</h3>
			</td>
			</tr>
			</table>
				<TABLE width="100%" >
				<tr>
				    <td width="13%" align="right">
				        加班单编号:
				    </td>
				    <td>
						<s:property value="otr.orNo" />
				    </td>
				</tr>
				<tr>
				    <td align="right">
				        加班总时间:
				    </td>
				    <td>
						<s:property value="otr.orTotalHours" />小时
				    </td>
				</tr>
				<tr>
					<td align="right">
						<label >员工姓名<span class="required">*</span>:</label>
					</td>
					<td>
						<s:property value="of_Bean.orEmpNo.empName" />
				    </td>
			    </tr>
				<tr>
					<td align="right">
						加班种类<span class="required">*</span>:
					</td>	
					<td>
						<s:select id="of_Bean.orOtNo.otNo" name="of_Bean.orOtNo.otNo" list="of_Bean.allOtType" listKey="otNo" listValue="otName" value="of_Bean.orOtNo.otNo" emptyOption="false" required="true" />
					</td>			
				</tr>
				<tr>
					<td align="right">
						日期<span class="required">*</span>:
					</td>
					<td>
						 <s:textfield id="es_Bean_startDate" name="of_Bean.startDate" required="true" size="12" /> 
						 <img onclick="WdatePicker({el:'es_Bean_startDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">				
					</td>
				</tr>
				<tr>
					<td align="right">
						<label >开始时间<span class="required">*</span>:</label></td>
					<td>
					<s:select id="startTime" name="of_Bean.startTime" list="#{'0':'0点','1':'1点','2':'2点','3':'3点','4':'4点','5':'5点','6':'6点','7':'7点','8':'8点','9':'9点','10':'10点','11':'11点','12':'12点','13':'13点','14':'14点','15':'15点','16':'16点','17':'17点','18':'18点','19':'19点','20':'20点','21':'21点','22':'22点','23':'23点'}" emptyOption="true" required="true"  />
					<s:select name="of_Bean.startTimeMinute" list="#{'0':'整','30':'半'}"	/></td>
				</tr>
				<tr>
					<td align="right">
						<label >结束时间<span class="required">*</span>:</label></td>
					<td>
					<s:select id="endTime" name="of_Bean.endTime" list="#{'0':'0点','1':'1点','2':'2点','3':'3点','4':'4点','5':'5点','6':'6点','7':'7点','8':'8点','9':'9点','10':'10点','11':'11点','12':'12点','13':'13点','14':'14点','15':'15点','16':'16点','17':'17点','18':'18点','19':'19点','20':'20点','21':'21点','22':'22点','23':'23点','24':'24点'}" emptyOption="true" required="true"  />
					<s:select name="of_Bean.endTimeMinute" list="#{'0':'整','30':'半'}"	/>
				</tr>
				<tr>					
					<s:textarea name="of_Bean.orReason" label="加班理由" cols="40" rows="6" required="true" />
				</tr>
				<tr >
					<td></td>
					<td height="1" colspan="9">								
					<input title="[Alt+S]" accesskey="S" id="submited" name="submited" class="button" type="submit" onclick="otClickDisable('submited','updateovertimerequest','false')" value="修改">	
					<input title="[Alt+A]" accesskey="A" id="reset" name="reset" class="button" type="reset" value="重置" >
					</td>
				</tr>
			</TABLE>
		</form>
	</s:if>
	<s:else>		
			要更新的加班申请不存在！	
	</s:else>
</body>
<script>
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度） 
</script>
</html>
