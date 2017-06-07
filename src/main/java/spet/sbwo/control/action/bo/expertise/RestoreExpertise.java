package spet.sbwo.control.action.bo.expertise;

import spet.sbwo.control.action.bo.base.RestoreEntity;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.data.table.Expertise;

public class RestoreExpertise extends RestoreEntity<Expertise> {

    public RestoreExpertise() {
        super(Expertise.class, ExpertiseChannel.class);
    }

}
