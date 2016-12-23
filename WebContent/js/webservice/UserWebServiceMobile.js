if(typeof(this["UserWebServiceMobile"])=="undefined")
	this.TestMobile={};
UserWebServiceMobile.login=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserWebServiceMobile.login方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserWebServiceMobile.service?login",params,successfulCallback,errorCallback);
};

UserWebServiceMobile.getUserInformation=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserWebServiceMobile.getUserInformation方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserWebServiceMobile.service?getUserInformation",params,successfulCallback,errorCallback);
};

UserWebServiceMobile.updateUserInformation=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserWebServiceMobile.updateUserInformation方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserWebServiceMobile.service?updateUserInformation",params,successfulCallback,errorCallback);
};
