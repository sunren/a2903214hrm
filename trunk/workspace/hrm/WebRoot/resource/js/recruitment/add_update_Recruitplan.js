/*
 * Compressed by JSA(www.xidea.org)
 */
function inputNoAbnormalLetter(_){_=window.event||_;var $=_.keyCode||_.which;if(($>32&&$<48)||($>57&&$<65)||($>90&&$<97))return false}function onlyNumber(_){_=window.event||_;var $=_.keyCode||_.which;if($==8)return true;if($<46||$>57)return false}function createPlan(){document.getElementById("DraftOrSubmit").value="draft";document.getElementById("btnSaveDraft").disabled="disabled";document.getElementById("btnSubmitRP").disabled="disabled";document.addrecruitplan.submit()}function submitPlan(){document.getElementById("DraftOrSubmit").value="submit";document.getElementById("btnSaveDraft").disabled="disabled";document.getElementById("btnSubmitRP").disabled="disabled";document.addrecruitplan.submit()}function deliver(){document.getElementById("oldComments").value=document.getElementById("comments").value}function updatePlanSubmit(){var $,_;_=document.getElementById("recruitplan.recpStatus").value;$=document.getElementById("oldComments").value==document.getElementById("comments").value;if($&&(_==21)){if(confirm("\u60a8\u7684\u5907\u6ce8\u6ca1\u6709\u66f4\u65b0\uff0c\u786e\u5b9e\u8981\u63d0\u4ea4\u5417\uff1f")){document.getElementById("btnUpdate").disabled="disable";document.getElementById("UpdateRecruitplan").submit()}}else{document.getElementById("btnUpdate").disabled="disable";document.getElementById("UpdateRecruitplan").submit()}}