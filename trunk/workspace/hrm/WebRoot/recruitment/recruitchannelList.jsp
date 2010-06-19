<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="ww" uri="webwork"%>

<html>
<head>
<title>招聘渠道列表</title>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">@import url("../resource/css/tab.css");</style>
<script type="text/javascript" src="../dwr/interface/RecruitchannelList.js"></script>
<script type="text/javascript" src="../resource/js/inc.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/css/tab.css" />
<script type="text/javascript" src="../resource/js/validator.js"></script>
<script type="text/javascript" src="../resource/js/tmp.js"></script>

<script type="text/javascript">
	var editObj = null;				//操作行 
	var cp = 'tabPage1';			//操作页面 
	
	/** 验证函数 **/
	function docheck(){
		var dep=document.getElementById(cp+'Form'),isture = true, _es=dep.elements;
		//clearErr();//clear error message.清除错误信息
						
		/** 必填验证 **/
		for(var i=1;i<3;i++){
			if(_es[i].value==''){ replaceText(_es[i],'必填');isture = false; }					 
		}
					
				
		/** 电话校验 **/
		var patrnTel = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
        if(!(patrnTel.test(_es[3].value))&&(_es[3].value!=""))	
	         {
        		replaceText(_es[3],'请输入电话号码的正确格式。如：021');isture = false;
	         }						
      
        /** Email校验 **/
        var patrnEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        if(!(patrnEmail.test(_es[4].value))&&(_es[4].value!=""))	
             {
        		replaceText(_es[4],'请输入Email的正确格式。');isture = false;
             }				

	         
        /** 邮编校验 **/
        var patrnPostcode = /^[0-9]{6}$/;;
        if(!(patrnPostcode.test(_es[6].value))&&(_es[6].value!=""))	
             {
        		replaceText(_es[6],'请输入邮编正确格式。');isture = false;
             }				
 
    

		var aTrs=document.getElementById(cp+'items').rows;
		/**  更新操作，跳过选择的渠道名字验证 ，修改时保证id重复没有问题 **/
    		
		if(editObj!=null  && _es[0].value==editObj.firstChild.innerHTML){
	
																	 return isture; }
  		
        return isture;
	}
				
				
				
				
	/** 添加动作 有回调 刷新页面 **/
	function addobj() { 
		if(!docheck()){ return false;}
		var params;
					
		objetC={id:null,recchName:null,recchRespPerson:null,recchPhone:null,recchEmail:null,recchCityNo:null,recchAddrZip:null,recchAddr:null,recchComments:null};
		param = DWRUtil.getValues(objetC);
		RecruitchannelList.addRecruitchannel(param,addcallback);
		//页面不显示id
		objetC1={recchName:null,recchRespPerson:null,recchPhone:null,recchEmail:null,recchCityNo:null,recchAddrZip:null,recchAddr:null,recchComments:null};			
		params=DWRUtil.getValues(objetC1);
		
		/** 回调函数，msg 为返回字符串 ，返回ID代表成功 **/
		function addcallback(msg) {
			if(msg=='ChannelNameExist'){
				errMsg("errMsg","招聘渠道名称已经存在，无法添加");
				return ;
			}
		   if(msg!='FAIL'){
			   try{
				   successMsg("errMsg","添加成功");
			      
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
			  errMsg("errMsg","添加失败");
		 	  }
		 	  closeDiag();
			}
			
		//closeDiag();
		//关闭窗口	
		}

		/**	删除操作	**/
	function del() {
		if(editObj==null){errMsg("errMsg","请选择要删除的行");return false;}
		if(!window.confirm('确定删除吗？')){ return ;}
		RecruitchannelList.delRecruitchannel(editObj.id,delcallback)
			
		function delcallback(msg) {
			if(msg=='related'){errMsg("errMsg","无法删除,有其他表与之关连");return;} 
			if(msg=='SUCC'){
				try{
					var nextobj = editObj;
					editObj.parentNode.removeChild(editObj);
					if(editObj!=null){editObj=null;}
					}catch(e){alert(e);}
					successMsg("errMsg","删除成功");}
				else{errMsg("errMsg","删除失败");}
			} 
		}

		/**	修改保存操作	**/
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
					if(msg=='SUCC'){successMsg("errMsg","修改成功");
					 dowirte(params1);//回写
					closeDiag();
					                
					}
					else{successMsg("errMsg","修改成功");}
					closeDiag();
					
				}
			
			
			//关闭窗口
				
		}


			/***  修改后刷新数据		**/
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

	/** 显示当前行 信息 **/
	function showdep(){
	try{					
		var dep = document.getElementById(cp+'Form'); 						//get current page form
		//clearErr();	
		if  ( editObj == null )  doselect();	  	// get the current tr
		var _es = dep.elements;
		var aTd = editObj.cells;
	
		//赋id
		var l = aTd.length;
		//赋值	
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
	
	/**	显示修改页面 **/
	var modifyType="";
	function showUpdate(){
		modifyType="update";
		var updatebtm=document.getElementById(cp+'updatebtm');
		if(updatebtm!=null){
			updatebtm.onclick=save;//让修改按钮指向save()
			updatebtm.value="保存";
		}
	}

	/** 显示添加表单 浮动窗口 **/
	function showAddForm(){
	try{
		clearTextarea();
		document.getElementById(cp+'Form').reset();
		document.getElementById(cp+'addbtm').style.display="inline";
		document.getElementById(cp+'updatebtm').style.display="none";
		hrm.common.openDialog(cp+'Form'+'hidd');
		}catch(e){alert(e);} 
	}
	/**	 更新 **/
	function updateRecord()
	{
	if(editObj==null){errMsg("errMsg","请选择要修改的行");return false;}
	showdep(editObj);
	showUpdate();
	}
	//清除TEXTAREA的错误信息
	function clearTextarea(){
		$("#recchName").next("span").html("");
		$("#recchRespPerson").next("span").html("");
	}
</script>
</head>
<body>
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'招聘渠道列表'" />
	</ww:component>
	<span class="errorMessage" id="msg"></span>
	<div class="tab-page" id="tabPage1">
		<table cellpadding="0" cellspacing="1" width="100%" class="basictable">
			<thead>
				<tr>					
					<th width="10%">名称</th>
					<th width="10%">负责人</th>
					<th width="10%">电话</th>
					<th width="10%">Email</th>
					<th width="5%">城市</th>
					<th width="10%">邮编</th>
					<th width="20%">地址</th>					
					<th width="20%">备注</th>
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
		<a href="#" onclick="showAddForm();">新增</a>
		<a href="#" onclick="del();">删除</a>
		<a href="#" onclick="updateRecord();">修改</a>
	</div>

	<br>
	<div id="tabPage1Formhidd" title="招聘渠道列表">
		<FORM id="tabPage1Form" method="post">
			<TABLE cellpadding="0" cellspacing="1" width="100%" class="prompt_div_body" >
				
				<TR>
					<INPUT id="id" TYPE="hidden"  />	
					<TD>名称<font color="red">*</font>：<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchName" TYPE="text" 
						maxlength="64"> <br>
					</TD>
				</TR>
				<TR>
					<TD>负责人<font color="red">*</font>：<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchRespPerson" TYPE="text"
						maxlength="32"> <br>
					</TD>
				</TR>
			
			
				<TR>
					<TD>电话：<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchPhone" TYPE="text"
						maxlength="32"> <br>
					</TD>
				</TR>
			
				<TR>
					<TD>Email：<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchEmail" TYPE="text"
						maxlength="64"> <br>
					</TD>
				</TR>
			
				<TR>
					<TD>城市：<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchCityNo" TYPE="text"
						maxlength="16"> <br>
					</TD>
				</TR>
			
				<TR>
					<TD>邮编：<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchAddrZip" TYPE="text"
						maxlength="6"> <br>
					</TD>
				</TR>
			
				<TR>
					<TD>地址：<br>
					</TD>
					<TD class="errorMessage"><INPUT id="recchAddr" TYPE="text" size="42"
						maxlength="255"> <br>
					</TD>
				</TR>
				
				<TR>
					<TD>备注：<br>
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
					onclick="addobj();" value="保存"> <ww:set name="hasbutton"
					value="1" /> <ww:set name="hasbutton" value="1" /> <INPUT
					id="tabPage1updatebtm" style="display:none" TYPE="button" NAME="btn"
					onclick="showUpdate();" value="修改"> <ww:if
					test="#hasbutton==1">
					<input id="formbutton" type="button" onclick="resetForm();"
						name="Submit2" value="清空">
				</ww:if> <input id="tabPage1close" type="button" onclick="closeDiag();"
						name="Submit3" value="关闭"></TD>
				</TR>
			</TABLE>
		</FORM>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;" frameborder="0" ></iframe>
</div>
</div>
<script type="text/javascript" language="javascript">
  		  		hrm.common.initDialog('tabPage1Formhidd',480);//拖动窗口
	  		    addObserve('tabPage1items');
		    </script>

</body>

</html>
