if(typeof(this["DataWebServiceMobile"])=="undefined")
	this.TestMobile={};
DataWebServiceMobile.getTrainingData=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.getTrainingData方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?getTrainingData",params,successfulCallback,errorCallback);
};
DataWebServiceMobile.queryDietData=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.queryDietData方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?queryDietData",params,successfulCallback,errorCallback);
};


