package spet.sbwo.control.mapper.expertise;

import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.data.table.Expertise;

import static spet.sbwo.control.mapper.Utils.NOT_NULL;

class ExpertiseToEntityPropertyMap extends PropertyMap<ExpertiseChannel, Expertise> {
    @Override
    protected void configure() {
        skip(destination.getId());
        skip(destination.getChangedBy());
        skip(destination.getChangedOn());
        skip(destination.getCreatedBy());
        skip(destination.getCreatedOn());
        skip(destination.isDeleted());
        skip(destination.getLocation());
        skip(destination.getFines());
        skip(destination.getCourt());
        skip(destination.getResponsible());
        when(NOT_NULL).map(source.getAdvance(), destination.getTariff().getAdvance());
        when(NOT_NULL).map(source.getPrice(), destination.getTariff().getPrice());
        when(NOT_NULL).map(source.getNextHearing(), destination.getNextHearing());
        when(NOT_NULL).map(source.getNote(), destination.getNote());
        when(NOT_NULL).map(source.getNumber(), destination.getNumber());
        when(NOT_NULL).map(source.getStatus(), destination.getStatus());
        when(NOT_NULL).map(source.getTitle(), destination.getTitle());
        when(NOT_NULL).map(source.getYear(), destination.getYear());

    }
}
