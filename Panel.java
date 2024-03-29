/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flyingSmile;


import java.awt.*;
import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.IOException;
 import javax.imageio.ImageIO;
 import javax.swing.JPanel;
/**
 *
 * @author matyldalange
 */

 //tworzenie panelu i ladowanie obrazka jablka
 public class Panel extends JPanel {
 
 	private BufferedImage image;
 
 	public Panel() {
 		super();
 
 		File imageFile = new File("/Users/matyldalange/NetBeansProjects/FlyingSmile/src/flyingSmile/jablko.png");
 		try {
 			image = ImageIO.read(imageFile);
 		} catch (IOException e) {
 			System.err.println("Blad odczytu obrazka");
 			e.printStackTrace();
 		}
 
 		Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
 		setPreferredSize(dimension);
 	}
 
 	@Override
 	public void paintComponent(Graphics g) {
 		Graphics2D g2d = (Graphics2D) g;
 		g2d.drawImage(image, 0, 0, this);
 	}
 }