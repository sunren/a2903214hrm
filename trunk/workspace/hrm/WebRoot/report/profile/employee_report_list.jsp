<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar"%>
<html>
	<head>
		<title>员工报表</title>
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
			<s:param name="pagetitle" value="'员工报表：'" />
			<s:param name="helpUrl" value="'37'" />
		</s:component>
		<div id="tabs">
			<ul>
				<li>
					<a href="#tabs-1" onclick="clearShow();">在职员工分析</a>
				</li>
				<li>
					<a href="#tabs-2" onclick="clearShow();">在职员工详细分析</a>
				</li>
				<li>
					<a href="#tabs-3" onclick="clearShow();">员工离职率分析</a>
				</li>
				<li>
					<a href="#tabs-4" onclick="clearShow();">员工流动信息统计</a>
				</li>
			</ul>
			<div id="tabs-1">
				<p>
					选择报表：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="select01">
						<option value="emp_department_report.rptdesign">
							在职员工人数统计-按部门
						</option>
						<option value="emp_location_report.rptdesign">
							在职员工人数统计-按工作地区
						</option>
						<option value="emp_emptype_report.rptdesign">
							在职员工人数统计-按用工形式
						</option>
						<option value="emp_jobtitle_report.rptdesign">
							在职员工人数统计-按职务
						</option>
						<option value="emp_politics_report.rptdesign">
							在职员工人数统计-按政治面貌
						</option>
						<option value="emp_diploma_report.rptdesign">
							在职员工人数统计-按学历
						</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value=" 查看 "
						onclick="display('select01','HTML');">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="PDF下载"
						onclick="display('select01','PDF');">
				</p>
			</div>
			<div id="tabs-2">
				<p>
					选择报表：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="select02">
						<option value="emp_by_location_report.rptdesign">
							在职员工人数按部门统计-分工作地区
						</option>
						<option value="emp_by_emptype_report.rptdesign">
							在职员工人数按部门统计-分用工形式
						</option>
						<option value="emp_by_jobtitle_report.rptdesign">
							在职员工人数按部门统计-分职务
						</option>
						<option value="emp_by_politics_report.rptdesign">
							在职员工人数按部门统计-分政治面貌
						</option>
						<option value="emp_by_diploma_report.rptdesign">
							在职员工人数按部门统计-分学历
						</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value=" 查看 "
						onclick="display('select02','HTML');" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="PDF下载"
						onclick="display('select02','PDF');" />
				</p>
			</div>
			<div id="tabs-3">
				<p>
					选择报表：
					<select id="select03">
						<option value="emp_dimissionrate_by_department.rptdesign">
							员工离职率分析-分部门
						</option>
						<option value="emp_dimissionrate_by_location.rptdesign">
							员工离职率分析-分工作地区
						</option>
						<option value="emp_dimissionrate_by_emptype.rptdesign">
							员工离职率分析-分用工形式
						</option>
						<option value="emp_dimissionrate_by_jobgrade.rptdesign">
							员工离职率分析-分薪资级别
						</option>
					</select>
					&nbsp;&nbsp;&nbsp; 选择时间范围：
					<s:textfield id="startDate" name="startDate" size="10" />
					<img onclick="WdatePicker({el:'startDate'})"
						src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16"
						height="22" style="cursor: pointer" align="absmiddle">
					到
					<s:textfield id="endDate" name="endDate" size="10" />
					<img onclick="WdatePicker({el:'endDate'})"
						src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16"
						height="22" style="cursor: pointer" align="absmiddle">
					&nbsp;&nbsp;
					<input type="button" value=" 查看 "
						onclick="dimission('select03','HTML');" />
					&nbsp;&nbsp;
					<input type="button" value="PDF下载"
						onclick="dimission('select03','PDF');" />
				</p>
			</div>
			<div id="tabs-4">
				<p>
					选择报表：
					<select id="select04">
						<option value="emp_sum_by_yearmonth.rptdesign">
							员工总数统计
						</option>
						<option value="emp_new_by_yearmonth.rptdesign">
							新进员工统计
						</option>
						<option value="emp_newrate_by_yearmonth.rptdesign">
							员工新进率统计
						</option>
						<option value="emp_dimissionsum_by_yearmonth.rptdesign">
							离职员工统计
						</option>
						<option value="emp_dimission_percent_by_yearmonth.rptdesign">
							员工离职率统计
						</option>
						<option value="emp_netrate_by_yearmonth.rptdesign">
							员工净流动率统计
						</option>
					</select>
					&nbsp;&nbsp;&nbsp; 选择时间范围：
					<s:textfield id="select04_startDate" name="startDate" size="10" />
					<img onclick="WdatePicker({el:'select04_startDate'})"
						src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16"
						height="22" style="cursor: pointer" align="absmiddle">
					到
					<s:textfield id="select04_endDate" name="endDate" size="10" />
					<img onclick="WdatePicker({el:'select04_endDate'})"
						src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16"
						height="22" style="cursor: pointer" align="absmiddle">
					&nbsp;&nbsp;
					<s:select id="select04_groupBy" list="#{'0':'按月份统计','1':'按年统计'}" />
					&nbsp;&nbsp;
					<input type="button" value=" 查看 "
						onclick="empSum('select04','HTML');" />
					&nbsp;&nbsp;
					<input type="button" value="PDF下载"
						onclick="empSum('select04','PDF');" />
					&nbsp;&nbsp;&nbsp;
					<span>(按月统计如果超过12个月，只显示前12个月)</span>
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
	      error +="开始日期格式错误！\n";
	  }
	  if(!isNull(endDate)&&!isDate(endDate)){
	      error +="结束日期格式错误！\n";
	  }
	  if(!isNull(startDate)&&!isNull(endDate)&&startDate>endDate){
		  error +="开始时间不能大于结束时间！\n";
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
		  error +="开始时间为空！"
		  }
		  if(isNull(endDate)){
		  error +="结束时间为空！";
		  }
		   if(error != ''){
	        alert(error);
	        return;
	      }
		  
		  if(!isDate(startDate)){
		      error +="开始日期格式错误！\n";
		  }
		  if(!isDate(endDate)){
		      error +="结束日期格式错误！\n";
		  }
		  if(startDate>endDate){
			  error +="开始时间不能大于结束时间！\n";
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
		
	//页面加载后执行
	function onPageInit(){
	  var now = getCurrentDate();
	  $('#endDate').val(now);
	  var month_begin = now.substring(0,8)+"01";
	  $('#startDate').val(month_begin);
	}
	onPageInit();
	
	
	//清楚页面中reportArea的内容。
	function clearShow(){
	 $("#reportArea").contents().find("html").remove();
	}
</script>
<jsp:include page="../../sitemesh/div_wait.jsp" flush="true"></jsp:include>
</body>
</html>
