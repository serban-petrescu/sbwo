package spet.sbwo.integration.web.googlemaps;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spet.sbwo.config.GeocodingEntry;
import spet.sbwo.data.table.Location;
import spet.sbwo.integration.api.geocode.IGeocodingApi;
import spet.sbwo.integration.api.geocode.model.Position;

public class GoogleMapsGeocodingApi implements IGeocodingApi {
    private static final Logger LOG = LoggerFactory.getLogger(GoogleMapsGeocodingApi.class);
    private final static String API_URL = "https://maps.googleapis.com/maps/api/geocode/json";

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

    protected Position callApi(String url) throws MalformedURLException, IOException {
        try (InputStream stream = new URL(url).openConnection().getInputStream();
             InputStreamReader in = new InputStreamReader(stream)) {
            Response response = gson.fromJson(in, Response.class);
            return responseToPosition(response);
        }
    }

    protected Position responseToPosition(Response response) {
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

    protected String buildUrl(String address) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder(API_URL);
        result.append("?key=");
        result.append(URLEncoder.encode(apiKey, "UTF-8"));
        result.append("&address=");
        result.append(URLEncoder.encode(address, "UTF-8"));
        return result.toString();
    }

    protected String buildUrl(Location location) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder(API_URL);
        result.append("?key=");
        result.append(URLEncoder.encode(apiKey, "UTF-8"));
        if (location.getCountry() != null && location.getCountry().getCode() != null) {
            result.append("&components=country:");
            result.append(URLEncoder.encode(location.getCountry().getCode(), "UTF-8"));
        }
        result.append("&address=");
        result.append(URLEncoder.encode(location.getAddress(), "UTF-8"));
        if (location.getRegion() != null) {
            result.append(URLEncoder.encode(", ", "UTF-8"));
            result.append(URLEncoder.encode(location.getRegion().getName(), "UTF-8"));
        }
        return result.toString();
    }
}
