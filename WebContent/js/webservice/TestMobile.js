if(typeof(this["TestMobile"])=="undefined")
	this.TestMobile={};
TestMobile.getTestListForMobile=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用TestMobile.getTestListForMobile方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("TestMobile.service?getTestListForMobile",params,successfulCallback,errorCallback);
};
