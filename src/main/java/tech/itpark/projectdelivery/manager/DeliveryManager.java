package tech.itpark.projectdelivery.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.exception.DataAccessException;
import tech.itpark.projectdelivery.mapper.CustomerRowMapper;
import tech.itpark.projectdelivery.mapper.DelivererRowMapper;
import tech.itpark.projectdelivery.mapper.VendorRowMapper;
import tech.itpark.projectdelivery.model.Customer;
import tech.itpark.projectdelivery.model.Deliverer;
import tech.itpark.projectdelivery.model.GeoObject;
import tech.itpark.projectdelivery.model.Vendor;
import tech.itpark.projectdelivery.service.DeliveryService;
import tech.itpark.projectdelivery.service.YaGeocoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DeliveryManager {
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate template;
    private final CustomerRowMapper customerRowMapper;
    private final DelivererRowMapper delivererRowMapper;
    private final VendorRowMapper vendorRowMapper;

    public double[] getCoordinates(String address) {
        YaGeocoder geocoder = new YaGeocoder();
        List<GeoObject> geoObjects = geocoder.directGeocode(address).collect(Collectors.toList());
        GeoObject geoObject = geoObjects.get(0);
        return Stream.of(geoObject.getPoint().split(" "))
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    public long getDeliverer(long vendorId) {
        VendorManager vendorManager = new VendorManager(
                dataSource, template, customerRowMapper, delivererRowMapper, vendorRowMapper);
        DelivererManager delivererManager = new DelivererManager(
                dataSource, template, customerRowMapper, delivererRowMapper, vendorRowMapper);
        DeliveryService service = new DeliveryService();
        Vendor vendor = vendorManager.getById(vendorId);
        List<Deliverer> deliverers = new ArrayList<>(delivererManager.getAll());
        ArrayList<Double> distances = new ArrayList<>();

        for (Deliverer deliverer : deliverers) {
            distances.add(service.getDistance(vendor.getLon(), vendor.getLat(), deliverer.getLon(), deliverer.getLat()));
        }

        int index = distances.indexOf(distances.stream().min(Double::compare).get());
        return index + 1;
    }

    public int getDeliveryPrice(long customerId, long vendorId, long categoryId) {
        CustomerManager customerManager = new CustomerManager(dataSource, template, customerRowMapper, delivererRowMapper, vendorRowMapper);
        VendorManager vendorManager = new VendorManager(
                dataSource, template, customerRowMapper, delivererRowMapper, vendorRowMapper);
        DeliveryService service = new DeliveryService();

        Customer customer = customerManager.getById(customerId);
        Vendor vendor = vendorManager.getById(vendorId);

        double customerLon = customer.getLon();
        double customerLat = customer.getLat();
        double vendorLon = vendor.getLon();
        double vendorLat = vendor.getLat();

        double distance = service.getDistance(customerLon, customerLat, vendorLon, vendorLat);
        int delivery_coefficient = 0;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement psCoefficient = conn.prepareStatement(
                        "SELECT delivery_coefficient FROM categories WHERE category_id = ? LIMIT 50")

        ) {
            psCoefficient.setLong(1, categoryId);
            ResultSet rsCoefficient = psCoefficient.executeQuery();
            while (rsCoefficient.next()) {
                delivery_coefficient = rsCoefficient.getInt("delivery_coefficient");
            }

        } catch (SQLException e) {
            throw new DataAccessException(e);

        }

        return (int) distance / delivery_coefficient;
    }
}
