package com.ferreteria.Ferreteria.services;

import com.ferreteria.Ferreteria.domain.Producto;
import com.ferreteria.Ferreteria.exceptions.ProductoNotFoundException;
import com.ferreteria.Ferreteria.exceptions.StockInsufficientException;
import com.ferreteria.Ferreteria.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productRepository;

    public List<Producto> getAllProducts() {
        return productRepository.findAll();
    }

    public Producto getProductById(Long id) {
        Optional<Producto> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "el Producto no se encuentra " + id);
        }
    }

    public Producto createProduct(Producto product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Producto product = getProductById(id);
        productRepository.delete(product);
    }


    public Producto findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
    public Producto comprarProducto(Long productId, int cantidadComprada) {
        Producto producto = productRepository.findById(productId)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + productId));
        if (cantidadComprada <= 0) {
            throw new IllegalArgumentException("La cantidad a comprar debe ser mayor a cero");
        }
        if (producto.getCantidad() < cantidadComprada) {
            throw new StockInsufficientException("Cantidad insuficiente para el producto con ID: " + productId);
        }
        if (producto.getCantidad() >= cantidadComprada) {
            producto.setCantidad(producto.getCantidad() - cantidadComprada);
            producto.setTotalaPagar(cantidadComprada); // Calcula el total a pagar para este producto
            productRepository.save(producto);
            return producto;
        } else {
            throw new StockInsufficientException("Cantidad insuficiente para el producto con ID: " + productId);
        }
    }
}
