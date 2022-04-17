package utez.edu.mx.citas.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchivoUtileria {
    
	static Logger logger = LoggerFactory.getLogger(ArchivoUtileria.class);

    public static String guardarArchivo(MultipartFile multipartFile, String ruta) {
		String nombreArchivo = multipartFile.getOriginalFilename();
		try {
			String rutaArchivo = ruta + "/" + nombreArchivo;
			File file = new File(rutaArchivo);
			multipartFile.transferTo(file);
			return nombreArchivo;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return "null";
		}
	}
}
