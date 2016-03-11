package gmail.ichglauben.timer.frame;

import com.gmail.ichglauben.basicgui.core.abstracts.frame.CustomFrame;

import gmail.ichglauben.timer.panel.MyPanel;

public class MyFrame extends CustomFrame {
	public MyFrame() {
		super();
	}
	
	public MyFrame(MyPanel panel) {
		super(panel);
		panel.setFrame(this);
		setTitle(panel.title);
	}
}
