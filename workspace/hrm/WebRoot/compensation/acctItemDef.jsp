<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<html>
<head>
	<script type="text/javascript" src="../dwr/interface/AcctItemDef.js"></script>
	<script type="text/javascript"src="../resource/js/compensation/accountItemDef.js"></script>
</head>
<body>
<table cellpadding="0" cellspacing="1" width="100%" class="tablesorter" id="table_acctitems">
	<thead>
		<tr>
			<th>项目名称</th>
			<th>项目种类</th>
			<th>项目描述</th>
			<th>数据类型</th>
			<th>尾数舍入</th>
			<th>输出格式</th>
			<th>打印长度</th>
		</tr>
	</thead>
	<tbody id="tbody_acctitems">
		<s:iterator value="datadefList">
		<tr id='<s:property value="esddId"/>'>
			<td><s:property value="esddName" /></td>
			<td>
				<s:if test="esddDataType==1">基本工资</s:if>
				<s:elseif test="esddDataType==2">固定项基数</s:elseif>
				<s:elseif test="esddDataType==3">固定项</s:elseif>
				<s:elseif test="esddDataType==4">固定项总额</s:elseif>
				<s:elseif test="esddDataType==5">浮动项基数</s:elseif>
				<s:elseif test="esddDataType==6">浮动项</s:elseif>
				<s:elseif test="esddDataType==7">浮动项总额</s:elseif>
				<s:elseif test="esddDataType==8">税前收入</s:elseif>
				<s:elseif test="esddDataType==9">社保基数</s:elseif>
				<s:elseif test="esddDataType==10">个人缴社保</s:elseif>
				<s:elseif test="esddDataType==11">公司代缴社保</s:elseif>
				<s:elseif test="esddDataType==12">个人缴公积金</s:elseif>
				<s:elseif test="esddDataType==13">公司代缴公积金</s:elseif>
				<s:elseif test="esddDataType==14">其他福利项</s:elseif>
				<s:elseif test="esddDataType==15">个人缴社保总额</s:elseif>
				<s:elseif test="esddDataType==16">公司代缴社保总额</s:elseif>
				<s:elseif test="esddDataType==17">应纳税所得额</s:elseif>
				<s:elseif test="esddDataType==18">所得税</s:elseif>
				<s:elseif test="esddDataType==19">税后收入</s:elseif>
				<s:elseif test="esddDataType==20">其他</s:elseif>
			</td>
			<td><s:property value="esddDesc" /></td>
			<td>
				<s:if test="esddDataIsCalc==0">固定值</s:if>
				<s:elseif test="esddDataIsCalc==1">浮动值</s:elseif>
				<s:else>计算公式</s:else>
			</td>
			<td>
				<s:if test="esddDataRounding==0">不处理</s:if>
				<s:if test="esddDataRounding==1">见分进角</s:if>
				<s:if test="esddDataRounding==2">见角进元</s:if>
				<s:if test="esddDataRounding==3">四舍五入进角</s:if>
				<s:if test="esddDataRounding==4">四舍五入进元</s:if>
				<s:if test="esddDataRounding==5">去分</s:if>
				<s:if test="esddDataRounding==6">不计角分</s:if>
			</td>
			<td>
				<s:if test="esddDataOutput==0"> 不输出</s:if>
				<s:else>
					<s:if test="esddDataOutput==1">excel输出</s:if>
					<s:else>不输出</s:else>
				</s:else>
			</td>
			<td><s:property value="esddDataLength" /></td>
		</tr>
	</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_acctitems">新增</a>
	<a href="#" id="link_delete_acctitems">删除</a>
	<a href="#" id="link_update_acctitems">修改</a>
	<a href="#" id="link_sort_acctitems">保存排序</a>
	<s:if test="changeIodef.length()>0">
	<a href="#" id="link_refresh_acctitems" onclick="refreshOMConfig('<s:property value="changeIodef" />');">刷新</a>
	</s:if>
</div>

<div id="dialog_acctitems" title="帐套项目定义">
	<table cellpadding="0" cellspacing="1" width="100%">
		<tr>
			<td>项目名称<font color="red">*</font>：</td>
			<td><input id="esddName" type="text" maxlength="64">
			    <label id="label_esddName">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>项目种类<font color="red">*</font>：</td>
			<td>
				<select id="esddDataType">
					<option style="color: red;" value="1">基本工资</option>
					<option value="2">固定项基数</option>
					<option value="3">固定项</option>
					<option value="4">固定项总额</option>
					<option value="5">浮动项基数</option>
					<option value="6">浮动项</option>
					<option value="7">浮动项总额</option>
					<option style="color: red;" value="8">税前收入</option>
					<option value="9">社保基数</option>
					<option value="10">个人缴社保</option>
					<option value="11">公司代缴社保</option>
					<option value="12">个人缴公积金</option>
					<option value="13">公司代缴公积金</option>
					<option value="14">其他福利项</option>
					<option value="15">个人缴社保总额</option>
					<option value="16">公司代缴社保总额</option>
					<option value="17">应纳税所得额</option>
					<option style="color: red;" value="18">所得税</option>
					<option style="color: red;" value="19">税后收入</option>
					<option value="20">其他</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>项目描述<font color="red">*</font>：</td>
			<td class="errorMessage">
				<input id="esddDesc" type="text" maxlength="64">
				<label id="label_esddDesc">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>数据类型<font color="red">*</font>：</td>
			<td>
				<s:select id="esddDataIsCalc"
					list="#{0:'固定值', 1:'浮动值',2:'计算公式'}" />
			</td>
		</tr>
		<tr>
			<td>尾数舍入<font color="red">*</font>：</td>
			<td>
				<s:select id="esddDataRounding"
					list="#{0:'不处理', 1:'见分进角',2:'见角进元',3:'四舍五入进角',4:'四舍五入进元',5:'去分',6:'不计角分'}" />
			</td>
		</tr>
		<tr>
			<td>输出格式：</td>
			<td>
				<s:select id="esddDataOutput" list="#{0:'不输出',1:'excel输出'}" />
			</td>
		</tr>
		<tr>
			<td>打印长度：</td>
			<td>
				<input id="esddDataLength" type="text" maxlength="64"
					onkeyup="value=value.replace(/\D/g,'')">
				<label id="label_esddDataLength">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="right">
				<input id="btn_add_acctitems" type="button" value="保存" />
				<input id="btn_update_acctitems" type="button" value="保存" />
				<input id="btn_close_acctitems" type="button" value="关闭" />
				<input type="hidden" id="esddId"/>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
    var config={
        tableId     :"table_acctitems",
        tbodyId     :"tbody_acctitems",
        dialogId    :"dialog_acctitems",
        updateButton:"btn_update_acctitems",
        addButton   :"btn_add_acctitems",
        dialogHeight:280,
        dialogWidth :400,
        addLink     :"link_add_acctitems",
        deleteLink  :"link_delete_acctitems",
        updateLink  :"link_update_acctitems",
        sortLink    :"link_sort_acctitems",
        closeButton :"btn_close_acctitems",
        modal       :true
    };
    dataDefManager = new Empsalarydatadef(config);

// 刷新薪资项目到导出配置；
function refreshOMConfig(str){
    AcctItemDef.refreshOMConfig(str,refreshCallback);
    function refreshCallback(info){
        if(info == 'success'){
            successMsg("errMsg", "刷新成功。");
        }else{
            errMsg("errMsg", "刷新失败。");
        }
    }
}
</script>
</body>
</html>
