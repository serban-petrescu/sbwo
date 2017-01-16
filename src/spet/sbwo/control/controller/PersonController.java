package spet.sbwo.control.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.control.mapper.PersonMapper;
import spet.sbwo.control.util.VCardBuilder;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonEmailAddress;
import spet.sbwo.data.table.PersonJuridical;
import spet.sbwo.data.table.PersonNatural;
import spet.sbwo.data.table.PersonTelephone;

public class PersonController extends JournalizedBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

	public PersonController(DatabaseFacade database) {
		super(database);
	}

	public int createPerson(PersonChannel data, String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(true)) {
			PersonMapper mapper = new PersonMapper(executor);
			Person p = mapper.toInternal(data);
			mapper.flush();
			this.setCreatedFields(p, username, executor);
			executor.commit();
			return p.getId();
		} catch (DatabaseException e) {
			LOG.error("Database error during person creation.");
			throw new ControlException(e, PersonChannel.class);
		}
	}

	public PersonChannel readPerson(int id) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			Person p = executor.find(Person.class, id);
			if (p != null) {
				return new PersonMapper(executor).toExternal(p);
			} else {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, PersonChannel.class);
			}
		} catch (DatabaseException e) {
			LOG.error("Database error during reading a person.");
			throw new ControlException(e, PersonChannel.class);
		}
	}

	public void updatePerson(int id, PersonChannel data, String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(true)) {
			PersonMapper mapper = new PersonMapper(executor);
			Person p = executor.find(Person.class, id);
			if (p != null) {
				mapper.merge(p, data);
				mapper.flush();
				this.setChangedFields(p, username, executor);
				executor.commit();
			} else {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, PersonChannel.class);
			}
		} catch (DatabaseException e) {
			LOG.warn("Database error while updating a person.");
			throw new ControlException(e, PersonChannel.class);
		}
	}

	public void deletePerson(int id, boolean force, String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			Person p = executor.find(Person.class, id);
			if (p != null) {
				this.doEntityDeletion(p, username, force, executor);
				executor.commit();
			} else {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, PersonChannel.class);
			}
		} catch (DatabaseException e) {
			LOG.warn("Database error when deleting a person.");
			throw new ControlException(e, PersonChannel.class);
		}
	}

	public void restorePerson(int id, String username) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			Person p = executor.find(Person.class, id);
			if (p != null && p.isDeleted()) {
				this.doEntityRestore(p, username, executor);
				executor.commit();
			} else {
				throw new ControlException(ControlError.ENTITY_NOT_FOUND, PersonChannel.class);
			}
		} catch (DatabaseException e) {
			LOG.warn("Database error while restoring a person.");
			throw new ControlException(e, PersonChannel.class);
		}
	}

	public String exportPerson(int id) throws ControlException {
		try (IDatabaseExecutor executor = this.database.buildExecutor(false)) {
			Person p = executor.find(Person.class, id);
			if (p != null) {
				VCardBuilder builder = new VCardBuilder();
				if (p instanceof PersonNatural) {
					PersonNatural natural = (PersonNatural) p;
					if (natural.getFirstName() != null && natural.getLastName() != null) {
						builder.name(natural.getFirstName() + " " + natural.getLastName());
					}
				} else {
					PersonJuridical juridical = (PersonJuridical) p;
					builder.name(juridical.getName());
				}
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
		} catch (DatabaseException e) {
			LOG.error("Database error during exporting a person.");
			throw new ControlException(e, PersonChannel.class);
		}
	}

}
