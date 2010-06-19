<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="ww" uri="webwork"%>

<html>
<head>
<title>��Ƹ�����б�</title>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">@import url("../resource/css/tab.css");</style>
<script type="text/javascript" src="../dwr/interface/RecruitchannelList.js"></script>
<script type="text/javascript" src="../resource/js/inc.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/css/tab.css" />
<script type="text/javascript" src="../resource/js/validator.js"></script>
<script type="text/javascript" src="../resource/js/tmp.js"></script>

<script type="text/javascript">
	var editObj = null;				//������ 
	var cp = 'tabPage1';			//����ҳ�� 
	
	/** ��֤���� **/
	function docheck(){
		var dep=document.getElementById(cp+'Form'),isture = true, _es=dep.elements;
		//clearErr();//clear error message.���������Ϣ
						
		/** ������֤ **/
		for(var i=1;i<3;i++){
			if(_es[i].value==''){ replaceText(_es[i],'����');isture = false; }					 
		}
					
				
		/** �绰У�� **/
		var patrnTel = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
        if(!(patrnTel.test(_es[3].value))&&(_es[3].value!=""))	
	         {
        		replaceText(_es[3],'������绰�������ȷ��ʽ���磺021');isture = false;
	         }						
      
        /** EmailУ�� **/
        var patrnEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        if(!(patrnEmail.test(_es[4].value))&&(_es[4].value!=""))	
             {
        		replaceText(_es[4],'������Email����ȷ��ʽ��');isture = false;
             }				

	         
        /** �ʱ�У�� **/
        var patrnPostcode = /^[0-9]{6}$/;;
        if(!(patrnPostcode.test(_es[6].value))&&(_es[6].value!=""))	
             {
        		replaceText(_es[6],'�������ʱ���ȷ��ʽ��');isture = false;
             }				
 
    

		var aTrs=document.getElementById(cp+'items').rows;
		/**  ���²���������ѡ�������������֤ ���޸�ʱ��֤id�ظ�û������ **/
    		
		if(editObj!=null  && _es[0].value==editObj.firstChild.innerHTML){
	
																	 return isture; }
  		
        return isture;
	}
				
				
				
				
	/** ��Ӷ��� �лص� ˢ��ҳ�� **/
	function addobj() { 
		if(!docheck()){ return false;}
		var params;
					
		objetC={id:null,recchName:null,recchRespPerson:null,recchPhone:null,recchEmail:null,recchCityNo:null,recchAddrZip:null,recchAddr:null,recchComments:null};
		param = DWRUtil.getValues(objetC);
		RecruitchannelList.addRecruitchannel(param,addcallback);
		//ҳ�治��ʾid
		objetC1={recchName:null,recchRespPerson:null,recchPhone:null,recchEmail:null,recchCityNo:null,recchAddrZip:null,recchAddr:null,recchComments:null};			
		params=DWRUtil.getValues(objetC1);
		
		/** �ص�������msg Ϊ�����ַ��� ������ID����ɹ� **/
		function addcallback(msg) {
			if(msg=='ChannelNameExist'){
				errMsg("errMsg","��Ƹ���������Ѿ����ڣ��޷����");
				return ;
			}
		   if(msg!='FAIL'){
			   try{
				   successMsg("errMsg","��ӳɹ�");
			      
				    var trA=document.getElementById(cp+'items').insertRow(-1); 
				    
				    trA.id=msg;
				 
				    $(trA).click(function (){
						doselect(this);
				   });
					$(trA).dblclick(function (){
						showdep();
				   });
				    trA.className='';	
				    //alert(1);		        	    
				    for(var pro in params){
					   var objCell = document.createElement("td");
					  objCell.innerHTML = params[pro];
				        trA.appendChild(objCell);					     
						if(pro=='id'){
							objCell.innerHTML = msg;
						}else{
			            	objCell.innerHTML = params[pro];
			            }
						}				
				}catch(e)
				    {alert(e);}
				}		
		  else{
			  errMsg("errMsg","���ʧ��");
		 	  }
		 	  closeDiag();
			}
			
		//closeDiag();
		//�رմ���	
		}

		/**	ɾ������	**/
	function del() {
		if(editObj==null){errMsg("errMsg","��ѡ��Ҫɾ������");return false;}
		if(!window.confirm('ȷ��ɾ����')){ return ;}
		RecruitchannelList.delRecruitchannel(editObj.id,delcallback)
			
		function delcallback(msg) {
			if(msg=='related'){errMsg("errMsg","�޷�ɾ��,����������֮����");return;} 
			if(msg=='SUCC'){
				try{
					var nextobj = editObj;
					editObj.parentNode.removeChild(editObj);
					if(editObj!=null){editObj=null;}
					}catch(e){alert(e);}
					successMsg("errMsg","ɾ���ɹ�");}
				else{errMsg("errMsg","ɾ��ʧ��");}
			} 
		}

		/**	�޸ı������	**/
		function save(){		 
				//clearErr();
				if(!docheck()){return false;}
				var params;
				var id=editObj.id;				
				objetC={id:id,recchName:null,recchRespPerson:null,recchPhone:null,recchEmail:null,recchCityNo:null,recchAddrZip:null,recchAddr:null,recchComments:null};
				objetC1={recchName:null,recchRespPerson:null,recchPhone:null,recchEmail:null,recchCityNo:null,recchAddrZip:null,recchAddr:null,recchComments:null};
				params = DWRUtil.getValues(objetC);
				params1 = DWRUtil.getValues(objetC1);
				RecruitchannelList.updateRecruitchannel(params,updatecallback);
				
				function updatecallback(msg) {
					if(msg=='SUCC'){successMsg("errMsg","�޸ĳɹ�");
					 dowirte(params1);//��д
					closeDiag();
					                
					}
					else{successMsg("errMsg","�޸ĳɹ�");}
					closeDiag();
					
				}
			
			
			//�رմ���
				
		}


			/***  �޸ĺ�ˢ������		**/
			function dowirte(params1){
					var aTd=editObj.cells,i=0;
				    count=0;
					for(var pro in params1){   
					if(pro=='id'){count=1;continue ;} 
						else{         
						          aTd[i].innerHTML  =params1[pro]; 
						    }		
						i++;
					}
					
			}

	/** ��ʾ��ǰ�� ��Ϣ **/
	function showdep(){
	try{					
		var dep = document.getElementById(cp+'Form'); 						//get current page form
		//clearErr();	
		if  ( editObj == null )  doselect();	  	// get the current tr
		var _es = dep.elements;
		var aTd = editObj.cells;
	
		//��id
		var l = aTd.length;
		//��ֵ	
		_es[0].value=editObj.id;
		for(var i=0;i<l;i++){
			_es[i+1].value=aTd[i].innerHTML.trim();
		}
		document.getElementById(cp+'addbtm').style.display="none";
		var _ui = document.getElementById(cp+'updatebtm');
	
		showUpdate();
		_ui.style.display = "";
		clearTextarea();
		hrm.common.openDialog(cp+'Formhidd');
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
		clearTextarea();
		document.getElementById(cp+'Form').reset();
		document.getElementById(cp+'addbtm').style.display="inline";
		document.getElementById(cp+'updatebtm').style.display="none";
		hrm.common.openDialog(cp+'Form'+'hidd');
		}catch(e){alert(e);} 
	}
	/**	 ���� **/
	function updateRecord()
	{
	if(editObj==null){errMsg("errMsg","��ѡ��Ҫ�޸ĵ���");return false;}
	showdep(editObj);
	showUpdate();
	}
	//���TEXTAREA�Ĵ�����Ϣ
	function clearTextarea(){
		$("#recchName").next("span").html("");
		$("#recchRespPerson").next("span").html("");
	}
</script>
</head>
<body>
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'��Ƹ�����б�'" />
	</ww:component>
	<span class="errorMessage" id="msg"></span>
	<div class="tab-page" id="tabPage1">
		<table cellpadding="0" cellspacing="1" width="100%" class="basictable">
			<thead>
				<tr>					
					<th width="10%">����</th>
					<th width="10%">������</th>
					<th width="10%">�绰</th>
					<th width="10%">Email</th>
					<th width="5%">����</th>
					<th width="10%">�ʱ�</th>
					<th width="20%">��ַ</th>					
					<th width="20%">��ע</th>
				</tr>
			</thead>
			<tbody id="tabPage1items">
				<ww:iterator value="recruitchannelList">
				 <tr onMouseOver="this.bgColor='#DDF0F8'" onMouseOut="this.bgColor='#ffffff'" id="<ww:property value="id"/>">
					<td><ww:property value="recchName" /></td>
					<td><ww:property value="recchRespPerson" /></td>
					<td><ww:property value="recchPhone" /></td>
					<td><ww:property value="recchEmail" /></td>
					<td><ww:property value="recchCityNo" /></td>					
					<td><ww:property value="recchAddrZip" /></td>
					<td><ww:property value="recchAddr" /></td>
					<td><ww:property value="recchComments" /></td>
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
	<div id="tabPage1Formhidd" title="��Ƹ�����б�">
		<FORM id="tabPage1Form" method="post">
			<TABLE cellpadding="0" cellspacing="1" width="100%" class="prompt_div_body" >
				
				<TR>
					<INPUT id="id" TYPE="hidden"  />	
					<TD>����<font color="red">*</font>��<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchName" TYPE="text" 
						maxlength="64"> <br>
					</TD>
				</TR>
				<TR>
					<TD>������<font color="red">*</font>��<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchRespPerson" TYPE="text"
						maxlength="32"> <br>
					</TD>
				</TR>
			
			
				<TR>
					<TD>�绰��<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchPhone" TYPE="text"
						maxlength="32"> <br>
					</TD>
				</TR>
			
				<TR>
					<TD>Email��<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchEmail" TYPE="text"
						maxlength="64"> <br>
					</TD>
				</TR>
			
				<TR>
					<TD>���У�<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchCityNo" TYPE="text"
						maxlength="16"> <br>
					</TD>
				</TR>
			
				<TR>
					<TD>�ʱࣺ<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchAddrZip" TYPE="text"
						maxlength="6"> <br>
					</TD>
				</TR>
			
				<TR>
					<TD>��ַ��<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchAddr" TYPE="text" size="42"
						maxlength="255"> <br>
					</TD>
				</TR>
				
				<TR>
					<TD>��ע��<br>
					</TD>
					<TD><textarea name="recchComments" cols="40" rows="3" width="50%"
						onkeypress="MKeyTextLength(this,255);"></textarea>
			
					</TD>
				</TR>
				<TR>
					<TD>&nbsp; <br>
					</TD>
					<TD class="right"><ww:set name="hasbutton" value="0" /> <INPUT
					id="tabPage1addbtm" style="" TYPE="button" NAME="btn"
					onclick="addobj();" value="����"> <ww:set name="hasbutton"
					value="1" /> <ww:set name="hasbutton" value="1" /> <INPUT
					id="tabPage1updatebtm" style="display:none" TYPE="button" NAME="btn"
					onclick="showUpdate();" value="�޸�"> <ww:if
					test="#hasbutton==1">
					<input id="formbutton" type="button" onclick="resetForm();"
						name="Submit2" value="���">
				</ww:if> <input id="tabPage1close" type="button" onclick="closeDiag();"
						name="Submit3" value="�ر�"></TD>
				</TR>
			</TABLE>
		</FORM>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;" frameborder="0" ></iframe>
</div>
</div>
<script type="text/javascript" language="javascript">
  		  		hrm.common.initDialog('tabPage1Formhidd',480);//�϶�����
	  		    addObserve('tabPage1items');
		    </script>

</body>

</html>
