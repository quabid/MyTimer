package gmail.ichglauben.timer;

import javax.swing.SwingUtilities;

import gmail.ichglauben.timer.frame.MyFrame;
import gmail.ichglauben.timer.panel.MyPanel;

public class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MyFrame(new MyPanel());
			}
		});
	}
}
