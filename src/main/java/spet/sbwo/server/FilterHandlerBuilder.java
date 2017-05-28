package spet.sbwo.server;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Handler which can hold a servlet filter.
 *
 * @author Serban Petrescu
 */
public class FilterHandlerBuilder implements IServletHandlerBuilder {
    private String path;
    private Filter filter;
    private EnumSet<DispatcherType> dispatches;

    FilterHandlerBuilder() {
        this.dispatches = EnumSet.of(DispatcherType.REQUEST);
    }

    /**
    * Sets the path specification for the holder.
    */
    public FilterHandlerBuilder path(String path) {
        this.path = path;
        return this;
    }

    /**
    * Sets the underlying filter.
    */
    public FilterHandlerBuilder filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    /**
    * Sets the dispatcher type(s) to be used for the filter.
    */
    public FilterHandlerBuilder dispatches(EnumSet<DispatcherType> dispatches) {
        this.dispatches = dispatches;
        return this;
    }

    @Override
    public void build(ServletContextHandler root) {
        FilterHolder holder = new FilterHolder(this.filter);
        root.addFilter(holder, this.path, dispatches);
    }

}
