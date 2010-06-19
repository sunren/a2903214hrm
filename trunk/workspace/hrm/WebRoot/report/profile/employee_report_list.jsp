<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar"%>
<html>
	<head>
		<title>Ա������</title>
		<script type="text/javascript">
		$(function() {
			$("#tabs").tabs();
		});
	</script>
		<style type="text/css">
#tabs-1,#tabs-2,#tabs-3,#tabs-4 {
	background-color: #ECF6FB;
	border-top: 1px #6BB5DA solid;
}
</style>
	</head>
	<body>
		<s:component template="bodyhead">
			<s:param name="pagetitle" value="'Ա������'" />
			<s:param name="helpUrl" value="'37'" />
		</s:component>
		<div id="tabs">
			<ul>
				<li>
					<a href="#tabs-1" onclick="clearShow();">��ְԱ������</a>
				</li>
				<li>
					<a href="#tabs-2" onclick="clearShow();">��ְԱ����ϸ����</a>
				</li>
				<li>
					<a href="#tabs-3" onclick="clearShow();">Ա����ְ�ʷ���</a>
				</li>
				<li>
					<a href="#tabs-4" onclick="clearShow();">Ա��������Ϣͳ��</a>
				</li>
			</ul>
			<div id="tabs-1">
				<p>
					ѡ�񱨱�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="select01">
						<option value="emp_department_report.rptdesign">
							��ְԱ������ͳ��-������
						</option>
						<option value="emp_location_report.rptdesign">
							��ְԱ������ͳ��-����������
						</option>
						<option value="emp_emptype_report.rptdesign">
							��ְԱ������ͳ��-���ù���ʽ
						</option>
						<option value="emp_jobtitle_report.rptdesign">
							��ְԱ������ͳ��-��ְ��
						</option>
						<option value="emp_politics_report.rptdesign">
							��ְԱ������ͳ��-��������ò
						</option>
						<option value="emp_diploma_report.rptdesign">
							��ְԱ������ͳ��-��ѧ��
						</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value=" �鿴 "
						onclick="display('select01','HTML');">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="PDF����"
						onclick="display('select01','PDF');">
				</p>
			</div>
			<div id="tabs-2">
				<p>
					ѡ�񱨱�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="select02">
						<option value="emp_by_location_report.rptdesign">
							��ְԱ������������ͳ��-�ֹ�������
						</option>
						<option value="emp_by_emptype_report.rptdesign">
							��ְԱ������������ͳ��-���ù���ʽ
						</option>
						<option value="emp_by_jobtitle_report.rptdesign">
							��ְԱ������������ͳ��-��ְ��
						</option>
						<option value="emp_by_politics_report.rptdesign">
							��ְԱ������������ͳ��-��������ò
						</option>
						<option value="emp_by_diploma_report.rptdesign">
							��ְԱ������������ͳ��-��ѧ��
						</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value=" �鿴 "
						onclick="display('select02','HTML');" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="PDF����"
						onclick="display('select02','PDF');" />
				</p>
			</div>
			<div id="tabs-3">
				<p>
					ѡ�񱨱�
					<select id="select03">
						<option value="emp_dimissionrate_by_department.rptdesign">
							Ա����ְ�ʷ���-�ֲ���
						</option>
						<option value="emp_dimissionrate_by_location.rptdesign">
							Ա����ְ�ʷ���-�ֹ�������
						</option>
						<option value="emp_dimissionrate_by_emptype.rptdesign">
							Ա����ְ�ʷ���-���ù���ʽ
						</option>
						<option value="emp_dimissionrate_by_jobgrade.rptdesign">
							Ա����ְ�ʷ���-��н�ʼ���
						</option>
					</select>
					&nbsp;&nbsp;&nbsp; ѡ��ʱ�䷶Χ��
					<s:textfield id="startDate" name="startDate" size="10" />
					<img onclick="WdatePicker({el:'startDate'})"
						src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16"
						height="22" style="cursor: pointer" align="absmiddle">
					��
					<s:textfield id="endDate" name="endDate" size="10" />
					<img onclick="WdatePicker({el:'endDate'})"
						src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16"
						height="22" style="cursor: pointer" align="absmiddle">
					&nbsp;&nbsp;
					<input type="button" value=" �鿴 "
						onclick="dimission('select03','HTML');" />
					&nbsp;&nbsp;
					<input type="button" value="PDF����"
						onclick="dimission('select03','PDF');" />
				</p>
			</div>
			<div id="tabs-4">
				<p>
					ѡ�񱨱�
					<select id="select04">
						<option value="emp_sum_by_yearmonth.rptdesign">
							Ա������ͳ��
						</option>
						<option value="emp_new_by_yearmonth.rptdesign">
							�½�Ա��ͳ��
						</option>
						<option value="emp_newrate_by_yearmonth.rptdesign">
							Ա���½���ͳ��
						</option>
						<option value="emp_dimissionsum_by_yearmonth.rptdesign">
							��ְԱ��ͳ��
						</option>
						<option value="emp_dimission_percent_by_yearmonth.rptdesign">
							Ա����ְ��ͳ��
						</option>
						<option value="emp_netrate_by_yearmonth.rptdesign">
							Ա����������ͳ��
						</option>
					</select>
					&nbsp;&nbsp;&nbsp; ѡ��ʱ�䷶Χ��
					<s:textfield id="select04_startDate" name="startDate" size="10" />
					<img onclick="WdatePicker({el:'select04_startDate'})"
						src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16"
						height="22" style="cursor: pointer" align="absmiddle">
					��
					<s:textfield id="select04_endDate" name="endDate" size="10" />
					<img onclick="WdatePicker({el:'select04_endDate'})"
						src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16"
						height="22" style="cursor: pointer" align="absmiddle">
					&nbsp;&nbsp;
					<s:select id="select04_groupBy" list="#{'0':'���·�ͳ��','1':'����ͳ��'}" />
					&nbsp;&nbsp;
					<input type="button" value=" �鿴 "
						onclick="empSum('select04','HTML');" />
					&nbsp;&nbsp;
					<input type="button" value="PDF����"
						onclick="empSum('select04','PDF');" />
					&nbsp;&nbsp;&nbsp;
					<span>(����ͳ���������12���£�ֻ��ʾǰ12����)</span>
				</p>
			</div>
		</div>
		<p>
			&nbsp;
		</p>
				<IFRAME ID="reportArea" name="reportArea" marginWidth="0"
					marginHeight="0" frameborder="0" align="left" width="100%"
					height="480" src="" style="overflow-y: auto;">
				</IFRAME>

				<script type="text/javascript">
	var MyInterval;
				
    function viewReport(reportFileLocation,format){
        var baseReportURL = "../report/employeeReport.action";
        var baseEmployeeReportFolador = "/report/profile/";
        var resultURL = baseReportURL +"?reportFormat="+format+"&reportFileLocation="+baseEmployeeReportFolador+reportFileLocation;
        $("#reportArea").attr("src",resultURL);
 
    }
    function display(id,format){
    MyInterval=setInterval("hideDia()",500);
	$('#divWait').show();
        var reportFileLocation = $("#"+id).val();
        if(reportFileLocation == ''){
            return;
        }
        viewReport(reportFileLocation,format);
    }
    
    
    function hideDia(){
    	if($('#reportArea').contents().find('img')[0]!=null){
    		clearInterval(MyInterval);
    		$('#divWait').hide();
    	}
   	}
     
    
	function dimission(id,format){
	  var startDate = $('#startDate').val();
	  var endDate = $('#endDate').val();
	  var error = "";
	  var reportFileLocation = $("#"+id).val();
	  if(!isNull(startDate)&&!isDate(startDate)){
	      error +="��ʼ���ڸ�ʽ����\n";
	  }
	  if(!isNull(endDate)&&!isDate(endDate)){
	      error +="�������ڸ�ʽ����\n";
	  }
	  if(!isNull(startDate)&&!isNull(endDate)&&startDate>endDate){
		  error +="��ʼʱ�䲻�ܴ��ڽ���ʱ�䣡\n";
	  }
      if(error != ''){
        alert(error);
        return;
      }
      var defaultAction = "../report/employeeReport.action";
      if($("#"+id).val() == 'emp_dimissionrate_by_jobgrade.rptdesign'){
          defaultAction = "../report/employeeDimissionReport.action";
      }
      var resultURL =defaultAction+"?reportFormat="+format+"&reportFileLocation=/report/profile/"+reportFileLocation+"&startDate="+startDate+"&endDate="+endDate;
      $("#reportArea").attr("src",resultURL);
	}

	function empSum(id,format){
	  MyInterval=setInterval("hideDia()",500);
	  $('#divWait').show();
		  var startDate = $('#'+id+'_startDate').val();
		  var endDate =  $('#'+id+'_endDate').val();
		  var error = "";
		  var reportFileLocation = $("#"+id).val();
		  var groupBy= $('#'+id+'_groupBy').val();
		  
		  if(isNull(startDate)){
		  error +="��ʼʱ��Ϊ�գ�"
		  }
		  if(isNull(endDate)){
		  error +="����ʱ��Ϊ�գ�";
		  }
		   if(error != ''){
	        alert(error);
	        return;
	      }
		  
		  if(!isDate(startDate)){
		      error +="��ʼ���ڸ�ʽ����\n";
		  }
		  if(!isDate(endDate)){
		      error +="�������ڸ�ʽ����\n";
		  }
		  if(startDate>endDate){
			  error +="��ʼʱ�䲻�ܴ��ڽ���ʱ�䣡\n";
		  }
	      if(error != ''){
	        alert(error);
	        return;
	      }
	      var defaultAction="../report/employeeSum.action";
	      if(reportFileLocation=="emp_sum_by_yearmonth.rptdesign"){
	            defaultAction = "../report/employeeSum.action";
	      }else if(reportFileLocation=='emp_new_by_yearmonth.rptdesign'){
	            defaultAction="../report/employeeNew.action";
	      }else if(reportFileLocation=='emp_newrate_by_yearmonth.rptdesign'){
	            defaultAction="../report/employeeNewRate.action";
	      }else if(reportFileLocation=='emp_dimissionsum_by_yearmonth.rptdesign'){
	            defaultAction="../report/employDimissionSum.action";
	      }else if(reportFileLocation=='emp_dimission_percent_by_yearmonth.rptdesign'){
	            defaultAction="../report/employeeDimissionRate.action";
	      }else if(reportFileLocation=='emp_netrate_by_yearmonth.rptdesign'){
	            defaultAction="../report/employeeNetRate.action";
	      }
	      
	      var resultURL =defaultAction+"?reportFormat="+format+"&reportFileLocation=/report/profile/"+reportFileLocation+"&startDate="+startDate+"&endDate="+endDate+"&groupBy="+groupBy;
	      $("#reportArea").attr("src",resultURL);
	}
		
	//ҳ����غ�ִ��
	function onPageInit(){
	  var now = getCurrentDate();
	  $('#endDate').val(now);
	  var month_begin = now.substring(0,8)+"01";
	  $('#startDate').val(month_begin);
	}
	onPageInit();
	
	
	//���ҳ����reportArea�����ݡ�
	function clearShow(){
	 $("#reportArea").contents().find("html").remove();
	}
</script>
<jsp:include page="../../sitemesh/div_wait.jsp" flush="true"></jsp:include>
</body>
</html>
