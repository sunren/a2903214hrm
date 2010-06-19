<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<!-- css������Ϣ -->
	<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />	

	<!-- �Լ���� -->
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
			alert("��ʼ�·ݱ���С�ڵ��ڽ����·�");
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
	<title>��Ƹ��Ϣ����</title>
</head>
<body>
<s:component template="bodyhead">
		<s:param name="pagetitle" value="'��Ƹ��Ϣ����'" />		
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
							<s:select label="��������" id="reportSelect" name="reportSelect" value="reportSelect" list="#{'report1':'��Ƹ�ƻ�����','report2':'ӦƸ�߷���'}" onchange="reportChange();"></s:select>
						</td>
						<td nowrap="nowrap" align="right">
							<label id="periodLable">ͳ�����ڣ�</label>
						</td>
						<td id="timeSelect" nowrap="nowrap" style="display:none;">
							<s:select id="year" name="year" list="yearList"></s:select>
							<s:select id="startMonths" name="startM" value="startM" list="#{'m1':'һ��','m2':'����','m3':'����','m4':'����','m5':'����','m6':'����','m7':'����','m8':'����','m9':'����','m10':'ʮ��','m11':'ʮһ��','m12':'ʮ����'}"></s:select> 
							��
							<s:select id="endMonths" name="endM"  value="endM" list="#{'m1':'һ��','m2':'����','m3':'����','m4':'����','m5':'����','m6':'����','m7':'����','m8':'����','m9':'����','m10':'ʮ��','m11':'ʮһ��','m12':'ʮ����'}"></s:select> 
						</td>
					</tr>
				</table>
			</td>
			<td>				
				<input title="�鿴����" accesskey="L" name="htmlReport" id="htmlReport" class="button" type="submit" value="�鿴����" onclick="javaScript:$('actionSrc').action='recruitmentReport.action';$('actionSrc').submit();$('htmlReport').disabled='disabled'">
				<input title="PDF����" accesskey="D" name="pdfReport" id="pdfReport" class="button" type="submit" value="PDF����" onclick="pdfGetSubmit()">
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