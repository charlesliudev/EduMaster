package persistence;

// represents data that can be saved to file
// *NOTE* interface design taken from TellerApp
public interface Saveable {
    // EFFECTS: writes the saveable to file
    boolean saveAll();
}