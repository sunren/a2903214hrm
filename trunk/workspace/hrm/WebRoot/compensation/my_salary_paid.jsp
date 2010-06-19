<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>

<head>
<title>�ҵ�н�ʷ����б�</title>
<script type="text/javascript" src="../resource/js/hrm/compensation.js"></script>
<script type='text/javascript' src='../dwr/interface/DWRforSalaryPaid.js'></script>
<script type='text/javascript' src='../dwr/interface/MySalaryPaid.js'></script>
</head>
<body>
<jsp:include flush="true" page="search_salary_paid_benediv.jsp"></jsp:include>
<span class="errorMessage" id="message"></span>

<s:component template="bodyhead">
	<s:param name="pagetitle"  value="empName+'��н�ʷ����б�(��'+startYear+'-'+startMonth+'��'+endYear+'-'+endMonth+')'" />
	<s:param name="helpUrl" value="'26'" />
</s:component>

<s:form id="mySalaryPaid" name="mySalaryPaid" action="mySalaryPaid" namespace="/compensation" method="POST">
<s:token></s:token>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable"> 
		<tr>
		    <input type="hidden" id="operate" name="page.operate"/>
			<s:hidden id="order" name="page.order"/>
			<s:hidden id="empId" name="empId"/>
			<td>
			    <label class="label" for="empName">Ա��:</label>
				<input type="text" readonly="true" id="empName" name="empName" value="<s:property value='empName'/>" />
				<ty:auth auths="201 or 211,3 or 211,2"> 
				    <img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick="showChooseEmpDiv(2,1);" alt='���ͼ��ѡ��Ա��'/>
				</ty:auth>
			</td>
			<td>
				<label class="label" for="empName">��ʼ����:</label>
				<s:select id="inputStartYear" name="startYear" list="years"  emptyOption="false"/>
				<s:select id="inputStartMonth" name="startMonth" list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}"  emptyOption="false"/>
			</td>
			<td>
				<label class="label" for="empName">��������:</label>
			    <s:select id="inputEndYear"  name="endYear" list="years"  emptyOption="false"/>
				<s:select id="inputEndMonth" name="endMonth" list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}"  emptyOption="false"/>
			</td>
			<td>
				<input title="[Alt+F]" accesskey="F" name="sub_button" id="submit_button" type="button" size="4" class="button" onclick="checkYearAndMonth()" value="��ѯ">
				<input title="[Alt+C]" accesskey="C" name="clear_button" class="button" type="button" onClick="window.location='mySalaryPaid.action';" value="����">
			</td>
		</tr>
	</table>

	<table width="100%">
	  <tr>
	    <br/>
	  </tr>
	  <tr>
		<td align="left">
			<ty:auth auths="201">
			    <input type="hidden" name="searchOrExport" id="searchOrExport" />
			    <input class="button" type="button" value="ƽ�����ʼ���" name="import" onclick="showAndCompAvgPay();" />
			    <input class="button" type="button" value="���ݵ���" id="btnOutput" onclick="HRMCommon.export_check('','','exportSalary');"/>
			</ty:auth>
		</td>
		<td align="right">���β�ѯ���õ�<s:property value="page.totalRows" />��н����ʷ��¼&nbsp;</td>
	  </tr>
	</table>
		
   	<table id="salary_paid_table" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
      <tr>
	     <th nowrap>
	    	<a href="#" onclick="hrm.common.order_submit('espYearmonth');">
	     	��-��</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='espYearmonth_img'>
	     </th>
	     <th nowrap>Ա�����</th>
	     <th nowrap>Ա������</th>
	     <th nowrap colspan="2">��������</th>
	     <th nowrap>˰ǰ������</th>
	     <th nowrap colspan="2">�籣�ܶ�</th>
	     <th nowrap>����˰</th>
	     <th nowrap>˰������</th>
		<ty:auth auths="201">
			<th nowrap>н������</th>
		</ty:auth>
	  </tr>
	  
	  <s:if test="!salaryPaidList.isEmpty()">
	     <s:iterator value="salaryPaidList" status="index">
	    	<tr>
				<%--Ա����Ϣ�������--%>
				<input type="hidden" id="salarypayID<s:property value="#index.count"/>" name="salarypayID<s:property value="#index.count"/>" value="<s:property value="id"/>" />
				<input type="hidden" id="empid<s:property value="#index.count"/>" name="empid<s:property value="#index.count"/>" value="<s:property value="espEmpno.id" />" />
				<input type="hidden" id="empName<s:property value="#index.count"/>" name="empName<s:property value="#index.count"/>" value="<s:property value="espEmpno.empName" />" />
				<input type="hidden" id="espComment<s:property value="#index.count"/>" name="espComment<s:property value="#index.count"/>" value="<s:property value="espComment" />" />
				<input type="hidden" id="espBenefitPlans<s:property value='#index.count'/>" name="espBenefitPlans<s:property value='#index.count'/>" value="<s:property value='espBenefitPlans' />" />
				<ty:auth auths="201 or 211">
					<input type="hidden" id="espEsacName<s:property value="#index.count"/>" name="espEsacName<s:property value="#index.count"/>" value="<s:property value="espEsavId.esavEsac.esacName" />" />	
				</ty:auth>
				<td id="yearAndMonth<s:property value="#index.count"/>" align="center"><s:property value="yearAndMonth" /></td>

				<td id="empDistinctNo<s:property value="#index.count"/>" align="center" nowrap>
					<s:property value='espEmpno.empDistinctNo'/>
				</td>
				<td id="name<s:property value="#index.count"/>" align="center" nowrap>
				    <s:property value='espEmpno.empName'/>
				</td>

			    <td id="empBaseSalaryTD<s:property value="#index.count"/>" align="right" nowrap>
					<span id="Basesalary_span<s:property value="#index.count"/>"><s:property value="showColumn1"/></span>
				</td>
				<td align="left" nowrap>
					<img id="searchImg<s:property value="#index.count"/>" src="../resource/images/Search.gif" alt="�鿴��ϸ" 
						onclick="myshowconfigDiv('<s:property value="#index.count"/>');" border="0" style="CURSOR: pointer" >
				</td> 
				<td id="empFixBuTie<s:property value="#index.count"/>" align="center">
					&nbsp;<s:property value="showColumn8" />
				</td>

				<td id="emp4Money<s:property value="#index.count"/>" align="right">
					&nbsp;
					<s:property value="showColumn15"/>	
				</td>
				<td align="left" nowrap>
						<s:if test="espBenefitPlans>0">
							<img id="benesearchImg<s:property value="#index.count"/>"
								src="../resource/images/Search.gif" alt="�鿴��ϸ"
								onclick="showBeneModifyDiv('<s:property value="#index.count"/>');"
								border="0" style="CURSOR: pointer" />
						</s:if>
				</td>
					
				<td id="empTaxamt<s:property value="#index.count"/>" align="center">
					&nbsp;<s:property value="showColumn18"/>
				</td>  
				<td id="empNetamt<s:property value="#index.count"/>" align="center">
					&nbsp;<s:property value="showColumn19"/>
				</td>
				<ty:auth auths="201">
					<td id="td_esac<s:property value='%{#index.count}'/>"
						align="center">
						<s:property value="espEsavId.esavEsac.esacName" />
					</td>
				</ty:auth>
			</tr>
	     	</s:iterator>
	     </s:if>

		<s:else>
			<tr>
				<td align="center" colspan="17">
					<span id="infomation">
					�޷���������Ա��н�ʷ�����Ϣ! 
				   </span>
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
       	  	    <s:param name="start" value="page.start" /><s:param name="end" value="page.end" /><s:param name="formId" value="'mySalaryPaid'" />
       	  	</s:component>
		  </td>
  		</tr>
	</table>

	<div id="tmpletId" style="DISPLAY: none">
	<img src="../resource/images/basic_search.gif" onload="HRMCommon.check_order();"/>
</s:form>


<!-- -salary pay div begin -->
<div id="dlgEmpSalaryPay" class="prompt_div_inline" style="width:420;display:none;">
           
	<div id="emp_salary_pay_div_header" style="CURSOR: move;">
		<input id="div_empid" type="hidden" name="hiddenName" />
		<input id="employeename" type="hidden" name="hiddenName" />
		<input id="div_rowIDPay" type="hidden" name="hiddenName" />
	</div>

	<div id="change_stutus_error" class="prompt_div_err"></div>
		<table id="newConfigTable" width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="1" align="left" valign="center">
				н�ʷ��ű�ע:</td>
				<td colspan="3" align="left" valign="center">
				  <textarea id="div_espComments" cols="26" rows="2"  maxlength="127" disabled>
				  </textarea>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="9">
					<input type="button" name="hiddenName" onclick="HRMCommon.closeDialog('dlgEmpSalaryPay');" value="�ر�">
				</td>
			</tr>
		</table>

	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;width: 430px; height: 246px; " frameborder="0"></iframe>
	</div>


<!-- -salary pay div end -->

    <!-- ƽ�����ʼ��㿪ʼ -->
	<div id="dlgComputeAvgPay" class="prompt_div_inline" style="width:400;display:none;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<th align="center" width="50%" style="{border-right: 0px solid #555555}"><strong>ʱ�䷶Χ</strong></th>
				<th align="center" width="25%" style="{border-right: 0px solid #555555}"><strong>˰ǰ����</strong></th>
				<th align="center" width="25%" style="{border-right: 0px solid #555555}"><strong>˰������</strong></th>
			</tr>
			<tr>
				<td align="center" nowrap>
					ǰ12��(<span id="12_period"></span>)��
				</td>
				<td>
					<input class='nothinginput' id="12_avg_pay_b" name="12_avg_pay_b" size="10" readonly style="text-align:right"/>
				</td>
				<td>
					<input class='nothinginput' id="12_avg_pay" name="12_avg_pay" size="10" readonly style="text-align:right"/>
				</td>
				</tr>
				<tr>
					<td align="center" nowrap>
						�����(<span id="lastyear_period"></span>)��
					</td>
					<td>
						<input class='nothinginput' id="lastyear_avg_pay_b" name="12_avg_pay_b" size="10" readonly style="text-align:right"/>
					</td>
					<td>
						<input class='nothinginput' id="lastyear_avg_pay" name="lastyear_avg_pay" size="10" readonly style="text-align:right"/>
					</td>
				</tr>
				<tr><td colspan="3">&nbsp;</td></tr>
				<tr>
					<td align="center">
						<table border="0">
							<tr>
								 <td nowrap>
									 ��ʼ���£�
									    <s:select id="computeStartYear" name="computeStartYear" list="years"  emptyOption="false"/>
				                        <s:select id="computeStartMonth" name="computeStartMonth" list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}" emptyOption="false"/>
								</td>
							</tr>
							<tr>
								 <td nowrap>
								         ��ֹ���£�
									   <s:select id="computeEndYear" name="computeEndYear" list="years"  emptyOption="false"/>
				                       <s:select id="computeEndMonth" name="computeEndMonth" list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}" emptyOption="false"/>
								  </td>
							</tr>
						</table>
					</td>
					<td>
							<input class='nothinginput' id="selected_avg_pay_b" name="selected_avg_pay_b" size="10" readonly style="text-align: right"/>
					</td>
					<td>
							<input class='nothinginput' id="selected_avg_pay" name="selected_avg_pay" size="10" readonly style="text-align: right"/>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center">
						<input id="ebfAddbtn" type="button" value="����" onClick="compuGivenAvgPay();" />
						&nbsp;&nbsp;
						<input type="button" value="����" onclick="HRMCommon.closeDialog('dlgComputeAvgPay');" />
					</td>
				</tr>
		</table>
		<iframe scrolling="no"
			style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;width: 399px; height: 209px; "
			frameborder="0"></iframe>
	</div>
	       
    <!-- ƽ�����ʼ������ -->
<script type="text/javascript" language="javascript">
//н�ʷ��Ų�
var acctItemLength;
function myshowconfigDiv(index){
	var payId = document.getElementById("salarypayID"+index).value;
	var empId = document.getElementById("empid"+index).value;
	var empName = document.getElementById("empName"+index).value;
	var acctName = document.getElementById("espEsacName" + index).value;
	var year_month = document.getElementById("yearAndMonth"+index).innerHTML;
	document.getElementById("div_rowID").value = index;
	
	$('#dlgEmpSalaryPay').dialog("option", "title", empName+"��н�ʷ�����Ϣ��"+acctName+"("+year_month+")");	
	document.getElementById("div_espComments").value = document.getElementById("espComment"+index).value.trim();

	for(var i=0;i<acctItemLength;i++) {
		document.getElementById('acctItems'+i).parentNode.removeChild(document.getElementById('acctItems'+i));
	}

	DWRforSalaryPaid.initCalcPay(payId, empId, year_month, callBack);
	function callBack(msg) {
		var trObj;
		acctItemLength = msg.length/2;
		//alert(acctItemLength);
		
		for(var i=0;i<msg.length;i++) {
			if(i%2==0){
				trObj = document.getElementById('newConfigTable').insertRow(i/2);
				trObj.id = 'acctItems'+(i/2);
				trObj.name = 'div_tr';
			}
			var toWrite=toWrite="readOnly class='nothinginput'";
			toWrite = toWrite + " style='text-align:right'";

			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.width='25%';
			tdObj.height='27px';
			tdObj.nowrap='true';
			tdObj.innerHTML="<label for='empsalaryconfig.escColumn"+(i+1)+"'>"+msg[i].esaiEsdd.esddName+":</label>";
			trObj.appendChild(tdObj);
			tdObj = document.createElement('td');
			tdObj.align='left';
			tdObj.height='27px';
			tdObj.width='25%';
			tdObj.innerHTML="<input type='text' "+toWrite+" name='espColumn' id='espColumn"+(i+1) + "' " + "value='" + msg[i].itemConfigValue.toFixed(2) + "'" + " size='9'/>";
			trObj.appendChild(tdObj);
			if(i==msg.length-1&&msg.length%2==1){
				tdObj = document.createElement('td');
				tdObj.width='25%';
				tdObj.height='27px';
				trObj.appendChild(tdObj);
				tdObj = document.createElement('td');
				tdObj.width='25%';
				tdObj.height='27px';
				trObj.appendChild(tdObj);
			  }
		   }
	    }
			
	HRMCommon.openDialog('dlgEmpSalaryPay');
}

//�����ѡ�������Ƿ���ȷ
function checkYearAndMonth()
{
var startMonth=document.getElementById('inputStartMonth').value;
var endMonth=document.getElementById('inputEndMonth').value;
if(startMonth<10)
  startMonth="0"+startMonth;
if(endMonth<10)
  endMonth="0"+endMonth;
var startYearAndMonth=document.getElementById('inputStartYear').value+startMonth;
var endYearAndMonth=document.getElementById('inputEndYear').value+endMonth;
   if(startYearAndMonth>endYearAndMonth)
   {
  alert("��ʼ���´��ڽ������£�������ѡ�����£�");
   return false;
 }
 document.getElementById("mySalaryPaid").submit();
}

// ���㲢��ʾƽ�����ʣ�
function showAndCompAvgPay(){
    var empId = document.getElementById("empId").value;
    MySalaryPaid.defaultComputePay(empId, defaultComputeShow);// ��ʾĬ�ϼ������ݣ�
    document.getElementById("selected_avg_pay_b").value = "";
    document.getElementById("selected_avg_pay").value = "";
    
    // ����Ĭ�ϵ���ֹ���£�
    var date = new Date();
    var year = date.getFullYear();//ie����¶�����
    var month = date.getMonth()+1;
    if (month < 10) month = "0"+month;
    document.getElementById("computeStartYear").value = year;
    document.getElementById("computeStartMonth").value = "01";
    document.getElementById("computeEndYear").value = year;
    document.getElementById("computeEndMonth").value = month;
    
    $('#dlgComputeAvgPay').dialog("option","title","ƽ�����ʼ���(<s:property value='empName'/>)");
    HRMCommon.openDialog('dlgComputeAvgPay');
}
function defaultComputeShow(data){
    var temp1 = data[0];
    var temp2 = data[1];
    document.getElementById("12_avg_pay_b").value = temp1.split(",")[0];
    document.getElementById("12_avg_pay").value = temp1.split(",")[1];
    document.getElementById("lastyear_avg_pay_b").value = temp2.split(",")[0];
    document.getElementById("lastyear_avg_pay").value = temp2.split(",")[1];
    document.getElementById("12_period").innerHTML = data[2];
    document.getElementById("lastyear_period").innerHTML = data[3];
}

// ����������µ�ƽ�����ʣ�
function compuGivenAvgPay(){
    var empId = document.getElementById("empId").value;
    var start = document.getElementById("computeStartYear").value + "" + document.getElementById("computeStartMonth").value;
    var end = document.getElementById("computeEndYear").value + "" + document.getElementById("computeEndMonth").value;
    if(parseInt(start) > parseInt(end)){
        alert("��ʼ����ҪС�ڵ�����ֹ���£�");
        return ;
    }
    MySalaryPaid.givenComputePay(empId, start, end, givenComputeShow);
}
function givenComputeShow(data){
    var dataArr = data.split(",");
    document.getElementById("selected_avg_pay_b").value = dataArr[0];
    document.getElementById("selected_avg_pay").value = dataArr[1];
}

HRMCommon.initDialog('dlg_emp_benefit_div');
HRMCommon.initDialog('dlgComputeAvgPay');  
HRMCommon.initDialog('dlgEmpSalaryPay'); 
</script>
<%@ include file="../profile/search_emp_div.jsp"%>
</body>