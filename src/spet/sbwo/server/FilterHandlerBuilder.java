package spet.sbwo.server;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class FilterHandlerBuilder implements IHandlerBuilder{
	private String path;
	private Filter filter;
	private EnumSet<DispatcherType> dispatches;
	
	FilterHandlerBuilder() {
		this.dispatches = EnumSet.of(DispatcherType.REQUEST);
	}
	
	public FilterHandlerBuilder setPath(String path) {
		this.path = path;
		return this;
	}
	
	public FilterHandlerBuilder setFilter(Filter filter) {
		this.filter = filter;
		return this;
	}

	public FilterHandlerBuilder setDispatches(EnumSet<DispatcherType> dispatches) {
		this.dispatches = dispatches;
		return this;
	}

	@Override
	public void build(ServletContextHandler root) {
		FilterHolder holder = new FilterHolder(this.filter);
		root.addFilter(holder, this.path, dispatches);
	}
	
	
}
