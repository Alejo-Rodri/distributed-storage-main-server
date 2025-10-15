package co.edu.upb.distributed_storage_main_server.grpc.client.hello;

import co.edu.upb.distributed_storage_main_server.grpc.client.AbstractClient;
import com.example.grpc.HelloRequest;
import com.example.grpc.HelloServiceGrpc;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Service;

@Service
public class HelloClient extends AbstractClient implements IHelloClient {
    private final HelloServiceGrpc.HelloServiceBlockingStub stub;

    public HelloClient(GrpcChannelFactory channelFactory) {
        var channel = channelFactory.createChannel("localhost:9090");
        this.stub = HelloServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public String greet(String name) {
        var request = HelloRequest.newBuilder().setName(name).build();
        var reply = stub.sayHello(request);

        return reply.getMessage();
    }
}
