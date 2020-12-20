package tech.itpark.projectdelivery.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.mapper.CustomerRowMapper;
import tech.itpark.projectdelivery.mapper.DelivererRowMapper;
import tech.itpark.projectdelivery.mapper.VendorRowMapper;
import tech.itpark.projectdelivery.model.Vendor;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class VendorManager {
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate template;
    private final CustomerRowMapper customerRowMapper;
    private final DelivererRowMapper delivererRowMapper;
    private final VendorRowMapper vendorRowMapper;

    public List<Vendor> getAll() {
        return template.query("SELECT * FROM vendors LIMIT 50",
                vendorRowMapper);
    }

    public Vendor getById(long id) {
        return template.queryForObject("SELECT * FROM vendors WHERE vendor_id = :vendor_id LIMIT 50",
                Map.of("vendor_id", id),
                vendorRowMapper
        );
    }

    public Vendor save(Vendor item) {
        DeliveryManager deliveryManager = new DeliveryManager(
                dataSource, template, customerRowMapper, delivererRowMapper, vendorRowMapper);
        double[] coordinates = deliveryManager.getCoordinates(item.getAddress());
        if (item.getId() == 0) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update("INSERT INTO vendors (name, address, lon, lat) VALUES (:name, :address, :lon, :lat)",
                    new MapSqlParameterSource(Map.of(
                            "name", item.getName(),
                            "address", item.getAddress(),
                            "lon", coordinates[0],
                            "lat", coordinates[1]
                    )),
                    keyHolder
            );
            long id = keyHolder.getKey().longValue();
            return getById(id);
        }

        template.update("INSERT INTO vendors (name, address, lon, lat) VALUES (:name, :address, :lon, :lat)",
                new MapSqlParameterSource(Map.of(
                        "name", item.getName(),
                        "address", item.getAddress(),
                        "lon", coordinates[0],
                        "lat", coordinates[1]
                ))
        );
        return getById(item.getId());
    }

    public Vendor removeById(long id) {
        Vendor item = getById(id);
        template.update("DELETE FROM vendors WHERE vendor_id = :vendor_id",
                Map.of("vendor_id", item.getId())
        );
        return item;
    }
}

