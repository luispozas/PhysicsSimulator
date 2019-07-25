package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class MyMenuBar extends JMenuBar {
	
	private static final long serialVersionUID = -3110699333608334606L;
	JMenu menu, configRun;
	JMenuItem openFile, gravityLaw, closeApp, run, stop;
	MainWindow _mainWindow;
	
	MyMenuBar(MainWindow mw){
		_mainWindow = mw;
		initGUI();
	}
	
	private void initGUI() {
		menu = new JMenu("Menu");
		this.add(menu);
		
		openFile = new JMenuItem("Open file...");
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		
		gravityLaw = new JMenuItem("Add gravity law");
		gravityLaw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		
		configRun = new JMenu("Execute");
		
		closeApp = new JMenuItem("Close");
		closeApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		
		menu.add(openFile);
		menu.add(gravityLaw);
		menu.add(configRun);
		menu.add(closeApp);
		
		run = new JMenuItem("Run");
		run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		
		stop = new JMenuItem("Stop");
		stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		configRun.add(run);
		configRun.add(stop);
		
		openFile.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.openFile();
			}
		});
		
		gravityLaw.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.chooseGravityLaw();
			}
		});
		
		run.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.runAction();
			}
		});
		
		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.stopAction();
			}
		});
		
		closeApp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mainWindow.exitAction();
			}
		});
	}	
	
	public void putEnableMenu(boolean enable) {
		openFile.setEnabled(enable);
		gravityLaw.setEnabled(enable);
		run.setEnabled(enable);
		closeApp.setEnabled(enable);
	}
}
