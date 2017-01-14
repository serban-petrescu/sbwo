package spet.sbwo.control.importer;

import java.util.Iterator;
import java.util.Map;

import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.table.User;

interface ISuite {

	ISuite process(Map<String, Iterator<Map<String, String>>> data, User user) throws DatabaseException;

	ISuite persist() throws DatabaseException;

}
