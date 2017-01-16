package spet.sbwo;

import java.io.IOException;
import java.util.EnumSet;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import spet.sbwo.data.table.*;
import spet.sbwo.data.base.*;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		MetadataSources metadata = new MetadataSources(new StandardServiceRegistryBuilder()
				.applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect").build());


		/*metadata.addAnnotatedClass(BaseEntity.class);
		metadata.addAnnotatedClass(Person.class);
		metadata.addAnnotatedClass(PersonBankAccount.class);
		metadata.addAnnotatedClass(PersonEmailAddress.class);
		metadata.addAnnotatedClass(PersonJuridical.class);
		metadata.addAnnotatedClass(PersonNatural.class);
		metadata.addAnnotatedClass(PersonTelephone.class);
		metadata.addAnnotatedClass(User.class);
		metadata.addAnnotatedClass(UserPreference.class);
		metadata.addAnnotatedClass(UserHomeTile.class);
		metadata.addAnnotatedClass(UserFavourite.class);
		metadata.addAnnotatedClass(Location.class);
		metadata.addAnnotatedClass(LocationAdministrativeUnit.class);
		metadata.addAnnotatedClass(LocationCountry.class);
		metadata.addAnnotatedClass(LocationRegion.class);*/
		metadata.addAnnotatedClass(UserSession.class);

		SchemaExport export = new SchemaExport();
		export.setOutputFile("E:/Downloads/dll2.sql");
		export.setDelimiter(";");
		export.setFormat(true);
		export.create(EnumSet.of(TargetType.SCRIPT), metadata.buildMetadata());
	}
}