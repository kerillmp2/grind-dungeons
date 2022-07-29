package profile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import utils.MessageController;

public class ProfileLoader {

    public static Profile loadProfileFrom(String path) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path));
            Profile profile = (Profile) objectInputStream.readObject();
            objectInputStream.close();
            return profile;
        } catch (FileNotFoundException e) {
            MessageController.print("ERROR on loading data! File " + path + " not found!");
        } catch (IOException e) {
            MessageController.print("ERROR on loading data! " + "Error initializing stream!");
        } catch (ClassNotFoundException e) {
            MessageController.print("ERROR on loading data! " + "Invalid data in file!");
        }
        return Profile.withName("ERROR_ON_LOAD");
    }
}
