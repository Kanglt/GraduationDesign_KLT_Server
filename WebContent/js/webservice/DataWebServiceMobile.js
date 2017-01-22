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

DataWebServiceMobile.getTotalTraining=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.getTotalTraining方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?getTotalTraining",params,successfulCallback,errorCallback);
};


DataWebServiceMobile.getAddTraining=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.addTraining方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?addTraining",params,successfulCallback,errorCallback);
};



DataWebServiceMobile.queryUserTrainingData=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.queryUserTrainingData方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?queryUserTrainingData",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.deleteUserTraining=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.deleteUserTraining方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?deleteUserTraining",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.queryUserTrainingTotalRecord=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.queryUserTrainingTotalRecord方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?queryUserTrainingTotalRecord",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.addUserTrainingRecord=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.addUserTrainingRecord方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?addUserTrainingRecord",params,successfulCallback,errorCallback);
};
DataWebServiceMobile.queryRecommendedTraining=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.queryRecommendedTraining方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?queryRecommendedTraining",params,successfulCallback,errorCallback);
};
DataWebServiceMobile.getUserBodyData=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.getUserBodyData方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?getUserBodyData",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.addUserBodyData=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.addUserBodyData方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?addUserBodyData",params,successfulCallback,errorCallback);
};


DataWebServiceMobile.addUserDynamic=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.addUserDynamic方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?addUserDynamic",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.queryUserPersonalDynamic=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.queryUserPersonalDynamic方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?queryUserPersonalDynamic",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.queryUserFocusDynamic=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.queryUserFocusDynamic方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?queryUserFocusDynamic",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.addUserFocus=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.addUserFocusc方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?addUserFocus",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.queryHotDynamic=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.queryHotDynamic方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?queryHotDynamic",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.queryDynamicComments=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.queryDynamicComments方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?queryDynamicComments",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.addDynamicComments=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.addDynamicComments方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?addDynamicComments",params,successfulCallback,errorCallback);
};

DataWebServiceMobile.deleteDynamicComments=function(jsonDataStr,successfulCallback,errorCallback){
	if(arguments.length!=3){
		alert("调用DataWebServiceMobile.deleteDynamicComments方法时提供的参数个数不正确，该方法需要下列参数jsonDataStr,successCallback,errorCallback");return;}
	var params = {jsonDataStr:jsonDataStr};
	WebService.service("DataWebServiceMobile.service?deleteDynamicComments",params,successfulCallback,errorCallback);
};