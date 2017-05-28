package spet.sbwo.api.service.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

public class CsvMapIteratorHolder implements AutoCloseable {
    private final Map<String, Iterator<Map<String, String>>> result = new HashMap<>();
    private final List<CSVParser> parsers = new LinkedList<>();

    public CsvMapIteratorHolder(Map<String, List<String>> fields, List<Attachment> files) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withHeader();
        for (Attachment file : files) {
            addFile(fields, format, file);
        }
    }

    protected void addFile(Map<String, List<String>> fields, CSVFormat format, Attachment file) throws IOException {
        String filename = file.getContentDisposition().getFilename().replaceFirst("[.][^.]+$", "");
        if (fields.containsKey(filename)) {
            CSVParser parser = new CSVParser(new InputStreamReader(file.getDataHandler().getInputStream()), format);
            result.put(filename, new CsvMapIterator(parser.iterator()));
            parsers.add(parser);
        }
    }

    public Map<String, Iterator<Map<String, String>>> getResult() {
        return result;
    }

    @Override
    public void close() throws IOException {
        for (CSVParser parser : parsers) {
            parser.close();
        }
    }

}