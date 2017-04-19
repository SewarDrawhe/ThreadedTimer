package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class ThreadedTimerWithGUI {
	private Composite composite;
	private Text text;
	private Button StartBtn;
	private Button StopBtn;
	private Button PauseBtn;
	private Button LapBtn;
	private Button CloseBtn;
	private TimerThread thread;
	private Group timeGroup;

	public static final String TIME_GRP_TXT = "Time";
	public static final String START_TIME_TXT = "0:00:00:000";
	public static final String START_BTN_TXT = "Start";
	public static final String STOP_BTN_TXT = "Stop";
	public static final String PAUSE_BTN_TXT = "Pause";
	public static final String RESUME_BTN_TXT = "Resume";
	public static final String LAP_BTN_TXT = "Next Lap";
	public static final String CLOSE_BTN_TXT = "Close";

	public ThreadedTimerWithGUI(Composite parent) {

		composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new GridLayout(1, false));
		Composite buttonComposite = new Composite(composite, SWT.NONE);
		buttonComposite.setLayout(new RowLayout());
		timeGroup = new Group(composite, SWT.BORDER);
		timeGroup.setLayout(new FillLayout(SWT.VERTICAL));
		timeGroup.setText(TIME_GRP_TXT);
		timeGroup.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		text = new Text(timeGroup, SWT.BORDER);
		text.setEditable(false);
		text.setText(START_TIME_TXT);

		StartBtn = new Button(buttonComposite, SWT.PUSH);
		StartBtn.setText(START_BTN_TXT);
		StartBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				thread = new TimerThread(timeGroup);
				thread.setDaemon(true);
				thread.start();
				PauseBtn.setText(PAUSE_BTN_TXT);
				StartBtn.setEnabled(false);
				StopBtn.setEnabled(true);
				LapBtn.setEnabled(true);
				PauseBtn.setEnabled(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		StopBtn = new Button(buttonComposite, SWT.PUSH);
		StopBtn.setText(STOP_BTN_TXT);
		StopBtn.setEnabled(false);
		StopBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				thread.interrupt();
				StartBtn.setEnabled(true);
				StopBtn.setEnabled(false);
				LapBtn.setEnabled(false);
				PauseBtn.setEnabled(false);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		PauseBtn = new Button(buttonComposite, SWT.PUSH);
		PauseBtn.setText(PAUSE_BTN_TXT);
		PauseBtn.setEnabled(false);
		PauseBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!thread.isPaused()) {
					thread.pause();
					LapBtn.setEnabled(false);
					PauseBtn.setText(RESUME_BTN_TXT);
				} else {
					thread.resumeTimer();
					;
					LapBtn.setEnabled(true);
					PauseBtn.setText(PAUSE_BTN_TXT);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		LapBtn = new Button(buttonComposite, SWT.PUSH);
		LapBtn.setText(LAP_BTN_TXT);
		LapBtn.setEnabled(false);
		LapBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				thread.nextLap();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		CloseBtn = new Button(buttonComposite, SWT.PUSH);
		CloseBtn.setText(CLOSE_BTN_TXT);
		CloseBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Composite parent = composite.getParent();
				composite.dispose();
				parent.pack();
				if (thread != null) {
					thread.interrupt();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
	}

}
