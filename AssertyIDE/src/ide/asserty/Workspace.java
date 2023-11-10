package ide.asserty;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JFrame;

public class Workspace extends JFrame{
	
	static Workspace stationIde;
	
	//Elements for workspace
	TextArea editor;
	
	public Workspace() {
		this.setSize(960, 540);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		//UI Pre-set
		editor = new TextArea();
		this.add(editor);
		editor.setFont(new Font(Font.MONOSPACED, Font.TRUETYPE_FONT, 15));
		editor.setSize(new Dimension(850, 300));
		editor.setLocation(32, 32);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		stationIde = new Workspace();
	}

}
