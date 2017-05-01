package spet.sbwo.control.controller.bo;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.JournalChannel;

public interface IBoController<C extends JournalChannel> {

	int create(C data, String username) throws ControlException;

	C read(int id) throws ControlException;

	void update(int id, C data, String username) throws ControlException;

	void delete(int id, String username) throws ControlException;

	void restore(int id, String username) throws ControlException;

}