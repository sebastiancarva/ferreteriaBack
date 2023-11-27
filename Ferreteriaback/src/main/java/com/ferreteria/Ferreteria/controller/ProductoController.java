package com.ferreteria.Ferreteria.controller;

import com.ferreteria.Ferreteria.domain.Producto;
import com.ferreteria.Ferreteria.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductoController {

    @Autowired
    private ProductoService productService;

    @GetMapping("/allproducts")
    public ResponseEntity<List<Producto>> getAllProducts() {
        List<Producto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductById(@PathVariable Long id) {
        try {
            Producto product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Producto product) {
        Producto createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto Creado Con Exito");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Se Elimino el producto Satisfactoriamente");
    }
    @PostMapping("/{productId}/buy")
    public ResponseEntity<Producto> buyProduct(@PathVariable Long productId, @RequestParam Integer quantity) {
        Producto productoComprado = productService.comprarProducto(productId, quantity);

        if (productoComprado != null) {
            double totalToPay = productoComprado.getPrecio() * quantity; // Calcular el total a pagar
            productoComprado.setTotalaPagar(totalToPay); // Establecer el total a pagar en el objeto Producto
            return ResponseEntity.ok(productoComprado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
