package co.edu.upb.distributed_storage_main_server.grpc.client.file;

import com.example.grpc.FileServiceGrpc;
import com.example.grpc.UploadFileRequest;
import com.example.grpc.UploadFileResponse;
import com.google.protobuf.ByteString;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Service;

@Service
public class FileClient implements IFileClient{
    private final FileServiceGrpc.FileServiceBlockingStub stub;

    public FileClient(GrpcChannelFactory channelFactory) {
        var channel = channelFactory.createChannel("localhost:9090");
        this.stub = FileServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public boolean uploadFile(String filename, byte[] bytes) {
        UploadFileRequest request = UploadFileRequest.newBuilder()
                .setFileName(filename)
                .setFileData(ByteString.copyFrom(bytes))
                .build();

        UploadFileResponse response = stub.uploadFile(request);
        return response.getSuccess();
    }
}
