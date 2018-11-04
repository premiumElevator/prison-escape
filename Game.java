public class Game
{
   private Room currentRoom;

   private Player player1;

   public Game(String name)
   {
      player1 = new Player(name);
      Item hammer = new Item("hammer", "it's a hammer. I should probably wrap it up in something so it doesn't make too much noise.", true);
      Item note = new Item("note from friend", "The note reads: 'Hey friend, behind the BIG BOX of obstacles in your life, you will find freedom' ", true);

      player1.addItem("hammer", hammer);
      player1.addItem("note", note);


      createRooms();

   }


   public void createRooms()
   {
      Room cell = new Room("My cell room. Contains a sink and a bed with a dusty cover");
      Room pipeRoom = new Room("This looks like the room with all the pipes that deliver water to the cells. It looks like I can shimmy down this pipe to the basement, but the pipe might be too hot.");
      Room basement = new Room("There is an oddly misplaced box in the corner of the room. Wait, what did my note say again...");
      Room courtYard = new Room("The courtyard. I'm almost free, just gotta get past these gate bars...Maybe I can bend them...");
      Room outside = new Room("I'm outside, I'm FINALLY FREE!!!");
      currentRoom = cell;

      Item blanket = new Item("blanket", "it's a dusty blanket", true);
      Item sink = new Item("sink", "it's a sink", false);

      cell.addItem("blanket", blanket);
      cell.addItem("sink", sink);

      Item hotPipe = new Item("hot water pipe", "it's a hot water pipe, I can't shimmy down this pipe unless I somehow find a way to cool it", false);
      pipeRoom.addItem("hot water pipe", hotPipe);

      Item box = new Item("big box", "it's a big box that seems out of place...maybe I can move it", false);
      basement.addItem("big box", box);

      Item bars = new Item("fence bars", "fence bars keeping me away from sweet freedom. I might be able to bend them with something I have...", false);
      courtYard.addItem("fence bars", bars);


   }

   public void takeItem(String name)
   {
      if(currentRoom.getItem(name) != null && currentRoom.getItem(name).getCanTake()==true){
         player1.addItem(currentRoom.getItem(name).getName(), currentRoom.getItem(name));
         currentRoom.removeItem(name);
         System.out.println("added " + name + " to your inventory.");
        }
      else
         System.out.println("can't do that");
   }

   public void changeRoom(Room room)
   {
      currentRoom = room;
   }

}
