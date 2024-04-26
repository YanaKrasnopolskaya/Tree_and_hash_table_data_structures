import java.util.*;

public class HashMap<K, V> implements Iterable<Entry<K, V>>  {

    private static final int INIT_BUCKET_COUNT = 16;

    private static final double LOAD_FACTOR = 0.5;

    private Bucket[] buckets;
        /**
         * Истинное количество элементов в хэш-таблице
         */
    private int size;

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<Entry<K, V>> {
        private int currentIndex = 0;
        private Entry<K, V>[] entries;

        public HashMapIterator() {
            entries = new Entry[buckets.length];
            for (int i = 0; i < buckets.length; i++) {
                Bucket bucket = buckets[i];
                if (bucket != null) {
                    Bucket.Node node = bucket.head;
                    while (node != null) {
                        entries[currentIndex++] = new Entry<>(node.value.key, node.value.value);
                        node = node.next;
                    }
                }
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex > 0;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements to iterate");
            }
            return entries[--currentIndex];
        }
    }

    /**
     * Елемент хеш-таблицы
     */
    class Entity{

        /**
         * Ключ
         */
        K key;

        /**
         * Значение
         */
        V value;
    }
    /**
     * Баккет(связный список)
     */
    class Bucket{
        /**
         * Указатель на первый элемент связного списка
         */
        Node head;
        /**
         * Узел баккета(связного списка)
         */
        class Node{

            /**
             * Указатель на следующий элемент связного списка
             */
            Node next;

            /**
             * Значение узла, указывающего на элемент хеш-таблицы
             */
            Entity value;
        }

        public V add(Entity entity){
            Node node = new Node();
            node.value = entity;

            if(head == null){
                head = node;
                return null;
            }

            Node currentNode = head;
            while (true) {
                if (currentNode.value.key.equals((entity.key))) {
                    V buf = currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }

                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                } else {
                    currentNode.next = node;
                    return null;
                }
            }
        }

        public V remove(K key){
            if(head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = head.value.value;
                head = head.next;
                return buf;
            }
            else {
                Node node = head;
                while (node.next != null){
                    if (node.next.value.key.equals(key)){
                        V buf = node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }

        public V get(K key){
            Node node = head;
            while (node != null){
                if (node.value.key.equals(key))
                    return  node.value.value;
                node = node.next;
            }
            return  null;
        }
    }

    private  int calculateBuckket(K key){
        return Math.abs(key.hashCode()) % buckets.length;
    }

    private  void recalculate(){
        size = 0;
        Bucket[] old = buckets;
        buckets = new HashMap.Bucket[old.length * 2];
        for (int i = 0; i < old.length; i++){
            Bucket bucket = old[i];
            if (bucket != null){
              Bucket.Node node = bucket.head;
              while (node != null){
                  put(node.value.key, node.value.value);
                  node = node.next;
              }
            }
        }
    }

    public V put(K key, V value){

        if(size >= buckets.length * LOAD_FACTOR){
            recalculate();
        }

        int index = calculateBuckket(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V buf = bucket.add(entity);
        if (buf == null){
            size++;
        }
        return buf;
    }

    public V get(K key){
        int index = calculateBuckket(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        return bucket.get(key);
    }

    public V remove(K key){
        int index = calculateBuckket(key);
        Bucket bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        V buf = bucket.remove(key);
        if (buf != null){
            size--;
        }
        return buf;
    }

    public HashMap() {
        buckets = new HashMap.Bucket[INIT_BUCKET_COUNT];
    }

    public HashMap(int initCount){
        buckets = new HashMap.Bucket[initCount];
    }
}


