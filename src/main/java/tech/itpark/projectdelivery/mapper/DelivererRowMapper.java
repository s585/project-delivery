package tech.itpark.projectdelivery.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.model.Deliverer;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DelivererRowMapper implements RowMapper<Deliverer> {
    public Deliverer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Deliverer(
                rs.getLong("deliverer_id"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getDouble("lon"),
                rs.getDouble("lat")
        );
    }
}