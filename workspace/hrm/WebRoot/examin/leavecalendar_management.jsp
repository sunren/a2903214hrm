<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />	
<jsp:include flush="true" page="/sitemesh/jsPackage.jsp"></jsp:include>	
<script type="text/javascript" src="../dwr/interface/LCMNG.js"></script>
</head>
<body>
	<br/>
	<s:component template="bodyhead">
	</s:component>
	<jsp:include flush="true" page="div_leavecalendar.jsp"></jsp:include>
	<form id="actionSrc" name="actionSrc" action="leavecalendarManage.action"  method="POST" onsubmit="return yearSubmit()">

		<!-- 隐藏字段 -->
		<s:hidden id="infoMeg" name="infoMeg" value="" />
		<s:hidden id="errorMsg" name="errorMsg" value=""/>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="btnlink">
			<tr>
				<td align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<input title="上一年"  name="prevYear" id="prevYear" class="button" type="submit" onclick="return yearchange('prev')" value="上一年" >
								<s:textfield id="yearSelect" name="yearSelect" size="10" readonly="true" maxlength="4"/>
								<input title="下一年"  name="nextYear" id="nextYear" class="button" type="submit" onClick="return yearchange('next')" value="下一年">
							</td>
						</tr>
					</table>
				</td>
				<td>				
					<input title="添加日历"  name="addLC" id="addLC" class="button" type="button" onclick="ShowLC(-1,'add');" value="添加日历">
					<input title="修改日历"  name="updateLC" id="updateLC" class="button" type="button" onclick="updateLeaveC()" value="修改日历">
					<input title="删除日历"  name="delLC" id="delLC" class="button" type="button" onclick="deleteLeaveC()" value="删除日历">
				</td>
			</tr>
		</table>
		<p align="left">&nbsp;</p> 
		<table cellpadding="0" cellspacing="0" width="100%" border="0" class="basictable" >
			<tr>
				<th>
					日期
				</th>
				<th>
					种类
				</th>
				<th>
					所属地区
				</th>
				<th>
					日期描述
				</th>
			</tr>
			<tbody id="tableSelect">
				<s:if test="result!=null&&!result.isEmpty()">
					<s:iterator value="result" status="index">
						<tr id="<s:property value='lcId' />">
							<td><s:date name="lcDate" format="yyyy-MM-dd" /></td>
							<td><s:property value="getTypeDescription(lcSign)" /></td>
							<td><s:property value="getLocationName(lcLocationNo)" /></td>
							<td><s:property value="lcDateDesc"/></td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td align="center" colspan="11">不存在符合条件的日历!</td>
					</tr>
				</s:else>
			</tbody>
	</table>
</form>

<script type="text/javascript" language="javascript">
var editObj = null;//全局变量(当前行)

model.simple.setParentIFrameFull("IFrame1"); // add by 金鑫（计算iframe高度）

//选中某行  
function doselect(){    
    var ele=event.target?event.target:event.srcElement;
	try{
	//新选中的行 TR
	var obj = ele.parentNode;
	//如果有旧行 并且不是同一行 内存中删除旧行
	if( editObj!=null && editObj!=obj){ editObj.className='';editObj = null; } //alert(editObj.className);
	editObj=obj;
	//如果 点了 已经选过的行 就不选
	//if(editObj.hasClassName('tr2')){ editObj.className='';editObj=null; }
	//else editObj.addClassName('tr2');
	$(editObj).toggleClass("tr2"); //jquery写法
	}catch(e){alert(e);}
}

//双击弹出某行
function doubleClickDo(){   
	if(editObj==null){
		doselect();
	}
	ShowLC(editObj.id,'update');
	return;
}

//单击与双击事件初始化
function addObserve(t){	
	$('#'+t).children().each(function(){	     
	    var clkFn="doselect();";
	    //alert(this.onclick());
	    if(typeof $(this).attr("onclick")=='undefined')clkFn+=$(this).attr("onclick");
		$(this).attr("onclick",clkFn);
		clkFn="doubleClickDo();";
		if(typeof $(this).attr("ondblclick")=='undefined')clkFn+=$(this).attr("ondblclick")
		$(this).attr("ondblclick",clkFn);
	});    
}

function getDateFromFormat(val,format) {
	val=val+"";
	format=format+"";
	var i_val=0;
	var i_format=0;
	var c="";
	var token="";
	var token2="";
	var x,y;
	var now=new Date();
	var year=now.getYear();
	var month=now.getMonth()+1;
	var date=1;
	var hh=now.getHours();
	var mm=now.getMinutes();
	var ss=now.getSeconds();
	var ampm="";
	
	while (i_format < format.length) {
		// Get next token from format string
		c=format.charAt(i_format);
		token="";
		while ((format.charAt(i_format)==c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
			}
		// Extract contents of value based on format token
		if (token=="yyyy" || token=="yy" || token=="y") {
			if (token=="yyyy") { x=4;y=4; }
			if (token=="yy")   { x=2;y=2; }
			if (token=="y")    { x=2;y=4; }
			year=_getInt(val,i_val,x,y);
			if (year==null) { return 0; }
			i_val += year.length;
			if (year.length==2) {
				if (year > 70) { year=1900+(year-0); }
				else { year=2000+(year-0); }
				}
			}
		else if (token=="MMM"||token=="NNN"){
			month=0;
			for (var i=0; i<MONTH_NAMES.length; i++) {
				var month_name=MONTH_NAMES[i];
				if (val.substring(i_val,i_val+month_name.length).toLowerCase()==month_name.toLowerCase()) {
					if (token=="MMM"||(token=="NNN"&&i>11)) {
						month=i+1;
						if (month>12) { month -= 12; }
						i_val += month_name.length;
						break;
						}
					}
				}
			if ((month < 1)||(month>12)){return 0;}
			}
		else if (token=="EE"||token=="E"){
			for (var i=0; i<DAY_NAMES.length; i++) {
				var day_name=DAY_NAMES[i];
				if (val.substring(i_val,i_val+day_name.length).toLowerCase()==day_name.toLowerCase()) {
					i_val += day_name.length;
					break;
					}
				}
			}
		else if (token=="MM"||token=="M") {
			month=_getInt(val,i_val,token.length,2);
			if(month==null||(month<1)||(month>12)){return 0;}
			i_val+=month.length;}
		else if (token=="dd"||token=="d") {
			date=_getInt(val,i_val,token.length,2);
			if(date==null||(date<1)||(date>31)){return 0;}
			i_val+=date.length;}
		else if (token=="hh"||token=="h") {
			hh=_getInt(val,i_val,token.length,2);
			if(hh==null||(hh<1)||(hh>12)){return 0;}
			i_val+=hh.length;}
		else if (token=="HH"||token=="H") {
			hh=_getInt(val,i_val,token.length,2);
			if(hh==null||(hh<0)||(hh>23)){return 0;}
			i_val+=hh.length;}
		else if (token=="KK"||token=="K") {
			hh=_getInt(val,i_val,token.length,2);
			if(hh==null||(hh<0)||(hh>11)){return 0;}
			i_val+=hh.length;}
		else if (token=="kk"||token=="k") {
			hh=_getInt(val,i_val,token.length,2);
			if(hh==null||(hh<1)||(hh>24)){return 0;}
			i_val+=hh.length;hh--;}
		else if (token=="mm"||token=="m") {
			mm=_getInt(val,i_val,token.length,2);
			if(mm==null||(mm<0)||(mm>59)){return 0;}
			i_val+=mm.length;}
		else if (token=="ss"||token=="s") {
			ss=_getInt(val,i_val,token.length,2);
			if(ss==null||(ss<0)||(ss>59)){return 0;}
			i_val+=ss.length;}
		else if (token=="a") {
			if (val.substring(i_val,i_val+2).toLowerCase()=="am") {ampm="AM";}
			else if (val.substring(i_val,i_val+2).toLowerCase()=="pm") {ampm="PM";}
			else {return 0;}
			i_val+=2;}
		else {
			if (val.substring(i_val,i_val+token.length)!=token) {return 0;}
			else {i_val+=token.length;}
			}
		}
	// If there are any trailing characters left in the value, it doesn't match
	if (i_val != val.length) { return 0; }
	// Is date valid for month?
	if (month==2) {
		// Check for leap year
		if ( ( (year%4==0)&&(year%100 != 0) ) || (year%400==0) ) { // leap year
			if (date > 29){ return 0; }
			}
		else { if (date > 28) { return 0; } }
		}
	if ((month==4)||(month==6)||(month==9)||(month==11)) {
		if (date > 30) { return 0; }
		}
	// Correct hours value
	if (hh<12 && ampm=="PM") { hh=hh-0+12; }
	else if (hh>11 && ampm=="AM") { hh-=12; }
	var newdate=new Date(year,month-1,date,hh,mm,ss);
	return newdate.getTime();
}


function _getInt(str,i,minlength,maxlength) {
	for (var x=maxlength; x>=minlength; x--) {
		var token=str.substring(i,i+x);
		if (token.length < minlength) { return null; }
		if (_isInteger(token)) { return token; }
		}
	return null;
}

//判整型
function _isInteger(val) {
	var digits="1234567890";
	for (var i=0; i < val.length; i++) {
		if (digits.indexOf(val.charAt(i))==-1) { return false; }
		}
	return true;
}

//当前年校验
function yearSubmit(){
	if(!isNum($('#yearSelect').val())){
				alert('输入年份不符合规则!');
				return false;
	}
}

//改变年(点击按钮)
function yearchange(varCondition){
	if(varCondition=='next'){
		if(!isNum($('#yearSelect').val())){
				alert('输入年份不符合规则!');
				return false;
		}
		$('#yearSelect').val(parseInt($('#yearSelect').val())+1);
	}else if(varCondition=='prev'){
		if(!isNum($('#yearSelect').val())){
				alert('输入年份不符合规则!');
				return false;
		}
		$('#yearSelect').val(parseInt($('#yearSelect').val())-1);
	}	
}

addObserve('tableSelect'); //tableSelect为tbody的id,初始化
</script>
</body>
</html>