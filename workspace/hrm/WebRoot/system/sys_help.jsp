<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<title>ϵͳ-�����ļ�</title>
<style type="text/css">@import url("../resource/css/style.css");</style>
<link rel="stylesheet" type="text/css" href="../resource/css/tab.css" />
<script type="text/javascript" src="../resource/js/dwr/tabpane.js"></script>
<script type="text/javascript" src="<s:url value="/resource/js/prototype.js"/>?ver=1.5"></script>
<script type="text/javascript" src="<s:url value="/resource/js/util.js"/>"></script>
<script type="text/javascript" src="../configuration/inc.js"></script>
<script type="text/javascript">
var help_index='<%=request.getParameter("index")%>';
if(help_index==null){help_index=0;}
if(/^\d+$/.test(help_index)){
	WebFXTabPane.setCookie( "webfxtab_tabPane1", help_index);
}else{
	WebFXTabPane.setCookie( "webfxtab_tabPane1", help_index);
}
</script>
<base target="_self">
</head>
<body>
<div class="tab-pane" id="tabPane1">
   <div class="tab-page" id="tabPage1">
      <h2 class="tab">Ա����Ϣ</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>Ա������</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��ѯ</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>Ա��ͨѶ¼</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
   </div>

   <div class="tab-page" id="tabPage2">
      <h2 class="tab">��˾��Ϣ</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>������˾��Ϣ</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��˾��Ϣ�б�</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
   </div>

   <div class="tab-page" id="tabPage3">
      <h2 class="tab">н��</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>Ա��н��ά��</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��н��ά��</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��н������</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>Ա��н�ʵķ���</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
   </div>

   <div class="tab-page" id="tabPage4">
      <h2 class="tab">��ѵ</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��ѵ����</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��ѵ����</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��Դ����</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>���߲���</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>�����ʾ�</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��ѵ����</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>�Ծ�����</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
   </div>

   <div class="tab-page" id="tabPage5">
      <h2 class="tab">����</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>����</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
	</div>

   <div class="tab-page" id="tabPage6">
   	   <h2 class="tab">��Ч</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��Ч</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
  </div>

   <div class="tab-page" id="tabPage7">
   	   <h2 class="tab">��Ƹ</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��Ƹ����ά��</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��Ƹ����ά��</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>������Ա��Ϣά��</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
  </div>

   <div class="tab-page" id="tabPage8">
   	   <h2 class="tab">����</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>Ա���������ϱ���</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>Ա��н�ʱ���</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>Ա����ѵ���ϱ���</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
  </div>

   <div class="tab-page" id="tabPage9">
   	   <h2 class="tab">ϵͳ</h2>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>����û�</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>��ɫ����</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>������������</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>������Ϣ
				</td>
			</tr>
	      </tbody>
         </table>
        <table cellpadding="0" cellspacing="1" width="100%" class="basictable">
	      <thead>
		    <tr><th>н������</th></tr>
	      </thead>
	      <tbody id="tabPage1items">
		    <tr>
		    	<td>н�����ð���</td>
			</tr>
	      </tbody>
         </table>
  </div>
                                                                                                                               
                                                                                                     
                                                                                                    
                                                                
                                                                            
</div>        
</body>
</html>
