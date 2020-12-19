package tech.itpark.projectdelivery.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.exception.DataAccessException;
import tech.itpark.projectdelivery.exception.NotFoundException;
import tech.itpark.projectdelivery.model.Cart;
import tech.itpark.projectdelivery.model.Order;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderManager {
    private final DataSource dataSource;
    private final DeliveryManager deliveryManager;

    public List<Order> getAll() {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rsOrder = stmt.executeQuery("SELECT * FROM customers_orders LIMIT 50")
        ) {

            List<Order> items = new ArrayList<>();
            while (rsOrder.next()) {
                Order order = mapRow(rsOrder);

                List<Integer> products = new ArrayList<>();
                List<Integer> qty = new ArrayList<>();

                try (PreparedStatement psProduct = conn.prepareStatement(
                        "SELECT product_id FROM orders_carts WHERE order_id = ? LIMIT 50")) {
                    psProduct.setLong(1, order.getId());
                    ResultSet rsProduct = psProduct.executeQuery();
                    while (rsProduct.next()) {
                        products.add(rsProduct.getInt("product_id"));
                        order.setProducts(products);
                    }
                }

                try (PreparedStatement psQty = conn.prepareStatement(
                        "SELECT qty FROM orders_carts WHERE order_id = ? LIMIT 50")) {
                    psQty.setLong(1, order.getId());
                    ResultSet rsQty = psQty.executeQuery();
                    while (rsQty.next()) {
                        qty.add(rsQty.getInt("qty"));
                        order.setQty(qty);
                    }
                }
                items.add(order);
            }
            return items;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Order getById(long id) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement psOrder = conn.prepareStatement(
                        "SELECT * FROM customers_orders WHERE order_id = ? LIMIT 50")

        ) {
            psOrder.setLong(1, id);
            ResultSet rsOrder = psOrder.executeQuery();

            if (rsOrder.next()) {
                Order order = mapRow(rsOrder);

                List<Integer> products = new ArrayList<>();
                List<Integer> qty = new ArrayList<>();

                try (PreparedStatement psProduct = conn.prepareStatement(
                        "SELECT product_id FROM orders_carts WHERE order_id = ? LIMIT 50")) {
                    psProduct.setLong(1, order.getId());
                    ResultSet rsProduct = psProduct.executeQuery();
                    while (rsProduct.next()) {
                        products.add(rsProduct.getInt("product_id"));
                        order.setProducts(products);
                    }
                }

                try (PreparedStatement psQty = conn.prepareStatement(
                        "SELECT qty FROM orders_carts WHERE order_id = ? LIMIT 50")) {
                    psQty.setLong(1, order.getId());
                    ResultSet rsQty = psQty.executeQuery();
                    while (rsQty.next()) {
                        qty.add(rsQty.getInt("qty"));
                        order.setQty(qty);
                    }
                }
                return order;
            }
            throw new NotFoundException();

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List<Order> getByCustomerId(long customerId) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement psOrder = conn.prepareStatement(
                        "SELECT * FROM customers_orders WHERE customer_id = ? LIMIT 50")

        ) {
            psOrder.setLong(1, customerId);
            ResultSet rsOrder = psOrder.executeQuery();

            List<Order> items = new ArrayList<>();
            while (rsOrder.next()) {
                Order order = mapRow(rsOrder);

                List<Integer> products = new ArrayList<>();
                List<Integer> qty = new ArrayList<>();

                try (PreparedStatement psProduct = conn.prepareStatement(
                        "SELECT product_id FROM customers_carts_orders_vendors " +
                                "WHERE customer_id = ? AND order_id = ? LIMIT 50")) {
                    psProduct.setLong(1, order.getCustomerId());
                    psProduct.setLong(2, order.getId());
                    ResultSet rsProduct = psProduct.executeQuery();
                    while (rsProduct.next()) {
                        products.add(rsProduct.getInt("product_id"));
                        order.setProducts(products);
                    }
                }

                try (PreparedStatement psQty = conn.prepareStatement(
                        "SELECT qty FROM customers_carts_orders_vendors " +
                                "WHERE customer_id = ? AND order_id = ? LIMIT 50")) {
                    psQty.setLong(1, order.getCustomerId());
                    psQty.setLong(2, order.getId());
                    ResultSet rsQty = psQty.executeQuery();
                    while (rsQty.next()) {
                        qty.add(rsQty.getInt("qty"));
                        order.setQty(qty);
                    }
                }
                items.add(order);

            }
            return items;

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List<Order> getByVendorId(long vendorId) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement psOrder = conn.prepareStatement(
                        "SELECT * FROM customers_orders WHERE vendor_id = ? LIMIT 50");

        ) {
            psOrder.setLong(1, vendorId);
            ResultSet rsOrder = psOrder.executeQuery();

            List<Order> items = new ArrayList<>();
            while (rsOrder.next()) {
                Order order = mapRow(rsOrder);

                List<Integer> products = new ArrayList<>();
                List<Integer> qty = new ArrayList<>();

                try (PreparedStatement psProduct = conn.prepareStatement(
                        "SELECT product_id FROM customers_carts_orders_vendors" +
                                " WHERE vendor_id = ? AND order_id = ? LIMIT 50")) {
                    psProduct.setLong(1, vendorId);
                    psProduct.setLong(2, order.getId());
                    ResultSet rsProduct = psProduct.executeQuery();
                    while (rsProduct.next()) {
                        products.add(rsProduct.getInt("product_id"));
                        order.setProducts(products);
                    }
                }

                try (PreparedStatement psQty = conn.prepareStatement(
                        "SELECT qty FROM customers_carts_orders_vendors " +
                                "WHERE vendor_id = ? AND order_id = ? LIMIT 50")) {
                    psQty.setLong(1, vendorId);
                    psQty.setLong(2, order.getId());
                    ResultSet rsQty = psQty.executeQuery();
                    while (rsQty.next()) {
                        qty.add(rsQty.getInt("qty"));
                        order.setQty(qty);
                    }
                }
                items.add(order);
            }
            return items;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List<Order> search(long delivererId, String date1, String date2) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement psOrder = conn.prepareStatement(
                        "SELECT * FROM customers_orders" +
                                " WHERE deliverer_id = ?" +
                                " AND date_time BETWEEN ? AND ? LIMIT 50");

        ) {
            psOrder.setLong(1, delivererId);
            psOrder.setTimestamp(2, Timestamp.valueOf(date1));
            psOrder.setTimestamp(3, Timestamp.valueOf(date2));
            ResultSet rsOrder = psOrder.executeQuery();

            List<Order> items = new ArrayList<>();
            while (rsOrder.next()) {
                Order order = mapRow(rsOrder);

                List<Integer> products = new ArrayList<>();
                List<Integer> qty = new ArrayList<>();

                try (PreparedStatement psProduct = conn.prepareStatement(
                        "SELECT product_id FROM orders_carts WHERE deliverer_id = ? AND order_id = ? LIMIT 50")) {
                    psProduct.setLong(1, delivererId);
                    psProduct.setLong(2, order.getId());
                    ResultSet rsProduct = psProduct.executeQuery();
                    while (rsProduct.next()) {
                        products.add(rsProduct.getInt("product_id"));
                        order.setProducts(products);
                    }
                }

                try (PreparedStatement psQty = conn.prepareStatement(
                        "SELECT qty FROM orders_carts WHERE deliverer_id = ? AND order_id = ? LIMIT 50")) {
                    psQty.setLong(1, delivererId);
                    psQty.setLong(2, order.getId());
                    ResultSet rsQty = psQty.executeQuery();
                    while (rsQty.next()) {
                        qty.add(rsQty.getInt("qty"));
                        order.setQty(qty);
                    }
                }
                items.add(order);
            }
            return items;

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Order checkOut(Cart item) {

        String query = "INSERT INTO orders (cart_id, deliverer_id, delivery_price) VALUES (?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement psOrder = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            psOrder.setLong(1, item.getId());
            psOrder.setLong(2, deliveryManager.getDeliverer(item.getVendorId()));
            psOrder.setInt(3, deliveryManager.getDeliveryPrice(
                    item.getCustomerId(), item.getVendorId(), item.getCategoryId()));
            psOrder.execute();

            try (ResultSet keys = psOrder.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    return getById(id);
                }
            }
            throw new DataAccessException("No generated keys");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Order removeById(long id) {
        Order item = getById(id);
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement deleteOrder = conn.prepareStatement("DELETE FROM orders WHERE order_id = ?")
        ) {
            deleteOrder.setLong(1, id);
            deleteOrder.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return item;
    }

    private Order mapRow(ResultSet rsOrder) throws SQLException {
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
