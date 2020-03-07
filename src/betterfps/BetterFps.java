package betterfps;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

/**
 * @author Guilherme Chaguri
 */
public class BetterFps extends JDialog implements ActionListener {

	private static final long serialVersionUID = 44667353872657875L;

	public static void info(String log, Object... data) {
		System.out.println(String.format(log, data));
	}

	public static void error(String log, Object... data) {
		System.err.println(String.format(log, data));
	}

	public static void main(String[] args) {
		warmupClasses();
		Map<String, Long> data = benchmark(1000000000, 5000);

		String bestAlgorithm = null;
		long bestTime = Long.MAX_VALUE;

		for (String key : data.keySet()) {
			long time = data.get(key);
			if (time < bestTime) {
				bestAlgorithm = key;
				bestTime = time;
			}
		}

		System.out.println(bestAlgorithm);
		System.exit(0);
	}

	private static void warmupClasses() {
		// Initialize the classes, so the loading time will not count in the
		// startBenchmark
		JavaMath.sin(0);
		VanillaMath.sin(0);
		LibGDXMath.sin(0);
		RivensMath.sin(0);
		RivensFullMath.sin(0);
		RivensHalfMath.sin(0);
		TaylorMath.sin(0);
	}

	private static Map<String, Long> benchmark(int maxLoops, int maxTime) {
		long javaMath = 0;
		long vanilla = 0;
		long libgdx = 0;
		long rivens = 0;
		long rivensFull = 0;
		long rivensHalf = 0;
		long taylors = 0;

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < maxLoops; i++) {
			float angle = (float) Math.toRadians(i % 360);
			long time;

			time = System.nanoTime();
			JavaMath.sin(angle);
			JavaMath.cos(angle);
			javaMath += System.nanoTime() - time;

			time = System.nanoTime();
			VanillaMath.sin(angle);
			VanillaMath.cos(angle);
			vanilla += System.nanoTime() - time;

			time = System.nanoTime();
			LibGDXMath.sin(angle);
			LibGDXMath.cos(angle);
			libgdx += System.nanoTime() - time;

			time = System.nanoTime();
			RivensMath.sin(angle);
			RivensMath.cos(angle);
			rivens += System.nanoTime() - time;

			time = System.nanoTime();
			RivensFullMath.sin(angle);
			RivensFullMath.cos(angle);
			rivensFull += System.nanoTime() - time;

			time = System.nanoTime();
			RivensHalfMath.sin(angle);
			RivensHalfMath.cos(angle);
			rivensHalf += System.nanoTime() - time;

			time = System.nanoTime();
			TaylorMath.sin(angle);
			TaylorMath.cos(angle);
			taylors += System.nanoTime() - time;

			if (System.currentTimeMillis() - startTime > maxTime) {
				break;
			}
		}

		HashMap<String, Long> results = new HashMap<String, Long>();
		results.put("java", javaMath);
		results.put("vanilla", vanilla);
		results.put("rivens", rivens);
		results.put("rivens-full", rivensFull);
		results.put("rivens-half", rivensHalf);
		results.put("libgdx", libgdx);
		results.put("taylors", taylors);
		return results;
	}

	private final String CHANGE_ALGORITHM = "change_algorithm";
	private final String TEST_AGAIN = "test_again";

	private final JLabel status;
	private final JPanel data;
	private final JButton again;
	private final JButton change;

	private String bestAlgorithm = "rivens-half";

	public BetterFps() {
		setTitle("BetterFps");
		setResizable(false);
		setModal(true);
		setMinimumSize(new Dimension(300, 200));
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
		int y = (int) screensize.getHeight() / 2 - getHeight() / 2;
		setLocation(x, y);

		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;

		status = new JLabel();
		status.setHorizontalAlignment(JLabel.CENTER);
		status.setFont(status.getFont().deriveFont(16F));
		content.add(status, c);

		c.gridy++;
		content.add(Box.createVerticalStrut(15), c);

		c.gridy++;
		data = new JPanel();
		data.setLayout(new GridLayout(0, 3));
		data.setVisible(false);
		content.add(data, c);

		c.gridy++;
		content.add(Box.createVerticalStrut(15), c);

		c.gridy++;
		again = new JButton("再测试一次");
		again.setActionCommand(TEST_AGAIN);
		again.addActionListener(this);
		again.setVisible(false);
		content.add(again, c);

		c.gridy++;
		change = new JButton();
		change.setActionCommand(CHANGE_ALGORITHM);
		change.addActionListener(this);
		change.setVisible(false);
		content.add(change, c);

		add(content);
		pack();
		setSize(getPreferredSize());
		// setLocationRelativeTo(installer);

		startBenchmark();
	}

	private void showResults(Map<String, Long> results) {
		info("Done! Showing the results");
		data.removeAll();

		long bestTime = Long.MAX_VALUE;

		for (String key : results.keySet()) {
			data.add(new JLabel(key + "算法"));

			long d = results.get(key);
			if (d < bestTime) {
				bestAlgorithm = key;
				bestTime = d;
			}

			JLabel nanoseconds = new JLabel(d + " ns");
			nanoseconds.setHorizontalAlignment(JLabel.RIGHT);
			data.add(nanoseconds);

			JLabel miliseconds = new JLabel(TimeUnit.NANOSECONDS.toMillis(d) + " ms");
			miliseconds.setHorizontalAlignment(JLabel.RIGHT);
			data.add(miliseconds);
		}

		String algorithmName = bestAlgorithm;
		change.setText("改用" + algorithmName + "算法 ");

		data.setVisible(true);
		again.setVisible(true);
		change.setVisible(true);
		status.setText("算法测试");

		pack();
		setSize(getPreferredSize());
	}

	private void startBenchmark() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				status.setText("正检测算法...");
				data.setVisible(false);
				again.setVisible(false);
				change.setVisible(false);

				info("Benchmarking algorithms...");
				warmupClasses();
				showResults(benchmark(1000000000, 10000));
			}
		});
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();

		if (action.equals(TEST_AGAIN)) {

			startBenchmark();

		} else if (action.equals(CHANGE_ALGORITHM)) {

			try {
				System.out.println("更改算法为"+bestAlgorithm);
				saveAlgorithm(bestAlgorithm);
			} catch (Exception ex) {
				error("Couldn't save the algorithm: %s", ex.getMessage());
			}
			setVisible(false);

		}
	}
    public void saveAlgorithm(String bestAlgorithm) throws IOException {
    	File directory = new File(".");
    	File fileold = new File(directory.getCanonicalPath() + "\\.minecraft\\betterfps.txt");
    	File filenew = new File(directory.getCanonicalPath() + "\\.minecraft\\config\\betterfps.json");
    	fileold.delete();
    	fileold.createNewFile();
    	BufferedWriter out = new BufferedWriter(new FileWriter(fileold));
    	out.write("#BetterFps Config\r\n");
    	out.write("#By WeiMinecraftTools\r\n");
    	out.write("algorithm=");
    	out.write(bestAlgorithm);
    	out.flush();
    	out.close();
    	
    	JSONObject jsonObject = new JSONObject(FileUtils.readFileToString(filenew, "UTF-8"));
    	jsonObject.put("algorithm",bestAlgorithm);
    	
    	
    }
}
