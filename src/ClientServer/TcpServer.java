package ClientServer;

import Handler.IHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;


public class TcpServer {
    /*
    Motivation: 2 running programs on remote hosts wish to communicate with each other over a network connection
    How is it done?
    Application layer only translates packets to meaningful data. How is it sent?

    Transport layer:
    End-to-end Connection between hosts for applications.
    It provides services such as:
    connection-oriented communication, Data integrity and Error correction, flow and congestion control

    Most importantly:
    1. Process to process delivery:
    A program wishes to send messages to and receive messages from the program on the other end.
    an instance of a running program is represented by a process, for which resources
    are allocated by the operating system

   application layer believe processes on hosts are connected by a data stream

    multiplexing -  of different applications over a network which is running on a host.
    The transport layer enables sending packet streams from various applications simultaneously over a network.

    A socket is an endpoint for communication between two machines.
    In application layer, each running program is represented by a process that executes
    operations
    There are 2 kinds of sockets:
    1. Server Socket - listen and accept incoming connection
    2. Operational Socket (known as a ClientServer.Client Socket) - read from/write to a data stream

    Each socket is associated with a transport protocol (TCP/UDP) and local information (ip and port).
    Port is used to distinguish between different types of data transmitted over the same data-line

    Real-life example:
    enter a url: https://www.ynet.co.il in chrome's address bar and press enter.
    1. we want to send an HTTP GET request and get ynet's homepage
    2. no ip address in cache
    3. figure out what is the ip address of ynet
    4. chrome needs to send a DNS request (A-Record) to figure out ynet's ip address.
    DNS Handler.Request is done in application layer
    5. DNS Handler.Request is done over either UDP/TCP.
    6. chrome needs to ask the operating system to open UDP socket
    7. after UDP socket creation bound to DNS protocol number (port), chrome send DNS request
    8. chrome receives ip address associated with ynet.
    9. chrome again needs services from OS - open TCP socket
    10. only now can chrome send the HTTP GET request

    How are sockets involved in the process?
    What is the role of transport layer?
     */
    /*
    Server:
    1. create a server socket
    2. Bind to a port number.
    3. Listens to incoming connections from clients
    4. If a request is accepted, return an operational socket for that specific connection
    5. Handle each client in a separate thread
    6. Extract tasks from input stream
    7. Tasks are handled according to a concrete strategy
    8. Result is written using socket's OutputStream
     */

    private final int port;
    private volatile boolean stopServer;
    private ThreadPoolExecutor threadPool;
    private volatile boolean requestHandler;

    public TcpServer(int port) {
        this.port = port;
        // initialize data members (although they are initialized by default)
        stopServer = false;
        threadPool = null;
        requestHandler = false;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        final TcpServer myServer = new TcpServer(8010);
        if (!myServer.requestHandler) {
            myServer.run();
        } else {
            System.out.println("The server is already running !");
        }
    }

    public void run() {
        this.requestHandler = true;
        System.out.println("Listening on port " + this.port);
        new Thread(() -> {
            threadPool = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>());
            try {
                try (ServerSocket serverSocket = new ServerSocket(this.port)) {
                    new Thread(() -> {
                        try {
                            acceptRequests(serverSocket);
                            // done with server communications
                            serverSocket.close();
                        } catch (Exception e) {
                            try {
                                serverSocket.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            System.out.println("Communication closed");
                        }
                    }).start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void acceptRequests(ServerSocket serverSocket) throws IOException {
        while (!stopServer) {
            Socket request = null;
            try {
                request = serverSocket.accept();
                if (stopServer) {
                    closeRequestStreams(request);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Server:: Handshake, Done in " + Thread.currentThread().getName());
            Socket finalRequest = request;
            handleRequest(finalRequest);
        }
    }

    private void handleRequest(Socket finalRequest) {
        Runnable runnableClientRequest = () -> {
            System.out.println("Start handling request, Handling in: " + Thread.currentThread().getName());
            try {
                IHandler.handle(finalRequest.getInputStream(), finalRequest.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeRequestStreams(finalRequest);
        };
        threadPool.execute(runnableClientRequest);
    }

    private void closeRequestStreams(Socket finalRequest) {
        try {
            finalRequest.getInputStream().close();
            finalRequest.getOutputStream().close();
            finalRequest.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void stop(){
        System.out.println("Starting server shutdown operation");
        if(!stopServer)
            stopServer = true;
        if(threadPool!=null) {
            threadPool.shutdown();
            System.out.println("Succesfully shutdown server!");
        }
    }

//    public void jvmInfo(){
//        System.out.println(ProcessHandle.current().pid());
//        System.out.println(Runtime.getRuntime().maxMemory());
//    }



}
