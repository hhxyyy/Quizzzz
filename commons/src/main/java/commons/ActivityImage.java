package commons;

public class ActivityImage {

    private byte[] image;
    private String name;

    /**
     * Default constructor for OM
     */
    public ActivityImage() {
    }

    /**
     * Constructor for ActivityImage
     * @param image image in byte array form
     * @param name the name of the path of the image
     */
    public ActivityImage(byte[] image, String name) {
        this.image = image;
        this.name = name;
    }

    /**
     * Getter for name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for file
     * @return
     */
    public byte[] getImage() {
        return image;
    }
}
