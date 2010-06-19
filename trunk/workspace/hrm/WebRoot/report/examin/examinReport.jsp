<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
  	<title>员工考勤报表</title>
	<!-- 自己添加 -->
	<script type="text/javascript">
		function reportChange(){
			var reportID=$('#reportSelect').val();
			$('#lrtype').attr("disabled","disabled");
			$('#ottype').attr("disabled","disabled");
			$('#lrtype').hide();
			$('#ottype').hide();
			$('#lrtypeName').hide();
			$('#ottypeName').hide();
			
			if(reportID=='report1'){
				$('#lrtype').removeAttr("disabled");
				$('#lrtype').show();
				$('#lrtypeName').show();
			}else if(reportID=='report2'){
				$('#ottype').removeAttr('disabled');
				$('#ottype').show();
				$('#ottypeName').show();
			}
		}
		function doCheck(){
			if($("#year").val() == null || $("#year").val() ==''){
				alert("统计周期为空，不能进行报表统计");
				return false;
			}
			var starNumt=$('#startMonths').val().substr(1);
			var endNum=$('#endMonths').val().substr(1);
			if(parseInt(starNumt)<=parseInt(endNum)){
				//$('htmlReport').disabled="disabled";
			//	$('pdfReport').disabled="disabled";
				return true;
			}
			alert("开始月份必须小于等于结束月份");
			return false;
		}
		function showReportOne(format){
			if(!doCheck()){
				return;
			}
			var reportID=$('#reportSelect').val();
			var url="../report/lrReportByTime.action?reportSelect=";
			if(reportID=='report2'){
				url="../report/otReportByTime.action?reportSelect=";
			}
			url+=$('#reportSelect').val();
			url+="&_format="+format;
			url+="&year="+$('#year').val();
			url+="&startMonths="+$('#startMonths').val();
			url+="&endMonths="+$('#endMonths').val();
			if($('#includeDeptPass').attr("checked") == true){
				url+="&includeDeptPass="+$('#includeDeptPass').val();
			}
			if(reportID=='report1'){
				url+="&examinType="+$('#lrtype').val();
			}else if(reportID=='report2'){
				url+="&examinType="+$('#ottype').val();
			}
			if(format=='pdf'){
				$('#pdfReportOne').attr("disabled","true");
				setTimeout(function(){$('#pdfReportOne').removeAttr("disabled");},8000);
			}
			$('#IFrame1').attr("src",url);
		}
		function showReportTwo(format){
			if($("#attendmonthlyYear").val() == ''){
				alert("统计年份为空，不能进行报表统计");
				return;
			}
			var url="../report/alyReportByType.action?reportTypeSelect=";
			url+=$("#reportTypeSelect").val();
			url+="&_format="+format;
			url+="&attendmonthlyYear="+$("#attendmonthlyYear").val();
			url+="&attendmonthlyMonths="+$("#attendmonthlyMonths").val();
			if(format=='pdf'){
				$('#pdfReportTwo').attr("disabled","true");
				setTimeout(function(){$('#pdfReportTwo').removeAttr("disabled");},8000);
			}
			$('#IFrame1').attr("src",url);
		}
	</script>
	<script type="text/javascript">
		$(function() {
			$("#tabs").tabs();
		});
	</script>
	<style type="text/css">
		#tabs-1,#tabs-2,#tabs-3{background-color: #ECF6FB;border-top: 1px #6BB5DA solid;}
	</style>
</head>
<body>
<s:component template="bodyhead">
    <s:param name="pagetitle" value="'员工考勤报表：'" />
    <s:param name="helpUrl" value="'37'"/>
</s:component>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1" onclick="clearShow();">考勤信息报表</a></li>
		<li><a href="#tabs-2" onclick="clearShow();">每月考勤报表</a></li>
	</ul>
	<div id="tabs-1">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="right">报表名称：</td>
							<td>
							<s:select id="reportSelect" name="reportSelect" value="reportSelect" list="#{'report1':'员工请假历史分析','report2':'员工加班历史分析'}" onchange="reportChange();"></s:select>
							</td>
							<td align="right">统计周期：</td>
							<td nowrap="nowrap">
								<s:select id="year" name="year" list="yearList"></s:select>
								<s:select id="startMonths" name="startMonths" value="startMonths" list="#{'m1':'一月','m2':'二月','m3':'三月','m4':'四月','m5':'五月','m6':'六月','m7':'七月','m8':'八月','m9':'九月','m10':'十月','m11':'十一月','m12':'十二月'}"></s:select> 
								到
								<s:select id="endMonths" name="endMonths" value="endMonths" list="#{'m1':'一月','m2':'二月','m3':'三月','m4':'四月','m5':'五月','m6':'六月','m7':'七月','m8':'八月','m9':'九月','m10':'十月','m11':'十一月','m12':'十二月'}"></s:select> 
							</td>
						</tr>
						<tr>
							<td align="right">
								<label id="lrtypeName">请假种类：</label>
								<label id="ottypeName">加班种类：</label>
							</td>
							<td>
							<s:select  id="lrtype" name="examinType" value="examinType" list="lrtypeList"  listKey="ltNo" listValue="ltName" emptyOption="true" ></s:select>
							<s:select  id="ottype" name="examinType" value="examinType" list="ottypeList" listKey="otNo" listValue="otName" emptyOption="true" ></s:select>
							</td>
							<td nowrap="nowrap" colspan="2" align="center">
								<input type="checkbox" id="includeDeptPass" name="includeDeptPass" value="includeDeptPass"> 
								包含部门已审
							</td>
						</tr>
					</table>
				</td>
				<td>				
					<input title="查看报表" accesskey="L" name="htmlReportOne" id="htmlReportOne" class="button" type="button" value="查看报表" onclick="showReportOne('')">
					<input title="PDF下载" accesskey="D" name="pdfReportOne" id="pdfReportOne" class="button" type="button" value="PDF下载" onclick="showReportOne('pdf')">
				</td>
			</tr>
		</table>
	</div>
	<div id="tabs-2">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="right">报表类型：</td>
							<td>
							<s:select id="reportTypeSelect" name="reportTypeSelect" value="reportTypeSelect" list="#{'1':'员工每月考勤-请假','2':'员工每月考勤-加班','3':'员工每月考勤-迟到','4':'员工每月考勤-早退','5':'员工每月考勤-旷工'}"></s:select>
							</td>
							<td align="right">统计年月：</td>
							<td>
							<s:select id="attendmonthlyYear" name="attendmonthlyYear" list="attendmonthlyYearList"></s:select>
							<s:select id="attendmonthlyMonths" name="attendmonthlyMonths" value="attendmonthlyMonths" list="#{'01':'一月','02':'二月','03':'三月','04':'四月','05':'五月','06':'六月','07':'七月','08':'八月','09':'九月','10':'十月','11':'十一月','12':'十二月'}"></s:select> 
							</td>
						</tr>
					</table>
				</td>
				<td>				
					<input title="查看报表" accesskey="L" name="htmlReportTwo" id="htmlReportTwo" class="button" type="button" value="查看报表" onclick="showReportTwo('')">
					<input title="PDF下载" accesskey="D" name="pdfReportTwo" id="pdfReportTwo" class="button" type="button" value="PDF下载" onclick="showReportTwo('pdf')">
					<br> 
				</td>
		</table>
	</div>
</div>
<p align="left">&nbsp;</p>
<IFRAME ID="IFrame1" name="IFrame1" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
 	width="100%" height="480" SRC="" style="overflow-y:auto;"></IFRAME>
<script type="text/javascript">
	reportChange();
		//清楚页面中reportArea的内容。
	function clearShow(){
	 $("#IFrame1").contents().find("html").remove();
	}
</script>
</body>
</html>