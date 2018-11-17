import java.util.ArrayList;
import java.util.*;


public class Room
{
   private String description, name;
   private HashMap<String, Item> itemsInRoom;
   private HashMap<String, Room> connectedRooms;

   public Room(String name, String description)
   {
      this.name = name;
      this.description = description;
      itemsInRoom = new HashMap<>();
      connectedRooms = new HashMap<>();
   }

   public void addItem(String name, Item item)
   {
      itemsInRoom.put(name, item);
   }

   public void removeItem(String item)
   {
      itemsInRoom.remove(item);
   }

   public Item getItem(String item)
   {
      if(itemsInRoom.containsKey(item))
      {
         return itemsInRoom.get(item);
      }
      else
      return null;
   }

   public Room getDirection(String name)
   {
      return connectedRooms.get(name);
   }

   public void addDirection(String direction, Room room)
   {
      connectedRooms.put(direction, room);
   }

   public String getDescription()
   {
      return description;
   }

   public String getName()
   {
      return name;
   }

   public String getLongDescription()
   {
      return "You are in " + description + "\n You notice the following items in the room: \n" + getItemString();
   }

   public String getItemString()
   {
      if(itemsInRoom.size() == 0)
      {
        return "there are no items in this room";
      }
      else
      {
          String returnString = "Items:";
          Set<String> keys = itemsInRoom.keySet();
          for(String item : keys)
          {
             returnString += " " + item;
          }
             return  returnString;


      }
   }

   public String getDirectionString()
   {
      if(connectedRooms.size() == 0)
      {
        return "There seems to be nowhere to go...";
      }
      else
      {
          String returnString = "Directions:";
          Set<String> keys = connectedRooms.keySet();
          for(String item : keys)
          {
             returnString += ", " + item;
          }
             return  returnString;


      }
   }



}
