package edu.poly.shop.utils;

import java.util.Collections;
import java.util.List;

import edu.poly.shop.model.Product;

public class RadomProductUtils {
    
    public List<Product> findRandomSimilarProducts(List<Product> similarProducts, int count) {
        // Trộn danh sách sản phẩm
        Collections.shuffle(similarProducts);
        // Lấy ra số lượng sản phẩm ngẫu nhiên
        List<Product> randomSimilarProducts = similarProducts.subList(0, Math.min(similarProducts.size(), count));
        return randomSimilarProducts;
    }
}
