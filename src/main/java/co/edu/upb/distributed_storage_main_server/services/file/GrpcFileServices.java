package co.edu.upb.distributed_storage_main_server.services.file;

import co.edu.upb.distributed_storage_main_server.grpc.client.file.FileNodeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class GrpcFileServices implements IFileServices {

    private final FileNodeClient fileNodeClient;

    @Override
    public boolean mvRenameFile(String sourceFile, String destinationDir) {
        var resp = fileNodeClient.moveOrRename(sourceFile, destinationDir);
        return resp.getSuccess();
    }

    @Override
    public boolean deleteFile(String filePath) {
        var resp = fileNodeClient.deleteFile(filePath);
        return resp.getSuccess();
    }
}
