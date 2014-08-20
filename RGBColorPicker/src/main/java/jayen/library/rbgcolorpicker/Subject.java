package jayen.library.rbgcolorpicker;

/**
 * The Subject interface,
 * implemented by classes that need to
 * notify Observers of changes that
 * they are interested in.
 */
public interface Subject {

    public void register(Observer observer);

    public void unRegister(Observer observer);

    public void notifyObservers();

}
