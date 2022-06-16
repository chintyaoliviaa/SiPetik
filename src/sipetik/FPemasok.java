/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipetik;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Chynthia
 */
public class FPemasok extends javax.swing.JInternalFrame implements internal{
koneksi k = new koneksi();
private DefaultTableModel model;
DefaultTableModel tablemodel;
String a,b,c,d,e,klik;
int row;
    /**
     * Creates new form FPemasok
     */
    public FPemasok() {
        initComponents();
        setCode();
    }

    @Override    
    public void setCode(){
        k.query="SELECT MAX(RIGHT(kode_pemasok,2)) AS no FROM tbpemasok";
        k.getData();
        try{
            while(k.rs.next()){
                if(k.rs.first()==false){
                    tfKodeP.setText("P-01");
                }
                else{
                    k.rs.last();
                    
                    int id = k.rs.getInt(1)+1;
                    String no = String.valueOf(id);
                    
                    for(int i=1;i<2;i++){
                        no = "0"+no;
                    }
                    tfKodeP.setText("P-"+no);
                }
            }
        }catch(SQLException e){
            
        }
    }
    
    public void refreshTable() {
        k.getData();
        //Menghapus seluruh data
        model.getDataVector().removeAllElements();

        //Memberi tahu bahwa telah kosong
        model.fireTableDataChanged();

        try {
            
            while (k.rs.next()) {
                //Lakukan penelusuran baris
                Object[] o = new Object[5];
                o[0] = k.rs.getString("Kode Pemasok");
                o[1] = k.rs.getString("Nama Pemasok");
                o[2] = k.rs.getString("Alamat");
                o[3] = k.rs.getString("Penanggungjawab");
                o[4] = k.rs.getString("No HP");

                model.addRow(o);
            }
            k.rs.close();
            k.s.close();
        } catch (SQLException e) {
            System.out.println("Terjadi error " + e);
        }
    }
    
    public void simpanData() { 
        try{
        k.query="insert into tbpemasok values(?,?,?,?,?)";
        k.setData();
        
        
        k.ps.setString(1, tfKodeP.getText());
        k.ps.setString(2, tfNamaP.getText());
        k.ps.setString(3, taAlamatP.getText());
        k.ps.setString(4, tfPJ.getText());
        k.ps.setString(5, tfNoP.getText());
        
        k.ps.executeUpdate();
        clear();
        showData();
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
     }
     
     public void clear(){
         tfKodeP.setText("");
         tfNamaP.setText("");
         taAlamatP.setText("");
         tfPJ.setText("");
         tfNoP.setText("");
     }
     
      public void showData() { 
        try { 
            tablePemasok.setDefaultEditor(Object.class, null);
            Object[] kolom ={"Kode Pemasok", "Nama Pemasok" ,"Alamat" , "Penanggungjawab" , "No. HP"};
            tablemodel= new DefaultTableModel(kolom,0); 
            tablePemasok.setModel(tablemodel);
            k.query="select * from tbpemasok"; 
            k.getData();
            while (k.rs.next()) {
                  a=k.rs.getString("kode_pemasok");
                  b=k.rs.getString("nama_pemasok");
                  c=k.rs.getString("alamat");
                  d=k.rs.getString("pj");
                  e=k.rs.getString("hp");
                  
                  String[] data={a,b,c,d,e};
                  tablemodel.addRow(data);
              }
              k.s.close();
              k.rs.close();
          } catch (SQLException ex) {
              System.out.println("Error"+ex);
          }  
    }
      
       void cariData(){
        try {
            Object[] coloum={"Kode Pemasok","Nama","Alamat","Penanggungjawab","No.HP"};
            tablemodel= new DefaultTableModel(coloum,0);
            tablePemasok.setModel(tablemodel);
            k.query="select kode_pemasok, nama_pemasok, alamat, pj, hp from tbpemasok where kode_pemasok like '%"
                    +tfCariP.getText()+
                    "%' or nama_pemasok like '%"+tfCariP.getText()+
                    "%' or alamat like '%"+tfCariP.getText()+
                    "%' or pj like '%"+tfCariP.getText()+
                    "%' or noHp like '%"+tfCariP.getText()+"%'";
            k.getData();
            while (k.rs.next()) {
                a=k.rs.getString("kode_pemasok");
                b=k.rs.getString("nama_pemasok");
                c=k.rs.getString("alamat");
                d=k.rs.getString("pj");
                e=k.rs.getString("hp");
                String[] data={a,b,c,d,e};
                tablemodel.addRow(data);
            }   
        } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
       }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        tfKodeP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfNamaP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taAlamatP = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        tfPJ = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfNoP = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        tfCariP = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePemasok = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel3.setText("Kode Pemasok");

        tfKodeP.setEditable(false);
        tfKodeP.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfKodeP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfKodePKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel4.setText("Nama Pemasok");

        tfNamaP.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfNamaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaPActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel7.setText("Alamat");

        taAlamatP.setColumns(20);
        taAlamatP.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        taAlamatP.setRows(5);
        jScrollPane1.setViewportView(taAlamatP);

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel8.setText("Penanggungjawab");

        tfPJ.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel9.setText("No. HP");

        tfNoP.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfNoP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNoPKeyTyped(evt);
            }
        });

        btnSimpan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan.png"))); // NOI18N
        btnSimpan.setBorderPainted(false);
        btnSimpan.setContentAreaFilled(false);
        btnSimpan.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpanon.png"))); // NOI18N
        btnSimpan.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan.png"))); // NOI18N
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sunting.png"))); // NOI18N
        btnEdit.setBorderPainted(false);
        btnEdit.setContentAreaFilled(false);
        btnEdit.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/suntingon.png"))); // NOI18N
        btnEdit.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sunting.png"))); // NOI18N
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bersihkan.png"))); // NOI18N
        btnRefresh.setBorderPainted(false);
        btnRefresh.setContentAreaFilled(false);
        btnRefresh.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bersihkanon.png"))); // NOI18N
        btnRefresh.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bersihkan.png"))); // NOI18N
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });

        btnHapus.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus.png"))); // NOI18N
        btnHapus.setBorderPainted(false);
        btnHapus.setContentAreaFilled(false);
        btnHapus.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapuson.png"))); // NOI18N
        btnHapus.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus.png"))); // NOI18N
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel12.setText("CARI DATA PEMASOK");

        tfCariP.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfCariP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariPKeyReleased(evt);
            }
        });

        tablePemasok.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tablePemasok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Pemasok", "Nama Pemasok", "Alamat", "Penanggungjawab", "No. HP"
            }
        ));
        tablePemasok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePemasokMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablePemasok);

        jLabel17.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 0, 0));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/DATA PEMASOK.png"))); // NOI18N

        btnPrint.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cetak.png"))); // NOI18N
        btnPrint.setBorderPainted(false);
        btnPrint.setContentAreaFilled(false);
        btnPrint.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cetakon.png"))); // NOI18N
        btnPrint.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cetak.png"))); // NOI18N
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpan)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)
                        .addGap(174, 174, 174))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfCariP, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tfNamaP)
                                        .addComponent(tfKodeP, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfNoP, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPrint)))
                        .addContainerGap(139, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfKodeP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(tfPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNamaP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(tfNoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEdit)
                    .addComponent(btnSimpan)
                    .addComponent(btnRefresh)
                    .addComponent(btnHapus))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(tfCariP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfNamaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamaPActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        setCode();
        showData();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        simpanData();
        setCode();
        refreshTable();
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
        clear();
        setCode();
    }//GEN-LAST:event_btnRefreshMouseClicked

    private void tfNoPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNoPKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_tfNoPKeyTyped

    private void tablePemasokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePemasokMouseClicked
       try {
            int row =tablePemasok.getSelectedRow();
            klik=(tablePemasok.getModel().getValueAt(row, 0).toString());
            k.query="select * from tbpemasok where kode_pemasok='"+klik+"'";
            k.getData();
            if(k.rs.next()){
                a=k.rs.getString("kode_pemasok");
                tfKodeP.setText(a);
                b=k.rs.getString("nama_pemasok");
                tfNamaP.setText(b);
                c=k.rs.getString("alamat");
                taAlamatP.setText(c);
                d=k.rs.getString("pj");
                tfPJ.setText(d);
                e=k.rs.getString("hp");
                tfNoP.setText(e);
            } } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
    }//GEN-LAST:event_tablePemasokMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
          try { 
           k.query="update tbpemasok set kode_pemasok=?, nama_pemasok=? , alamat=? , pj=? , hp=? where kode_pemasok='"
                   +tfKodeP.getText()+"'";
           k.setData();
           k.ps.setString(1, tfKodeP.getText());
           k.ps.setString(2, tfNamaP.getText());
           k.ps.setString(3, taAlamatP.getText());
           k.ps.setString(4, tfPJ.getText());
           k.ps.setString(5, tfNoP.getText());
           
          k.ps.executeUpdate();
          clear();
          showData();
           
       } catch(SQLException ex) { 
           System.out.println("error" +ex);
       }
          setCode();
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
       int j;
       j= JOptionPane.showConfirmDialog(null, "Anda Ingin Menghapus Data Kode Pemasok "
               +tfKodeP.getText()+" ?","Info",JOptionPane.YES_NO_OPTION);
       if(j==JOptionPane.YES_OPTION){
            try {
                k.query="delete from tbpemasok where kode_pemasok='"+tfKodeP.getText()+"'";
                k.setData();
                k.ps.executeUpdate();
                clear();
                showData();
            } catch (SQLException ex) {
                System.out.println("error "+ex);
            }
       }
       setCode();
    }//GEN-LAST:event_btnHapusMouseClicked

    private void tfCariPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariPKeyReleased
        cariData();
    }//GEN-LAST:event_tfCariPKeyReleased

    private void tfKodePKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfKodePKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_tfKodePKeyTyped

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        try{
            String file = "/eReport/LPemasok.jasper";
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file),null,k.conn());
            JasperViewer.viewReport(print, false);

        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_btnPrintMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea taAlamatP;
    private javax.swing.JTable tablePemasok;
    private javax.swing.JTextField tfCariP;
    private javax.swing.JTextField tfKodeP;
    private javax.swing.JTextField tfNamaP;
    private javax.swing.JTextField tfNoP;
    private javax.swing.JTextField tfPJ;
    // End of variables declaration//GEN-END:variables
}
