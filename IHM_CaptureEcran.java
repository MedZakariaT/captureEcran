package screenshooter;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author MedZT
 */
public class IHM_CaptureEcran extends JFrame {
    
    private IHM_CaptureRect cr=null;
    private final static Capture C=new Capture();
    //private Cursor c;
    //éléments graphiques de la fenetre
    private JMenuBar menu=new JMenuBar();
    private JMenu captureEcran=new JMenu("Capture écran");
    private JMenuItem capturePleinEcran=new JMenuItem("Capture plein écran")
            ,captureRect=new JMenuItem("Capture rectangulaire"),quitter=new JMenuItem("Quitter");
    private JButton annuler=new JButton("Annuler");
    
    private JMenu about=new JMenu("Aide");
    private JMenuItem aboutItem=new JMenuItem("À propos");
    private JMenuItem aide=new JMenuItem("Aide");
    
    private JLabel help;
    
    public IHM_CaptureEcran(){
        super();          
    }

    private void initIHM() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ZZCaptureEcran");
        setResizable(false);   
        
        capturePleinEcran.addActionListener(new ActionListener(){
            //à corriger capture écran ne doit pas contenir l'application
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p=getLocation();
                setLocation(10000,10000);
                C.fullScreenShot();
                setLocation(p.x,p.y);
            }
        });      
        capturePleinEcran.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,java.awt.event.KeyEvent.CTRL_DOWN_MASK));
        captureEcran.add(capturePleinEcran);
        
        captureRect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {     
                Point p=getLocation();
                setLocation(10000,10000);
                Image image=C.getScreenShotBuffer();              
                cr=new IHM_CaptureRect(image);
                setLocation(p.x,p.y);
                toFront();
            }
        });
        captureRect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, java.awt.event.KeyEvent.CTRL_DOWN_MASK));
        captureEcran.add(captureRect);       
       
        quitter.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,java.awt.event.KeyEvent.CTRL_DOWN_MASK ));
        captureEcran.addSeparator();
        captureEcran.add(quitter);
        captureEcran.setMnemonic('C');
        captureEcran.setIcon(new ImageIcon(getClass().getResource("capture.png")));
        
        annuler.setIcon(new ImageIcon(getClass().getResource("delete_edit.png")));
        annuler.setToolTipText("Annuler la capture");
        annuler.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(cr!=null)
                   fermerCaptureRect();
            }
        });
        help=new JLabel("Sélectionnez un type de capture dans le menu capture écran"
                ,new ImageIcon(getClass().getResource("help.png")),SwingConstants.RIGHT);
        
        about.setIcon(new ImageIcon(getClass().getResource("aide.jpg")));
        aide.setIcon(new ImageIcon(getClass().getResource("help.png")));
        aide.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane jop = new JOptionPane();
                ImageIcon img = new ImageIcon(getClass().getResource("help.png"));
                String mess="Choisir un type de capture écran :\n";
                mess+="1.Une capture pleine écran.\n";
                mess+="2.Une capture d'une zone rectangulaire à spécifier par l'utilisateur.";  
                jop.showMessageDialog(null, mess, "Aide",JOptionPane.INFORMATION_MESSAGE, img);     
            }
        });
        
        aboutItem.setIcon(new ImageIcon(getClass().getResource("info.jpg")));
        aboutItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane jop = new JOptionPane();
                ImageIcon img = new ImageIcon(getClass().getResource("SidiBou.jpg"));
                String mess="Application java multiplatforme pour la création de capture écran.\n";
                mess+="Copy Right (C) 2017 Mohamed Zakaria Tebbessi.\n";
                mess+="Je t'aime ZZ.";  
                jop.showMessageDialog(null, mess, "À propos",JOptionPane.INFORMATION_MESSAGE, img);           
            }
        });
        
        menu.add(captureEcran);
        menu.add(annuler);       
        about.add(aide);
        about.addSeparator();
        about.add(aboutItem);     
        menu.add(about);
        //pour l'ajout du barre des menus
        setJMenuBar(menu);
        getContentPane().add(help, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void fermerCaptureRect(){
        cr.dispose();
    }
   
    public static void main(String[]args){
        final IHM_CaptureEcran cE=new IHM_CaptureEcran();
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                cE.initIHM();
            }
        });
        
    }
}
