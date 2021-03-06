package Useless;

import Topics.Index;
import Topics.Node;

import java.util.Collection;

/**
 * This interface defines the functionality required for a traversable graph
 */
public interface Traversable<T> {
    public Node<T> getOrigin();
    public int getValue(T index);
    //todo: check about includeDiagonal
    public Collection<Node<T>>  getReachableNodes(Node<T> someNode,boolean includeDiagonal);
    public Collection<Node<Index>> getNeighborNodes(Node<Index> someNode, boolean includeDiagonal);

    Collection<Node<Index>> getReachableNodes2(Node<Index> someNode);
}