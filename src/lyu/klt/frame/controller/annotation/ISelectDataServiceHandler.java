package lyu.klt.frame.controller.annotation;

import lyu.klt.frame.module.systemcode.SelectProcedure;
import lyu.klt.frame.module.systemcode.TreeSelectProcedure;

public interface ISelectDataServiceHandler {

	public SelectProcedure getSelectProcedure(String selectType)
			throws Exception;

	public TreeSelectProcedure getTreeSelectProcedure(String selectType)
			throws Exception;
}
