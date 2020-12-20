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
import tech.itpark.projectdelivery.model.Deliverer;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DelivererManager {
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate template;
    private final CustomerRowMapper customerRowMapper;
    private final DelivererRowMapper delivererRowMapper;
    private final VendorRowMapper vendorRowMapper;

    public List<Deliverer> getAll() {
        return template.query("SELECT * FROM deliverers LIMIT 50",
                delivererRowMapper);
    }

    public Deliverer getById(long id) {
        return template.queryForObject("SELECT * FROM deliverers WHERE deliverer_id = :deliverer_id LIMIT 50",
                Map.of("deliverer_id", id),
                delivererRowMapper
        );
    }

    public Deliverer save(Deliverer item) {
        DeliveryManager deliveryManager = new DeliveryManager(
                dataSource, template, customerRowMapper, delivererRowMapper, vendorRowMapper);
        double[] coordinates = deliveryManager.getCoordinates(item.getLocation());
        if (item.getId() == 0) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update("INSERT INTO deliverers (name, location, lon, lat) VALUES (:name, :address, :lon, :lat)",
                    new MapSqlParameterSource(Map.of(
                            "name", item.getName(),
                            "address", item.getLocation(),
                            "lon", coordinates[0],
                            "lat", coordinates[1]
                    )),
                    keyHolder
            );
            long id = keyHolder.getKey().longValue();
            return getById(id);
        }

        template.update("INSERT INTO deliverers (name, location, lon, lat) VALUES (:name, :location, :lon, :lat)",
                new MapSqlParameterSource(Map.of(
                        "name", item.getName(),
                        "address", item.getLocation(),
                        "lon", coordinates[0],
                        "lat", coordinates[1]
                ))
        );
        return getById(item.getId());
    }

    public Deliverer removeById(long id) {
        Deliverer item = getById(id);
        template.update("DELETE FROM deliverers WHERE deliverer_id = :deliverer_id",
                Map.of("deliverer_id", item.getId())
        );
        return item;
    }
}
