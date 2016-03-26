package gui;

import hibernate.UserEntity;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import main.UserType;
import contoller.ManageUsers;

public class AddPartyDialog extends JDialog {
	private static final long serialVersionUID = -40660472380365712L;

	private static ManageUsers manageUsers = new ManageUsers();
	private DefaultTableModel tableModel;

	private JTextField partynameField;

	public AddPartyDialog(Frame parent) {

		super(parent);

		createLayout();

		setTitle("Add New Party");
		setSize(400, 400);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);

	}

	private void createLayout() {

		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -4912504997913867904L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableModel.addColumn("Party");

		Container panel = getContentPane();

		panel.setLayout(new GridBagLayout());

		JLabel lblUserName = new JLabel("Party Name :");
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.insets = new Insets(5, 5, 10, 0);
		gbc_lblUserName.anchor = GridBagConstraints.EAST;
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 0;
		panel.add(lblUserName, gbc_lblUserName);

		partynameField = new JTextField();
		partynameField.setFont(new Font("Tahoma", Font.BOLD, 12));
		partynameField.setColumns(15);
		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.insets = new Insets(5, 5, 10, 0);
		gbc_userNameField.anchor = GridBagConstraints.WEST;
		gbc_userNameField.gridx = 1;
		gbc_userNameField.gridy = 0;
		panel.add(partynameField, gbc_userNameField);

		JButton add = new JButton("Add");
		add.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_add = new GridBagConstraints();
		gbc_add.insets = new Insets(5, 5, 10, 0);
		gbc_add.anchor = GridBagConstraints.WEST;
		gbc_add.gridx = 2;
		gbc_add.gridy = 0;
		panel.add(add, gbc_add);

		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String user = partynameField.getText().trim();
				if (user.length() != 0) {
					if (!manageUsers.isUserExists(user)) {
						boolean addUser = manageUsers.addUser(new UserEntity(
								user, System.currentTimeMillis(), UserType.PARTY.ordinal()));
						if (addUser) {
							String message = "New Party added successfully !";
							JOptionPane.showMessageDialog(new JFrame(),
									message, "Dialog",
									JOptionPane.INFORMATION_MESSAGE);
							loadUsers();
							partynameField.setText("");
						} else {
							String message = "Error while adding a new party !";
							JOptionPane.showMessageDialog(new JFrame(),
									message, "Dialog",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String message = "Party with same name already exists !";
						JOptionPane.showMessageDialog(new JFrame(), message,
								"Dialog", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					String message = "The Party Name Field must be filled in !";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);
					partynameField.grabFocus();
				}
			}
		});

		JTable table = new JTable(tableModel);
		
		try {
			table.setAutoCreateRowSorter(false);
			table.setTableHeader(new JTableHeader(table.getColumnModel()) {
				private static final long serialVersionUID = 2318388492515069297L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table.setRowHeight(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(180, 200));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridwidth = 3;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		panel.add(scroll, gbc_table);
		
		loadUsers();

		JButton close = new JButton("Close");
		add.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_close = new GridBagConstraints();
		gbc_close.insets = new Insets(15, 0, 5, 0);
		gbc_close.anchor = GridBagConstraints.CENTER;
		gbc_close.gridx = 1;
		gbc_close.gridy = 2;
		panel.add(close, gbc_close);


		close.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				hideDialog();
			}
		});
	}

	private void loadUsers() {
		tableModel.setNumRows(0);
		List<UserEntity> list = manageUsers.listUsers(UserType.PARTY.ordinal());
		for (Iterator<UserEntity> iterator = list.iterator(); iterator.hasNext();) {
			UserEntity user = iterator.next();
			tableModel.addRow(new Object[] { user.getUserName() });
		}

	}
	
	private void hideDialog(){
		setVisible(false);
		dispose();
	}
}
