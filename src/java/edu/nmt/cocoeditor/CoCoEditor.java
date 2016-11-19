/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.cocoeditor;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Skyler
 */
public class CoCoEditor extends HttpServlet {

    public static final int KEY_FETCH_TIMEOUT = 500;
    
    public static CoCoEditor instance;
    private static PrintWriter lastOut;
    
    private List<ActiveSession> sessions;
    
    private static void setInstance(CoCoEditor instance) {
        System.out.println("Setting isntance");
        CoCoEditor.instance = instance;
    }
    
    
    public void printError(String msg) {
        lastOut.println("Encountered error: " + msg);
    }
    
    private static final SecureRandom random = new SecureRandom();
    private static char[] charset;
    
    private static String generateSessionKey() {
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
        
        
        int len = 24;
        String ret = "";
        while (len > 0) {
            ret += charset[random.nextInt(52)];
            len--;
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
        String id = generateSessionKey();
        int i = 0;
        while (DatabaseStatus.instance().hasSession(id)) {
            if (i > KEY_FETCH_TIMEOUT) {
                printError("Failed to generate unique session key in time");
                return null;
            }
            id = generateSessionKey();
            i++;
        }
        
        addSession(id);
        
        return id;
    }

//    /**
//     * Forwards the user to a session, with the alias in the request
//     * @param sessionID
//     * @param alias the alias to enter the session with
//     * @return the new user ID for the added user in the session
//     **/
//    public String submit(String sessionID, String alias);
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
            sessions = new LinkedList<>();
        
        sessions.add(new ActiveSession(sessionID));
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
