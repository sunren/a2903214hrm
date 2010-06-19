<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<!-- css修饰信息 -->
	<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />	

	<!-- 自己添加 -->
	<script type="text/javascript">
	function reportChange(){
			var reportID=$('#reportSelect').val();
			
			$('#timeSelect').hide();
			$('#year').css('disabled', 'true');
			$('#startMonths').css('disabled', 'true');
			$('#endMonths').css('disabled', 'true');
			$('#periodLable').hide();
			
			if(reportID=='report1'){
				return ;
			}
			if(reportID=='report2'){
				$('#year').css('disabled', 'false');
				$('#startMonths').css('disabled', 'false');
				$('#endMonths').css('disabled', 'false');
				$('timeSelect').show();
				$('periodLable').show();
			}
		}
		function pdfGetSubmit(){
			var reportID=$('#reportSelect').val();
			var formAction=document.getElementById('actionSrc');
			$('#pdfReport').css('disabled', 'true');
			switch(reportID){
				case 'report1':
				formAction.action="<s:url value='/report/rpReportByNow.action'/>"+'?_format=pdf';
				break;
				case 'report2':
				formAction.action="<s:url value='/report/raReportByTime.action'/>"+'?_format=pdf';
				break;
			}
			util.setTimeoutDisabled('pdfReport',8000);
			$('actionSrc').submit();
		}
		function submitCheck(){
			var starNumt=$('#startMonths').val().substr(1);
			var endNum=$('#endMonths').val().substr(1);
			if(parseInt(starNumt)<=parseInt(endNum)){
				return true;
			}
			alert("开始月份必须小于等于结束月份");
			return false;
		}
		function showReport(){
			var url="../report/rpReportByNow.action";
			if(reportID=='report2'){
				url="../report/raReportByTime.action?year="+$('#year').val()+"&startM="+$('#startMonths').val()+"&endMonths="+$('#endMonths').val();
			}
			$('#IFrame1').attr('src', url);
		}
	</script>
	<title>招聘信息报表</title>
</head>
<body>
<s:component template="bodyhead">
		<s:param name="pagetitle" value="'招聘信息报表'" />		
		<s:param name="helpUrl" value="'46'" />
	</s:component>
	<br/>
	<form id="actionSrc" action="recruitmentReport.action"  method="POST" onsubmit="return submitCheck();">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td nowrap="nowrap">
							<s:select label="报表名称" id="reportSelect" name="reportSelect" value="reportSelect" list="#{'report1':'招聘计划分析','report2':'应聘者分析'}" onchange="reportChange();"></s:select>
						</td>
						<td nowrap="nowrap" align="right">
							<label id="periodLable">统计周期：</label>
						</td>
						<td id="timeSelect" nowrap="nowrap" style="display:none;">
							<s:select id="year" name="year" list="yearList"></s:select>
							<s:select id="startMonths" name="startM" value="startM" list="#{'m1':'一月','m2':'二月','m3':'三月','m4':'四月','m5':'五月','m6':'六月','m7':'七月','m8':'八月','m9':'九月','m10':'十月','m11':'十一月','m12':'十二月'}"></s:select> 
							到
							<s:select id="endMonths" name="endM"  value="endM" list="#{'m1':'一月','m2':'二月','m3':'三月','m4':'四月','m5':'五月','m6':'六月','m7':'七月','m8':'八月','m9':'九月','m10':'十月','m11':'十一月','m12':'十二月'}"></s:select> 
						</td>
					</tr>
				</table>
			</td>
			<td>				
				<input title="查看报表" accesskey="L" name="htmlReport" id="htmlReport" class="button" type="submit" value="查看报表" onclick="javaScript:$('actionSrc').action='recruitmentReport.action';$('actionSrc').submit();$('htmlReport').disabled='disabled'">
				<input title="PDF下载" accesskey="D" name="pdfReport" id="pdfReport" class="button" type="submit" value="PDF下载" onclick="pdfGetSubmit()">
				<br> 
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		reportChange();
	</script>
		<p align="left">&nbsp;</p> 
		<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
	 			 width="100%" height="480" SRC="../report/rpReportByNow.action" style="overflow-y:auto;"></IFRAME>
	</form>
</body>
</html>