package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else {
            if (key.compareTo(p.key) > 0) {
                return getHelper(key, p.right);
            } else {
                return getHelper(key, p.left);
            }
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            p = new Node(key, value);
            return p;
        }
        if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> kset = new HashSet<>();
        keySetHelper(kset, root);
        return kset;
    }
    private void keySetHelper(Set<K> kset, Node p) {
        if (p != null) {
            kset.add(p.key);
            keySetHelper(kset, p.left);
            keySetHelper(kset, p.right);
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    private Node findRightMin(Node p) {
        if (p.left == null) {
            return p;
        } else {
            return findRightMin(p.left);
        }
    }
    private V removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            V returnVal = p.value;
            if (p.left == null && p.right == null) {
                size--;
                p = null;
                return returnVal;
            } else {
                if (p.left != null && p.right != null) {
                    Node tmp = findRightMin(p.right);
                    p.key = tmp.key;
                    p.value = tmp.value;
                    tmp = null;
                    size--;
                    return returnVal;
                } else {
                    if (p.left != null) {
                        p = p.left;
                    }
                    if (p.right != null) {
                        p = p.right;
                    }
                    size--;
                    return returnVal;
                }
            }

        } else {
            if (key.compareTo(p.key) > 0) {
                return removeHelper(key, p.right);
            } else {
                return removeHelper(key, p.left);
            }
        }
    }
    @Override
    public V remove(K key) {
        return removeHelper(key, root);
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    private V removeHelper(K key, V value, Node p) {
        if (root == null) {
            return null;
        }
        if (key.compareTo(root.key) == 0 && value != p.value) {
            return null;
        }
        if (key.compareTo(root.key) == 0 && value == p.value) {
            V returnVal = root.value;
            if (p.left == null && p.right == null) {
                size--;
                p = null;
                return returnVal;
            } else {
                if (p.left != null && p.right != null) {
                    Node tmp = findRightMin(p.right);
                    p.key = tmp.key;
                    p.value = tmp.value;
                    tmp = null;
                    size--;
                    return returnVal;
                } else {
                    if (p.left != null) {
                        p = p.left;
                    }
                    if (p.right != null) {
                        p = p.right;
                    }
                    size--;
                    return returnVal;
                }
            }
        } else {
            if (key.compareTo(root.key) > 0) {
                return removeHelper(key, value, root.right);
            } else {
                return removeHelper(key, value, root.left);
            }
        }
    }
    @Override
    public V remove(K key, V value) {
        return removeHelper(key, value, root);
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
