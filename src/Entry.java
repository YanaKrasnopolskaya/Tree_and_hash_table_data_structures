/**
 * Класс Entry для итерации по парам ключ-значение
 */
public class Entry<K, V> {
    public final K key;
    public final V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
