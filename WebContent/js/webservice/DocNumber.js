if(typeof(this["DocNumber"])=="undefined")
	this.DocNumber={};
DocNumber.getDocNumberList=function(formData,page,successfulCallback,errorCallback){
	if(arguments.length!=4){
		alert("调用DocNumber.getDocNumberList方法时提供的参数个数不正确，该方法需要下列参数formData,page,successCallback,errorCallback");return;}
	var params = {formData:formData,page:page};
	WebService.service("DocNumber.service?getDocNumberList",params,successfulCallback,errorCallback);
};
DocNumber.newDocNumber=function(formData,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DocNumber.newDocNumber方法时提供的参数个数不正确，该方法需要下列参数formData,successCallback,errorCallback");return;}
	var params = {formData:formData};
	WebService.service("DocNumber.service?newDocNumber",params,successfulCallback,errorCallback);
};
DocNumber.deleteDocNumber=function(id,recordVersion,successfulCallback,errorCallback){
	if(arguments.length!=4){
		alert("调用DocNumber.deleteDocNumber方法时提供的参数个数不正确，该方法需要下列参数id,recordVersion,successCallback,errorCallback");return;}
	var params = {id:id,recordVersion:recordVersion};
	WebService.service("DocNumber.service?deleteDocNumber",params,successfulCallback,errorCallback);
};
DocNumber.getDocNumber=function(id,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DocNumber.getDocNumber方法时提供的参数个数不正确，该方法需要下列参数id,successCallback,errorCallback");return;}
	var params = {id:id};
	WebService.service("DocNumber.service?getDocNumber",params,successfulCallback,errorCallback);
};
DocNumber.updateDocNumber=function(id,recordVersion,formData,successfulCallback,errorCallback){
	if(arguments.length!=5){
		alert("调用DocNumber.updateDocNumber方法时提供的参数个数不正确，该方法需要下列参数id,recordVersion,formData,successCallback,errorCallback");return;}
	var params = {id:id,recordVersion:recordVersion,formData:formData};
	WebService.service("DocNumber.service?updateDocNumber",params,successfulCallback,errorCallback);
};
