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

//        try (
//                Connection conn = dataSource.getConnection();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT * FROM customers LIMIT 50");
//        ) {
//
//            List<Customer> items = new ArrayList<>();
//            while (rs.next()) {
//                items.add(new Customer(
//                        rs.getLong("customer_id"),
//                        rs.getString("name"),
//                        rs.getString("address"),
//                        rs.getDouble("lon"),
//                        rs.getDouble("lat")
//                ));
//            }
//            return items;
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }

    public Customer getById(long id) {
        return template.queryForObject("SELECT * FROM customers WHERE customer_id = :customer_id LIMIT 50",
                Map.of("customer_id", id),
                customerRowMapper
        );
    }
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement(
//                        "SELECT * FROM customers WHERE customer_id = ? LIMIT 50");
//        ) {
//            ps.setLong(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return new Customer(
//                        rs.getLong("customer_id"),
//                        rs.getString("name"),
//                        rs.getString("address"),
//                        rs.getDouble("lon"),
//                        rs.getDouble("lat")
//                );
//            }
//            throw new NotFoundException();
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }

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
//            try (
//                    Connection conn = dataSource.getConnection();
//                    PreparedStatement ps = conn.prepareStatement(
//                            "INSERT INTO customers (name, address, lon, lat) VALUES (?,?,?,?)",
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
//                        "UPDATE customers SET name = ?, address = ? , lon = ?, lat = ? WHERE customer_id = ?");
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

    public Customer removeById(long id) {
        Customer item = getById(id);
        template.update("DELETE FROM customers WHERE customer_id = :customer_id",
                Map.of("customer_id", item.getId())
        );
        return item;
    }

//    public Customer removeById(long id) {
//        Customer item = getById(id);
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE customer_id = ?");
//        ) {
//            ps.setLong(1, id);
//            ps.execute();
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//        return item;
//    }
}
