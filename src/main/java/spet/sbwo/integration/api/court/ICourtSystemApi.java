package spet.sbwo.integration.api.court;

import spet.sbwo.integration.api.court.model.Case;

@FunctionalInterface
public interface ICourtSystemApi {
	public Case read(String number);
}
