
public class test {
    public static void main(String[] args) {
        String name = "Virendra";
        String course = "SQLIte";
        
//        String full = "My Name is " + name + " this is onother videp on " + course;
        String full = String.format("My name is %s this is another vedio on %s ",name , course );
    System.out.println(full);
    }
}
