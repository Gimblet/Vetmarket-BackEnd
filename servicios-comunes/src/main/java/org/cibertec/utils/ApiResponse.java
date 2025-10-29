package org.cibertec.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

	private boolean success;
	//private LocalDateTime fecha;
	private String mensaje;
	private T data;
	
	

	

}
