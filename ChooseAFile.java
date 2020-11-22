import java.io.*; 
import javax.swing.JFileChooser; 
import java.util.Scanner; 
public class ChooseAFile {
  
  public static void main(String[] args){
       
	   //creating a Jfilechooser menu (the window that pops up)
	   JFileChooser chooser = new JFileChooser(".");
       
	   //fc = user interaction
        int fc = chooser.showOpenDialog(null);
		
		//file = selected file´s path
		String file =  chooser.getSelectedFile().getAbsolutePath();
		
		//if user presses the approve button print out ...
	   if(fc == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose: " +
                    file);
        }
    }
}