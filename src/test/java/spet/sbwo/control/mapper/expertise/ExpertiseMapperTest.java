package spet.sbwo.control.mapper.expertise;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import spet.sbwo.control.channel.expertise.CourtChannel;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.control.channel.user.UserChannel;
import spet.sbwo.data.domain.ExpertiseStatus;
import spet.sbwo.data.embed.Tariff;
import spet.sbwo.data.table.Court;
import spet.sbwo.data.table.Expertise;
import spet.sbwo.data.table.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ExpertiseMapperTest {
    private Expertise entity;
    private ExpertiseChannel channel;
    private Function<Integer, User> userProvider = createUserProvider();
    private Function<Integer, Court> courtProvider = createCourtProvider();
    private ExpertiseMapper mapper = new ExpertiseMapper(userProvider, courtProvider);

    public static Expertise createEntity(Court court, User responsible) {
        Expertise result = new Expertise();
        result.setCourt(court);
        result.setResponsible(responsible);
        result.setTitle("Some title");
        result.setYear(2017);
        result.setStatus(ExpertiseStatus.STUDIED);
        result.setNumber("123");
        result.setNote("Some note");
        result.setNextHearing(LocalDate.of(2017, 1, 1));

        Tariff tariff = new Tariff();
        tariff.setPrice(new BigDecimal("10.00"));
        tariff.setAdvance(new BigDecimal("1.00"));
        result.setTariff(tariff);

        return result;
    }

    public static ExpertiseChannel createChannel(Integer courtId, Integer responsibleUserId) {
        ExpertiseChannel result = new ExpertiseChannel();
        result.setYear(2016);
        result.setTitle("Other title");
        result.setStatus(2);
        result.setNumber("321");
        result.setNote("Other note");
        result.setNextHearing(LocalDate.of(2016, 1, 1));
        result.setPrice(new BigDecimal("5.00"));
        result.setAdvance(new BigDecimal("2.00"));

        UserChannel user = new UserChannel();
        user.setId(responsibleUserId);
        result.setResponsible(user);

        CourtChannel court = new CourtChannel();
        court.setId(courtId);
        result.setCourt(court);

        return result;
    }

    public static Function<Integer, Court> createCourtProvider() {
        final Map<Integer, Court> courts = new HashMap<>();
        courts.put(1, court(1, "A"));
        courts.put(2, court(2, "B"));
        courts.put(3, court(3, "C"));
        return i -> courts.get(i);
    }

    public static Function<Integer, User> createUserProvider() {
        final Map<Integer, User> users = new HashMap<>();
        users.put(1, user(1, "X"));
        users.put(2, user(2, "Y"));
        users.put(3, user(3, "Z"));
        return i -> users.get(i);
    }

    @Before
    public void setUp() {
        entity = createEntity(courtProvider.apply(1), userProvider.apply(1));
        channel = createChannel(2, 2);
    }

    @Test
    public void testToEntity() {
        Expertise result = mapper.toEntity(channel);
        assertEquals(2016, result.getYear());
        assertEquals("Other title", result.getTitle());
        assertEquals(ExpertiseStatus.values()[2], result.getStatus());
        assertEquals("321", result.getNumber());
        assertEquals("Other note", result.getNote());
        assertEquals(LocalDate.of(2016, 1, 1), result.getNextHearing());
        assertEquals(courtProvider.apply(2), result.getCourt());
        assertEquals(userProvider.apply(2), result.getResponsible());
        assertEquals(new BigDecimal("2.00"), result.getTariff().getAdvance());
        assertEquals(new BigDecimal("5.00"), result.getTariff().getPrice());
    }

    @Test
    public void testToChannel() {
        ExpertiseChannel result = mapper.toChannel(entity);
        assertEquals(2017, result.getYear());
        assertEquals("Some title", result.getTitle());
        assertEquals(ExpertiseStatus.STUDIED.ordinal(), result.getStatus().intValue());
        assertEquals("123", result.getNumber());
        assertEquals("Some note", result.getNote());
        assertEquals(LocalDate.of(2017, 1, 1), result.getNextHearing());
        assertEquals(1, result.getCourt().getId().intValue());
        assertEquals(1, result.getResponsible().getId().intValue());
        assertEquals(new BigDecimal("1.00"), result.getAdvance());
        assertEquals(new BigDecimal("10.00"), result.getPrice());
    }

    @Test
    public void testMerge() {
        mapper.mergeIntoEntity(channel, entity);
        assertEquals(2016, entity.getYear());
        assertEquals("Other title", entity.getTitle());
        assertEquals(ExpertiseStatus.values()[2], entity.getStatus());
        assertEquals("321", entity.getNumber());
        assertEquals("Other note", entity.getNote());
        assertEquals(LocalDate.of(2016, 1, 1), entity.getNextHearing());
        assertEquals(courtProvider.apply(2), entity.getCourt());
        assertEquals(userProvider.apply(2), entity.getResponsible());
        assertEquals(new BigDecimal("2.00"), entity.getTariff().getAdvance());
        assertEquals(new BigDecimal("5.00"), entity.getTariff().getPrice());
    }

    private static Court court(int id, String name) {
        Court court = new Court();
        court.setId(id);
        court.setName(name);
        return court;
    }

    private static User user(int id, String name) {
        User user = new User();
        user.setId(id);
        user.setUsername(name);
        return user;
    }
}
