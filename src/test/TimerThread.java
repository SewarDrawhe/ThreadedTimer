package test;

import java.time.Duration;
import java.time.LocalTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class TimerThread extends Thread {
	private volatile Composite parent;
	private volatile Text text;
	private boolean isPaused;
	private Duration tmpDuration = Duration.ofNanos(0);
	LocalTime startCountTime;

	public TimerThread(Composite parent) {
		this.parent = parent;
		text = (Text) parent.getChildren()[0];
		for (Control element : parent.getChildren()) {
			element.dispose();
		}
		text = new Text(parent, SWT.BORDER);
		text.setEditable(false);
		parent.layout(true);
		parent.getShell().pack();
		isPaused = false;
	}

	public void pause() {
		isPaused = true;
	}

	public void resumeTimer() {
		isPaused = false;
		startCountTime = LocalTime.now();
		synchronized (this) {
			this.notify();
		}

	}

	@Override
	public void run() {
		startCountTime = LocalTime.now();
		while (true) {
			synchronized (this) {
				if (isPaused) {
					try {
						tmpDuration = Duration.between(startCountTime, LocalTime.now()).plus(tmpDuration);
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}

				}
			}

			Display.getDefault().syncExec(new Runnable() {

				@Override
				public void run() {
					try {
						text.setText(formatDuration(Duration.between(startCountTime, LocalTime.now()).plus(tmpDuration)));
					} catch (Exception e) {
						return;
					}
				}
			});
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				return;
			}

		}

	}

	public boolean isPaused() {
		return isPaused;
	}

	public void nextLap() {
		if (isAlive()) {
			text = new Text(parent, SWT.BORDER);
			text.setEditable(false);
			parent.getShell().pack();
		}

	}

	public static String formatDuration(Duration duration) {
		long seconds = duration.getSeconds();
		long absSeconds = Math.abs(seconds);
		int nanos = duration.getNano();
		String positive = String.format("%d:%02d:%02d:%03d", absSeconds / 3600, (absSeconds % 3600) / 60,
				absSeconds % 60, nanos / 1000000);
		return  positive;
	}
}
