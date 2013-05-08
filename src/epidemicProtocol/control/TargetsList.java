package epidemicProtocol.control;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Structure that store a list of local computers addresses. These address are found with the broadcast
 * messages (sent with {@link epidemicProtocol.clients.BroadcastClient}) received in
 * {@link epidemicProtocol.servers.TargetsListServer}. This list is used to spread the messages
 * received in the {@link epidemicProtocol.servers.SpreadServer}.
 *
 * @author Lucas S Bueno
 */
public class TargetsList {

   private List<String> addressesList;
   private String localHost;
   /**
    * variable used as index to get a address from the addressesList. It is being added in a way to
    * make a circular rotation in the list.
    */
   private int auxCount;

   public TargetsList() {
      addressesList = new ArrayList<>();
      auxCount = 0;

      try {
         localHost = InetAddress.getLocalHost().getHostAddress();
      } catch (Exception ex) {
         System.err.println("Error: " + ex.getMessage());
      }
   }

   /**
    * Try to add a address in the addressesList. If the address being added is equal to the
    * localHost address, it don't need to be added.
    *
    * @param address Some address to be added to the addressesList.
    * @return True if the address was added to the addressesList successfully.
    */
   public boolean tryToAdd(String address) {
      synchronized (this) {
         if ((!addressesList.contains(address)) && (!address.equals(localHost))) {
            return addressesList.add(address);
         }
         
         return false;
      }
   }

   /**
    * Try to remove the address passed in the param from the addressesList.
    *
    * @param address address to be removed.
    * @return True if the address was removed successfully.
    */
   public boolean tryToRemove(String address) {
      synchronized (this) {
         return addressesList.remove(address);
      }
   }

   /**
    * Return one String address. It uses the auxCount attribute to make a circular rotation with the
    * addresses stored.
    *
    * @return One of the addresses of the addressList attribute.
    */
   public String getOneAddress() {
      synchronized (this) {
         int listSize = addressesList.size();

         if (listSize <= auxCount) {
            auxCount = 0;
         }

         if (listSize != 0) {
            String oneAddress = addressesList.get(auxCount);
            auxCount = (auxCount + 1) % listSize;

            return oneAddress;
         }

         return null;
      }
   }
}