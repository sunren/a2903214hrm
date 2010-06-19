<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>

<head>
	<script type="text/javascript" src="../resource/js/training/training.js"></script>
	<script type="text/javascript" src="../resource/js/tmp.js"></script>	
	</head>
<body onload="check_order();">
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'课程设置'" />
		<ww:param name="helpUrl" value="'55'" />
	</ww:component>

	<ww:form id="trcForm" name="trcForm" action="trcourseConfig" namespace="/training" method="POST">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<ww:textfield label="课程名称" id="trcName" name="trc.trcName" size="16" maxlength="10" />								
								<ww:select id="trcType" label="课程培训种类" name="trc.trcType.trtNo" list="trTypeList" listKey="trtNo" listValue="trtName" emptyOption="true" required="false" />
								<td>课程状态：<ww:select id="trcStatus" name="trc.trcStatus" list="#{1:'允许开课', 0:'关闭'}" emptyOption="true"></ww:select></td>	
							</tr>
						</table>
					</td>
					<td>
						<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"   value="查询">
						<input title="[Alt+C]" accesskey="C" class="button" type="button" onClick="window.location='trcourseConfig.action';" value="重置">
						<br> 
					</td>
				</tr>
			</table>
		
			<p>
				&nbsp;
			</p>
		<input class="button" type="button" onClick="window.location='trcAddInit.action';" name="createPlan" value="新增课程">
 
		<table id="trainingplantable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
			<ww:hidden id="order" name="page.order" />
			<ww:hidden id="operate" name="page.operate" />
			<ww:if test="page.isSplit()">
			<ww:if test="page.totalPages>1">
				<tr>
					<td colspan="10" align="right" class="listViewPaginationTdS1">
						<ww:hidden name="page.currentPage" />
						<a href="#" onclick="splits('first', 'trcForm');"><img
								src='../resource/images/start.gif' width='11' height='9'
								alt='开始'>开始</a>
						<a href="#" onclick="splits('previous', 'trcForm');"><img
								src='../resource/images/previous.gif' width='6' height='9'
								alt='上页'>上页</a> （
						<ww:property value="page.currentPage+'/'+page.totalPages" />
						页｜共
						<ww:property value="page.totalRows" />
						条）
						<a href="#" onclick="splits('next', 'trcForm');">下页<img
								src='../resource/images/next.gif' width='6' height='9'></a>
						<a href="#" onclick="splits('last','trcForm');">最后<img
								src='../resource/images/end.gif' width='11' height='9' alt='最后'>
						</a>
					</td>
				</tr>
			</ww:if>
			</ww:if>
				<tr>
				<th><a href="#" onclick="order_submit('trcName', 'trcForm');">课程名称</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcName_img'></th>
				<th><a href="#" onclick="order_submit('trcType.trtNo', 'trcForm');">培训种类</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcType.trtNo_img'></th>			
				<th><a href="#" onclick="order_submit('trcLastChangeBy.id', 'trcForm');">最后修改者</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcLastChangeBy.id_img'></th>				
				<th><a href="#" onclick="order_submit('trcLastChangeTime', 'trcForm');">最后修改时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcLastChangeTime_img'></th>
				<th><a href="#" onclick="order_submit('trcStatus', 'trcForm');">状态</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='trcStatus_img'></th>
				<th>操作</th>	
			</tr>
			<ww:if test="!trcList.isEmpty()">
				<ww:iterator value="trcList" status="index">
						<tr>
								<td><a href="viewTrcTrcpInit.action?trcNo=<ww:property value="trcNo" />" class="listViewTdLinkS1">
									<ty:text counts="17"><ww:property value="trcName" /></ty:text></a></td>
								<td><ww:property value="trcType.trtName" /></td>
								<td><ww:property value="trcLastChangeBy.empName" /></td>
								<td><ww:date name="trcLastChangeTime" format="yyyy-MM-dd" /></td>
								<td>
									<ww:if test="trcStatus == 1">允许开课<a href="#" onclick="redirectPrompt('closeTrc.action?trcNo=<ww:property value='trcNo'/>', '您确定要关闭该课程吗？')"><img src="../resource/images/unpublish_inline.gif" alt='关闭' border='0' /></a>
									</ww:if>
									<ww:else>关闭<a href="#" onclick="redirectPrompt('trcOpen.action?trcNo=<ww:property value='trcNo'/>', '您确定要开放该课程吗？')"><img src="../resource/images/unpublish_inline.gif" alt='开放' border='0' /></a>									
									</ww:else>																
								</td>
								<td align="center">
									<a href="updateTrcInit.action?trcNo=<ww:property value="trcNo" />"><img src="../resource/images/edit.gif" alt='修改' border='0' /></a>
									<a href="#" onclick="redirectPrompt('deleteTrc.action?trcNo=<ww:property value="trcNo" />', '您确定要删除该课程吗？')"><img src="../resource/images/delete.gif" alt='删除' border='0' /></a>					
							
				</td>
			</tr>
			</ww:iterator>
		</ww:if>
		<ww:else>
			<tr>
				<!-- 不存在符合条件的培训课程！ -->
				<td align="center" colspan="11">
					不存在符合条件的培训课程!
				</td>
			</tr>
		</ww:else>
		</table>		
		</ww:form>
</body>
