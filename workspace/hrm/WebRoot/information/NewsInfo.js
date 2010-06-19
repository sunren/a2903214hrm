//��н�������
var Infoclass = function(){};
Infoclass._exist 	= "EXISTED";//�����Ѿ�����
Infoclass._noauth 	= "NOAUTH";//��Ȩ����
Infoclass._fail 	= "FAIL";//�й���
Infoclass._error 	= "related";//ϵͳ�쳣
Infoclass._SUCC 	= "SUCC";//�����ɹ�
var InfoclassManager = function(config){BaseManager.call(this,config);};
InfoclassManager.prototype = BaseManager.prototype;
InfoclassManager.prototype._doCreateInit=function(){//��form��Ԫ�ظ�ֵ
	DWRUtil.setValue("infoclassId","");
	DWRUtil.setValue("infoclassName","");
	DWRUtil.setValue("infoclassStatus","1");//����
	DWRUtil.setValue("infoclassSortId","0");
};
InfoclassManager.prototype._doUpdateInit=function(row){
	DWRUtil.setValue("infoclassId",row.getAttribute("id"));//uuid
	DWRUtil.setValue("infoclassName",DWRUtil.unescapeHtml(row.cells[0].innerHTML));//����
	DWRUtil.setValue("infoclassStatus",row.getAttribute("infoclassStatus"));
};
InfoclassManager.prototype._fetchDataFromForm=function(){//��form���л�ȡԪ�ص�ֵ����װ�ɶ���
	var infoclass = new Infoclass();
	infoclass.id				=	DWRUtil.getValue("infoclassId");
	infoclass.infoclassName		=	DWRUtil.getValue("infoclassName");
	infoclass.infoclassStatus	=	parseInt(DWRUtil.getValue("infoclassStatus"));
	infoclass.infoclassSortId	=	0;
	return infoclass;
};
InfoclassManager.prototype._handleCallbackData=function(data,infoclass){//��������Ϣ
	if(data == Infoclass._exist){
		return DWRUtil.escapeHtml(infoclass.infoclassName)+"�Ѿ�����,����ʧ�ܣ�";
	}else if(data == Infoclass._noauth){
		return "��û��Ȩ�޽��в�����";
	}else if(data == Infoclass._fail){
		return "�޷�ɾ��,���������!";
	}else if(data == Infoclass._error){
		return "ϵͳ�쳣��";
	}else{
		return null;
	}
};
InfoclassManager.prototype._validate=function(infoclass){//��֤
	if(infoclass.infoclassName==null || infoclass.infoclassName.trim().length==0){
		this._addErrorMessage("infoclassName","�����");
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
		successMsg("errMsg", "��˾��Ϣ��������(" + DWRUtil.escapeHtml(infoclass.infoclassName) + ")�ɹ���"); 
		infoclass.id=data;//dwr��������������¼��uuid

		var cellFuncs = [
			function(item) { return DWRUtil.escapeHtml(infoclass.infoclassName); },
			function(item) { return infoclass.infoclassStatus=='0'?'�ر�':'����';}
		];
		DWRUtil.addRows(that.tbodyId,[''],cellFuncs,{
		 	rowCreator:function(options) {
				var row = document.createElement("tr"); 
				row.setAttribute("id",infoclass.id);//uuid
				row.setAttribute("infoclassStatus",infoclass.infoclassStatus);//����״̬
				return row;
			}
		});
		that._initTable();
	}	
};
InfoclassManager.prototype.doDelete=function(){
	var row = this._getSelectedRow();
	if(row == null){
		errMsg("errMsg", "��ѡ��Ҫɾ�����С�");
		return;
	}
	if(!confirm("ȷ��Ҫɾ����")){
		return;
	}
	var infoclassId = row.getAttribute("id");
	var infoclassName = DWRUtil.escapeHtml(row.cells[0].innerHTML);
	my.delInfo(infoclassId, delcallback);
	var that = this;
	function delcallback(data){
		if(data != ''){
			errMsg("errMsg", data+"����ʧ�ܣ�");
			return;
		}
		successMsg("errMsg", "ɾ����˾��Ϣ����(" +infoclassName + ")�ɹ���");
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
		row.cells[1].innerHTML = infoclass.infoclassStatus =='0'?'�ر�':'����';
		row.setAttribute("infoclassStatus",infoclass.infoclassStatus);//����״̬
		successMsg("errMsg", "��˾��Ϣ����(" + DWRUtil.escapeHtml(infoclass.infoclassName) + ")�޸ĳɹ���");
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
    	successMsg("errMsg", "�������򱣴�ɹ���");
    });
};