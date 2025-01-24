package jchess.Java_Frontend;

import java.awt.*;
import java.net.*;
// import jchess.JChessApp;
import java.util.HashMap;

public final class Image_Loader
{
    static private HashMap<String,Image> imageStore = new HashMap<>();
    
    public static Image getImage(String name){
        if(imageStore.containsKey(name)){
            return imageStore.get(name);
        }
        imageStore.put(name,loadImage(name));
        return imageStore.get(name);
    }

    private static Image loadImage(String name)
    {
        Image img = null;
        URL url = null;
        Toolkit tk = Toolkit.getDefaultToolkit();
        int i = 0;
        try
        {
            String imageLink = "resources/theme/default/images/" + name;
            url = Image_Loader.class.getResource(imageLink);            
            img = tk.getImage(url);
            //Hack, used because the image only decides its own dimensions when being observed.
            i = img.getWidth(null) +img.getHeight(null); 
        }
        catch (Exception e)
        {
            System.out.println("some error loading image!");
            e.printStackTrace();
        }
        return img;
    }/*--endOf-loadImage--*/

}
