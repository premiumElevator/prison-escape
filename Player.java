import java.util.HashMap;

public class Player
{
   private String name;
   private int stealthMeter;
   private HashMap<String, Item> inventory;



   public Player(String name)
   {
      this.name = name;
      this.stealthMeter = 10;
      inventory = new HashMap<>();
   }

   public int getStealthMeter()
   {
      return stealthMeter;
   }

   public void addItem(String itemName, Item item)
   {
      inventory.put(itemName, item);
   }

   public void removeItem(String itemName)
   {
      inventory.remove(itemName);
   }


   public void lostStealth()
   {
      stealthMeter--;
   }

   public String inspectItem(String item)
   {
      if(inventory.containsKey(item))
         return inventory.get(item).getDescription();
      else
         return "no such item";
   }
   public Item getItem(String item)
   {
      return inventory.get(item);
   }
}
