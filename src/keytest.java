import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
  
public class keytest extends JPanel {

    public keytest(){
    	addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                   switch(e.getKeyCode()){
                   case KeyEvent.VK_ENTER: errprinter err=new errprinter(3);
                  }
                  
                  repaint();
           }
     });
    }
}
  
