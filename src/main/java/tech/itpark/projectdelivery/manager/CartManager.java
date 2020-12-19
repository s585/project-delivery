package tech.itpark.projectdelivery.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.itpark.projectdelivery.exception.DataAccessException;
import tech.itpark.projectdelivery.exception.NotFoundException;
import tech.itpark.projectdelivery.model.Cart;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartManager {
    private final DataSource dataSource;

    public List<Cart> getAll() {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rsCart = stmt.executeQuery("SELECT * FROM carts LIMIT 50")
        ) {
            ArrayList<Cart> items = new ArrayList<>();
            while (rsCart.next()) {
                Cart cart = new Cart();
                List<Integer> products = new ArrayList<>();
                List<Integer> qty = new ArrayList<>();

                cart.setId(rsCart.getLong("cart_id"));
                cart.setCustomerId(rsCart.getLong("customer_id"));


                try (PreparedStatement psProduct = conn.prepareStatement(
                        "SELECT product_id FROM carts_products WHERE cart_id = ? LIMIT 50")) {
                    psProduct.setLong(1, cart.getId());
                    ResultSet rsProduct = psProduct.executeQuery();
                    while (rsProduct.next()) {
                        products.add(rsProduct.getInt("product_id"));
                        cart.setProducts(products);
                    }
                }

                try (PreparedStatement psQty = conn.prepareStatement(
                        "SELECT qty FROM carts_products WHERE cart_id = ? LIMIT 50")) {
                    psQty.setLong(1, cart.getId());
                    ResultSet rsQty = psQty.executeQuery();
                    while (rsQty.next()) {
                        qty.add(rsQty.getInt("qty"));
                        cart.setQty(qty);
                    }
                }

                try (PreparedStatement psVendor = conn.prepareStatement(
                        "SELECT vendor_id FROM total WHERE cart_id = ?")
                ) {
                    psVendor.setLong(1, cart.getId());
                    ResultSet rsTotal = psVendor.executeQuery();
                    while (rsTotal.next()) {
                        cart.setVendorId(rsTotal.getLong("vendor_id"));
                    }
                }

                try (PreparedStatement psCategory = conn.prepareStatement(
                        "SELECT category_id FROM total WHERE cart_id = ?")
                ) {
                    psCategory.setLong(1, cart.getId());
                    ResultSet rsCategory = psCategory.executeQuery();
                    while (rsCategory.next()) {
                        cart.setCategoryId(rsCategory.getLong("category_id"));
                    }
                }

                try (PreparedStatement psTotal = conn.prepareStatement("SELECT total FROM total WHERE cart_id = ?")
                ) {
                    psTotal.setLong(1, cart.getId());
                    ResultSet rsTotal = psTotal.executeQuery();
                    while (rsTotal.next()) {
                        cart.setTotal(rsTotal.getInt("total"));
                    }
                }
                items.add(cart);
            }
            return items;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }


    public Cart getById(long id) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement psCart = conn.prepareStatement(
                        "SELECT * FROM carts WHERE cart_id = ? LIMIT 50")
        ) {
            psCart.setLong(1, id);
            ResultSet rsCart = psCart.executeQuery();

            if (rsCart.next()) {
                Cart cart = new Cart();
                List<Integer> products = new ArrayList<>();
                List<Integer> qty = new ArrayList<>();

                cart.setId(rsCart.getLong("cart_id"));
                cart.setCustomerId(rsCart.getLong("customer_id"));


                try (PreparedStatement psProduct = conn.prepareStatement(
                        "SELECT product_id FROM carts_products WHERE cart_id = ? LIMIT 50")) {
                    psProduct.setLong(1, id);
                    ResultSet rsProduct = psProduct.executeQuery();
                    while (rsProduct.next()) {
                        products.add(rsProduct.getInt("product_id"));
                        cart.setProducts(products);
                    }
                }

                try (PreparedStatement psQty = conn.prepareStatement(
                        "SELECT qty FROM carts_products WHERE cart_id = ? LIMIT 50")) {
                    psQty.setLong(1, id);
                    ResultSet rsQty = psQty.executeQuery();
                    while (rsQty.next()) {
                        qty.add(rsQty.getInt("qty"));
                        cart.setQty(qty);
                    }
                }

                try (PreparedStatement psVendor = conn.prepareStatement(
                        "SELECT vendor_id FROM total WHERE cart_id = ?")
                ) {
                    psVendor.setLong(1, id);
                    ResultSet rsTotal = psVendor.executeQuery();
                    while (rsTotal.next()) {
                        cart.setVendorId(rsTotal.getLong("vendor_id"));
                    }
                }

                try (PreparedStatement psCategory = conn.prepareStatement(
                        "SELECT category_id FROM total WHERE cart_id = ?")
                ) {
                    psCategory.setLong(1, id);
                    ResultSet rsCategory = psCategory.executeQuery();
                    while (rsCategory.next()) {
                        cart.setCategoryId(rsCategory.getLong("category_id"));
                    }
                }

                try (PreparedStatement psTotal = conn.prepareStatement("SELECT total FROM total WHERE cart_id = ?")
                ) {
                    psTotal.setLong(1, id);
                    ResultSet rsTotal = psTotal.executeQuery();
                    while (rsTotal.next()) {
                        cart.setTotal(rsTotal.getInt("total"));
                    }
                }
                return cart;
            }
            throw new NotFoundException();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Cart save(Cart item) {
        if (item.getId() == 0) {
            try (
                    Connection conn = dataSource.getConnection();
                    PreparedStatement psCart = conn.prepareStatement("INSERT INTO carts (customer_id) VALUES (?)",
                            Statement.RETURN_GENERATED_KEYS
                    )
            ) {
                psCart.setLong(1, item.getCustomerId());
                psCart.execute();

                try (ResultSet keys = psCart.getGeneratedKeys()) {
                    if (keys.next()) {
                        long id = keys.getLong(1);
                        ArrayList<Integer> products = (ArrayList<Integer>) item.getProducts();
                        ArrayList<Integer> qty = (ArrayList<Integer>) item.getQty();
                        for (Integer product : products) {
                            try (PreparedStatement psProduct = conn.prepareStatement(
                                    "INSERT INTO carts_products (cart_id, product_id) VALUES ( ?,? )")
                            ) {
                                psProduct.setLong(1, id);
                                psProduct.setInt(2, product);
                                psProduct.execute();
                            }
                        }
                        int index = 0;
                        for (Integer integer : qty) {
                            try (final PreparedStatement psQty = conn.prepareStatement(
                                    "UPDATE carts_products SET qty = ? WHERE cart_id = ? AND product_id = ?")
                            ) {
                                psQty.setLong(2, id);
                                psQty.setInt(3, products.get(index++));
                                psQty.setInt(1, integer);
                                psQty.execute();
                            }
                        }
                        return getById(id);
                    }
                }
                throw new DataAccessException("No generated keys");
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        }
        return item;
    }

    public Cart removeById(long id) {
        Cart item = getById(id);
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM carts WHERE cart_id = ?")
        ) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return item;
    }

}




