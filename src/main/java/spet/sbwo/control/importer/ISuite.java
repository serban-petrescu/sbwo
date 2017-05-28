package spet.sbwo.control.importer;

import java.util.Iterator;
import java.util.Map;

import spet.sbwo.data.table.User;

public interface ISuite {

	ISuite process(Map<String, Iterator<Map<String, String>>> data, User user) ;

	ISuite persist() ;

}
