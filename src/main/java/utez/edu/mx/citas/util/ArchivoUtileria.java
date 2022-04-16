package utez.edu.mx.citas.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class ArchivoUtileria {
    
    public static String guardarArchivo(MultipartFile multipartFile, String ruta) {
		String nombreArchivo = multipartFile.getOriginalFilename();
		System.err.println(nombreArchivo);
		try {
			System.err.println(ruta);
			String rutaArchivo = ruta + "/" + nombreArchivo;
			System.err.println(rutaArchivo);
			File file = new File(rutaArchivo);
			multipartFile.transferTo(file);
			return nombreArchivo;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return "null";
		}
	}
}
