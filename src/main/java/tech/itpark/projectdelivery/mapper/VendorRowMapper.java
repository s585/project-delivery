package tech.itpark.projectdelivery.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.model.Vendor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VendorRowMapper implements RowMapper<Vendor> {
    public Vendor mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vendor(
                rs.getLong("vendor_id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getDouble("lon"),
                rs.getDouble("lat")
        );
    }
}
