package util;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JFrame11 {

		public static  void InsertImg(String Path){
		Image image1 = null;

	    try {
	        // 파일로부터 이미지 읽기
	        File sourceimage = new File(Path);
	        image1 = ImageIO.read(sourceimage);
	    
	    } catch (IOException e) {
	    }
	    

	    JFrame frame = new JFrame();
	    JLabel label1 = new JLabel(new ImageIcon(image1));
  
	    frame.getContentPane().add(label1, BorderLayout.CENTER);
	    frame.pack();
	    frame.setVisible(true);
		}

   


}