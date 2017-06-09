package spet.sbwo.control.mapper.expertise;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import spet.sbwo.control.channel.expertise.ExpertiseFineChannel;
import spet.sbwo.data.table.ExpertiseFine;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpertiseFineMapperTest {
    private ExpertiseFine entity;
    private ExpertiseFineChannel channel;
    private ExpertiseFineMapper mapper = new ExpertiseFineMapper();

    public static ExpertiseFine createEntity() {
        ExpertiseFine result = new ExpertiseFine();
        result.setDate(LocalDate.of(2017, 1, 1));
        result.setSum(new BigDecimal("10.00"));
        return result;
    }

    public static ExpertiseFineChannel createChannel() {
        ExpertiseFineChannel result = new ExpertiseFineChannel();
        result.setDate(LocalDate.of(2016, 1, 1));
        result.setSum(new BigDecimal("20.00"));
        return result;
    }

    @Before
    public void setUp() {
        entity = createEntity();
        channel = createChannel();
    }

    @Test
    public void testToEntity() {
        ExpertiseFine result = mapper.toEntity(channel);
        assertEquals(LocalDate.of(2016, 1, 1), result.getDate());
        assertEquals(new BigDecimal("20.00"), result.getSum());
    }

    @Test
    public void testToChannel() {
        ExpertiseFineChannel result = mapper.toChannel(entity);
        assertEquals(LocalDate.of(2017, 1, 1), result.getDate());
        assertEquals(new BigDecimal("10.00"), result.getSum());
    }

    @Test
    public void testMerge() {
        mapper.mergeIntoEntity(channel, entity);
        assertEquals(LocalDate.of(2016, 1, 1), entity.getDate());
        assertEquals(new BigDecimal("20.00"), entity.getSum());
    }

}
