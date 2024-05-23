package io.github.dragoncrafter10.wordlike.meta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.io.ObjectInputFilter.FilterInfo;
import java.io.ObjectInputFilter.Status;
import java.nio.file.Path;

import io.github.dragoncrafter10.wordlike.Enemy;

/**
 * A collection of data that can be saved for meta-progression.
 */
public final class SaveData implements Serializable {
    
    // Increment this on every commit and pr that changes this file
    @Serial
    private static final long serialVersionUID = 2l;

    private Dictionary discoveries;

    public SaveData() {
        discoveries = new Dictionary();
    }

    public Dictionary getDiscoveries(){
        return discoveries;
    }

    /**
     * Saves this data by writing it to the given {@link ObjectOutputStream}. This
     * is nearly identical to simply using {@link ObjectOutputStream#writeObject(Object)},
     * but will provide a {@code boolean} result rather than exceptions.
     * 
     * @param os The stream to output to.
     * @return {@code true} if the save was successful, {@code false} otherwise.
     */
    public boolean save(ObjectOutputStream os){
        if(os == null) return false;

        try {
            os.writeObject(this);
            return true;
        } catch (IOException e) { // save failed
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves this data by writing it to the given {@link OutputStream}. This
     * is nearly identical to simply constructing an {@link ObjectOutputStream} with the given
     * stream and using {@link ObjectOutputStream#writeObject(Object)},
     * but will provide a {@code boolean} result rather than exceptions. 
     * 
     * @param os The stream to output to.
     * @return {@code true} if the save was successful, {@code false} otherwise.
     */
    public boolean save(OutputStream os){
        if(os == null) return false;

        try (var oos = new ObjectOutputStream(os)) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) { // save failed
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves this data to a given file. If the file is found to not exist, this
     * method will create the file before saving. If the given file cannot be
     * written to, the saving will simply fail.
     * 
     * @param file The file to save to.
     * @return {@code true} if the save was successful, {@code false} otherwise.
     */
    public boolean save(File file){
        if(file == null) return false;

        if(!file.exists()){
            try {
                file.createNewFile();
                save(file);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        if(!file.canWrite()){
            return false;
        }

        try (var writer = new ObjectOutputStream(new FileOutputStream(file))) {
            writer.writeObject(this);
            return true;
        } catch (IOException e) { // save failed
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves this data to a given path. This is identical to calling {@link #save(File)} on
     * the result of {@link Path#toFile()}.
     * 
     * @param path The path to save to.
     * @return {@code true} if the save was successful, {@code false} otherwise.
     */
    public boolean save(Path path){
        if(path == null) return false;

        File file = path.toFile();

        return save(file);
    }

    public static SaveData load(InputStream in){
        try (ObjectInputStream is = new ObjectInputStream(in)) {
            is.setObjectInputFilter(SaveData::filterSaveData);
            return (SaveData) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SaveData load(ObjectInputStream in){
        try {
            return (SaveData) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Status filterSaveData(FilterInfo fi) {
        return Status.ALLOWED; // TODO: filter properly
    }

    static final Class<?>[] arr = {
        SaveData.class,
        Dictionary.class,
        DictionaryEntry.class,
        Enemy.class,
    };

    public void pushDiscovery(DictionaryEntry entry){
        discoveries.add(entry);
    }

    public void wipe(){
        discoveries.clear();
    }

    @Override
    public String toString() {
        return "SaveData [discoveries=" + discoveries + "]";
    }
}
