package spet.sbwo.control.controller.bo;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.control.mapper.PersonMapper;
import spet.sbwo.control.util.VCardBuilder;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.domain.EntityType;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonEmailAddress;
import spet.sbwo.data.table.PersonJuridical;
import spet.sbwo.data.table.PersonNatural;
import spet.sbwo.data.table.PersonTelephone;

public class PersonController extends JournalizedBaseController implements IDeleteRestoreController {
	private static final String CREATE_ERROR = "Database error during person creation.";
	private static final String READ_ERROR = "Database error during reading a person.";
	private static final String UPDATE_ERROR = "Database error while updating a person.";
	private static final String DELETE_ERROR = "Database error when deleting a person.";
	private static final String RESTORE_ERROR = "Database error while restoring a person.";
	private static final String EXPORT_ERROR = "Database error while exporting a person.";

	public PersonController(IDatabaseExecutorCreator database, int directDeleteInterval) {
		super(database, PersonChannel.class, directDeleteInterval);
	}

	public int create(PersonChannel data, String username) throws ControlException {
		IUserAction<Integer> action = (executor, user) -> {
			PersonMapper mapper = new PersonMapper(executor);
			Person person = mapper.toInternal(data);
			mapper.flush();
			setCreatedFields(person, user, executor);
			executor.commit();
			return person.getId();
		};
		return execute(username, CREATE_ERROR, action);
	}

	public PersonChannel read(int id) throws ControlException {
		IAction<PersonChannel> action = executor -> {
			Person p = executor.find(Person.class, id);
			if (p != null) {
				return new PersonMapper(executor).toExternal(p);
			} else {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, PersonChannel.class);
			}
		};
		return execute(READ_ERROR, action);
	}

	public void update(int id, PersonChannel data, String username) throws ControlException {
		IUserAction<Void> action = (executor, user) -> {
			PersonMapper mapper = new PersonMapper(executor);
			Person p = executor.find(Person.class, id);
			if (p != null) {
				mapper.merge(p, data);
				mapper.flush();
				setChangedFields(p, user, executor);
				executor.commit();
			} else {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, PersonChannel.class);
			}
			return null;
		};
		execute(username, UPDATE_ERROR, action);
	}

	@Override
	public void delete(int id, boolean force, String username) throws ControlException {
		IUserAction<Void> action = (executor, user) -> {
			Person p = executor.find(Person.class, id);
			if (p != null) {
				doEntityDeletion(p, user, force, executor);
				executor.commit();
			} else {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, PersonChannel.class);
			}
			return null;
		};
		execute(username, DELETE_ERROR, action);
	}

	@Override
	public void restore(int id, String username) throws ControlException {
		IUserAction<Void> action = (executor, user) -> {
			Person p = executor.find(Person.class, id);
			if (p != null && p.isDeleted()) {
				doEntityRestore(p, user, executor);
				executor.commit();
			} else {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, PersonChannel.class);
			}
			return null;
		};
		execute(username, RESTORE_ERROR, action);
	}

	public String export(int id) throws ControlException {
		IAction<String> action = executor -> {
			Person p = executor.find(Person.class, id);
			if (p != null) {
				VCardBuilder builder = new VCardBuilder();
				builder.name(getPersonName(p));
				builder.address(p.getLocation().getAddress());
				for (PersonEmailAddress email : p.getEmailAddresses()) {
					builder.email(email.getEmail(), email.isPrimary());
				}
				for (PersonTelephone phone : p.getTelephones()) {
					builder.phone(phone.getTelephone(), phone.isPrimary());
				}
				return builder.build();
			} else {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, PersonChannel.class);
			}
		};
		return execute(EXPORT_ERROR, action);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.PERSON;
	}

	protected String getPersonName(Person p) {
		if (p instanceof PersonNatural) {
			PersonNatural natural = (PersonNatural) p;
			if (natural.getFirstName() != null && natural.getLastName() != null) {
				return natural.getFirstName() + " " + natural.getLastName();
			}
		} else {
			PersonJuridical juridical = (PersonJuridical) p;
			return juridical.getName();
		}
		return "";
	}

}
