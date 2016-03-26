package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

public class ClockPane extends JPanel {

	private static final long serialVersionUID = -4505593504609263452L;
	private JLabel clock;

    public ClockPane() {
        clock = new JLabel();
        setBackground(new Color(230, 230, 250));
        clock.setHorizontalAlignment(JLabel.CENTER);
        clock.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 14f));
        tickTock();
        add(clock);

        Timer timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tickTock();
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
    }

    public void tickTock() {
        clock.setText(DateFormat.getDateTimeInstance().format(new Date()));
    }
}
