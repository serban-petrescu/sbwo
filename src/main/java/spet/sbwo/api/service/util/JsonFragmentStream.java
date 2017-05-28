package spet.sbwo.api.service.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import javax.ws.rs.core.StreamingOutput;

public class JsonFragmentStream implements StreamingOutput {
    private File file;

    public JsonFragmentStream(File file) {
        this.file = file;
    }

    @Override
    public void write(OutputStream output) throws IOException {
        boolean closed = false;
        output.write("[".getBytes());
        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if ("}".equals(line)) {
                    closed = true;
                } else if ("{".equals(line) && closed) {
                    line = ",{";
                }
                output.write(line.getBytes());
            }
        }
        output.write("]".getBytes());
    }

}
