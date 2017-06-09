package spet.sbwo.control.mapper.location;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import spet.sbwo.control.channel.location.LocationAdmUnitChannel;
import spet.sbwo.control.channel.location.LocationChannel;
import spet.sbwo.control.channel.location.LocationCountryChannel;
import spet.sbwo.control.channel.location.LocationRegionChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.data.table.Location;
import spet.sbwo.data.table.LocationAdministrativeUnit;
import spet.sbwo.data.table.LocationCountry;
import spet.sbwo.data.table.LocationRegion;

import java.util.HashMap;
import java.util.Map;

public class LocationMapperTest {
    private Map<Integer, LocationCountry> countries;
    private Map<Integer, LocationRegion> regions;
    private Map<Integer, LocationAdministrativeUnit> units;
    private Location entity;
    private LocationChannel fullChannel;
    private LocationChannel nullChannel;
    private LocationChannel nullChildChannel;

    @Before
    public void setUpEntities() {
        setUpCountries();
        setUpRegions();
        setUpUnits();
        setUpMainEntity();
    }

    @Before
    public void setUpFullChannel() {
        fullChannel = setUpFullMainChannel();

        LocationCountryChannel country = new LocationCountryChannel();
        country.setId(2);
        country.setName("b country");
        country.setCode("b");
        fullChannel.setCountry(country);

        LocationRegionChannel region = new LocationRegionChannel();
        region.setId(2);
        region.setName("b region");
        region.setCode("b");
        fullChannel.setRegion(region);

        LocationAdmUnitChannel unit = new LocationAdmUnitChannel();
        unit.setId(2);
        unit.setName("b unit");
        unit.setCode("b");
        fullChannel.setAdministrativeUnit(unit);
    }

    @Before
    public void setUpNullChannel() {
        nullChannel = new LocationChannel();
    }

    @Before
    public void setUpNullChildChannel() {
        nullChildChannel = setUpFullMainChannel();
        nullChildChannel.setCountry(new LocationCountryChannel());
        nullChildChannel.setRegion(new LocationRegionChannel());
        nullChildChannel.setAdministrativeUnit(new LocationAdmUnitChannel());
    }

    @Test
    public void testToEntityFullMainEntity() {
        Location result = mapper().toEntity(fullChannel);
        assertEquals("Other street no. 9", result.getAddress());
        assertFalse(result.isGeocoded());
        assertNull(result.getLatitude());
        assertNull(result.getLongitude());
    }

    @Test
    public void testToEntityFullChildAssignment() {
        Location result = mapper().toEntity(fullChannel);
        assertEquals(countries.get(2), result.getCountry());
        assertEquals(regions.get(2), result.getRegion());
        assertEquals(units.get(2), result.getAdministrativeUnit());
    }

    @Test
    public void testToEntityFullChildNotChanged() {
        Location result = mapper().toEntity(fullChannel);
        assertEquals("B", result.getCountry().getCode());
        assertEquals("B Country", result.getCountry().getName());
        assertEquals("B", result.getRegion().getCode());
        assertEquals("B Region", result.getRegion().getName());
        assertEquals("B", result.getAdministrativeUnit().getCode());
        assertEquals("B Unit", result.getAdministrativeUnit().getName());
    }

    @Test
    public void testToChannelMainEntity() {
        LocationChannel result = mapper().toChannel(entity);
        assertEquals(1, result.getId().intValue());
        assertEquals("Some street no. 7", result.getAddress());
        assertTrue(result.isGeocoded());
        assertEquals(42.02d, result.getLatitude(), 0.001);
        assertEquals(32.45d, result.getLongitude(), 0.001);
    }

    @Test
    public void testToChannelChildEntities() {
        LocationChannel result = mapper().toChannel(entity);
        assertEquals(1, result.getCountry().getId().intValue());
        assertEquals("A", result.getCountry().getCode());
        assertEquals("A Country", result.getCountry().getName());
        assertEquals(1, result.getRegion().getId().intValue());
        assertEquals("A", result.getRegion().getCode());
        assertEquals("A Region", result.getRegion().getName());
        assertEquals(1, result.getAdministrativeUnit().getId().intValue());
        assertEquals("A", result.getAdministrativeUnit().getCode());
        assertEquals("A Unit", result.getAdministrativeUnit().getName());
    }

    @Test
    public void testMergeFullMainEntity() {
        mapper().mergeIntoEntity(fullChannel, entity);
        assertEquals("Other street no. 9", entity.getAddress());
        assertFalse(entity.isGeocoded());
        assertEquals(42.02d, entity.getLatitude(), 0.001);
        assertEquals(32.45d, entity.getLongitude(), 0.001);
    }

    @Test
    public void testMergeFullChildAssignment() {
        mapper().mergeIntoEntity(fullChannel, entity);
        assertEquals(countries.get(2), entity.getCountry());
        assertEquals(regions.get(2), entity.getRegion());
        assertEquals(units.get(2), entity.getAdministrativeUnit());
    }

    @Test
    public void testMergeNullMainEntity() {
        mapper().mergeIntoEntity(nullChannel, entity);
        assertEquals("Some street no. 7", entity.getAddress());
        assertTrue(entity.isGeocoded());
    }

    @Test
    public void testMergeNullChildAssignment() {
        mapper().mergeIntoEntity(nullChannel, entity);
        assertNull(entity.getCountry());
        assertNull(entity.getRegion());
        assertNull(entity.getAdministrativeUnit());
    }
    @Test
    public void testMergeNullChildMainEntity() {
        mapper().mergeIntoEntity(nullChildChannel, entity);
        assertEquals("Other street no. 9", entity.getAddress());
        assertFalse(entity.isGeocoded());
    }

    @Test
    public void testMergeNullChildChildAssignment() {
        mapper().mergeIntoEntity(nullChildChannel, entity);
        assertNull(entity.getCountry());
        assertNull(entity.getRegion());
        assertNull(entity.getAdministrativeUnit());
    }

    private IMapper<Location, LocationChannel> mapper() {
        return new LocationMapper(i -> countries.get(i), i -> regions.get(i), i -> units.get(i));
    }

    private LocationChannel setUpFullMainChannel() {
        LocationChannel channel = new LocationChannel();
        channel.setAddress("Other street no. 9");
        channel.setGeocoded(false);
        channel.setLatitude(25.04d);
        channel.setLongitude(32.06d);
        channel.setId(1);
        return channel;
    }

    private void setUpMainEntity() {
        entity = new Location();
        entity.setAdministrativeUnit(units.get(1));
        entity.setCountry(countries.get(1));
        entity.setRegion(regions.get(1));
        entity.setAddress("Some street no. 7");
        entity.setGeocoded(true);
        entity.setLatitude(42.02d);
        entity.setLongitude(32.45d);
        entity.setId(1);
    }

    private void setUpCountries() {
        countries = new HashMap<>();
        countries.put(1, country(1, "A", "A Country"));
        countries.put(2, country(2, "B", "B Country"));
        countries.put(3, country(3, "C", "C Country"));
    }

    private void setUpRegions() {
        regions = new HashMap<>();
        regions.put(1, region(1, "A", "A Region"));
        regions.put(2, region(2, "B", "B Region"));
        regions.put(3, region(3, "C", "C Region"));
    }

    private void setUpUnits() {
        units = new HashMap<>();
        units.put(1, admUnit(1, "A", "A Unit"));
        units.put(2, admUnit(2, "B", "B Unit"));
        units.put(3, admUnit(3, "C", "C Unit"));
    }

    private static LocationCountry country(int id, String code, String name) {
        LocationCountry result = new LocationCountry();
        result.setId(id);
        result.setCode(code);
        result.setName(name);
        return result;
    }

    private static LocationRegion region(int id, String code, String name) {
        LocationRegion result = new LocationRegion();
        result.setId(id);
        result.setCode(code);
        result.setName(name);
        return result;
    }

    private static LocationAdministrativeUnit admUnit(int id, String code, String name) {
        LocationAdministrativeUnit result = new LocationAdministrativeUnit();
        result.setId(id);
        result.setCode(code);
        result.setName(name);
        return result;
    }
}
