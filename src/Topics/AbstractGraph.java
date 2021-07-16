package Topics;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public abstract class AbstractGraph<T> {
	protected HashMap<T, Node<T>> nodes;

	public AbstractGraph() {
		nodes = new HashMap<>();
	}

	public Collection<Node<T>> getAllVertexesUnmodifiable() {
		return Collections.unmodifiableCollection(this.nodes.values());
	}

	public Node<T> getGraphNode(T identifier) {
		return this.nodes.get(identifier);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		nodes.values().forEach(graphNode -> builder.append(graphNode.toString() + "\n"));
		return "Graph{ \n" +
				builder.toString()
				+ '}';
	}
}
