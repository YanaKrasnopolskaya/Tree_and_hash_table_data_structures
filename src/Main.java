// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Employee employee1 = new Employee("AAAA", 33);
        System.out.println(employee1.hashCode());
        Employee employee2 = new Employee("BBBB", 41);
        System.out.println(employee2.hashCode());

        HashMap<String, String> hashMap1 = new HashMap<>(4);

        String oldValue;
        oldValue = hashMap1.put("+7827567824892", "Ivan");
        oldValue = hashMap1.put("+78279765892", "Yana");
        oldValue = hashMap1.put("+78275892", "Max");
        oldValue = hashMap1.put("+782758934252", "Alise");
        oldValue = hashMap1.put("+78271115892", "Den");
        oldValue = hashMap1.put("+782758575492", "Nina");

        String res = hashMap1.get("+78275892");
        res = hashMap1.get("+78275811111111192");

        oldValue = hashMap1.remove("+7827567824892");
        oldValue = hashMap1.remove("+7827567824892");

        for (Entry<String, String> element: hashMap1) {
            System.out.println("{" + element.key + " : " + element.value + "}");
        }
    }


}