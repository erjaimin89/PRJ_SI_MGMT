package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class PrintUIWindow implements Printable, ActionListener {

	private JPanel frameToPrint;
	
	private JDialog dialogToPrint;
	private boolean flag;

	public PrintUIWindow(JPanel frameToPrint) {
		this.frameToPrint = frameToPrint;
		this.flag = true;
	}
	
	public PrintUIWindow(JDialog dialogToPrint) {
		this.dialogToPrint = dialogToPrint;
	}

	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		if (page > 0) { /* We have only one page, and 'page' is zero-based */
			return NO_SUCH_PAGE;
		}

		Paper paper = pf.getPaper();
		double margin1 = 40.0;
		double margin2 = 20.0;
		paper.setImageableArea(margin1, 
				margin2, 
		        paper.getWidth() - 2* margin1, paper.getHeight()- 2* margin2);
		pf.setPaper(paper);
		/*
		 * User (0,0) is typically outside the imageable area, so we must
		 * translate by the X and Y values in the PageFormat to avoid clipping
		 */
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform originalTransform = g2d.getTransform();

//		double scaleX = pf.getImageableWidth() / frameToPrint.getWidth();
//		double scaleY = pf.getImageableHeight() / frameToPrint.getHeight();
		// Maintain aspect ratio
//		double scale = Math.min(scaleX, scaleY);
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		g2d.scale(0.65, 0.65);
//		this.printAll(g2);

		
		
		
//		g2d.translate(pf.getImageableX(), pf.getImageableY());

		/* Now print the window and its visible contents */
		if(flag)
			frameToPrint.printAll(g2d);
		else
			dialogToPrint.paintAll(g2d);

		g2d.setTransform(originalTransform);	
		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
	}

	public void actionPerformed(ActionEvent e) {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean ok = job.printDialog();
		if (ok) {
			try {
				job.print();
			} catch (PrinterException ex) {
				/* The job did not successfully complete */
			}
		}
	}

}
