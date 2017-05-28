package spet.sbwo.control.channel;

public class LocationImportChannel implements Comparable<LocationImportChannel> {
    private String countryCode;
    private String countryName;
    private String regionCode;
    private String regionName;
    private String admUnitCode;
    private String admUnitName;

    public LocationImportChannel(String countryCode, String countryName, String regionCode, String regionName,
                                 String admUnitCode, String admUnitName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.admUnitCode = admUnitCode;
        this.admUnitName = admUnitName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAdmUnitCode() {
        return admUnitCode;
    }

    public void setAdmUnitCode(String admUnitCode) {
        this.admUnitCode = admUnitCode;
    }

    public String getAdmUnitName() {
        return admUnitName;
    }

    public void setAdmUnitName(String admUnitName) {
        this.admUnitName = admUnitName;
    }

    @Override
    public int compareTo(LocationImportChannel o) {
        int result;
        if ((result = this.countryCode.compareTo(o.countryCode)) != 0) {
            return result;
        }
        if ((result = this.regionCode.compareTo(o.regionCode)) != 0) {
            return result;
        }
        if ((result = this.admUnitCode.compareTo(o.admUnitCode)) != 0) {
            return result;
        }
        return 0;
    }

}
