package tech.itpark.projectdelivery.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderRowMapper implements RowMapper<Order> {
    public Order mapRow(ResultSet rsOrder, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rsOrder.getLong("order_id"));
        order.setCustomerId(rsOrder.getLong("customer_id"));
        order.setDelivererId(rsOrder.getLong("deliverer_id"));
        order.setDate(rsOrder.getTimestamp("date_time"));
        order.setTotal(rsOrder.getInt("total"));
        order.setDeliveryPrice(rsOrder.getInt("delivery_price"));
        order.setStatus(rsOrder.getInt("status"));
        return order;
    }
}
