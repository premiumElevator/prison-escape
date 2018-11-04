public class Item
{
   private String name, description;

   private boolean canTake;

   public Item(String name, String description, boolean canTake)
   {
      this.name = name;
      this.description = description;
      this.canTake = canTake;

   }

   public String getName()
   {
      return name;
   }

   public String getDescription()
   {
      return description;
   }

   public boolean getCanTake()
   {
      return canTake;
   }
}
