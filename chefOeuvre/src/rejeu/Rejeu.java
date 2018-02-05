/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rejeu;

import java.io.File;

/**
 *
 * @author mundial
 */
//pour la génération d'un fichier rejeu
public class Rejeu {
    
    private File file;

    public Rejeu(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    
}
