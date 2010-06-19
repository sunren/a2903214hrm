<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
 
<div id="dlgCalendarShow" style="width:350px;display:none;" >
	<form id="lcContent" name="lcContent" action="#" method="POST" enctype="multipart/form-data">
		<s:hidden id="lc.lcId" name="lc.lcId" /> 
		<table cellSpacing=1 cellPadding=0 width="100%" class="formtable" width="100%">
			<TR>
				<TD>
					����
				</TD>
				<TD>				 
					<s:textfield id="lc_lcDate" name="lc.lcDate" required="true" size="11" />
		        	<img onclick="WdatePicker({el:'lc_lcDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">			        	  		             
				</TD>
			</TR>
			<TR>
				<TD>
					����
				</TD>
				<TD>
					<s:select id="lc.lcSign" name="lc.lcSign" list="typeList" listKey="lcSign" listValue="signName" />
				</TD>
			</TR>
			<TR>
				<TD>
					��������
				</TD>
				<TD >
					<s:select id="lc.lcLocationNo.id" name="lc.lcLocationNo.id" list="locationList" listKey="id" listValue="locationName" emptyOption="false"/>
				</TD>
			</TR>
			<TR>
				<TD>
					��������
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
					<input id="submited" type="button" onclick="submitDo();" value="�ύ">
					&nbsp;
					<input id="reseted" type="button" onclick="canceled();" value="ȡ��">
				</TD>
			</TR>
		 </table>
	</form>
</div>

<script type="text/javascript" language="javascript">
//�������
function addLC(){
	ShowLC(-1,'add');
}
 
//�޸�����
function updateLeaveC(){
	if(editObj==null||editObj.id.length==0){
		alert('û��ѡ���κ�����!');
		return;
	}
	ShowLC(editObj.id,'update'); //editObj.id ��ǰ��
}
 
//ɾ������ 
function deleteLeaveC(){
	if(editObj==null){
		alert('û��ѡ���κ�����!');
		return;
	}
	ShowLC(editObj.id,'del');
}

//invoke_drag("lcshowhead","lcshow");
var operartion; //��������(���ñ���)
var optID;    //��ǰ��Id
 
//�ύ(Dialog�ύ��ť)
function submitDo(){
	$('#submited').attr("disabled",true);  
	if(operartion!='del' && !hrm.common.isDate($('#lc_lcDate').val()) ){ //��common���� operartion!='del' && $('#lc_lcDate').val().length!=10
		alert("�������벻��ȷ��");
		$('#submited').attr("disabled",false);
		return;
	}
	if(operartion!='del'&& $('#lc_lcDate').val().indexOf($('#yearSelect').val())!=0){ //$('lc.lcDate').value.indexOf($('yearSelect').value)!=0
		alert("���������뵱ǰ���޲�����");
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
			$('#infoMeg').val('�����ɹ���');
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
 
//�ر�(ȡ����ť)
function canceled(){
	//Element.hide('dlgCalendarShow');
	hrm.common.closeDialog('dlgCalendarShow'); 	
}
 
//��ʾ����(��ɾ�Ĺ���)
function ShowLC(varID,varOpt){ //��ǰ��id���������
	operartion=varOpt;
	optID=varID;
	if(operartion=='del'){
		if(confirm("��ȷ��Ҫɾ��ѡ��������")){
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
		$('#dlgCalendarShow').dialog("option","title","������Ϣ��ʾ");
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
	 		alert("����Ȩִ�д˲����������µ�½��");
	 		return;
	 	}
	 	//$('dlgCalendarShow').style.display='inline';
	 	//document.getElementById('dlgCalendarShow').style.display='block';
	 	$('#dlgCalendarShow').dialog("option","title","������Ϣ��ʾ");
	 	hrm.common.openDialog('dlgCalendarShow');
 		DWRUtil.setValue('lc.lcId',lc.lcId);
 		DWRUtil.setValue('lc.lcDate', lc.lcDate.toHRMDateString()); //�ĵ���������lc.lcDate.format('yyyy-MM-dd')
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