package spet.sbwo.control.importer.base;

import spet.sbwo.data.table.User;

import java.util.Iterator;
import java.util.Map;

public interface ISuite {

    ISuite process(Map<String, Iterator<Map<String, String>>> data, User user);

    ISuite persist();

}
