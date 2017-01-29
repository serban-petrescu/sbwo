package spet.sbwo.control.controller;

import spet.sbwo.data.access.IDatabaseExecutorCreator;

public class BaseMainController extends BaseController {
	protected final IDatabaseExecutorCreator database;

	public BaseMainController(IDatabaseExecutorCreator database) {
		super();
		this.database = database;
	}
}
