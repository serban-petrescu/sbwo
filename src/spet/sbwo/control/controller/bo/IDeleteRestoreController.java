package spet.sbwo.control.controller.bo;

import spet.sbwo.control.ControlException;

public interface IDeleteRestoreController extends IEntityController {

	void restore(int id, String username) throws ControlException;

	void delete(int id, boolean force, String username) throws ControlException;
}
