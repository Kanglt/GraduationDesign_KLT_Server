if(typeof(this["UserPhotoWebServiceMobile"])=="undefined")
	this.TestMobile={};
UserPhotoWebServiceMobile.updateUserPhoto=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用UserPhotoWebServiceMobile.updateUserPhoto方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("UserPhotoWebServiceMobile.service?updateUserPhoto",params,successfulCallback,errorCallback);
};

