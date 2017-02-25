package spet.sbwo.control.action.misc;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.LocationChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Location;
import spet.sbwo.integration.api.geocode.IGeocodingApi;
import spet.sbwo.integration.api.geocode.model.Position;

public class GeocodeLocation extends BaseDatabaseAction<Location, Void> {
	private final IGeocodingApi api;

	public GeocodeLocation(IGeocodingApi api) {
		super(LocationChannel.class);
		this.api = api;
	}

	@Override
	protected Void doRun(Location input, IDatabaseExecutor executor) throws ControlException, DatabaseException {
		Position position = api.geocode(input);
		if (position != null) {
			input.setLatitude(position.getLatitude());
			input.setLongitude(position.getLongitude());
		}
		input.setGeocoded(true);
		return null;
	}

}
