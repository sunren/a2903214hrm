/*
 * Compressed by JSA(www.xidea.org)
 */
if(hrm==null)var hrm={};if(hrm.cp==null)hrm.cp={};if(HRMCp==null)var HRMCp=hrm.cp;hrm.cp.viewDetail=function(_){document.getElementById("id").value=_;var $="viewdetail.action?detailid="+_;window.location=$};hrm.cp.checkAccountValid=function($){var _=/^\s*[a-z0-9A-Z_\<\>\-\*\.\(\)]+\s*$/;if(!_.test($))return false;return true}