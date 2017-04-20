package test;

import java.time.Duration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

public class AnalogClock {
	private Composite composite;
	private int sec;
	private int min;
	private int hour;
	private Point size;

	public AnalogClock(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.addPaintListener(event -> {
			 size = composite.getSize();
			
			drawDial(event);
			drawHands(event);

		});

	}


	private void drawDial(PaintEvent event) {
		event.gc.drawOval(0, 0, size.x - 1, size.y - 1);
		event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_RED));
		Point centerPoint = new Point(size.x / 2, size.y / 2);
		for (int i = 0; i < 60; i += 5) {
			Point point = getCoordinate(i, size.x / 2 - 1, size.y / 2 - 1, centerPoint);
			event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_DARK_GRAY));
			event.gc.drawLine(size.x / 2, size.y / 2, point.x, point.y);
		}

	}

	private void drawHands(PaintEvent event) {
		Point centerPoint = new Point(size.x / 2, size.y / 2);
		event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_RED));
		Point point = getCoordinate(sec, size.x / 2 - (int)(size.x*0.1), size.y / 2 - (int)(size.y*0.1), centerPoint);
		event.gc.drawLine(size.x / 2, size.y / 2, point.x, point.y);
		event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_BLUE));
		point = getCoordinate(min, size.x / 2 - (int)(size.x*0.2), size.y / 2 - (int)(size.y*0.2), centerPoint);
		event.gc.drawLine(size.x / 2, size.y / 2, point.x, point.y);
		event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_YELLOW));
		point = getCoordinate(hour, size.x / 2 - (int)(size.x*0.3), size.y / 2 - (int)(size.y*0.3), centerPoint);
		event.gc.drawLine(size.x / 2, size.y / 2, point.x, point.y);
	}

	private static Point getCoordinate(int time, int with, int height, Point center) {
		int x = (int) (Math.cos(time * Math.PI / 30 - Math.PI / 2) * with + center.x);
		int y = (int) (Math.sin(time * Math.PI / 30 - Math.PI / 2) * height + center.y);
		return new Point(x, y);

	}

	public void SetDuration(Duration duration) {
		long absSeconds = Math.abs(duration.getSeconds());
		hour = (int) (absSeconds / 3600);
		min = (int) ((absSeconds % 3600) / 60);
		sec = (int) (absSeconds % 60);
		composite.redraw();
	}



}
