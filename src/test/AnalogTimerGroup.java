package test;

import java.time.Duration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

public class AnalogTimerGroup implements TimerInterface {
	private Group timeGroup;
	private AnalogClock clock;

	public AnalogTimerGroup(Composite parent) {
		timeGroup = new Group(parent, SWT.BORDER);
		timeGroup.setLayout(new RowLayout());
		timeGroup.setText(ThreadedTimerWithGUI.TIME_GRP_TXT);
		timeGroup.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		start();
	}

	@Override
	public void SetDuration(Duration duration) {
		clock.SetDuration(duration);

	}

	@Override
	public void nexLap() {
		clock = new AnalogClock(timeGroup);
		timeGroup.layout();
		timeGroup.getShell().pack();
	}

	@Override
	public void start() {
		for (Control element : timeGroup.getChildren()) {
			element.dispose();
		}
		clock = new AnalogClock(timeGroup);
		timeGroup.layout();
		timeGroup.getShell().pack();

	}

}
