<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
 
<div id="dlgCalendarShow" style="width:350px;display:none;" >
	<form id="lcContent" name="lcContent" action="#" method="POST" enctype="multipart/form-data">
		<s:hidden id="lc.lcId" name="lc.lcId" /> 
		<table cellSpacing=1 cellPadding=0 width="100%" class="formtable" width="100%">
			<TR>
				<TD>
					日期
				</TD>
				<TD>				 
					<s:textfield id="lc_lcDate" name="lc.lcDate" required="true" size="11" />
		        	<img onclick="WdatePicker({el:'lc_lcDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">			        	  		             
				</TD>
			</TR>
			<TR>
				<TD>
					种类
				</TD>
				<TD>
					<s:select id="lc.lcSign" name="lc.lcSign" list="typeList" listKey="lcSign" listValue="signName" />
				</TD>
			</TR>
			<TR>
				<TD>
					所属地区
				</TD>
				<TD >
					<s:select id="lc.lcLocationNo.id" name="lc.lcLocationNo.id" list="locationList" listKey="id" listValue="locationName" emptyOption="false"/>
				</TD>
			</TR>
			<TR>
				<TD>
					日历描述
				</TD>
				<TD colspan="3" >
					<textarea id="lc.lcDateDesc" cols="30" ></textarea>
				</TD>
			</TR>
			<TR>
				<TD colspan="2" align="center">
					<strong class="errorMessage"><label id="errorinfo"></label></strong>
				</TD>
			</TR>
			<TR>
				<TD>
				</TD>
				<TD>
					<input id="submited" type="button" onclick="submitDo();" value="提交">
					&nbsp;
					<input id="reseted" type="button" onclick="canceled();" value="取消">
				</TD>
			</TR>
		 </table>
	</form>
</div>

<script type="text/javascript" language="javascript">
//添加日历
function addLC(){
	ShowLC(-1,'add');
}
 
//修改日历
function updateLeaveC(){
	if(editObj==null||editObj.id.length==0){
		alert('没有选中任何日历!');
		return;
	}
	ShowLC(editObj.id,'update'); //editObj.id 当前行
}
 
//删除日历 
function deleteLeaveC(){
	if(editObj==null){
		alert('没有选中任何日历!');
		return;
	}
	ShowLC(editObj.id,'del');
}

//invoke_drag("lcshowhead","lcshow");
var operartion; //操作类型(公用变量)
var optID;    //当前行Id
 
//提交(Dialog提交按钮)
function submitDo(){
	$('#submited').attr("disabled",true);  
	if(operartion!='del' && !hrm.common.isDate($('#lc_lcDate').val()) ){ //调common方法 operartion!='del' && $('#lc_lcDate').val().length!=10
		alert("日期输入不正确！");
		$('#submited').attr("disabled",false);
		return;
	}
	if(operartion!='del'&& $('#lc_lcDate').val().indexOf($('#yearSelect').val())!=0){ //$('lc.lcDate').value.indexOf($('yearSelect').value)!=0
		alert("输入日期与当前年限不符！");
		$('#submited').attr("disabled",false);
		return;
	}
	if(operartion=='add'){
		var location={'id':DWRUtil.getValue('lc.lcLocationNo.id')};
		var lc={'lcId':0,'lcSign':DWRUtil.getValue('lc.lcSign'),'lcDate':getDateFromFormat($('#lc_lcDate').val(),'yyyy-MM-dd'),'lcLocationNo':location,'lcDateDesc':DWRUtil.getValue('lc.lcDateDesc')};
		LCMNG.insertLeavecalendar(lc,addupdate_callback);
	}else if(operartion=='update'){
		var year=getDateFromFormat($('#lc_lcDate').val(),'yyyy');
		
		var location={'id':DWRUtil.getValue('lc.lcLocationNo.id')};
		var lc={'lcId':DWRUtil.getValue('lc.lcId'),'lcDate':getDateFromFormat($('#lc_lcDate').val(),'yyyy-MM-dd'),'lcSign':DWRUtil.getValue('lc.lcSign'),'lcLocationNo':location,'lcDateDesc':DWRUtil.getValue('lc.lcDateDesc')};
		LCMNG.updateLeavecalendar(lc,addupdate_callback);
	}else if(operartion=='del'){
		LCMNG.delLeavecalendar(optID,addupdate_callback);
	}
	function addupdate_callback(msg){
		if(msg=='suc'){
			//Element.hide('dlgCalendarShow');
			hrm.common.closeDialog('dlgCalendarShow');
			$('#infoMeg').val('操作成功。');
			$('#actionSrc').get(0).submit();
			return;
		}
		if(operartion=='del'){
			alert(msg);
		}else{
			DWRUtil.setValue('errorinfo',msg);
			//$('submited').disabled=false;
			$('#submited').attr("disabled",false);
		}
	}
}
 
//关闭(取消按钮)
function canceled(){
	//Element.hide('dlgCalendarShow');
	hrm.common.closeDialog('dlgCalendarShow'); 	
}
 
//显示日历(增删改公用)
function ShowLC(varID,varOpt){ //当前行id与操作类型
	operartion=varOpt;
	optID=varID;
	if(operartion=='del'){
		if(confirm("您确定要删除选中日历？")){
			submitDo();
		}
		return;
	}
	if(operartion=='update'){
 	LCMNG.loadLeavecalendar(optID,showlc_callback);
 	DWRUtil.setValue('errorinfo','');
	}else if(operartion=='add'){
		//$('dlgCalendarShow').style.display='inline';
		//document.getElementById('dlgCalendarShow').style.display='block';
		$('#dlgCalendarShow').dialog("option","title","日历信息显示");
		hrm.common.openDialog('dlgCalendarShow');
		DWRUtil.setValue('lc.lcId','');
 	DWRUtil.setValue('lc.lcDate', '');
 	DWRUtil.setValue('lc.lcDateDesc','');
 	DWRUtil.setValue('lc.lcLocationNo.id','');
 	DWRUtil.setValue('lc.lcSign','');
 	DWRUtil.setValue('errorinfo','');
	}
	function showlc_callback(lc) {
	 	if(lc==null){
	 		alert("您无权执行此操作，请重新登陆！");
	 		return;
	 	}
	 	//$('dlgCalendarShow').style.display='inline';
	 	//document.getElementById('dlgCalendarShow').style.display='block';
	 	$('#dlgCalendarShow').dialog("option","title","日历信息显示");
	 	hrm.common.openDialog('dlgCalendarShow');
 		DWRUtil.setValue('lc.lcId',lc.lcId);
 		DWRUtil.setValue('lc.lcDate', lc.lcDate.toHRMDateString()); //改掉公共方法lc.lcDate.format('yyyy-MM-dd')
 		DWRUtil.setValue('lc.lcDateDesc',lc.lcDateDesc);
 		if(lc.lcLocationNo!=null){
 		DWRUtil.setValue('lc.lcLocationNo.id',lc.lcLocationNo.id);
 		}
 		else{
 		DWRUtil.setValue('lc.lcLocationNo.id','all');
 		}
 		DWRUtil.setValue('lc.lcSign',lc.lcSign);
 	}
} 

hrm.common.initDialog('dlgCalendarShow');
</script>