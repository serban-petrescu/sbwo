package spet.sbwo.control.mapper;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.PersonChannel;
import spet.sbwo.control.channel.PersonChannel.BankAccount;
import spet.sbwo.control.channel.PersonChannel.Email;
import spet.sbwo.control.channel.PersonChannel.Telephone;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.domain.IdentityCardType;
import spet.sbwo.data.domain.PersonType;
import spet.sbwo.data.table.Person;
import spet.sbwo.data.table.PersonBankAccount;
import spet.sbwo.data.table.PersonEmailAddress;
import spet.sbwo.data.table.PersonJuridical;
import spet.sbwo.data.table.PersonNatural;
import spet.sbwo.data.table.PersonTelephone;

public class PersonMapper extends JournalMapper<Person, PersonChannel> {
	private BankAccountMapper bankAccountMapper;
	private TelephoneMapper telephoneMapper;
	private EmailMapper emailMapper;
	private LocationMapper locationMapper;

	public PersonMapper(IDatabaseExecutor executor) {
		super(executor);
		this.bankAccountMapper = new BankAccountMapper(executor);
		this.telephoneMapper = new TelephoneMapper(executor);
		this.emailMapper = new EmailMapper(executor);
		this.locationMapper = new LocationMapper(executor);
	}

	@Override
	public void merge(PersonChannel result, Person input) throws ControlException {
		super.merge(result, input);

		result.setBankAccounts(this.bankAccountMapper.toExternal(input.getBankAccounts()));
		result.setEmailAddresses(this.emailMapper.toExternal(input.getEmailAddresses()));
		result.setType(input.getType() == null ? null : input.getType().ordinal());
		result.setLocation(this.locationMapper.toExternal(input.getLocation()));
		result.setTelephones(this.telephoneMapper.toExternal(input.getTelephones()));

		if (input instanceof PersonNatural) {
			PersonNatural person = (PersonNatural) input;
			result.setFirstName(person.getFirstName());
			result.setLastName(person.getLastName());
			result.setIdentityCardNumber(person.getIdentityCardNumber());
			result.setIdentityCardType(
					person.getIdentityCardType() == null ? null : person.getIdentityCardType().ordinal());
			result.setPersonalNumber(person.getPersonalNumber());
		} else if (input instanceof PersonJuridical) {
			PersonJuridical person = (PersonJuridical) input;
			result.setName(person.getName());
			result.setRegNumber(person.getRegNumber());
			result.setIdNumber(person.getIdNumber());
			result.setJointStock(person.getJointStock());
		}
	}

	@Override
	public void merge(Person internal, PersonChannel external) throws ControlException {
		super.merge(internal, external);

		internal.setBankAccounts(
				ifNotNullForEach(bankAccountMapper.merge(internal.getBankAccounts(), external.getBankAccounts()),
						internal, PersonBankAccount::setPerson));
		addOperations(bankAccountMapper);

		internal.setTelephones(
				ifNotNullForEach(telephoneMapper.merge(internal.getTelephones(), external.getTelephones()), internal,
						PersonTelephone::setPerson));
		addOperations(telephoneMapper);

		internal.setEmailAddresses(
				ifNotNullForEach(emailMapper.merge(internal.getEmailAddresses(), external.getEmailAddresses()),
						internal, PersonEmailAddress::setPerson));
		addOperations(emailMapper);

		internal.setLocation(locationMapper.toInternalMandatory(internal.getLocation(), external.getLocation()));
		addOperations(0, locationMapper);

		if (internal instanceof PersonNatural) {
			PersonNatural person = (PersonNatural) internal;
			person.setFirstName(external.getFirstName());
			person.setLastName(external.getLastName());
			person.setIdentityCardNumber(external.getIdentityCardNumber());
			person.setIdentityCardType(this.getEnumValue(IdentityCardType.values(), external.getIdentityCardType()));
			person.setPersonalNumber(external.getPersonalNumber());
		} else if (internal instanceof PersonJuridical) {
			PersonJuridical person = (PersonJuridical) internal;
			person.setName(external.getName());
			person.setRegNumber(external.getRegNumber());
			person.setIdNumber(external.getIdNumber());
			person.setJointStock(external.getJointStock());
		}
	}

	@Override
	protected Person newInternal(PersonChannel external) throws ControlException {
		if (external.getType() != null) {
			if (external.getType() == PersonType.NATURAL.ordinal()) {
				return new PersonNatural();
			} else if (external.getType() == PersonType.JURIDICAL.ordinal()) {
				return new PersonJuridical();
			}
		}
		throw new ControlException(ControlError.INVALID_PROPERTY_VALUE, PersonChannel.class);
	}

	@Override
	protected PersonChannel newExternal(Person internal) {
		return new PersonChannel();
	}

	public static class EmailMapper extends BaseMapper<PersonEmailAddress, Email> {
		public EmailMapper(IDatabaseExecutor executor) {
			super(executor);
		}

		@Override
		protected PersonEmailAddress newInternal(Email external) throws ControlException {
			return new PersonEmailAddress();
		}

		@Override
		protected Email newExternal(PersonEmailAddress internal) throws ControlException {
			return new Email();
		}

		@Override
		public void merge(Email external, PersonEmailAddress internal) throws ControlException {
			super.merge(external, internal);
			external.setEmail(internal.getEmail());
			external.setName(internal.getName());
			external.setPrimary(internal.isPrimary());
		}

		@Override
		public void merge(PersonEmailAddress internal, Email external) throws ControlException {
			super.merge(internal, external);
			internal.setEmail(external.getEmail());
			internal.setName(external.getName());
			this.ifNotNull(external.isPrimary(), internal::setPrimary);
		}
	}

	public static class TelephoneMapper extends BaseMapper<PersonTelephone, Telephone> {
		public TelephoneMapper(IDatabaseExecutor executor) {
			super(executor);
		}

		@Override
		protected PersonTelephone newInternal(Telephone external) throws ControlException {
			return new PersonTelephone();
		}

		@Override
		protected Telephone newExternal(PersonTelephone internal) throws ControlException {
			return new Telephone();
		}

		@Override
		public void merge(Telephone external, PersonTelephone internal) throws ControlException {
			super.merge(external, internal);
			external.setTelephone(internal.getTelephone());
			external.setName(internal.getName());
			external.setPrimary(internal.isPrimary());
		}

		@Override
		public void merge(PersonTelephone internal, Telephone external) throws ControlException {
			super.merge(internal, external);
			internal.setTelephone(external.getTelephone());
			internal.setName(external.getName());
			this.ifNotNull(external.isPrimary(), internal::setPrimary);
		}
	}

	public static class BankAccountMapper extends BaseMapper<PersonBankAccount, BankAccount> {
		public BankAccountMapper(IDatabaseExecutor executor) {
			super(executor);
		}

		@Override
		protected PersonBankAccount newInternal(BankAccount external) throws ControlException {
			return new PersonBankAccount();
		}

		@Override
		protected BankAccount newExternal(PersonBankAccount internal) throws ControlException {
			return new BankAccount();
		}

		@Override
		public void merge(BankAccount external, PersonBankAccount internal) throws ControlException {
			super.merge(external, internal);
			external.setBank(internal.getBank());
			external.setAccountNumber(internal.getAccountNumber());
			external.setPrimary(internal.isPrimary());
		}

		@Override
		public void merge(PersonBankAccount internal, BankAccount external) throws ControlException {
			super.merge(internal, external);
			internal.setBank(external.getBank());
			internal.setAccountNumber(external.getAccountNumber());
			this.ifNotNull(external.isPrimary(), internal::setPrimary);
		}
	}
}
