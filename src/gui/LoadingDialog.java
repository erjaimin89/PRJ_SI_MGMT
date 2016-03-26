package gui;

import javax.swing.JDialog;

public class LoadingDialog extends JDialog {
	private static final long serialVersionUID = 4083749001275659164L;

	public LoadingDialog(String message, boolean closable) {
		setTitle(message);
		setSize(500, 0);
		if(closable)
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		else
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void hideDialog(){
		setVisible(false);
		dispose();
	}
}
