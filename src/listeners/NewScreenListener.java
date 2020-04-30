package listeners;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.EventListener;

public interface NewScreenListener extends EventHandler {
    public void ChangeScreen(Node node);
    public void removeTopScreen();
}
