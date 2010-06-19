<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<!-- 员工经理查询 -->
<div id="dlgChooseManagerDiv" style="width:400px">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center">快速查询：
			  	<input type="hidden" name="moduleNo" id="moduleNo">  
			  	<input type="hidden" name="searchAll" id="searchAll">  
				<input type="text" id="searchCondition" name="searchCondition" onkeydown="search_emp_div_checkKeyCode()" />
				<input type="button" id="btn_searchCondition" onclick="search_emp_div_searchManager()" value=" 查询 " />
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="1" cellpadding="0" class="prompt_div_body">
					<tr>
						<td>编号</td>
						<td>姓名</td>
						<td>部门</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<div id="content" align="left" style="overflow: auto; border: 1px #dddddd solid; height: 150px; background-color: #ECF3F6;">
					<table width="100%" border="0pt" cellspacing="0" cellpadding="0">
						<tbody id="items">
		
						</tbody>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">

/*
 * 弹出ajax员工选择框（编号、姓名、部门）
 * @author sjinxin 2010-02-07 
 * @param module =1 员工； =2 薪资； =4 考勤
 * @param search =0 只抓离职员工； =1 只抓在职员工； = null 抓所有员工
 */
showChooseEmpDiv=function(module, search){
	$('#moduleNo').val(module);
	$('#searchAll').val(search);
	search_emp_div_searchManager();
	$('#dlgChooseManagerDiv').dialog("option","modal",false);
	hrm.common.openDialog('dlgChooseManagerDiv');
}

// 采用get返回，searchCondition被JS转换后，以UTF-8格式返回到后台，需要继续转码成"ISO-8859-1";
search_emp_div_searchManager=function(){
	var module = $('#moduleNo').val();
	var search = $('#searchAll').val();
	if(hrm.common.isNull(module)) return; // 参数异常

	search_emp_div_createRequest();
	
	var url = "../other/ajaxEmpList.action?searchConditon=" + encodeURIComponent($('#searchCondition').val())
		 	+ "&moduleNo=" + module + "&searchAll=" + search;
	url = encodeURI(url); // 二次encode URL，在getRequest时会自动以UTF-8格式解开，用作encodeURIComponent的补充
	xmlHttpRequest.open("GET",url,true);
	xmlHttpRequest.onreadystatechange = search_emp_div_handleManagerList;
	xmlHttpRequest.send(null);
}

search_emp_div_createRequest=function(){
	if(window.ActiveXObject){
		xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	}
	else{
		xmlHttpRequest = new XMLHttpRequest();
	}
}
//将经理列表填充到div
search_emp_div_handleManagerList=function(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			search_emp_div_clearList();
			search_emp_div_fillManagerList();
		}
	}
}
//将查询结果填充到列表中
search_emp_div_fillManagerList=function(){
	// get the xml document trived from the server
	var dom = xmlHttpRequest.responseXML;
	
	var managers = dom.getElementsByTagName("manager");
	for(var i = 0; i < managers.length; i++){
		var currentManager = managers[i];
		var id = currentManager.getElementsByTagName("id")[0].firstChild.data;
		var managerName = currentManager.getElementsByTagName("name")[0].firstChild.data;
		var managerDepartment = currentManager.getElementsByTagName("department")[0].firstChild.data;
		var managerId = currentManager.getElementsByTagName("managerId")[0].firstChild.data;
		//alert(managerId);
		
		var row = document.createElement("tr");
		row.onmouseover = function(){this.bgColor='#DDF0F8';this.style.cursor="hand";};
		row.onmouseout = function(){this.bgColor='#ECF3F6';this.style.cursor="pointer";};
		row.setAttribute("id",managerId);
		row.onclick = function(){search_emp_div_setManager(this)};
		
		// id column
		var col = document.createElement("td");
		col.setAttribute("class", "tablefield");
		col.setAttribute("nowrap", "nowrap");
		col.appendChild(document.createTextNode(id));
		row.appendChild(col);
		
		// name column
		col = document.createElement("td");
		col.setAttribute("class", "tablefield");
		col.setAttribute("nowrap", "nowrap");
		col.appendChild(document.createTextNode(managerName));
		row.appendChild(col);
		
		// department column
		col = document.createElement("td");
		col.setAttribute("class", "tablefield");
		col.setAttribute("nowrap", "nowrap");
		col.appendChild(document.createTextNode(managerDepartment));
		row.appendChild(col);
		
		// add the row to the table
		document.getElementById('items').appendChild(row);
	}
}
//显示某行的经理
search_emp_div_setManager=function(row){
	//var id = row.childNodes[0].firstChild.data;
	var managerId = row.attributes["id"].value;
	var name = row.childNodes[1].firstChild.data;
	if(document.getElementById("empManagerId")){
		document.getElementById("empManagerId").value= managerId;
	}
	if(document.getElementById("permissionId")){
		document.getElementById("permissionId").value= managerId;
	}
	if(document.getElementById('managerId')){
		document.getElementById('managerId').value = managerId;
	}
	if(document.getElementById('empId')){
		document.getElementById('empId').value = managerId;
	}
	if(document.getElementById("empManagerName")){
		document.getElementById("empManagerName").value = name;
	}
	if(document.getElementById("permission")){
		document.getElementById("permission").value = name;
	}
	if(document.getElementById("empName")){
		document.getElementById("empName").value = name;
	}
	if(document.getElementById("orgheadsEmpNo")){
		document.getElementById("orgheadsEmpNo").value= managerId;
	}
	if(document.getElementById("upd_empManagerId")){
		document.getElementById("upd_empManagerId").value = managerId;
	}
    if(document.getElementById("upd_empName")){
    	document.getElementById("upd_empName").value = name;
	}
	hrm.common.closeDialog("dlgChooseManagerDiv");
}
//清除列表中的所有经理
search_emp_div_clearList=function(){
	var size =document.getElementById("items").childNodes.length;
	for(var i = size - 1; i >= 0; i--){
		document.getElementById("items").removeChild(document.getElementById("items").childNodes[i]);
	}
}
//按下回车时提交
search_emp_div_checkKeyCode=function(){
    if(window.event.keyCode==13){
    	search_emp_div_searchManager();
    }
}
hrm.common.initDialog('dlgChooseManagerDiv',400);
</script>