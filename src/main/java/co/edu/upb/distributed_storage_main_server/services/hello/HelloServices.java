package co.edu.upb.distributed_storage_main_server.services.hello;

import co.edu.upb.distributed_storage_main_server.grpc.client.hello.IHelloClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HelloServices implements IHelloServices {
    private final IHelloClient helloClient;

    @Override
    public String sayHello(String name) {
        return helloClient.greet(name);
    }
}
