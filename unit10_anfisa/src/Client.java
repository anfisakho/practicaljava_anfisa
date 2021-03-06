import java.io.*;
import java.net.*;

/* client for StockQoteServer
 * Sends tickets from tickers array
 */

public class Client {
  public static void main(String[] args) {

    String tickers[] = { "EPAM", "LXFT", "AAPL", "IBM", "GOOG" };

    OutputStream outbound = null;
    BufferedReader inbound = null;
    Socket clientSocket = null;
    for (int i = 0; i < tickers.length; i++) {
      try {

        // Open a client socket connection
        clientSocket = new Socket("localhost",  3000);
        System.out.println("Client: " + clientSocket);

        // Get the streams
        outbound = clientSocket.getOutputStream();
        inbound = new BufferedReader(new InputStreamReader(
            clientSocket.getInputStream()));

        // Send stock symbol to the server
        outbound.write((tickers[i] + "\n").getBytes());
        // outbound.write("End\n".getBytes());

        String quote;

        // Delay, in milliseconds before we stop waiting for server reply.
        long patience = 16000;
        long startTime = System.currentTimeMillis();

        while (true) {
          if ((System.currentTimeMillis() - startTime) < patience) {
            quote = inbound.readLine();
            if (quote.equals("End")) {
              break;
            }
            System.out.println("Got the quote for " + tickers[i] + ":" + quote);
          } else {
            System.out.println("Tiered waiting for " + tickers[i]);
            break;
          }
        }
      } catch (UnknownHostException uhe) {
        System.out.println("UnknownHostException: " + uhe);
      } catch (IOException ioe) {
        System.err.println("IOException: " + ioe);
      } finally {
        // Close the streams
        try {
          outbound.close();
          inbound.close();
          clientSocket.close();
        } catch (IOException e) {
          System.out.println("Can not close streams..." + e.getMessage());
        }
      }
    }
  }
}
