/* Distributed Whiteboard User Interface - Pupil Implementation
 * KRISHNA GADIA
 * MRINALINI VASANTHI
 * COMP 512
 * SPRING 2016
 */
import java.net.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.lang.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.math.*;

public class UIPupil {
	static Graphics g;
	static Color drawColor = Color.black; // default starting color is BLACK
	static int prevX, prevY, x, y;
	static int brushsize;
	static boolean isActive = false;
	static boolean token = false;
	static int mode;
	static int portNo = 6789;
	static String record = "!";
	static int rptr = 1;
	static String hostName = "zeno";
	static JButton red_button, blue_button, yellow_button, black_button,
			white_button, green_button, pink_button, cyan_button, choose_color,
			fill, tiny, big, eraser_button, textbox, clear, freehand,
			line_button, rectangle_button, circle_button, triangle_button,
			square_button, token_button;
	static Socket echoSocket;
	static PrintWriter outToServer;
	static BufferedReader inFromServer;
	static BufferedReader stdIn;

	public static void main(String[] args) {

		try {
			echoSocket = new Socket(hostName, portNo);

			outToServer = new PrintWriter(echoSocket.getOutputStream(), true);
			inFromServer = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));

			// Main JFrame
			JFrame frame = new JFrame("DISTRIBUTED WHITEBOARD");
			// Canvas Panel
			JPanel panel = new JPanel();
			int x1 = 20;
			int x2 = 20;
			int x3 = 100;
			int x4 = 100;
			// Another JPanel for holding all the tools on the side
			JPanel sidepanel = new JPanel();

			// Red Color
			red_button = new JButton("");
			red_button.setForeground(Color.BLACK);
			red_button.setBackground(Color.RED);
			red_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(red_button);
			// Blue Color
			blue_button = new JButton("");
			blue_button.setForeground(Color.BLUE);
			blue_button.setBackground(Color.BLUE);
			blue_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(blue_button);
			// Yellow Color
			yellow_button = new JButton("");
			yellow_button.setForeground(Color.YELLOW);
			yellow_button.setBackground(Color.YELLOW);
			yellow_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(yellow_button);
			// Green Color
			green_button = new JButton("");
			green_button.setForeground(Color.GREEN);
			green_button.setBackground(Color.GREEN);
			green_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(green_button);
			// Black Color
			black_button = new JButton("");
			black_button.setForeground(Color.BLACK);
			black_button.setBackground(Color.BLACK);
			black_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(black_button);
			// Pink Color
			pink_button = new JButton("");
			pink_button.setForeground(Color.PINK);
			pink_button.setBackground(Color.PINK);
			pink_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(pink_button);
			// White Color
			white_button = new JButton("");
			white_button.setForeground(Color.WHITE);
			white_button.setBackground(Color.WHITE);
			white_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(white_button);
			// Cyan Color
			cyan_button = new JButton("");
			cyan_button.setForeground(Color.CYAN);
			cyan_button.setBackground(Color.CYAN);
			cyan_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(cyan_button);
			// Choose Color
			choose_color = new JButton("Palette");
			choose_color.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(choose_color);

			// Fill Tool
			Icon fill_image = new ImageIcon("fill.png");
			fill = new JButton(fill_image);
			fill.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(fill);

			// Eraser Tool
			Icon eraser_image = new ImageIcon("eraser.png");
			eraser_button = new JButton(eraser_image);
			eraser_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(eraser_button);

			// Tiny Eraser
			Icon tiny_image = new ImageIcon("brush1.png");
			tiny = new JButton(tiny_image);
			tiny.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(tiny);

			// Big Eraser
			Icon big_image = new ImageIcon("brush2.png");
			big = new JButton(big_image);
			big.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(big);

			// Free-hand TOOL
			Icon pencil_image = new ImageIcon("pencil.png");
			freehand = new JButton(pencil_image);
			freehand.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(freehand);

			// Text-Box TOOL
			Icon textbox_image = new ImageIcon("textbox.png");
			textbox = new JButton(textbox_image);
			textbox.setPreferredSize(new Dimension(120, 25));
			textbox.setEnabled(false);
			sidepanel.add(textbox);

			// Clear Button
			Icon clear_image = new ImageIcon("clear.gif");
			clear = new JButton(clear_image);
			clear.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(clear);

			// Line Button
			line_button = new JButton("LINE");
			line_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(line_button);

			// Circle Button
			circle_button = new JButton("CIRCLE");
			circle_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(circle_button);

			// Square Button
			square_button = new JButton("SQUARE");
			square_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(square_button);

			// Rectangle Button
			rectangle_button = new JButton("RECTANGLE");
			rectangle_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(rectangle_button);

			// Triangle Button
			triangle_button = new JButton("TRIANGLE");
			triangle_button.setPreferredSize(new Dimension(120, 25));
			sidepanel.add(triangle_button);

			// Set the preferred size of the SidePanel to 150,200
			sidepanel.setPreferredSize(new Dimension(150, 200));
			// Add the SidePanel to WEST in the main Frame
			frame.add(sidepanel, BorderLayout.WEST);

			/*
			 * // Create a Menu - File JMenu file=new JMenu("File"); // Create
			 * the main Menu Bar JMenuBar menubar=new JMenuBar(); // Add the
			 * Menu to the Menu Bar menubar.add(file);
			 * 
			 * //Menu Items for Menu File JMenuItem quit=new JMenuItem("Quit");
			 * quit.addActionListener(new ActionListener() { public void
			 * actionPerformed(ActionEvent ev) {
			 * 
			 * int response = JOptionPane.showConfirmDialog(null,
			 * "Are you sure you want to exit?", "EXIT!!",
			 * JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); if
			 * (response == JOptionPane.NO_OPTION) { } else if (response ==
			 * JOptionPane.YES_OPTION) { System.exit(0); } } }); file.add(quit);
			 */
			// Disabling the buttons - Pupils will not have the rights to
			// draw/write on the Whiteboard
			blue_button.setEnabled(false);
			red_button.setEnabled(false);
			green_button.setEnabled(false);
			black_button.setEnabled(false);
			yellow_button.setEnabled(false);
			pink_button.setEnabled(false);
			white_button.setEnabled(false);
			cyan_button.setEnabled(false);
			choose_color.setEnabled(false);

			// Disabling the drawing tools
			square_button.setEnabled(false);
			line_button.setEnabled(false);
			rectangle_button.setEnabled(false);
			circle_button.setEnabled(false);
			triangle_button.setEnabled(false);
			eraser_button.setEnabled(false);
			fill.setEnabled(false);
			tiny.setEnabled(false);
			big.setEnabled(false);
			freehand.setEnabled(false);
			clear.setEnabled(false);

			// TextBox IMPLEMENTATION
			textbox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTextArea textarea = new JTextArea(5, 10);
					textarea.setEditable(true);
					// textarea.setEnable(true);
					panel.add(textarea, BorderLayout.WEST);
					frame.add(panel);
					frame.setVisible(true);
				}
			});

			// Clear IMPLEMENTATION
			clear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					g = panel.getGraphics();
					g.setColor(Color.white);
					g.fillRect(0, 0, 1000, 700);
				}
			});

			// Tiny Eraser - SIZE 1 Implementation
			// After selecting TINY ERASER, you need to click on ERASER button
			// again to eraser
			tiny.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					brushsize = 15;
				}
			});

			// Big Eraser - SIZE 2 Implementation
			// After selecting BIG ERASER, you need to click on ERASER button
			// again to eraser
			big.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					brushsize = 50;
				}
			});

			// Action Listener for the JButton- colors
			// Blue Implementation
			blue_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					drawColor = Color.blue;
					System.out.println("I am Blue");
				}
			});

			// Green Implementation
			green_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					drawColor = Color.green;
				}
			});

			// White Implementation
			white_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					drawColor = Color.white;
				}
			});

			// Pink Implementation
			pink_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					drawColor = Color.pink;
				}
			});

			// Black Implementation
			black_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					drawColor = Color.black;
				}
			});

			// Red Implementaion
			red_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					drawColor = Color.red;
				}
			});

			// Yellow Implementation
			yellow_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					drawColor = Color.yellow;
				}
			});

			// Cyan Implementation
			cyan_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					drawColor = Color.cyan;
				}
			});

			// Color Palette Implementation
			choose_color.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Color c = JColorChooser.showDialog(null, "Color Palette",
							Color.white);
					drawColor = c;
				}
			});

			// Circle Implementation mode 2
			circle_button.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
				}
			});

			circle_button.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent ev) {
				}

				public void mouseClicked(MouseEvent e) {
					isActive = true;
					mode = 2;
				}

				public void mouseReleased(MouseEvent e) {
					isActive = true;
					mode = 2;
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

			});

			// Square Implementation - Mode :3
			square_button.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
				}
			});

			square_button.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent ev) {
				}

				public void mouseClicked(MouseEvent e) {
					isActive = true;
					mode = 3;
				}

				public void mouseReleased(MouseEvent e) {
					isActive = true;
					mode = 3;
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

			});

			// RECTANGLE Implementation : Mode - 4
			rectangle_button.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
				}
			});

			rectangle_button.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent ev) {
				}

				public void mouseClicked(MouseEvent e) {
					isActive = true;
					mode = 4;
				}

				public void mouseReleased(MouseEvent e) {
					isActive = true;
					mode = 4;
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

			});

			// TRIANGLE Implementation : Mode -5
			triangle_button.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
				}
			});

			triangle_button.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent ev) {
				}

				public void mouseClicked(MouseEvent e) {
					isActive = true;
					mode = 5;
				}

				public void mouseReleased(MouseEvent e) {
					isActive = true;
					mode = 5;
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

			});

			// LINE IMPLEMENTATION : Mode -1
			line_button.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {

				}
			});

			line_button.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent ev) {
				}

				public void mouseClicked(MouseEvent e) {
					isActive = true;
					mode = 1;
				}

				public void mouseReleased(MouseEvent e) {
					isActive = true;
					mode = 1;
				}

				public void mouseEntered(MouseEvent e) {

				}

				public void mouseExited(MouseEvent e) {

				}

			});

			// ERASER Implementation : Mode -10
			eraser_button.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent ev) {
				}

				public void mouseClicked(MouseEvent e) {
					isActive = true;
					mode = 10;
				}

				public void mouseReleased(MouseEvent e) {
					isActive = true;
					mode = 10;
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

			});

			eraser_button.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
				}
			});

			// PANEL Implementation
			panel.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					if (isActive) {
						x = e.getX();
						y = e.getY();

						if (mode == 6) // Line implemented
						{
							g = panel.getGraphics();
							g.setColor(drawColor);
							g.drawLine(prevX, prevY, x, y);

							prevX = x;
							prevY = y;
						} else if (mode == 10) // Eraser was selected
						{
							Graphics2D g2 = (Graphics2D) g;
							g = panel.getGraphics();
							g2.setColor(Color.white);

							g2.setStroke(new BasicStroke(brushsize));
							g2.drawLine(prevX, prevY, x, y);

							prevX = x;
							prevY = y;
						}

					}
				}
			});

			panel.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent ev) {
					if (isActive) {
						prevX = ev.getX();
						prevY = ev.getY();
					}
				}

				public void mouseClicked(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
					if (isActive) {
						g = panel.getGraphics();
						g.setColor(drawColor);
						int mx, my, dx, dy, r, b, gr;
						r = drawColor.getRed();
						gr = drawColor.getGreen();
						b = drawColor.getBlue();

						if (mode == 2) // Circle
						{
							mx = Math.min(x, prevX);
							my = Math.min(y, prevY);
							dx = Math.abs(x - prevX);
							dy = Math.abs(y - prevY);
							g.drawOval(mx, my, dx, dy);

						}

						else if (mode == 3) // Square
						{
							mx = Math.min(x, prevX);
							my = Math.min(y, prevY);
							dx = Math.max(Math.abs(x - prevX),
									Math.abs(y - prevY));
							g.drawRect(mx, my, dx, dx);

						}

						else if (mode == 4) // Rectangle
						{
							mx = Math.min(x, prevX);
							my = Math.min(y, prevY);
							dx = Math.abs(x - prevX);
							dy = Math.abs(y - prevY);
							g.drawRect(mx, my, dx, dy);

						}

						else if (mode == 5) // Triangle
						{
							g.drawLine(prevX, y, x, y);
							g.drawLine(prevX, y, (int) ((x + prevX) / 2), prevY);
							g.drawLine((int) ((x + prevX) / 2), prevY, x, y);

						}

						else if (mode == 1) // Line
						{
							g.drawLine(prevX, prevY, x, y);
							UI.writeToServer("L," + prevX + "," + prevY + ","
									+ x + "," + y + ",", outToServer,
									inFromServer);

						} else
							System.out.println("Wrong mode!");
					}
					isActive = false;
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

			});

			// FREE HAND IMPLEMENTATION
			freehand.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent ev) {
				}

				public void mouseClicked(MouseEvent e) {
					isActive = true;
					mode = 6;
				}

				public void mouseReleased(MouseEvent e) {
					isActive = true;
					mode = 6;
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

			});

			freehand.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
				}
			});

			// FILL Implementation
			fill.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					g = panel.getGraphics();
					g.setColor(drawColor);
					g.fillRect(0, 0, 1000, 700);
					drawColor = Color.black; // setting it back to default color
												// - BLACK
				}
			});

			panel.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					try {
						g = panel.getGraphics();
						String ServerInput = "";
						String Splits[];
						int v1 = 0, v2 = 0, v3 = 0, v4 = 0;
						char type;
						int ctr = 5;
						while ((ServerInput = inFromServer.readLine()) != null) {
							// System.out.println("Co-od: " + ServerInput);
							if (ServerInput.charAt(0) == '#'
									&& ServerInput.charAt(3) != '#')
								System.exit(0);
							Splits = ServerInput.split(",");
							type = Splits[0].charAt(0);
							v1 = Integer.parseInt(Splits[1]);
							// System.out.println(Splits.length);

							v2 = Integer.parseInt(Splits[2]);
							v3 = Integer.parseInt(Splits[3]);
							v4 = Integer.parseInt(Splits[4]);

							System.out.println(" Type " + type + " Vars " + v1
									+ " ," + v2 + " ," + v3 + " ," + v4 + " ");
							switch (type) {
							case 'R':
								g.drawRect(v1, v2, v3, v4);
								break;
							case 'O':
								g.drawOval(v1, v2, v3, v4);
								break;
							case 'L':
								g.drawLine(v1, v2, v3, v4);
								break;
							case 'C':
								g.setColor(new Color(v1));
								break;
							case 'X':
								Color tem = g.getColor();
								g.setColor(Color.white);
								g.fillRect(0, 0, 1000, 700);
								g.setColor(tem);
								break;
							case 'F':
								Color t = g.getColor();
								g.fillRect(0, 0, 1000, 700);
								g.setColor(t);
								break;
							case 'T':
								System.out.println("Hi"); // enableButton();
								blue_button.setEnabled(true);
								red_button.setEnabled(true);
								green_button.setEnabled(true);
								black_button.setEnabled(true);
								pink_button.setEnabled(true);
								yellow_button.setEnabled(true);
								white_button.setEnabled(true);
								cyan_button.setEnabled(true);
								choose_color.setEnabled(true);

								// Enable the drawing tools
								square_button.setEnabled(true);
								line_button.setEnabled(true);
								rectangle_button.setEnabled(true);
								circle_button.setEnabled(true);
								triangle_button.setEnabled(true);
								eraser_button.setEnabled(true);
								fill.setEnabled(true);
								tiny.setEnabled(true);
								big.setEnabled(true);
								freehand.setEnabled(true);
								clear.setEnabled(true);
								try {
									echoSocket = new Socket(hostName, 6791);
									outToServer = new PrintWriter(echoSocket
											.getOutputStream(), true);
									inFromServer = new BufferedReader(
											new InputStreamReader(echoSocket
													.getInputStream()));
									stdIn = new BufferedReader(
											new InputStreamReader(System.in));
									System.out.println("Connected Server");
								} catch (Exception er) {
								}

							}

						}
					} catch (IOException ex) {
						System.err
								.println("Couldn't get I/O for the connection to "
										+ hostName);
						System.exit(1);
					}
				}
			});

			// Add the MenuBar to the Frame window
			// frame.setJMenuBar(menubar);
			// menubar.setVisible(true);

			// Set the default background of the Panel to WHITE
			panel.setBackground(Color.white);
			// Add the Panel to the Frame window
			frame.add(panel);
			frame.setSize(1000, 700); // Set the size of the Frame to 1000,700
			frame.setVisible(true);
			frame.setTitle("Distributed Whiteboard"); // Displays the title of
														// the Frame as
														// 'Distributed
														// Whiteboard'
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to "
					+ hostName);
			System.exit(1);
		}
	}

	public static void writeToServer(String set, PrintWriter outToServer,
			BufferedReader inFromServer) {
		try {
			record = record.concat(set);
			record = record.concat("!"); // signifies end of one instruction
			outToServer.println(set + "!");
			String ServerInput = inFromServer.readLine();
			System.out.println("server sent: " + ServerInput);
		} catch (Exception er) {
		}
	}

	public static void enableButton() {
		// Enable the buttons - Pupils will have the rights to draw/write on the
		// Whiteboard because of the TOKEN
		blue_button.setEnabled(true);
		red_button.setEnabled(true);
		green_button.setEnabled(true);
		black_button.setEnabled(true);
		yellow_button.setEnabled(true);
		white_button.setEnabled(true);
		cyan_button.setEnabled(true);
		choose_color.setEnabled(true);

		// Enable the drawing tools
		square_button.setEnabled(true);
		line_button.setEnabled(true);
		rectangle_button.setEnabled(true);
		circle_button.setEnabled(true);
		triangle_button.setEnabled(true);
		eraser_button.setEnabled(true);
		fill.setEnabled(true);
		tiny.setEnabled(true);
		big.setEnabled(true);
		freehand.setEnabled(true);
		clear.setEnabled(true);

	}
}
