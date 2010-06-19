<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="ww" uri="webwork"%>

<html>
	<head>
		<title>��ѵ����</title>
		<script type="text/javascript" src="../resource/js/inc.js"></script>
		<script type="text/javascript" src="../dwr/interface/Trtype.js"></script>
		<link rel="stylesheet" type="text/css" href="../resource/css/tab.css" />
		<script type="text/javascript" src="../resource/js/tmp.js"></script>
		<script type="text/javascript">
	var editObj = null;				//������ 
	var cp = 'tabPage1';			//����ҳ�� 
//���TEXTAREA�Ĵ�����Ϣ
function clearTextarea(){
	$("#trtName").next("span").html("");
	$("#trtDesc").next("span").html("");
}
/*�޸İ�ť �����¼�*/
function updateRecord(){
	if(editObj==null){ 
		errMsg("errMsg","��ѡ��Ҫ�޸ĵ��С�");
		return false;
	}
	showdep(editObj);
	showUpdate();
}
	/** ��֤���� **/
	function docheck(){
		var dep=document.getElementById(cp+'Form'),isture = true, _es=dep.elements;
		//clearErr();//clear error message.���������Ϣ
		clearTextarea();
						
		/** ������֤ **/
		for(var i=0;i<2;i++){
			if(_es[i].value==''){ replaceText(_es[i],'�����');isture = false; }					 
		}
					
		if(_es[2].value!=''){
    		if(_es[2].value.length>128){
            	replaceText(_es[2],'���������');
             	isture = false; 
         	}
		}
		var aTrs=document.getElementById(cp+'items').rows;
    		
		if(editObj!=null  && _es[0].value==editObj.firstChild.innerHTML){ return isture; }
        return isture;
	}
				
				
				
				
	/** ��Ӷ��� �лص� ˢ��ҳ�� **/
	function addobj() { 
		if(!docheck()){ return false;}
		var params;
					
		objetC={trtNo:null, trtName:document.getElementById('trtName').value, trtDesc:document.getElementById('trtDesc').value};
		params = DWRUtil.getValues(objetC);
		Trtype.addTrType(params,addcallback);
					
		/** �ص�������msg Ϊ�����ַ��� ������ID����ɹ� **/
		function addcallback(msg) {
			if (msg != 'FAIL' && msg != 'noauth'){
				try{
					successMsg("errMsg","��ӳɹ���");
					var trA=model.simple.getElement(cp+'items').insertRow(-1); 
					trA.id=msg;
					$(trA).click(function (){
						doselect(this);
				   });
					$(trA).dblclick(function (){
						showdep();
				   });
					trA.className='';
							    
					for(var pro in params){
						if(pro!="trtNo"){
							var objCell = document.createElement("td");
							var input = document.createElement("input");
							input.id = 'trtNo'+params[pro];
							input.type = 'hidden';
							input.value = msg;
							trA.appendChild(input);
							objCell.innerHTML = params[pro];
							trA.appendChild(objCell);
			            } 
					}
				}catch(e){alert(e);}
			} else if (msg == 'noauth') {
				errMsg("errMsg","��û����ӵ�Ȩ�ޣ�");
			} else {
				errMsg("errMsg","���ʧ�ܣ�");
		    }	
	    }
			
		closeDiag();
		//�رմ���	
	}

		/**	ɾ������	**/
	function del() {
		if(editObj==null){ errMsg("errMsg","��ѡ��Ҫɾ�����С�");return false;}
		if(!window.confirm('ȷ��ɾ����')){ return ;}
		Trtype.delTrType(editObj.id,delcallback)
			
		function delcallback(msg) {
			if(msg=='related'){ errMsg("errMsg","�޷�ɾ��,����������֮������");return;} 
			else if(msg=='SUCC'){
				var nextobj = editObj;
				editObj.parentNode.removeChild(editObj);
				if(editObj!=null){editObj=null;}
				
				successMsg("errMsg","ɾ���ɹ���");}
		      else if (msg == 'noauth')
		        {
		     	errMsg("errMsg","��û��ɾ����Ȩ�ޣ�");
		         }
		   			 else
			    {
			      errMsg("errMsg","ɾ��ʧ�ܣ�");
			    }
	        } 
		}

		/**	�޸ı������	**/
		function save(){		 
			//clearErr();
			if(!docheck()){return false;}
			var params;
			objetC={trtNo:trtNo, trtName:document.getElementById('trtName').value, trtDesc:document.getElementById('trtDesc').value};
			params = DWRUtil.getValues(objetC);
			Trtype.updateTrType(params,updatecallback);

			function updatecallback(msg) {
				if(msg=='SUCC'){successMsg("errMsg","�޸ĳɹ���");
					dowirte(params);//��д
				} else if (msg == 'noauth') {
					errMsg("errMsg","��û���޸ĵ�Ȩ�ޣ�");
			    } else {
			       errMsg("errMsg","�޸�ʧ�ܣ�");
			    }
				//�رմ���
				closeDiag();
			}
		}


		/***  �޸ĺ�ˢ������		**/
		function dowirte(params){
			var aTd=editObj.cells;
			var i=0;
		    count=0;
			for(var pro in params){   
				if(i!=0)
				aTd[i-1].innerHTML  = params[pro]; 
				i++;
			}
		}
			
	var trtNo;
	/** ��ʾ��ǰ�� ��Ϣ **/
	function showdep(){
	try{						
		var dep=document.getElementById(cp+'Form'); 						//get current page form
		//clearErr();									//clear error message.
		if  ( editObj == null )  doselect();	  	// get the current tr
		var _es=dep.elements;
		//��id
		var aTd = editObj.cells;
		var l = aTd.length;
		

		//��ֵ
		for(var i=0;i<l;i++){
			_es[i].value=aTd[i].innerHTML.trim();
		}
		trtNo=document.getElementById("trtNo"+_es[0].value).value;
	
		document.getElementById(cp+'addbtm').style.display = "none";
		var _ui = document.getElementById(cp+'updatebtm');
		showUpdate();
		_ui.style.display = "";
		hrm.common.openDialog(cp+'Formhidd');
			clearTextarea();
		}catch(e){alert(e);}
	}
	
	/**	��ʾ�޸�ҳ�� **/
	var modifyType="";
	function showUpdate(){
		modifyType="update";
		var updatebtm=document.getElementById(cp+'updatebtm');
		if(updatebtm!=null){
			updatebtm.onclick=save;//���޸İ�ťָ��save()
			updatebtm.value="����";
		}
	}

	/** ��ʾ��ӱ� �������� **/
	function showAddForm(){
	try{
		document.getElementById('tabPage1Form').reset();
		clearTextarea();
		document.getElementById(cp+'addbtm').style.display="inline";
		document.getElementById(cp+'updatebtm').style.display="none";
		hrm.common.openDialog(cp+'Form'+'hidd');
		}catch(e){alert(e);} 
	}
</script>
	</head>
	<body>
		<ww:component template="bodyhead">
			<ww:param name="pagetitle" value="'��ѵ�����б�'" />
		</ww:component>
		<span class="errorMessage" id="msg"></span>
		<div class="tab-page" id="tabPage1">
			<table cellpadding="0" cellspacing="1" width="100%"
				class="basictable">
				<thead>
					<tr>
						<th>
							��ѵ��������
						</th>
						<th>
							��ѵ��������
						</th>
					</tr>
				</thead>
				<tbody id="tabPage1items">
					<ww:iterator value="trtList">
						<tr id="<ww:property value="trtNo"/>">
							<input type="hidden" value="<ww:property value='trtNo'/>" id="trtNo<ww:property value='trtName' />"/>
							<td>
								<ww:property value="trtName" />
							</td>
							<td>
								<ww:property value="trtDesc" />
							</td>
						</tr>
					</ww:iterator>
				</tbody>
			</table>
			<div class="btnlink">
				<a href="#" onclick="showAddForm();">����</a>
				<a href="#" onclick="del();">ɾ��</a>
				<a href="#" onclick="updateRecord();">�޸�</a>
			</div>

			<br>
			<div id="tabPage1Formhidd" title="��ѵ�����б�">
				<FORM id="tabPage1Form" method="post">
					<TABLE cellpadding="0" cellspacing="1" width="100%"
						class="prompt_div_body">

						<TR>
							<TD>
								��ѵ��������
								<font color="red">*</font>��
								<br>
							</TD>
							<TD class="errorMessage">
								<INPUT id="trtName" TYPE="text" maxlength="64">
								<br>
							</TD>
						</TR>
						<TR>
							<TD>
								��ѵ��������
								<font color="red">*</font>��
								<br>
							</TD>
							<TD class="errorMessage">
								<textarea id="trtDesc" name="recchComments" cols="40" rows="3"
									onKeyDown='MKeyTextLength(this,128);'></textarea>

							</TD>
						</TR>
						<TR>
							<TD>
								&nbsp;
								<br>
							</TD>
							<TD class="right">
								<INPUT id="tabPage1addbtm" style="" TYPE="button" NAME="btn"
									onclick="addobj();" value="����">
								<INPUT id="tabPage1updatebtm" style="display:none" TYPE="button"
									NAME="btn" onclick="showUpdate();" value="�޸�">
								<input id="formbutton" type="button" onclick="resetForm();"
									name="Submit2" value="���">
								<input id="tabPage1close" type="button" onclick="closeDiag();"
									name="Submit3" value="�ر�">
							</TD>
						</TR>
					</TABLE>
				</FORM>
				<iframe scrolling="no"
					style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;"
					frameborder="0"></iframe>
			</div>
		</div>
		<script type="text/javascript" language="javascript">
  		  		hrm.common.initDialog('tabPage1Formhidd',480);//�϶�����
	  		    addObserve('tabPage1items');
		    </script>

	</body>

</html>
