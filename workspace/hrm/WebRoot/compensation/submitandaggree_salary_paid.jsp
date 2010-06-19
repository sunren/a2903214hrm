<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<!--
 =========================================================
' File:search_salary_paid.jsp
' Version:1.1.0 standard version
' Date: 2007-2-2
' Script Written by hr.com
'=========================================================
' Copyright   2007 hr.com. All rights reserved.
' Web: http://www.hr.com
' Email: admin@hr.com
'=======================================================
 -->
<head>
<title>н�ʷ����б�</title>

<!-- DWR Start -->
    <script type='text/javascript' src='../dwr/interface/my.js'></script>
	<script type='text/javascript' src="../dwr/interface/DWRforSalaryPaid.js"></script>
<!-- DWR end -->

</head>
<body>

<s:component template="bodyhead">
	<s:param name="pagetitle" value="espdStatus==0?'н�ʷ����ύ�б�('+year+'-'+month+')':'н�ʷ�������б�('+year+'-'+month+')'" />
	<s:param name="helpUrl" value="'26'" />
</s:component>

<s:form id="ViewAllSalarypaid" name="ViewAllSalarypaid" action="viewAllSalarypaid" namespace="/compensation" method="POST">
	<s:hidden id="year" name="year"/>
	<s:hidden id="month" name="month"/>
	<s:hidden id="order" name="page.order"/>
	<s:hidden id="espdStatus" name="espdStatus" value='<s:property value="espdStatus"/>'/>
	<s:token></s:token>
<table id="salary_paid_table" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
 	<s:if test="!salaryPaidConvergeList.isEmpty()">
 		<s:if test="espdStatus==0">
			<input class="button" id="btnsubmit" type=button value="�����ύ" onclick="confirm();">
			<input class="button" id="btnback" type=button value=" ���� " onclick="back()">
		</s:if>
		<s:if test="espdStatus==1">
			<input class="button" id="btnapprove" type=button value=" ���� " onclick="approve(2);">
			<input class="button" id="btnrevoke" type=button value="�˻�����" onclick="approve(0);">
			<input class="button" id="btnback" type=button value=" ���� " onclick="back()">
		</s:if>
	</s:if> 
		<thead>
		<tr>
	      
	     	<th nowrap>
	     		<a href="#" onclick="sortAble('salary_paid_table', 0, 'Chinese','th1');">����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='th1'>
	     	</th>
	     	
	     	<th nowrap>
	     		<a href="#" onclick="sortAble('salary_paid_table', 1, 'float','th2');">����ĩ����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='th2'>
	     	</th>
	     	<th nowrap>
	     		<a href="#" onclick="sortAble('salary_paid_table', 2, 'float','th3');">����ĩ����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='th3'>
	     	</th>
	     	<th nowrap>
	     		<a href="#" onclick="sortAble('salary_paid_table', 3, 'float','th4');">��ְ����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='th4'>
	     	</th>
	     	<th nowrap>
	     		<a href="#" onclick="sortAble('salary_paid_table', 4, 'float','th5');">��ְ����</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='th5'>
	     	</th>
	     
	     	<th nowrap>
	     		<a href="#" onclick="sortAble('salary_paid_table', 5, 'float','th6');">����н��(˰ǰ)</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='th6'>
	     	</th>
	     	<th nowrap>
	     		<a href="#" onclick="sortAble('salary_paid_table', 6, 'float','th7');">����н��(˰ǰ)</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='th7'>
	     	</th>
	     	<th nowrap>
	     		<a href="#" onclick="sortAble('salary_paid_table', 7, 'float','th8');">����(˰ǰ)</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='th8'>
	     	</th>
	     </tr>
		</thead>
	     <s:if test="!salaryPaidConvergeList.isEmpty()">
	     	<tbody>
	     	<tr style="display:none"></tr>
	     	<s:iterator value="salaryPaidConvergeList" status="index">
	     		<s:if test="!#index.last">
	     		<tr>
					<td align="right" style="{font: small-caps 10pt Arial}" class="basictable">&nbsp;<s:property value="empDepartment"/></td> 
					<td align="right" style="{font: small-caps 10pt Arial}" class="basictable">&nbsp;<s:property value="empNumber"/></td> 
					<td align="right" style="{font: small-caps 10pt Arial}" class="basictable">&nbsp;<s:property value="lastMonthEmpNumber"/></td>
					<td align="right" style="{font: small-caps 10pt Arial}" class="basictable">&nbsp;<s:property value="quitEmpNumber"/></td>  
					<td align="right" style="{font: small-caps 10pt Arial}" class="basictable">&nbsp;<s:property value="joinEmpNumber"/></td> 
					<td align="right" style="{font: small-caps 10pt Arial}" class="basictable">&nbsp;<s:property value="salaryAmount"/></td>
					<td align="right" style="{font: small-caps 10pt Arial}" class="basictable">&nbsp;<s:property value="lastMonthSalaryAmount"/></td> 
					<td align="right" style="{font: small-caps 10pt Arial}" class="basictable">&nbsp;<s:property value="salaryDifference"/></td>  
				</tr>
				</s:if>
				<s:else>
					<td align="right" style="{font: small-caps bold 10pt Arial}" class="basictable">&nbsp;<s:property value="empDepartment"/></td> 
					<td align="right" style="{font: small-caps bold 10pt Arial}" class="basictable">&nbsp;<s:property value="empNumber"/></td> 
					<td align="right" style="{font: small-caps bold 10pt Arial}" class="basictable">&nbsp;<s:property value="lastMonthEmpNumber"/></td>
					<td align="right" style="{font: small-caps bold 10pt Arial}" class="basictable">&nbsp;<s:property value="quitEmpNumber"/></td>  
					<td align="right" style="{font: small-caps bold 10pt Arial}" class="basictable">&nbsp;<s:property value="joinEmpNumber"/></td> 
					<td align="right" style="{font: small-caps bold 10pt Arial}" class="basictable">&nbsp;<s:property value="salaryAmount"/></td>
					<td align="right" style="{font: small-caps bold 10pt Arial}" class="basictable">&nbsp;<s:property value="lastMonthSalaryAmount"/></td> 
					<td align="right" style="{font: small-caps bold 10pt Arial}" class="basictable">&nbsp;<s:property value="salaryDifference"/></td>  
					</tr>
				</s:else>
	     	</s:iterator>
	     	
	     
	     </s:if>
		<s:else>
			<tr><!-- ������Ա����н��Ϣ -->
					<td align="center" colspan="15"><s:property value="year"/>��<s:property value="month"/>�²�����Ա��н�ʷ��Ųݸ�!</td>
			</tr>
		</s:else>
		</tbody>
</table>

</s:form>

<script type="text/javascript" language="javascript">
var asc = false; 
function ieOrFireFox(ob){      
     if (ob.textContent != null)      
     return ob.textContent;      
     var s = ob.innerText;      
     return s.substring(0, s.length);      
} 
//���� tableId: ���id,iCol:�ڼ��� ��dataType��iCol��Ӧ������ʾ���ݵ���������      
function sortAble(tableId, iCol, dataType, imgId){  
    var table = document.getElementById(tableId);      
    var tbody = table.tBodies[0];      
    var colRows = tbody.rows;      
    var aTrs = new Array;
    var img = document.getElementById(imgId);
    
    if(table.sortCol == iCol){
    	if(asc){
    		img.src = '../resource/images/arrow_down.gif';
    		asc = false;
    	}else{
    		img.src = '../resource/images/arrow_up.gif' ;
    		asc = true;
    	}
    }else{
    	for(var i=0;i<10;i=i+1){
    		if(document.getElementById("th"+i)!=null){
    			document.getElementById("th"+i).src = '../resource/images/arrow_.gif' ;
    		}
    		
    	}
    	img.src = '../resource/images/arrow_up.gif' ;
    	asc = true;
    }
             
    //�����õ����з������飬����      
    for (var i=0; i < colRows.length-1; i=i+1) {      
         aTrs[i] = colRows[i+1];      
    }      
                                                  
 //�ж���һ�����е��к�������Ҫ���е��Ƿ�ͬһ����      
    if (table.sortCol == iCol) {      
        aTrs.reverse();      
    } else {      
     //�������ͬһ�У�ʹ�������sort����������������      
        aTrs.sort(compareEle(iCol, dataType));   
		//a.sort(function(a,b){return a.localeCompare(b)});   
    }               
    var oFragment = document.createDocumentFragment();      
                     
    for (var i=0; i < aTrs.length; i++) {      
        oFragment.appendChild(aTrs[i]);      
    }                      
    tbody.appendChild(oFragment);      
     //��¼���һ�������������      
    table.sortCol = iCol;      
 }
  //��������iCol��ʾ��������dataType��ʾ���е���������      
function compareEle(iCol, dataType) { 
	if(dataType=="Chinese"){
		 return  function (oTR1, oTR2) {      
	         var vValue1 = ieOrFireFox(oTR1.cells[iCol]);      
	         var vValue2 =ieOrFireFox(oTR2.cells[iCol]);      
	         return vValue1.localeCompare(vValue2);   
	        };      
	}else{     
	    return  function (oTR1, oTR2) {      
	         var vValue1 = convert(ieOrFireFox(oTR1.cells[iCol]), dataType);      
	         var vValue2 = convert(ieOrFireFox(oTR2.cells[iCol]), dataType);      
	         if (vValue1 < vValue2) {      
	             return -1;      
	         } else if (vValue1 > vValue2) {      
	             return 1;      
	         } else {      
	             return 0;      
	         }      
	        };      
     }
 }
  //���е�����ת������Ӧ�Ŀ������е���������      
function convert(sValue, dataType) {      
	if(sValue=="" || sValue==null || sValue.trim()=="") sValue = "-0.1";
     switch(dataType) {      
     case "int":      
         return parseInt(sValue);      
     case "float":      
         return parseFloat(sValue);      
     case "date":      
         return new Date(Date.parse(sValue));      
     default:      
         return sValue.toString();      
     }      
 }      
function confirm()
{	
	document.forms[0].btnsubmit.disabled = true;
	document.forms[0].btnback.disabled = true;
	document.forms[0].action='confirmSubmit.action';
	document.forms[0].submit();
}
function approve(status)
{	
	document.forms[0].btnapprove.disabled = true;
	document.forms[0].btnrevoke.disabled = true;
	var no=status;
	document.forms[0].action='approve.action?status='+no;
	document.forms[0].submit();
}


</script>

</body>	