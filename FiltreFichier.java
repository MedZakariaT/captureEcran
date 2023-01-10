package screenshooter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltreFichier extends FileFilter {
    
    private String ext1 = ".jpg";
    private String ext2 = ".png";
    private String description = "Image de type JPG ou PNG";

    public FiltreFichier() {
        super();    
    }
    
    @Override
    public boolean accept(File file){
        return (file.isFile()&&(file.getName().endsWith(this.ext1)||file.getName().endsWith(ext2)));
    }
      
    @Override
    public String getDescription(){
        return ext1 +" ou "+ ext2 + " - " + this.description;
    }
}
