<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
  	<title>Ա�����ڱ���</title>
	<!-- �Լ���� -->
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
				alert("ͳ������Ϊ�գ����ܽ��б���ͳ��");
				return false;
			}
			var starNumt=$('#startMonths').val().substr(1);
			var endNum=$('#endMonths').val().substr(1);
			if(parseInt(starNumt)<=parseInt(endNum)){
				//$('htmlReport').disabled="disabled";
			//	$('pdfReport').disabled="disabled";
				return true;
			}
			alert("��ʼ�·ݱ���С�ڵ��ڽ����·�");
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
				alert("ͳ�����Ϊ�գ����ܽ��б���ͳ��");
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
    <s:param name="pagetitle" value="'Ա�����ڱ���'" />
    <s:param name="helpUrl" value="'37'"/>
</s:component>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1" onclick="clearShow();">������Ϣ����</a></li>
		<li><a href="#tabs-2" onclick="clearShow();">ÿ�¿��ڱ���</a></li>
	</ul>
	<div id="tabs-1">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="right">�������ƣ�</td>
							<td>
							<s:select id="reportSelect" name="reportSelect" value="reportSelect" list="#{'report1':'Ա�������ʷ����','report2':'Ա���Ӱ���ʷ����'}" onchange="reportChange();"></s:select>
							</td>
							<td align="right">ͳ�����ڣ�</td>
							<td nowrap="nowrap">
								<s:select id="year" name="year" list="yearList"></s:select>
								<s:select id="startMonths" name="startMonths" value="startMonths" list="#{'m1':'һ��','m2':'����','m3':'����','m4':'����','m5':'����','m6':'����','m7':'����','m8':'����','m9':'����','m10':'ʮ��','m11':'ʮһ��','m12':'ʮ����'}"></s:select> 
								��
								<s:select id="endMonths" name="endMonths" value="endMonths" list="#{'m1':'һ��','m2':'����','m3':'����','m4':'����','m5':'����','m6':'����','m7':'����','m8':'����','m9':'����','m10':'ʮ��','m11':'ʮһ��','m12':'ʮ����'}"></s:select> 
							</td>
						</tr>
						<tr>
							<td align="right">
								<label id="lrtypeName">������ࣺ</label>
								<label id="ottypeName">�Ӱ����ࣺ</label>
							</td>
							<td>
							<s:select  id="lrtype" name="examinType" value="examinType" list="lrtypeList"  listKey="ltNo" listValue="ltName" emptyOption="true" ></s:select>
							<s:select  id="ottype" name="examinType" value="examinType" list="ottypeList" listKey="otNo" listValue="otName" emptyOption="true" ></s:select>
							</td>
							<td nowrap="nowrap" colspan="2" align="center">
								<input type="checkbox" id="includeDeptPass" name="includeDeptPass" value="includeDeptPass"> 
								������������
							</td>
						</tr>
					</table>
				</td>
				<td>				
					<input title="�鿴����" accesskey="L" name="htmlReportOne" id="htmlReportOne" class="button" type="button" value="�鿴����" onclick="showReportOne('')">
					<input title="PDF����" accesskey="D" name="pdfReportOne" id="pdfReportOne" class="button" type="button" value="PDF����" onclick="showReportOne('pdf')">
				</td>
			</tr>
		</table>
	</div>
	<div id="tabs-2">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="right">�������ͣ�</td>
							<td>
							<s:select id="reportTypeSelect" name="reportTypeSelect" value="reportTypeSelect" list="#{'1':'Ա��ÿ�¿���-���','2':'Ա��ÿ�¿���-�Ӱ�','3':'Ա��ÿ�¿���-�ٵ�','4':'Ա��ÿ�¿���-����','5':'Ա��ÿ�¿���-����'}"></s:select>
							</td>
							<td align="right">ͳ�����£�</td>
							<td>
							<s:select id="attendmonthlyYear" name="attendmonthlyYear" list="attendmonthlyYearList"></s:select>
							<s:select id="attendmonthlyMonths" name="attendmonthlyMonths" value="attendmonthlyMonths" list="#{'01':'һ��','02':'����','03':'����','04':'����','05':'����','06':'����','07':'����','08':'����','09':'����','10':'ʮ��','11':'ʮһ��','12':'ʮ����'}"></s:select> 
							</td>
						</tr>
					</table>
				</td>
				<td>				
					<input title="�鿴����" accesskey="L" name="htmlReportTwo" id="htmlReportTwo" class="button" type="button" value="�鿴����" onclick="showReportTwo('')">
					<input title="PDF����" accesskey="D" name="pdfReportTwo" id="pdfReportTwo" class="button" type="button" value="PDF����" onclick="showReportTwo('pdf')">
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
		//���ҳ����reportArea�����ݡ�
	function clearShow(){
	 $("#IFrame1").contents().find("html").remove();
	}
</script>
</body>
</html>