package spet.sbwo.integration.api.geocode;

import spet.sbwo.data.table.Location;
import spet.sbwo.integration.api.geocode.model.Position;

public interface IGeocodingApi {

	Position geocode(String address);

	Position geocode(Location location);

}
