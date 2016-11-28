if(typeof(this["UploadFile"])=="undefined")
	this.UploadFile={};
UploadFile.getUploadFileList=function(formData,page,successfulCallback,errorCallback){
	if(arguments.length!=4){
		alert("调用UploadFile.getUploadFileList方法时提供的参数个数不正确，该方法需要下列参数formData,page,successCallback,errorCallback");return;}
	var params = {formData:formData,page:page};
	WebService.service("UploadFile.service?getUploadFileList",params,successfulCallback,errorCallback);
};
UploadFile.newUploadFile=function(formData,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UploadFile.newUploadFile方法时提供的参数个数不正确，该方法需要下列参数formData,successCallback,errorCallback");return;}
	var params = {formData:formData};
	WebService.service("UploadFile.service?newUploadFile",params,successfulCallback,errorCallback);
};
UploadFile.deleteUploadFile=function(id,recordVersion,successfulCallback,errorCallback){
	if(arguments.length!=4){
		alert("调用UploadFile.deleteUploadFile方法时提供的参数个数不正确，该方法需要下列参数id,recordVersion,successCallback,errorCallback");return;}
	var params = {id:id,recordVersion:recordVersion};
	WebService.service("UploadFile.service?deleteUploadFile",params,successfulCallback,errorCallback);
};
UploadFile.getUploadFile=function(id,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UploadFile.getUploadFile方法时提供的参数个数不正确，该方法需要下列参数id,successCallback,errorCallback");return;}
	var params = {id:id};
	WebService.service("UploadFile.service?getUploadFile",params,successfulCallback,errorCallback);
};
UploadFile.updateUploadFile=function(id,recordVersion,formData,successfulCallback,errorCallback){
	if(arguments.length!=5){
		alert("调用UploadFile.updateUploadFile方法时提供的参数个数不正确，该方法需要下列参数id,recordVersion,formData,successCallback,errorCallback");return;}
	var params = {id:id,recordVersion:recordVersion,formData:formData};
	WebService.service("UploadFile.service?updateUploadFile",params,successfulCallback,errorCallback);
};
