package screenshooter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.Point;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.awt.event.MouseMotionListener;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.w3c.dom.css.Rect;

public class PanneauDessin extends JPanel{
    
    //Variables utilisées pour le dessin de la forme rectangulaire sur l'écran
    private ArrayList<Point> listePoints=new ArrayList();
    //zone de la capture ecran selectionne 
    private Rectangle zone;
    //couleur de tracage de la zone 
    private final Color couleurPointeur=Color.RED;   
    //instance de la classe capture pour capturer la zone défénie 
    private final static Capture C=new Capture();
    //Image a partir de laquelle on va faire la capture rectangulaire
    private Image image=null;
    //pour pouvoir fermer la fenetre parente aprés sauvegarde du capture rectangulaire 
    private JWindow parent=null;
    //Image du curseur modifié 
    private static Image imgCurseur=null;
    
    public PanneauDessin(JWindow w,Image image) {
        super();
        parent=w;
        this.image=image;
        modifCurseur();
        ajoutEcouteurSouris();      
    }
    
    public void modifCurseur(){           
        //recupere le Toolkit
        Toolkit tk = Toolkit.getDefaultToolkit();
        //sur ce dernier lire le fichier avec "getClass().getRessource" pour
        //pouvoir l'ajouter a un .jar
        imgCurseur=tk.getImage(getClass().getResource("curseur.png"));
        //modifi le curseur avec la nouvelle image,en le posissionant grace hotSpot
        //et en lui donnant le nom "X"
        Cursor c=tk.createCustomCursor(imgCurseur,new Point(0,0),"Rectangle");
        //puis on l'associe au Panel
        this.setCursor(c);
    }
    
    public void setImage(Image image) {
        this.image = image;
        repaint();
    }
     
    public PanneauDessin(String path) throws IOException {
        super();
        setImage(path);
    }
      
    public void setImage(String path) throws IOException {
        try {
            this.image = ImageIO.read(new File(path));
            repaint();
        } 
        catch (IOException e) {
            throw new IOException(path+" introuvable", e);
        }
    }

    public Image getImg() {
        return image;
    }
    
    public final void ajoutEcouteurSouris(){
        this.addMouseListener(new MouseAdapter(){        
                @Override
                public void mousePressed(MouseEvent e){}              
                @Override
                public void mouseReleased(MouseEvent e){  
                        if(zone!=null&&(zone.getWidth()*zone.getHeight()!=0)){                          
                            C.screenShot(zone);
                            parent.dispose();
                        }
                        else{
                            listePoints.clear();
                            repaint();
                        }
                }
            });
            this.addMouseMotionListener(new MouseMotionListener(){
                @Override
                public void mouseDragged(MouseEvent e) {
                    listePoints.add(new Point(e.getX(),e.getY()));
                    repaint();
                }
                @Override
                public void mouseMoved(MouseEvent e) {}
            });
    }
    
    @Override
    public void paintComponent(Graphics g){
        if(image!=null){
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            g.setColor(couleurPointeur);
            
            if(listePoints.size()>1){    
                int x = (int) listePoints.get(0).getX(), y = (int) listePoints.get(0).getY(), xn =
                    (int) listePoints.get(listePoints.size() - 1).getX(), yn =
                    (int) listePoints.get(listePoints.size() - 1).getY();  
                              
                if(xn-x>0&&yn-y>0){     
                    zone =  new Rectangle(x, y, xn - x, yn - y);
                    g.drawRect(x, y, xn - x, yn - y);                      
                }
                else{
                    if(xn-x<0&&yn-y>0) {             
                        zone=new Rectangle(xn, y, x-xn, yn-y);
                        g.drawRect(xn, y, x-xn, yn-y);
                    }
                    else{
                        if(xn-x>0&&yn-y<0){ 
                           zone=new Rectangle(x, yn, xn-x, y-yn); 
                           g.drawRect(x, yn, xn-x, y-yn);
                        }
                        else{
                            zone=new Rectangle(xn, yn, x-xn, y-yn);
                            g.drawRect(xn, yn, x-xn, y-yn);
                        }
                    }
                }
                g.dispose();
            }
        }
    }
 
}
