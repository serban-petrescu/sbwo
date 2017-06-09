package spet.sbwo.control.mapper.person;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import spet.sbwo.control.channel.person.PersonChannel;
import spet.sbwo.data.domain.IdentityCardType;
import spet.sbwo.data.domain.PersonType;
import spet.sbwo.data.table.PersonJuridical;
import spet.sbwo.data.table.PersonNatural;

import java.math.BigDecimal;

public class PersonPlainMapperTest {
    private PersonJuridical juridicalEntity;
    private PersonNatural naturalEntity;
    private PersonChannel juridicalChannel;
    private PersonChannel naturalChannel;
    private final PersonPlainMapper mapper = new PersonPlainMapper();

    private static PersonJuridical createJuridicalEntity() {
        PersonJuridical juridicalEntity = new PersonJuridical();
        juridicalEntity.setId(1);
        juridicalEntity.setName("Some Company");
        juridicalEntity.setJointStock(new BigDecimal("123.00"));
        juridicalEntity.setRegNumber("J32/123/123");
        juridicalEntity.setIdNumber("3213123");
        juridicalEntity.setType(PersonType.JURIDICAL);
        return juridicalEntity;
    }

    private static PersonNatural createNaturalEntity() {
        PersonNatural naturalEntity = new PersonNatural();
        naturalEntity.setId(1);
        naturalEntity.setFirstName("John");
        naturalEntity.setLastName("Doe");
        naturalEntity.setIdentityCardNumber("123");
        naturalEntity.setIdentityCardType(IdentityCardType.PASSPORT);
        naturalEntity.setPersonalNumber("1234");
        naturalEntity.setType(PersonType.NATURAL);
        return naturalEntity;
    }

    private static PersonChannel createJuridicalChannel() {
        PersonChannel juridicalChannel = new PersonChannel();
        juridicalChannel.setId(2);
        juridicalChannel.setName("Other Company");
        juridicalChannel.setJointStock(new BigDecimal("321.00"));
        juridicalChannel.setRegNumber("J21/123/123");
        juridicalChannel.setIdNumber("ABC");
        juridicalChannel.setType(PersonType.JURIDICAL.ordinal());
        return juridicalChannel;
    }

    private static PersonChannel createNaturalChannel() {
        PersonChannel naturalChannel = new PersonChannel();
        naturalChannel.setFirstName("Peter");
        naturalChannel.setLastName("White");
        naturalChannel.setIdentityCardNumber("987");
        naturalChannel.setIdentityCardType(IdentityCardType.BULLETIN.ordinal());
        naturalChannel.setPersonalNumber("9876");
        naturalChannel.setType(PersonType.NATURAL.ordinal());
        return naturalChannel;
    }

    @Before
    public void setUp() {
        juridicalEntity = createJuridicalEntity();
        naturalEntity = createNaturalEntity();
        juridicalChannel = createJuridicalChannel();
        naturalChannel = createNaturalChannel();
    }

    @Test
    public void testMergeJuridical() {
        mapper.mergeIntoEntity(juridicalChannel, juridicalEntity);
        assertEquals("Other Company", juridicalEntity.getName());
        assertEquals("ABC", juridicalEntity.getIdNumber());
        assertEquals("J21/123/123", juridicalEntity.getRegNumber());
        assertEquals(new BigDecimal("321.00"), juridicalEntity.getJointStock());
    }

    @Test
    public void testMergeNatural() {
        mapper.mergeIntoEntity(naturalChannel, naturalEntity);
        assertEquals("Peter", naturalEntity.getFirstName());
        assertEquals("White", naturalEntity.getLastName());
        assertEquals("987", naturalEntity.getIdentityCardNumber());
        assertEquals(IdentityCardType.BULLETIN, naturalEntity.getIdentityCardType());
        assertEquals("9876", naturalEntity.getPersonalNumber());
    }

    @Test
    public void testToEntityNatural() {
        PersonNatural result = (PersonNatural) mapper.toEntity(naturalChannel);
        assertEquals("Peter", result.getFirstName());
        assertEquals("White", result.getLastName());
        assertEquals("987", result.getIdentityCardNumber());
        assertEquals(IdentityCardType.BULLETIN, result.getIdentityCardType());
        assertEquals("9876", result.getPersonalNumber());
    }

    @Test
    public void testToEntityJuridical() {
        PersonJuridical result = (PersonJuridical) mapper.toEntity(juridicalChannel);
        assertEquals("Other Company", result.getName());
        assertEquals("ABC", result.getIdNumber());
        assertEquals("J21/123/123", result.getRegNumber());
        assertEquals(new BigDecimal("321.00"), result.getJointStock());
    }

    @Test
    public void testToChannelJuridical() {
        PersonChannel result = mapper.toChannel(juridicalEntity);
        assertEquals(PersonType.JURIDICAL.ordinal(), result.getType().intValue());
        assertEquals("Some Company", result.getName());
        assertEquals(new BigDecimal("123.00"), result.getJointStock());
        assertEquals("J32/123/123", result.getRegNumber());
        assertEquals("3213123", result.getIdNumber());
    }

    @Test
    public void testToChannelNatural() {
        PersonChannel result = mapper.toChannel(naturalEntity);
        assertEquals(PersonType.NATURAL.ordinal(), result.getType().intValue());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("123", result.getIdentityCardNumber());
        assertEquals("1234", result.getPersonalNumber());
        assertEquals(IdentityCardType.PASSPORT.ordinal(), result.getIdentityCardType().intValue());
    }

}
