package co.edu.upb.distributed_storage_main_server.services.file;

import org.springframework.stereotype.Service;

@Service
public class FileServices implements IFileServices {
    @Override
    public boolean mvRenameFile(String sourceFile, String destinationDir) {
        return false;
    }

    @Override
    public boolean deleteFile(String filePath) {
        return false;
    }
}
