<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>员工薪资报表</title>
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
	<s:param name="pagetitle" value="'员工薪资报表：'" />
	<s:param name="helpUrl" value="'37'" />
</s:component>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1" onclick="clearShow();" >薪资配置分析</a></li>
		<li><a href="#tabs-2" onclick="clearShow();" >薪资历史分析</a></li>
		<li><a href="#tabs-3" onclick="clearShow();" >人工成本分析</a></li>
	</ul>
	<div id="tabs-1">
		<p>
			选择报表：&nbsp;&nbsp;
			<select id="select01">
				<option value="employee.empDeptNo.departmentName">薪资配置分析-按部门</option>
				<option value="employee.empLocationNo.locationName">薪资配置分析-按工作地区</option>
				<option value="employee.empType.emptypeName">薪资配置分析-按用工形式</option>
			</select>&nbsp;&nbsp;
			选择帐套项目：&nbsp;&nbsp;
			<s:select list="items" listKey="esddId" listValue="esddName" id="itemid" emptyOption="true"></s:select>&nbsp;&nbsp;
			<input type="button" value=" 查看 " onclick="showReport('HTML');" />&nbsp;&nbsp;
			<input type="button" value="PDF下载" onclick="showReport('PDF');">
		</p>
	</div>
	<div id="tabs-2">
		<table width="100%" style="border: 0pt" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					选择部门：
					<s:select list="departments" listKey="id"
						listValue="departmentName" id="tab2_department"
						emptyOption="true"></s:select>
				</td>
				<td>
					起始年月：
					<s:select id="tab2_startyear" name="year" list="years"
						emptyOption="false" />
					<s:select id="tab2_startmonth" name="tab2_startmonth"
						list="#{'01':'1','02':'2','03':'3','04':'4','05':'5','06':'6','07':'7','08':'8','09':'9','10':'10','11':'11','12':'12'}"
						emptyOption="false" />
				</td>
				<td>
					选择帐套项目：
					<s:select list="items" listKey="esddId" listValue="esddName"
						id="tab2_itemid" emptyOption="true"></s:select>
				</td>
				<td rowspan='2'>
					<input type="button" value=" 查看 " onclick="viewHistoryReport('HTML')" />&nbsp;&nbsp;
					<input type="button" value="PDF下载" onclick="viewHistoryReport('PDF')" />
				</td>
			</tr>
			<tr>
				<td>
					薪资级别：
					<s:select list="jobgrades" listKey="id" listValue="jobGradeName"
						id="tab2_jobgrade" emptyOption="true"></s:select>
				</td>
				<td>
					结束年月：
					<s:select id="tab2_endyear" name="year" list="years" emptyOption="false" />
					<s:select id="tab2_endmonth" name="month" emptyOption="false"
						list="#{'01':'1','02':'2','03':'3','04':'4','05':'5','06':'6','07':'7','08':'8','09':'9','10':'10','11':'11','12':'12'}" />
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div id="tabs-3">
		<table width="100%" style="border: 0pt" cellspacing="0" cellpadding="0" >
			<tr>
				<td>
					选择部门：
					<s:select list="departments" listKey="id"
						listValue="departmentName" id="tab3_department"
						emptyOption="true"></s:select>
				</td>
				<td>
					起始年月：
					<s:select id="tab3_startyear" name="year" list="years"
						emptyOption="false" />
					<s:select id="tab3_startmonth" name="tab3_startmonth"
						list="#{'01':'1','02':'2','03':'3','04':'4','05':'5','06':'6','07':'7','08':'8','09':'9','10':'10','11':'11','12':'12'}"
						emptyOption="false" />
				</td>
				<td>
					帐套项目1：
					<s:select list="items" listKey="esddId" listValue="esddName" id="tab3_itemid" emptyOption="true"></s:select>
				</td>
				<td rowspan='2'>
					<input type="button" value=" 查看 " onclick="viewCostReport('HTML')" /> &nbsp;&nbsp;
					<input type="button" value="PDF下载" onclick="viewCostReport('PDF')" />
				</td>
			</tr>
			<tr>
				<td>
					薪资级别：
					<s:select list="jobgrades" listKey="id" listValue="jobGradeName"
						id="tab3_jobgrade" emptyOption="true"></s:select>
				</td>
				<td>
					结束年月：
					<s:select id="tab3_endyear" name="year" list="years" emptyOption="false" />
					<s:select id="tab3_endmonth" name="month"
						list="#{'01':'1','02':'2','03':'3','04':'4','05':'5','06':'6','07':'7','08':'8','09':'9','10':'10','11':'11','12':'12'}"
						emptyOption="false" />
				</td>						
				<td>
					帐套项目2：
					<s:select list="items" listKey="esddId" listValue="esddName" id="tab3_itemid2" emptyOption="true"></s:select>
				</td>
			</tr>
		</table>
	</div>
</div>
<p>&nbsp;</p>
<IFRAME ID="reportArea" name="reportArea" marginWidth="0" marginHeight="0" frameborder="0" 
	align="left" width="100%" height="480" src="" style="overflow-y: auto;">
</IFRAME>
<script type="text/javascript">
     function getReportLocation(){
         var index = $('#select01')[0].selectedIndex;
         var base = "/report/compensation/";
         var result = "";
         switch(index){
             case 0:
                 result = base + "salary_analysis_departmenet.rptdesign";
                 break;
             case 1:
                 result = base + "salary_analysis_location.rptdesign";
                 break;
             case 2:
                 result = base + "salary_analysis_emptype.rptdesign";
                 break;
             default:
                 result = base + "salary_analysis_departmenet.rptdesign";
         }
         return result;
     }
     function showReport(format){
         var baseReportURL = "../compensation/salaryRp.action";
         var itemId = $('#itemid').val();
         if(isEmpty(itemId)){
        	 alert("请选择帐套项目");
             return;
         }
         var resultURL = baseReportURL +"?reportFormat="+format+"&expression="+$('#select01').val()+"&reportFileLocation="+getReportLocation();
         resultURL += "&itemId="+itemId;
         $('#reportArea').attr('src',resultURL);
     }
     function viewHistoryReport(format){
     	var baseReportURL = "../compensation/salaryHistoryReport.action";
     	//帐套项目
     	var itemId = $('#tab2_itemid').val();
         if(isEmpty(itemId)){
             alert("请选择帐套项目");
             return;
         }
         //部门
         var dep = $('#tab2_department').val();
         if(isEmpty(dep)){
         	dep = "";
         }
         //薪资级别
         var jobgrade = $('#tab2_jobgrade').val();
         if(isEmpty(jobgrade)){
         	jobgrade="";
         }
         var startyear = $('#tab2_startyear').val();//起始年
         var startmonth = $('#tab2_startmonth').val();//起始月
         var endyear = $('#tab2_endyear').val();//结束年
         var endmonth = $('#tab2_endmonth').val();//结束月

         var startDate = new Date(startyear,startmonth-1);
         var endDate = new Date(endyear,endmonth-1);
         if(startDate>endDate){
		 	alert("起始时间不能大于结束时间");
		 	return;
         }
         
         //拼接url
         var resultURL = baseReportURL +"?reportFormat="+format+"&bean.jobgradeId="+jobgrade+"&bean.departmentId="+dep+"&bean.itemId="+itemId+"&bean.startYear="+startyear+"&bean.endYear="+endyear+"&bean.startMonth="+startmonth+"&bean.endMonth="+endmonth;
     	$('#reportArea').attr('src',resultURL);
     }
     function viewCostReport(format){
     	var baseReportURL = "../compensation/salaryCostReport.action";
     	//帐套项目1
     	var itemId = $('#tab3_itemid').val();
         if(isEmpty(itemId)){
        	 alert("请选择帐套项目");
             return;
         }
         //帐套项目2
     	var itemId2 = $('#tab3_itemid2').val();
         if(isEmpty(itemId2)){
        	 alert("请选择帐套项目2");
             return;
         }
         //部门
         var dep = $('#tab3_department').val();
         if(isEmpty(dep)){
         	dep = "";
         }
         //薪资级别
         var jobgrade = $('#tab3_jobgrade').val();
         if(isEmpty(jobgrade)){
         	jobgrade="";
         }
         var startyear = $('#tab3_startyear').val();//起始年
         var startmonth = $('#tab3_startmonth').val();//起始月
         var endyear = $('#tab3_endyear').val();//结束年
         var endmonth = $('#tab3_endmonth').val();//结束月

		 var startDate = new Date(startyear,startmonth-1);
		 var endDate = new Date(endyear,endmonth-1);
		 if(startDate>endDate){
			alert("起始时间不能大于结束时间！");
			return;
		 }
			 
         
         //拼接url
         var resultURL = baseReportURL +"?reportFormat="+format+"&bean.jobgradeId="+jobgrade+"&bean.departmentId="+dep+"&bean.itemId="+itemId+"&bean.itemId2="+itemId2+"&bean.startYear="+startyear+"&bean.endYear="+endyear+"&bean.startMonth="+startmonth+"&bean.endMonth="+endmonth;
     	$('#reportArea').attr('src',resultURL);
     }
     ///页面初始化时默认选中“税前收入+代缴社保总额”
     function initSelect(){
     	var text1 = '税前收入';
     	var text2 = '代缴社保总额';
     	doSelect('tab3_itemid',text1);
     	doSelect('tab3_itemid2',text2);
     }
     function doSelect(selectid,text){
     	var select = $("#"+selectid)[0];
     	for(var i = 0; i < select.options.length; i++){
     		var tmp = select.options[i];
     		if(tmp.text == text){
     			tmp.selected = true;
     			return;
     		}
     	}
     }
     initSelect();
     
     //清楚页面中reportArea的内容。
	function clearShow(){
	 $("#reportArea").contents().find("html").remove();
	}
</script>
</body>
</html>