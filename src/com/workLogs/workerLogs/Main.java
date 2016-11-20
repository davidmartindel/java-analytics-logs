package com.workLogs.workerLogs;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.workLogs.bean.Line;

public class Main {

	static final boolean FALSE = false;
	static final boolean TRUE = true;
	static final String NAMEDATOS = "data.log";

	public static void main(String args[]) {

		//Var init
			
		// args basicos demo
		if (args.length < 2) {
			args = new String[3];
			//en target/class
			args[0] = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath(); // dir
			args[0] = "/tmp/";
			args[1] = "hostUno"; // host a analizar
			args[2] = "hostDos"; // host a analizar
		}
		//args = Arrays.copyOfRange(args, 1, args.length);
		
		String logDatos = args[0] + NAMEDATOS; 
		
		String dirArg = args[0];
		
		
		
		// /////////////////////
		// /EJECUTA CADA 30 mins
		// /////////////////////
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int inicioMin = c.get(Calendar.MINUTE);

		while ((inicioMin < 29 && c.get(Calendar.MINUTE) < 29)
				|| (inicioMin > 29 && c.get(Calendar.MINUTE) > 29)) {

			String separator = System.getProperty("file.separator");

			c = Calendar.getInstance();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			int hourOfDay = (c.get(Calendar.HOUR) * 100)
					+ c.get(Calendar.MINUTE);

			(new File(dirArg + hourOfDay)).mkdir();
			System.out.println("  Inicio ");

			FileOutputStream file;
			try {
				file = new FileOutputStream(dirArg + hourOfDay + separator + "output.log");
				TeePrintStream tee = new TeePrintStream(file, System.out);
				System.setOut(tee);
				System.setErr(tee);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int width = 80;
			int height = 30;

			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.setFont(new Font("SansSerif", Font.PLAIN, 24));

			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			DateFormat dateFormat = new SimpleDateFormat("hh:mm");
			graphics.drawString(dateFormat.format(c.getTime()), 10, 20);
			System.out.println(c.getTime());
			System.out.println(" inicio imagen  ");
			for (int y = 0; y < height; y++) {
				StringBuilder sb = new StringBuilder();
				for (int x = 10; x < width; x++) {

					sb.append(image.getRGB(x, y) == -16777216 ? " " : "#");

				}

				if (sb.toString().trim().isEmpty()) {
					continue;
				}

				System.out.println(sb);
			}

			File sourceLoc = new File(dirArg);
			boolean isFolderExisted = FALSE;
			isFolderExisted = (sourceLoc.exists() == TRUE ? sourceLoc
					.isDirectory() == TRUE ? TRUE : FALSE : FALSE);
			if (isFolderExisted == FALSE) {
				System.out.println("     ERROR");
				System.out.println("  +-------------------------+");
				System.out.println("  ! no existe el directorio !");
				System.out.println("  !  " + dirArg);
				System.out.println("  !                         ! ");
				System.out.println("  +-------------------------+ ");
				System.out.println("   ");
				System.out
						.println("Presione la tecla ENTER para cerrar la ventana");
				System.out.println("   ");
				Scanner waitForKeypress = new Scanner(System.in);
				waitForKeypress.nextLine();
			} else {
				try {
					FileWriter statsFile = null;
					Etl worker = new Etl();

					

					System.out
							.println("============================================");

					Scanner scannerNumLeidas = (((new File(dirArg
							+ "num_lines.txt")).exists()) ? (new Scanner(
							new FileReader(dirArg + "num_lines.txt"))) : null);
					Long numLeidas = (scannerNumLeidas != null ? scannerNumLeidas.nextLong() : 0);
					File logFile=new File(logDatos);
					LineNumberReader lnr = new LineNumberReader(new FileReader(
							logFile));
					lnr.skip(Long.MAX_VALUE);

					int lineasSinLeer = (int) ((lnr.getLineNumber() + 1) - numLeidas);
					String data ="";
					System.out.println(lnr.getLineNumber() );
					if(lineasSinLeer>0)
						data = worker.getLastLine(logDatos, lineasSinLeer);
					else{
						System.out.println("no más lineas");
						System.exit(0);
					}
					System.out.println(data);
					List<Line> linesBean = worker.workLines(data);
					for (String hostName : args) {

						Analitics analitics = new Analitics(hostName,
								linesBean, dirArg + hourOfDay, separator);
						analitics.start();

					}
					String lineasN = Long.toString((lnr.getLineNumber() + 1));
					
					
					File fileNum = new File(dirArg + "num_lines.txt");
		            BufferedWriter output = new BufferedWriter(new FileWriter(fileNum));
		            output.write(lineasN);
		            output.close();
		            
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				// pause cada 5 seg
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
