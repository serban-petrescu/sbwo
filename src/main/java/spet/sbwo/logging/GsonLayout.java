package spet.sbwo.logging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;

@Plugin(name = "GsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class GsonLayout extends AbstractStringLayout {
    private final Gson gson;

    protected GsonLayout(Charset charset) {
        super(charset);
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @PluginFactory
    public static GsonLayout createLayout(
        @PluginAttribute(value = "charset", defaultString = "UTF-8") final Charset charset) {
        return new GsonLayout(charset);
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public String toSerializable(LogEvent event) {
        LogEvent e = event instanceof MutableLogEvent ? ((MutableLogEvent) event).createMemento() : event;
        return gson.toJson(e) + "\r\n";
    }
}
