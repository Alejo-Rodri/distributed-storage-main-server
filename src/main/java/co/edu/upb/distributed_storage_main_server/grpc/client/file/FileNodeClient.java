package co.edu.upb.distributed_storage_main_server.grpc.client.file;

import com.example.grpc.*;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

@Component
public class FileNodeClient {
    private final FileNodeServiceGrpc.FileNodeServiceBlockingStub stub;

    public FileNodeClient(GrpcChannelFactory channelFactory) {
        // Usa el mismo esquema que HelloClient/DirectoryClient
        var channel = channelFactory.createChannel("localhost:9090");
        this.stub = FileNodeServiceGrpc.newBlockingStub(channel);
    }

    public MoveOrRenameResponse moveOrRename(String sourcePath, String destinationPath) {
        MoveOrRenameRequest req = MoveOrRenameRequest.newBuilder()
                .setSourcePath(sourcePath)
                .setDestinationPath(destinationPath)
                .build();
        return stub.moveOrRename(req);
    }

    public DeleteFileResponse deleteFile(String path) {
        DeleteFileRequest req = DeleteFileRequest.newBuilder()
                .setPath(path)
                .build();
        return stub.deleteFile(req);
    }
}
