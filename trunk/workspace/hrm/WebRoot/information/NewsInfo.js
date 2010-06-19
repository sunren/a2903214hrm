//加薪种类对象
var Infoclass = function(){};
Infoclass._exist 	= "EXISTED";//名称已经存在
Infoclass._noauth 	= "NOAUTH";//无权操作
Infoclass._fail 	= "FAIL";//有关联
Infoclass._error 	= "related";//系统异常
Infoclass._SUCC 	= "SUCC";//操作成功
var InfoclassManager = function(config){BaseManager.call(this,config);};
InfoclassManager.prototype = BaseManager.prototype;
InfoclassManager.prototype._doCreateInit=function(){//给form表单元素赋值
	DWRUtil.setValue("infoclassId","");
	DWRUtil.setValue("infoclassName","");
	DWRUtil.setValue("infoclassStatus","1");//正常
	DWRUtil.setValue("infoclassSortId","0");
};
InfoclassManager.prototype._doUpdateInit=function(row){
	DWRUtil.setValue("infoclassId",row.getAttribute("id"));//uuid
	DWRUtil.setValue("infoclassName",DWRUtil.unescapeHtml(row.cells[0].innerHTML));//名称
	DWRUtil.setValue("infoclassStatus",row.getAttribute("infoclassStatus"));
};
InfoclassManager.prototype._fetchDataFromForm=function(){//从form表单中获取元素的值，封装成对象
	var infoclass = new Infoclass();
	infoclass.id				=	DWRUtil.getValue("infoclassId");
	infoclass.infoclassName		=	DWRUtil.getValue("infoclassName");
	infoclass.infoclassStatus	=	parseInt(DWRUtil.getValue("infoclassStatus"));
	infoclass.infoclassSortId	=	0;
	return infoclass;
};
InfoclassManager.prototype._handleCallbackData=function(data,infoclass){//处理返回信息
	if(data == Infoclass._exist){
		return DWRUtil.escapeHtml(infoclass.infoclassName)+"已经存在,保存失败！";
	}else if(data == Infoclass._noauth){
		return "您没有权限进行操作！";
	}else if(data == Infoclass._fail){
		return "无法删除,有外键关联!";
	}else if(data == Infoclass._error){
		return "系统异常！";
	}else{
		return null;
	}
};
InfoclassManager.prototype._validate=function(infoclass){//验证
	if(infoclass.infoclassName==null || infoclass.infoclassName.trim().length==0){
		this._addErrorMessage("infoclassName","必填项！");
		return false;
	}
	return true;
};
InfoclassManager.prototype.doCreate=function(){
	var infoclass = this._fetchDataFromForm();
	if(!this._validate(infoclass)){
		return;
	}
	$("#"+this.dialogId).dialog('close');
	my.addInfo(infoclass,addCallBack);
	var that = this;
	function addCallBack(data){
		var error = that._handleCallbackData(data,infoclass);
		if(error != null){
			errMsg("errMsg", error);
			return;
		}
		successMsg("errMsg", "公司信息分类设置(" + DWRUtil.escapeHtml(infoclass.infoclassName) + ")成功。"); 
		infoclass.id=data;//dwr方法返回新增记录的uuid

		var cellFuncs = [
			function(item) { return DWRUtil.escapeHtml(infoclass.infoclassName); },
			function(item) { return infoclass.infoclassStatus=='0'?'关闭':'正常';}
		];
		DWRUtil.addRows(that.tbodyId,[''],cellFuncs,{
		 	rowCreator:function(options) {
				var row = document.createElement("tr"); 
				row.setAttribute("id",infoclass.id);//uuid
				row.setAttribute("infoclassStatus",infoclass.infoclassStatus);//启用状态
				return row;
			}
		});
		that._initTable();
	}	
};
InfoclassManager.prototype.doDelete=function(){
	var row = this._getSelectedRow();
	if(row == null){
		errMsg("errMsg", "请选择要删除的行。");
		return;
	}
	if(!confirm("确定要删除吗？")){
		return;
	}
	var infoclassId = row.getAttribute("id");
	var infoclassName = DWRUtil.escapeHtml(row.cells[0].innerHTML);
	my.delInfo(infoclassId, delcallback);
	var that = this;
	function delcallback(data){
		if(data != ''){
			errMsg("errMsg", data+"操作失败！");
			return;
		}
		successMsg("errMsg", "删除公司信息分类(" +infoclassName + ")成功。");
		that._deleteRow(row);
		that._initTable();
	}
};
InfoclassManager.prototype.doUpdate=function(){
	var infoclass = this._fetchDataFromForm();
	if(!this._validate(infoclass)){
		return;
	}
	$("#"+this.dialogId).dialog('close');
	my.updateInfo(infoclass, updatecallback);
	var that = this;
	function updatecallback(data){
		var error = that._handleCallbackData(data,infoclass);
		if(error != null){
			errMsg("errMsg", error);
			return;
		}

		var row = that._getRowById(infoclass.id);
		row.cells[0].innerHTML = DWRUtil.escapeHtml(infoclass.infoclassName);
		row.cells[1].innerHTML = infoclass.infoclassStatus =='0'?'关闭':'正常';
		row.setAttribute("infoclassStatus",infoclass.infoclassStatus);//启用状态
		successMsg("errMsg", "公司信息分类(" + DWRUtil.escapeHtml(infoclass.infoclassName) + ")修改成功。");
	}
};
InfoclassManager.prototype.doBatchSort=function(){
	var ids = this._getIdsInTable();
	var that = this;
    my.saveInforclassIdByBatch(ids,function (data){
    	var error = that._handleCallbackData(data);
		if(error != null){
			errMsg("errMsg", error);
			return;
		}
    	successMsg("errMsg", "最新排序保存成功。");
    });
};