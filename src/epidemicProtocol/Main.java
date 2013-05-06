package epidemicProtocol;

import epidemicProtocol.clients.BroadcastClient;
import epidemicProtocol.clients.MessagesSender;
import epidemicProtocol.control.TargetsList;
import epidemicProtocol.servers.SpreadServer;
import epidemicProtocol.servers.TargetsListServer;

/**
 * Class that have de main method of this project. It starts the 4 initial threads of the
 * application: {@link epidemicProtocol.servers.TargetsListServer}, {@link epidemicProtocol.clients.BroadcastClient},
 * {@link epidemicProtocol.servers.SpreadServer} and
 * {@link epidemicProtocol.clients.MessagesSender}.
 *
 * @author Lucas S Bueno
 */
public class Main {

   /**
    * Port that will be used to discover other computers in the local network.
    */
   private static final int TARGETS_LIST_PORT = 60000;
   /**
    * Port that will be used to send messages from one computer to other.
    */
   private static final int SPREAD_SERVER_PORT = 60001;

   /**
    * Entry point of the application. Starts some threads and show some messages in the output.
    *
    * @param args Args in this main fuction are not used.
    */
   public static void main(String[] args) {
      TargetsList targetsList = new TargetsList();
      System.out.println("Starting Targets List Sever...");
      new TargetsListServer(targetsList, TARGETS_LIST_PORT).start();
      System.out.println("Starting Broadcast Client...");
      new BroadcastClient(TARGETS_LIST_PORT).start();
      System.out.println("Starting Spread Server...");
      new SpreadServer(targetsList, SPREAD_SERVER_PORT).start();
      System.out.println("Starting Messages Sender...");
      new MessagesSender(SPREAD_SERVER_PORT).start();
      System.out.println("Starting Epidemic Protocol...");
   }
}
