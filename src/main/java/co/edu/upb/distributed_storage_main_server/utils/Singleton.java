package co.edu.upb.distributed_storage_main_server.utils;

public enum Singleton {
    INSTANCE;

    private static final String BASE_XSD_LOCATION = "xml/schema/";

    public static final String FILE_XSD_LOCATION = BASE_XSD_LOCATION + "files.xsd";
    public static final String FILE_NAMESPACE_URI = "http://example.com/files";

    public static final String HELLO_XSD_LOCATION = BASE_XSD_LOCATION + "hello.xsd";
    public static final String HELLO_NAMESPACE_URI = "http://example.com/hello";

    public static final String DIRECTORY_XSD_LOCATION = BASE_XSD_LOCATION + "directories.xsd";
    public static final String DIRECTORY_NAMESPACE_URI = "http://example.com/directories";
}
