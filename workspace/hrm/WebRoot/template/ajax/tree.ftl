<#--
/*
 * $Id: pom.xml 560558 2007-07-28 15:47:10Z apetrelli $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
 <script type="text/javascript">
        <!--
        dojo.require("dojo.lang.*");
        dojo.require("dojo.widget.*");
        dojo.require("dojo.widget.Tree");
		dojo.require("dojo.widget.TreeContextMenu");
		dojo.require("dojo.widget.TreeRPCController");
		dojo.require("dojo.widget.TreeSelector");
 		dojo.require("dojo.widget.TreeNode");
		dojo.require("dojo.collections.*");
        // dojo.hostenv.writeIncludes();
		/**
		* 移动（Drag and Drop）一个节点到新的节点下
		*/
		function moveNode(child, newParent, index) {
		  //alert("childId=" + child.objectId + ";\nnewParentId=" + newParent.objectId + ";\nIndex=" + index);
		
		} 
       -->
 </script>
<div dojoType="TreeRPCController" RPCUrl="" widgetId="treeController" doMove="moveNode"></div>
<div dojoType="Tree"   
	<#if parameters.blankIconSrc?exists>
	gridIconSrcT="<@s.url value='${parameters.blankIconSrc}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcL?exists>
	gridIconSrcL="<@s.url value='${parameters.gridIconSrcL}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcV?exists>
	gridIconSrcV="<@s.url value='${parameters.gridIconSrcV}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcP?exists>
	gridIconSrcP="<@s.url value='${parameters.gridIconSrcP}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcC?exists>
	gridIconSrcC="<@s.url value='${parameters.gridIconSrcC}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcX?exists>
	gridIconSrcX="<@s.url value='${parameters.gridIconSrcX}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcY?exists>
	gridIconSrcY="<@s.url value='${parameters.gridIconSrcY}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcZ?exists>
	gridIconSrcZ="<@s.url value='${parameters.gridIconSrcZ}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.expandIconSrcPlus?exists>
	expandIconSrcPlus="<@s.url value='${parameters.expandIconSrcPlus}' includeParams='none'/>"
	</#if>
	<#if parameters.expandIconSrcMinus?exists>
	expandIconSrcMinus="<@s.url value='${parameters.expandIconSrcMinus?html}' includeParams='none'/>"
	</#if>
	<#if parameters.iconWidth?exists>
	iconWidth="<@s.url value='${parameters.iconWidth?html}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.iconHeight?exists>
	iconHeight="<@s.url value='${parameters.iconHeight?html}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.toggleDuration?exists>
	toggleDuration=${parameters.toggleDuration?c}
	</#if>
	<#if parameters.templateCssPath?exists>
	templateCssPath="<@s.url value='${parameters.templateCssPath}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.showGrid?exists>
	showGrid="${parameters.showGrid?default(true)?string}"
	</#if>
	<#if parameters.showRootGrid?exists>
	showRootGrid="${parameters.showRootGrid?default(true)?string}"
	</#if>
    <#if parameters.id?exists>
    id="${parameters.id?html}"
    </#if>
    <#if parameters.treeSelectedTopic?exists>
    publishSelectionTopic="${parameters.treeSelectedTopic?html}"
    </#if>
    <#if parameters.treeExpandedTopic?exists>
    publishExpandedTopic="${parameters.treeExpandedTopic?html}"
    </#if>
    <#if parameters.treeCollapsedTopic?exists>
    publishCollapsedTopic="${parameters.treeCollapsedTopic?html}"
    </#if>
    <#if parameters.toggle?exists>
    toggle="${parameters.toggle?html}"
    </#if>
    controller="treeController"
    >
    <#if parameters.label?exists>
    <div dojoType="TreeNode" title="${parameters.label?html}"
    <#if parameters.nodeIdProperty?exists>
    id="${stack.findValue(parameters.nodeIdProperty)}"
    <#else>
    id="${parameters.id}_root"
    </#if>
    >
    <#elseif parameters.rootNode?exists>
    ${stack.push(parameters.rootNode)}
    <#include "/${parameters.templateDir}/ajax/treenode-include.ftl" />
    <#assign oldNode = stack.pop()/> <#-- pop the node off of the stack, but don't show it -->
    </#if>
