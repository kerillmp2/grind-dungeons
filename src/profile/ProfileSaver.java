package profile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import utils.MessageController;

public class ProfileSaver {

    public static void saveProfile(Profile profile, String path) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path));
            objectOutputStream.writeObject(profile);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            MessageController.print("ERROR on saving data! File " + path + " not found!");
        } catch (IOException e) {
            MessageController.print("ERROR on saving data! " + "Error initializing stream!");
            e.printStackTrace();
        }
    }
}
