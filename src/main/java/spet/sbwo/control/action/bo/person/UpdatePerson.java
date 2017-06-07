package spet.sbwo.control.action.bo.person;

import spet.sbwo.control.action.bo.base.UpdateEntity;
import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.control.mapper.person.PersonMapper;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Person;

public class UpdatePerson extends UpdateEntity<Person, PersonChannel> {

    public UpdatePerson() {
        super(Person.class, PersonChannel.class);
    }

    @Override
    protected IMapper<Person, PersonChannel> mapper(IDatabaseExecutor executor) {
        return PersonMapper.newInstance(executor);
    }

}
