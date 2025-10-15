package co.edu.upb.distributed_storage_main_server.config;

import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig implements WsConfigurer {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        var servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "files")
    public DefaultWsdl11Definition filesWsdl(XsdSchema filesSchema) {
        var definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("FilesPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace(Singleton.FILE_NAMESPACE_URI);
        definition.setSchema(filesSchema);

        return definition;
    }

    @Bean
    public XsdSchema filesSchema() {
        return new SimpleXsdSchema(new ClassPathResource(Singleton.FILE_XSD_LOCATION));
    }

    @Bean(name = "hello")
    public DefaultWsdl11Definition helloWsdl(XsdSchema helloSchema) {
        var definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("HelloPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace(Singleton.HELLO_NAMESPACE_URI);
        definition.setSchema(helloSchema);

        return definition;
    }

    @Bean
    public XsdSchema helloSchema() {
        return new SimpleXsdSchema(new ClassPathResource(Singleton.HELLO_XSD_LOCATION));
    }

    @Bean(name = "directory")
    public DefaultWsdl11Definition directoryWsdl(XsdSchema directorySchema) {
        var definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("DirectoryPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace(Singleton.DIRECTORY_NAMESPACE_URI);
        definition.setSchema(directorySchema);

        return definition;
    }

    @Bean
    public XsdSchema directorySchema() {
        return new SimpleXsdSchema(new ClassPathResource(Singleton.DIRECTORY_XSD_LOCATION));
    }

}
