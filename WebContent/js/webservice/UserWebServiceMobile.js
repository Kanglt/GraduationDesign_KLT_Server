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

UserWebServiceMobile.queryUserHomePageInfo=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserWebServiceMobile.queryUserHomePageInfo方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserWebServiceMobile.service?queryUserHomePageInfo",params,successfulCallback,errorCallback);
};

UserWebServiceMobile.queryUserFocusInfo=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserWebServiceMobile.queryUserFocusInfo方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserWebServiceMobile.service?queryUserFocusInfo",params,successfulCallback,errorCallback);
};

UserWebServiceMobile.queryUserFansInfo=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserWebServiceMobile.queryUserFansInfo方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserWebServiceMobile.service?queryUserFansInfo",params,successfulCallback,errorCallback);
};

UserWebServiceMobile.queryIsFocus=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserWebServiceMobile.queryIsFocus方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserWebServiceMobile.service?queryIsFocus",params,successfulCallback,errorCallback);
};

UserWebServiceMobile.deleteUserFocus=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserWebServiceMobile.deleteUserFocus方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserWebServiceMobile.service?deleteUserFocus",params,successfulCallback,errorCallback);
};

UserWebServiceMobile.queryPersonalInfo=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserWebServiceMobile.queryPersonalInfo方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserWebServiceMobile.service?queryPersonalInfo",params,successfulCallback,errorCallback);
};