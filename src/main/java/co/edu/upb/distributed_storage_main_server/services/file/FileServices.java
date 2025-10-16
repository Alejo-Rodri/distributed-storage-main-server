package co.edu.upb.distributed_storage_main_server.services.file;

import co.edu.upb.distributed_storage_main_server.grpc.client.file.IFileClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileServices implements IFileServices {
    private final IFileClient iFileClient;

    @Override
    public boolean mvRenameFile(String sourceFile, String destinationDir) {
        return false;
    }

    @Override
    public boolean deleteFile(String filePath) {
        return false;
    }

    @Override
    public boolean uploadFile(String filename, byte[] bytes) {
        return iFileClient.uploadFile(filename, bytes);
    }
}
