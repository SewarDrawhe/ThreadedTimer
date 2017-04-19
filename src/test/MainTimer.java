package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import test.ThreadedTimerWithGUI;

public class MainTimer {
	public static final String SHELL_HEADER = "Timer";
	public static final String ADD_TIMER_BTN_TXT = "Add Timer";

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText(SHELL_HEADER);
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		Button addTimerBtn = new Button(shell, SWT.PUSH);
		addTimerBtn.setLayoutData(new RowData(245, 40));
		addTimerBtn.setText(ADD_TIMER_BTN_TXT);
		addTimerBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				new ThreadedTimerWithGUI(shell);
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
