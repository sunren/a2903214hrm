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
	     		<a href="#" onclick="HRMCommon.order_submit('attmDutyHours');">
	     		ȫ��(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmDutyHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmOnDutyHours');">
	     		����(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmOnDutyHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmOffDutyHours');">
	     		ȱ��(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmOffDutyHours_img'></a>
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
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveHours');">
	     		���(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveAnnualHours');">
	     		���(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveAnnualHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveCasualHours');">
	     		�¼�(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveCasualHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveSickHours');">
	     		����(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveSickHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveSick01Hours');">
	     		����סԺ(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveSick01Hours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveSick02Hours');">
	     		��н����(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveSick02Hours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveWeddingHours');">
	     		���(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveWeddingHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveMaternityHours');">
	     		����(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveMaternityHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveTravelHours');">
	     		����(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveTravelHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveOuterHours');">
	     		�����(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveOuterHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveTiaoxiuHours');">
	     		����(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveTiaoxiuHours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveTiaoxiu01Hours');">
	     		���ݹ���(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveTiaoxiu01Hours_img'></a>
	     	</th>
	     	<th nowrap="nowrap">
	     		<a href="#" onclick="HRMCommon.order_submit('attmLeaveOtherHours');">
	     		�������(Сʱ)<img src='../resource/images/arrow_.gif' width='8' height='10' id='attmLeaveOtherHours_img'></a>
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
					<td id="empDeptNameo<s:property value="#index.count"/>" align="center" nowrap>
						<s:property value="attmEmpId.empDeptNo.departmentName"/>
					</td>		
					<td id="dutyHoursTD<s:property value="#index.count"/>" align="right" nowrap="nowrap">
						<span id="dutyHours<s:property value="#index.count"/>">
						<s:property value="formatBD(attmDutyHours)" />
						</span>
						<ty:auth auths="401,3 or 401,2">
						<s:if test="status==0">
						<img id="searchImg<s:property value="#index.count"/>" src="../resource/images/Search.gif" alt="�鿴��ϸ" 
							onclick="myshowconfigDiv('<s:property value="#index.count"/>');" border="0" style="CURSOR: pointer" >
						</s:if>
						</ty:auth>
					</td> 
					<td id="onDutyHoursTD<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmOnDutyHours)" />
					</td>
					<td id="offDutyHours<s:property value="#index.count"/>" align="right">
						<s:property value="formatBD(attmOffDutyHours)"/>
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
					<td id="LeaveHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveHours)"/>
					</td>
					<td id="LeaveAnnualHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveAnnualHours)"/>
					</td>
					<td id="LeaveCasualHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveCasualHours)"/>
					</td>
					<td id="LeaveSickHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveSickHours)"/>
					</td>
					<td id="LeaveSick01Hours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveSick01Hours)"/>
					</td>
					<td id="LeaveSick02Hours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveSick02Hours)"/>
					</td>
					<td id="LeaveWeddingHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveWeddingHours)"/>
					</td>
					<td id="MaternityHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveMaternityHours)"/>
					</td>
					<td id="LeaveTravelHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveTravelHours)"/>
					</td>
					<td id="LeaveOuterHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveOuterHours)"/>
					</td>
					<td id="LeaveTiaoxiuHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveTiaoxiuHours)"/>
					</td>
					<td id="LeaveTiaoxiu01Hours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveTiaoxiu01Hours)"/>
					</td>
					<td id="LeaveOtherHours<s:property value="#index.count"/>" align="right">
						&nbsp;<s:property value="formatBD(attmLeaveOtherHours)"/>
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

<!-- ������Ϣ����(��Сʱ) --> 
<div id="dlgExaminHourInfo" style="width:430px;display:none;"  >	 
	<input id="div_empid" type="hidden" name="hiddenName"/>
	<input id="div_attdId" type="hidden" name="hiddenName"/>
	<input id="div_rowID" type="hidden" name="hiddenName"  />
	<div id="change_stutus_error" class="prompt_div_err"></div>
		<table id="newConfigTable" width="100%"  border="0" cellspacing="0" cellpadding="0" class="basictable" >
			<tr>
				<td>ȫ��(Сʱ)��</td>
				<td><input type="text" id="div_duty_hours" name="div_duty_hours" value="" size="5" maxlength="5"="4" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>���(Сʱ)��</td>
				<td><input type="text" id="div_annual_leave_hours" name="div_annual_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>����(Сʱ)��</td>
				<td><input type="text" id="div_on_duty" name="div_on_duty" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>�¼�(Сʱ)��</td>
				<td><input type="text" id="div_casual_leave_hours" name="div_casual_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>ȱ��(Сʱ)��</td>
				<td><input type="text" id="div_off_duty" name="div_off_duty" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>����(Сʱ)��</td>
				<td><input type="text" id="div_sick_leave_hours" name="div_sick_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
		    </tr>
			<tr>
				<td>����(��)��</td>
				<td><input type="text" id="div_absent" name="div_absent" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>����סԺ(Сʱ)��</td>
				<td><input type="text" id="div_sick01_leave_hours" name="div_sick01_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>����סԺ(Сʱ)��</td>
				<td><input type="text" id="div_sick02_leave_hours" name="div_sick02_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>���ݹ���(Сʱ)��</td>
				<td><input type="text" id="div_tiaoxiu01_leave_hours" name="div_tiaoxiu01_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>�ٵ�(��)��</td>
				<td><input type="text" id="div_late_time" name="div_late_times" value="" size="5" maxlength="5" onkeydown="MKeyIsNumber(this);" style='text-align:right'></td>
				<td>���(Сʱ)��</td>
				<td><input type="text" id="div_wedding_leave_hours" name="div_wedding_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>����(��)��</td>
				<td><input type="text" id="div_earlyleave_time" name="div_earlyleave_time" value="" size="5" maxlength="3" onkeydown="MKeyIsNumber(this);" style='text-align:right'></td>
				<td>����(Сʱ)��</td>
				<td><input type="text" id="div_maternity_leave_hours" name="div_maternity_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>�ճ��Ӱࣺ</td>
				<td><input type="text" id="div_normal_ot_hours" name="div_normal_ot_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>	
				<td>����(Сʱ)��</td>
				<td><input type="text" id="div_travel_leave_hours" name="div_travel_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>��ĩ�Ӱࣺ</td>
				<td><input type="text" id="div_weekend_ot_hours" name="div_weekend_ot_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
				<td>�����(Сʱ)��</td>
				<td><input type="text" id="div_outer_leave_hours" name="div_outer_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td>�ڼ��ռӰࣺ</td>
				<td><input type="text" id="div_holiday_ot_hours" name="div_holiday_ot_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
		    	<td>����(Сʱ)��</td>
				<td><input type="text" id="div_tiaoxiu_leave_hours" name="div_tiaoxiu_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td>�������(Сʱ)��</td>
				<td><input type="text" id="div_other_leave_hours" name="div_other_leave_hours" value="" size="5" maxlength="5" onkeydown="MKeyIsNumberOrDot(this);" style='text-align:right'></td>
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
					<input type="button" name="hiddenName" onclick="hrm.common.closeDialog('dlgExaminHourInfo')" value="�ر�">
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
function myshowconfigDiv(rowId){ //����Ŵ󾵰�ť����Dialog
    //alert('myshowconfigDiv');  
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
			var data = msg.SUCCESS
			setFieldValue("div_duty_hours",data.attmDutyHours);//����Ӧ��������
			setFieldValue("div_on_duty",data.attmOnDutyHours);//����ʵ�ʳ�������
			setFieldValue("div_off_duty",data.attmOffDutyHours);//����ȱ������
			setFieldValue("div_late_time",data.attmLateTimes);//�ٵ�����
			setFieldValue("div_earlyleave_time",data.attmEarlyLeave);//����ʱ���1����
			setFieldValue("div_absent",data.attmAbsentDays);//��������
			setFieldValue("div_annual_leave_hours",data.attmLeaveAnnualHours);//���
			setFieldValue("div_tiaoxiu_leave_hours",data.attmLeaveTiaoxiuHours);//����Сʱ��
			setFieldValue("div_tiaoxiu01_leave_hours",data.attmLeaveTiaoxiuHours);//���ݼٹ���Сʱ��
			setFieldValue("div_normal_ot_hours",data.attmOtNormalHours);//�ճ��Ӱ�Сʱ��
			setFieldValue("div_casual_leave_hours",data.attmLeaveCasualHours);//�¼�����
			setFieldValue("div_weekend_ot_hours",data.attmOtWeekendHours);//��ĩ�Ӱ�Сʱ��
			setFieldValue("div_sick_leave_hours",data.attmLeaveSickHours);//����Сʱ��
			setFieldValue("div_sick01_leave_hours",data.attmLeaveSick01Hours);//����סԺСʱ��
			setFieldValue("div_sick02_leave_hours",data.attmLeaveSick02Hours);//��н����Сʱ��
			setFieldValue("div_holiday_ot_hours",data.attmOtHolidayHours);//�ڼ��ռӰ�Сʱ��
			setFieldValue("div_wedding_leave_hours",data.attmLeaveWeddingHours);//���Сʱ��
			setFieldValue("div_maternity_leave_hours",data.attmLeaveMaternityHours);//����Сʱ��
			setFieldValue("div_travel_leave_hours",data.attmLeaveTravelHours);//����Сʱ��
			setFieldValue("div_outer_leave_hours",data.attmLeaveOuterHours);//�����Сʱ��
			setFieldValue("div_other_leave_hours",data.attmLeaveOtherHours);//�������Сʱ��
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
	//document.getElementById('emp_salary_pay_div').style.display='block';
	$("#dlgExaminHourInfo").dialog("option","title","�޸�Ա��"+empname+document.getElementById("attendDate").value+"�Ŀ��ڻ�����Ϣ");
	hrm.common.openDialog('dlgExaminHourInfo');
}

/*****************************************************
 * ��ʾԱ��һ����ÿ�տ���������ϸ
 * ������id Ա�����
 * ������empName Ա������
 * ����ֵ�� ��
 *****************************************************
 */
function dwrAttendDailyMemory(id,empName){ //ҳ���е��Ա������ʱ������
	//alert('dwrAttendDailyMemory11');
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
			var td5 = createTd(data[i].oughtDutyHours);
			tr.appendChild(td5);//Ӧ����
			var td6 = createTd(data[i].lateMinutes);
			tr.appendChild(td6);//�ٵ�
			var td7 = createTd(data[i].earlyMinutes);
			tr.appendChild(td7);//����
			var td8 = createTd(data[i].absentTimes);
			tr.appendChild(td8);//ȱ��
			var td9 = createTd(data[i].leaveHours);
			tr.appendChild(td9);//���
			var td10 = createTd(data[i].overtimeHours);
			tr.appendChild(td10);//�Ӱ�
			var td11 = createTd(data[i].comments);
			tr.appendChild(td11);//����
			
			//��dlgExaminDetail��tbody�����һϵ��tr
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

	if(document.getElementById("div_duty_hours").value==""||document.getElementById("div_duty_hours").value==null){
		alert("ȫ����������Ϊ�գ�");
		document.getElementById("div_duty_hours").focus();
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
        attmDutyHours: document.getElementById("div_duty_hours").value, 
        attmOnDutyHours: document.getElementById("div_on_duty").value, 
        attmOffDutyHours: document.getElementById("div_off_duty").value,
        attmLateTimes: document.getElementById("div_late_time").value, 
        attmEarlyLeave: document.getElementById("div_earlyleave_time").value,
        attmOtNormalHours: document.getElementById("div_normal_ot_hours").value,
        attmAbsentDays:document.getElementById("div_absent").value,
        attmOtWeekendHours: document.getElementById("div_weekend_ot_hours").value, 
        attmOtHolidayHours: document.getElementById("div_holiday_ot_hours").value,
        attmLeaveAnnualHours: document.getElementById("div_annual_leave_hours").value,
        attmLeaveTiaoxiuHours: document.getElementById("div_tiaoxiu_leave_hours").value,
        attmLeaveTiaoxiu01Hours: document.getElementById("div_tiaoxiu01_leave_hours").value,
        attmLeaveCasualHours: document.getElementById("div_casual_leave_hours").value,
        attmLeaveSickHours: document.getElementById("div_sick_leave_hours").value,
        attmLeaveSick01Hours: document.getElementById("div_sick01_leave_hours").value,
        attmLeaveSick02Hours: document.getElementById("div_sick02_leave_hours").value,
        attmLeaveOuterHours: document.getElementById("div_outer_leave_hours").value,
        attmLeaveWeddingHours: document.getElementById("div_wedding_leave_hours").value,
        attmLeaveMaternityHours: document.getElementById("div_maternity_leave_hours").value,
        attmLeaveTravelHours: document.getElementById("div_travel_leave_hours").value,
        attmLeaveOtherHours: document.getElementById("div_other_leave_hours").value,
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
			//document.getElementById('emp_salary_pay_div').style.display='none';
			hrm.common.closeDialog('dlgExaminHourInfo');
			var id = data.id;
			var rowId = document.getElementById("div_rowID").value;
			document.getElementById("id"+rowId).value = id;
			document.getElementById("div_attdId").value = id;

			setTdValue("dutyHours"+rowId,data.attmDutyHours);//ȫ��
			setTdValue("onDutyHoursTD"+rowId,data.attmOnDutyHours);//����
			setTdValue("offDutyHours"+rowId,data.attmOffDutyHours);//ȱ��
			setTdValue("lateTime"+rowId,data.attmLateTimes);//�ٵ�
			setTdValue("EarlyLeaveTime"+rowId,data.attmEarlyLeave);//����
			setTdValue("AbsentDays"+rowId,data.attmAbsentDays);//����
			setTdValue("LeaveHours"+rowId,data.attmLeaveHours);//�����ʱ��
			setTdValue("LeaveAnnualHours"+rowId,data.attmLeaveAnnualHours);//���
			setTdValue("LeaveCasualHours"+rowId,data.attmLeaveCasualHours);//�¼�
			setTdValue("LeaveSickHours"+rowId,data.attmLeaveSickHours);//����
			setTdValue("LeaveSick01Hours"+rowId,data.attmLeaveSick01Hours);//����סԺ
			setTdValue("LeaveSick02Hours"+rowId,data.attmLeaveSick02Hours);//����סԺ
			setTdValue("LeaveWeddingHours"+rowId,data.attmLeaveWeddingHours);//���
			setTdValue("MaternityHours"+rowId,data.attmLeaveMaternityHours);//����
			setTdValue("LeaveTravelHours"+rowId,data.attmLeaveTravelHours);//����
			setTdValue("LeaveOuterHours"+rowId,data.attmLeaveOuterHours);//�����
			setTdValue("LeaveTiaoxiuHours"+rowId,data.attmLeaveTiaoxiuHours);//����
			setTdValue("LeaveTiaoxiu01Hours"+rowId,data.attmLeaveTiaoxiu01Hours);//����
			setTdValue("LeaveOtherHours"+rowId,data.attmLeaveOtherHours);//�������
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