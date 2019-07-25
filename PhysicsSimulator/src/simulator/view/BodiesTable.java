package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	
	private static final long serialVersionUID = -3561504278904781532L;
	
	BodiesTable(Controller ctrl, AbstractTableModel a, String title) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), 
				title, TitledBorder.LEFT, TitledBorder.TOP));
		
		this.setPreferredSize(new Dimension(1000, 200));
		
		JTable table = new JTable(a);
		table.setShowHorizontalLines(true);
		table.setGridColor(Color.LIGHT_GRAY);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
	}
}
