package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = -2902854954016008171L;
	private MyMenuBar _menuBar;
	private BodiesTable _bodiesTable;
	private ControlPanel _ctrlPanel;
	private Viewer _viewer;
	private StatusBar _statusBar;
	private JFileChooser fc;
	private boolean _stopped;
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		this.setAutoRequestFocus(true);
		fc = new JFileChooser();
		_stopped = true;
		_ctrl = ctrl;
		initGUI();
	}
	private void initGUI() {
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		_ctrlPanel = new ControlPanel(_ctrl, this);
		mainPanel.add(_ctrlPanel, BorderLayout.PAGE_START);
		
		_menuBar = new MyMenuBar(this);
		this.setJMenuBar(_menuBar);
		
		_statusBar = new StatusBar(_ctrl);
		mainPanel.add(_statusBar, BorderLayout.PAGE_END);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		_bodiesTable = new BodiesTable(_ctrl, new BodiesTableModel(_ctrl), "bodies");
		centerPanel.add(_bodiesTable);
		
		_viewer = new Viewer(_ctrl);
		centerPanel.add(_viewer);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		this.setMinimumSize(new Dimension(750, 500)); //Minimo para evitar solapamiento
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		
		addWindowListener(new WindowAdapter() { //Para cerrar el Frame con el aspa.
			public void windowClosing(WindowEvent e) {
				exitAction();
			}
		});
	}
	
	public void openFile() {
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			_ctrl.setFile(f);
			try(InputStream in = new FileInputStream(f)){
				_ctrl.reset();
				_ctrl.loadBodies(in);
			} catch (IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, "File selected doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Reading failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void chooseGravityLaw() {
		List<JSONObject> list = _ctrl.getGravityLawsFactory().getInfo();
		String lawsOption[] = createArrayOfElementJSONList(list, "desc", "type");
		String sel = (String) JOptionPane.showInputDialog(this, "Select gravity Law to be used.", "Gravity Laws Selector",
				JOptionPane.INFORMATION_MESSAGE, null, lawsOption, lawsOption[0]);
		
		if(sel != null) {
			for(int i = 0; i < lawsOption.length; ++i) {
				if(sel.equals(lawsOption[i])) {
					_ctrl.setGravityLaws(list.get(i));
					return;
				}
			}
		}	
	}
	
	private String[] createArrayOfElementJSONList(List<JSONObject> l, String v1, String v2) {
		int i = 0;
		String str[] = new String[l.size()];
		for(JSONObject o : l) {
			str[i] = o.getString(v1) + " (" + o.getString(v2) + ") ";
			i++;
		}
		return str;
	}
	
	public void runAction() {
		_ctrlPanel.putEnableButtons(false);
		_menuBar.putEnableMenu(false);
		_stopped = false;
		try {
			_ctrl.setDeltaTime(_ctrlPanel.getDTimeValue());
			run_sim(_ctrlPanel.getStepsValue());
		}
		catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.INFORMATION_MESSAGE);
			_ctrlPanel.putEnableButtons(true);
			_menuBar.putEnableMenu(true);
		}
	}
	
	public void resetAction() {
		//El controller tiene un File, asi cada vez que se cargue un fichero desde la "gui" poder tener acceso.
		try(InputStream in = new FileInputStream(_ctrl.getFile())){
			_ctrl.reset();
			_ctrl.loadBodies(in);
		}
		catch(Exception nullpointerException) {
			JOptionPane.showMessageDialog(this, "There aren't bodies to reset.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void stopAction() {
		_stopped = true;
	}
	
	public void exitAction(){
		Object[] options = {"Yes", "Cancel"};
	   	int sel = JOptionPane.showOptionDialog(this, "Do you really want to exit?", "Exit", JOptionPane.YES_NO_OPTION,
	   			JOptionPane.WARNING_MESSAGE, null, options, null);
	   	if(sel == 0) System.exit(0);
	}
	
	
	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				_ctrlPanel.putEnableButtons(true);
				_menuBar.putEnableMenu(true);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
		} else {
			_stopped = true;
			_ctrlPanel.putEnableButtons(true);
			_menuBar.putEnableMenu(true);
		}
	}
	
}
