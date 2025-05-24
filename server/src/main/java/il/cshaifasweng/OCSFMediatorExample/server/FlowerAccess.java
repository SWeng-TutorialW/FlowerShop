package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlowerAccess {

    //Get all flowers
    public static List<Flower> getAllFlowers() {
        List<Flower> flowers = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM flowers")) {

            while (rs.next()) {
                Flower flower = new Flower(
                        rs.getInt("sku"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getString("description")
                );
                flowers.add(flower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flowers;
    }

    //Get a flower by SKU
    public static Flower getFlowerBySku(int sku) {
        Flower flower = null;
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM flowers WHERE sku = ?")) {

            stmt.setInt(1, sku);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    flower = new Flower(
                            rs.getInt("sku"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getDouble("price"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flower;
    }

    //Update flower price
    public static boolean updateFlowerPrice(int sku, double newPrice) {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE flowers SET price = ? WHERE sku = ?")) {

            stmt.setDouble(1, newPrice);
            stmt.setInt(2, sku);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

