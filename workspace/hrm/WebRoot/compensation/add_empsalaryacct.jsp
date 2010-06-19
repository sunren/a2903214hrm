<%@ page language="java" contentType="text/html; charset=GBK"	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>

<head>
	<title>薪资帐套</title>
	<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />	
	<script type="text/javascript" src="../dwr/interface/DWRforFormulaValidate.js" ></script>
	<script type="text/javascript" src="../dwr/interface/DWRforAcctItemDef.js" ></script>
</head>
<body onload="initDisabledField()">
	<s:component template="bodyhead">
	    <s:param name="pagetitle" value="'薪资帐套配置'" />
	    <s:param name="helpUrl" value="'64'" />
	</s:component>
	<div id="div1" style="position:absolute;left:150px;top:20px;z-index:5;solid;width:240px ;display:none"  >
		<table width="100%" height =116px border="1" cellspacing="0" cellpadding="0" >
		<tr><td bgcolor="#eff3ff">固定值：在员工薪资配置中初始化，每月薪资发放时可修改。<br>浮动值：没有任何初始值，仅在每月薪资发放时可修改。<br>计算公式：在帐套设置中定义，系统自动计算，不能修改。</td></tr>
		</table>
	</div>
	<s:form id="addesa" name="addesa" action="addesa" namespace="/compensation" method="POST">
		<table width="100%" cellpadding="0" border="0">
			<tr><td>
		         <input type="submit" class='button' value=" 保存 " onclick="return valadi();"/>	
		         &nbsp;<input type="reset" class='button' value=" 重置 "/>		
				 &nbsp;<input type="button" class='button' value=" 返回 " onclick="window.location='searchesa.action'"/>
			   </td>
			 </tr>
		</table>  
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
			<tr>
			  	<s:textfield label="帐套名称" id="empsalaryacct.esacName" name="empsalaryacct.esacName"  size="25"></s:textfield>
			  	<td></td>
			  	<s:textfield label="帐套描述" id="empsalaryacct.esacDesc" name="empsalaryacct.esacDesc"  size="45"></s:textfield>
			</tr>
		</table>
		<p>
		&nbsp;	
		</p>
		<table id="esaContentTable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtable2">	
			<thead>
			<tr >	
			    <th>项目编号</th> <th>项目种类</th> <th>项目名称</th> <th>字段属性</th> <th>计算公式<img border='0' src='../resource/images/help_1.gif' width='12' height='12' alt='说明' onMouseOver="showDiv(this,'1');" onMouseOut="changeSmall('1');"/></th> <th colspan="2">尾数舍入</th>
			</tr>
			</thead>
			<input type="hidden" id="EMPSALARY_ACCT_LIMIT" value="<s:property value="@com.hr.base.Constants@EMPSALARY_ACCT_LIMIT"/>"/>
			<tbody id="itemsinputtable">
			<s:bean name="org.apache.struts2.util.Counter" id="rowcounter">
  			<s:param name="first" value="1"/><s:param name="last" value="dataDefIds!=null&&dataDefIds.length>=10?dataDefIds.length+1:10"/>
			</s:bean>
			<s:iterator value="#rowcounter" status="rowstatus">			
			<tr height="20" id="tr<s:property value='#rowstatus.count'/>">
			<td  align="center">A<s:property value="#rowstatus.count"/>
			</td>					
			<td  align="center">
				<input type="hidden" id="dataTypeValues" name="dataTypeValues" value="<s:property value='dataTypes[#rowstatus.count-1]' />"/>
				<select id="dataTypes" name="dataTypes" onchange="editTr=this.parentNode.parentNode;changeItems(this.value)" value="0"> 
					<option value="0">不启用</option><option style="color: red;" value="1">基本工资*</option><option value="2">固定项基数</option>
					<option value="3">固定项</option><option value="4">固定项总额</option>
					<option value="5">浮动项基数</option><option value="6">浮动项</option>
					<option value="7">浮动项总额</option><option style="color: red;" value="8">税前收入*</option>
					<option value="9">社保基数</option><option value="10">个人缴社保</option>
					<option value="11">公司代缴社保</option><option value="12">个人缴公积金</option>
					<option value="13">公司代缴公积金</option><option value="14">其他福利项</option>
					<option value="15">个人缴社保总额</option><option value="16">公司代缴社保总额</option>
					<option value="17">应纳税所得额</option><option style="color: red;" value="18">所得税*</option>
				    <option style="color: red;" value="19">税后收入*</option><option value="20">其他</option>
				</select>
			</td>
			<td align="center">
				<input type="hidden" name="dataNames" value="<s:property value='dataNames[#rowstatus.count-1]' />"/>
			   	<input type="hidden" name="dataDefIdValues" value="<s:property value='dataDefIds[#rowstatus.count-1]' />"/>
			   	<select id="dataDefIds" name="dataDefIds" onchange="editTr=this.parentNode.parentNode;getDefault(this.value)" value="<s:property value='dataDefIds[#rowstatus.count-1]' />" style="width:110px"></select>
			</td>
			<td  align="center">
				<s:select id="dataIsCalcs"  name="dataIsCalcs" value="dataIsCalcs[#rowstatus.count-1]" list="#{0:'固定值', 1:'浮动值',2:'计算公式'}"/>
			</td>
			<td  align="center">
			 	<input type="text" onfocus="changetono();" onblur="formulaVali(this, this.value)" onclick="currentTr=this.parentNode.parentNode;editTr=currentTr;" id="dataCalcs" value="<s:property value='dataCalcs[#rowstatus.count-1]'/>"  name="dataCalcs"  size="40"/>
			</td>	
			<td  align="center">
				<s:select id="dataRoundings" name="dataRoundings" value="dataRoundings[#rowstatus.count-1]" list="#{0:'不处理', 1:'见分进角',2:'见角进元',3:'四舍五入进角',4:'四舍五入进元',5:'去分',6:'不计角分'}"/>
			</td>
			<td align="center">
						<img src="../resource/images/delete_inline.gif" onMouseOver="this.style.cursor='hand';" 
						onClick="acctDelrow(this);" /></td>
			</tr>
			</s:iterator>
	        </tbody>
	        <tfoot>
			<tr><td height="0" colspan="11" rowspan="3">		
					<!-- 新增行项目 -->				
					<input class='button' name='button1' type="button" value=" 新增行 " onClick="acctAddrow('itemsinputtable');" />
					<!-- 清除所有 -->
					<input class='button' type='reset' name='button2' value=" 清除所有 " /></td>
			</tr>
	</tfoot>
		</table>
		<br>
	<table width="100%" cellspacing="0" cellpadding="0" class="gridtableList" name="resumeTable">
   <tbody>
	<tr><td>公式说明(详见用户手册)：<br>
	  &nbsp;&nbsp;内部变量：即项目编号，A1 A2 ... A48<br>
	  &nbsp;&nbsp;外部变量：B1YGL-参加工作工龄、B2YGL-本公司工龄、C1-本月应考勤天数、D1-薪资级别等级、D2-社保基数等<br>
	  &nbsp;&nbsp;表达式：数字、变量和符号，允许的算术表达式符号有+ - * / ()<br>
	  &nbsp;&nbsp;逻辑表达式：逻辑表达式符号有 >= > < <= == <> AND OR NOT IF<br>
	  &nbsp;&nbsp;函数：ABS CEILING INT MOD MAX MIN ROUND ROUNDUP ROUNDDOWN SIGN $ L$ N$ #
	</td></tr>
   </tbody></table>
   </s:form>
<script type="text/javascript" language="java">
// here defined a util;
if(typeof util=="undefined"){var util=new Object();}
util = {
isHTMLElement : function(ele, nodeName) {
  		if (ele == null || typeof ele != "object" || ele.nodeName == null) 
   			return false;
  		if (nodeName != null) {
    		var test = ele.nodeName.toLowerCase();
    	if (typeof nodeName == "string") { return test == nodeName.toLowerCase();}
			return false;
		}
  		return true;
		}
}
//end define
if	(newTr == null) { var newTr = null; }
if	(tbody == null)	{ var tbody = null; }
if  (check == null) { var check = false;} 
if  (currentTr == null) { var currentTr = null; }
if  (jj == null) {var jj = null;}
if  (row == null) { var row = null; } //需要操作的行
//初始化tbody
function initTable(tbodyId){
if	(newTr == null) newTr = [];
if (check) {if(tbody == null)tbody = [];}
try{
		for (var i = 0; i < arguments.length; i++){
			if (check){
				tbody[arguments[i]]=document.getElementById(arguments[i]);
			}
			newTr[arguments[i]]=lastRow(arguments[i]).cloneNode(true);
		}
}catch(e){alert('initTable :'+e);}
}

/*
 *  get(return) the first cell(TD) in this row(TR)
 */
function firstCell(tr){
	if(HRMCommon.navigatorIsIE()) return tr.firstChild;
	//下面 有待改进
	else return tr.childNodes[util.isHTMLElement(tr.childNodes[0],'TD')?0:1];
}

/*
 *  get(return) the last row(TR) in this tbody
 */
function lastRow(tbodyId){
		var trs= document.getElementById(tbodyId).childNodes;
		var j = trs.length - 1;
		return trs[util.isHTMLElement(trs[j],'TR') ? j:(j-1)];
}


function changetono(){
var obj=event.srcElement ? event.srcElement : event.target;

	obj.parentNode.style.border="1px inset #316ac5";;//"1px solid #316ac5";
	obj.style.backgroundColor="transparent";
}

function getTableIndex() {
	var trArray = document.getElementById('itemsinputtable').rows;
	for(var index =0;index<trArray.length;index++) {
		if(currentTr == trArray[index]) {
			return index;
		}
	}
	return 0;
}
//改变公式内容，例如在A7行前增加一行时，下面公式中的A8变为A9,1为增加，-1位减少
function changeCalc(type) {
	var index = getTableIndex();
	if(type<0){
		index += 2;
	}
	var itemsToChange = document.getElementsByName('dataCalcs');
	for(var i=0;i<itemsToChange.length;i++) {
		var itemsValue = itemsToChange[i].value;
		if(itemsValue == null ||itemsValue == ''){
			continue;
		}
		if(type<0){
			for(var j=index;j<49;j++) {
				itemsValue = replaceAllInStr(itemsValue,'A'+j,'A'+(j+type));
			}
		}else {
			for(var j=47;j>index;j--) {
				itemsValue = replaceAllInStr(itemsValue,'A'+j,'A'+(j+type));
			}
		}
		itemsToChange[i].value = itemsValue;
	}
}
function replaceAllInStr(str1,str2,str3) {
	if(str1 == null || str2 ==null || str3 == null) {
		return str1;
	}
	while(str1.indexOf(str2)>=0) {
		str1 = str1.replace(str2,str3);
	}
	return str1;
} 
 
function changeItems(type) {
		DWRforAcctItemDef.getItemsByType(type, changItemsCallBack);
		function changItemsCallBack(msg) {
			var editSelect = editTr.getElementsByTagName("select")[1];
			for(var i=0;i<editSelect.options.length;i++) {
				editSelect.options[i] = null;
			}
			if(msg==null || msg == ''){
				return;
			}
			for(var i=0;i<msg.length;i+=2){
				editSelect.options[i/2] = new Option(msg[i+1],msg[i],false,false);
			}
			getDefault(editSelect.value);
			//alert(msg);
		}
	}
function getDefault(esddId) {
	DWRforAcctItemDef.getItemById(esddId, changItemCallBack);
	function changItemCallBack(msg) {
		var isCalcSelect = editTr.getElementsByTagName("select")[2];
		var roundingSelect = editTr.getElementsByTagName("select")[3];
		isCalcSelect.value = msg.esddDataIsCalc;
		roundingSelect.value = msg.esddDataRounding;
	}
}

var editTr;
function valadi() {
	if(document.getElementById('empsalaryacct.esacName').value=='' || document.getElementById('empsalaryacct.esacName').value==null) {
		alert("帐套名称不能为空！");
		return false;
	}
	var typeArray = new Array();
	for(var i=0;i<20;i++) {
		typeArray[i] = 1;
	}
	types = document.getElementsByName('dataTypes');
	for(var i=0;i<types.length;i++) {
		flag = types[i].value;
		typeArray[flag] = 0;
	}
	var errors='';
	if(typeArray[1]==1){
		errors=errors+'基本工资';
	}
	if(typeArray[8]==1){
		errors=errors+' 税前收入';
	}
	if(typeArray[18]==1){
		errors=errors+' 所得税';
	}
	if(typeArray[19]==1){
		errors=errors+' 税后收入';
	}
	if(errors!='') {
		errMsg("errMsg","项目种类不完整，帐套必须包含："+errors);
		window.location="#";
		return false;
	}
	var itemIds = document.getElementsByName("dataDefIds");
	var jumpFlag = 0;
	for(var i=0;i<itemIds.length;i++) {
		if(itemIds[i].value!=null&&itemIds[i].value!=''){
			if(jumpFlag!=0) {
				alert("帐套项目中间不能有空行！请删除项目A"+i+"再试。");
				return false;
			}
		}else {
			jumpFlag = 1;
		}
		for(var j=0;j<itemIds.length;j++) {
	
			if(i!=j&&itemIds[i].value!=null&&itemIds[i].value!=''&&itemIds[i].value==itemIds[j].value) {
				alert("项目名称有重复！");
				return false;
			}
		}
	}
	return true;
}

function formulaVali(root, value) {
	var obj=event.srcElement ? event.srcElement : event.target;
	if(value == null || value == '') {
		obj.parentNode.style.border="1px #bbb solid";
		return;
	}
	DWRforFormulaValidate.validateFormula(value,callback);
	
	function callback(msg) {
		try{
			var retcode = HRMCommon.alertMsgHandler(msg);
			if(retcode == 'SUCC' || retcode == 'noauth') {
				obj.parentNode.style.border="1px #bbb solid"; // 正常的边框（黑色）
			}else {
				obj.parentNode.style.border="1px #FF0000 solid"; // 不正常的边框（红色）
			}
		}catch(e){alert(e);}
	}
  		obj.parentNode.style.borderTopWidth="0px";
  		obj.parentNode.style.borderLeftWidth="0px";
}


function initDisabledField() {
	var items = document.getElementsByName("dataTypes");
	var itemvalues = document.getElementsByName("dataTypeValues");
	var itemIds = document.getElementsByName("dataDefIds"); 
	var itemNames = document.getElementsByName("dataNames"); 
	var itemIdValues = document.getElementsByName("dataDefIdValues");
	for(var i=0;i<items.length;i++) {
		if(itemvalues[i].value!=null&&itemvalues[i].value!='') {
			items[i].value = itemvalues[i].value;
		}
	}
	for(var i=0;i<itemNames.length;i++) {
		itemIds[i].options[0] = new Option(itemNames[i].value,itemIdValues[i].value, false,false);
	}
}


 function changeSmall(img) {
	document.getElementById("div"+img).style.display = 'none';
}
 function  showDiv(e,img){   
    var x = e.offsetLeft, y = e.offsetTop;   
    while(e = e.offsetParent) { 
    x += e.offsetLeft;   
    y += e.offsetTop;
    } 
    document.getElementById("div"+img).style.left = x-250;
    document.getElementById("div"+img).style.top = y - 100;
   	document.getElementById("div"+img).style.display = 'inline';
}
/**
 * initialize the numbers ,like this (A1,A2,A3)
 */
function acct_initNo(tbodyId){
	var trs= document.getElementById(tbodyId).childNodes;
	for(var i=0,j=1,l=trs.length;i<l;i++)
		if(util.isHTMLElement(trs[i],'TR'))
			{firstCell(trs[i]).innerHTML="A"+j++;continue;}
}
/*
 *  delete a row in the tbody,and initialize the numbers
 */
function acctDelrow(obj){
	if(obj==null){obj=this;}
	var objRow=obj.parentNode.parentNode;
	var objTable=objRow.parentNode;
	currentTr = objRow;
	changeCalc(-1);//改变公式内容，例如在A7行删除时，下面公式中的A8变为A7
	objTable.removeChild(objRow);
	acct_initNo(objTable.id);
}
function acctAddrow(tbodyId){
	if(newTr==null||newTr[tbodyId]==null){initTable(tbodyId);}
	var trs= document.getElementById(tbodyId).childNodes;
	var j=0;
	for(var i=0,l=trs.length;i<l;i++)
		if(util.isHTMLElement(trs[i],'TR'))
			{j++;continue;}
			
	if(j>=document.getElementById('EMPSALARY_ACCT_LIMIT').value) {
		alert("帐套中最多包含"+document.getElementById('EMPSALARY_ACCT_LIMIT').value+"个项目！");
		return;
	}
	newRow = newTr[tbodyId].cloneNode(true);
	if(editTr==null){
		document.getElementById(tbodyId).appendChild(newRow);
	}else {
		changeCalc(1);//改变公式内容，例如在A7行前增加一行时，下面公式中的A8变为A9
		document.getElementById(tbodyId).insertBefore(newRow,editTr);
	}
	acct_initNo(tbodyId);
}
</script>

</body>
