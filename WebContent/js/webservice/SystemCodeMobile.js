if(typeof(this["SystemCodeMobile"])=="undefined")
	this.SystemCodeMobile={};
SystemCodeMobile.getComponentSelectList=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用SystemCodeMobile.getComponentSelectList方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("SystemCodeMobile.service?getComponentSelectList",params,successfulCallback,errorCallback);
};
