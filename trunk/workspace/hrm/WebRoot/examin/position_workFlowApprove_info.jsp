<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
</head>
<body>
<form id="searchForm" action="workFlowApproveManageUpdate.action">
<s:hidden name="positionId" id="positionId"></s:hidden>
<table class="gridtableList" width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<th>员工名称</th>
			<th>职位</th>
			<th>部门</th>
			<th>类型</th>
			<th>审批权限</th>
			<th>审批额度</th>
		</tr>
		<s:if test="!items.isEmpty()">
			<s:iterator value="items" status="s">
			<tr>
				<td align="center"><s:property value="name"/></td>
				<td align="center"><s:property value="description"/></td>
				<td align="center"><s:property value="deptName"/></td>
				<td align="center">
					<input type="hidden" name="workFlow<s:property value="#s.count"/>.id" value="<s:property value="workFlow.id"/>"/>
					<input type="hidden" name="workFlow<s:property value="#s.count"/>.workFlowApproverId" value="<s:property value="workFlow.workFlowApproverId"/>"/>
					<input type="hidden" name="workFlow<s:property value="#s.count"/>.workFlowApproverType" value="<s:property value="workFlow.workFlowApproverType"/>"/>
					<s:if test="workFlow.workFlowApproverType=='leaverequest'">请假</s:if>
					<s:if test="workFlow.workFlowApproverType=='overtimerequest'">加班</s:if>
				</td>
				<td align="center">
					<s:select onchange="doChange('%{#s.count}',this);" id="ind_%{#s.count}" list="#{2:'无审批资格',0:'可以审批',3:'无条件终审',1:'有条件终审'}" name="workFlow.workFlowApproverInd"></s:select>
					<input type="hidden" value="<s:property value="workFlow.workFlowApproverInd"/>" name="workFlow<s:property value="#s.count" />.workFlowApproverInd" id="indValue_<s:property value="#s.count" />"/>
				</td>
			<s:if test="workFlow.id != null">
				<td align="center">
						<SPAN style="display:block" id="infoSpan_<s:property value='#s.count'/>">
						<s:if test="workFlow.workFlowApproverType=='leaverequest'">请假超过</s:if>
						<s:else>当月累计加班超过</s:else>
						<input size="6" maxlength="6" onkeypress="hrm.common.isHrmNumber('<s:property value="workFlow.workFlowLimit"/>',false,true);" id="limit_<s:property value='#s.count'/>" name="workFlow<s:property value="#s.count"/>.workFlowLimit" value='<s:property value="workFlow.workFlowLimit"/>'/>
						<s:if test="workFlow.workFlowApproverType=='leaverequest'">天需上级审批</s:if>
						<s:else>小时需上级审批</s:else></SPAN>
						<script type="text/javascript">
							var lt = '<s:property value="workFlow.workFlowLimit"/>';
							var count = '<s:property value="#s.count"/>';
							if(lt ==0){
								document.getElementById("infoSpan_"+count).style.display="none";
							}
						</script>
				</td>
				</s:if>
				<s:else>
				<td align="center">
						<SPAN style="display:none" id="infoSpan_<s:property value='#s.count'/>">
						<s:if test="workFlow.workFlowApproverType=='leaverequest'">请假超过</s:if>
						<s:else>当月累计加班超过</s:else>
						<input size="6" maxlength="6" onkeypress="hrm.common.isHrmNumber('<s:property value="workFlow.workFlowLimit"/>',false,true);" id="limit_<s:property value='#s.count'/>" name="workFlow<s:property value="#s.count"/>.workFlowLimit" value='0'/>
						<s:if test="workFlow.workFlowApproverType=='leaverequest'">天需上级审批</s:if>
						<s:else>小时需上级审批</s:else>
						</SPAN>
				</td>
				</s:else>	
			</tr>
		</s:iterator>
			<tr>
				<td align="center" colspan="6">
					<input type="button" value="确定" onclick="update();">
				</td>
			</tr>
		</s:if><s:else>
			<tr>
				<td colspan="5" align="center">无相关记录！</td>
			</tr>
		</s:else>
	</table>
</form>
<script type="text/javascript">
//选择审批权限(select下拉框)
function doChange(count,select){ //prototype-->jquery
	var ind;
	for(var i=0;i<select.options.length;i++){
		if(select.options[i].selected){
			ind = select.options[i].value;
		}
	}
	document.getElementById('indValue_'+count).value=ind;
	switch(ind){
		case '2':
			$('#infoSpan_'+count).hide();
			break;
		default:
			$('#infoSpan_'+count).show();
			//无条件审批，limit设置为0
			if(ind == '3'|| ind =='0'){
				$('#infoSpan_'+count).hide();
			}
			break;
	}
}

function update(){
	var indValue1 = $('#indValue_1').val();
	if(indValue1 == '1'){
		if(!validate($('#limit_1').val())) return;
	}else{
		$('#limit_1').val(0);
	}
	var indValue2 = $('#indValue_2').val();
	if(indValue2 == '1'){
		if(!validate($('#limit_2').val())) return;
	}else{
		$('#limit_2').val(0);
	}
	document.getElementById('searchForm').submit();
}

//数据校验
function validate(limit){
	if(!hrm.common.isNotEmpty(limit)){  //isEmpty改调common方法
		alert('审批额度不能为空！');
		return false;
	}
	if(isNaN(limit)){
		alert('非法字符，请输入数字！');
		return false;
	}
	if(limit < 0){
		alert('必须输入大于0的数字！');
		return false;
	}
	return true;
}
</script>
</body>
</html>