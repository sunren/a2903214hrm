<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<%@taglib prefix="jscalendar" uri="/jscalendar" %>
<html>
<HEAD>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../dwr/interface/EmpJobHis.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpEduHis.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpTrainHis.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpRelations.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpAddUpdate.js"></script>
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
</HEAD>
<body> 
<div id="selectcontent" class="selectdiv" style="visibility:hidden;pixelHeight:20px;z-index:9;">
	<iframe id="selframe" frameborder="0" height="100%"></iframe>
	<div id="selecthtml" class="selectcontent">selectdate</div>
</div>
<!-- �������select -->
<script type="text/javascript" src="../resource/js/edit_select.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />

<s:component template="bodyhead"/>
<ty:auth auths="101"><s:set name="hasAuth" value="'has'"/></ty:auth>
<h3 align="left">��������
	<s:if test="#request.hasAuth=='has'">
		<a href="#"><img src="../resource/images/CreateProject.gif" onclick="showehjForm('add','')"></img></a>
	</s:if>
</h3>
<table width="100%" class="gridtableList" cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
		<th nowrap="nowrap">��ʼ����</th>
		<th nowrap="nowrap">��������</th>
		<th nowrap="nowrap">��ְ��λ</th>
		<th nowrap="nowrap">ְλ</th>
		<th nowrap="nowrap">��������</th>
		<th nowrap="nowrap">����</th>
	</tr>
	<tbody id="ehjList">
		<s:iterator value="ehjList">
			<tr id="<s:property value="ehjId"/>">
				<td align="center"><s:date name="ehjDateStart" format="yyyy-MM" /></td>
				<td align="center"><s:date name="ehjDateEnd" format="yyyy-MM" /></td>
				<td align="center"><s:property value="ehjCompany" /></td>
				<td align="center"><s:property value="ehjPosition" /></td>
				<td align="center"><s:property value="ehjDescription" /></td>
				<td align="right" width="40">
					<s:if test="#request.hasAuth=='has'">
						<a href="#" onclick="showehjForm('update','<s:property value="ehjId"/>')">
						<img src="../resource/images/edit.gif" alt="�޸�"></img></a>
						<a href="#" onclick="ehjDel('<s:property value="ehjId"/>')">
						<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
					</s:if>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<br>
<h3 align="left">��������
	<s:if test="#request.hasAuth=='has'">
	<a href="#"><img src="../resource/images/CreateProject.gif" onclick="showEheForm('add','')"></img></a>
	</s:if>
</h3>
<table width="100%" class="gridtableList" cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
		<th nowrap="nowrap">��ʼ����</th>
		<th nowrap="nowrap">��������</th>
		<th nowrap="nowrap">����ѧУ</th>
		<th nowrap="nowrap">��ѧרҵ</th>
		<th nowrap="nowrap">����ѧλ</th>
		<th nowrap="nowrap">��ע</th>
		<th nowrap="nowrap">����</th>
	</tr>
	<tbody id="eheList">
		<s:iterator value="eheList">
			<tr id="<s:property value="eheId" />">
				<td align="center"><s:date name="eheDateStart" format="yyyy-MM" /></td>
				<td align="center"><s:date name="eheDateEnd" format="yyyy-MM" /></td>
				<td align="center"><s:property value="eheSchool" /></td>
				<td align="center"><s:property value="eheMajor" /></td>
				<td align="center"><s:property value="eheDegree" /></td>
				<td align="center"><s:property value="eheComments" /></td>
				<td align="right" width="60">
					<s:if test="eheAttachment!='' && eheAttachment!=null">
						<a href="downProfile.action?fileName=<s:property value="eheAttachment"/>&contentDisposition=<s:property value="eheAttachment"/>">
							<img src="../resource/images/attachment.gif"/></a>
						<input type="hidden" id="attach<s:property value="eheId"/>" value="<s:property value="eheAttachment"/>"/>
					</s:if>
					<s:else>&nbsp;&nbsp;</s:else>
					<s:if test="#request.hasAuth=='has'">
						<a href="#" onclick="showEheForm('update','<s:property value="eheId"/>')">
							<img src="../resource/images/edit.gif" alt="�޸�"></img></a>
						<a href="#" onclick="eheDel('<s:property value="eheId"/>')">
							<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
					</s:if>
				</td>	
			</tr>
		</s:iterator>
	</tbody>
</table>
<br>
<h3 align="left">��ѵ����
	<s:if test="#request.hasAuth=='has'">
		<a href="#"><img src="../resource/images/CreateProject.gif" onclick="showEhtForm('add')"></img></a>
	</s:if>
</h3>
<table width="100%" class="gridtableList" cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
		<th nowrap="nowrap">��ʼ����</th>
		<th nowrap="nowrap">��������</th>
		<th nowrap="nowrap">��ѵ�γ�</th>
		<th nowrap="nowrap">��ѵ����</th>
		<th nowrap="nowrap">��ѵ�ص�</th>
		<th nowrap="nowrap">����֤��</th>
		<th nowrap="nowrap">��ע</th>
		<th nowrap="nowrap">����</th>
	</tr>
	<tbody id="ehtList">
		<s:iterator value="ehtList">
			<tr id="<s:property value="ehtId"/>">
				<td align="center"><s:date name="ehtStartDate" format="yyyy-MM" /></td>
				<td align="center"><s:date name="ehtEndDate" format="yyyy-MM" /></td>
				<td align="center"><s:property value="ehtCourse" /></td>
				<td align="center"><s:property value="ehtDepartment" /></td>
				<td align="center"><s:property value="ehtLocation" /></td>
				<td align="center"><s:property value="ehtCertificate" /></td>
				<td align="center"><s:property value="ehtComments"/></td>
				<td width="60">
					<s:if test="ehtAttatchment!='' && ehtAttatchment!=null">
						<a href="downProfile.action?fileName=<s:property value="ehtAttatchment"/>&contentDisposition=<s:property value="ehtAttatchment"/>">
						<img src="../resource/images/attachment.gif"/>
						</a>
						<input type="hidden" id="attach<s:property value="ehtId"/>" value="<s:property value="ehtAttatchment"/>"/>
					</s:if>
					<s:else>&nbsp;&nbsp;</s:else>
					<s:if test="#request.hasAuth=='has'">
						<a href="#" onclick="showEhtForm('update','<s:property value="ehtId"/>')">
						<img src="../resource/images/edit.gif" alt="�޸�"></img></a>
						<a href="#" onclick="ehtDel('<s:property value="ehtId"/>')">
						<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
					</s:if>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<br>
<h3 align="left">����ϵ
	<s:if test="#request.hasAuth=='has'">
		<a href="#">
			<img src="../resource/images/CreateProject.gif" onclick="showErelForm('add','')"></img>
		</a>
	</s:if>
</h3>
<table width="100%" class="gridtableList" cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
		<th nowrap="nowrap">��ϵ</th>
		<th nowrap="nowrap">����</th>
		<th nowrap="nowrap">����</th>
		<th nowrap="nowrap">��������</th>
		<th nowrap="nowrap">��˾</th>
		<th nowrap="nowrap">ְλ</th>
		<th nowrap="nowrap">��ϵ��ʽ</th>
		<th nowrap="nowrap">����</th>
	</tr>
	<tbody id="erelList">
		<s:iterator value="erelList">
			<tr id="<s:property value="erelId"/>">
			    <td align="center"><s:property value="erelRelationship"/></td>
			    <td align="center"><s:property value="erelName"/></td>
			    <td align="center"><s:date name="erelBirthday" format="yyyy-MM-dd"/></td>
			    <td align="center">
				    <s:if test="erelBirthdayRemind==1">����</s:if>
					<s:else>������</s:else>  
			    </td>
			    <td align="center"><s:property value="erelCompany"/></td>
			    <td align="center"><s:property value="erelPosition"/></td>
				<td align="center"><s:property value="erelContactInfo"/></td>
				<td width="40">
					<s:if test="#request.hasAuth=='has'">
						<a href="#" onclick="showErelForm('update','<s:property value="erelId"/>')">
						<img src="../resource/images/edit.gif" alt="�޸�"></img></a>
						<a href="#" onclick="erelDel('<s:property value="erelId"/>')">
						<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
					</s:if>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<br>
<h3 align="left">Ա������
	<s:if test="#request.hasAuth=='has'">
	<a href="#"><img src="../resource/images/CreateProject.gif" onclick="hrm.common.openDialog('dlgEmpResume');"></img></a>
	</s:if>
</h3>
<table width="100%" class="gridtableList" cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
		<s:if test="employee.empResume1!=null&&employee.empResume1!=''">
			<td>
				<a href="downProfile.action?fileName=<s:property value="employee.empResume1"/>&contentDisposition=<s:property value="resume1DownLoadName"/>">���ļ���</a>
				<s:if test="#request.hasAuth=='has'">
					<a href="#" onclick="if(window.confirm('ȷ��ɾ����')){ window.location='resumeDelete.action?empNo=<s:property value="empNo"/>&filecFileName=<s:property value="employee.empResume1"/>';}">
					<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
				</s:if>
			</td>
		</s:if>
		<td>
			<s:if test="employee.empResume2!=''&&employee.empResume2!=null">
				<a href="downProfile.action?fileName=<s:property value="employee.empResume2"/>&contentDisposition=<s:property value="resume2DownLoadName"/>">Ӣ�ļ���</a>
				<s:if test="#request.hasAuth=='has'">
					<a href="#" onclick="if(window.confirm('ȷ��ɾ����')){ window.location='resumeDelete.action?empNo=<s:property value="empNo"/>&fileeFileName=<s:property value="employee.empResume2"/>';}">
					<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
				</s:if>
			</s:if>
		</td>
	</tr>
</table>
<br>
<h3 align="left">�Զ�����Ϣ</h3>
<form action="" method="post">
<table width="100%" class="gridtableList" cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
	<s:iterator value="empaddconfList" status="rowstatus">
		<td><s:property value="eadcFieldName"/></td>
		<td>
			<s:if test="eadcFieldType=='input'">
				<input type="text" value="<s:property value="value"/>" onKeyDown="$('#saveField')[0].disabled=false;"
					size="36" maxlength="128" id="<s:property value="fieldName" />" <s:if test="#request.hasAuth!='has'">disabled</s:if>/>
			</s:if>
			<s:if test="eadcFieldType=='number'">
				<input id="<s:property value="fieldName" />" type="text" value="<s:property value="value"/>"
					onkeypress="hrm.common.MKeyTextLength(this,12);" onKeyDown="$('#saveField')[0].disabled=false;" size="36" maxlength="128" 
					onkeyup="value=value.replace(/[^0-9{\.}]/g,'');changeNumPoint(this);" <s:if test="#request.hasAuth!='has'">disabled</s:if>/>
			</s:if>
			<s:if test="eadcFieldType=='textarea'">
				<textarea id="<s:property value="fieldName"/>" cols="26"  onkeypress="hrm.common.MKeyTextLength(this,255);"
					onKeyDown="$('#saveField')[0].disabled=false;" <s:if test="#request.hasAuth!='has'">disabled</s:if>><s:property value="value"/></textarea>
			</s:if>			
			<s:if test="eadcFieldType=='date'">
				<input type="text" size="10" maxlength="10" onKeyDown="$('#saveField')[0].disabled=false;"
					<s:if test="#request.hasAuth!='has'">disabled</s:if> value="<s:property value="value"/>" maxlength="25"
					<s:if test="#request.hasAuth=='has'">id="<s:property value="fieldName"/>" </s:if>/>
				<s:if test="#request.hasAuth=='has'">
					<img onclick="WdatePicker({el:'<s:property value="fieldName"/>'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				</s:if>
			</s:if>
			<s:if test="eadcFieldType=='select'">
				<select id="<s:property value="fieldName"/>"  onKeyDown="$('#saveField')[0].disabled=false;" <s:if test="#request.hasAuth!='has'">disabled</s:if>>
					<option value="">��ѡ��</option>
					<s:iterator value="fieldValueList">
						<option value="<s:property/>" <s:if test="#request['value']==top">selected</s:if>>
							<s:property/>
						</option>
					</s:iterator>
				</select>
			</s:if>
		</td>
		<s:if test="#rowstatus.odd != true">
			</tr><tr>
		</s:if>
	</s:iterator></tr>
	<tr>
		<td colspan="4" align="center">
		<s:if test="#request.hasAuth=='has'">
			<input type="button" value="�����޸�" disabled="true" id="saveField" onclick="empAddConfUpdate()"/>&nbsp;&nbsp;&nbsp;</s:if>
		</td>
	</tr>
</table>
</form>

<!-- ��������dialog -->
<div id="dlgAddHistoryJob" style="width:425px;" class="prompt_div_inline">	
	<form id="ehjForm" method="post">
		<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
			<tr>
				<td>��ʼ���£�</td>
				<td><select id="jobH-startJobYear"></select>��&nbsp;<select id="jobH-startJobMonth" ></select>��</td>
			</tr>
			<tr>
				<td>�������£�</td>
				<td><select id="jobH-endJobYear"></select>��&nbsp;<select id="jobH-endJobMonth" ></select>��</td>
			</tr>
			<tr>
				<td>��ְ��λ<span class="required">*</span>��</td>
				<td><input id="jobH-ehjCompany" name="emphistoryjob.ehjCompany" maxlength="64" size="30"/></td>
			</tr>
			<tr>
				<td>ְλ����<span class="required">*</span>��</td>
				<td><input id="jobH-ehjPosition" name="emphistoryjob.ehjPosition" maxlength="64" size="30"/></td>
			</tr>
			<tr>
				<td>����������</td>
				<td><textarea id="jobH-ehjDescription" name="emphistoryjob.ehjDescription" cols="40" rows="8"></textarea></td>
			</tr>
			<tr height="35">
				<input type="hidden" id="jobH-ehjId"/>
				<td colspan="2" align="center">
					<input type="button" onclick="jobHisAdd()" id="ehjAddbtn" value="����">
					<input type="button" onclick="jobHisUpdate()" id="ehjUpdatebtn" value="�޸�">
					<input type="button" value="ȡ��" onclick="hrm.common.closeDialog('dlgAddHistoryJob');">
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- ��������dialog -->
<div id="dlgAddHistoryEdu" style="width:425px;" class="prompt_div_inline">
	<form name="eheForm" id="eheForm" action="eduAdd.action" method="POST" enctype="multipart/form-data">
	  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	  		<tr>
	  			<td>
	  				<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
						<tr>
							<td>��ʼ���£�</td>
							<td><select id="eduH-startEduYear"></select>��&nbsp;<select id="eduH-startEduMonth"></select>��</td>
						</tr>
						<tr>
							<td>�������£�</td>
							<td><select id="eduH-endEduYear"></select>��&nbsp;<select id="eduH-endEduMonth"></select>��</td>
						</tr>
						<tr>
							<td>����ѧУ<span class="required">*</span>��</td>
							<td><input id="eduH-eheSchool" name="emphistoryedu.eheSchool" maxlength="64" size="30"/></td>
						</tr>
						<tr>
							<td>��ѧרҵ<span class="required">*</span>��</td>
							<td><input id="eduH-eheMajor" name="emphistoryedu.eheMajor" maxlength="64" size="30"/></td>
						</tr>
						<tr>
							<td>����ѧλ��</td>
							<td><input id="eduH-eheDegree" name="emphistoryedu.eheDegree" maxlength="64" size="30"/></td>
						</tr>
						<tr>
	                        <td>��ע��</td>
	                        <td><textarea id="eduH-eheComments" name="emphistoryedu.eheComments" cols="40" rows="8"></textarea></td>
	                    </tr>
						<tr>
							<td>�ϴ�������</td>
							<td><input name="file" type="file" size="25" maxlength="128"/> <span id="eheAttach"></span></td>
						</tr>
						<tr height="35">
							<input type="hidden" name="empNo" id="eduH-currEmpId"/>
							<input type="hidden" name="emphistoryedu.eheDateStart" id="eduH-eheDateStart"/>
							<input type="hidden" name="emphistoryedu.eheDateEnd" id="eduH-eheDateEnd"/>
							<input type="hidden" id="eheEmpId" name="emphistoryedu.employee.id" value="self"/>
							<input type="hidden" id="eduH-eheId" name="emphistoryedu.eheId"/>
							<td colspan="2" align="center">
								<input type="submit" id="eheAddbtn" onclick="return eduHisValid()" value="����"/>
								<input type="submit" id="eheUpdatebtn" onclick="return eduHisValid()" value="�޸�"/>
								<input type="button" value="ȡ��" onclick="hrm.common.closeDialog('dlgAddHistoryEdu');">
							</td>
						</tr>
					</table>
	  			</td>
	  		</tr>
		</table>
	</form>
</div>

<!-- ��ѵ����dialog -->
<div id="dlgAddHistoryTrain" style="width:425px;" class="prompt_div_inline">	
  	<form name="ehtForm" id="ehtForm" action="trainAdd.action" method="post" enctype="multipart/form-data">
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td>
  				<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td>��ʼ���£�</td>
						<td>
							<select id="trainH-startTrainYear"></select>��&nbsp;
							<select id="trainH-startTrainMonth"></select>��
						</td>
					</tr>
					<tr>
						<td>�������£�</td>
						<td>
						<select id="trainH-endTrainYear"></select>��&nbsp;
						<select id="trainH-endTrainMonth"></select>��</td>
					</tr>
					<tr>
						<td>��ѵ�γ�<span class="required">*</span>��</td>
						<td><input id="trainH-ehtCourse" name="emphistorytrain.ehtCourse" maxlength="64" size="30"/></td>
					</tr>
					<tr>
						<td>��ѵ������</td><td><input id="trainH-ehtDepartment" name="emphistorytrain.ehtDepartment" maxlength="64" size="30"/></td>
					</tr>
					<tr>
						<td>��ѵ�ص㣺</td><td><input id="trainH-ehtLocation" name="emphistorytrain.ehtLocation" maxlength="64" size="30"/></td>
					</tr>
					<tr>
						<td>����֤�飺</td><td><input id="trainH-ehtCertificate" name="emphistorytrain.ehtCertificate" maxlength="64" size="30"/></td>
					</tr>
					<tr>
                        <td>��ע��</td>
                        <td><textarea id="trainH-ehtComments" onkeypress="hrm.common.MKeyTextLength(this,255);" rows="8" cols="40"
                        	name="emphistorytrain.ehtComments"></textarea></td>
                    </tr>
					<tr>
						<td>�ϴ�������</td>
						<td><input name="file" type="file" size="25" maxlength="128"/> <span id="ehtAttach"></span></td>
					</tr>
					<tr height="35">
						<input type="hidden" name="empNo" id="trainH-currEmpId"/>
						<input type="hidden" name="emphistorytrain.ehtStartDate" id="trainH-ehtStartDate"/>
						<input type="hidden" name="emphistorytrain.ehtEndDate" id="trainH-ehtEndDate"/>
						<input type="hidden" id="ehtEmpId" name="emphistorytrain.employee.id" value="self"/>
						<input type="hidden" id="trainH-ehtId" name="emphistorytrain.ehtId"/>
						<td colspan="2" align="center">
							<input type="submit" id="ehtAddbtn" onclick="return trainHisValid()" value="����">
							<input type="submit" id="ehtUpdatebtn" onclick="return trainHisValid()" value="�޸�"/>
							<input type="button" value="ȡ��" onclick="hrm.common.closeDialog('dlgAddHistoryTrain');">
						</td>
					</tr>
				</table>
  			</td>
  		</tr>
	</table>
	</form>
</div>

<!-- ����ϵdialog -->
<div id="dlgEmpRelations" style="width:425px;" class="prompt_div_inline">	
	<form id="erelForm" name="erelForm" action="relationsAdd.action" method="post" enctype="multipart/form-data">
		<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
			<tr>
				<td>��ϵ<span class="required">*</span>��</td>
				<td>
					<s:component template="editselect" name="emprelations.erelRelationship">
						<s:param name="list" value="{'����','ĸ��','����','Ů��','��ż','����'}"/>
						<s:param name="size" value="10"/>
						<s:param name="height" value="100"/>
					</s:component>
				</td>
			</tr>
			<tr>
				<td>����<span class="required">*</span>��</td>
				<td><input id="relH-erelName" name="emprelations.erelName" maxlength="64" size="30"/></td>
			</tr>
			<tr>
				<td>���գ�</td>
				<td>
					<s:textfield id="relH-erelBirthday" name="emprelations.erelBirthday" required="true" size="10"/>
					<img onclick="WdatePicker({el:'relH-erelBirthday'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
				</td> 
			</tr>
			<tr>
				<td>�������ѣ�</td>
				<td>
					<s:select id="relH-erelBirthdayRemind" name="emprelations.erelBirthdayRemind"
						value="emprelations.erelBirthdayRemind" tabindex="4" list="#{0:'������', 1:'����'}"/>
				</td>
			</tr>
			<tr>
				<td>��˾��</td>
				<td><input id="relH-erelCompany" name="emprelations.erelCompany" maxlength="64" size="30"/></td>
			</tr>
			<tr>
				<td>ְλ��</td>
				<td><input id="relH-erelPosition" name="emprelations.erelPosition" maxlength="64" size="30"/></td>
			</tr>
			<tr>
				<td>��ϵ��ʽ��</td>
				<td>
					<textarea id="relH-erelContactInfo" onkeypress="hrm.common.MKeyTextLength(this,128);" rows="8" cols="40"
						name="emprelations.erelContactInfo"></textarea>
				</td>
			</tr>
			<tr height="35">
				<input type="hidden" id="relH-empNo" name="empNo"/>
				<input type="hidden" id="erelEmpId" name="emprelations.employee.id" value="self"/>
			    <input type="hidden" id="relH-erelId" name="emprelations.erelId"/>
				<td colspan="2" align="center">
				<input type="submit" id="erelAddbtn" onclick="return relationsValid()"  value="����">
				<input type="submit" id="erelUpdatebtn" onclick="return relationsValid()"  value="�޸�">
				<input type="button" value="ȡ��" onclick="hrm.common.closeDialog('dlgEmpRelations');"></td>
			</tr>
		</table>
	</form>
</div>

<!-- Ա������dialog -->
<div id="dlgEmpResume" style="width:350px;" class="prompt_div_inline">
	<s:form id="feedbackForm" action="resumeUpload" method="post" enctype="multipart/form-data">
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td>
  				<table class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<input type="hidden" id="operEmpNo" name="empNo" value="<s:property value="empNo"/>"/>
					</tr>
					<tr>
						<td>���ļ�����</td>
						<td><input name="filec" type="file" maxlength="128" size="25"/></td>
					</tr>
					<tr>
						<td>Ӣ�ļ�����</td>
						<td><input name="filee" type="file" maxlength="128" size="25"/></td>
					</tr>
					<tr height="35">
						<td colspan="2" align="center"><input type="submit" value="�ϴ�"/>&nbsp;&nbsp;&nbsp;<input type="button" value="ȡ��" onclick="hrm.common.closeDialog('dlgEmpResume');"/></td>
					</tr>
				</table>
  			</td>
  		</tr>
	</table>
	</s:form>
</div>
<script type="text/javascript">

parent.document.getElementById('parentEmpName').innerHTML ='<s:property value="empName"/>';
$(parent.document.getElementById('parentEmpName')).attr('name','<s:property value="empNo"/>');

var currOperName;//��ǰ��������Ա������
<s:if test="#session.empName!=''">
	currOperName='<s:property value="empName"/>';
</s:if>
<s:else>
	currOperName='<s:property value="#session.empName"/>';
</s:else>

var year=new Date().getYear();//���ڶ����е�����������ֵ
if(!hrm.common.navigatorIsIE()) {
	year=year+1900
}
var editObj;//���޸ĵ���
//��ʾ�����������ӻ��޸ĶԻ���
function showehjForm(type,ehjId){
	var titleString="����������"+currOperName+"��";
	$('#dlgAddHistoryJob').dialog("option","title",titleString);
	if(type=='add') {
		$("#ehjForm")[0].reset();
		document.getElementById("jobH-startJobYear").value=(new Date()).getFullYear();
		document.getElementById("jobH-endJobYear").value=(new Date()).getFullYear();
		document.getElementById("jobH-startJobMonth").value="01";
		document.getElementById("jobH-endJobMonth").value="01";
		$('#ehjAddbtn')[0].style.display="inline";
		$('#ehjUpdatebtn')[0].style.display="none";
		hrm.common.openDialog('dlgAddHistoryJob');
	}else if(type=='update') {
		editObj = $("#"+ehjId)[0];
		EmpJobHis.loadJobHis(ehjId,jobloadcallback);
	}
	function jobloadcallback(ehj){
		if(ehj==null){
			alert(ehjId+" doesn't exist");
			return;
		}
		ehj["startJobYear"]=ehj["ehjDateStart"].toFormatString("yyyy");
		ehj["endJobYear"]=ehj["ehjDateEnd"].toFormatString("yyyy");
		ehj["startJobMonth"]=ehj["ehjDateStart"].toFormatString("MM");
		ehj["endJobMonth"]=ehj["ehjDateEnd"].toFormatString("MM");
		model.simple.initByObject("dlgAddHistoryJob","jobH",ehj);
		$('#ehjAddbtn')[0].style.display="none";
		$('#ehjUpdatebtn')[0].style.display="inline";
		hrm.common.openDialog('dlgAddHistoryJob');
	}
}
//�жϹ���������ʼʱ�䲻�ܱȽ���ʱ�������Ϸ��򱣴浽���ݿ�
function jobHisAdd() {
	var startDate = new Date($('#jobH-startJobYear').val(),$('#jobH-startJobMonth').val()-1);
	var endDate   = new Date($('#jobH-endJobYear').val(),$('#jobH-endJobMonth').val()-1);
	if(startDate>endDate){alert("��ʼʱ�䲻�ܱȽ���ʱ����");return;}
	if(hrm.common.isNull($('#jobH-ehjCompany').val())){
		alert("��ְ��λ���");  
		return false;
	}
	if(hrm.common.isNull($('#jobH-ehjPosition').val())){
		alert("ְλ���");
		return false;
	}
	objetC={
		ehjId:null,
		ehjDateStart:startDate,
		ehjDateEnd:endDate,
		ehjCompany:$('#jobH-ehjCompany').val(),
		ehjPosition:$('#jobH-ehjPosition').val(),
		ehjDescription:$('#jobH-ehjDescription').val()
	};
	hrm.common.closeDialog('dlgAddHistoryJob');
	var params = DWRUtil.getValues(objetC);
	var operEmpNo=$('#operEmpNo').val();
	if( operEmpNo==''||operEmpNo==null) {
		operEmpNo = 'self';
	}
	EmpJobHis.jobHisAdd(params,operEmpNo,jobaddcallback)
			
	function jobaddcallback(msg) {
		try{
			if(msg=='noauth') {
				errMsg("errMsg","��û������Ȩ��ִ�б�������");
				return;
			}
			var aTd=$('#ehjList')[0].insertRow(-1);
			aTd.id=msg;
			var dep=$('#ehjForm')[0];
			var _es=dep.elements; //��ȡdialog��Form�����ֵ
			var formIdex=0;
			for(var i=0;i<5;i++){
				var objCell = document.createElement("td")
				if(i<2){
					objCell.innerHTML=_es[formIdex++].value+"-"+_es[formIdex++].value;
					
				}else{	
					objCell.innerHTML=_es[formIdex++].value.trim();
				}
				objCell.setAttribute("align","center");
				aTd.appendChild(objCell);
			}	
			var objCell = document.createElement("td");
			objCell.innerHTML = "<a href='#' onclick=\"showehjForm('update','"+msg+"')\"><img src='../resource/images/edit.gif' alt='�޸�'></img></a> <a href='#' onclick=\"ehjDel('"+msg+"')\"><img src='../resource/images/delete.gif' alt='ɾ��'></img></a>";
			aTd.appendChild(objCell);
			objCell = document.createElement("td");
			successMsg("errMsg","�������������ɹ���");
		}catch(e){alert(e);}
	}
}
//�жϿ�ʼʱ�䲻�ܱȽ���ʱ�������Ϸ����޸ı��浽���ݿ�
function jobHisUpdate() {
	var startDate = new Date($('#jobH-startJobYear').val(),$('#jobH-startJobMonth').val()-1);
	var endDate   = new Date($('#jobH-endJobYear').val(),$('#jobH-endJobMonth').val()-1);
	if(startDate>endDate){alert("��ʼʱ�䲻�ܱȽ���ʱ����");return;}
	if(hrm.common.isNull($('#jobH-ehjCompany').val())){
		alert("��ְ��λ���");  
		return false;
	}
	if(hrm.common.isNull($('#jobH-ehjPosition').val())){
		alert("ְλ���");
		return false;
	}
	objetC={
		ehjId:$('#jobH-ehjId').val(),
		ehjDateStart:startDate,
		ehjDateEnd:endDate,
		ehjCompany:$('#jobH-ehjCompany').val(),
		ehjPosition:$('#jobH-ehjPosition').val(),
		ehjDescription:$('#jobH-ehjDescription').val()
	};
	hrm.common.closeDialog('dlgAddHistoryJob');
	var params = DWRUtil.getValues(objetC);
	EmpJobHis.jobHisUpdate(params,jobupdatecallback)
			
	function jobupdatecallback(msg) {
		try{
			if(msg=='noauth') {
				errMsg("errMsg","��û���޸�Ȩ��ִ�б�������");
				return;
			}
			if(msg=='noId') {
				errMsg("errMsg","��ѡ���в����ڡ�");
			}else if(msg=='success') {
				var dep=$('#ehjForm')[0];
				var _es=dep.elements; //��ȡdialog�����ֵ
				var aTd = editObj.cells;//��������
				
				var formIdex=0;
				for(var i=0;i<5;i++){
					if(i<2){
						aTd[i].innerHTML=_es[formIdex++].value+"-"+_es[formIdex++].value;
					}else			
					aTd[i].innerHTML=_es[formIdex++].value.trim();
				}
				successMsg("errMsg","�޸Ĺ��������ɹ���");
			}
		}catch(e){alert(e);}	
	}
}
//ɾ��ĳ��id�Ĺ�������
function ehjDel(ehjId) { 
	if(!window.confirm('ȷ��ɾ����')){ return ;}
	EmpJobHis.jobHisDel(ehjId,jobdelcallback);
	function jobdelcallback(msg) {
		if(msg=='noauth') {
			errMsg("errMsg","��û��ɾ��Ȩ��ִ�б�������");
			return;
		}
		if(msg=='success') {
			$("#"+ehjId)[0].parentNode.removeChild($("#"+ehjId)[0]);
			successMsg("errMsg","ɾ�����������ɹ���");
		}
	}
}
//����ҳ������ݶ�����޸�dialog��ֵ
function showdep(form){
try{					
	var dep=$("#"+form)[0];	  	// get the current tr
	var _es=dep.elements;
	//��id
	var aTd = editObj.cells;
	var l = aTd.length;
	//��ֵ
	var formIdex=0;
	for(var i=0;i<l-1;i++){
		if(i<2){
			var stringdate=aTd[i].innerHTML.trim();
			_es[formIdex++].value=stringdate.substring(0,5);
			_es[formIdex++].value=stringdate.substring(5,7);
		}else			
			_es[formIdex++].value=aTd[i].innerHTML.trim();
	}
	alert(_es[0]+":"+_es[0].value+"\n"+typeof _es[1]+":"+_es[1].value);
	}catch(e){alert(e);}
}
//������ʾ������Ϊ����ϵdialog��ֵ
function showdepErel(form){
try{					
	var dep=$("#"+form)[0];	  	// get the current tr
	var _es=dep.elements;
	//��id
	var aTd = editObj.cells;
	var l = aTd.length;
	//��ֵ
	var formIdex=0;
	for(var i=0;i<l-1;i++){	
	    if(i==1)
	    {
		_es[formIdex++].value=aTd[i].innerHTML.trim();
		}
		if(i==3)
		{
		if(aTd[i].innerHTML.trim()=="����")
		   _es[formIdex++].value=1;
		else
		   _es[formIdex++].value=0;
		}
		else{
		_es[formIdex++].value=aTd[i].innerHTML.trim();
		
		}
	}
	}catch(e){alert(e);}
}
//**********************************************************************************************
//education experience script:******************************************************************
//**********************************************************************************************
//��ʾ�������������ӻ��޸�dialog
function showEheForm(type,eheId){
	$('#eheAttach')[0].innerHTML='';
	var titleString="����������"+currOperName+"��";
	$('#dlgAddHistoryEdu').dialog("option","title",titleString);
	if(type=='add') {
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('eduH-currEmpId').value = $(currEmp).attr('name');
		$("#eheForm")[0].reset();
		document.getElementById("eduH-startEduYear").value=(new Date()).getFullYear();
		document.getElementById("eduH-endEduYear").value=(new Date()).getFullYear();
		document.getElementById("eduH-startEduMonth").value="01";
		document.getElementById("eduH-endEduMonth").value="01";
		$('#eheAddbtn')[0].style.display="inline";
		$('#eheUpdatebtn')[0].style.display="none";
		$('#eheForm')[0].action="eduAdd.action";
		hrm.common.openDialog('dlgAddHistoryEdu');
	}else if(type=='update') {
		EmpEduHis.loadEduHis(eheId,eduloadcallback);
	}
	function eduloadcallback(ehe){
		if(ehe==null){
			alert(eheId+" doesn't exist");
			return;
		}
		ehe["startEduYear"]=ehe["eheDateStart"].toFormatString("yyyy");
		ehe["endEduYear"]=ehe["eheDateEnd"].toFormatString("yyyy");
		ehe["startEduMonth"]=ehe["eheDateStart"].toFormatString("MM");
		ehe["endEduMonth"]=ehe["eheDateEnd"].toFormatString("MM");
		model.simple.initByObject("dlgAddHistoryEdu","eduH",ehe);
		$('#eheAddbtn')[0].style.display="none";
		$('#eheUpdatebtn')[0].style.display="inline";
		$('#eheForm')[0].action='eduUpdate.action';
		if($('#attach'+eheId).val()!=null){
			$('#eheAttach')[0].innerHTML="<a onclick=\"attachDelete('"+$('attach'+eheId).value+"','"+eheId+"')\" href='#'>ɾ��ԭ����<img alt='ɾ��' src='../resource/images/delete.gif'/></a>";
		}
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('eduH-currEmpId').value = $(currEmp).attr('name');
		hrm.common.openDialog('dlgAddHistoryEdu');
	}	
}
//ɾ������
function attachDelete(attach,id) {
	if(window.confirm('ȷ��ɾ����')){ 
		var currEmp = parent.document.getElementById('currEmpId');
		window.location='deleteEduAttach.action?eheId='+id+'&fileFileName='+attach+"&empNo="+$(currEmp).attr('name');
		}
}
//�жϽ������������Ӻ��޸�dialog�ı�����Ϳ�ʼ����ʱ���Ƿ����Ҫ��
function eduHisValid() {
	var startDate = new Date($('#eduH-startEduYear').val(),$('#eduH-startEduMonth').val()-1);
	var endDate   = new Date($('#eduH-endEduYear').val(),$('#eduH-endEduMonth').val()-1);
	if(startDate>endDate){alert("��ʼʱ�䲻�ܱȽ���ʱ����");return false;}
	if(hrm.common.isNull($('#eduH-eheSchool').val())){
		alert("����ѧУ����Ϊ�գ�"); 
		return false;
	}
	if(hrm.common.isNull($('#eduH-eheMajor').val())){
		alert("����רҵ����Ϊ�գ�");
		return false;
	}
 	$('#eduH-eheDateStart').val($('#eduH-startEduYear').val()+"-"+$('#eduH-startEduMonth').val()+"-01");
	$('#eduH-eheDateEnd').val($('#eduH-endEduYear').val()+"-"+$('#eduH-endEduMonth').val()+"-01");
	var operEmpNo=$('#operEmpNo').val();
	if( operEmpNo==''||operEmpNo==null) {
		operEmpNo = 'self';
	}
	$('#eheEmpId').val(operEmpNo);
	return true;
}
//ɾ��ĳ��id�Ľ�������
function eheDel(eheId) {
	if(!window.confirm('ȷ��ɾ����')){ return ;}
	
	EmpEduHis.eduHisDel(eheId,edudelcallback);
	function edudelcallback(msg) {
		if(msg=='noauth') {
			errMsg("errMsg","��û��ɾ��Ȩ��ִ�б�������");
			return;
		}		
		if(msg=='success') {
			$("#"+eheId)[0].parentNode.removeChild($("#"+eheId)[0]);
			successMsg("errMsg","ɾ�����������ɹ���");
		}
	}
}
//**********************************************************************************************
//training experience script:******************************************************************
//**********************************************************************************************
//��ʾ��ѵ���������ӻ��޸�dialog
function showEhtForm(type,ehtId){
	var titleString="��ѵ������"+currOperName+"��";
	$('#dlgAddHistoryTrain').dialog("option","title",titleString);
	if(type=='add') {
		$('#ehtForm')[0].reset();
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('trainH-currEmpId').value = $(currEmp).attr('name');
		document.getElementById("trainH-startTrainYear").value=(new Date()).getFullYear();
		document.getElementById("trainH-endTrainYear").value=(new Date()).getFullYear();
		document.getElementById("trainH-startTrainMonth").value="01";
		document.getElementById("trainH-endTrainMonth").value="01";
		$('#ehtAddbtn')[0].style.display="inline";
		$('#ehtUpdatebtn')[0].style.display="none";
		$('#ehtForm')[0].action="trainAdd.action";
		hrm.common.openDialog('dlgAddHistoryTrain');	
	}else if(type=='update') {
		EmpTrainHis.loadTrainHis(ehtId,trainloadcallback);
	}
	function trainloadcallback(eht){
		$('#ehtAddbtn')[0].style.display="none";
		$('#ehtUpdatebtn')[0].style.display="inline";
		$('#ehtForm')[0].action="trainUpdate.action";

		eht["startTrainYear"]=eht["ehtStartDate"].toFormatString("yyyy");
		eht["endTrainYear"]=eht["ehtEndDate"].toFormatString("yyyy");
		eht["startTrainMonth"]=eht["ehtStartDate"].toFormatString("MM");
		eht["endTrainMonth"]=eht["ehtEndDate"].toFormatString("MM");
		model.simple.initByObject("dlgAddHistoryTrain","trainH",eht);
		if($('#attach'+ehtId).val()!=null){
			$('#ehtAttach')[0].innerHTML="<a onclick=\"ehtAttachDelete('"+$('attach'+ehtId).value+"','"+ehtId+"')\" href='#'>ɾ��ԭ����<img alt='ɾ��' src='../resource/images/delete.gif'/></a>";
		}
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('trainH-currEmpId').value = $(currEmp).attr('name');
		hrm.common.openDialog('dlgAddHistoryTrain');
	}
}
//ɾ������
function ehtAttachDelete(attach,id) {
	if(window.confirm('ȷ��ɾ����')){ 
		var currEmp = parent.document.getElementById('currEmpId');
		window.location='deleteTrainAttach.action?ehtId='+id+'&fileFileName='+attach+"&empNo="+$(currEmp).attr('name');
	}
}
//�ж���ѵ���������Ӻ��޸�dialog�ı�����Ϳ�ʼ����ʱ���Ƿ����Ҫ��
function trainHisValid() {
	var startDate = new Date($('#trainH-startTrainYear').val(),$('#trainH-startTrainMonth').val()-1);
	var endDate   = new Date($('#trainH-endTrainYear').val(),$('#trainH-endTrainMonth').val()-1);
	if(startDate>endDate){alert("��ʼʱ�䲻�ܱȽ���ʱ����");return false;}
	$('#trainH-ehtStartDate').val($('#trainH-startTrainYear').val()+"-"+$('#trainH-startTrainMonth').val()+"-01");
	$('#trainH-ehtEndDate').val($('#trainH-endTrainYear').val()+"-"+$('#trainH-endTrainMonth').val()+"-01");
	if(hrm.common.isNull($('#trainH-ehtCourse').val())){
		alert("��ѵ�γ̱��");
		return false;
	}
	
	var operEmpNo=$('#operEmpNo').val();
	if( operEmpNo==''||operEmpNo==null) {
		operEmpNo = 'self';
	}
	$('#ehtEmpId').val(operEmpNo);
	return true;
}
//ɾ��ĳ��id�Ľ���������ѵ����
function ehtDel(ehtId) {
	if(!window.confirm('ȷ��ɾ����')){ return ;}
	
	EmpTrainHis.trainHisDel(ehtId,traindelcallback);
	function traindelcallback(msg) {
		if(msg=='noauth') {
			errMsg("errMsg","��û��ɾ��Ȩ��ִ�б�������");
			return;
		}		
		if(msg=='success') {
			$("#"+ehtId)[0].parentNode.removeChild($("#"+ehtId)[0]);
			successMsg("errMsg","ɾ����ѵ�����ɹ���");
		}
	}
}
//**********************************************************************************************
//relations experience script:******************************************************************
//**********************************************************************************************
//��ʾ����ϵ�����ӻ��޸�dialog
function showErelForm(type,erelId){
	var titleString="����ϵ��"+currOperName+"��";
	$('#dlgEmpRelations').dialog("option","title",titleString);
	if(type=='add') {
		$("#erelForm")[0].reset();
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('relH-empNo').value = $(currEmp).attr('name');
		$('#erelAddbtn')[0].style.display="inline";
		$('#erelUpdatebtn')[0].style.display="none";
		$('#erelForm')[0].action="relationsAdd.action";
		hrm.common.openDialog('dlgEmpRelations');
	}else if(type=='update') {
		EmpRelations.loadTrainHis(erelId,relloadcallback);
	}
	function relloadcallback(erel){
		if(erel==null){
			alert(ehjId+" doesn't exist");
			return;
		}
		if(erel['erelBirthday']!=null){
			erel['erelBirthday']=erel['erelBirthday'].toHRMDateString();
		}
		
		document.getElementById('emprelations.erelRelationship_id').value=erel['erelRelationship'];
		model.simple.initByObject("dlgEmpRelations","relH",erel);
		$('#erelAddbtn')[0].style.display="none";
		$('#erelUpdatebtn')[0].style.display="inline";
		$('#erelForm')[0].action='relationsUpdate.action';
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('relH-empNo').value = $(currEmp).attr('name');
		hrm.common.openDialog('dlgEmpRelations');
	}
}
//�ж�����ϵ�����Ӻ��޸�dialog�ı������ʱ���Ƿ����Ҫ��
function relationsValid() {
	if(document.getElementById('emprelations.erelRelationship_id').value=='') {
		alert("��ϵ����Ϊ�գ�"); 
		return false;
	}
	if($('#relH-erelName').val()==''){
		alert("��������Ϊ�գ�"); 
		return false;
	}if(($('#relH-erelBirthday').val()!='')&&(!hrm.common.isDate($('#relH-erelBirthday').val()))) {
		alert('Ա���������ڸ�ʽ����ȷ��');
		return false;
	}
	var operEmpNo=$('#operEmpNo').val();
	if( operEmpNo==''||operEmpNo==null) {
		operEmpNo = 'self';
	}
	$('#erelEmpId').val(operEmpNo);
	return true;
}
//ɾ��ĳ��id������ϵ
function erelDel(erelId) {
	if(!window.confirm('ȷ��ɾ����')){ return ;}
	
	EmpRelations.relationsDel(erelId,relationsdelcallback);
	function relationsdelcallback(msg) {
		if(msg=='noauth') {
			errMsg("errMsg","��û��ɾ��Ȩ��ִ�б�������");
			return;
		}		
		if(msg=='success') {
			$("#"+erelId)[0].parentNode.removeChild($("#"+erelId)[0]);
			successMsg("errMsg","ɾ������ϵ�ɹ���");
		}
	}
}
//**********************************************************************************************
//empAddConfUpdate experience script:******************************************************************
//**********************************************************************************************
//�Զ�����Ϣ���޸�
function empAddConfUpdate() {
	var fieldValue1,fieldValue2,fieldValue3,fieldValue4,fieldValue5,fieldValue6,fieldValue7,fieldValue8,fieldValue9,fieldValue10,fieldValue11,fieldValue12,fieldValue13,fieldValue14,fieldValue15,fieldValue16;
	if($('#empAdditional1')!=null)fieldValue1=$('#empAdditional1').val();
	if($('#empAdditional2')!=null)fieldValue2=$('#empAdditional2').val();
	if($('#empAdditional3')!=null)fieldValue3=$('#empAdditional3').val();
	if($('#empAdditional4')!=null)fieldValue4=$('#empAdditional4').val();
	if($('#empAdditional5')!=null)fieldValue5=$('#empAdditional5').val();
	if($('#empAdditional6')!=null)fieldValue6=$('#empAdditional6').val();
	if($('#empAdditional7')!=null)fieldValue7=$('#empAdditional7').val();
	if($('#empAdditional8')!=null)fieldValue8=$('#empAdditional8').val();
	if($('#empAdditional9')!=null)fieldValue9=$('#empAdditional9').val();
	if($('#empAdditional10')!=null)fieldValue10=$('#empAdditional10').val();
	if($('#empAdditional11')!=null)fieldValue11=$('#empAdditional11').val();
	if($('#empAdditional12')!=null)fieldValue12=$('#empAdditional12').val();
	if($('#empAdditional13')!=null)fieldValue13=$('#empAdditional13').val();
	if($('#empAdditional14')!=null)fieldValue14=$('#empAdditional14').val();
	if($('#empAdditional15')!=null)fieldValue15=$('#empAdditional15').val();
	if($('#empAdditional16')!=null)fieldValue16=$('#empAdditional16').val();
	objetC={empAdditional1:fieldValue1,empAdditional2:fieldValue2,empAdditional3:fieldValue3,empAdditional4:fieldValue4,empAdditional5:fieldValue5,
			empAdditional6:fieldValue6,empAdditional7:fieldValue7,empAdditional8:fieldValue8,empAdditional9:fieldValue9,empAdditional10:fieldValue10,
			empAdditional11:fieldValue11,empAdditional12:fieldValue12,empAdditional13:fieldValue13,empAdditional14:fieldValue14,empAdditional15:fieldValue15,empAdditional16:fieldValue16};
	var params = DWRUtil.getValues(objetC);
	
	var operEmpNo=$('#operEmpNo').val();
	if( operEmpNo==''||operEmpNo==null) {
		operEmpNo = 'self';
	}
	EmpAddUpdate.updateAdditional(params,operEmpNo,updatecallback)
			
	function updatecallback(msg) {
		try{
			if(msg=='success') {
				alert("�޸��Զ�����Ϣ�ɹ�");
				$('#saveField')[0].disabled=true;
			}else{
				alert("��û��Ȩ��ִ�б�������");
				$('#saveField')[0].disabled=true;
			}
		}catch(e){alert(e);}	
	}
}
//�õ�С����ǰ�����֣�С������ȫ��ɾ��
function changeNumPoint(Number){
	if(!Number || Number.value.length == 0){
	    return;
	}
		var l = Number.value.length;
		var flag=0;
		if(Number.value.charAt(0)=="."){
			Number.value='';
			return;
		}
		for(var i=0; i<l; i++){
			var digit = Number.value.charAt(i);
			if(digit=="."){
				if(flag>0){
					Number.value=Number.value.substring(0,i);
				}
				flag++;
			}
		}
		if(flag==0){
			if(l>12){
				Number.value=Number.value.substring(0,12);
			}
		}
	return;
}
function initYearSelect(eleId,start,end){
	var yearArr={};
	var currYear=(new Date()).getFullYear();
	for(var i=currYear+end;i>currYear-start;i--) {
		yearArr[i+""]=i;
	}
	DWRUtil.removeAllOptions(eleId);
	DWRUtil.addOptions(eleId, yearArr);
}
function initMonthSelect(eleId){
	var monthArr={};
	for(var i=1;i<13;i++) {
		var str=i<10?"0"+i:i+"";
		monthArr[str]=str;
	}
	DWRUtil.removeAllOptions(eleId);
	DWRUtil.addOptions(eleId, monthArr);
}
initYearSelect("eduH-startEduYear",50,5);
initYearSelect("eduH-endEduYear",50,5);
initMonthSelect("eduH-startEduMonth");
initMonthSelect("eduH-endEduMonth");

initYearSelect("jobH-startJobYear",40,0);
initYearSelect("jobH-endJobYear",40,0);
initMonthSelect("jobH-startJobMonth");
initMonthSelect("jobH-endJobMonth");

initYearSelect("trainH-startTrainYear",45,1);
initYearSelect("trainH-endTrainYear",45,1);
initMonthSelect("trainH-startTrainMonth");
initMonthSelect("trainH-endTrainMonth");


hrm.common.initDialog('dlgAddHistoryJob');//���������Ի���
hrm.common.initDialog('dlgAddHistoryEdu');//���������Ի���
hrm.common.initDialog('dlgAddHistoryTrain');//��ѵ�����Ի���
hrm.common.initDialog('dlgEmpRelations');//����ϵ�Ի���
hrm.common.initDialog('dlgEmpResume');//Ա�������Ի���
model.simple.setParentIFrameFull("IFrame1"); // add by ���ȣ�����iframe�߶ȣ�
</script>
</body>
</html>
