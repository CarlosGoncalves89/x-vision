package view;

import controller.Controller;
import exception.CVVException;
import exception.CardNumberException;
import exception.QueryModelException;
import exception.SaveModelException;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 * CheckOut GUI represents the final step of rental process where the customer enters with its card information to payment. 
 * @author 
 */
public class CheckOut extends javax.swing.JFrame {

    private final Controller controller; 
    
    /**
     * Creates a CheckOut GUI using a controller object. 
     * @param controller - the application controller object
     */
    public CheckOut(Controller controller) {
        this.controller = controller;
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panel = new javax.swing.JPanel();
        desktoPanel = new javax.swing.JDesktopPane();
        lblCheckout = new javax.swing.JLabel();
        lblCardNo = new javax.swing.JLabel();
        lblCVV = new javax.swing.JLabel();
        lblExpiry = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        txtCVV = new javax.swing.JTextField();
        txtCardNo = new javax.swing.JTextField();
        txtExpiry = new javax.swing.JTextField();
        btnProceed = new javax.swing.JButton();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblOfferCode = new javax.swing.JLabel();
        txtOfferCode = new javax.swing.JTextField();
        btnSeeBasket = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuHome = new javax.swing.JMenu();
        menuRent = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        panel.setBackground(new java.awt.Color(255, 255, 255));

        desktoPanel.setBackground(new java.awt.Color(255, 255, 255));

        lblCheckout.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblCheckout.setForeground(new java.awt.Color(255, 0, 0));
        lblCheckout.setText("Checkout");

        lblCardNo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCardNo.setForeground(new java.awt.Color(255, 0, 0));
        lblCardNo.setText("Card No:");

        lblCVV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCVV.setForeground(new java.awt.Color(255, 0, 0));
        lblCVV.setText("CVV:");

        lblExpiry.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblExpiry.setForeground(new java.awt.Color(255, 0, 0));
        lblExpiry.setText("Expiry:");

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtCVV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCVV.setForeground(new java.awt.Color(255, 0, 0));
        txtCVV.setAutoscrolls(false);

        txtCardNo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCardNo.setForeground(new java.awt.Color(255, 0, 0));
        txtCardNo.setAutoscrolls(false);

        txtExpiry.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtExpiry.setForeground(new java.awt.Color(255, 0, 0));
        txtExpiry.setAutoscrolls(false);

        btnProceed.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnProceed.setText("PROCEED");
        btnProceed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProceedActionPerformed(evt);
            }
        });

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 0, 0));
        lblEmail.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 0, 0));
        txtEmail.setAutoscrolls(false);

        lblOfferCode.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOfferCode.setForeground(new java.awt.Color(255, 0, 0));
        lblOfferCode.setText("Offer Code:");

        txtOfferCode.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtOfferCode.setForeground(new java.awt.Color(255, 0, 0));
        txtOfferCode.setAutoscrolls(false);

        btnSeeBasket.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btnSeeBasket.setText("SEE YOUR BASKET");
        btnSeeBasket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeeBasketActionPerformed(evt);
            }
        });

        desktoPanel.setLayer(lblCheckout, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(lblCardNo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(lblCVV, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(lblExpiry, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(btnCancel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(txtCVV, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(txtCardNo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(txtExpiry, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(btnProceed, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(lblEmail, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(txtEmail, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(lblOfferCode, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(txtOfferCode, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktoPanel.setLayer(btnSeeBasket, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktoPanelLayout = new javax.swing.GroupLayout(desktoPanel);
        desktoPanel.setLayout(desktoPanelLayout);
        desktoPanelLayout.setHorizontalGroup(
            desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(desktoPanelLayout.createSequentialGroup()
                        .addComponent(btnSeeBasket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProceed, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, desktoPanelLayout.createSequentialGroup()
                        .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCVV, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCheckout, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, desktoPanelLayout.createSequentialGroup()
                                .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCardNo)
                                    .addComponent(lblExpiry)
                                    .addComponent(lblOfferCode))
                                .addGap(26, 26, 26)
                                .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtOfferCode, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCVV, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCardNo, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        desktoPanelLayout.setVerticalGroup(
            desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCheckout)
                .addGap(57, 57, 57)
                .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCardNo)
                    .addComponent(txtCardNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCVV)
                    .addComponent(txtCVV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpiry)
                    .addComponent(txtExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOfferCode)
                    .addComponent(txtOfferCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(desktoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProceed)
                    .addComponent(btnCancel)
                    .addComponent(btnSeeBasket))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        desktoPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCVV, txtCardNo, txtEmail, txtExpiry});

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktoPanel)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addComponent(desktoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        menuHome.setText("Home");
        menuBar.add(menuHome);

        menuRent.setText("Rent Movie");
        menuBar.add(menuRent);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    /**
     * Proceeds the payment process calling the Controller's checkOut function. 
     * @param evt Action Event (press button)
     */
    private void btnProceedActionPerformed(java.awt.event.ActionEvent evt) {                                           
       
        // gets all text values
        String cardNumber = txtCardNo.getText().trim().replaceAll(" ", "");
        String cvv = txtCVV.getText().trim().replaceAll(" ", "");
        String email = txtEmail.getText().trim().replaceAll(" ", "");
        String offerCode = txtOfferCode.getText().trim().replaceAll(" ", "");
        
        //checks if some field is empty
        if(cardNumber.length() > 0 && cvv.length() > 0 && email.length() > 0 && offerCode.length() > 0){
            try 
            {
                this.controller.checkOut(cardNumber, cvv, email, offerCode);
                String message = "Your payment was successful.\nEnjoye your movies.\nGet your discs.";
                JOptionPane.showMessageDialog(null, message, "Payment Successful", JOptionPane.INFORMATION_MESSAGE);
                //close this view and open the welcome view. 
                this.dispose();
                new Welcome(controller).setVisible(true);
            } catch (SQLException | QueryModelException | SaveModelException |  CardNumberException | CVVException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Return Movie ERROR", JOptionPane.ERROR_MESSAGE);
            } 
        }else{
            String message = "Check out: Some fields are empty. Check the fields.";
            JOptionPane.showMessageDialog(null, message, "Some fields are empty", JOptionPane.ERROR_MESSAGE);
            lblCheckout.setText(message);
        }
    }                                                                        

    /***
     * Closes the form and opens Welcome (first screen) form. 
     * @param evt Acition Event
     */
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {                                          
        this.dispose();
        new Welcome(controller).setVisible(true);
    }                                                                            

    /***
     * Comes back to ShoppingBasket form to see the movie Rental list. 
     * @param evt ActionEvent (press button)
     */
    private void btnSeeBasketActionPerformed(java.awt.event.ActionEvent evt) {                                             
        this.dispose();
        new ShoppingBasket(controller).show();
    }                                            
    

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnProceed;
    private javax.swing.JButton btnSeeBasket;
    private javax.swing.JDesktopPane desktoPanel;
    private javax.swing.JLabel lblCVV;
    private javax.swing.JLabel lblCardNo;
    private javax.swing.JLabel lblCheckout;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblExpiry;
    private javax.swing.JLabel lblOfferCode;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuHome;
    private javax.swing.JMenu menuRent;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField txtCVV;
    private javax.swing.JTextField txtCardNo;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtExpiry;
    private javax.swing.JTextField txtOfferCode;
    // End of variables declaration                   
}

