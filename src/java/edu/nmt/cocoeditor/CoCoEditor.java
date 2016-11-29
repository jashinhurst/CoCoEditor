/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.cocoeditor;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The serverlet itself. Contains all of the interface methods we set up.
 * @note this implementation is unsuited for actual deployment, since it
 *       ignores existing data in the database and assumes it's empty on
 *       startup. It should (design decision) load up all sessions on startup
 *       and use those. We didn't need that since sessions would assumedly die
 *       when the serverlet dies.
 * @author Skyler
 */
public class CoCoEditor extends HttpServlet {

    public static final int KEY_FETCH_TIMEOUT = 500;
    public static final int KEY_SESSION_LENGTH = 24;
    public static final int KEY_USER_LENGTH = 18;
    
    public static CoCoEditor instance;
    private static PrintWriter lastOut;
    
    private Map<String, ActiveSession> sessions;
    private Map<String, User> users;
    
    public static void checkInstance(){
        if(CoCoEditor.instance == null){
            CoCoEditor.instance = new CoCoEditor();
        }
    }
    
    private static void setInstance(CoCoEditor instance) {
        System.out.println("Setting isntance");
        CoCoEditor.instance = instance;
    }
    
    
    public void printError(String msg) {
        lastOut.println("Encountered error: " + msg);
    }
    
    private static final SecureRandom S_RAND = new SecureRandom();
    private static char[] charset;
    
    private static String generateKey(int length) {
        if (charset == null) {
            charset = new char[62];
            int i = 0;
            for (char ch = '0'; ch < '9'; ch++) {
                charset[i] = ch;
                i++;
            }
            for (char ch = 'a'; ch < 'z'; ch++) {
                charset[i] = ch;
                charset[i+1] = Character.toUpperCase(ch);
                i+=2;
            }
        }
        
        
        String ret = "";
        while (length > 0) {
            ret += charset[S_RAND.nextInt(52)];
            length--;
        }
            
        return ret;
    }
    
    
    
    //Create page and Join page interface

    /**
     * Creates a new session, with no users. The session also has no text,
     * and no history. Returns it's ID
     * @return the session ID
     **/
    public String createSession() {
        String id = generateKey(KEY_SESSION_LENGTH);
        int i = 0;
        while (DatabaseStatus.instance().hasSession(id)) {
           
            if (i > KEY_FETCH_TIMEOUT) {
                printError("Failed to generate unique session key in time");
                return null;
            }
            id = generateKey(KEY_SESSION_LENGTH);
            i++;
        }
        System.out.println("here with id: " + id);
        addSession(id);
        
        return id;
    }

    /**
     * Forwards the user to a session, with the alias in the request
     * @param sessionID
     * @param alias the alias to enter the session with
     * @return the new user ID for the added user in the session
     **/
    public String submit(String sessionID, String alias) {
        //check if session exists, then gen user and return id
        if (!DatabaseStatus.instance().hasSession(sessionID)) {
            printError("Could not locate session from submit id: " + sessionID);
            return null;
        }
        
        String userID = generateKey(KEY_USER_LENGTH);
        if (userID == null) {
            printError("Failed generating user ID");
            return null;
        }
        
        User user = new User(userID);
        user.submit(sessionID, alias);
        
        addUser(userID, user);
        
        return userID;
    }
//
//
//
//
//
//    //Edit page and statistics page interface
//
//    /**
//     * Sets the cursor position for the user.
//     * @param pos the new position
//     **/
//    public void moveCursor(int pos);
//
//    /**
//     * Deletes some number of characters from the text, from the current position.
//     * @param sizeOfDeletion how many characters to delete. Deletes backwards
//     **/
//    public void delete(int sizeOfDeletion);
//
//    /**
//     * Adds text to the hosted text. The text added is as given
//     * @param text the text to add
//     **/
//    public void addText(String text);
//
//    /**
//     * Leaves the current session
//     **/
//    public void leave();
//
//    /**
//     * Returns a link to the current session
//     * @return the link, in string form
//     **/
//    public String getLink();
//
//    /**
//     * Fetches the total text from the server
//     * @return the text from the server, e.g. the server's most recent version
//     **/
//    public String fetchText();
//
//    /**
//     * Fetches statistics from the server, and updates the graphs
//     **/
//    public void refreshStatistics();

    
    
    // Intenral methods
    private void addSession(String sessionID) {
        if (sessions == null)
            sessions = new HashMap<>();
        
        ActiveSession session = new ActiveSession(sessionID);
        session.submit("", new Date(Calendar.getInstance().getTime().getTime()));
        sessions.put(sessionID, session);
    }
    
    private void addUser(String userID, User user) {
        if (users == null)
            users = new HashMap<>();
        
        users.put(userID, user);
    }
    
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setInstance(this);
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            CoCoEditor.lastOut = out;
            
            out.println("Generated key: " + createSession());
                        
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CoCoEditor</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CoCoEditor at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
