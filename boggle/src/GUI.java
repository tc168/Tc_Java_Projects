import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class GUI extends JPanel implements ActionListener {
	private static final int time = 180;
	private int duration = 0;
	private int score = 0;
	private JFrame frame = new JFrame("Boggle Game!");
	private JButton buttonSubmit;
	private JTextField txtTypeYourWord;
	private JTextArea txtCurrentWords, txtCurrentMessages;
	private JLabel lblTime, lblCurrentScore;
	private Timer timer;
	private Task task;
	private Boggle boggle;
	private JButton[][] letters;
	private JMenuBar menuBar;
	private JMenuItem mntmStop, mntmStart, mntmChangeSize;
	private int size;
	private JList list;
	private DefaultListModel listModel;

	// Constructs a GUI and initialises the JPanel, frame and various other GUI
	// components
	public GUI() throws IOException {
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(650, 650);
		frame.setVisible(true);
		setLayout(null);

		boggle = new Boggle(4);
		letters = new JButton[boggle.getNumDimensions()][boggle
				.getNumDimensions()];

		// Create the textfield for entering words, and the label to accompany
		// it.
		JLabel lblTypeYourWord = new JLabel("Enter your word:");
		add(lblTypeYourWord);
		lblTypeYourWord.setBounds(20, 148, 100, 20);

		txtTypeYourWord = new JTextField();
		add(txtTypeYourWord);
		txtTypeYourWord.setBounds(20, 168, 141, 20);

		buttonSubmit = new JButton("Submit");
		buttonSubmit.setEnabled(false);
		// add actionlistener to the button to allow actionPerformed
		buttonSubmit.addActionListener(this);
		add(buttonSubmit);
		buttonSubmit.setBounds(81, 199, 80, 20);

		// create the buttons

		for (int row = 0; row < boggle.getNumDimensions(); ++row) {
			for (int col = 0; col < boggle.getNumDimensions(); ++col) {
				letters[row][col] = new JButton("");
				add(letters[row][col]);
				letters[row][col].setBounds(50 + col * 70, 300 + row * 70, 70,
						70);
			}
		}

		// create the label for time remaining
		JLabel lblTimeRemaining = new JLabel("Time Remaining:");
		add(lblTimeRemaining);
		lblTimeRemaining.setBounds(20, 33, 100, 20);

		lblTime = new JLabel("");
		lblTime.setOpaque(true);
		lblTime.setBackground(Color.WHITE);
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTime);
		lblTime.setBounds(20, 58, 100, 20);

		// setup the label for the score
		JLabel lblScore = new JLabel("Score:");
		add(lblScore);
		lblScore.setBounds(20, 89, 100, 20);

		lblCurrentScore = new JLabel("");
		lblCurrentScore.setOpaque(true);
		lblCurrentScore.setBackground(Color.WHITE);
		lblCurrentScore.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblCurrentScore);
		lblCurrentScore.setBounds(20, 114, 100, 20);

		// setup the found words label
		JLabel lblFoundWords = new JLabel("Human Words");
		add(lblFoundWords);
		lblFoundWords.setBounds(187, 11, 150, 20);

		txtCurrentWords = new JTextArea("");
		txtCurrentWords.setLineWrap(true);
		txtCurrentWords.setEditable(false);
		add(txtCurrentWords);
		txtCurrentWords.setBounds(187, 36, 201, 236);

		JLabel lblMessages = new JLabel("Words in the Puzzle");
		add(lblMessages);
		lblMessages.setBounds(398, 11, 194, 20);

		txtCurrentMessages = new JTextArea("");
		txtCurrentMessages.setLineWrap(true);
		txtCurrentMessages.setEditable(false);
		add(txtCurrentMessages);
		txtCurrentMessages.setBounds(398, 36, 194, 236);

		menuBar = new JMenuBar();
		menuBar.setBackground(Color.DARK_GRAY);
		frame.setJMenuBar(menuBar);

		mntmStart = new JMenuItem("Start");
		mntmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					startGame();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menuBar.add(mntmStart);

		mntmStop = new JMenuItem("Stop");
		mntmStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				handleEndOfGame();
			}
		});
		menuBar.add(mntmStop);

	}

	private void startGame() throws FileNotFoundException {
		// disable start button and reset the textfields and labels (assuming
		// new game) because start of game

		mntmStart.setEnabled(false);
		score = 0;
		lblCurrentScore.setText("" + score);
		txtCurrentWords.setText("");
		txtCurrentMessages.setText("");

		// ask for user input and disable previous buttons
		size = Integer
				.parseInt(JOptionPane.showInputDialog("How big map size"));
		for (int row = 0; row < boggle.getNumDimensions(); ++row) {
			for (int col = 0; col < boggle.getNumDimensions(); ++col) {
				letters[row][col].setVisible(false);
			}
		}
		// create a new board with new letters

		boggle = new Boggle(size);

		letters = new JButton[boggle.getNumDimensions()][boggle
				.getNumDimensions()];

		// add buttons to the GUI
		for (int row = 0; row < boggle.getNumDimensions(); ++row) {
			for (int col = 0; col < boggle.getNumDimensions(); ++col) {
				letters[row][col] = new JButton("");
				add(letters[row][col]);
				letters[row][col].setBounds(50 + col * 70, 300 + row * 70, 70,
						70);
			}
		}

		/*
		 * Go through all the rows and columns of the Grid and set the
		 * appropriate character in each cell.
		 */

		for (int row = 0; row < size; ++row) {
			for (int col = 0; col < size; ++col) {
				letters[row][col].setText(boggle.getChar(row, col));
			}
		}

		duration = time;
		timer = new Timer();
		task = new Task();
		timer.scheduleAtFixedRate(task, 0, 1000);
		buttonSubmit.setEnabled(true);
		mntmStop.setEnabled(true);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == buttonSubmit) {
			handleSubmitButton();
		}
	}

	private void handleSubmitButton() {
		String word = txtTypeYourWord.getText();

		/*
		 * If the submitted word is a valid word and hasn't been found already,
		 * add to the list of words found and increment score based off of the
		 * length of the word.
		 */
		if (boggle.hasWord(word)) {
			String words = txtCurrentWords.getText();
			String tempWords = " " + words + ",";
			if (tempWords.toLowerCase()
					.contains(" " + word.toLowerCase() + ",")) {
				txtCurrentMessages.setText("Already found \"" + word);
			} else {
				words += (words.isEmpty()) ? word : ", " + word;
				txtCurrentWords.setText(words);
				txtCurrentMessages.setText("");
				score += getScore(word);
				score = (score < 0) ? 0 : score;
				lblCurrentScore.setText("" + score);
			}
		} else {
			if (word.isEmpty()) {
				txtCurrentMessages
						.setText("The entered word cannot be blank! Please try again.");
			} else {
				txtCurrentMessages.setText(word
						+ "\" is not one of the valid words on the board.");
				score -= getScore(word);
				score = (score < 0) ? 0 : score;
				lblCurrentScore.setText("" + score);
			}
		}

		txtTypeYourWord.setText("");
	}

	private String secondsToMinutesAndSeconds(int seconds) {
		int min = seconds / 60;
		int sec = seconds - min * 60;
		String strSec = "" + sec;

		if (strSec.length() < 2) {
			strSec = "0" + strSec;
		}

		return min + ":" + strSec;
	}

	private void handleEndOfGame() {
		/*
		 * Cancel the timer, blank out the timer label and revert buttons to
		 * their original states.
		 */
		timer.cancel();
		lblTime.setText("");
		buttonSubmit.setEnabled(false);
		mntmStart.setEnabled(true);
		mntmStop.setEnabled(false);

		String msg = "The game has ended, here is the list of all valid words on the board:\n\n";
		ArrayList list = boggle.getAllWords();

		for (Object obj : list) {
			String word = (String) obj;
			msg += word + ", ";
		}

		msg = msg.substring(0, msg.length() - 2);
		txtCurrentMessages.setText(msg);
	}

	class Task extends TimerTask {
		/*
		 * Keep running until time runs out. Decrement the duration (time left)
		 * each time to prevent from running indefinitely.
		 */
		public void run() {
			if (duration <= 0) {
				handleEndOfGame();
				return;
			}

			lblTime.setText(secondsToMinutesAndSeconds(--duration));
		}
	}

	private int getScore(String word) {
		int length = word.length();

		switch (length) {
		case 0:
		case 1:
		case 2:
			return 0;
		case 3:
			return 1;
		case 4:
		case 5:
		case 6:
		case 7:
			return length - 3;
		}

		return 11;
	}



}
