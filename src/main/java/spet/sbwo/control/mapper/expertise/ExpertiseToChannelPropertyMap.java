package spet.sbwo.control.mapper.expertise;

import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.data.table.Expertise;

class ExpertiseToChannelPropertyMap extends PropertyMap<Expertise, ExpertiseChannel> {

    @Override
    protected void configure() {
        map(source.getTariff().getAdvance(), destination.getAdvance());
        map(source.getTariff().getPrice(), destination.getPrice());
    }
}
