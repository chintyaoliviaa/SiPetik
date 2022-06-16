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

//public class FBatik extends javax.swing.JInternalFrame implements internal
public class FBatik extends javax.swing.JInternalFrame{
koneksi k = new koneksi();
String a,b,c,d,e,f,g;
Integer A, B;
private DefaultTableModel model;
DefaultTableModel tablemodel;
int row;
    /**
     * Creates new form FBatik
     */
    public FBatik() {
        initComponents();
        setCode();
        showData();
    }
    
    public void setCode(){
        try{
            k.query="SELECT MAX(RIGHT(kode_batik,4)) AS no FROM tbbatik";
            k.getData();
            while(k.rs.next()){
                //cek untuk record yang paling awal di tbbatik
                if(k.rs.first()==false){
                    //menampilkan ke dalam textfield kode pemasok
                    tfKodeBatik.setText("BK000001");
                }else{
                    //cek untuk record yang paling alhir di tbbatik
                    k.rs.last();
                    
                    //digit terakhir dari kodeBatik ditambah
                    int id = k.rs.getInt(1) + 1;
                    
                    //konversi ke string
                    String no = String.valueOf(id);
                    //cek untuk angka digit terakhir pada kodeBatik
                    switch (no.length()){
                        case 1:
                            no ="00000" + id;
                            break;
                        case 2:
                            no ="0000" + id;
                            break;
                        case 3:
                            no ="000" + id;
                            break;
                        case 4:
                            no ="00" + id;
                            break;
                        case 5:
                            no ="0" + id;
                            break;
                        case 6:
                            no ="" + id;
                            break;
                    }
                    tfKodeBatik.setText("BK" + no);
                }
            }
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
    }
    
    void simpanStok(){
        try{
            k.query= "INSERT INTO tbstok VALUES (?,?)";
            k.setData();
            k.ps.setString(1, tfKodeBatik.getText());
            k.ps.setString(2, tfStok.getText());
            k.ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
    }
    
    public void clear(){
        tfKodePB.setText("");
        tfNamaBatik.setText("");
        tfStok.setText("");
        tfHargaJual.setText("");
        tfHargaBeli.setText("");
        tfNamaPB.setText("");
    }
    
    public void showData() { 
        try { 
            tableBatik.setDefaultEditor(Object.class, null);
            Object[] kolom ={"Kode Batik", "Nama Batik" ,"Stok" , "Harga Jual" , "Harga Beli"};
            tablemodel= new DefaultTableModel(kolom,0); 
            tableBatik.setModel(tablemodel);
            k.query="SELECT tbbatik.kode_batik, tbbatik.nama_batik, tbbatik.harga_jual, tbbatik.harga_beli, tbstok.stok FROM tbbatik INNER JOIN tbstok WHERE tbbatik.kode_batik=tbstok.kode_batik"; 
            k.getData();
            while (k.rs.next()) {
                  b=k.rs.getString("kode_batik");
                  c=k.rs.getString("nama_batik");
                  d=k.rs.getString("stok");
                  e=k.rs.getString("harga_jual");
                  f=k.rs.getString("harga_beli");
                  
                  String[] data={b,c,d,e,f};
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
            Object[] kolom ={"Kode Batik", "Nama Batik" ,"Stok" , "Harga Jual" , "Harga Beli"};
            tablemodel= new DefaultTableModel(kolom,0);
            tableBatik.setModel(tablemodel);
            k.query="select tbbatik.kode_batik, tbbatik.nama_batik, tbstok.stok, tbbatik.harga_jual, tbbatik.harga_beli,tbbatik.kode_pemasok,  tbpemasok.nama_pemasok FROM tbbatik INNER JOIN tbpemasok on tbbatik.kode_pemasok=tbpemasok.kode_pemasok INNER JOIN tbstok on tbbatik.kode_batik=tbstok.kode_batik where tbbatik.kode_batik like '%"
                    +tfCariBatik.getText()+
                    "%' or tbbatik.nama_batik like '%"+tfCariBatik.getText()+
                    "%' or tbstok.stok like '%"+tfCariBatik.getText()+
                    "%' or tbbatik.harga_jual like '%"+tfCariBatik.getText()+
                    "%' or tbbatik.harga_beli like '%"+tfCariBatik.getText()+
                    "%' or tbpemasok.kode_pemasok like '%"+tfCariBatik.getText()+
                    "%' or tbpemasok.nama_pemasok like '%"+tfCariBatik.getText()+"%'";
            k.getData();
            while (k.rs.next()) {
                a=k.rs.getString("kode_batik");
                b=k.rs.getString("nama_batik");
                c=k.rs.getString("stok");
                d=k.rs.getString("harga_jual");
                e=k.rs.getString("harga_beli");
                f=k.rs.getString("kode_pemasok");
                g=k.rs.getString("nama_pemasok");
                String[] data={a,b,c,d,e,f,g};
                tablemodel.addRow(data);
            }   
        } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
       }
    
    void hapusStok(){
        try { 
           k.query="delete from tbstok where kode_batik='"
                   +tfKodeBatik.getText()+"'";
         
          k.setData();
          k.ps.executeUpdate(k.query);
           
       } catch(SQLException ex) { 
           System.out.println("error" +ex);
       }
    }
    
    void editStok(){
        try{
            k.query="update tbstok set kode_batik=?, stok=?  where kode_batik='"
                   +tfKodeBatik.getText()+"'";
           k.setData();
           k.ps.setString(1,tfKodeBatik.getText());
           k.ps.setString(2,tfStok.getText());
           
          k.ps.executeUpdate();
           
       } catch(SQLException ex) { 
           System.out.println("error" +ex);
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
        tfKodePB = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfNamaPB = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfKodeBatik = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfNamaBatik = new javax.swing.JTextField();
        tfStok = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfHargaJual = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tfHargaBeli = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        tfCariBatik = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableBatik = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel3.setText("Kode Pemasok");

        tfKodePB.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        tfKodePB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfKodePBKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel4.setText("Nama Pemasok");

        tfNamaPB.setEditable(false);
        tfNamaPB.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        tfNamaPB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaPBActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel6.setText("Kode Batik");

        tfKodeBatik.setEditable(false);
        tfKodeBatik.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        tfKodeBatik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfKodeBatikKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel7.setText("Nama Batik");

        tfNamaBatik.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        tfNamaBatik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaBatikActionPerformed(evt);
            }
        });

        tfStok.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        tfStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfStokKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfStokKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel8.setText("Stok Batik");

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel9.setText("Harga Jual");

        tfHargaJual.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        tfHargaJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfHargaJualActionPerformed(evt);
            }
        });
        tfHargaJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfHargaJualKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfHargaJualKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel10.setText("Rp.");

        jLabel11.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel11.setText("Harga Beli");

        jLabel12.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel12.setText("Rp.");

        tfHargaBeli.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        tfHargaBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfHargaBeliActionPerformed(evt);
            }
        });
        tfHargaBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfHargaBeliKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfHargaBeliKeyTyped(evt);
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

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel13.setText("CARI DATA BATIK");

        tfCariBatik.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        tfCariBatik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfCariBatikKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariBatikKeyReleased(evt);
            }
        });

        tableBatik.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tableBatik.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Batik", "Nama Batik", "Stok", "Harga Jual", "Harga Beli"
            }
        ));
        tableBatik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBatikMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableBatik);

        jLabel17.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 0, 0));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/DATA BATIK.png"))); // NOI18N

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
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(tfHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(tfStok, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(18, 18, 18)
                                                .addComponent(tfHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7))
                                    .addGap(25, 25, 25)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tfNamaBatik)
                                        .addComponent(tfKodeBatik, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(90, 90, 90)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                    .addGap(25, 25, 25)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tfNamaPB)
                                        .addComponent(tfKodePB, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(tfCariBatik, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSimpan)
                            .addGap(18, 18, 18)
                            .addComponent(btnEdit)
                            .addGap(18, 18, 18)
                            .addComponent(btnRefresh)
                            .addGap(18, 18, 18)
                            .addComponent(btnHapus))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(btnPrint)))
                    .addComponent(jLabel17))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel17)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tfKodeBatik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNamaBatik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfKodePB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNamaPB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnEdit)
                    .addComponent(btnRefresh)
                    .addComponent(btnHapus))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(tfCariBatik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfNamaPBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaPBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamaPBActionPerformed

    private void tfNamaBatikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaBatikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamaBatikActionPerformed

    private void tfHargaJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfHargaJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfHargaJualActionPerformed

    private void tfHargaBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfHargaBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfHargaBeliActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void tfStokKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfStokKeyReleased
        int a = Integer.parseInt(tfStok.getText());
        if(a<=0){
            JOptionPane.showMessageDialog(null, "Stok harus lebih dari 0", "Warning", JOptionPane.WARNING_MESSAGE);
            tfStok.setText("");
        }
    }//GEN-LAST:event_tfStokKeyReleased

    private void tfStokKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfStokKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            JOptionPane.showMessageDialog(null, "Only number", "Information", JOptionPane.INFORMATION_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_tfStokKeyTyped

    private void tfHargaJualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfHargaJualKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            JOptionPane.showMessageDialog(null, "Only number", "Information", JOptionPane.INFORMATION_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_tfHargaJualKeyTyped

    private void tfHargaBeliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfHargaBeliKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            JOptionPane.showMessageDialog(null, "Only number", "Information", JOptionPane.INFORMATION_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_tfHargaBeliKeyTyped

    private void tfHargaJualKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfHargaJualKeyReleased
        int a = Integer.parseInt(tfHargaJual.getText());
        if(a<=0){
            JOptionPane.showMessageDialog(null, "Harga jual harus lebih dari 0", "Warning", JOptionPane.WARNING_MESSAGE);
            tfHargaJual.setText("");
        }
    }//GEN-LAST:event_tfHargaJualKeyReleased

    private void tfHargaBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfHargaBeliKeyReleased
        int a = Integer.parseInt(tfHargaBeli.getText());
        if(a<=0){
            JOptionPane.showMessageDialog(null, "Harga beli harus lebih dari 0", "Warning", JOptionPane.WARNING_MESSAGE);
            tfHargaBeli.setText("");
        }
    }//GEN-LAST:event_tfHargaBeliKeyReleased

    private void tfKodePBKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfKodePBKeyReleased
        try{
            //cek kode pemasok
            k.query = "SELECT nama_pemasok FROM tbpemasok WHERE kode_pemasok = '" +tfKodePB.getText() +"'";
            k.getData();
            while (k.rs.next()){
                a= k.rs.getString("nama_pemasok");
            }
            if(a!=null){
                tfNamaPB.setText(a);
            }
        }catch(SQLException e){
            System.out.println("Error "+e);
        }
    }//GEN-LAST:event_tfKodePBKeyReleased

    private void tfKodeBatikKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfKodeBatikKeyReleased
       
    }//GEN-LAST:event_tfKodeBatikKeyReleased

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        A = Integer.parseInt(tfHargaJual.getText());
        B = Integer.parseInt(tfHargaBeli.getText());
        
        //cek harga jual jika lebih kecil
        if ((A<B) || A==B){
            JOptionPane.showMessageDialog(null, "Harga Jual Kurang!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }else{
            try{
                k.query="INSERT INTO tbbatik VALUES (?,?,?,?,?)";
                k.setData();
                k.ps.setString(1,tfKodeBatik.getText());
                k.ps.setString(2,tfKodePB.getText());
                k.ps.setString(3,tfNamaBatik.getText());
                k.ps.setString(4,tfHargaJual.getText());
                k.ps.setString(5,tfHargaBeli.getText());
                k.ps.executeUpdate();
                simpanStok();
                setCode();
                showData();
                clear();
            }catch(SQLException e){
                System.out.println("Error "+e);
            }
        }
    }//GEN-LAST:event_btnSimpanMouseClicked
    String klik;
    private void tableBatikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBatikMouseClicked
        try {
            int row =tableBatik.getSelectedRow();
            klik=(tableBatik.getModel().getValueAt(row, 0).toString());
            k.query="select tbbatik.kode_pemasok, tbbatik.kode_batik, tbbatik.nama_batik, tbbatik.harga_jual, tbbatik.harga_beli, tbpemasok.nama_pemasok, tbstok.stok FROM tbbatik INNER JOIN tbpemasok on tbbatik.kode_pemasok=tbpemasok.kode_pemasok INNER JOIN tbstok on tbbatik.kode_batik=tbstok.kode_batik WHERE tbbatik.kode_batik='"+klik+"'";
            k.getData();
            if(k.rs.next()){
                tfKodePB.setText(k.rs.getString("kode_pemasok"));
                tfNamaPB.setText(k.rs.getString("nama_pemasok"));
                tfKodeBatik.setText(k.rs.getString("kode_batik"));
                tfNamaBatik.setText(k.rs.getString("nama_batik"));
                tfStok.setText(k.rs.getString("stok"));
                tfHargaJual.setText(k.rs.getString("harga_jual"));
                tfHargaBeli.setText(k.rs.getString("harga_beli"));
            } } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
    }//GEN-LAST:event_tableBatikMouseClicked

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
        clear();
        setCode();
    }//GEN-LAST:event_btnRefreshMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        try { 
           editStok();
           k.query="update tbbatik set kode_batik=?, kode_pemasok=? ,nama_batik=? , harga_jual=?, harga_beli=? where kode_batik='"
                   +tfKodeBatik.getText()+"'";
           k.setData();
           k.ps.setString(1,tfKodeBatik.getText());
           k.ps.setString(2,tfKodePB.getText());
           k.ps.setString(3,tfNamaBatik.getText());
           k.ps.setString(4,tfHargaJual.getText());
           k.ps.setString(5,tfHargaBeli.getText());
           
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
       j= JOptionPane.showConfirmDialog(null, "Anda Ingin Menghapus Data Kode Batik "
               +tfKodeBatik.getText()+" ?","Info",JOptionPane.YES_NO_OPTION);
       if(j==JOptionPane.YES_OPTION){
            try {
                hapusStok();
                k.query="delete from tbbatik where kode_batik='"+tfKodeBatik.getText()+"'";
                k.setData();
                k.ps.executeUpdate(k.query);
                showData();
                setCode();
                clear();
                
            } catch (SQLException ex) {
                System.out.println("error "+ex);
            }
       }
       
    }//GEN-LAST:event_btnHapusMouseClicked

    private void tfCariBatikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariBatikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCariBatikKeyPressed

    private void tfCariBatikKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariBatikKeyReleased
        cariData();
    }//GEN-LAST:event_tfCariBatikKeyReleased

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        try{
            String file = "/eReport/LBatik.jasper";
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableBatik;
    private javax.swing.JTextField tfCariBatik;
    private javax.swing.JTextField tfHargaBeli;
    private javax.swing.JTextField tfHargaJual;
    private javax.swing.JTextField tfKodeBatik;
    private javax.swing.JTextField tfKodePB;
    private javax.swing.JTextField tfNamaBatik;
    private javax.swing.JTextField tfNamaPB;
    private javax.swing.JTextField tfStok;
    // End of variables declaration//GEN-END:variables
}
