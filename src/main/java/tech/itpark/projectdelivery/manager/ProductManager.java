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

    public Product getById(long id) {
        return template.queryForObject("SELECT * FROM products WHERE product_id = :product_id LIMIT 50",
                Map.of("product_id", id),
                rowMapper
        );
    }

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

    public Product removeById(long id) {
        Product item = getById(id);
        template.update("DELETE FROM products WHERE product_id = :product_id",
                Map.of("product_id", item.getId())
        );
        return item;
    }
}

