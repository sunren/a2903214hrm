<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="ww" uri="webwork"%>

<html>
	<head>
		<title>培训种类</title>
		<script type="text/javascript" src="../resource/js/inc.js"></script>
		<script type="text/javascript" src="../dwr/interface/Trtype.js"></script>
		<link rel="stylesheet" type="text/css" href="../resource/css/tab.css" />
		<script type="text/javascript" src="../resource/js/tmp.js"></script>
		<script type="text/javascript">
	var editObj = null;				//操作行 
	var cp = 'tabPage1';			//操作页面 
//清除TEXTAREA的错误信息
function clearTextarea(){
	$("#trtName").next("span").html("");
	$("#trtDesc").next("span").html("");
}
/*修改按钮 触发事件*/
function updateRecord(){
	if(editObj==null){ 
		errMsg("errMsg","请选择要修改的行。");
		return false;
	}
	showdep(editObj);
	showUpdate();
}
	/** 验证函数 **/
	function docheck(){
		var dep=document.getElementById(cp+'Form'),isture = true, _es=dep.elements;
		//clearErr();//clear error message.清除错误信息
		clearTextarea();
						
		/** 必填验证 **/
		for(var i=0;i<2;i++){
			if(_es[i].value==''){ replaceText(_es[i],'必填项！');isture = false; }					 
		}
					
		if(_es[2].value!=''){
    		if(_es[2].value.length>128){
            	replaceText(_es[2],'输入过长！');
             	isture = false; 
         	}
		}
		var aTrs=document.getElementById(cp+'items').rows;
    		
		if(editObj!=null  && _es[0].value==editObj.firstChild.innerHTML){ return isture; }
        return isture;
	}
				
				
				
				
	/** 添加动作 有回调 刷新页面 **/
	function addobj() { 
		if(!docheck()){ return false;}
		var params;
					
		objetC={trtNo:null, trtName:document.getElementById('trtName').value, trtDesc:document.getElementById('trtDesc').value};
		params = DWRUtil.getValues(objetC);
		Trtype.addTrType(params,addcallback);
					
		/** 回调函数，msg 为返回字符串 ，返回ID代表成功 **/
		function addcallback(msg) {
			if (msg != 'FAIL' && msg != 'noauth'){
				try{
					successMsg("errMsg","添加成功。");
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
				errMsg("errMsg","您没有添加的权限！");
			} else {
				errMsg("errMsg","添加失败！");
		    }	
	    }
			
		closeDiag();
		//关闭窗口	
	}

		/**	删除操作	**/
	function del() {
		if(editObj==null){ errMsg("errMsg","请选择要删除的行。");return false;}
		if(!window.confirm('确定删除吗？')){ return ;}
		Trtype.delTrType(editObj.id,delcallback)
			
		function delcallback(msg) {
			if(msg=='related'){ errMsg("errMsg","无法删除,有其他表与之关联！");return;} 
			else if(msg=='SUCC'){
				var nextobj = editObj;
				editObj.parentNode.removeChild(editObj);
				if(editObj!=null){editObj=null;}
				
				successMsg("errMsg","删除成功。");}
		      else if (msg == 'noauth')
		        {
		     	errMsg("errMsg","您没有删除的权限！");
		         }
		   			 else
			    {
			      errMsg("errMsg","删除失败！");
			    }
	        } 
		}

		/**	修改保存操作	**/
		function save(){		 
			//clearErr();
			if(!docheck()){return false;}
			var params;
			objetC={trtNo:trtNo, trtName:document.getElementById('trtName').value, trtDesc:document.getElementById('trtDesc').value};
			params = DWRUtil.getValues(objetC);
			Trtype.updateTrType(params,updatecallback);

			function updatecallback(msg) {
				if(msg=='SUCC'){successMsg("errMsg","修改成功。");
					dowirte(params);//回写
				} else if (msg == 'noauth') {
					errMsg("errMsg","您没有修改的权限！");
			    } else {
			       errMsg("errMsg","修改失败！");
			    }
				//关闭窗口
				closeDiag();
			}
		}


		/***  修改后刷新数据		**/
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
	/** 显示当前行 信息 **/
	function showdep(){
	try{						
		var dep=document.getElementById(cp+'Form'); 						//get current page form
		//clearErr();									//clear error message.
		if  ( editObj == null )  doselect();	  	// get the current tr
		var _es=dep.elements;
		//赋id
		var aTd = editObj.cells;
		var l = aTd.length;
		

		//赋值
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
			<ww:param name="pagetitle" value="'培训种类列表'" />
		</ww:component>
		<span class="errorMessage" id="msg"></span>
		<div class="tab-page" id="tabPage1">
			<table cellpadding="0" cellspacing="1" width="100%"
				class="basictable">
				<thead>
					<tr>
						<th>
							培训类型名称
						</th>
						<th>
							培训种类描述
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
				<a href="#" onclick="showAddForm();">新增</a>
				<a href="#" onclick="del();">删除</a>
				<a href="#" onclick="updateRecord();">修改</a>
			</div>

			<br>
			<div id="tabPage1Formhidd" title="培训种类列表">
				<FORM id="tabPage1Form" method="post">
					<TABLE cellpadding="0" cellspacing="1" width="100%"
						class="prompt_div_body">

						<TR>
							<TD>
								培训类型名称
								<font color="red">*</font>：
								<br>
							</TD>
							<TD class="errorMessage">
								<INPUT id="trtName" TYPE="text" maxlength="64">
								<br>
							</TD>
						</TR>
						<TR>
							<TD>
								培训种类描述
								<font color="red">*</font>：
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
									onclick="addobj();" value="保存">
								<INPUT id="tabPage1updatebtm" style="display:none" TYPE="button"
									NAME="btn" onclick="showUpdate();" value="修改">
								<input id="formbutton" type="button" onclick="resetForm();"
									name="Submit2" value="清空">
								<input id="tabPage1close" type="button" onclick="closeDiag();"
									name="Submit3" value="关闭">
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
  		  		hrm.common.initDialog('tabPage1Formhidd',480);//拖动窗口
	  		    addObserve('tabPage1items');
		    </script>

	</body>

</html>
