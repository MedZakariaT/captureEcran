package screenshooter;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JWindow;

public class IHM_CaptureRect extends JWindow {
    @SuppressWarnings("compatibility:7507275738698490431")
    private static final long serialVersionUID = 1446907825584656851L;

    private PanneauDessin pd;
    //taille du JWindow 
    private final int largeur=Toolkit.getDefaultToolkit().getScreenSize().width
            ,hauteur=Toolkit.getDefaultToolkit().getScreenSize().height;
    
    
    public IHM_CaptureRect(Image image){
        super();
        pd=new PanneauDessin(this,image);
        initIHM();
    }

    private void initIHM() {
        setSize(largeur,hauteur);
        //ou bien choisir automatiquement la taille convenable pour afficher tous les composants : pack()
        //pour centrer la fenetre, il faut l'appeler aprés avoir fixer la taille de la fenetre ou apres pack()
        setLocationRelativeTo(null);
        getContentPane().add(pd,BorderLayout.CENTER); 
        setFocusable(true);
        requestFocus();       
        setVisible(true);
    }   
}
