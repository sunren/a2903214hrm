<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>

<table id="attend_monthly_table" style="border-top:0px" cellpadding="0" cellspacing="0" border="0" class="gridtableList">
	     <tr>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('emp.empDistinctNo');">
	     		����<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empDistinctNo_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('emp.empName');">
	     		����<img src='../resource/images/arrow_.gif' width='8' height='10' id='emp.empName_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('empOrgDept.departmentName');">
	     		����<img src='../resource/images/arrow_.gif' width='8' height='10' id='empOrgDept.departmentName_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmDutyDays');">
	     		ȫ��(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmDutyDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmOnDutyDays');">
	     		����(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmOnDutyDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmOffDutyDays');">
	     		ȱ��(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmOffDutyDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLateTimes');">
	     		�ٵ�(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLateTimes_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmEarlyLeave');">
	     		����(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmEarlyLeave_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmAbsentDays');">
	     		����(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmAbsentDays_img'></a>
	     		</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveDays');">
	     		���(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveAnnualDays');">
	     		���(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveAnnualDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveCasualDays');">
	     		�¼�(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveCasualDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveSickDays');">
	     		����(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveSickDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveSick01Days');">
	     		����סԺ(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveSick01Days_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveSick02Days');">
	     		��н����(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveSick02Days_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveWeddingDays');">
	     		���(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveWeddingDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveMaternityDays');">
	     		����(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveMaternityDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveTravelDays');">
	     		����(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveTravelDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveOuterDays');">
	     		�����(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveOuterDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveTiaoxiuDays');">
	     		����(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveTiaoXiuHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveTiaoxiu01Days');">
	     		���ݹ���(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveTiaoxiu01Days_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveOtherDays');">
	     		�������(��)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveOtherDays_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmOvertimeHours');">
	     		�Ӱ�(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmOvertimeHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmOtNormalHours');">
	     		�ճ��Ӱ�(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmOtNormalHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmOtWeekendHours');">
	     		��ĩ�Ӱ�(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmOtWeekendHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmOtHolidayHours');">
	     		�ڼ��ռӰ�(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmOtHolidayHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">����<s:hidden id="listsize" name="listsize" value="%{salaryPaidList.size}" />
	 		<s:hidden id="thisyear" name="thisyear" value="%{year}" /><s:hidden id="thismonth" name="thismonth" value="%{month}" />
	     	</th>
	     </tr>
	     <s:if test="!attendmonthlyList.isEmpty()">
	     	<s:iterator value="attendmonthlyList" status="index">
	     		<tr>
	     			<s:hidden id="%{'empName'+(#index.count)}" name="hiddenName" value="%{attmEmpId.empName}"/>
	     			<s:hidden id="%{'empId'+(#index.count)}" name="hiddenName" value="%{attmEmpId.id}"/>
	     			<s:hidden id="%{'id'+(#index.count)}" name="hiddenName" value="%{id}"/>
					<s:hidden id="%{'comments'+(#index.count)}" name="hiddenName" value="%{attmComments}"/>
	     			
					<%--Ա����Ϣ�������--%>
					<td id="empDistinctNo<s:property value="#index.count"/>" align="center" nowrap>
						<s:property value="attmEmpId.empDistinctNo"/>
					</td>
					<td nowrap="nowrap" id="name<s:property value="#index.count"/>">
						<span 
TITLE="Ա����ţ�<s:property value='attmEmpId.empDistinctNo'/>
�ù���ʽ��<s:property value='%{getEmpType(attmEmpId.empType.id)}'/>
����������<s:property value='%{getLocName(attmEmpId.empLocationNo.id)}'/> 
�������ţ�<s:property value='%{getDepName(attmEmpId.empDeptNo.id)}'/> 
<s:if test="buNo!=''">ҵ��Ԫ��<s:property value='%{getBuName(attmEmpId.buNo)}'/></s:if>" />
						&nbsp;
						<ty:auth auths="401 or 411,3 or 411,2">
						    <a href="#" class="listViewTdLinkS1" onclick=dwrAttendDailyMemory("<s:property value='attmEmpId.id'/>","<s:property value='attmEmpId.empName'/>")><s:property value="attmEmpId.empName" /></a>
						</ty:auth>
					</td>
					<td id="empDeptNameo<s:property value="#index.count"/>" align="center" nowrap="nowrap">
						<s:property value="attmEmpId.empDeptNo.departmentName"/>
					</td>		
					<td id="dutyDaysTD<s:property value="#index.count"/>" align="right" nowrap="nowrap">
						<span id="dutyDays<s:property value="#index.count"/>">
						<s:property value="formatBD(attmDutyDays)" />
						</span>
						<ty:auth auths="401,3 or 401,2">
						<s:if test="status==0">
						<img id="searchImg<s:property value="#index.count"/>" src="../resource/images/Search.gif" alt="�鿴��ϸ" 
							onclick="myshowconfigDiv('<s:property value="#index.count"/>');" border="0" style="CURSOR: pointer" >
						</s:if>
						</ty:auth>
					</td> 
					<td id="onDutyDaysTD<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmOnDutyDays)" />
					</td>
					<td id="offDutyDays<s:property value="#index.count"/>" align="right">
						<s:property value="formatBD(attmOffDutyDays)"/>
					</td>
					<td id="lateTime<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLateTimes)"/>
					</td> 
					<td id="EarlyLeaveTime<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmEarlyLeave)"/>	
					</td> 
					<td id="AbsentDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmAbsentDays)"/>
					</td>
					<td id="LeaveDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveDays)"/>
					</td>
					<td id="LeaveAnnualDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveAnnualDays)"/>
					</td>
					<td id="LeaveCasualDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveCasualDays)"/>
					</td>
					<td id="LeaveSickDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveSickDays)"/>
					</td>
					<td id="LeaveSick01Days<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveSick01Days)"/>
					</td>
					<td id="LeaveSick02Days<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveSick02Days)"/>
					</td>
					<td id="LeaveWeddingDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveWeddingDays)"/>
					</td>
					<td id="MaternityDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveMaternityDays)"/>
					</td>
					<td id="LeaveTravelDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveTravelDays)"/>
					</td>
					<td id="LeaveOuterDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveOuterDays)"/>
					</td>
					<td id="LeaveTiaoxiuDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveTiaoxiuDays)"/>
					</td>
					<td id="LeaveTiaoxiu01Days<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveTiaoxiu01Days)"/>
					</td>
					<td id="LeaveOtherDays<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveOtherDays)"/>
					</td>
					<td id="otHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmOvertimeHours)"/>
					</td>
					<td id="OtNormalHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmOtNormalHours)"/>
					</td>
					<td id="OtWeekendHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmOtWeekendHours)"/>
					</td>
					<td id="OtHolidayHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmOtHolidayHours)"/>
					</td>
					<td align="center" id="empOperation<s:property value="#index.count"/>">
					<ty:auth auths="401,3 or 401,2">
						<s:if test="id!=null&&id!=''">
							<a href="#" onclick="delEmpMonthlyRecords('<s:property value="#index.count"/>','<s:property value="id"/>');"><img src="../resource/images/deletesalaryconf.gif" alt='ɾ��'  border='0'/></a>
						</s:if>
					</ty:auth>
					</td>   
			</tr>
	     	</s:iterator>
	     </s:if>
		<s:else>
			<tr><!-- ������Ա����н��Ϣ -->
				<td colspan="13" align="center">	
					<table height="300">
						<tr>
							<td>�޷��������Ŀ��ڼ�¼!</td>
						</tr>
						<s:if test="period==null">
							<ty:auth auths="401,3 or 401,2">
								<script type="text/javascript" language="javascript">
									var year = document.getElementById('year').value;
									var month = document.getElementById('month').value;
									if(confirm(year+"��"+month+"�µĻ������ݲ����ڣ��Ƿ�Ҫ���ܣ�")){
										document.searchAttendmonthly.action = "calDailyToAttendmonthly.action";
										document.searchAttendmonthly.submit();
									}
								</script>
							</ty:auth>
						</s:if>
					</table>		
				</td>
			</tr>
		</s:else>
</table>

<!-- ������Ϣ����(����) -->
<div id="dlgExaminDayInfo"  style="width:430px;display:none;">
	    <input id="div_empid" type="hidden" name="hiddenName"/>
	    <input id="div_attdId" type="hidden" name="hiddenName"/>
		<input id="div_rowID" type="hidden" name="hiddenName" />

	<div id="change_stutus_error" class="prompt_div_err"></div>
		<table id="newConfigTable" width="100%"  border="0" cellspacing="0" cellpadding="0" class="basictable" >
			<tr>
				<td>ȫ��(��)��</td>
				<td><input type="text" id="div_duty_days" name="div_duty_days" value="" size="5" maxlength="5"="4" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>���(��)��</td>
				<td><input type="text" id="div_annual_leave_days" name="div_annual_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>����(��)��</td>
				<td><input type="text" id="div_on_duty" name="div_on_duty" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>�¼�(��)��</td>
				<td><input type="text" id="div_casual_leave_days" name="div_casual_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>ȱ��(��)��</td>
				<td><input type="text" id="div_off_duty" name="div_off_duty" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>����(��)��</td>
				<td><input type="text" id="div_sick_leave_days" name="div_sick_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
		    </tr>
			<tr>
				<td>����(��)��</td>
				<td><input type="text" id="div_absent" name="div_absent" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>����סԺ(��)��</td>
				<td><input type="text" id="div_sick01_leave_days" name="div_sick01_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>����סԺ(��)��</td>
				<td><input type="text" id="div_sick02_leave_days" name="div_sick02_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>���ݹ���(��)��</td>
				<td><input type="text" id="div_tiaoxiu01_leave_days" name="div_tiaoxiu01_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>�ٵ�(��)��</td>
				<td><input type="text" id="div_late_time" name="div_late_times" value="" size="5" maxlength="5" onkeydown="MKeyIsNumber(this);" style='text-align:right'></td>
				<td>���(��)��</td>
				<td><input type="text" id="div_wedding_leave_days" name="div_wedding_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>����(��)��</td>
				<td><input type="text" id="div_earlyleave_time" name="div_earlyleave_time" value="" size="5" maxlength="3" onkeydown="MKeyIsNumber(this);" style='text-align:right'></td>
				<td>����(��)��</td>
				<td><input type="text" id="div_maternity_leave_days" name="div_maternity_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>�ճ��Ӱࣺ</td>
				<td><input type="text" id="div_normal_ot_hours" name="div_normal_ot_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>	
				<td>����(��)��</td>
				<td><input type="text" id="div_travel_leave_days" name="div_travel_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>��ĩ�Ӱࣺ</td>
				<td><input type="text" id="div_weekend_ot_hours" name="div_weekend_ot_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>�����(��)��</td>
				<td><input type="text" id="div_outer_leave_days" name="div_outer_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>�ڼ��ռӰࣺ</td>
				<td><input type="text" id="div_holiday_ot_hours" name="div_holiday_ot_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
		    	<td>����(��)��</td>
				<td><input type="text" id="div_tiaoxiu_leave_days" name="div_tiaoxiu_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td>�������(��)��</td>
				<td><input type="text" id="div_other_leave_days" name="div_other_leave_days" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<s:iterator value="attdConfList" status="index">
				<s:if test="#index.odd"><tr></s:if>
				<td><s:property value="eadcFieldName"/>��</td>
				<td>
					<s:if test="eadcFieldType=='number'">
						<input type="text" name="div_field<s:property value="eadcSeq"/>" 
						maxLength="15" size="5" id="div_field<s:property value="eadcSeq"/>" value="">
					</s:if>
				</td>
				<s:if test="#index.last && #index.odd"><td/><td/></s:if>
				<s:if test="#index.even"></tr></s:if>
			</s:iterator>
			<tr>
				<td align="left" align="center">��ע��</td>
				<td align="left" colspan="3" align="center">
					<input type="text" name="div_comments" id="div_comments" size="37" maxLength="127" value="">
				</td>
			</tr>
			<tr>
				<td align="center" colspan="4" align="center">
					<input id="div_btnSubmit" name="hiddenName" type="button" onclick="saveAttend();" value="����">
					<input type="button" name="hiddenName" onclick="hrm.common.closeDialog('dlgExaminDayInfo')" value="�ر�">
				</td>
			</tr>
		</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;width: 430px; height: 120px; " frameborder="0"></iframe>
</div>
<script type="text/javascript">
/*****************************************************
 * ���һ��ÿ�¿��ڼ�¼ʱ���ڵ���������ʾ�ü�¼����ϸ
 * ���� �� rowId ��¼�к�
 * ����ֵ�� ��
 *****************************************************
 */
function myshowconfigDiv(rowId){
	var empname = document.getElementById('empName'+rowId).value;
	//document.getElementById("div_h3").innerHTML="�޸�Ա��"+empname+document.getElementById("attendDate").value+"�Ŀ��ڻ�����Ϣ";
	var id = document.getElementById("id"+rowId).value;
	document.getElementById("div_rowID").value = rowId;//ÿ�¿��ڼ�¼UUID
	document.getElementById("div_empid").value = document.getElementById('empId'+rowId).value;//Ա��ID
	document.getElementById("div_comments").value = document.getElementById('comments'+rowId).value;//��ע

	//dwr���ã�����ÿ�¿��ڼ�¼ID��ȡ��¼��ϸ
	if(id!='') {
		DwrForAttend.getEmpAttendmonthly(id,callBack);
	}
	function callBack(msg){
		if(msg.ERROR){
			errMsg("errMsg",msg.ERROR);
		}else{
			var data = msg.SUCCESS;
			setFieldValue("div_duty_days",data.attmDutyDays);//����Ӧ��������
			setFieldValue("div_on_duty",data.attmOnDutyDays);//����ʵ�ʳ�������
			setFieldValue("div_off_duty",data.attmOffDutyDays);//����ȱ������
			setFieldValue("div_late_time",data.attmLateTimes);//�ٵ�����
			setFieldValue("div_earlyleave_time",data.attmEarlyLeave);//����ʱ���1����
			setFieldValue("div_absent",data.attmAbsentDays);//��������
			setFieldValue("div_annual_leave_days",data.attmLeaveAnnualDays);//���
			setFieldValue("div_tiaoxiu_leave_days",data.attmLeaveTiaoxiuDays);//���ݼ�����
			setFieldValue("div_tiaoxiu01_leave_days",data.attmLeaveTiaoxiu01Days);//���ݼٹ�������
			setFieldValue("div_normal_ot_hours",data.attmOtNormalHours);//�ճ��Ӱ�����
			setFieldValue("div_casual_leave_days",data.attmLeaveCasualDays);//�¼�����
			setFieldValue("div_weekend_ot_hours",data.attmOtWeekendHours);//��ĩ�Ӱ�����
			setFieldValue("div_sick_leave_days",data.attmLeaveSickDays);//��������
			setFieldValue("div_sick01_leave_days",data.attmLeaveSick01Days);//����סԺ����
			setFieldValue("div_sick02_leave_days",data.attmLeaveSick02Days);//��н��������
			setFieldValue("div_holiday_ot_hours",data.attmOtHolidayHours);//�ڼ��ռӰ�����
			setFieldValue("div_wedding_leave_days",data.attmLeaveWeddingDays);//�������
			setFieldValue("div_maternity_leave_days",data.attmLeaveMaternityDays);//��������
			setFieldValue("div_travel_leave_days",data.attmLeaveTravelDays);//��������
			setFieldValue("div_outer_leave_days",data.attmLeaveOuterDays);//���������
			setFieldValue("div_other_leave_days",data.attmLeaveOtherDays);//�����������
			setFieldValue("div_comments",data.attmComments);//��ע
			//�Զ����ֶθ�ֵ
			for(var i=1 ; i<=24; i++){
				var tmp = i > 9?(i+""):("0"+i);
				if(document.getElementById("div_field"+i)!=null){
					setFieldValue("div_field"+i,data['attmField'+tmp]);
				}
			}
		}
	}
	//document.getElementById('dlgExaminDayInfo').style.display='block';
	$("#dlgExaminDayInfo").dialog("option","title","�޸�Ա��"+empname+document.getElementById("attendDate").value+"�Ŀ��ڻ�����Ϣ");
	hrm.common.openDialog('dlgExaminDayInfo');
}

/*****************************************************
 * ��ʾԱ��һ����ÿ�տ���������ϸ
 * ������id Ա�����
 * ������empName Ա������
 * ����ֵ�� ��
 *****************************************************
 */
function dwrAttendDailyMemory(id,empName){
    //alert('dwrAttendDailyMemory');
	var year = document.getElementById('year').value;
	var month = document.getElementById('month').value;
	removeContents();//�������
	
	DwrForAttend.getAttendDailyMemory(id,year,month,memoryCallBack);
	function memoryCallBack(msg){
		if(msg.ERROR){
			errMsg("errMsg",msg.ERROR);
			return;
		}
		var data = msg.SUCCESS;
		var size = data.length;
		for(var i = 0; i < size; i++){
			var tr = createTr(data[i].displayColor);
			var td1= createDateTd(data[i].examinDate);
			tr.appendChild(td1);//����
			var td2 = createTd(data[i].shiftName);
			tr.appendChild(td2);//���
			var td3 =  createTimeTd(data[i].onDutyTime);
			tr.appendChild(td3);//�ϰ�
			var td4 =  createTimeTd(data[i].offDutyTime);
			tr.appendChild(td4);//�°�
			var td5 = createTd(data[i].oughtDutyDays);
			tr.appendChild(td5);//Ӧ����
			var td6 = createTd(data[i].lateMinutes);
			tr.appendChild(td6);//�ٵ�
			var td7 = createTd(data[i].earlyMinutes);
			tr.appendChild(td7);//����
			var td8 = createTd(data[i].absentDays);
			tr.appendChild(td8);//ȱ��
			var td9 = createTd(data[i].leaveDays);
			tr.appendChild(td9);//���
			var td10 = createTd(data[i].overtimeHours);
			tr.appendChild(td10);//�Ӱ�
			var td11 = createTd(data[i].comments);
			tr.appendChild(td11);//����
			
			document.getElementById('memoryDataArea').appendChild(tr);
		}
		//document.getElementById('div_h4').innerHTML = empName+year+"��"+month+"�µĿ�����ϸ";
		$('#dlgExaminDetail').dialog("option","title",empName+year+'��'+month+'�µĿ�����ϸ');
		//document.getElementById('examinDetail').style.display="block";
		hrm.common.openDialog('dlgExaminDetail');
	}
}

/*****************************************************
 * �����һ��ÿ�¿��ڼ�¼���޸�
 * ���� �� rowId ��¼�к�
 * ����ֵ�� ��
 *****************************************************
 */
function saveAttend(){
	var id = document.getElementById("div_attdId").value;
	var empId = document.getElementById("div_empid").value;

	if(document.getElementById("div_duty_days").value==""||document.getElementById("div_duty_days").value==null){
		alert("ȫ����������Ϊ�գ�");
		document.getElementById("div_duty_days").focus();
		return;
	}
	if(document.getElementById("div_on_duty").value==""||document.getElementById("div_on_duty").value==null){
		alert("������������Ϊ�գ�");
		document.getElementById("div_on_duty").focus();
		return;
	}
	if(document.getElementById("div_off_duty").value==""||document.getElementById("div_off_duty").value==null){
		alert("ȱ����������Ϊ�գ�");
		document.getElementById("div_off_duty").focus();
		return;
	}
	for(var i = 1; i <= 24; i++){//�Զ����ֶ���֤��ֻ��Ϊ��������
		if(document.getElementById("div_field"+i) && document.getElementById("div_field"+i).value != ''){
			if(!/^\d+(\.\d{1,2})?$/.test(document.getElementById("div_field"+i).value)){
				alert('�Ƿ�����������,С��λ�������λ');
				document.getElementById("div_field"+i).focus();
				return;
			}
		}
	}
	params = 
      {
        id: id,
		attmYear: document.getElementById("year").value, 
        attmMonth: document.getElementById("month").value, 
        attmYearmonth: document.getElementById("year").value + document.getElementById("month").value,
        attmDutyDays: document.getElementById("div_duty_days").value, 
        attmOnDutyDays: document.getElementById("div_on_duty").value, 
        attmOffDutyDays: document.getElementById("div_off_duty").value,
        attmLateTimes: document.getElementById("div_late_time").value, 
        attmEarlyLeave: document.getElementById("div_earlyleave_time").value,
        attmOtNormalHours: document.getElementById("div_normal_ot_hours").value,
        attmAbsentDays:document.getElementById("div_absent").value,
        attmOtWeekendHours: document.getElementById("div_weekend_ot_hours").value, 
        attmOtHolidayHours: document.getElementById("div_holiday_ot_hours").value,
        attmLeaveAnnualDays: document.getElementById("div_annual_leave_days").value,
        attmLeaveTiaoxiuDays: document.getElementById("div_tiaoxiu_leave_days").value,
        attmLeaveTiaoxiu01Days: document.getElementById("div_tiaoxiu01_leave_days").value,
        attmLeaveCasualDays: document.getElementById("div_casual_leave_days").value,
        attmLeaveSickDays: document.getElementById("div_sick_leave_days").value,
        attmLeaveSick01Days: document.getElementById("div_sick01_leave_days").value,
        attmLeaveSick02Days: document.getElementById("div_sick02_leave_days").value,
        attmLeaveOuterDays: document.getElementById("div_outer_leave_days").value,
        attmLeaveWeddingDays: document.getElementById("div_wedding_leave_days").value,
        attmLeaveMaternityDays: document.getElementById("div_maternity_leave_days").value,
        attmLeaveTravelDays: document.getElementById("div_travel_leave_days").value,
        attmLeaveOtherDays: document.getElementById("div_other_leave_days").value,
        attmComments: document.getElementById("div_comments").value,
        attmField01: document.getElementById("div_field1")==null?null:document.getElementById("div_field1").value,
        attmField02: document.getElementById("div_field2")==null?null:document.getElementById("div_field2").value,
        attmField03: document.getElementById("div_field3")==null?null:document.getElementById("div_field3").value,
        attmField04: document.getElementById("div_field4")==null?null:document.getElementById("div_field4").value,
        attmField05: document.getElementById("div_field5")==null?null:document.getElementById("div_field5").value,
        attmField06: document.getElementById("div_field6")==null?null:document.getElementById("div_field6").value,
        attmField07: document.getElementById("div_field7")==null?null:document.getElementById("div_field7").value,
        attmField08: document.getElementById("div_field8")==null?null:document.getElementById("div_field8").value,
        attmField09: document.getElementById("div_field9")==null?null:document.getElementById("div_field9").value,
        attmField10: document.getElementById("div_field10")==null?null:document.getElementById("div_field10").value,
        attmField11: document.getElementById("div_field11")==null?null:document.getElementById("div_field11").value,
        attmField12: document.getElementById("div_field12")==null?null:document.getElementById("div_field12").value,
        attmField13: document.getElementById("div_field13")==null?null:document.getElementById("div_field13").value,
        attmField14: document.getElementById("div_field14")==null?null:document.getElementById("div_field14").value,
        attmField15: document.getElementById("div_field15")==null?null:document.getElementById("div_field15").value,
        attmField16: document.getElementById("div_field16")==null?null:document.getElementById("div_field16").value,   
        attmField17: document.getElementById("div_field17")==null?null:document.getElementById("div_field17").value,
        attmField18: document.getElementById("div_field18")==null?null:document.getElementById("div_field18").value,
        attmField19: document.getElementById("div_field19")==null?null:document.getElementById("div_field19").value,
        attmField20: document.getElementById("div_field20")==null?null:document.getElementById("div_field20").value,
        attmField21: document.getElementById("div_field21")==null?null:document.getElementById("div_field21").value,
        attmField22: document.getElementById("div_field22")==null?null:document.getElementById("div_field22").value,
        attmField23: document.getElementById("div_field23")==null?null:document.getElementById("div_field23").value,
        attmField24: document.getElementById("div_field24")==null?null:document.getElementById("div_field24").value    		
      };

	DwrForAttend.saveEmpAttendmonthly(params,empId,savecallBack);
	function savecallBack(msg){
		if(msg.ERROR){
			errMsg("errMsg",msg.ERROR);
		}else{
			var data = msg.SUCCESS;
			//document.getElementById('dlgExaminDayInfo').style.display='none';
			hrm.common.closeDialog('dlgExaminDayInfo');
			var id = data.id;
			var rowId = document.getElementById("div_rowID").value;
			document.getElementById("id"+rowId).value = id;
			document.getElementById("div_attdId").value = id;

			setTdValue("dutyDays"+rowId,data.attmDutyDays);//ȫ��
			setTdValue("onDutyDaysTD"+rowId,data.attmOnDutyDays);//����
			setTdValue("offDutyDays"+rowId,data.attmOffDutyDays);//ȱ��
			setTdValue("lateTime"+rowId,data.attmLateTimes);//�ٵ�
			setTdValue("EarlyLeaveTime"+rowId,data.attmEarlyLeave);//����
			setTdValue("AbsentDays"+rowId,data.attmAbsentDays);//����
			setTdValue("LeaveDays"+rowId,data.attmLeaveDays);//�����ʱ��
			setTdValue("LeaveAnnualDays"+rowId,data.attmLeaveAnnualDays);//���
			setTdValue("LeaveCasualDays"+rowId,data.attmLeaveCasualDays);//�¼�
			setTdValue("LeaveSickDays"+rowId,data.attmLeaveSickDays);//����
			setTdValue("LeaveSick01Days"+rowId,data.attmLeaveSick01Days);//����סԺ
			setTdValue("LeaveSick02Days"+rowId,data.attmLeaveSick02Days);//����סԺ
			setTdValue("LeaveWeddingDays"+rowId,data.attmLeaveWeddingDays);//���
			setTdValue("MaternityDays"+rowId,data.attmLeaveMaternityDays);//����
			setTdValue("LeaveTravelDays"+rowId,data.attmLeaveTravelDays);//����
			setTdValue("LeaveOuterDays"+rowId,data.attmLeaveOuterDays);//�����
			setTdValue("LeaveTiaoxiuDays"+rowId,data.attmLeaveTiaoxiuDays);//����
			setTdValue("LeaveTiaoxiu01Days"+rowId,data.attmLeaveTiaoxiu01Days);//����
			setTdValue("LeaveOtherDays"+rowId,data.attmLeaveOtherDays);//�������
			//setTdValue("comments"+rowId,data.attmComments);//��ע
			setTdValue("OtNormalHours"+rowId,data.attmOtNormalHours);//��ͨ�Ӱ�
			setTdValue("OtWeekendHours"+rowId,data.attmOtWeekendHours);//��ĩ�Ӱ�
			setTdValue("OtHolidayHours"+rowId,data.attmOtHolidayHours);//�ڼ��ռӰ�
			setTdValue("otHours"+rowId,data.attmOvertimeHours);//�Ӱ���ʱ��
			
			successMsg("errMsg","�����ɹ���");
		}
		
	}
}
</script>
