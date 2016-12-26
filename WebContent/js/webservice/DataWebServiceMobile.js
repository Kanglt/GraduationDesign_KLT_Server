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
DataWebServiceMobile.getDietData_foodMateria=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.getDietData_foodMateria方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?getDietData_foodMateria",params,successfulCallback,errorCallback);
};
DataWebServiceMobile.getDietDetaile_step=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.getDietDetaile_step方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?getDietDetaile_step",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.getDietData_with_dinneTime_dietType=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.getDietData_with_dinneTime_dietType方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?getDietData_with_dinneTime_dietType",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.getMusicData_with_musicType=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.getMusicData_with_musicType方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?getMusicData_with_musicType",params,successfulCallback,errorCallback);
};
