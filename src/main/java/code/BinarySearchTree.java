package code;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// attention: Comparable is supported but Comparator is not
public class BinarySearchTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private Node<T> root;
    private int size;
    private boolean first;

    public BinarySearchTree(boolean first) {
        this.root = null;
        this.first = first;
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    /**
     * Добавление элемента в дерево
     * <p>
     * Если элемента нет в множестве, функция добавляет его в дерево и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * <p>
     * Спецификация: {@link Set#add(Object)} (Ctrl+Click по add)
     * <p>
     * Пример
     */
    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
            closest.left.parent = closest;
        } else {
            assert closest.right == null;
            closest.right = newNode;
            closest.right.parent = closest;
        }
        size++;
        return true;
    }

    /**
     * Удаление элемента из дерева
     * <p>
     * Если элемент есть в множестве, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     * <p>
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     * <p>
     * Средняя
     */
    //временные затраты: O(N) (проходим по бинарному дереву, худший случай - вырожденное дерево)
    //затраты памяти: O(1) (пройденные узлы нигде не хранятся)
    @Override
    public boolean remove(Object o) {
        Node<T> current = root;
        @SuppressWarnings("unchecked")
        T value = (T) o;
        if (current == null) return false;
        while (value.compareTo(current.value) != 0) {
            if (value.compareTo(current.value) < 0) current = current.left;
            else current = current.right;
            if (current == null) return false;
        }
        if (current.left == null && current.right == null) {//случай, когда у удаляемого узла нет потомков
            if (root == current) root = null;
            else if (current.parent.left == current) current.parent.left = null;
            else current.parent.right = null;
        } else if (current.right == null) {
            if (root == current) root = current.left;
            else if (current.parent.left == current) current.parent.left = current.left;
            else current.parent.right = current.left;
        } else if (current.left == null)
            if (root == current) root = current.right;
            else if (current.parent.left == current) current.parent.left = current.right;
            else current.parent.right = current.right;
        else {   //случай, когда у удаляемого узла есть оба потомка
            Node<T> heir = findHeir(current.right); //находим преемника
            if (current == root) root = heir;
            else if (current.parent.left == current) current.parent.left = heir;
            else current.parent.right = heir;
            heir.left = current.left;
        }
        size--;
        return true;
    }

    public Node<T> findHeir(Node<T> newRoot) { //функция поиска преемника для случая с двумя потомками
        //преемником является либо правый потомок удаляемого узла (newRoot),
        //либо один из его левых потомков
        Node<T> heirParent = null;
        Node<T> heir = null;
        Node<T> currentNode = newRoot;
        while (currentNode != null) {
            heirParent = heir;
            heir = currentNode;
            currentNode = currentNode.left;
        }
        if (heir != newRoot) {
            heirParent.left = heir.right;
            heir.right = newRoot;
        }
        return heir;
    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }


    /**
     * Подмножество всех элементов в диапазоне [fromElement, toElement)
     * <p>
     * Функция возвращает множество, содержащее в себе все элементы дерева, которые
     * больше или равны fromElement и строго меньше toElement.
     * При равенстве fromElement и toElement возвращается пустое множество.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     * <p>
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     * <p>
     * Спецификация: {@link SortedSet#subSet(Object, Object)} (Ctrl+Click по subSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     * <p>
     * Очень сложная (в том случае, если спецификация реализуется в полном объёме)
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new IllegalStateException();
    }

    /**
     * Подмножество всех элементов строго меньше заданного
     * <p>
     * Функция возвращает множество, содержащее в себе все элементы дерева строго меньше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     * <p>
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     * <p>
     * Спецификация: {@link SortedSet#headSet(Object)} (Ctrl+Click по headSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new IllegalStateException();
    }

    /**
     * Подмножество всех элементов нестрого больше заданного
     * <p>
     * Функция возвращает множество, содержащее в себе все элементы дерева нестрого больше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     * <p>
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     * <p>
     * Спецификация: {@link SortedSet#tailSet(Object)} (Ctrl+Click по tailSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new IllegalStateException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }



    private static class Node<T> {
        final T value;
        Node<T> parent = null;
        Node<T> left = null;
        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        if (first)
            return new BinarySearchTreeIteratorFirst();
        else return new BinarySearchTreeIteratorSecond();
    }

    public class BinarySearchTreeIteratorFirst implements Iterator<T> {
        private final Stack<Node<T>> stack = new Stack<>();

        private BinarySearchTreeIteratorFirst() {
            if (root != null) {
                stack.push(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        //временные затраты: O(1)
        @Override
        public T next() {
            if (stack.isEmpty())
                throw new NoSuchElementException();
            Node<T> current = stack.pop();
            if (current.right != null)
                stack.push(current.right);
            if (current.left != null)
                stack.push(current.left);
            return current.value;
        }

        @Override
        public void remove() {
            throw new IllegalStateException();
        }

    }

    public class BinarySearchTreeIteratorSecond implements Iterator<T> {
        private final Integer initialSize;
        private Node<T> lastNode;
        private Node<T> checkpoint; //чтобы не терять next() при удалении
        private int nodesPassed;

        private BinarySearchTreeIteratorSecond() {
            nodesPassed = 0;
            initialSize = size();
            if (root == null)
                return;
            checkpoint = findSmallestNode(root);
        }

        public Node<T> findSmallestNode(Node<T> root) {
            if (root.left != null) return findSmallestNode(root.left);
            else return root;
        }

        public Node<T> findNextSmallestParent(Node<T> node) {
            if (node == node.parent.right) return findNextSmallestParent(node.parent);
            else return node.parent;
        }

        //временные затраты: O(1)
        //затраты памяти: O(1)
        @Override
        public boolean hasNext() {
            return nodesPassed < initialSize;
        }

        //временные затраты: O(1)
        //затраты памяти: O(1)
        @Override
        public T next() {
            if (nodesPassed == initialSize || initialSize == 0) throw new NoSuchElementException();
            if (nodesPassed == 0) { //когда перебор только начался
                nodesPassed++;
                lastNode = checkpoint;
                return checkpoint.value;   //везде возвращаем checkpoint, чтоыб не терять next при удалении
            }
            nodesPassed++;
            if (checkpoint.right != null) { // следующий минимальный узел - самый левый лист правого потомка
                lastNode = findSmallestNode(checkpoint.right);
                checkpoint = lastNode;
                return checkpoint.value;
            } else if (nodesPassed != initialSize) checkpoint = findNextSmallestParent(checkpoint);
            //следующий минимальный узел - следующий наименьший предок в дереве
            lastNode = checkpoint;
            return checkpoint.value;
        }

        /**
         * Проверка наличия следующего элемента
         * <p>
         * Функция возвращает true, если итерация по множеству ещё не окончена (то есть, если вызов next() вернёт
         * следующий элемент множества, а не бросит исключение); иначе возвращает false.
         * <p>
         * Спецификация: {@link Iterator#hasNext()} (Ctrl+Click по hasNext)
         * <p>
         * Средняя
         */

        /**
         * Получение следующего элемента
         * <p>
         * Функция возвращает следующий элемент множества.
         * Так как BinarySearchTree реализует интерфейс SortedSet, последовательные
         * вызовы next() должны возвращать элементы в порядке возрастания.
         * <p>
         * Бросает NoSuchElementException, если все элементы уже были возвращены.
         * <p>
         * Спецификация: {@link Iterator#next()} (Ctrl+Click по next)
         * <p>
         * Средняя
         */

        /**
         * Удаление предыдущего элемента
         * <p>
         * Функция удаляет из множества элемент, возвращённый крайним вызовом функции next().
         * <p>
         * Бросает IllegalStateException, если функция была вызвана до первого вызова next() или же была вызвана
         * более одного раза после любого вызова next().
         * <p>
         * Спецификация: {@link Iterator#remove()} (Ctrl+Click по remove)
         * <p>
         * Сложная
         */
        //временные затраты: O(N) (аналогично функции remove(Object))
        //затраты памяти: O(1) (аналогично функции remove(Object))
        @Override
        public void remove() {
            if (lastNode == null) throw new IllegalStateException();
            BinarySearchTree.this.remove(lastNode.value);
            lastNode = null;
        }

    }

}