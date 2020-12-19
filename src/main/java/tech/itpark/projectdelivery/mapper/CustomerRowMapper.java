package tech.itpark.projectdelivery.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(
                rs.getLong("customer_id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getDouble("lon"),
                rs.getDouble("lat")
        );
    }
}