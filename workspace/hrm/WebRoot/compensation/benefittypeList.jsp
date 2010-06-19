<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<head>
    <script type="text/javascript" src="../dwr/interface/bftType.js"></script>
    <script type="text/javascript" src="../resource/js/compensation/benefitType.js"></script>
</head>
<body>
	<div id="scrollDiv" style="overflow:scroll;">
	<table id="table_benefittype" cellpadding="0" cellspacing="1" width="100%" class="tablesorter">
    <thead>
        <tr align="center">
            <th nowrap="nowrap">种类名称</th>
            <th nowrap="nowrap">缴费地区</th>
            <th nowrap="nowrap">缴费方式</th>
            <th nowrap="nowrap">养老金基数上限</th>
            <th nowrap="nowrap">养老金基数下限</th>
            <th nowrap="nowrap">失业基数上限</th>
            <th nowrap="nowrap">失业基数下限</th>
            <th nowrap="nowrap">医疗基数上限</th>
            <th nowrap="nowrap">医疗基数下限</th>
            <th nowrap="nowrap">工伤基数上限</th>
            <th nowrap="nowrap">工伤基数下限</th>
            <th nowrap="nowrap">生育基数上限</th>
            <th nowrap="nowrap">生育基数下限</th>
            <th nowrap="nowrap">公积金基数上限</th>
            <th nowrap="nowrap">公积金基数下限</th>
            <th nowrap="nowrap">其他基数上限</th>
            <th nowrap="nowrap">其他基数下限</th>
            <th nowrap="nowrap">种类描述</th>
        </tr>
    </thead>
    <tbody id="tbody_benefittype">
        <s:iterator value="bfTypeList">
            <tr id='<s:property value="id"/>' locationId="<s:property value='benefitTypeLocNo.id'/>" benefitTypePayType="<s:property value='benefitTypeLocNo.id'/>" align="center" >
                <td width="5%"><s:property value="benefitTypeName" /></td>
                <td width="5%"><s:property value="benefitTypeLocNo.locationName" /></td>
                <td width="5%"><s:if test="benefitTypePayType==0">缴当月</s:if><s:elseif test="benefitTypePayType==1">缴下月</s:elseif></td>
                <td width="5%"><s:property value="ebfTypePensionUplimit" /></td>
                <td width="5%"><s:property value="ebfTypePensionLowlimit" /></td>
                <td width="5%"><s:property value="ebfTypeUnemploymentUplimit"/></td>
                <td width="5%"><s:property value="ebfTypeUnemploymentLowlimit"/></td>
                <td width="5%"><s:property value="ebfTypeMedicareUplimit"/></td>
                <td width="5%"><s:property value="ebfTypeMedicareLowlimit"/></td>
                <td width="5%"><s:property value="ebfTypeInjuryUplimit"/></td>
                <td width="5%"><s:property value="ebfTypeInjuryLowlimit"/></td>
                <td width="5%"><s:property value="ebfTypeChildbirthUplimit"/></td>
                <td width="5%"><s:property value="ebfTypeChildbirthLowlimit"/></td>
                <td width="5%"><s:property value="ebfTypeHousingUplimit" /></td>
                <td width="5%"><s:property value="ebfTypeHousingLowlimit" /></td>
                <td width="5%"><s:property value="ebfTypeOtherUplimit" /></td>
                <td width="5%"><s:property value="ebfTypeOtherLowlimit" /></td>
                <td width="15%" nowrap="nowrap"><s:property value="benefitTypeDescription" /></td>
            </tr>
        </s:iterator>
    </tbody>
</table>
</div>
<div class="btnlink">
    <a href="#" id="link_add_benefittype">新增</a>
	<a href="#" id="link_delete_benefittype">删除</a>
	<a href="#" id="link_update_benefittype">修改</a>
	<a href="#" id="link_sort_benefittype">保存排序</a>
</div>

<div id="dialog_benefittype" title="社保种类设置" >  
    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>种类名称<font color="red">*</font>：</td>
                <td class="errorMessage">
                    <input id="benefitTypeName" type="text" maxlength="64"><label id="label_benefitTypeName">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>缴费地区<font color="red">*</font>：</td>
                <td><select id="locationId" >
                    	<s:iterator value="locList">
                        	<option value="<s:property value="id"/>"><s:property value="locationName"/></option>
                        </s:iterator>
                     </select><label id="label_locationId">&nbsp;</label>
                </td> 
            </tr>   
            <tr>
                <td>缴费方式<font color="red">*</font>：</td>
                <td>
                    <select id="benefitTypePayType">
                        <option value="0">缴当月</option>
                        <option value="1">缴下月</option>
                     </select>
                </td> 
            </tr>
            <tr>
            	<td>养老金基数上限(D20)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypePensionUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypePensionUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>养老金基数下限(D21)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypePensionLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypePensionLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>失业保险基数上限(D22)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypeUnemploymentUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeUnemploymentUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>失业保险基数下限(D23)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypeUnemploymentLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeUnemploymentLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>医疗保险基数上限(D24)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypeMedicareUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeMedicareUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>医疗保险基数下限(D25)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypeMedicareLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeMedicareLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>工伤保险基数上限(D26)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypeInjuryUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeInjuryUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>工伤保险基数下限(D27)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypeInjuryLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeInjuryLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>生育保险基数上限(D28)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypeChildbirthUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeChildbirthUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>生育保险基数下限(D29)：</td>
            	<td class="errorMessage">
                    <input id="ebfTypeChildbirthLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeChildbirthLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>公积金基数上限(D30)：
                </td>
                <td class="errorMessage">
                    <input id="ebfTypeHousingUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeHousingUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>公积金基数下限(D31)：</td>
                <td class="errorMessage">
                    <input id="ebfTypeHousingLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeHousingLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>其他基数上限(D40)：</td>
                <td class="errorMessage">
                    <input id="ebfTypeOtherUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeOtherUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>其他基数下限(D41)：</td>
                <td class="errorMessage">
                    <input id="ebfTypeOtherLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeOtherLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>种类描述：</td>
                <td class="errorMessage">
                    <textarea id="benefitTypeDescription" cols="30" rows="3"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan=2>
					<font color="red">备注：D20 ～ D41表示薪资帐套中对应的变量引用名称。</font>
                </td>
            </tr>
            <tr height="35">
               <td colspan="2" align="center">
                   <input id="btn_add_benefittype" type="button" value="保存">
				   <input id="btn_update_benefittype" type="button" value="修改">
				   <input id="btn_close_benefittype" type="button" value="关闭">
				   <s:hidden id="benefitTypeId"></s:hidden>
                   <s:hidden id="benefitTypeSortId"></s:hidden>
               </td>
            </tr>
     </table>
</div>
<script type="text/javascript">
    var config={
        tableId     :"table_benefittype",
        tbodyId     :"tbody_benefittype",
        dialogId    :"dialog_benefittype",
        updateButton:"btn_update_benefittype",
        addButton   :"btn_add_benefittype",
        dialogHeight:540,
        dialogWidth :400,
        addLink     :"link_add_benefittype",
        deleteLink  :"link_delete_benefittype",
        updateLink  :"link_update_benefittype",
        sortLink    :"link_sort_benefittype",
        closeButton :"btn_close_benefittype",
        modal       :true
    };
    benefittypeManager = new BenefitTypeManager(config);

    // 设定id对应的元素或者指定元素的宽度，使其高度正好占满浏览器剩下的宽度
    $('#scrollDiv').width(document.body.clientWidth-230);
</script>
</body>
</html>
