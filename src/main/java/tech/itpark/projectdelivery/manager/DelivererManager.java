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
//        try (
//                Connection conn = dataSource.getConnection();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT * FROM deliverers LIMIT 50")
//        ) {
//
//            List<Deliverer> items = new ArrayList<>();
//            while (rs.next()) {
//                items.add(new Deliverer(
//                        rs.getLong("deliverer_id"),
//                        rs.getString("name"),
//                        rs.getString("location"),
//                        rs.getDouble("lon"),
//                        rs.getDouble("lat")
//                ));
//            }
//            return items;
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }

    public Deliverer getById(long id) {
        return template.queryForObject("SELECT * FROM deliverers WHERE deliverer_id = :deliverer_id LIMIT 50",
                Map.of("deliverer_id", id),
                delivererRowMapper
        );
    }
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement(
//                        "SELECT * FROM Deliverers WHERE deliverer_id = ? LIMIT 50")
//        ) {
//            ps.setLong(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return new Deliverer(
//                        rs.getLong("deliverer_id"),
//                        rs.getString("name"),
//                        rs.getString("location"),
//                        rs.getDouble("lon"),
//                        rs.getDouble("lat")
//                );
//            }
//            throw new NotFoundException();
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }

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

//        DeliveryManager deliveryManager = new DeliveryManager(dataSource, template, customerRowMapper, vendorRowMapper);
//        if (item.getId() == 0) {
//            try (
//                    Connection conn = dataSource.getConnection();
//                    PreparedStatement ps = conn.prepareStatement(
//                            "INSERT INTO deliverers (name, location, lon, lat) VALUES (?,?,?,?)",
//                            Statement.RETURN_GENERATED_KEYS
//                    )
//            ) {
//                double[] coordinates = deliveryManager.getCoordinates(item.getLocation());
//                int index = 0;
//                ps.setString(++index, item.getName());
//                ps.setString(++index, item.getLocation());
//                ps.setDouble(++index, coordinates[0]);
//                ps.setDouble(++index, coordinates[1]);
//                ps.execute();
//
//                try (ResultSet keys = ps.getGeneratedKeys()) {
//                    if (keys.next()) {
//                        long id = keys.getLong(1);
//                        return getById(id);
//                    }
//                    throw new DataAccessException("No generated keys");
//                }
//            } catch (SQLException e) {
//                throw new DataAccessException(e);
//            }
//        }
//
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement(
//                        "UPDATE deliverers SET name = ?, location = ?, lon = ?, lat = ? WHERE deliverer_id = ?")
//        ) {
//            double[] coordinates = deliveryManager.getCoordinates(item.getLocation());
//            int index = 0;
//            ps.setString(++index, item.getName());
//            ps.setString(++index, item.getLocation());
//            ps.setDouble(++index, coordinates[0]);
//            ps.setDouble(++index, coordinates[1]);
//            ps.setLong(++index, item.getId());
//            ps.execute();
//
//            return getById(item.getId());
//
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }

    public Deliverer removeById(long id) {
        Deliverer item = getById(id);
        template.update("DELETE FROM deliverers WHERE deliverer_id = :deliverer_id",
                Map.of("deliverer_id", item.getId())
        );
        return item;
    }
//    public Deliverer removeById(long id) {
//        Deliverer item = getById(id);
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement("DELETE FROM deliverers WHERE deliverer_id = ?")
//        ) {
//            ps.setLong(1, id);
//            ps.execute();
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//        return item;
//    }
}
