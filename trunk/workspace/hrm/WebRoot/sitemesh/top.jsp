<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<script type="text/javascript" src="../resource/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../dwr/interface/searchEmp.js"></script>
	
<script language="javascript">
	//通过 topSearchKey 里面的内容，搜索匹配的no或者name，生成下来列表供用户选择
	function topFindEmpNoOrName(){
		var ele=model.simple.getElement("topSearchKey");
		var searchValue=ele.value.trim();
		ele.value=searchValue;
		searchEmp.searchNoOrName(searchValue,callback);
		function callback(idNos){
			model.simple.setSelectDiv("topSearchKey","divTopSearchSelect",idNos);
		}
	}

	//跳转到empTreeInit.action，根据topSearchKey里面的内容来显示对应的员工信息
	function topSearchEmp() {
		var searchKey=model.simple.getElementValue("topSearchKey");
		if(hrm.common.isEmpty(searchKey)){
			alert("请输入你要搜索的员工信息！");
			return;
		}
		
		window.location.href = encodeURI("../profile/empTreeInit.action?id=" + encodeURIComponent(searchKey)); 
	}

	/**
	* 在菜单数组(menuList)里面增加一个菜单，其属性包括menuUrl,menuDesc,menuIcon, simpleDesc
	*/
	function addOneMenu(menuList,menuUrl,menuDesc/*, menuIcon, simpleDesc*/){
		var menu={};
		menu["url"]=menuUrl;
		menu["desc"]=menuDesc;
		if(arguments[3]!=null && arguments[3]!=""){
			menu["icon"]=hrm.common._imgPath+arguments[3];
		}
		if(arguments[4]==null || arguments[4]==""){
			menu["descSim"]=menuDesc;
		}else{
			menu["descSim"]=arguments[4];
		}
		menuList[menuList.length]=menu;
	}
	/**
	* 在菜单数组(menuList)里面增加一个菜单，其属性包括menuDesc,dir,subMenu, menuUrl, menuIcon。
	* 如果增加菜单里面没有子菜单(subMenu)，则不增加这个菜单
	*/
	function addOneMenuWithSubMenu(menuList,menuDesc,dir,subMenu/*, menuUrl, menuIcon*/){
		if(subMenu.length<1){
			return;
		}
		var menu={};
		menu["desc"]=menuDesc;
		menu["dir"]=dir;
		for(var i=0;i<subMenu.length;i++){
			if(subMenu[i]["url"].indexOf("../")==-1){
				subMenu[i]["url"]="../"+dir+"/"+subMenu[i]["url"];
			}
		}
		menu["subMenu"]=subMenu;
		if(arguments[4]==null || arguments[4]==""){
			menu["url"]=subMenu[0]["url"];
		}else{
			menu["url"]=arguments[4];
		}
		if(arguments[5]!=null && arguments[5].length){
			menu["icon"]=hrm.common._imgPath+arguments[5];
		}
		menuList[menuList.length]=menu;
	}
	/**
	* 初始化一级、二级菜单数组，每个菜单是一个object，包含属性：desc(描述)，dir(隶属目录)，url，subMenu(子菜单数组)，icon(图标),descSim(简单描述)
	*/
	function getHrmAllMenu(){
		var menuList=new Array();
		var indexTmp=0;
		//首页
		var subMenuList=new Array();
		<ty:auth auths="111">addOneMenu(subMenuList,"../profile/myInfo.action?empNo=<s:property value='#session.userinfo.id'/>","我的资料");</ty:auth>
		addOneMenu(subMenuList,"../information/passwordOwn.action","修改密码");
		<ty:auth auths="101 or 111">addOneMenu(subMenuList,"../profile/empTreeInit.action?id=<s:property value='#session.userinfo.id'/>","员工通讯录");</ty:auth>
		<ty:auth auths="411">addOneMenu(subMenuList,"../examin/empExaminAdd.action?tab=1","休假申请");</ty:auth>
		<ty:auth auths="411">addOneMenu(subMenuList,"../examin/empExaminAdd.action?tab=2","加班申请");</ty:auth>
		<ty:auth auths="311">addOneMenu(subMenuList,"../training/trepAddInit.action","培训申请");</ty:auth>
		<ty:auth auths="611">addOneMenu(subMenuList,"../recruitment/SearchRecruitplan.action","招聘计划");</ty:auth>
		addOneMenuWithSubMenu(menuList,"首页","homepage",subMenuList,"../homepage/index.action");

		//员工
		subMenuList=new Array();
		<ty:auth auths="101 or 111,2 or 111,3">addOneMenu(subMenuList,"searchEmp.action","员工管理","Teams.gif");</ty:auth>
		<ty:auth auths="101">addOneMenu(subMenuList,"empCreateInit.action","新员工入职","CreateEmployees.gif","新增员工");</ty:auth>
		<ty:auth auths="101">addOneMenu(subMenuList,"manageContract.action","合同管理","Tasks.gif");</ty:auth>
		<ty:auth auths="101">addOneMenu(subMenuList,"empQuitManagement.action","离职员工管理","Employees_quit.gif","离职员工");</ty:auth>
		<ty:auth auths="101 or 111,3 or 111,2">addOneMenu(subMenuList,"profileManagement.action","档案管理","Documents.gif");</ty:auth>
		<ty:auth auths="101,2 or 101,3">addOneMenu(subMenuList,"empReport.action","员工报表","MeetingReports.gif","报表");</ty:auth>
		<ty:auth auths="101 or 111">addOneMenu(subMenuList,"empTreeInit.action?id=<s:property value='#session.userinfo.id'/>","员工通讯录","namelist.gif","通讯录");</ty:auth>
		<ty:auth auths="111">addOneMenu(subMenuList,"myInfo.action?tab=1&empNo=<s:property value='#session.userinfo.id'/>","我的资料","member_face2.gif");</ty:auth>
		<ty:auth auths="101,3">addOneMenu(subMenuList,"orgDeptManage.action","部门管理","QueryBuilder.gif");</ty:auth>
		<ty:auth auths="101,3">addOneMenu(subMenuList,"showPbManagePage.action","职位管理","MyProject.gif");</ty:auth>
		<ty:auth auths="101,3">addOneMenu(subMenuList,"showPositionStructure.action","组织结构管理","users.gif");</ty:auth>
		<ty:auth auths="101,3">addOneMenu(subMenuList,"showOrgMap.action","组织结构图","org_chart.png");</ty:auth>
		addOneMenuWithSubMenu(menuList,"员工","profile",subMenuList,null,"icon1.gif");
		
		//薪资
		subMenuList=new Array();
		<ty:auth auths="201">addOneMenu(subMenuList,"searchSalary.action?emp.empStatus=1","员工薪资配置","Prospects.gif","薪资配置");</ty:auth>
		<ty:auth auths="201 or 211,3 or 211,2">addOneMenu(subMenuList,"searchSalaryPaid.action","每月薪资发放","ProjectPaste.gif","薪资发放");</ty:auth>
		<ty:auth auths="201,3 or 201,2">addOneMenu(subMenuList,"searchBatchCompaplan.action","员工调薪计划","SalaryPaidApprove.gif","调薪计划");</ty:auth>
		<ty:auth auths="201,3 or 201,2">addOneMenu(subMenuList,"searchCompaplan.action","调薪历史查询","Reports.gif","调薪历史");</ty:auth>
		<ty:auth auths="201">addOneMenu(subMenuList,"searchEmpbenefit.action?employee.empStatus=1","员工社保管理","Documents.gif","社保");</ty:auth>
		<ty:auth auths="201">addOneMenu(subMenuList,"searchEbp.action","员工社保补缴","Documents.gif","社保补缴");</ty:auth>
		<ty:auth auths="201">addOneMenu(subMenuList,"searchBeneHistory.action","社保缴费历史","Documents.gif","缴费历史");</ty:auth>
		<ty:auth auths="211">addOneMenu(subMenuList,"mySalaryConf.action","我的薪资配置","Prospects.gif");</ty:auth>
		<ty:auth auths="201 or 211">addOneMenu(subMenuList,"mySalaryPaid.action","我的薪资发放","ProjectPaste.gif");</ty:auth>
		<ty:auth auths="201,3">addOneMenu(subMenuList,"salaryRpInit.action","薪资综合报表","CompaReport.gif","报表");</ty:auth>
		<ty:auth auths="201,3 or 201,2">addOneMenu(subMenuList,"compensationConfig.action","薪资设置","Releases.gif");</ty:auth>
		<ty:auth auths="201,3 or 201,2">addOneMenu(subMenuList,"searchesa.action","薪资帐套配置","Releases.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"薪资","compensation",subMenuList,null,"icon2.gif");

		//考勤
		subMenuList=new Array();
		<ty:auth auths="411">addOneMenu(subMenuList,"myExamins.action","我的请假加班","MyProject.gif");</ty:auth>
		<ty:auth auths="411 or 401">addOneMenu(subMenuList,"empExaminAdd.action","请假加班申请","CreateProject.gif","申请");</ty:auth>
		<ty:auth auths="411,2 or 411,3">addOneMenu(subMenuList,"deptExaminSearch.action","经理审批","DeptApprove.gif");</ty:auth>
		<ty:auth auths="401,2 or 401,3">addOneMenu(subMenuList,"hrExaminSearch.action","HR备案","HRApprove.gif");</ty:auth>
		<ty:auth auths="411,2 or 411,3 or 401">addOneMenu(subMenuList,"allExaminSearch.action","请假加班列表","Reports.gif","请假加班");</ty:auth>
		<s:if test="@com.hr.base.ComonBeans@getShiftEnable()==1">
		<ty:auth auths="401 or 411,3 or 411,2">addOneMenu(subMenuList,"examinScheduleSearch.action","排班管理","ArrangeShift.gif","排班");</ty:auth>
		<ty:auth auths="401 or 411,3 or 411,2">addOneMenu(subMenuList,"originalDataImportShow.action","刷卡管理","TimePeriods.gif","刷卡");</ty:auth>
		<!--<ty:auth auths="401,3 or 401,2">addOneMenu(subMenuList,"attdSyncRecordShow.action","考勤机数据同步","TimePeriods.gif","刷卡");</ty:auth>-->
		</s:if>
		<ty:auth auths="411 or 401">addOneMenu(subMenuList,"searchAttenddaily.action","每日考勤记录","Schedulers.gif","每日考勤");</ty:auth>
		<ty:auth auths="401 or 411,3 or 411,2">addOneMenu(subMenuList,"searchAttendmonthly.action","每月考勤汇总 ","Calendar.gif","每月汇总");</ty:auth>
		<ty:auth auths="401,3 or 401,2">addOneMenu(subMenuList,"examinReport.action","考勤报表 ","AttdReports.gif","报表");</ty:auth>
		<ty:auth auths="401,3 or 401,2">addOneMenu(subMenuList,"workFlowApprove.action","审批设置","Newsletters.gif");</ty:auth>
		<ty:auth auths="401,3 or 401,2">addOneMenu(subMenuList,"leavebalanceManage.action","考勤设置","Newsletters.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"考勤","examin",subMenuList,null,"icon3.gif");

		//培训
		subMenuList=new Array();
		<ty:auth auths="311">addOneMenu(subMenuList,"myTrainingList.action","我的培训计划","MyProject.gif");</ty:auth>
		<ty:auth auths="311">addOneMenu(subMenuList,"trepAddInit.action","培训申请","CreateProject.gif");</ty:auth>
		<ty:auth auths="311,2 or 311,3">addOneMenu(subMenuList,"trepDeptApprove.action","部门经理审批","DeptApprove.gif");</ty:auth>
		<ty:auth auths="301,2">addOneMenu(subMenuList,"trepHrApprove.action","HR备案","HRApprove.gif");</ty:auth>
		<ty:auth auths="311,2 or 311,3 or 301">addOneMenu(subMenuList,"trepList.action","员工培训计划","Reports.gif");</ty:auth>
		<ty:auth auths="301">addOneMenu(subMenuList,"trReport.action","培训报表","CallReports.gif","报表");</ty:auth>
		<ty:auth auths="301">addOneMenu(subMenuList,"trcourseConfig.action","课程设置","Project2Weeks.gif");</ty:auth>
		<ty:auth auths="301">addOneMenu(subMenuList,"trtConfig.action","培训种类","Administration.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"培训","training",subMenuList,null,"icon4.gif");
		
		//招聘
		subMenuList=new Array();
		<ty:auth auths="611">addOneMenu(subMenuList,"../recruitment/SearchRecruitplan.action","我的招聘计划","MyProject.gif");</ty:auth>
		<ty:auth auths="611">addOneMenu(subMenuList,"../recruitment/AddRecruitplanInit.action","新增招聘计划","CreateProject.gif");</ty:auth>
		<ty:auth auths="611,2 or 611,3">addOneMenu(subMenuList,"../recruitment/ApproverRecruitplanDept.action","部门经理审批","DeptApprove.gif");</ty:auth>
		<ty:auth auths="601,2">addOneMenu(subMenuList,"../recruitment/ApproverRecruitplanHR.action","HR备案","HRApprove.gif");</ty:auth>
		<ty:auth auths="611,2 or 611,3 or 601">addOneMenu(subMenuList,"../recruitment/SearchRecruitplanForHR.action","招聘计划列表","Reports.gif");</ty:auth>
		<ty:auth auths="601">addOneMenu(subMenuList,"../recruitment/recruitapplierSearch.action","应聘人才库","Meetings.gif");</ty:auth>
		<ty:auth auths="601">addOneMenu(subMenuList,"../recruitment/recruitmentReport.action","招聘报表","Preview.gif");</ty:auth>
		<ty:auth auths="601">addOneMenu(subMenuList,"../recruitment/recruitchannellist.action","招聘渠道列表","Rebuild.gif","招聘渠道");</ty:auth>
		addOneMenuWithSubMenu(menuList,"招聘","recruitment",subMenuList,null,"icon5.gif",null,"icon5.gif");
		
		//报表
		subMenuList=new Array();
		<ty:auth auths="101">addOneMenu(subMenuList,"empReport.action","员工报表","MyReports.gif");</ty:auth>
		<ty:auth auths="201,2">addOneMenu(subMenuList,"salaryRpInit.action","薪资报表","CompaReport.gif");</ty:auth>
		<ty:auth auths="401">addOneMenu(subMenuList,"empReport.action","考勤报表","AttdReports.gif");</ty:auth>
		<ty:auth auths="301">addOneMenu(subMenuList,"empReport.action","培训报表","CallReports.gif");</ty:auth>
		<ty:auth auths="601">addOneMenu(subMenuList,"empReport.action","招聘报表","Preview.gif");</ty:auth>
		<ty:auth auths="701">addOneMenu(subMenuList,"listAll.action?report.reportType=0","自定义报表","ReportMaker.gif");</ty:auth>
		<ty:auth auths="701">addOneMenu(subMenuList,"empReport.action","预定义报表","BirtReports.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"报表","report",subMenuList,null,"report.gif");
		
		//信息中心
		subMenuList=new Array();
		<ty:auth auths="802 or 801">addOneMenu(subMenuList,"searchInfo.action","公司信息","SugarPortal.gif");</ty:auth>
		<ty:auth auths="801">addOneMenu(subMenuList,"createInfoInit.action","新增公司信息","CreateContacts.gif");</ty:auth>
		<ty:auth auths="801">addOneMenu(subMenuList,"emailsend.action","邮件通知","EmailSend.gif");</ty:auth>
		<ty:auth auths="801">addOneMenu(subMenuList,"NewsInfo.action","公司信息种类","Rebuild.gif");</ty:auth>
		addOneMenu(subMenuList,"passwordOwn.action","修改密码","edit_inline.gif");
		addOneMenuWithSubMenu(menuList,"信息中心","information",subMenuList,null,"info.gif");
		
		//系统
		subMenuList=new Array();
		<ty:auth auths="911">addOneMenu(subMenuList,"userList.action","用户管理","CreateUsers.gif","用户");</ty:auth>
		<ty:auth auths="921">addOneMenu(subMenuList,"getRoleList.action","角色管理","CreateRoles.gif","角色");</ty:auth>
		<ty:auth auths="963">addOneMenu(subMenuList,"backupResume.action","备份与还原","Backups.gif","备份还原");</ty:auth>
		<ty:auth auths="941">addOneMenu(subMenuList,"logscan.action","日志管理","ConfigureTabs.gif","日志");</ty:auth>
		<ty:auth auths="962">addOneMenu(subMenuList,"dataclean.action","数据清理","DataSets.gif");</ty:auth>
		<ty:auth auths="951">addOneMenu(subMenuList,"emailSearch.action","系统邮件管理","EmailSend.gif","邮件");</ty:auth>
		<ty:auth auths="952">addOneMenu(subMenuList,"config.action","基础表格设置","Rebuild.gif","基础表格");</ty:auth>
		<ty:auth auths="961">addOneMenu(subMenuList,"systemconfiginit.action","系统参数设置","Administration.gif","系统参数");</ty:auth>
		<ty:auth auths="702">addOneMenu(subMenuList,"iodefList.action","数据接口配置","MigrateFields.gif","数据接口");</ty:auth>
		<ty:auth auths="931">addOneMenu(subMenuList,"clientInit.action","公司注册信息","License.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"系统","configuration",subMenuList,null,"icon6.gif");
		return menuList;
	}	

	//通过当前url得到菜单列表的对应索引
	function getCurrTopMenuIndex(menuList){
		for(var i=0;i<menuList.length;i++){
			if(document.location.pathname.indexOf("/"+menuList[i]["dir"]+"/")>-1){
				return i;
			}
		}
		return -1;
	}
	//初始化页面顶部菜单html
	function initTopMenu(menuList,selectIndex){
		var menuArr=new Array();
		for(var i=0;i<menuList.length;i++){
			var j=menuList.length-1-i;
			if(i==selectIndex){
				menuArr[j]='<li class="sswo">'
			}else{
				menuArr[j]='<li class="ss">'
			}
			menuArr[j]+='<a href="'+menuList[i]["url"]+'" onmouseover="showSubMenu(this,'+i+');" >'+menuList[i]["desc"]+'</a>';
		}
		var divEle=model.simple.getElement("topmenu");
		divEle.innerHTML="<ul>"+menuArr.join("\n")+"</ul>";
	}
	//根据选中的一级菜单，先是对应的二级菜单
	function showSubMenu(menuEle,selectIndex){
		//根据cookie判断是否显示二级菜单
		if(hrm.common.getCookie('showLeftCol')=="true"){
			return;
		}
		//得到二级菜单
		var subMenuArr=hrmAllMenu[selectIndex]["subMenu"];
		//设定二级菜单html
		var menuArr=new Array();
		for(var i=0;i<subMenuArr.length;i++){
			menuArr[i]='<a href="'+subMenuArr[i]["url"]+'">'+subMenuArr[i]["descSim"]+'</a>';
		}
		//将二级菜单内容写入div
		var divEle=model.simple.getElement("submenu");
		divEle.style.width="";
		divEle.innerHTML="<table id='subMenuTable' align='left'><tr><td style='white-space:nowrap'>"+menuArr.join("</td><td>")+"</td></tr></table>";
		//设定二级菜单位置
		var tableEle=model.simple.getElement("subMenuTable");
		//为超连接字体变粗时预留位置
		tableWidth=tableEle.offsetWidth+10;
		tableEle.style.width=tableWidth+"px";
		divEle.style.width=tableWidth+"px";
		var menuPosition=model.simple.getElementPosition(menuEle);
		var divPosition=model.simple.getElementPosition(divEle);
		if(divPosition.left>menuPosition.left){
			var width=divPosition.left-menuPosition.left+divEle.offsetWidth;
			divEle.style.width=width+"px";
		}
	}	
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="top">
	<tr style="padding-left:5px;padding-top:5px;padding-bottom:5px">
		<td rowspan="2" width="200"><img src="../resource/images/logo_365hrm.gif" alt="365hrm logo" /></td>
		<td align="right" valign="bottom">
			<span class="welcome">
				<a href="http://www.tengsource.com/contact.htm" target="_blank">联系我们</a>
				| <a href="../help/searchHelp.action" target="_blank">帮助</a>
				| <a href="../system/about.jsp" target="_blank">版本信息</a> &nbsp;
			</span>
			<input type="text" name="topSearchKey" id="topSearchKey" onkeyup="topFindEmpNoOrName()"/>
			<input name="Submit" type="button" value="搜索" onclick='topSearchEmp()'>
			&nbsp;
			<div style="position:absolute;z-index:10;border:black 1px solid;bgColor:FFFFFF;display: none;" id="divTopSearchSelect"></div>
		</td>
	</tr>
	
	<tr>
		<td height="25" align="right" valign="bottom">
			<div id="topmenu"></div>
		</td>
	</tr>
	
	<tr class="visits">
		<td nowrap="nowrap">
			欢迎您，<s:property value="#session['empName']"/>
			|<a href="../configuration/logout.action"><span class="visitsA">退出系统</span></a>
		</td>
		<td align="right">
			<div id="submenu" style="z-index:10" class="secondmenu"></div>
		</td>
	</tr>
</table>
<script language="javascript">
//初始化一级二级菜单
var hrmAllMenu=getHrmAllMenu();
//初始化当前菜单索引
var currMenuIndex=getCurrTopMenuIndex(hrmAllMenu);
initTopMenu(hrmAllMenu,currMenuIndex);
</script>
<!-- end top -->
