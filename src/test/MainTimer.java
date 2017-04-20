package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainTimer {
	public static final String SHELL_HEADER = "Timer";
	public static final String ADD_TIMER_BTN_TXT = "Add Digit Timer";
	public static final String ADD_ANALOG_TIMER_BTN_TXT = "Add Analog Timer";

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText(SHELL_HEADER);
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		Composite buttonComposite = new Composite(shell, SWT.NONE);
		buttonComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		Button addTimerBtn = new Button(buttonComposite, SWT.PUSH);
		addTimerBtn.setText(ADD_TIMER_BTN_TXT);
		addTimerBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				new ThreadedTimerWithGUI(shell, false);
				shell.pack();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		Button addAnalogTimerBtn = new Button(buttonComposite, SWT.PUSH);
		addAnalogTimerBtn.setText(ADD_ANALOG_TIMER_BTN_TXT);
		addAnalogTimerBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				new ThreadedTimerWithGUI(shell, true);
				shell.pack();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
