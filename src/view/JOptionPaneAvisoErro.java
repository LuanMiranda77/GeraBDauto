package view;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class JOptionPaneAvisoErro  extends JOptionPane{
	
	public JOptionPaneAvisoErro(String erro) {
		  JOptionPane.showMessageDialog(null, erro,"Aviso de Erro !", JOptionPane.ERROR_MESSAGE);
	}
}
