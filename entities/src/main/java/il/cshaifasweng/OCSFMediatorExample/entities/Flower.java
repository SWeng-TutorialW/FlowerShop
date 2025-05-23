package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class Flower implements Serializable {
    private int sku;
    private String name;
    private String type;
    private double price;
    private String description;

    // Constructor
    public Flower(int sku, String name, String type, double price, String description) {
        this.sku = sku;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
    }

    // Empty constructor
    public Flower() {}

    // Getters and setters
    public int getSku() { return sku; }
    public void setSku(int sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Flower{" +
                "sku=" + sku +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
