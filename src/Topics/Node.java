package Topics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class Node<T> {
    private final T identifier;
    private Collection<Node<T>> connectedTo;

    public Node(T identifier) {
        this.connectedTo = new ArrayList<>();
        this.identifier = identifier;
    }

    public T getIdentifier() {
        return this.identifier;
    }

    public Collection<Node<T>> getNeighborsUnmodifiable() {
        return Collections.unmodifiableCollection(connectedTo);
    }

    public void connectTo(Node<T> node) {
        this.connectTo.add(node);
    }

    public void connectTo(Collection<Node<T>> nodes) {
        nodes.forEach(this::connectTo);
    }

        @Override
        public String toString() {
            var str = this.connectedTo.stream().map(gn -> gn.identifier).collect(Collectors.toList());

            return "Node{" +
                    "Index= " + identifier +
                    "connectedTo= " + str +
                    '}';
        }


}