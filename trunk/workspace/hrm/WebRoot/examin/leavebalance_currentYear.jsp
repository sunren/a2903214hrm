<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
	<head>
		<s:head/>
		<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />	
		<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
		<script type='text/javascript' src='../dwr/interface/LeavebalanceCurrentYear.js'></script>
	    <script type="text/javascript" src="../dwr/interface/LeaveBalanceDWR.js"></script>  
		<title>员工休假管理</title>
	</head>
	<body onload="cacheObjectList();hrm.common.check_order();">
		<jsp:include flush="true" page="div_updateBalance.jsp"></jsp:include>
		<br/>
		<s:component template="bodyhead">
		</s:component>
		<form id="actionSrc" name="actionSrc" action="leavebalanceCurYear.action"  method="POST">
			<!--隐藏信息-->
		 	<input id="operate" type="hidden" name="page.operate"/>
		 	<s:hidden id="updateIDs" name="updateIDs" value="" />
		 	<s:hidden id="lbType" name="lbType" value="%{leaveBalance.lbLeaveType}" />
		 	<s:hidden id="specialIds" name="specialIds" />
		 	<s:hidden id="order" name="page.order" />
			<table width="100%" border="0" cellspacing="2" cellpadding="0" class="formtable">
				<tr>
					<td align="right">员工:</td>
					<td>
					<s:textfield id="lbs_Bean.emp" name="leaveBalance.lbEmpNo.empNoName" size="16" maxlength="64"></s:textfield>
					</td>
					<td align="right">休假类型:</td>
					<td>
					<s:select id="leaveBalance.lbLeaveType" name="leaveBalance.lbLeaveType" onchange="document.getElementById('actionSrc').submit()" list="#{1:'年假',5:'带薪病假',10:'调休(不过期)'}" emptyOption="false"></s:select>
					</td>
					<td align="right">休假状态:</td>
					<td>
					<s:select id="lbs_Bean.status" name="leaveBalance.lbStatus" list="#{0:'请选择',1:'初始化',2:'允许请假',9:'禁止请假'}"></s:select>
					</td>
					<td align="right">地区:</td>
					<td>
					<s:select id="location" name="leaveBalance.lbEmpNo.empLocationNo.id" list="locationList" listKey="id" listValue="locationName" emptyOption="true"></s:select>
					</td>
					<td colspan="2" rowspan="2" align="center">				
						<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="查询">
						<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="submit" onclick="window.location='leavebalanceManage.action';" value="重置">
					</td>
				</tr>
				<tr>
					<td align="right">组织单元:</td>
					<td>
					    <s:orgselector id="orgselector1" name="leaveBalance.lbEmpNo.empDeptNo.departmentName" hiddenFieldName="leaveBalance.lbEmpNo.empDeptNo.id"/>
					</td>
					<td align="right">查询条件:</td>
					<td>
					<s:select name="searchType" list="#{'1':'已设置假期','0':'未配置假期'}"></s:select>
					</td>
					<td align="right">按天/小时:</td>
					<td>
					<s:select name="leaveType" list="#{0:'按天',1:'按小时'}"></s:select>
					</td>
				</tr>
			</table>
			
			<table width="100%">
			  <tr><td></td> </tr>
			  <tr>
				<td align="left">
					<input type="button" class="button" id="down" name="down" value="上一年" onclick="previewYear();" >
					<s:textfield id="year" name="leaveBalance.lbYear" size="7" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>
					<input type="button" class="button" id="up" name="up" value="下一年" onclick="nextYear();">
					<input id="initSelect" name="initSelect" class="button" type="button" value="假期初始化" onclick="hrm.common.openDialog('dlgVacationInitial');">
					<input id="submitLeaveBalance" name="submitLeaveBalance" class="button" type="button" value=" 提交 " onclick="doSubmitLeaveBalnace();">
					<input id="deleteLeaveBalance" name="deleteLeaveBalance" class="button" type="button" value=" 删除 " onclick="doDeleteLeaveBalance()">
				</td>
				<td align="right">本次查询共得到<s:property value="page.totalRows" />条休假记录&nbsp;</td>
			  </tr>
			</table>
			
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
				<tr>
					<th>
						<input id="changIds" name='changIds' class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('changIds','changIds');setInitStatus('changIds')" value="选中草稿">
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('leaveBalance.lbEmpNo.empDistinctNo','actionSrc');">员工编号<img src='../resource/images/arrow_.gif' width='8' height='10'
							id='leaveBalance.lbEmpNo.empDistinctNo_img'></a>
					</th>
					<th>
						<a href="#">员工姓名</a>
					</th>
					<th>
						<a href="#">种类</a>
					</th>
					<s:if test="leaveBalance.lbLeaveType == 1 || leaveBalance.lbLeaveType == 10">					
						<th>
							<a href="#">上年余额</a>
						</th>
						<th>
							<a href="#">余额到期日</a>
						</th>
					</s:if>					
					<th>
						<a href="#">本年额度</a>
					</th>
					<s:if test="leaveBalance.lbLeaveType == 1 || leaveBalance.lbLeaveType == 5">					
						<th>
							<a href="#">释放额度</a>
						</th>
						<th>
							<a href="#">释放起始日</a>
						</th>
					</s:if>
					<s:if test="leaveBalance.lbLeaveType == 1">
						<th>
							<a href="#">法定(天)</a>
						</th>
					</s:if>
					<th>
						<a href="#">备注</a>
					</th>
					<th>状态</th>
					<th>
						<a href="#">操作</a>
					</th>
				</tr>
				<s:if test="result!=null&&!result.isEmpty()" >
					<s:iterator value="result" status="index" >
						<tr id="row_<s:property value='leaveBalance.lbId'/>">
							<td align="center">
								<s:if test="leaveBalance!=null">
									<input id="changIds" type="checkbox" name="changIds" class="checkbox" value="<s:property value='leaveBalance.lbId'/>" onclick="setInitStatus('changIds');"/>
								</s:if>
							</td>
							<td align="center">
								<strong><a href="#" onclick="showEmpLtInfo('<s:property value='id'/>');"><s:property value="empDistinctNo"/></a>
								</strong>
							</td>
							<td>
								<s:property value='empName'/>
							</td>
							<td>
								<s:if test="leaveBalance != null">
									<s:if test="leaveBalance.lbLeaveType == 1">年假</s:if>
									<s:elseif test="leaveBalance.lbLeaveType == 5">带薪病假</s:elseif>
									<s:elseif test="leaveBalance.lbLeaveType == 10">调休(不过期)</s:elseif>
								</s:if>
							</td>
							<s:if test="leaveBalance.lbLeaveType == 1 || leaveBalance.lbLeaveType == 10">					
								<td>
									<s:property value="leaveBalance.lbBalForwardDay"/>
								</td>
								<td>
									<s:date name="leaveBalance.lbBalEndDate" format="yyyy-MM-dd"/>
								</td>
							</s:if>
							<td>
								<s:property value="leaveBalance.lbDaysOfYear"/>
							</td>
							<s:if test="leaveBalance.lbLeaveType == 1 || leaveBalance.lbLeaveType == 5">					
								<td>
									<s:property value="leaveBalance.lbDaysForRelease"/>
								</td>
								<td>
									<s:date name="leaveBalance.lbReleaseStartDate" format="yyyy-MM-dd"/>
								</td>
							</s:if>
							<s:if test="leaveBalance.lbLeaveType == 1">
								<td>
									<s:property value="leaveBalance.lbOtherDays"/>
								</td>
							</s:if>
							<td>
								<s:property value="leaveBalance.lbComments"/>
							</td>
							<td>
								<s:if test="leaveBalance.lbStatus==1">初始化</s:if>
								<s:elseif test="leaveBalance.lbStatus==2">允许请假</s:elseif>
								<s:elseif test="leaveBalance.lbStatus==9">禁止请假</s:elseif>
							</td>
							<td>
								<s:if test="leaveBalance!=null">  <!--以下两个方法在Div_updateBalance.jsp中-->
									<a href="#" onclick="showUpdateBalanceDiv('<s:property value='leaveBalance.lbId'/>')"><img src="../resource/images/edit.gif" alt='调整' border='0' /></a>
									<a href="#" onclick="deleteBalance('<s:property value='leaveBalance.lbId'/>')" >
										<img src="../resource/images/delete.gif" alt='清除' border='0' />
									</a>
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td align="center" colspan="11">
							<s:if test="leaveBalance != null">
								<s:if test="leaveBalance.lbLeaveType == 1">不存在符合条件的年假信息！</s:if>
								<s:elseif test="leaveBalance.lbLeaveType ==5">不存在符合条件的带薪病假信息！</s:elseif>
								<s:elseif test="leaveBalance.lbLeaveType ==10">不存在符合条件的调休假信息！</s:elseif>
							</s:if>
						</td>
					</tr>
				</s:else>
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gridtableList">
	  			<tr class="listViewPaginationTdS1">
	 			  <td colspan="10" align="center">
	 			    <s:hidden id="page.currentPage" name="page.currentPage" />
	 			    <s:component template="pager">
	 			        <s:param name="pageNo" value="page.currentPage" /><s:param name="totalPages" value="page.totalPages" /><s:param name="totalRows" value="page.totalRows" />
	          	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'actionSrc'" />
	          	  	</s:component>
				  </td>
	  			</tr>
			</table>
		</form>
		
<!-- 选择员工弹出层  点击页面假期初始化按钮弹出Dialog--> 
<div id="dlgVacationInitial" style="width:450px;display:none;" title="员工假期初始化">
<table width="100%"  border="0" cellspacing="1" cellpadding="5">
  <tr>
    <td width="75px" align="right">选择员工：</td>
    <td>
        <div class="btnlink">
            <a href="#" onclick="selectClass('all')">所有人</a>
            <a href="#" onclick="selectClass('dep')">按部门</a>
            <a href="#" onclick="selectClass('local')">按地区</a>
        </div>
    </td>
  </tr>   
</table>
<!-- 按分类选择人员 -->
<table width="450px"  border="0" cellspacing="0" cellpadding="0" class="formtable" id="classTable" style="display:none;margin-left:0px;">  
  <tr>
    <td>
    <!-- 部门列表 -->
    <div id="depList" style="display:none">    
        <s:iterator value="deptList" status="index1">
	        <span style='width:100px;height:25px;'>
	            <input type="checkbox" class ="checkbox" id="depRadio<s:property value='id'/>" name="depRadio<s:property value="id"/>" 
                    value="<s:property value="id"/>"
                    onclick="selectSubClass('dep',this)" >
                    <s:property value="departmentName"/>
	        </span>
        </s:iterator>  
    </div>
    <!-- 地区列表 -->
    <div id="localList" style="display:none">
        <s:iterator value="locationList" status="index3">
	        <span style='width:100px;height:25px;'>
	            <input type="checkbox" class ="checkbox" id="localRadio<s:property value='id'/>" name="localRadio<s:property value="id"/>" 
                    value="<s:property value="id"/>"
                    onclick="selectSubClass('local',this)" >
                    &nbsp;<s:property value="locationName"/>
	         </span>
        </s:iterator> 
    </div>  
    </td>
  </tr>
</table>
<br>
<table>
    <tr>
      <td>
        <input id="emp_sear_value" name="emp_sear_value" size="20" maxlength="50"/>
        <input type="button" id="searButton" name="searButton" value="查询" onkeydown="enterDown(event);" onclick="searchEmp(document.getElementById('emp_sear_value').value);"/>
      </td>
    </tr>
    <tr>
        <td>
            <s:optiontransferselect
             name="leftList"
             leftTitle="选择员工："
             rightTitle="已选中的员工："
             list="empList"
             multiple="true"
             listKey="id"
             listValue="%{empName+' '+ empDistinctNo}"
             addToLeftLabel="向左移动"
             addToRightLabel="向右移动"
             cssStyle="width: 200px; height: 150px;"
             emptyOption="true"
             addAllToRightLabel=">>"
             addAllToLeftLabel="<<"
             addToLeftLabel=" <"
             addToRightLabel=" >"
             allowAddAllToLeft="true"
             allowSelectAll="false"
             allowUpDownOnLeft="false"
             allowUpDownOnRight="false"
             ondblclick="moveSelectedOptions(document.getElementById('leftList'), document.getElementById('rightList'), false, '');"
             emptyOption="false"
             doubleList="{}"
             doubleName="rightList"
             doubleEmptyOption="false"
             doubleMultiple="true"
             doubleCssStyle="width: 200px; height: 150px;"
             doubleOndblclick="moveSelectedOptions(document.getElementById('rightList'), document.getElementById('leftList'), false, '');"
            />
        </td>
    </tr>
    <tr>
    	<td>
    		休假类型:&nbsp;<input type="checkbox" class="checkbox" id="annual"/>年假&nbsp;
    		<input type="checkbox" class="checkbox" id="sick"/>带薪病假&nbsp;
    		<input type="checkbox" class="checkbox" id="tiaoxiunoexp">调休(不过期)&nbsp;
    	</td>
    </tr>
    <tr>
        <td align="center">
            <input id="btnInit" type="button" value="初始化" onclick="doInit();">
			<input id="btnClose" type="button" value="关闭" onclick="hrm.common.closeDialog('dlgVacationInitial');"/>
        </td>
    </tr>
</table>
</div>

<!-- 请假种类汇总弹出层 -->
<div id="dlgEmpLtInfo" style="width:560px;display:none;height:auto;">
	<table id="listTable" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
	    <thead>
	        <th>编号</th>
	        <th>开始时间</th>
	        <th>结束时间</th>
	        <th>合计时间</th>
	        <th>状态</th>
	    </thead>
	    <tbody id="listBody">
	        
	    </tbody>
	</table>
	<table id="valueTable" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
	    <tbody id="valueBody">
	    </tbody>
	</table>
	<table id="valueTable" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
       <tr><td align="center"><input id="empLrClose" type="button" value="关闭" onclick="hrm.common.closeDialog('dlgEmpLtInfo');"/></td></tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;" frameborder="0" ></iframe>
</div>
</body>
<script type="text/javascript">
model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度） 
	
//给initSelect赋初始值(假期初始化可见性)
function setInitStatus(var1){
	try{
	     //alert('1setInitStatus');
	    checkBoxName=document.getElementsByName(var1);  //$N(var1);
	                
	    if(checkBoxName.length>1)
		{
			for(var i=1;i<checkBoxName.length;i=i+1)
			{
				if(checkBoxName[i].checked==true){
				$("#initSelect").attr("disabled",false);  //$('#initSelect').disabled=false;
				return;
				}
			}
		}
		}catch(e){
			alert(e);
		}
		$("#initSelect").attr("disabled",true);
}

//下一年记录(点击页面下一年按钮)
function nextYear(){
	submitForm(1);
}

//上一年记录
function previewYear(){
	submitForm(-1);
}

//提交表单(可调用公共方法)
function submitForm(year){
	var date = document.getElementById("year").value;
	document.getElementById("year").value = parseInt(date)+year;
	document.actionSrc.submit();
}
	 
	 
//全局变量，保存所有的员工信息，包含员工id，员工编号，员工姓名，部门，业务单元，地区
var empListArray = new Array();
/************************************
** 将整个员工列表缓存到页面上
*************************************/
function cacheObjectList(){
   <s:iterator value="empList" status="index">
      var emp = new Object();
      emp.id = '<s:property value="id"/>';
      emp.no = '<s:property value="empDistinctNo"/>';
      emp.name = '<s:property value="empName"/>';
      emp.dept = '<s:property value="empDeptNo.id"/>';
      emp.loc = '<s:property value="empLocationNo.id"/>';
      empListArray.push(emp);
   </s:iterator> 
}
    
/************************************
** 查询员工列表，可以分别按照姓名，员工号，部门模糊查询
** 输入：员工姓名/编号
** 返回：匹配的员工列表
*************************************/
function searchEmp(value){
    if(value.trim() == ''){
        return;
    }
    var leftSelect = document.getElementById('leftList');
    clearSelect(leftSelect);
    var size = empListArray.length;
    for(var i = 0; i < size; i++){
        var emp = empListArray[i];
        if(emp.no.lastIndexOf(value) != -1 || emp.name.lastIndexOf(value) != -1){
            addOneToLeft(leftSelect, emp.name+" " +emp.no, emp.id);
        }
    }
}

/************************************
 ** 分地区/部门/业务单元显示员工
 *************************************/
function selectClass(className){
    clearCheckbox();
    if(className=="" || className=="all"){
        document.getElementById('classTable').style.display="none";
    }
    document.getElementById('classTable').style.display="block";
    document.getElementById('depList').style.display="none";
    document.getElementById('localList').style.display="none";
    
    if(className=='dep'){
        document.getElementById('depList').style.display="block";
        clearSelect(document.getElementById('leftList'));
    }else if(className=='local'){   
        document.getElementById('localList').style.display="block";
        clearSelect(document.getElementById('leftList'));
    }else{
        document.getElementById('classTable').style.display="none";
        selectSubClass('all',null);
    }
}
   
/************************************
** 向左编列表中添加一个选项
*************************************/
function addOneToLeft(obj, text, value){
    //判断在左，右边列表中是否已经存在；不存在，才添加；
    if(!oneExist(document.getElementById('rightList'), value) && !oneExist(document.getElementById('leftList'), value)){
        obj.options[obj.length] = new Option(text, value);
    }
}
    
/************************************
** 从下拉列表中删除一个选项
*************************************/
function deleteOneFromSelect(obj, value){
    for(var i=0; obj!=null && i<obj.length; i++){
        if(obj.options[i].value == value){
            obj.options[i] = null;
            i--;
            break;
        }
    }
    return;
}
    
/************************************
** 判断一个选项在列表中是否已经存在
** 返回：true/false
*************************************/
function oneExist(obj, value){
    for(var i=0; obj!=null&&i<obj.length; i++){
        if(obj.options[i].value == value){
            return true;
        }
    }
    return false;
}
    
/************************************
** 将本类选中的员工添加到左边的下拉列表中
*************************************/
function addCurrentSelectedEmp(classVar){
    var objArr = document.getElementsByTagName("input");
    var objId = "";
    var flag = false;
    for(var i=0; objArr!=null&&i<objArr.length; i++){
        if(objArr[i].checked){
            getEmpListCheckbox(classVar,objArr[i].value);
            flag = true;
        }
    }
}
    
/************************************
** 将当前选中的所有员工添加到左边下拉列表中
*************************************/
function addCurrentSelectedEmp(){
    var classVar = "";
    var objArr = document.getElementsByTagName("input");
    var objId = "";
    var flag = false;
    for(var i=0; objArr!=null&&i<objArr.length; i++){
        objId = objArr[i].id;
        if(objId.indexOf("depRadio") == 0){
            classVar = "dep";
        }
        if(objId.indexOf("localRadio")==0){
            classVar = "local";
        }
        if(objArr[i].checked){
            getEmpListCheckbox(classVar,objArr[i].value);
            flag = true;
        }
    }
    if(!flag){
        selectSubClass("all", null);
    }
}

/**清除列表框中的项；*/
function clearSelect(obj){
    for(var i=0; obj!=null&&i<obj.length; i++){
        obj.options[i] = null;
        i--;
    }
}

/**选下一个class前清楚所有class下面的复选框*/
function clearCheckbox(){
    var obj=classTable.getElementsByTagName("input");
    for(var i=0;i<obj.length;i++){
        var objid = obj[i].id;
        if((objid.indexOf("depRadio")==0 || objid.indexOf("localRadio")==0) && obj[i].checked){
            obj[i].checked = false;
        }
    }
}

//所有,地区,部门
function selectSubClass(classVar,obj){
    //alert('selectSubClass');
    if(classVar=='all'){
        var leftSelect = document.getElementById('leftList');
        var size = empListArray.length;
        for(var i = 0; i < size; i++){
            var emp = empListArray[i];
            addOneToLeft(leftSelect, emp.name+" " + emp.no, emp.id);
        }
    }else{
        if(obj.checked==true){
            getEmpListCheckbox(classVar,obj.value);
            addCurrentSelectedEmp(classVar);
        }else if(obj.checked==false){
            dropEmpListCheckbox(classVar,obj.value);
        }
    }
}

/**将所选员工添加到左边待选列表中；*/
function getEmpListCheckbox(classVar,classValue){
    var empclass="";
    var emplist="";
    if(classVar==null || classVar.trim().length==0)return;
    var leftSelect = document.getElementById('leftList');
    var size = empListArray.length;
    if(classVar=='dep'){
        for(var i = 0; i < size; i++){
            var emp = empListArray[i];
            if(emp.dept == classValue){
                addOneToLeft(leftSelect, emp.name+" " +emp.no, emp.id);
            }
        }   
    }else if(classVar=='local'){
        for(var i = 0; i < size; i++){
            var emp = empListArray[i];
            if(emp.loc == classValue){
                addOneToLeft(leftSelect, emp.name+" " +emp.no, emp.id);
            }
        }
    }
}

/**从左边列表中删除待选员工；*/
function dropEmpListCheckbox(classVar,classValue){
    var empclass="";
    var emplist="";
    if(classVar==null || classVar.trim().length==0)return;
    var leftSelect = document.getElementById('leftList');
    var size = empListArray.length;
    if(classVar=='dep'){
        for(var i = 0; i < size; i++){
            var emp = empListArray[i];
            if(emp.dept == classValue){
                deleteOneFromSelect(leftSelect,emp.id);
            }
        }       
    }else if(classVar=='local'){
        for(var i = 0; i < size; i++){
            var emp = empListArray[i];
            if(emp.loc == classValue){
                deleteOneFromSelect(leftSelect,emp.id);
            }
        }
    }
}

//onkeydown按下键盘触发
function enterDown(event){
    event = event ? event : (window.event ? window.event : null);
    if(event!=null && event.keyCode==13){
        searchEmp(document.getElementById('emp_sear_value').value);  
        return;                              
    }
}
    
//初始化Dialog(点击初始化按钮)    
function doInit(){
	var select = document.getElementById('rightList');
    var len = select.options.length;
    if(len ==0){
    	alert("请至少选择一个员工！");
        return;
    }
    var types = "";
    if(document.getElementById('annual').checked){
 		types += "1,"
    }
    if(document.getElementById('sick').checked){
   		types += "5,"
    }
    if(document.getElementById('tiaoxiunoexp').checked){
   		types += "10,"
    }
    if(types.length ==0){
    	alert("请至少选择一个休假类型！");
        return;
    }
    var year = document.getElementById('year').value;
    if(!confirm("确定要初始化"+year+"年的休假吗?")){
   		return;
    }
    //document.getElementById('dlgVacationInitial').style.display='none';
    hrm.common.openDialog('dlgVacationInitial');
    var ids = new Array();
    for(var i = 0; i < select.options.length; i++){
      	ids += select.options[i].value;
      	if(i != select.options.length-1){
			ids+=",";
      	}
    }
  	document.getElementById("actionSrc").action='initLeaveBalance.action';
  	document.getElementById("updateIDs").value=ids;
  	document.getElementById("specialIds").value = types.substring(0,types.length-1);
  	document.getElementById("actionSrc").submit();
}

//获取选中的员工(以钩的)
function getCheckBoxIds(){
	var result = "";
	var ids = document.getElementsByName("changIds");
	for(var i = 0; i < ids.length; i++){
		if(ids[i].checked){
			var value = ids[i].value;
			if(value == null || value.length == 0){
				continue;
			}
			result += value + ",";
		}
	}
	return result == ""?"":result.substring(0,result.length-1);
}

//提交(点击提交按钮)   
function doSubmitLeaveBalnace(){
	var ids = getCheckBoxIds();
	if(ids.length ==0){
		alert("请选择要提交的记录!");
		return;
	}
	if(!confirm('确定要进行提交操作吗？')){
		return;
    }
	document.getElementById("actionSrc").action='doSubmitLeaveBalance.action';
    document.getElementById("updateIDs").value=ids;
    //document.getElementById("specialIds").value = types.substring(0,types.length-1);
    document.getElementById("actionSrc").submit();
}

//删除(点击删除按钮) 
function doDeleteLeaveBalance(){
    var ids = getCheckBoxIds();
	if(ids.length ==0){
		alert("请选择要删除的记录!");
		return;
	}
	if(!confirm('确定要进行删除操作吗？')){
		return;
    }
	document.getElementById("actionSrc").action='doDeleteLeaveBalance.action';
    document.getElementById("updateIDs").value=ids;
    //document.getElementById("specialIds").value = types.substring(0,types.length-1);
    document.getElementById("actionSrc").submit();
}
        
// 显示员工请假详细信息； prototype-->jquery实现
function showEmpLtInfo(empId){
    // 清除上次显示的行；
    DWRUtil.removeAllRows("listBody");
    DWRUtil.removeAllRows("valueBody");
    
    var ltSpecialCat = document.getElementById("leaveBalance.lbLeaveType").value;
    var year = document.getElementById("year").value;
    var dayOrHour = document.getElementById("leaveType").value;
    LeavebalanceCurrentYear.getEmpLtInfo(year, empId, ltSpecialCat, dayOrHour, showInfoCallback);
    // 回调函数；
    function showInfoCallback(data){
        if(data.length > 1){// 有请假数据，显示在列表中；
            for(var i=0; i<data.length-1; i++){
                var lr = data[i];
                var lrNo = lr.lrNo;
                var lrStartDate, lrEndDate, totalTime, lrStatus;
                if(dayOrHour==0){// 按天显示；
                	lrStartDate = lr.lrStartDate.toHRMDateString("yyyy-MM-dd") + " " + getAM_PMDesc(lr.lrStartApm);
            	    lrEndDate = lr.lrEndDate.toHRMDateString("yyyy-MM-dd") + " " + getAM_PMDesc(lr.lrEndApm); //format-->toHRMDateString(common.js中)
                }else{// 按小时显示；
                	lrStartDate = lr.lrStartDate.toFormatString("yyyy-MM-dd hh:mm");
                	lrEndDate = lr.lrEndDate.toFormatString("yyyy-MM-dd hh:mm"); //format-->toFormatString
                }
                if(dayOrHour==0){
                    totalTime = lr.lrTotalDays+"天";
                }else{
                    totalTiime = lr.lrTotalHours+"小时";
                }
                lrStatus = lr.lrStatusMean;

                // 添加行；
                var row = document.createElement("tr");
        		var cellFuncs = [
        		    function(item) { return lrNo; },
        		    function(item) { return lrStartDate; },
        		    function(item) { return lrEndDate; },
        		    function(item) { return totalTime; },
        		    function(item) { return lrStatus; },
        		];
                DWRUtil.addRows("listBody", [''], cellFuncs, {
    			  rowCreator:function(options) {
    				var row = document.createElement("tr"); 
    				return row;
    			    }
    		    });
            }
        }

        // 显示汇总值行；
        var valuesArr = data[data.length-1];
        var typeStr = valuesArr[3]==0?"天":"小时";
        var row = document.createElement("tr");
		var cellFuncs = [
		    function(item) { return "总数"; },
		    function(item) { return valuesArr[0]+typeStr; },
		    function(item) { return "已用"; },
		    function(item) { return valuesArr[1]+typeStr; },
		    function(item) { return "可用"; },
		    function(item) { return valuesArr[2]+typeStr },
		];
        DWRUtil.addRows("valueBody", [''], cellFuncs, {
		  rowCreator:function(options) {
			var row = document.createElement("tr"); 
			return row;
		    }
	    });
        //document.getElementById('dlgEmpLtInfo').style.display="block";
        hrm.common.openDialog('dlgEmpLtInfo');
    }
}

  
//上下午判断
function getAM_PMDesc(AM_PM){
    if(AM_PM==null || AM_PM==0){ 
        return "上午";
    }
    return "下午";
}

hrm.common.initDialog('dlgVacationInitial');
hrm.common.initDialog('dlgEmpLtInfo');
</script>
</html>