package co.edu.upb.distributed_storage_main_server.DTOs.hello;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "HelloResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class HelloResponse {
    private @Getter @Setter String message;
}
