package jchess;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import jchess.Java_Frontend.JChessView;

public class JChessApp extends SingleFrameApplication {
    public static JChessView jcv;
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        jcv = new JChessView(this);
        show(jcv);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of JChessApp
     */
    public static JChessApp getApplication() {
        return Application.getInstance(JChessApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(JChessApp.class, args);
    }
}
