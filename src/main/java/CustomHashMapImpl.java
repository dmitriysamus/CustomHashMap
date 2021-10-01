import java.util.Objects;

public class CustomHashMapImpl <K, V> implements CustomHashMap {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            }
    }

    private final int DEFAULT_CAPACITY = 16;
    private Node<K, V> table[] = new Node[DEFAULT_CAPACITY];
    private int size;
    private int capacity;

    /**
     * Пустой конструктор объекта с capacity по умолчания.
     */
    CustomHashMapImpl() {
        this.capacity = DEFAULT_CAPACITY;
    }

    /**
     * Конструктор объекта, принимающий на вход capacity.
     */
    public CustomHashMapImpl(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Возвращает размер словаря.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет словарь на наличие объектов.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object get(Object key) {
        if (null == key && table[0] != null) {
            return null;
        }

        Node<K, V> tmp = table[getBucketId((K) key)];
        while (tmp != null) {
            if (getItemHash(tmp.key) == getItemHash((K)key) && Objects.equals(tmp.key, key)) {
                return tmp.value;
            }
            tmp = tmp.next;
        }

        return null;
    }

    /**
     * Добавляет объект в словарь.
     */
    @Override
    public Object put(Object key, Object value) {

        int bucketId = getBucketId((K)key);

        Node<K, V> tmp = table[bucketId];
        Node<K, V> prev = table[bucketId];

        while (tmp != null) {

            if (getItemHash(tmp.key) == getItemHash((K)key) && Objects.equals(tmp.key, key)) {
                V oldValue = tmp.value;
                tmp.value = (V)value;

                return oldValue;
            }

            prev = tmp;
            tmp = tmp.next;
        }

        Node<K, V> insertedNode = new Node<K, V> ((K)key, (V)value);
        if (prev == null) {
            table[bucketId] = insertedNode;
            size++;
        } else {
            prev.next = insertedNode;
            size++;
        }

        return null;
    }

    /**
     * Возвращает ID бакета по ключу.
     */
    private int getBucketId(K key) {
        if (key == null) {
            return 0;
        }
        return 1 + key.hashCode() % (table.length - 1);
    }

    /**
     * Возвращает hash объекта.
     */
    private int getItemHash(K item) {
        if (item == null) {
            return 0;
        }
        return item.hashCode();
    }

    /**
     * Удаляет объект из словаря.
     */
    @Override
    public Object remove(Object key) {
        if (!containsKey(key)) {
            return null;
        }

        if (key == null && table[0] != null) {
            table[0] = null;
            return null;
        }

        Node<K, V> tmp = table[getBucketId((K) key)];
        Node<K, V> tmpPrev = null;

        while (tmp != null) {
            if (getItemHash(tmp.key) == getItemHash((K)key) && Objects.equals(tmp.key, key)) {
                break;
            }
            tmpPrev = tmp;
            tmp = tmp.next;
        }

        Node<K, V> result = tmp;
        if (tmpPrev == null) {
            table[getBucketId((K) key)] = tmp.next;
        } else {
            tmpPrev.next = tmp.next;
        }
        size--;

        return result.value;
    }

    /**
     * Проверяет наличие ключа объекта в словаре.
     */
    @Override
    public boolean containsKey(Object key) {
        if (key == null && table[0] != null) {
            return true;
        }
        return get(key) != null;
    }

    /**
     * Проверяет наличия значения объекта в словаре.
     */
    @Override
    public boolean containsValue(Object value) {
        if (null == value && table[0] != null) {
            return true;
        }

        for (int i = 0; i < table.length; ++i) {
            Node<K, V> tmp = table[i];
            while (tmp != null) {
                if (Objects.equals(tmp.value, value)) {
                    return true;
                }
                tmp = tmp.next;
            }
        }

        return false;
    }

    /**
     * Возврящает множество всех ключей словаря.
     */
    @Override
    public Object[] keys() {
        Object[] keys = new Object[size];

        int j = 0;
        for (int i = 0; i < table.length; ++i) {
            Node<K, V> tmp = table[i];
            while (tmp != null) {
                keys[j++] = tmp.key;
                tmp = tmp.next;
            }
        }

        return keys;
    }

    /**
     * Возврящает множество всех значений словаря.
     */
    @Override
    public Object[] values() {
        Object[] values = new Object[size];

        int j = 0;
        for (int i = 0; i < table.length; ++i) {
            Node<K, V> tmp = table[i];
            while (tmp != null) {
                values[j++] = tmp.value;
                tmp = tmp.next;
            }
        }

        return values;
    }

    /**
     * Возвращает строковое представление дерева.
     */
    @Override
    public String toString() {
        StringBuilder cb = new StringBuilder();

        cb.append("[ ");

        for (int i = 0; i < table.length; ++i) {

            Node<K, V> tmp = table[i];
            while (tmp != null) {

                cb.append("{ key=" + tmp.key + ";value=" + tmp.value + " } ");
                tmp = tmp.next;
            }
        }

        cb.append("]");
        return cb.toString();
    }
}
