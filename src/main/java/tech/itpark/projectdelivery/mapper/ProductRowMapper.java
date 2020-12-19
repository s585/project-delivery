package tech.itpark.projectdelivery.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductRowMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getLong("product_id"),
                rs.getLong("category_id"),
                rs.getLong("vendor_id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("unit_price")
        );
    }
}
