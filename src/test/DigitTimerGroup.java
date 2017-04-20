package test;

import java.time.Duration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class DigitTimerGroup implements TimerInterface {
	private Group timeGroup;
	private Duration duration;
	private Text text;

	public DigitTimerGroup(Composite parent) {
		timeGroup = new Group(parent, SWT.BORDER);
		timeGroup.setLayout(new FillLayout(SWT.VERTICAL));
		timeGroup.setText(ThreadedTimerWithGUI.TIME_GRP_TXT);
		timeGroup.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		start();
	}

	@Override
	public void SetDuration(Duration duration) {
		text.setText(formatDuration(duration));
	}

	@Override
	public void nexLap() {
		text = new Text(timeGroup, SWT.BORDER);
		text.setEditable(false);
		text.getShell().pack();
	}

	public static String formatDuration(Duration duration) {
		long seconds = duration.getSeconds();
		long absSeconds = Math.abs(seconds);
		int nanos = duration.getNano();
		String positive = String.format("%d:%02d:%02d:%03d", absSeconds / 3600, (absSeconds % 3600) / 60,
				absSeconds % 60, nanos / 1000000);
		return positive;
	}

	@Override
	public void start() {
		for (Control element : timeGroup.getChildren()) {
			element.dispose();
		}
		text = new Text(timeGroup, SWT.BORDER);
		duration = Duration.ofNanos(0);
		text.setText(formatDuration(duration));
		text.getParent().layout();
		text.getShell().pack();
	}

}
