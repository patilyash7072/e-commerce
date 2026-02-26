package com.dss.ecommerce.entity;

public record Product(int productId, String name, String company, int price) implements Comparable<Product>{
    @Override
    public int compareTo(Product o) {
        return this.productId - o.productId;
    }
}
