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
            <th nowrap="nowrap">��������</th>
            <th nowrap="nowrap">�ɷѵ���</th>
            <th nowrap="nowrap">�ɷѷ�ʽ</th>
            <th nowrap="nowrap">���Ͻ��������</th>
            <th nowrap="nowrap">���Ͻ��������</th>
            <th nowrap="nowrap">ʧҵ��������</th>
            <th nowrap="nowrap">ʧҵ��������</th>
            <th nowrap="nowrap">ҽ�ƻ�������</th>
            <th nowrap="nowrap">ҽ�ƻ�������</th>
            <th nowrap="nowrap">���˻�������</th>
            <th nowrap="nowrap">���˻�������</th>
            <th nowrap="nowrap">������������</th>
            <th nowrap="nowrap">������������</th>
            <th nowrap="nowrap">�������������</th>
            <th nowrap="nowrap">�������������</th>
            <th nowrap="nowrap">������������</th>
            <th nowrap="nowrap">������������</th>
            <th nowrap="nowrap">��������</th>
        </tr>
    </thead>
    <tbody id="tbody_benefittype">
        <s:iterator value="bfTypeList">
            <tr id='<s:property value="id"/>' locationId="<s:property value='benefitTypeLocNo.id'/>" benefitTypePayType="<s:property value='benefitTypeLocNo.id'/>" align="center" >
                <td width="5%"><s:property value="benefitTypeName" /></td>
                <td width="5%"><s:property value="benefitTypeLocNo.locationName" /></td>
                <td width="5%"><s:if test="benefitTypePayType==0">�ɵ���</s:if><s:elseif test="benefitTypePayType==1">������</s:elseif></td>
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
    <a href="#" id="link_add_benefittype">����</a>
	<a href="#" id="link_delete_benefittype">ɾ��</a>
	<a href="#" id="link_update_benefittype">�޸�</a>
	<a href="#" id="link_sort_benefittype">��������</a>
</div>

<div id="dialog_benefittype" title="�籣��������" >  
    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>��������<font color="red">*</font>��</td>
                <td class="errorMessage">
                    <input id="benefitTypeName" type="text" maxlength="64"><label id="label_benefitTypeName">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>�ɷѵ���<font color="red">*</font>��</td>
                <td><select id="locationId" >
                    	<s:iterator value="locList">
                        	<option value="<s:property value="id"/>"><s:property value="locationName"/></option>
                        </s:iterator>
                     </select><label id="label_locationId">&nbsp;</label>
                </td> 
            </tr>   
            <tr>
                <td>�ɷѷ�ʽ<font color="red">*</font>��</td>
                <td>
                    <select id="benefitTypePayType">
                        <option value="0">�ɵ���</option>
                        <option value="1">������</option>
                     </select>
                </td> 
            </tr>
            <tr>
            	<td>���Ͻ��������(D20)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypePensionUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypePensionUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>���Ͻ��������(D21)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypePensionLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypePensionLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>ʧҵ���ջ�������(D22)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypeUnemploymentUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeUnemploymentUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>ʧҵ���ջ�������(D23)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypeUnemploymentLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeUnemploymentLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>ҽ�Ʊ��ջ�������(D24)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypeMedicareUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeMedicareUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>ҽ�Ʊ��ջ�������(D25)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypeMedicareLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeMedicareLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>���˱��ջ�������(D26)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypeInjuryUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeInjuryUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>���˱��ջ�������(D27)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypeInjuryLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeInjuryLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>�������ջ�������(D28)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypeChildbirthUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeChildbirthUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>�������ջ�������(D29)��</td>
            	<td class="errorMessage">
                    <input id="ebfTypeChildbirthLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeChildbirthLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>�������������(D30)��
                </td>
                <td class="errorMessage">
                    <input id="ebfTypeHousingUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeHousingUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>�������������(D31)��</td>
                <td class="errorMessage">
                    <input id="ebfTypeHousingLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeHousingLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>������������(D40)��</td>
                <td class="errorMessage">
                    <input id="ebfTypeOtherUplimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeOtherUplimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>������������(D41)��</td>
                <td class="errorMessage">
                    <input id="ebfTypeOtherLowlimit" type="text" onkeyup="value=value.replace(/[^0-9{\.}]/g,'');"/>
                    <label id="label_ebfTypeOtherLowlimit">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>����������</td>
                <td class="errorMessage">
                    <textarea id="benefitTypeDescription" cols="30" rows="3"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan=2>
					<font color="red">��ע��D20 �� D41��ʾн�������ж�Ӧ�ı����������ơ�</font>
                </td>
            </tr>
            <tr height="35">
               <td colspan="2" align="center">
                   <input id="btn_add_benefittype" type="button" value="����">
				   <input id="btn_update_benefittype" type="button" value="�޸�">
				   <input id="btn_close_benefittype" type="button" value="�ر�">
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

    // �趨id��Ӧ��Ԫ�ػ���ָ��Ԫ�صĿ�ȣ�ʹ��߶�����ռ�������ʣ�µĿ��
    $('#scrollDiv').width(document.body.clientWidth-230);
</script>
</body>
</html>
