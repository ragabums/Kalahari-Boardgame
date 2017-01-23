package kalahariMain;

import kalahariConsoleClient.KalahariConsoleClient;

/**
 * Main: Displays rules or starts game (according to player's choice)
 * 
 * @author Theo Wilhelm, Dec. 2016
 *
 */
public class KalahariComputerMain {

	public static void main(String[] args) {
		KalahariConsoleClient console = new KalahariConsoleClient();
		console.start();
	}

}
