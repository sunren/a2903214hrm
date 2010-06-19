<%@ page language="java" contentType="text/html; charset=GBK"	pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
	<head>
		<script type="text/javascript" src="../resource/js/configuration/configExamin.js"></script>
	</head>
<body>
<div class="wrapper">
	<div class="tipdiv">
		<p>1. ȱʡ����£���ټӰ�����������Ա�Ű࣬����鿴����ÿ�տ��ڣ���������֯�ṹͼ������</p>
		<p>2. ȱʡ�����HR���Դ����ύ��ټӰ����룬��������ʱ�޸����룬HR����ִ�����ٺͼӰ�ȷ�ϲ���</p>
		<p>3. ÿ�¿������ݻ��ܿ�ʼ�������ƣ����ѡ�����£�ֻ����16��-28�գ�����Ǳ��£�ֻ����1��-15�գ�ȱʡΪ����1��</p>
		<p>4. ������ˢ�����ܣ�����ֻ�ܰ�����㣻����ˢ�����ܣ�����ٿ��԰�Сʱ���㣬ÿ�¿��ڻ��ܿ��԰�Сʱ�����죩ͳ�ƣ�ȱʡһ�����8Сʱ</p>
	</div>
	<div class="contentdiv">
		<form id="examinForm" action="examinUpdateConfig.action" method="post">
			<s:token />
			<p>
				�����Ǳ�����ټӰ��¼��
				<s:hidden id="sys_examin_create_level" name="sys_examin_create_level" ></s:hidden>
                <s:if test="sys_examin_create_level==0">
					<input type="radio" class="radio" id="submitSub" name="submitSub" onclick="changeSelectSub()" value="1"></input>����
					<input type="radio" class="radio" id="submitSub" name="submitSub" onclick="changeSelectSub()" value="0" checked></input>��ֹ
				</s:if>
				<s:else>
					<input type="radio" class="radio" id="submitSub" name="submitSub" onclick="changeSelectSub()" value="1" checked></input>����
					<input type="radio" class="radio" id="submitSub" name="submitSub" onclick="changeSelectSub()" value="0"></input>��ֹ
				</s:else>
			</p>
			<p>
				����ʱ�޸���ټӰ��¼��
				<s:hidden id="sys_examin_update_level" name="sys_examin_update_level"></s:hidden>
                <s:if test="sys_examin_update_level==0">
				    <input type="radio" class="radio" id="sys_examin_update_levelRadio" name="sys_examin_update_levelRadio" onClick="changeUpdateLevelRadio()" value="1"></input>����
				    <input type="radio" class="radio" id="sys_examin_update_levelRadio" name="sys_examin_update_levelRadio" onClick="changeUpdateLevelRadio()" value="0" checked></input>��ֹ
				</s:if>
				<s:else>
				    <input type="radio" class="radio" id="sys_examin_update_levelRadio" name="sys_examin_update_levelRadio" onClick="changeUpdateLevelRadio()" value="1" checked></input>����
				    <input type="radio" class="radio" id="sys_examin_update_levelRadio" name="sys_examin_update_levelRadio" onClick="changeUpdateLevelRadio()" value="0"></input>��ֹ
				</s:else>
			</p>
			<p>
				����Ƿ�����HR����:
				<s:hidden id="sys_examin_lr_confirm" name="sys_examin_lr_confirm"></s:hidden>
		        <s:if test="sys_examin_lr_confirm==0">
		            <input type="radio" class="radio" id="sys_examin_lr_confirmRadio" name="sys_examin_lr_confirmRadio" onClick="changeLrConfirmRadio()" value="0" checked/>������
		            <input type="radio" class="radio" id="sys_examin_lr_confirmRadio" name="sys_examin_lr_confirmRadio" onClick="changeLrConfirmRadio()" value="1" />����
		        </s:if>
		        <s:else>
		            <input type="radio" class="radio" id="sys_examin_lr_confirmRadio" name="sys_examin_lr_confirmRadio" onClick="changeLrConfirmRadio()" value="0" />������
		            <input type="radio" class="radio" id="sys_examin_lr_confirmRadio" name="sys_examin_lr_confirmRadio" onClick="changeLrConfirmRadio()" value="1" checked/>����
		        </s:else>
			</p>
			<p>
				�Ӱ��Ƿ�����HRȷ��:
				<s:hidden id="sys_examin_ot_confirm" name="sys_examin_ot_confirm"></s:hidden>
		        <s:if test="sys_examin_ot_confirm==0">
		            <input type="radio" class="radio" id="sys_examin_ot_confirmRadio" name="sys_examin_ot_confirmRadio" onClick="changeOtConfirmRadio()" value="0" checked/>������
		            <input type="radio" class="radio" id="sys_examin_ot_confirmRadio" name="sys_examin_ot_confirmRadio" onClick="changeOtConfirmRadio()" value="1" />����
		        </s:if>
		        <s:else>
		            <input type="radio" class="radio" id="sys_examin_ot_confirmRadio" name="sys_examin_ot_confirmRadio" onClick="changeOtConfirmRadio()" value="0" />������
		            <input type="radio" class="radio" id="sys_examin_ot_confirmRadio" name="sys_examin_ot_confirmRadio" onClick="changeOtConfirmRadio()" value="1" checked/>����
		        </s:else>
			</p>
			<p>
				ÿ�¿��ڻ�����ʼ����:
			    <s:select name="beginMonth" list="#{1:'����',0:'����'}" id="beginMonth"></s:select>
			    <s:textfield id="beginDay" maxlength="2" size="3" name="beginDay" onkeyup="value=value.replace(/\D/g,'')" ></s:textfield>��
			</p>
			<p>
				�Ƿ񿪷�ˢ������:
		        <s:hidden id="sys_examin_shift_enable" name="sys_examin_shift_enable"></s:hidden>
		        <s:if test="sys_examin_shift_enable==0">
		            <input type="radio" class="radio"  name="shiftOpen" onClick="changeShiftRadio()" value="0" checked/>������
		            <input type="radio" class="radio"  name="shiftOpen" onClick="changeShiftRadio()" value="1" />����
		        </s:if>
		        <s:else>
		            <input type="radio" class="radio"  name="shiftOpen" onClick="changeShiftRadio()" value="0" />������
		            <input type="radio" class="radio"  name="shiftOpen" onClick="changeShiftRadio()" value="1" checked/>����
		        </s:else>
			</p>
			<p>
				�Ű���Ϣ�����Ƿ񸲸�:
		        <s:hidden id="sys_examin_shiftimport_override" name="sys_examin_shiftimport_override"></s:hidden>
		        <s:if test="sys_examin_shiftimport_override==0">
		            <input type="radio" class="radio"  name="shiftImportOpen" onClick="changeShiftImportRadio()" value="0" checked/>��
		            <input type="radio" class="radio"  name="shiftImportOpen" onClick="changeShiftImportRadio()" value="1" />��
		        </s:if>
		        <s:else>
		            <input type="radio" class="radio"  name="shiftImportOpen" onClick="changeShiftImportRadio()" value="0" />��
		            <input type="radio" class="radio"  name="shiftImportOpen" onClick="changeShiftImportRadio()" value="1" checked/>��
		        </s:else>
			</p>
			<p>
				<s:hidden id="leaveMode" name="leaveMode"></s:hidden>
			           ����ÿ�»��ܼ��㷽ʽ����
			    <s:select list="#{'day':'��','hour':'Сʱ'}"  id="templeaveMode" name="templeaveMode" value="%{leaveMode}" onchange="changeLeaveMode();" ></s:select>
			             ���㣬һ�����<s:textfield id="hoursPerDay" name="hoursPerDay" size="3" maxlength="2" onkeyup="value=value.replace(/\D/g,'')"></s:textfield>Сʱ
			</p>
			<p>
				�������ģʽ��<s:select id="sys_examin_leave_type" name="sys_examin_leave_type" list="#{'0':'���ģʽ','1':'��Сʱ','2':'����'}" emptyOption="false"></s:select>
			</p>
			<p>
				<br>
			    <input id="updateExaminBt" type="button" onclick="updateExaminConfirm();" value=" �� �� " />
			</p>
		</form>
	</div>
</div>
<script type="text/javascript">
	changeShiftRadio();
</script>
</body>
</html>
				
			

