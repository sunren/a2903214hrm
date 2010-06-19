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
		<title>Ա���ݼٹ���</title>
	</head>
	<body onload="cacheObjectList();hrm.common.check_order();">
		<jsp:include flush="true" page="div_updateBalance.jsp"></jsp:include>
		<br/>
		<s:component template="bodyhead">
		</s:component>
		<form id="actionSrc" name="actionSrc" action="leavebalanceCurYear.action"  method="POST">
			<!--������Ϣ-->
		 	<input id="operate" type="hidden" name="page.operate"/>
		 	<s:hidden id="updateIDs" name="updateIDs" value="" />
		 	<s:hidden id="lbType" name="lbType" value="%{leaveBalance.lbLeaveType}" />
		 	<s:hidden id="specialIds" name="specialIds" />
		 	<s:hidden id="order" name="page.order" />
			<table width="100%" border="0" cellspacing="2" cellpadding="0" class="formtable">
				<tr>
					<td align="right">Ա��:</td>
					<td>
					<s:textfield id="lbs_Bean.emp" name="leaveBalance.lbEmpNo.empNoName" size="16" maxlength="64"></s:textfield>
					</td>
					<td align="right">�ݼ�����:</td>
					<td>
					<s:select id="leaveBalance.lbLeaveType" name="leaveBalance.lbLeaveType" onchange="document.getElementById('actionSrc').submit()" list="#{1:'���',5:'��н����',10:'����(������)'}" emptyOption="false"></s:select>
					</td>
					<td align="right">�ݼ�״̬:</td>
					<td>
					<s:select id="lbs_Bean.status" name="leaveBalance.lbStatus" list="#{0:'��ѡ��',1:'��ʼ��',2:'�������',9:'��ֹ���'}"></s:select>
					</td>
					<td align="right">����:</td>
					<td>
					<s:select id="location" name="leaveBalance.lbEmpNo.empLocationNo.id" list="locationList" listKey="id" listValue="locationName" emptyOption="true"></s:select>
					</td>
					<td colspan="2" rowspan="2" align="center">				
						<input title="[Alt+F]" accesskey="F" name="submit_search" id="submit_search" class="button" type="submit" value="��ѯ">
						<input title="[Alt+A]" accesskey="A" name="submit_all" id="submit_all" class="button" type="submit" onclick="window.location='leavebalanceManage.action';" value="����">
					</td>
				</tr>
				<tr>
					<td align="right">��֯��Ԫ:</td>
					<td>
					    <s:orgselector id="orgselector1" name="leaveBalance.lbEmpNo.empDeptNo.departmentName" hiddenFieldName="leaveBalance.lbEmpNo.empDeptNo.id"/>
					</td>
					<td align="right">��ѯ����:</td>
					<td>
					<s:select name="searchType" list="#{'1':'�����ü���','0':'δ���ü���'}"></s:select>
					</td>
					<td align="right">����/Сʱ:</td>
					<td>
					<s:select name="leaveType" list="#{0:'����',1:'��Сʱ'}"></s:select>
					</td>
				</tr>
			</table>
			
			<table width="100%">
			  <tr><td></td> </tr>
			  <tr>
				<td align="left">
					<input type="button" class="button" id="down" name="down" value="��һ��" onclick="previewYear();" >
					<s:textfield id="year" name="leaveBalance.lbYear" size="7" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>
					<input type="button" class="button" id="up" name="up" value="��һ��" onclick="nextYear();">
					<input id="initSelect" name="initSelect" class="button" type="button" value="���ڳ�ʼ��" onclick="hrm.common.openDialog('dlgVacationInitial');">
					<input id="submitLeaveBalance" name="submitLeaveBalance" class="button" type="button" value=" �ύ " onclick="doSubmitLeaveBalnace();">
					<input id="deleteLeaveBalance" name="deleteLeaveBalance" class="button" type="button" value=" ɾ�� " onclick="doDeleteLeaveBalance()">
				</td>
				<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />���ݼټ�¼&nbsp;</td>
			  </tr>
			</table>
			
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
				<tr>
					<th>
						<input id="changIds" name='changIds' class="checkbox" type="checkbox" onclick="hrm.common.checkAllByName('changIds','changIds');setInitStatus('changIds')" value="ѡ�вݸ�">
					</th>
					<th>
						<a href="#" onclick="hrm.common.order_submit('leaveBalance.lbEmpNo.empDistinctNo','actionSrc');">Ա�����<img src='../resource/images/arrow_.gif' width='8' height='10'
							id='leaveBalance.lbEmpNo.empDistinctNo_img'></a>
					</th>
					<th>
						<a href="#">Ա������</a>
					</th>
					<th>
						<a href="#">����</a>
					</th>
					<s:if test="leaveBalance.lbLeaveType == 1 || leaveBalance.lbLeaveType == 10">					
						<th>
							<a href="#">�������</a>
						</th>
						<th>
							<a href="#">������</a>
						</th>
					</s:if>					
					<th>
						<a href="#">������</a>
					</th>
					<s:if test="leaveBalance.lbLeaveType == 1 || leaveBalance.lbLeaveType == 5">					
						<th>
							<a href="#">�ͷŶ��</a>
						</th>
						<th>
							<a href="#">�ͷ���ʼ��</a>
						</th>
					</s:if>
					<s:if test="leaveBalance.lbLeaveType == 1">
						<th>
							<a href="#">����(��)</a>
						</th>
					</s:if>
					<th>
						<a href="#">��ע</a>
					</th>
					<th>״̬</th>
					<th>
						<a href="#">����</a>
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
									<s:if test="leaveBalance.lbLeaveType == 1">���</s:if>
									<s:elseif test="leaveBalance.lbLeaveType == 5">��н����</s:elseif>
									<s:elseif test="leaveBalance.lbLeaveType == 10">����(������)</s:elseif>
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
								<s:if test="leaveBalance.lbStatus==1">��ʼ��</s:if>
								<s:elseif test="leaveBalance.lbStatus==2">�������</s:elseif>
								<s:elseif test="leaveBalance.lbStatus==9">��ֹ���</s:elseif>
							</td>
							<td>
								<s:if test="leaveBalance!=null">  <!--��������������Div_updateBalance.jsp��-->
									<a href="#" onclick="showUpdateBalanceDiv('<s:property value='leaveBalance.lbId'/>')"><img src="../resource/images/edit.gif" alt='����' border='0' /></a>
									<a href="#" onclick="deleteBalance('<s:property value='leaveBalance.lbId'/>')" >
										<img src="../resource/images/delete.gif" alt='���' border='0' />
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
								<s:if test="leaveBalance.lbLeaveType == 1">�����ڷ��������������Ϣ��</s:if>
								<s:elseif test="leaveBalance.lbLeaveType ==5">�����ڷ��������Ĵ�н������Ϣ��</s:elseif>
								<s:elseif test="leaveBalance.lbLeaveType ==10">�����ڷ��������ĵ��ݼ���Ϣ��</s:elseif>
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
		
<!-- ѡ��Ա��������  ���ҳ����ڳ�ʼ����ť����Dialog--> 
<div id="dlgVacationInitial" style="width:450px;display:none;" title="Ա�����ڳ�ʼ��">
<table width="100%"  border="0" cellspacing="1" cellpadding="5">
  <tr>
    <td width="75px" align="right">ѡ��Ա����</td>
    <td>
        <div class="btnlink">
            <a href="#" onclick="selectClass('all')">������</a>
            <a href="#" onclick="selectClass('dep')">������</a>
            <a href="#" onclick="selectClass('local')">������</a>
        </div>
    </td>
  </tr>   
</table>
<!-- ������ѡ����Ա -->
<table width="450px"  border="0" cellspacing="0" cellpadding="0" class="formtable" id="classTable" style="display:none;margin-left:0px;">  
  <tr>
    <td>
    <!-- �����б� -->
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
    <!-- �����б� -->
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
        <input type="button" id="searButton" name="searButton" value="��ѯ" onkeydown="enterDown(event);" onclick="searchEmp(document.getElementById('emp_sear_value').value);"/>
      </td>
    </tr>
    <tr>
        <td>
            <s:optiontransferselect
             name="leftList"
             leftTitle="ѡ��Ա����"
             rightTitle="��ѡ�е�Ա����"
             list="empList"
             multiple="true"
             listKey="id"
             listValue="%{empName+' '+ empDistinctNo}"
             addToLeftLabel="�����ƶ�"
             addToRightLabel="�����ƶ�"
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
    		�ݼ�����:&nbsp;<input type="checkbox" class="checkbox" id="annual"/>���&nbsp;
    		<input type="checkbox" class="checkbox" id="sick"/>��н����&nbsp;
    		<input type="checkbox" class="checkbox" id="tiaoxiunoexp">����(������)&nbsp;
    	</td>
    </tr>
    <tr>
        <td align="center">
            <input id="btnInit" type="button" value="��ʼ��" onclick="doInit();">
			<input id="btnClose" type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgVacationInitial');"/>
        </td>
    </tr>
</table>
</div>

<!-- ���������ܵ����� -->
<div id="dlgEmpLtInfo" style="width:560px;display:none;height:auto;">
	<table id="listTable" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
	    <thead>
	        <th>���</th>
	        <th>��ʼʱ��</th>
	        <th>����ʱ��</th>
	        <th>�ϼ�ʱ��</th>
	        <th>״̬</th>
	    </thead>
	    <tbody id="listBody">
	        
	    </tbody>
	</table>
	<table id="valueTable" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
	    <tbody id="valueBody">
	    </tbody>
	</table>
	<table id="valueTable" cellSpacing="0" cellPadding="0" width="100%" class="gridview">
       <tr><td align="center"><input id="empLrClose" type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgEmpLtInfo');"/></td></tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;" frameborder="0" ></iframe>
</div>
</body>
<script type="text/javascript">
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ� 
	
//��initSelect����ʼֵ(���ڳ�ʼ���ɼ���)
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

//��һ���¼(���ҳ����һ�갴ť)
function nextYear(){
	submitForm(1);
}

//��һ���¼
function previewYear(){
	submitForm(-1);
}

//�ύ��(�ɵ��ù�������)
function submitForm(year){
	var date = document.getElementById("year").value;
	document.getElementById("year").value = parseInt(date)+year;
	document.actionSrc.submit();
}
	 
	 
//ȫ�ֱ������������е�Ա����Ϣ������Ա��id��Ա����ţ�Ա�����������ţ�ҵ��Ԫ������
var empListArray = new Array();
/************************************
** ������Ա���б��浽ҳ����
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
** ��ѯԱ���б����Էֱ���������Ա���ţ�����ģ����ѯ
** ���룺Ա������/���
** ���أ�ƥ���Ա���б�
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
 ** �ֵ���/����/ҵ��Ԫ��ʾԱ��
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
** ������б������һ��ѡ��
*************************************/
function addOneToLeft(obj, text, value){
    //�ж������ұ��б����Ƿ��Ѿ����ڣ������ڣ�����ӣ�
    if(!oneExist(document.getElementById('rightList'), value) && !oneExist(document.getElementById('leftList'), value)){
        obj.options[obj.length] = new Option(text, value);
    }
}
    
/************************************
** �������б���ɾ��һ��ѡ��
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
** �ж�һ��ѡ�����б����Ƿ��Ѿ�����
** ���أ�true/false
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
** ������ѡ�е�Ա����ӵ���ߵ������б���
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
** ����ǰѡ�е�����Ա����ӵ���������б���
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

/**����б���е��*/
function clearSelect(obj){
    for(var i=0; obj!=null&&i<obj.length; i++){
        obj.options[i] = null;
        i--;
    }
}

/**ѡ��һ��classǰ�������class����ĸ�ѡ��*/
function clearCheckbox(){
    var obj=classTable.getElementsByTagName("input");
    for(var i=0;i<obj.length;i++){
        var objid = obj[i].id;
        if((objid.indexOf("depRadio")==0 || objid.indexOf("localRadio")==0) && obj[i].checked){
            obj[i].checked = false;
        }
    }
}

//����,����,����
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

/**����ѡԱ����ӵ���ߴ�ѡ�б��У�*/
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

/**������б���ɾ����ѡԱ����*/
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

//onkeydown���¼��̴���
function enterDown(event){
    event = event ? event : (window.event ? window.event : null);
    if(event!=null && event.keyCode==13){
        searchEmp(document.getElementById('emp_sear_value').value);  
        return;                              
    }
}
    
//��ʼ��Dialog(�����ʼ����ť)    
function doInit(){
	var select = document.getElementById('rightList');
    var len = select.options.length;
    if(len ==0){
    	alert("������ѡ��һ��Ա����");
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
    	alert("������ѡ��һ���ݼ����ͣ�");
        return;
    }
    var year = document.getElementById('year').value;
    if(!confirm("ȷ��Ҫ��ʼ��"+year+"����ݼ���?")){
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

//��ȡѡ�е�Ա��(�Թ���)
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

//�ύ(����ύ��ť)   
function doSubmitLeaveBalnace(){
	var ids = getCheckBoxIds();
	if(ids.length ==0){
		alert("��ѡ��Ҫ�ύ�ļ�¼!");
		return;
	}
	if(!confirm('ȷ��Ҫ�����ύ������')){
		return;
    }
	document.getElementById("actionSrc").action='doSubmitLeaveBalance.action';
    document.getElementById("updateIDs").value=ids;
    //document.getElementById("specialIds").value = types.substring(0,types.length-1);
    document.getElementById("actionSrc").submit();
}

//ɾ��(���ɾ����ť) 
function doDeleteLeaveBalance(){
    var ids = getCheckBoxIds();
	if(ids.length ==0){
		alert("��ѡ��Ҫɾ���ļ�¼!");
		return;
	}
	if(!confirm('ȷ��Ҫ����ɾ��������')){
		return;
    }
	document.getElementById("actionSrc").action='doDeleteLeaveBalance.action';
    document.getElementById("updateIDs").value=ids;
    //document.getElementById("specialIds").value = types.substring(0,types.length-1);
    document.getElementById("actionSrc").submit();
}
        
// ��ʾԱ�������ϸ��Ϣ�� prototype-->jqueryʵ��
function showEmpLtInfo(empId){
    // ����ϴ���ʾ���У�
    DWRUtil.removeAllRows("listBody");
    DWRUtil.removeAllRows("valueBody");
    
    var ltSpecialCat = document.getElementById("leaveBalance.lbLeaveType").value;
    var year = document.getElementById("year").value;
    var dayOrHour = document.getElementById("leaveType").value;
    LeavebalanceCurrentYear.getEmpLtInfo(year, empId, ltSpecialCat, dayOrHour, showInfoCallback);
    // �ص�������
    function showInfoCallback(data){
        if(data.length > 1){// ��������ݣ���ʾ���б��У�
            for(var i=0; i<data.length-1; i++){
                var lr = data[i];
                var lrNo = lr.lrNo;
                var lrStartDate, lrEndDate, totalTime, lrStatus;
                if(dayOrHour==0){// ������ʾ��
                	lrStartDate = lr.lrStartDate.toHRMDateString("yyyy-MM-dd") + " " + getAM_PMDesc(lr.lrStartApm);
            	    lrEndDate = lr.lrEndDate.toHRMDateString("yyyy-MM-dd") + " " + getAM_PMDesc(lr.lrEndApm); //format-->toHRMDateString(common.js��)
                }else{// ��Сʱ��ʾ��
                	lrStartDate = lr.lrStartDate.toFormatString("yyyy-MM-dd hh:mm");
                	lrEndDate = lr.lrEndDate.toFormatString("yyyy-MM-dd hh:mm"); //format-->toFormatString
                }
                if(dayOrHour==0){
                    totalTime = lr.lrTotalDays+"��";
                }else{
                    totalTiime = lr.lrTotalHours+"Сʱ";
                }
                lrStatus = lr.lrStatusMean;

                // ����У�
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

        // ��ʾ����ֵ�У�
        var valuesArr = data[data.length-1];
        var typeStr = valuesArr[3]==0?"��":"Сʱ";
        var row = document.createElement("tr");
		var cellFuncs = [
		    function(item) { return "����"; },
		    function(item) { return valuesArr[0]+typeStr; },
		    function(item) { return "����"; },
		    function(item) { return valuesArr[1]+typeStr; },
		    function(item) { return "����"; },
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

  
//�������ж�
function getAM_PMDesc(AM_PM){
    if(AM_PM==null || AM_PM==0){ 
        return "����";
    }
    return "����";
}

hrm.common.initDialog('dlgVacationInitial');
hrm.common.initDialog('dlgEmpLtInfo');
</script>
</html>