package spet.sbwo.integration.web.googlemaps;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spet.sbwo.config.GeocodingEntry;
import spet.sbwo.data.table.Location;
import spet.sbwo.integration.api.geocode.IGeocodingApi;
import spet.sbwo.integration.api.geocode.model.Position;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleMapsGeocodingApi implements IGeocodingApi {
    private static final Logger LOG = LoggerFactory.getLogger(GoogleMapsGeocodingApi.class);
    private static final String API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String DEFAULT_ENCODING = "UTF-8";

    private final Gson gson;
    private final String apiKey;

    public GoogleMapsGeocodingApi(GeocodingEntry config) {
        this.apiKey = config.getKey();
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    @Override
    public Position geocode(Location location) {
        if (location.getAddress() != null) {
            try {
                return callApi(buildUrl(location));
            } catch (Exception e) {
                LOG.error("Suppressed error while performing location-based geocoding.", e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Position geocode(String address) {
        try {
            return callApi(buildUrl(address));
        } catch (Exception e) {
            LOG.error("Suppressed error while performing address-based geocoding.", e);
            return null;
        }
    }

    private Position callApi(String url) throws IOException {
        try (InputStream stream = new URL(url).openConnection().getInputStream();
             InputStreamReader in = new InputStreamReader(stream)) {
            Response response = gson.fromJson(in, Response.class);
            return responseToPosition(response);
        }
    }

    private Position responseToPosition(Response response) {
        if (response.getResults() != null && !response.getResults().isEmpty()) {
            Result first = response.getResults().get(0);
            if (first.getGeometry() != null && first.getGeometry().getLocation() != null) {
                LatLng location = first.getGeometry().getLocation();
                Position position = new Position();
                position.setLatitude(location.getLat());
                position.setLongitude(location.getLng());
                return position;
            }
        }
        return null;
    }

    private String buildUrl(String address) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder(API_URL);
        result.append("?key=");
        result.append(URLEncoder.encode(apiKey, DEFAULT_ENCODING));
        result.append("&address=");
        result.append(URLEncoder.encode(address, DEFAULT_ENCODING));
        return result.toString();
    }

    private String buildUrl(Location location) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder(API_URL);
        result.append("?key=");
        result.append(URLEncoder.encode(apiKey, DEFAULT_ENCODING));
        if (location.getCountry() != null && location.getCountry().getCode() != null) {
            result.append("&components=country:");
            result.append(URLEncoder.encode(location.getCountry().getCode(), DEFAULT_ENCODING));
        }
        result.append("&address=");
        result.append(URLEncoder.encode(location.getAddress(), DEFAULT_ENCODING));
        if (location.getRegion() != null) {
            result.append(URLEncoder.encode(", ", DEFAULT_ENCODING));
            result.append(URLEncoder.encode(location.getRegion().getName(), DEFAULT_ENCODING));
        }
        return result.toString();
    }
}
