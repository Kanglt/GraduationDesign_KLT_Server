if(typeof(this["SystemCode"])=="undefined")
	this.SystemCode={};
SystemCode.getComponentSelectList=function(selectType,queryParentId,queryValue,page,successfulCallback,errorCallback){
	if(arguments.length!=6){
		alert("调用SystemCode.getComponentSelectList方法时提供的参数个数不正确，该方法需要下列参数selectType,queryParentId,queryValue,page,successCallback,errorCallback");return;}
	var params = {selectType:selectType,queryParentId:queryParentId,queryValue:queryValue,page:page};
	WebService.service("SystemCode.service?getComponentSelectList",params,successfulCallback,errorCallback);
};
SystemCode.getSystemCodeList=function(formData,page,successfulCallback,errorCallback){
	if(arguments.length!=4){
		alert("调用SystemCode.getSystemCodeList方法时提供的参数个数不正确，该方法需要下列参数formData,page,successCallback,errorCallback");return;}
	var params = {formData:formData,page:page};
	WebService.service("SystemCode.service?getSystemCodeList",params,successfulCallback,errorCallback);
};
SystemCode.getComponentTreeList=function(selectType,extraParams,successfulCallback,errorCallback){
	if(arguments.length!=4){
		alert("调用SystemCode.getComponentTreeList方法时提供的参数个数不正确，该方法需要下列参数selectType,extraParams,successCallback,errorCallback");return;}
	var params = {selectType:selectType,extraParams:extraParams};
	WebService.service("SystemCode.service?getComponentTreeList",params,successfulCallback,errorCallback);
};
SystemCode.newSystemCode=function(formData,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用SystemCode.newSystemCode方法时提供的参数个数不正确，该方法需要下列参数formData,successCallback,errorCallback");return;}
	var params = {formData:formData};
	WebService.service("SystemCode.service?newSystemCode",params,successfulCallback,errorCallback);
};
SystemCode.deleteSystemCode=function(id,recordVersion,successfulCallback,errorCallback){
	if(arguments.length!=4){
		alert("调用SystemCode.deleteSystemCode方法时提供的参数个数不正确，该方法需要下列参数id,recordVersion,successCallback,errorCallback");return;}
	var params = {id:id,recordVersion:recordVersion};
	WebService.service("SystemCode.service?deleteSystemCode",params,successfulCallback,errorCallback);
};
SystemCode.getSystemCode=function(id,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用SystemCode.getSystemCode方法时提供的参数个数不正确，该方法需要下列参数id,successCallback,errorCallback");return;}
	var params = {id:id};
	WebService.service("SystemCode.service?getSystemCode",params,successfulCallback,errorCallback);
};
SystemCode.updateSystemCode=function(id,recordVersion,formData,successfulCallback,errorCallback){
	if(arguments.length!=5){
		alert("调用SystemCode.updateSystemCode方法时提供的参数个数不正确，该方法需要下列参数id,recordVersion,formData,successCallback,errorCallback");return;}
	var params = {id:id,recordVersion:recordVersion,formData:formData};
	WebService.service("SystemCode.service?updateSystemCode",params,successfulCallback,errorCallback);
};
