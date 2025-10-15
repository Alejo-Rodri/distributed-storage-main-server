package co.edu.upb.distributed_storage_main_server.grpc.client.directory;

import co.edu.upb.distributed_storage_main_server.DTOs.directory.CreateDirectoryResponse;
import co.edu.upb.distributed_storage_main_server.grpc.client.AbstractClient;
import com.example.grpc.CreateDirectoryRequest;
import com.example.grpc.DirectoryServiceGrpc;
import com.example.grpc.HelloServiceGrpc;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class DirectoryClient extends AbstractClient implements IDirectoryClient {
    protected final DirectoryServiceGrpc.DirectoryServiceBlockingStub stub;

    public DirectoryClient(GrpcChannelFactory channelFactory) {
        var channel = channelFactory.createChannel("localhost:9090");
        this.stub = DirectoryServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public CreateDirectoryResponse createDirectory(String path) {
        var request = CreateDirectoryRequest.newBuilder().setPath(path).build();
        var reply = stub.createDirectory(request);

        var response = CreateDirectoryResponse.builder()
                .path(reply.getPath())
                .owner(reply.getOwner())
                .createdAt(LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(reply.getCreatedAt().getSeconds(), reply.getCreatedAt().getNanos()),
                        ZoneId.systemDefault()
                ))
                .build();

        return response;
    }
}
