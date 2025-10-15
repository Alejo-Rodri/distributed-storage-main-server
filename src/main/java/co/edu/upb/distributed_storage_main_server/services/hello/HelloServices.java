package co.edu.upb.distributed_storage_main_server.services.hello;

import co.edu.upb.distributed_storage_main_server.grpc.client.HelloClient;
import org.springframework.stereotype.Service;

@Service
public class HelloServices implements IHelloServices {
    private final HelloClient helloClient;

    public HelloServices(HelloClient helloClient) {
        this.helloClient = helloClient;
    }

    @Override
    public String sayHello(String name) {
        return helloClient.greet(name);
    }
}
