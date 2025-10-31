package org.cibertec.client;

import org.cibertec.dto.ProductoResponseDTO;
import org.cibertec.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "producto", url="http://localhost:8080/producto")
public interface ProductoClient {
	@GetMapping(value = "/producto/buscar", params = "id")
    ResponseEntity<ApiResponse<ProductoResponseDTO>> obtenerProductoPorID(@RequestParam int id);
}