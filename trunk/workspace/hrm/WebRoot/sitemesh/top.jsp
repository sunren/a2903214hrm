<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<script type="text/javascript" src="../resource/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../dwr/interface/searchEmp.js"></script>
	
<script language="javascript">
	//ͨ�� topSearchKey ��������ݣ�����ƥ���no����name�����������б��û�ѡ��
	function topFindEmpNoOrName(){
		var ele=model.simple.getElement("topSearchKey");
		var searchValue=ele.value.trim();
		ele.value=searchValue;
		searchEmp.searchNoOrName(searchValue,callback);
		function callback(idNos){
			model.simple.setSelectDiv("topSearchKey","divTopSearchSelect",idNos);
		}
	}

	//��ת��empTreeInit.action������topSearchKey�������������ʾ��Ӧ��Ա����Ϣ
	function topSearchEmp() {
		var searchKey=model.simple.getElementValue("topSearchKey");
		if(hrm.common.isEmpty(searchKey)){
			alert("��������Ҫ������Ա����Ϣ��");
			return;
		}
		
		window.location.href = encodeURI("../profile/empTreeInit.action?id=" + encodeURIComponent(searchKey)); 
	}

	/**
	* �ڲ˵�����(menuList)��������һ���˵��������԰���menuUrl,menuDesc,menuIcon, simpleDesc
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
	* �ڲ˵�����(menuList)��������һ���˵��������԰���menuDesc,dir,subMenu, menuUrl, menuIcon��
	* ������Ӳ˵�����û���Ӳ˵�(subMenu)������������˵�
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
	* ��ʼ��һ���������˵����飬ÿ���˵���һ��object���������ԣ�desc(����)��dir(����Ŀ¼)��url��subMenu(�Ӳ˵�����)��icon(ͼ��),descSim(������)
	*/
	function getHrmAllMenu(){
		var menuList=new Array();
		var indexTmp=0;
		//��ҳ
		var subMenuList=new Array();
		<ty:auth auths="111">addOneMenu(subMenuList,"../profile/myInfo.action?empNo=<s:property value='#session.userinfo.id'/>","�ҵ�����");</ty:auth>
		addOneMenu(subMenuList,"../information/passwordOwn.action","�޸�����");
		<ty:auth auths="101 or 111">addOneMenu(subMenuList,"../profile/empTreeInit.action?id=<s:property value='#session.userinfo.id'/>","Ա��ͨѶ¼");</ty:auth>
		<ty:auth auths="411">addOneMenu(subMenuList,"../examin/empExaminAdd.action?tab=1","�ݼ�����");</ty:auth>
		<ty:auth auths="411">addOneMenu(subMenuList,"../examin/empExaminAdd.action?tab=2","�Ӱ�����");</ty:auth>
		<ty:auth auths="311">addOneMenu(subMenuList,"../training/trepAddInit.action","��ѵ����");</ty:auth>
		<ty:auth auths="611">addOneMenu(subMenuList,"../recruitment/SearchRecruitplan.action","��Ƹ�ƻ�");</ty:auth>
		addOneMenuWithSubMenu(menuList,"��ҳ","homepage",subMenuList,"../homepage/index.action");

		//Ա��
		subMenuList=new Array();
		<ty:auth auths="101 or 111,2 or 111,3">addOneMenu(subMenuList,"searchEmp.action","Ա������","Teams.gif");</ty:auth>
		<ty:auth auths="101">addOneMenu(subMenuList,"empCreateInit.action","��Ա����ְ","CreateEmployees.gif","����Ա��");</ty:auth>
		<ty:auth auths="101">addOneMenu(subMenuList,"manageContract.action","��ͬ����","Tasks.gif");</ty:auth>
		<ty:auth auths="101">addOneMenu(subMenuList,"empQuitManagement.action","��ְԱ������","Employees_quit.gif","��ְԱ��");</ty:auth>
		<ty:auth auths="101 or 111,3 or 111,2">addOneMenu(subMenuList,"profileManagement.action","��������","Documents.gif");</ty:auth>
		<ty:auth auths="101,2 or 101,3">addOneMenu(subMenuList,"empReport.action","Ա������","MeetingReports.gif","����");</ty:auth>
		<ty:auth auths="101 or 111">addOneMenu(subMenuList,"empTreeInit.action?id=<s:property value='#session.userinfo.id'/>","Ա��ͨѶ¼","namelist.gif","ͨѶ¼");</ty:auth>
		<ty:auth auths="111">addOneMenu(subMenuList,"myInfo.action?tab=1&empNo=<s:property value='#session.userinfo.id'/>","�ҵ�����","member_face2.gif");</ty:auth>
		<ty:auth auths="101,3">addOneMenu(subMenuList,"orgDeptManage.action","���Ź���","QueryBuilder.gif");</ty:auth>
		<ty:auth auths="101,3">addOneMenu(subMenuList,"showPbManagePage.action","ְλ����","MyProject.gif");</ty:auth>
		<ty:auth auths="101,3">addOneMenu(subMenuList,"showPositionStructure.action","��֯�ṹ����","users.gif");</ty:auth>
		<ty:auth auths="101,3">addOneMenu(subMenuList,"showOrgMap.action","��֯�ṹͼ","org_chart.png");</ty:auth>
		addOneMenuWithSubMenu(menuList,"Ա��","profile",subMenuList,null,"icon1.gif");
		
		//н��
		subMenuList=new Array();
		<ty:auth auths="201">addOneMenu(subMenuList,"searchSalary.action?emp.empStatus=1","Ա��н������","Prospects.gif","н������");</ty:auth>
		<ty:auth auths="201 or 211,3 or 211,2">addOneMenu(subMenuList,"searchSalaryPaid.action","ÿ��н�ʷ���","ProjectPaste.gif","н�ʷ���");</ty:auth>
		<ty:auth auths="201,3 or 201,2">addOneMenu(subMenuList,"searchBatchCompaplan.action","Ա����н�ƻ�","SalaryPaidApprove.gif","��н�ƻ�");</ty:auth>
		<ty:auth auths="201,3 or 201,2">addOneMenu(subMenuList,"searchCompaplan.action","��н��ʷ��ѯ","Reports.gif","��н��ʷ");</ty:auth>
		<ty:auth auths="201">addOneMenu(subMenuList,"searchEmpbenefit.action?employee.empStatus=1","Ա���籣����","Documents.gif","�籣");</ty:auth>
		<ty:auth auths="201">addOneMenu(subMenuList,"searchEbp.action","Ա���籣����","Documents.gif","�籣����");</ty:auth>
		<ty:auth auths="201">addOneMenu(subMenuList,"searchBeneHistory.action","�籣�ɷ���ʷ","Documents.gif","�ɷ���ʷ");</ty:auth>
		<ty:auth auths="211">addOneMenu(subMenuList,"mySalaryConf.action","�ҵ�н������","Prospects.gif");</ty:auth>
		<ty:auth auths="201 or 211">addOneMenu(subMenuList,"mySalaryPaid.action","�ҵ�н�ʷ���","ProjectPaste.gif");</ty:auth>
		<ty:auth auths="201,3">addOneMenu(subMenuList,"salaryRpInit.action","н���ۺϱ���","CompaReport.gif","����");</ty:auth>
		<ty:auth auths="201,3 or 201,2">addOneMenu(subMenuList,"compensationConfig.action","н������","Releases.gif");</ty:auth>
		<ty:auth auths="201,3 or 201,2">addOneMenu(subMenuList,"searchesa.action","н����������","Releases.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"н��","compensation",subMenuList,null,"icon2.gif");

		//����
		subMenuList=new Array();
		<ty:auth auths="411">addOneMenu(subMenuList,"myExamins.action","�ҵ���ټӰ�","MyProject.gif");</ty:auth>
		<ty:auth auths="411 or 401">addOneMenu(subMenuList,"empExaminAdd.action","��ټӰ�����","CreateProject.gif","����");</ty:auth>
		<ty:auth auths="411,2 or 411,3">addOneMenu(subMenuList,"deptExaminSearch.action","��������","DeptApprove.gif");</ty:auth>
		<ty:auth auths="401,2 or 401,3">addOneMenu(subMenuList,"hrExaminSearch.action","HR����","HRApprove.gif");</ty:auth>
		<ty:auth auths="411,2 or 411,3 or 401">addOneMenu(subMenuList,"allExaminSearch.action","��ټӰ��б�","Reports.gif","��ټӰ�");</ty:auth>
		<s:if test="@com.hr.base.ComonBeans@getShiftEnable()==1">
		<ty:auth auths="401 or 411,3 or 411,2">addOneMenu(subMenuList,"examinScheduleSearch.action","�Ű����","ArrangeShift.gif","�Ű�");</ty:auth>
		<ty:auth auths="401 or 411,3 or 411,2">addOneMenu(subMenuList,"originalDataImportShow.action","ˢ������","TimePeriods.gif","ˢ��");</ty:auth>
		<!--<ty:auth auths="401,3 or 401,2">addOneMenu(subMenuList,"attdSyncRecordShow.action","���ڻ�����ͬ��","TimePeriods.gif","ˢ��");</ty:auth>-->
		</s:if>
		<ty:auth auths="411 or 401">addOneMenu(subMenuList,"searchAttenddaily.action","ÿ�տ��ڼ�¼","Schedulers.gif","ÿ�տ���");</ty:auth>
		<ty:auth auths="401 or 411,3 or 411,2">addOneMenu(subMenuList,"searchAttendmonthly.action","ÿ�¿��ڻ��� ","Calendar.gif","ÿ�»���");</ty:auth>
		<ty:auth auths="401,3 or 401,2">addOneMenu(subMenuList,"examinReport.action","���ڱ��� ","AttdReports.gif","����");</ty:auth>
		<ty:auth auths="401,3 or 401,2">addOneMenu(subMenuList,"workFlowApprove.action","��������","Newsletters.gif");</ty:auth>
		<ty:auth auths="401,3 or 401,2">addOneMenu(subMenuList,"leavebalanceManage.action","��������","Newsletters.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"����","examin",subMenuList,null,"icon3.gif");

		//��ѵ
		subMenuList=new Array();
		<ty:auth auths="311">addOneMenu(subMenuList,"myTrainingList.action","�ҵ���ѵ�ƻ�","MyProject.gif");</ty:auth>
		<ty:auth auths="311">addOneMenu(subMenuList,"trepAddInit.action","��ѵ����","CreateProject.gif");</ty:auth>
		<ty:auth auths="311,2 or 311,3">addOneMenu(subMenuList,"trepDeptApprove.action","���ž�������","DeptApprove.gif");</ty:auth>
		<ty:auth auths="301,2">addOneMenu(subMenuList,"trepHrApprove.action","HR����","HRApprove.gif");</ty:auth>
		<ty:auth auths="311,2 or 311,3 or 301">addOneMenu(subMenuList,"trepList.action","Ա����ѵ�ƻ�","Reports.gif");</ty:auth>
		<ty:auth auths="301">addOneMenu(subMenuList,"trReport.action","��ѵ����","CallReports.gif","����");</ty:auth>
		<ty:auth auths="301">addOneMenu(subMenuList,"trcourseConfig.action","�γ�����","Project2Weeks.gif");</ty:auth>
		<ty:auth auths="301">addOneMenu(subMenuList,"trtConfig.action","��ѵ����","Administration.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"��ѵ","training",subMenuList,null,"icon4.gif");
		
		//��Ƹ
		subMenuList=new Array();
		<ty:auth auths="611">addOneMenu(subMenuList,"../recruitment/SearchRecruitplan.action","�ҵ���Ƹ�ƻ�","MyProject.gif");</ty:auth>
		<ty:auth auths="611">addOneMenu(subMenuList,"../recruitment/AddRecruitplanInit.action","������Ƹ�ƻ�","CreateProject.gif");</ty:auth>
		<ty:auth auths="611,2 or 611,3">addOneMenu(subMenuList,"../recruitment/ApproverRecruitplanDept.action","���ž�������","DeptApprove.gif");</ty:auth>
		<ty:auth auths="601,2">addOneMenu(subMenuList,"../recruitment/ApproverRecruitplanHR.action","HR����","HRApprove.gif");</ty:auth>
		<ty:auth auths="611,2 or 611,3 or 601">addOneMenu(subMenuList,"../recruitment/SearchRecruitplanForHR.action","��Ƹ�ƻ��б�","Reports.gif");</ty:auth>
		<ty:auth auths="601">addOneMenu(subMenuList,"../recruitment/recruitapplierSearch.action","ӦƸ�˲ſ�","Meetings.gif");</ty:auth>
		<ty:auth auths="601">addOneMenu(subMenuList,"../recruitment/recruitmentReport.action","��Ƹ����","Preview.gif");</ty:auth>
		<ty:auth auths="601">addOneMenu(subMenuList,"../recruitment/recruitchannellist.action","��Ƹ�����б�","Rebuild.gif","��Ƹ����");</ty:auth>
		addOneMenuWithSubMenu(menuList,"��Ƹ","recruitment",subMenuList,null,"icon5.gif",null,"icon5.gif");
		
		//����
		subMenuList=new Array();
		<ty:auth auths="101">addOneMenu(subMenuList,"empReport.action","Ա������","MyReports.gif");</ty:auth>
		<ty:auth auths="201,2">addOneMenu(subMenuList,"salaryRpInit.action","н�ʱ���","CompaReport.gif");</ty:auth>
		<ty:auth auths="401">addOneMenu(subMenuList,"empReport.action","���ڱ���","AttdReports.gif");</ty:auth>
		<ty:auth auths="301">addOneMenu(subMenuList,"empReport.action","��ѵ����","CallReports.gif");</ty:auth>
		<ty:auth auths="601">addOneMenu(subMenuList,"empReport.action","��Ƹ����","Preview.gif");</ty:auth>
		<ty:auth auths="701">addOneMenu(subMenuList,"listAll.action?report.reportType=0","�Զ��屨��","ReportMaker.gif");</ty:auth>
		<ty:auth auths="701">addOneMenu(subMenuList,"empReport.action","Ԥ���屨��","BirtReports.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"����","report",subMenuList,null,"report.gif");
		
		//��Ϣ����
		subMenuList=new Array();
		<ty:auth auths="802 or 801">addOneMenu(subMenuList,"searchInfo.action","��˾��Ϣ","SugarPortal.gif");</ty:auth>
		<ty:auth auths="801">addOneMenu(subMenuList,"createInfoInit.action","������˾��Ϣ","CreateContacts.gif");</ty:auth>
		<ty:auth auths="801">addOneMenu(subMenuList,"emailsend.action","�ʼ�֪ͨ","EmailSend.gif");</ty:auth>
		<ty:auth auths="801">addOneMenu(subMenuList,"NewsInfo.action","��˾��Ϣ����","Rebuild.gif");</ty:auth>
		addOneMenu(subMenuList,"passwordOwn.action","�޸�����","edit_inline.gif");
		addOneMenuWithSubMenu(menuList,"��Ϣ����","information",subMenuList,null,"info.gif");
		
		//ϵͳ
		subMenuList=new Array();
		<ty:auth auths="911">addOneMenu(subMenuList,"userList.action","�û�����","CreateUsers.gif","�û�");</ty:auth>
		<ty:auth auths="921">addOneMenu(subMenuList,"getRoleList.action","��ɫ����","CreateRoles.gif","��ɫ");</ty:auth>
		<ty:auth auths="963">addOneMenu(subMenuList,"backupResume.action","�����뻹ԭ","Backups.gif","���ݻ�ԭ");</ty:auth>
		<ty:auth auths="941">addOneMenu(subMenuList,"logscan.action","��־����","ConfigureTabs.gif","��־");</ty:auth>
		<ty:auth auths="962">addOneMenu(subMenuList,"dataclean.action","��������","DataSets.gif");</ty:auth>
		<ty:auth auths="951">addOneMenu(subMenuList,"emailSearch.action","ϵͳ�ʼ�����","EmailSend.gif","�ʼ�");</ty:auth>
		<ty:auth auths="952">addOneMenu(subMenuList,"config.action","�����������","Rebuild.gif","�������");</ty:auth>
		<ty:auth auths="961">addOneMenu(subMenuList,"systemconfiginit.action","ϵͳ��������","Administration.gif","ϵͳ����");</ty:auth>
		<ty:auth auths="702">addOneMenu(subMenuList,"iodefList.action","���ݽӿ�����","MigrateFields.gif","���ݽӿ�");</ty:auth>
		<ty:auth auths="931">addOneMenu(subMenuList,"clientInit.action","��˾ע����Ϣ","License.gif");</ty:auth>
		addOneMenuWithSubMenu(menuList,"ϵͳ","configuration",subMenuList,null,"icon6.gif");
		return menuList;
	}	

	//ͨ����ǰurl�õ��˵��б�Ķ�Ӧ����
	function getCurrTopMenuIndex(menuList){
		for(var i=0;i<menuList.length;i++){
			if(document.location.pathname.indexOf("/"+menuList[i]["dir"]+"/")>-1){
				return i;
			}
		}
		return -1;
	}
	//��ʼ��ҳ�涥���˵�html
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
	//����ѡ�е�һ���˵������Ƕ�Ӧ�Ķ����˵�
	function showSubMenu(menuEle,selectIndex){
		//����cookie�ж��Ƿ���ʾ�����˵�
		if(hrm.common.getCookie('showLeftCol')=="true"){
			return;
		}
		//�õ������˵�
		var subMenuArr=hrmAllMenu[selectIndex]["subMenu"];
		//�趨�����˵�html
		var menuArr=new Array();
		for(var i=0;i<subMenuArr.length;i++){
			menuArr[i]='<a href="'+subMenuArr[i]["url"]+'">'+subMenuArr[i]["descSim"]+'</a>';
		}
		//�������˵�����д��div
		var divEle=model.simple.getElement("submenu");
		divEle.style.width="";
		divEle.innerHTML="<table id='subMenuTable' align='left'><tr><td style='white-space:nowrap'>"+menuArr.join("</td><td>")+"</td></tr></table>";
		//�趨�����˵�λ��
		var tableEle=model.simple.getElement("subMenuTable");
		//Ϊ������������ʱԤ��λ��
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
				<a href="http://www.tengsource.com/contact.htm" target="_blank">��ϵ����</a>
				| <a href="../help/searchHelp.action" target="_blank">����</a>
				| <a href="../system/about.jsp" target="_blank">�汾��Ϣ</a> &nbsp;
			</span>
			<input type="text" name="topSearchKey" id="topSearchKey" onkeyup="topFindEmpNoOrName()"/>
			<input name="Submit" type="button" value="����" onclick='topSearchEmp()'>
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
			��ӭ����<s:property value="#session['empName']"/>
			|<a href="../configuration/logout.action"><span class="visitsA">�˳�ϵͳ</span></a>
		</td>
		<td align="right">
			<div id="submenu" style="z-index:10" class="secondmenu"></div>
		</td>
	</tr>
</table>
<script language="javascript">
//��ʼ��һ�������˵�
var hrmAllMenu=getHrmAllMenu();
//��ʼ����ǰ�˵�����
var currMenuIndex=getCurrTopMenuIndex(hrmAllMenu);
initTopMenu(hrmAllMenu,currMenuIndex);
</script>
<!-- end top -->
