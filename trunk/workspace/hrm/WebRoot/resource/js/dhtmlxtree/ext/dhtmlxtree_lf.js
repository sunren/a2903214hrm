/*
 * Compressed by JSA(www.xidea.org)
 */
dhtmlXTreeObject.prototype.setOnLoadingStart_lf=function(func){if(typeof(func)=="function")this.onXLS_2=func;else this.onXLS_2=eval(func)};dhtmlXTreeObject.prototype.setOnLoadingEnd_lf=function(func){if(typeof(func)=="function")this.onXLE_2=func;else this.onXLE_2=eval(func)};dhtmlXTreeObject.prototype.enableLoadingItem=function($){this.setOnLoadingStart(this._showFakeItem);this.setOnLoadingEnd(this._hideFakeItem);this.setOnLoadingStart=this.setOnLoadingStart_lf;this.setOnLoadingEnd=this.setOnLoadingEnd_lf;this._tfi_text=$||"Loading..."};dhtmlXTreeObject.prototype._showFakeItem=function($,_){if(this.onXLS_2)this.onXLS_2($,_);if((_===null)||(this._globalIdStorageFind("fake_load_xml_"+_)))return;this.insertNewItem(_,"fake_load_xml_"+_,this._tfi_text)};dhtmlXTreeObject.prototype._hideFakeItem=function($,_){if(this.onXLE_2)this.onXLE_2($,_);if(_===null)return;this.deleteItem("fake_load_xml_"+_)}