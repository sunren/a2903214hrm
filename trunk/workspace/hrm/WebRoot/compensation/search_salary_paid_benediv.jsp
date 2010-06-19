<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ty" uri="/tengsource"%>

    <!---benefit div begin jet miao, 2009-06-25 -->
	<div id="dlg_emp_benefit_div" title="" class="prompt_div_inline" style="width:600;display:none;">

		<div id="emp_benefit_div_header">
			<input id="div_empid" type="hidden" name="hiddenName" />
			<input id="employeename" type="hidden" name="hiddenName" />
			<input id="div_rowID" type="hidden" name="hiddenName" />
		</div>

		<div id="change_stutus_error" class="prompt_div_err"></div>
		<table id="newBenefitTable" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" id="beneBody"></td>
			</tr>
			<tr>
				<td align="center" colspan="12">
					<input type="button" name="hiddenName"
						onclick="javascript:$('#dlg_emp_benefit_div').dialog('close');" value="关闭">
				</td>
			</tr>
		</table>
	</div>
	<!-- -benefit div end -->
	
<script type="text/javascript" language="javascript">
   //显示社保缴纳和补缴记录；
var beneItemLength = 0;
function showBeneModifyDiv(index){
    document.getElementById("beneBody").innerHTML = "";
	var widthDiv = document.getElementById("espBenefitPlans"+index).value;
	widthDiv = widthDiv * 80;
	widthDiv = widthDiv + 160;
    var empname=document.getElementById('empName'+index).value;
    $('#dlg_emp_benefit_div').dialog("option","width",widthDiv);
    var payId = document.getElementById("salarypayID"+index).value;
	DWRforSalaryPaid.getBeneItemsByPay(payId, callBack);
	function callBack(data) {
	    if(data.length == 0){
	        return;
	    }
	    var content = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
	    var align="";
	    for(var i=0; i<data.length; i++){
	        content += "<tr>";
	        for(var j=0; j<data[i].length; j++){
	            align = i==0||j==0?"align='center'":"align='right'";
	            content += "<td nowrap='nowrap' "+align+">" + data[i][j] + "</td>";
	        }
	        content += "</tr>";
	    }
	    content += "</table>";
	    document.getElementById("beneBody").innerHTML = content;
	    //alert("widthDiv="+widthDiv);
		$('#dlg_emp_benefit_div').dialog("option", "title", empname+"的社保缴费信息");
	
		HRMCommon.openDialog('dlg_emp_benefit_div');
	}
}
	
</script>
