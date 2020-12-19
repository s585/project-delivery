package tech.itpark.projectdelivery.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.mapper.ProductRowMapper;
import tech.itpark.projectdelivery.model.Product;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductManager {
    private final NamedParameterJdbcTemplate template;
    private final ProductRowMapper rowMapper;

    public List<Product> getAll() {
        return template.query("SELECT * FROM products LIMIT 50",
                rowMapper
        );
    }
//        try (
//                Connection conn = dataSource.getConnection();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT * FROM products LIMIT 50")
//        ) {
//
//            List<Product> items = new ArrayList<>();
//            while (rs.next()) {
//                items.add(mapRow(rs));
//            }
//            return items;
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }

//    private Product mapRow(ResultSet rs) throws SQLException {
//        return new Product(
//                rs.getLong("product_id"),
//                rs.getLong("category_id"),
//                rs.getLong("vendor_id"),
//                rs.getString("name"),
//                rs.getString("image_url"),
//                rs.getInt("unit_price")
//        );
//    }

    public Product getById(long id) {
        return template.queryForObject("SELECT * FROM products WHERE product_id = :product_id LIMIT 50",
                Map.of("product_id", id),
                rowMapper
        );
    }
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement(
//                        "SELECT * FROM products WHERE product_id = ? LIMIT 50")
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

    public Product save(Product item) {
        if (item.getId() == 0) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(
                    "INSERT INTO products (name, category_id, vendor_id, unit_price) " +
                            "VALUES (:name, :category_id, :vendor_id, :unit_price)",
                    new MapSqlParameterSource(Map.of(
                            "name", item.getName(),
                            "category_id", item.getCategoryId(),
                            "vendor_id", item.getVendorId(),
                            "unit_price", item.getUnitPrice()
                    )),
                    keyHolder
            );
            long id = keyHolder.getKey().longValue();
            return getById(id);
        }

        template.update(
                "INSERT INTO products (name, category_id, vendor_id, unit_price) " +
                        "VALUES (:name, :category_id, :vendor_id, :unit_price)",
                new MapSqlParameterSource(Map.of(
                        "name", item.getName(),
                        "category_id", item.getCategoryId(),
                        "vendor_id", item.getVendorId(),
                        "unit_price", item.getUnitPrice()
                ))
        );
        return getById(item.getId());
    }

//            try (
//                    Connection conn = dataSource.getConnection();
//                    PreparedStatement ps = conn.prepareStatement(
//                            "INSERT INTO products (name, category_id, vendor_id, unit_price) VALUES (?,?,?,?)",
//                            Statement.RETURN_GENERATED_KEYS
//                    )
//            ) {
//                int index = 0;
//                ps.setString(++index, item.getName());
//                ps.setLong(++index, item.getCategoryId());
//                ps.setLong(++index, item.getVendorId());
//                ps.setInt(++index, item.getUnitPrice());
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
//                        "UPDATE products SET name = ?, category_id = ?, vendor_id = ?, unit_price = ?" +
//                                " WHERE product_id = ?")
//        ) {
//            int index = 0;
//            ps.setString(++index, item.getName());
//            ps.setLong(++index, item.getCategoryId());
//            ps.setLong(++index, item.getVendorId());
//            ps.setInt(++index, item.getUnitPrice());
//            ps.setLong(++index, item.getId());
//
//            ps.execute();
//
//            return getById(item.getId());
//
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }

    public Product removeById(long id) {
        Product item = getById(id);
        template.update("DELETE FROM products WHERE product_id = :product_id",
                Map.of("product_id", item.getId())
        );
        return item;
    }
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE product_id = ?")
//        ) {
//            ps.setLong(1, id);
//            ps.execute();
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//        return item;
//    }
}

