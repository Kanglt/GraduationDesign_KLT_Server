if(typeof(this["SystemCodeDetail"])=="undefined")
	this.SystemCodeDetail={};
SystemCodeDetail.getSystemCodeDetailList=function(queryCodeName,queryCodeId,page,successfulCallback,errorCallback){
	if(arguments.length!=5){
		alert("调用SystemCodeDetail.getSystemCodeDetailList方法时提供的参数个数不正确，该方法需要下列参数queryCodeName,queryCodeId,page,successCallback,errorCallback");return;}
	var params = {queryCodeName:queryCodeName,queryCodeId:queryCodeId,page:page};
	WebService.service("SystemCodeDetail.service?getSystemCodeDetailList",params,successfulCallback,errorCallback);
};
SystemCodeDetail.newSystemCodeDetail=function(codeId,formData,successfulCallback,errorCallback){
	if(arguments.length!=4){
		alert("调用SystemCodeDetail.newSystemCodeDetail方法时提供的参数个数不正确，该方法需要下列参数codeId,formData,successCallback,errorCallback");return;}
	var params = {codeId:codeId,formData:formData};
	WebService.service("SystemCodeDetail.service?newSystemCodeDetail",params,successfulCallback,errorCallback);
};
SystemCodeDetail.deleteSystemCodeDetail=function(id,recordVersion,successfulCallback,errorCallback){
	if(arguments.length!=4){
		alert("调用SystemCodeDetail.deleteSystemCodeDetail方法时提供的参数个数不正确，该方法需要下列参数id,recordVersion,successCallback,errorCallback");return;}
	var params = {id:id,recordVersion:recordVersion};
	WebService.service("SystemCodeDetail.service?deleteSystemCodeDetail",params,successfulCallback,errorCallback);
};
SystemCodeDetail.getSystemCodeDetail=function(id,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用SystemCodeDetail.getSystemCodeDetail方法时提供的参数个数不正确，该方法需要下列参数id,successCallback,errorCallback");return;}
	var params = {id:id};
	WebService.service("SystemCodeDetail.service?getSystemCodeDetail",params,successfulCallback,errorCallback);
};
SystemCodeDetail.updateSystemCodeDetail=function(id,recordVersion,formData,successfulCallback,errorCallback){
	if(arguments.length!=5){
		alert("调用SystemCodeDetail.updateSystemCodeDetail方法时提供的参数个数不正确，该方法需要下列参数id,recordVersion,formData,successCallback,errorCallback");return;}
	var params = {id:id,recordVersion:recordVersion,formData:formData};
	WebService.service("SystemCodeDetail.service?updateSystemCodeDetail",params,successfulCallback,errorCallback);
};
