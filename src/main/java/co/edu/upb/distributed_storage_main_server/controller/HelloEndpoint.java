package co.edu.upb.distributed_storage_main_server.controller;

import co.edu.upb.distributed_storage_main_server.DTOs.hello.HelloRequest;
import co.edu.upb.distributed_storage_main_server.DTOs.hello.HelloResponse;
import co.edu.upb.distributed_storage_main_server.services.hello.IHelloServices;
import co.edu.upb.distributed_storage_main_server.utils.Singleton;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HelloEndpoint {
    private final IHelloServices iHelloServices;

    public HelloEndpoint(IHelloServices iHelloServices) {
        this.iHelloServices = iHelloServices;
    }

    @PayloadRoot(namespace = Singleton.HELLO_NAMESPACE_URI, localPart = "HelloRequest")
    @ResponsePayload
    public HelloResponse sayHello(@RequestPayload HelloRequest request) {
        var response = new HelloResponse();
        response.setMessage(iHelloServices.sayHello(request.getName()));

        return response;
    }
}
