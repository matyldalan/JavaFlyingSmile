/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flyingSmile;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author matyldalange
 */
public class Renderer extends JPanel {
    
    
    private static final long serialVersionUID=1L;
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        
        FlyingSmile.flyingSmile.repaint(g);
    }
}
