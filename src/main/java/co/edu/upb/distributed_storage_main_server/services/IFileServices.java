package co.edu.upb.distributed_storage_main_server.services;

public interface IFileServices {
    boolean mvRenameFile(String sourceFile, String destinationDir);
    boolean deleteFile(String filePath);
}
