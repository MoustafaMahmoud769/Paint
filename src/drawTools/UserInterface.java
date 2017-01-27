package drawTools;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBException;

import files.Filetools;
import files.XmlEncoder;

public class UserInterface extends JFrame {

	// public static LoadClasses loadClass = new LoadClasses();
	public static boolean loaded = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface frame = new UserInterface();
					frame.setTitle("Paint");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JButton btn[] = new JButton[12];
	private JButton colorBtn[] = new JButton[2];
	private JPanel contentPane;
	private JFileChooser fileChooser, imageChooser;
	private JMenu fileMenu, modesMenu, StrokeMenu;
	private FileFilter filter1, filter2;
	private PaintGrid grid;
	private JMenuBar menu;
	private JMenuItem menu1[] = new JMenuItem[6];
	private JMenuItem menu2[] = new JMenuItem[3];
	private JMenuItem menu3[] = new JMenuItem[3];

	private JPopupMenu popup;

	public UserInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1000, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		contentPane.setLayout(null);
		contentPane.setFocusable(true);
		contentPane.requestFocusInWindow();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		menuBar();
		buttonsBar();
		drawColors();
		intializeGrid();
		setFilters();
		buttonListener();
		colorBtnListener();
		fileListener();
		modesListener();
		strokeListener();
		keyboardListener();
	}

	private void buttonListener() {
		for (int i = 0; i < 6; i++) {
			final int j = i + 1;
			btn[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					grid.setDrawMode(j);
				}
			});
		}
		editColorButton();
		btn[7].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteShape();
			}
		});
		btn[8].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});
		btn[9].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				redo();
			}
		});
		btn[10].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btn[11].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
	}

	private void buttonsBar() {
		String names1[] = { "Line", "Ellipse", "Circle", "Rectangle", "Square", "Triangle" };
		String names2[] = { "Color", "Delete", "Undo", "Redo", "Save", "Load" };
		for (int i = 0; i < btn.length; i++) {
			ImageIcon image = null;
			if (i < 6) {
				try {
					image = new ImageIcon(ImageIO.read(resources.resourceLoader("images/" + names1[i] + ".png")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				btn[i] = new JButton(image);
				btn[i].setToolTipText(names1[i]);
			} else {
				if (names2[i - 6] == "Save" || names2[i - 6] == "Load")
					btn[i] = new JButton(names2[i - 6]);
				else {
					try {
						image = new ImageIcon(
								ImageIO.read(resources.resourceLoader("images/" + names2[i - 6] + ".png")));
					} catch (IOException e) {
						e.printStackTrace();
					}
					btn[i] = new JButton(image);
				}
				btn[i].setToolTipText(names2[i - 6]);
			}
			btn[i].setBounds(0, 30 + (i * 55), 80, 55);
			btn[i].setBackground(Color.WHITE);
			btn[i].setFocusable(false);
			contentPane.add(btn[i]);
		}
	}

	private void colorBtnListener() {
		colorBtn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c;
				c = JColorChooser.showDialog(((Component) e.getSource()).getParent(), "Outline Color", Color.black);
				grid.setOutColor(c);
			}
		});
		colorBtn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c;
				c = JColorChooser.showDialog(((Component) e.getSource()).getParent(), "Fill Color", Color.white);
				grid.setFillColor(c);
			}
		});

	}

	private void deleteShape() {
		try {
			grid.deleteShape();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Please Select a shape");
		}
	}

	private void drawColors() {
		ImageIcon image = new ImageIcon();
		try {
			image = new ImageIcon(ImageIO.read(resources.resourceLoader("images/Outline Color.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		colorBtn[0] = new JButton(image);
		colorBtn[0].setBounds(150, 660, 300, 40);
		colorBtn[0].setToolTipText("Outline Color");
		colorBtn[0].setBackground(Color.WHITE);
		colorBtn[0].setFocusable(false);
		contentPane.add(colorBtn[0]);
		try {
			image = new ImageIcon(ImageIO.read(resources.resourceLoader("images/Fill Color.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		colorBtn[1] = new JButton(image);
		colorBtn[1].setBounds(550, 660, 300, 40);
		colorBtn[1].setToolTipText("Fill Color");
		colorBtn[1].setBackground(Color.WHITE);
		colorBtn[1].setFocusable(false);
		contentPane.add(colorBtn[1]);
	}

	private void editColorButton() {
		popup = new JPopupMenu();
		JMenuItem outlineColor = new JMenuItem("Edit Outline Color");
		popup.add(outlineColor);
		JMenuItem FColor = new JMenuItem("Edit Fill Color");
		popup.add(FColor);
		btn[6].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				popup.show(btn[6], btn[6].getWidth(), btn[6].getHeight() / 80);
			}
		});
		outlineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Color c = JColorChooser.showDialog(((Component) e.getSource()).getParent(),
							"Choose the new Outline Color", Color.black);
					grid.changeStrokeColor(c);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please Select a shape");
				}
			}
		});
		FColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Color c = JColorChooser.showDialog(((Component) e.getSource()).getParent(),
							"Choose the new Fill Color", Color.white);
					grid.changeFillColor(c);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please Select a shape");
				}
			}
		});
	}

	private void fileListener() {
		menu1[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(grid);
				intializeGrid();
			}
		});
		menu1[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		menu1[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveImage();
			}
		});
		menu1[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		menu1[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(null, "Do you want to save?", "", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION)
					save();
				System.exit(0);
			}
		});
	}

	private void intializeGrid() {
		grid = new PaintGrid();
		grid.setBounds(80, 30, 950, 630);
		grid.setBackground(Color.WHITE);
		grid.setLayout(null);
		grid.setFillColor(Color.WHITE);
		grid.setOutColor(Color.BLACK);
		grid.setStroke(2);
		contentPane.add(grid);
		grid.repaint();
	}

	private void keyboardListener() {
		contentPane.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					undo();
				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					redo();
				if (e.getKeyCode() == KeyEvent.VK_M) {
					try {
						grid.setResizeMode(false);
						grid.setMoveMode(true);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Please Select a shape");
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					try {
						grid.setResizeMode(false);
						grid.setMoveMode(true);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Please Select a shape");
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_S)
					save();
				if (e.getKeyCode() == KeyEvent.VK_L)
					load();
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
					deleteShape();
				else
					Toolkit.getDefaultToolkit().beep();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	private void load() {
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(filter1);
		int returnVal = fileChooser.showOpenDialog(contentPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			if (file.exists()) {
				if (Filetools.getExtension(file) == null)
					JOptionPane.showMessageDialog(null, "File Extension Invalid ! Please enter a valid File Extension");
				else if (Filetools.getExtension(file).equals("xml")) {
					XmlEncoder reader = new XmlEncoder(grid);
					try {
						grid.setData(reader.load(filePath));
					} catch (JAXBException e) {
						JOptionPane.showMessageDialog(null, "Error");
					}
					grid.repaint();
				} else
					JOptionPane.showMessageDialog(null, "File Extension Invalid ! Please enter a valid File Extension");
			}
		}
	}

	private void menuBar() {
		String Menu1[] = { "New", "Save", "Save Jpg", "Load", "Exit" };
		String Menu2[] = { "Draw", "Move", "Resize" };
		String Menu3[] = { "Thin", "Medium", "Thick" };
		menu = new JMenuBar();
		menu.setBounds(0, 0, 1000, 30);
		fileMenu = new JMenu("File");
		for (int i = 0; i < 5; i++) {
			menu1[i] = new JMenuItem(Menu1[i]);
			menu1[i].setFocusable(false);
			fileMenu.add(menu1[i]);
		}
		menu.add(fileMenu);
		modesMenu = new JMenu("Modes");
		for (int i = 0; i < 3; i++) {
			menu2[i] = new JMenuItem(Menu2[i]);
			menu2[i].setFocusable(false);
			modesMenu.add(menu2[i]);
		}
		menu.add(modesMenu);
		StrokeMenu = new JMenu("Stroke");
		for (int i = 0; i < 3; i++) {
			menu3[i] = new JMenuItem(Menu3[i]);
			menu3[i].setFocusable(false);
			StrokeMenu.add(menu3[i]);
		}
		menu.add(StrokeMenu);
		contentPane.add(menu);
	}

	private void modesListener() {
		final boolean states[] = { false, false, true };
		for (int i = 0; i < 3; i++) {
			final int j = i;
			menu2[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						grid.setResizeMode(states[j]);
						grid.setMoveMode(states[(j + 1) % 3]);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Please Select a shape");
					}
				}
			});
		}
	}

	private void redo() {
		try {
			grid.redo();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Can't Redo, No history");
		}
	}

	private void save() {
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(filter1);
		int returnVal = fileChooser.showSaveDialog(contentPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			if (Filetools.getExtension(file) == null)
				JOptionPane.showMessageDialog(null, "File Extension Invalid !,Please enter a valid File Extension");
			else if (Filetools.getExtension(file).equalsIgnoreCase("xml")) {
				try {
					file.getParentFile().mkdirs();
					file.createNewFile();
					XmlEncoder writer = new XmlEncoder(grid);
					writer.save(path);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else
				JOptionPane.showMessageDialog(null, "File Extension Invalid ! Please enter a valid File Extension");
		}
	}

	private void saveImage() {
		imageChooser = new JFileChooser();
		imageChooser.addChoosableFileFilter(filter2);
		String filePath = null;
		int returnVal = imageChooser.showSaveDialog(contentPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filePath = imageChooser.getSelectedFile().getAbsolutePath();
			BufferedImage image = grid.getImg();
			File file = new File(filePath);
			if (Filetools.getExtension(file) == null || !Filetools.getExtension(file).equalsIgnoreCase("jpg"))
				JOptionPane.showMessageDialog(null, "File Extension Invalid !,Please enter a valid File Extension");

			try {
				ImageIO.write(image, "JPEG", file);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Error");
			}
		}
	}

	private void setFilters() {
		filter1 = new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.isDirectory())
					return true;
				String extension = Filetools.getExtension(file);
				if (extension != null)
					return (extension.equals("xml") || extension.equals("json"));
				else
					return false;
			}

			@Override
			public String getDescription() {
				return "XML & Json files";
			}
		};
		filter2 = new FileFilter() {

			@Override
			public boolean accept(File file) {

				if (file.isDirectory()) {
					return true;
				}

				String extension = Filetools.getExtension(file);

				if (extension != null) {
					if (extension.equals("jar")) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

			@Override
			public String getDescription() {
				return "jar files";
			}
		};
		filter2 = new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.isDirectory())
					return true;
				String extension = Filetools.getExtension(file);
				if (extension != null)
					return extension.equals("jpg");
				else
					return false;
			}

			@Override
			public String getDescription() {
				return "Jpg Images";
			}
		};
	}

	private void strokeListener() {
		for (int i = 0; i < 3; i++) {
			final int j = i + 1;
			menu3[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					grid.setStroke(j * 2);
				}
			});
		}
	}

	private void undo() {
		try {
			grid.undo();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Can't Undo, No history");
		}
	}
}
