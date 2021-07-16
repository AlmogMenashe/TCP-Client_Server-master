package Handler;
import Topics.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MatrixIHandler extends IHandler {
    private Matrix matrix;
    private Index start,end;
    private boolean doWork = true;



    /*
    to clear data members between clients (if same instance is shared among clients/tasks)
     */
    private void resetParams(){
        this.matrix = null;
        this.start = null;
        this.end = null;
    }

    @Override
    public void handle(InputStream fromClient, OutputStream toClient)
            throws IOException, ClassNotFoundException {

        // In order to read either objects or primitive types we can use ObjectInputStream
        ObjectInputStream objectInputStream = new ObjectInputStream(fromClient);
        // In order to write either objects or primitive types we can use ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(toClient);
        this.resetParams(); // in order to use same handler between tasks/clients

        TraversableMatrix traversableMatrix;
        while(doWork){
            /*
             Use switch-case in order to get commands from client
             - client sends a 2D array
             - client send start index
             - client send end index
             - client sends an index and wished to get neighbors
             - client sends an index and wished to get reachable indices
             */

            // client send a verbal command
            switch(objectInputStream.readObject().toString()){
                case "matrix":{
                    // client will send a 2d array. handler will create a new Topics.Matrix object
                    int[][] primitiveMatrix = (int[][])objectInputStream.readObject();
                    System.out.println("Server: Got 2d array from client");
                    this.matrix = new Matrix(primitiveMatrix);
                    this.matrix.printMatrix();
                    break;
                }

                case "Task1":
                    List<HashSet<Index>> linkedPointsLists = new ArrayList<>();
                    traversableMatrix = new TraversableMatrix(this.matrix);
                    //get all active points
                    List<Index> actives = this.matrix.getAllActivePoints();
                    //run traverse dfs on all active points
                    while (!actives.isEmpty()){
                        traversableMatrix.setStartIndex(actives.get(0));
                        ThreadLocalDfsVisit threadLocalDfsVisit = new ThreadLocalDfsVisit<Index>();
                        //finding all the linked points and add to HashSet
                        List<Index> points = threadLocalDfsVisit.traverse(traversableMatrix,true);
                        linkedPointsLists.add(new HashSet<>(points));
                        //Remove all linked points that found
                        actives = actives.stream().filter(index -> !points.contains(index)).collect(Collectors.toList());
                    }

                    //Sort by size
                    linkedPointsLists = linkedPointsLists.stream().sorted(Comparator.comparingInt(HashSet::size)).collect(Collectors.toList());

                    //return to client
                    objectOutputStream.writeObject(linkedPointsLists);
                    break;

                case "Task2":
                    Index source = (Index)objectInputStream.readObject();
                    Index dest = (Index)objectInputStream.readObject();
                    if(matrix.getValue(dest)==0 ||matrix.getValue(source)==0)
                    {
                        System.out.println("Give me index with value 1!");
                        break;
                    }
                    TraversableMatrix traversableMatrix = new TraversableMatrix(this.matrix);
                    HashSet<HashSet<Index>> SetConnectComponents = new HashSet<>();
                    traversableMatrix.startIndex = source;
                    SetConnectComponents.add((HashSet<Index>) DFSvisit.travers(traversableMatrix));//not localthread
                    if(SetConnectComponents.contains(dest)){

                    }
                    System.out.println("Dijkstra returned:"+ SetConnectComponents);
                    break;
            }




/*
                case "getShortestPath":
                    traversableMatrix = new Topics.TraversableMatrix(this.matrix);
                    Topics.Index source  = (Topics.Index)objectInputStream.readObject();
                    Topics.Index dest = (Topics.Index)objectInputStream.readObject();
                    Topics.DFSvisit<Topics.Index> bfsVisit = new Topics.DFSvisit<>();
                    Collection<Collection<Topics.Index>> path = Topics.DFSvisit.travers(traversableMatrix,new Topics.Node(source),new Topics.Node(dest));

                    //return to client
                    objectOutputStream.writeObject(path);
                    break;
*/


//                case "neighbors":{
//                    Topics.Index findNeighborsIndex = (Topics.Index)objectInputStream.readObject();
//                    List<Topics.Index> neighbors = new ArrayList<>();
//                    if(this.matrix!=null){
//                        neighbors.addAll(this.matrix.getNeighbors(findNeighborsIndex,false));
//                        // print result in server
//                        System.out.println("neighbors of " + findNeighborsIndex + ": " + neighbors);
//                        // send to socket's OutputStream
//                        objectOutputStream.writeObject(neighbors);
//                    }
//                    break;
//                }

//                case "reachables":{
//                    Topics.Index findNeighborsIndex = (Topics.Index)objectInputStream.readObject();
//                    List<Topics.Index> reachables = new ArrayList<>();
//                    if(this.matrix!=null){
//                        reachables.addAll(this.matrix.getReachables(findNeighborsIndex));
//                        // print result in server
//                        System.out.println("reachables of " + findNeighborsIndex + ": " + reachables);
//                        // send to socket's OutputStream
//                        objectOutputStream.writeObject(reachables);
//                    }
//                    break;
//                }

                case "start index":{
                    this.start = (Index)objectInputStream.readObject();
                    break;
                }

                case "end index":{
                    this.end = (Index)objectInputStream.readObject();
                    break;
                }

                case "stop":{
                    doWork = false;
                    break;
                }
            }
        }
    }
}