package spet.sbwo.api.service.util;

import org.apache.commons.csv.CSVRecord;

import java.util.Iterator;
import java.util.Map;

public class CsvMapIterator implements Iterator<Map<String, String>> {
    private Iterator<CSVRecord> base;

    public CsvMapIterator(Iterator<CSVRecord> base) {
        this.base = base;
    }

    @Override
    public boolean hasNext() {
        return base.hasNext();
    }

    @Override
    public Map<String, String> next() {
        return base.next().toMap();
    }

}
