package spet.sbwo.control.action.bo.person;

import spet.sbwo.control.action.bo.base.UpdateEntity;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.control.mapper.BaseMapper;
import spet.sbwo.control.mapper.PersonMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Person;

public class UpdatePerson extends UpdateEntity<Person, PersonChannel> {

    public UpdatePerson() {
        super(Person.class, PersonChannel.class);
    }

    @Override
    protected BaseMapper<Person, PersonChannel> mapper(IDatabaseExecutor executor) {
        return new PersonMapper(executor);
    }

}
