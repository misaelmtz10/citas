package utez.edu.mx.citas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
  
  public void addResourceHandlers(ResourceHandlerRegistry handlerRegistry) {

    handlerRegistry.addResourceHandler("/file-citas/**").addResourceLocations("file:C:/citas/file-citas/");
  }
}
