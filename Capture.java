package screenshooter;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
//tests + UML
public class Capture {
    
    JFileChooser fs=new JFileChooser();
    private FiltreFichier filtre=new FiltreFichier();
    private File f=null;

    public Capture(){
        fs.addChoosableFileFilter(filtre);
    }
    
    //Capture plein écran 
    public void fullScreenShot() {
                        
        BufferedImage buf = null; // Notre capture d'écran originale
        BufferedImage bufFinal = null; // Notre capture d'écran redimensionnée
        
        GraphicsEnvironment ge =GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = ge.getDefaultScreenDevice();
        DisplayMode currentDisplayMode = graphicsDevice.getDisplayMode();
        int width=currentDisplayMode.getWidth();
        int height=currentDisplayMode.getHeight();
        // Création de notre capture d'écran
        try {
            buf = new Robot().createScreenCapture(new Rectangle(0,0,width,height));                          
        } 
        catch (AWTException e) {e.printStackTrace();}            
        // Création de la capture finale
        bufFinal = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);             
        // Redimensionnement de la capture originale
        Graphics2D g = (Graphics2D) bufFinal.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(buf, 0, 0,width,height, null);
        g.dispose();
        
        JOptionPane info = new JOptionPane();
        if(fs.showSaveDialog(null)==fs.APPROVE_OPTION){
            f=fs.getSelectedFile();
            if(fs.getFileFilter().accept(f)){            
                try{    
                    ImageIO.write(bufFinal, "png", f);
                } 
                catch (IOException e) {
                    e.printStackTrace();
                    info.showMessageDialog(null, "La sauvegarde de la capture écran a échoué !", 
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }    
            else{
                //Si vous n'avez pas spécifié une extension valide !
                info.showMessageDialog(null, "Erreur d'extension de fichier !\nVotre sauvegarde a échoué !", 
                                                "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    //Capture d'une zone rectangulaire
    public void screenShot(Rectangle screenArea){
                        
        BufferedImage buf = null; // Notre capture d'écran originale
        BufferedImage bufFinal = null; // Notre capture d'écran redimensionnée

        try {
            // Création de notre capture d'écran
            buf = new Robot().createScreenCapture(screenArea);
            
        } 
        catch (AWTException e) {
            e.printStackTrace();
        }
        // Création de la capture finale
        bufFinal = new BufferedImage(screenArea.width,screenArea.height, BufferedImage.TYPE_INT_RGB);              
        // Redimensionnement de la capture originale
        Graphics2D g = (Graphics2D) bufFinal.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);            
        g.drawImage(buf, 0, 0, screenArea.width,screenArea.height, null);           
        g.dispose();
        
        JOptionPane info = new JOptionPane();
        int i=1;
        if(fs.showSaveDialog(null)==fs.APPROVE_OPTION){
            f=fs.getSelectedFile();
            if(f!=null&&fs.getFileFilter().accept(f)){     
            
                try{    
                    ImageIO.write(bufFinal, "png", f);
                } 
                catch (IOException e) {
                    e.printStackTrace();
                    info.showMessageDialog(null, "La sauvegarde de la capture écran a échoué !", 
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                catch(IllegalArgumentException e){
                    info.showMessageDialog(null, "La sauvegarde a échoué !", 
                                                    "Erreur", JOptionPane.ERROR_MESSAGE);    
                }
            }    
            else{
                //Si vous n'avez pas spécifié une extension valide !
                info.showMessageDialog(null, "Erreur d'extension de fichier !\nVotre sauvegarde a échoué !", 
                                                "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     *
     * @return BufferedImage;retourne BufferedImage de la totalité de l'ecran 
     */
    public BufferedImage getScreenShotBuffer() {
                        
        BufferedImage buf = null; 
        Rectangle screenArea= new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        
        buf=new BufferedImage(screenArea.width,screenArea.height, BufferedImage.TYPE_INT_RGB);
        try {
            // Création de notre capture d'écran
            buf = new Robot().createScreenCapture(screenArea);
        } 
        catch (AWTException e) {
            e.printStackTrace();
        }
        return buf;
    }
    
}
