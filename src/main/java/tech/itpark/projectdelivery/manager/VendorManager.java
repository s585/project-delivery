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
//        try (
//                Connection conn = dataSource.getConnection();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT * FROM vendors LIMIT 50");
//        ) {
//
//            List<Vendor> items = new ArrayList<>();
//            while (rs.next()) {
//                items.add(mapRow(rs));
//            }
//            return items;
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }

//    private Vendor mapRow(ResultSet rs) throws SQLException {
//        return new Vendor(
//                rs.getLong("vendor_id"),
//                rs.getString("name"),
//                rs.getString("address"),
//                rs.getDouble("lon"),
//                rs.getDouble("lat")
//        );
//    }

    public Vendor getById(long id) {
        return template.queryForObject("SELECT * FROM vendors WHERE vendor_id = :vendor_id LIMIT 50",
                Map.of("vendor_id", id),
                vendorRowMapper
        );
    }
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement(
//                        "SELECT * FROM vendors WHERE vendor_id = ? LIMIT 50");
//        ) {
//            ps.setLong(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return mapRow(rs);
//            }
//            throw new NotFoundException();
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }

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



//            try (
//                    Connection conn = dataSource.getConnection();
//                    PreparedStatement ps = conn.prepareStatement(
//                            "INSERT INTO vendors (name, address, lon, lat) VALUES (?,?,?,?)",
//                            Statement.RETURN_GENERATED_KEYS
//                    );
//            ) {
//                double[] coordinates = deliveryManager.getCoordinates(item.getAddress());
//                int index = 0;
//                ps.setString(++index, item.getName());
//                ps.setString(++index, item.getAddress());
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
//                        "UPDATE vendors SET name = ?, address = ? , lon = ?, lat = ? WHERE vendor_id = ?");
//        ) {
//            double[] coordinates = deliveryManager.getCoordinates(item.getAddress());
//            int index = 0;
//            ps.setString(++index, item.getName());
//            ps.setString(++index, item.getAddress());
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

    public Vendor removeById(long id) {
        Vendor item = getById(id);
        template.update("DELETE FROM vendors WHERE vendor_id = :vendor_id",
                Map.of("vendor_id", item.getId())
        );
        return item;
    }
}
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement("DELETE FROM vendors WHERE vendor_id = ?");
//        ) {
//            ps.setLong(1, id);
//            ps.execute();
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//        return item;
//                }
//                }

