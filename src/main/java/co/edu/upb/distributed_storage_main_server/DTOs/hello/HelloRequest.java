package co.edu.upb.distributed_storage_main_server.DTOs.hello;

import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@XmlRootElement(namespace = Singleton.HELLO_NAMESPACE_URI, name = "HelloRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class HelloRequest {
    private @Getter String name;
}
