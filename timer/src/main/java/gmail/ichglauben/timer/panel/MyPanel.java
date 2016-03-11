package gmail.ichglauben.timer.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gmail.ichglauben.basicgui.core.abstracts.panel.CustomPanel;

import gmail.ichglauben.timer.frame.MyFrame;
import gmail.ichglauben.timer.utils.Constants;
import gmail.ichglauben.timer.utils.StringUtils;

public class MyPanel extends CustomPanel {
	public final static String title = StringUtils.cfc("timer");
	private MyFrame frame;
	private JTextField jtfHour = null;
	private JTextField jtfMinute = null;
	private JTextField jtfSecond = null;
	private JButton button_main, button_reset;
	private JPanel panel_button_main = new JPanel();
	private JPanel panel_button_reset = new JPanel();
	private JPanel panel_hour = new JPanel();
	private JPanel panel_minute = new JPanel();
	private JPanel panel_second = new JPanel();
	private int second_counter = 0;
	private Timer timer = null;
	
	public MyPanel() {
		super();
		initGui();
	}
	
	void startTimer() {
		timer = new Timer();
		timer.schedule(new Incrementor(),0, TimeUnit.SECONDS.toSeconds(1000));
	}
	
	void stopTimer() {
		timer.cancel();
	}
	
	void resetTimer() {
		second_counter = 0;
		jtfSecond.setText("0");
		jtfMinute.setText("0");
		jtfHour.setText("0");
	}
	
	void incrementCounter() {
		second_counter += 1;
		if (second_counter == 59) {
			second_counter = 0;
		} 
		updateSecond();
	}
	
	void updateSecond() {
		int s = Integer.parseInt(jtfSecond.getText());
		if (s<=58) {
			s += 1;
		} else {
			s = 0;
			updateMinute();
		}
		jtfSecond.setText(String.valueOf(s));
	}
	
	void updateMinute() {
		int m = Integer.parseInt(jtfMinute.getText());
		if (m<=58) {
			m += 1;
		} else {
			m = 0;
			updateHour();
		}
		jtfMinute.setText(String.valueOf(m));
	}
	
	void updateHour() {
		int h = Integer.parseInt(jtfHour.getText());
		h += 1;
		jtfHour.setText(String.valueOf(h));
	}
	
	private void initGui() {
		Font font = new Font("TimesRoman", Font.BOLD, 18);
		jtfHour = new JTextField();
		jtfMinute = new JTextField();
		jtfSecond = new JTextField();		
		jtfHour.setPreferredSize(new Dimension(62,30));
		jtfMinute.setPreferredSize(jtfHour.getPreferredSize());
		jtfSecond.setPreferredSize(jtfHour.getPreferredSize());		
		jtfHour.setFont(font);
		jtfMinute.setFont(font);
		jtfSecond.setFont(font);		
		jtfHour.setHorizontalAlignment(JTextField.CENTER);
		jtfMinute.setHorizontalAlignment(JTextField.CENTER);
		jtfSecond.setHorizontalAlignment(JTextField.CENTER);		
		jtfHour.setBackground(Color.white);
		jtfHour.setForeground(Color.black);		
		jtfMinute.setBackground(Color.white);
		jtfMinute.setForeground(Color.black);		
		jtfSecond.setBackground(Color.white);
		jtfSecond.setForeground(Color.black);		
		jtfHour.setEditable(false);
		jtfMinute.setEditable(false);
		jtfSecond.setEditable(false);		
		jtfHour.setText("0");
		jtfMinute.setText("0");
		jtfSecond.setText(String.valueOf(second_counter));			
		// Northern panel
		JPanel panel_north = new JPanel();
		JLabel label_hour = new JLabel(StringUtils.cfc("hours"));
		label_hour.setHorizontalAlignment(JLabel.CENTER);
		JLabel label_minute = new JLabel(StringUtils.cfc("minutes"));
		label_minute.setHorizontalAlignment(JLabel.CENTER);
		JLabel label_second = new JLabel(StringUtils.cfc("seconds"));
		label_second.setHorizontalAlignment(JLabel.CENTER);		
		panel_hour.add(jtfHour);
		panel_minute.add(jtfMinute);
		panel_second.add(jtfSecond);		
		JPanel panel_hours = new JPanel(new BorderLayout());
		JPanel panel_minutes = new JPanel(new BorderLayout());
		JPanel panel_seconds = new JPanel(new BorderLayout());
		panel_hours.add(label_hour, BorderLayout.NORTH);
		panel_hours.add(panel_hour, BorderLayout.CENTER);		
		panel_minutes.add(label_minute, BorderLayout.NORTH);
		panel_minutes.add(panel_minute, BorderLayout.CENTER);		
		panel_seconds.add(label_second, BorderLayout.NORTH);
		panel_seconds.add(panel_second, BorderLayout.CENTER);		
		panel_north.add(panel_hours);
		panel_north.add(panel_minutes);
		panel_north.add(panel_seconds);
		add(panel_north, BorderLayout.NORTH);		
		// Central panel
		JPanel panel_center = new JPanel();
		button_main = new JButton(StringUtils.cfc(Constants.START));
		button_main.setActionCommand(Constants.ACTION_START);
		button_main.addActionListener(new ButtonHandler());
		button_reset = new JButton(StringUtils.cfc(Constants.RESET));
		button_reset.setActionCommand(Constants.ACTION_RESET);
		button_reset.addActionListener(new ButtonHandler());
		button_reset.setEnabled(false);
		panel_button_main.add(button_main);
		panel_button_reset.add(button_reset);
		panel_center.add(panel_button_main);
		panel_center.add(panel_button_reset);
		add(panel_center, BorderLayout.CENTER);				
		updateUI();
	}
	
	private void exitProg() {
		frame.exitProg();
	}
	
	public void setFrame(MyFrame mf) {
		this.frame = mf;
	}
	
	private class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			JButton button = (JButton) ae.getSource();
			buttonHandler(button);
		}
		
		void buttonHandler(final JButton btn) {
			Runnable runner = new Runnable() {
				@Override
				public void run() {
					switch (btn.getActionCommand()) {
					case Constants.ACTION_START:
						startTimer();
						button_reset.setEnabled(false);
						btn.setText(Constants.STOP);
						btn.setActionCommand(Constants.ACTION_STOP);
						break;
						
					case Constants.ACTION_STOP:
						stopTimer();
						button_reset.setEnabled(true);
						btn.setText(Constants.START);
						btn.setActionCommand(Constants.ACTION_START);
						break;
						
					case Constants.ACTION_RESET:
						resetTimer();
						break;
					}
				}
			};
			EventQueue.invokeLater(runner);
		}
	}
	
	private class Incrementor extends TimerTask {
		@Override
		public void run() {
			 incrementCounter();
		}		
	}
}
