<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<!-- css修饰信息 -->
	<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />	
	<!-- 自己添加 -->
	<script type="text/javascript">
		function pdfGetSubmit(){
			var reportID=$('#reportSelect').val();
			var formAction=document.getElementById('actionSrc');
			$('#pdfReport').css('disabled', 'true');
			switch(reportID){
				case 'report1':
				formAction.action="<s:url value='/report/trReportByTime.action'/>"+'?_format=pdf';
				break;
			}
			util.setTimeoutDisabled('pdfReport',8000);
			document.getElementById('actionSrc').submit();
		}
		function showReport(){
			var url="../report/trReportByTime.action?year="+$('#year').val()+"&trType="+$('#trType').val();
			$('#IFrame1').attr('src', url);
		}
	</script>
	<title>培训信息报表</title>
</head>
<body>
	<br/>
	<form id="actionSrc" action="trReport.action"  method="POST">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<s:hidden name="reportSelect" value="report1"></s:hidden>
						<td nowrap="nowrap">
						<label>报表名称：</label>
							员工培训分析
						</td>
						<td nowrap="nowrap" align="right">
							<label id="periodLable">统计周期：</label>
						</td>
						<td nowrap="nowrap">			
							<s:select id="year" name="year" list="yearList"></s:select>
						</td>
						<td nowrap="nowrap">
							<s:select id="trType" name="trType"  list="trcourseList" listKey="trcNo" listValue="trcName" emptyOption="true"></s:select>
						</td>
					</tr>
				</table>
			</td>
			<td>				
				<input title="查看报表" accesskey="L" name="htmlReport" id="htmlReport" class="button" type="button" value="查看报表" onclick="javaScript:showReport();">
				<input title="PDF下载" accesskey="D" name="pdfReport" id="pdfReport" class="button" type="submit" value="PDF下载" onclick="pdfGetSubmit()">
				<br> 
			</td>
		</tr>
	</table>
		<p align="left">&nbsp;</p> 
		<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
	 			 width="100%" height="480" SRC="" style="overflow-y:auto;"></IFRAME>
	 	<!--隐藏信息-->
	</form>
</body>
</html>