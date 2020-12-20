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
import tech.itpark.projectdelivery.model.Customer;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomerManager {
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate template;
    private final CustomerRowMapper customerRowMapper;
    private final DelivererRowMapper delivererRowMapper;
    private final VendorRowMapper vendorRowMapper;

    public List<Customer> getAll() {
        return template.query("SELECT * FROM customers LIMIT 50",
                customerRowMapper);
    }

    public Customer getById(long id) {
        return template.queryForObject("SELECT * FROM customers WHERE customer_id = :customer_id LIMIT 50",
                Map.of("customer_id", id),
                customerRowMapper
        );
    }

    public Customer save(Customer item) {
        DeliveryManager deliveryManager = new DeliveryManager(
                dataSource, template, customerRowMapper, delivererRowMapper, vendorRowMapper);
        double[] coordinates = deliveryManager.getCoordinates(item.getAddress());
        if (item.getId() == 0) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update("INSERT INTO customers (name, address, lon, lat) VALUES (:name, :address, :lon, :lat)",
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

        template.update("INSERT INTO customers (name, address, lon, lat) VALUES (:name, :address, :lon, :lat)",
                new MapSqlParameterSource(Map.of(
                        "name", item.getName(),
                        "address", item.getAddress(),
                        "lon", coordinates[0],
                        "lat", coordinates[1]
                ))
        );
        return getById(item.getId());
    }

    public Customer removeById(long id) {
        Customer item = getById(id);
        template.update("DELETE FROM customers WHERE customer_id = :customer_id",
                Map.of("customer_id", item.getId())
        );
        return item;
    }
}
