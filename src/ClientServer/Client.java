package ClientServer;

import Handler.TaskType;
import Handler.Request;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        client.TORequest(Client.Task1);
        client.TORequest(Client.Task2);
        client.TORequest(Client.Task3);
        client.TORequest(Client.Task4);
    }

    public void TORequest(Request request) throws IOException, ClassNotFoundException {
        Scanner scanner= new Scanner(System.in);
        Socket socket = new Socket("127.0.0.1", 8010);
        System.out.println("client: Created Socket\n");

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        System.out.println("Started communication with the server");

        //use decorators
        ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
        ObjectInputStream fromServer = new ObjectInputStream(inputStream);

        request.execute(fromServer, toServer);

        //Closing socket when finished:
        fromServer.close();
        toServer.close();
        socket.close();
        System.out.println("Connection closed :) ");
    }

    //Task 1 - Find all groups with index 1 (with the diagonals)
    static Request Task1 = (ObjectInputStream fromServer, ObjectOutputStream toServer) -> {
        toServer.writeObject(TaskType.Task_1);
        int[][] matrix = {
                {1, 0, 0},
                {1, 0, 1},
                {0, 1, 1},
        };
        toServer.writeObject(matrix);
        var result = fromServer.readObject();
        System.out.println("ClientServer.Client side print :");
        System.out.println(result);
    };

    //Task 2 - Find the shortest routes from source to destination
    static Request Task2 = (ObjectInputStream fromServer, ObjectOutputStream toServer) -> {
        toServer.writeObject(TaskType.Task_2);
        int[][] matrix = {
                {1, 0, 1, 1, 1},
                {1, 0, 0, 1, 1},
                {0, 0, 0, 1, 1},
        };
//        toServer.writeObject(matrix);
//        boolean result = (boolean) fromServer.readObject();
//        if (result) {
//            Integer numOfSubmarines = (Integer) fromServer.readObject();
//            System.out.println("Valid game with " + numOfSubmarines + " valid submarines");
//        } else {
//            System.out.println("Submarine game input was not a valid input");
//        }
    };

    static Request Task3 = (ObjectInputStream fromServer, ObjectOutputStream toServer) -> {
        toServer.writeObject(TaskType.Task_3);
        int[][] matrix = {
                {1, 0, 0, 1},
                {1, 0, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 0, 1},
        };
//        toServer.writeObject(matrix);
//        toServer.writeObject(new Index(0, 0));
//        toServer.writeObject(new Index(1, 3));
//        var validationResult = (boolean) fromServer.readObject();
//        if (!validationResult) {
//            System.out.println("Input validation has failed...");
//        } else {
//            var result = (List<List<Index>>) fromServer.readObject();
//            if (result != null) {
//                System.out.println("ClientServer.Client side print of shortest paths ");
//                result.forEach((path) -> {
//                    System.out.println(path);
//                });
//            } else {
//                System.out.println("A path between vertices could not be found :(");
//            }
//        }
    };

    static Request Task4 = (ObjectInputStream fromServer, ObjectOutputStream toServer) -> {
        toServer.writeObject(TaskType.Task_4);
        int[][] matrix = {
                {1, 0, 0},
                {1, 0, 1},
                {0, 1, 1},
        };
//        toServer.writeObject(matrix);
//        var result = fromServer.readObject();
//        System.out.println("ClientServer.Client side print :");
//        System.out.println(result);
    };

    /*
        //Task 1 - Find all groups with index 1 (with the diagonals)
        toServer.writeObject("Task1");
        System.out.println("Client finished Task 1\n");

        List<HashSet<Index>> linkedPoints =
               new ArrayList<HashSet<Index>>((List<HashSet<Index>>) fromServer.readObject());
        //todo: change prints
        for (HashSet<Index> set:linkedPoints){
            for (Index index : set){
                System.out.print(index.toString());
            }
            System.out.print(",");
        }
        toServer.writeObject(destinationIndex);
        System.out.println("Client finished Task 2\n");

/*
        //Task 2 - Find the shortest routes from source to destination

        toServer.writeObject("Task2");
        toServer.writeObject(new Topics.Index(0, 0));
        System.out.println(sourceIndex);
        toServer.writeObject(new Topics.Index(1, 2));
        toServer.writeObject(sourceIndex);

        List<Topics.Index> reachables =
                new ArrayList<Topics.Index>((List<Topics.Index>) fromServer.readObject());
        Collection<List<Topics.Index>> paths = (Collection<List<Topics.Index>>) fromServer.readObject();
        for(List<Topics.Index> path : paths){
            System.out.println("from client - Shortest path are:  " + path);
        }
*/

        // get neighboring indices as list
//        List<Topics.Index> AdjacentIndices =
//                new ArrayList<Topics.Index>((List<Topics.Index>) fromServer.readObject());
//        System.out.println("from client - Neighboring Indices are: "+ AdjacentIndices);

        //send "reachables" command then write an index to socket
//        toServer.writeObject("reachables");
//        toServer.writeObject(new Topics.Index(1,1));

        // get reachable indices as list
//        List<Topics.Index> reachables =
//                new ArrayList<Topics.Index>((List<Topics.Index>) fromServer.readObject());
//        System.out.println("from client - Reachable Indices are:  "+ reachables);


}
